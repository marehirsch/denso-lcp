package painting;

import jbCAP.*;
import jVARIANT.*;

import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics; 
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.awt.event.MouseMotionAdapter;

import painting.*;



public class mouseCtrl {
    
    public static void main(String[] args) {   
    	
    		SwingUtilities.invokeLater(new Runnable() {
    			public void run() {
    				gui();    				
    			}
    		});   
    	
    }
    
    public static void gui() {        
        JFrame f = new JFrame("Mouse Control");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        mPanel p = new mPanel();
        f.add(p);
        f.setSize(340,265);
        f.setVisible(true);        		
    }
    
}

// class for handling mouse interaction and updating GUI accordingly
class mPanel extends JPanel {
	
	public static int mouseX = 150;
	public static int mouseY = 170;
	
	RedSquare redSquare = new RedSquare();	

	// new instance of the robot control protocol. r.run() sets robot to active listening state
	// currently, r can append verts from CSV to "r.vertsR" and send as a list of commands
	// or, stream positions in real-time with "mouseX/mouseY"
	bCapSlaveMove r = new bCapSlaveMove();
	csvIn in = new csvIn();
	
	
    public mPanel() {    	
        setBorder(BorderFactory.createLineBorder(Color.black));

        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                moveSquare(e.getX(),e.getY());
                in.readCsv();
                r.vertsR.addAll(in.verts);
                r.run();
            }
        });

        addMouseMotionListener(new MouseAdapter(){
            public void mouseDragged(MouseEvent e){
                moveSquare(e.getX(),e.getY()); 
                int mapX = 415 - e.getY();
                int mapY = e.getX() - 170;
                mouseX = mapX;
                mouseY = mapY;
//                r.run();
                
            }
        });        
    }

    public void moveSquare(int x, int y){
 
        // Current square state, stored as final variables 
        // to avoid repeat invocations of the same methods.
        int CURR_X = redSquare.getX();
        final int CURR_Y = redSquare.getY();
        final int CURR_W = redSquare.getWidth();
        final int CURR_H = redSquare.getHeight();
        final int OFFSET = 1;

        if ((CURR_X!=x) || (CURR_Y!=y)) {

            // The square is moving, repaint background 
            // over the old square location. 
            repaint(CURR_X,CURR_Y,CURR_W+OFFSET,CURR_H+OFFSET);

            // Update coordinates.
            redSquare.setX(x);
            redSquare.setY(y);

            // Repaint the square at the new location.
            repaint(redSquare.getX(), redSquare.getY(), 
                    redSquare.getWidth()+OFFSET, 
                    redSquare.getHeight()+OFFSET);
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(250,200);
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);              
        redSquare.paintSquare(g);
        
    }      

}

// class representing x-y position of robot end effector 
class RedSquare{
	
    private int xPos = 50;
    private int yPos = 50;
    private int width = 10;
    private int height = 10;
    

    public void setX(int xPos){ 
        this.xPos = xPos - 5;
    }

    public int getX(){
    
        return xPos;
    }
    
    
    public void setY(int yPos){
        this.yPos = yPos - 5;
    }

    public int getY(){
        return yPos;
    }

    public int getWidth(){
        return width;
    } 

    public int getHeight(){
        return height;
    }

    public void paintSquare(Graphics g){
        g.setColor(Color.RED);
        g.fillRect(xPos,yPos,width,height);
        g.setColor(Color.BLACK);
        g.drawRect(xPos,yPos,width,height);       
 
    }
}



class bCapSlaveMove extends Thread{
	
	public ArrayList<String> vertsR = new ArrayList<String>();
	public static void main(String[] args){
		new bCapSlaveMove();
	}
	
	// initialize robot controller connections and settings
	private static final String SERVER_IP_ADDRESS = "192.168.0.2"; /* Your controller IP address */
	private static final int SERVER_PORT_NUM = 5007;
	
	private static final int PERIOD = 100;	/* Period Cycle */
	private static final int AMPLITUDE = 10;/* Amplitude */
	
	private static final int E_BUF_FULL = 0x83201483;
	
	public bCapSlaveMove(){
		Thread th = new Thread(this);
		th.start();
	}
	
	@Override
	public void run(){		      
        // activate robot controller protocol 
		bCapEngine eng = new bCapEngine();

		/* Get controller handle */
		bCapController ctrl = eng.AddController(SERVER_IP_ADDRESS, SERVER_PORT_NUM, true, 1000, 1, "", "CaoProv.DENSO.VRC", SERVER_IP_ADDRESS, "");

		if(eng.HRESULT() >= 0){
			/* Robot access */
			bCapRobot robot = ctrl.AddRobot("", "");
			if(ctrl.HRESULT() >= 0){						        								
				
				/* Move to initial pose */
//				robot.Move(1, new VARIANT(VARENUM.VT_BSTR, "@E J1"), "");
				//robot.Speed(1, 80);
				
				/* Method 1: mouse control */
				robot.Move(1, new VARIANT(VARENUM.VT_BSTR, "@P P("+mPanel.mouseX+","+mPanel.mouseY+",185,0,1,1,1)"), "");
			
				
				/* Method 2: explicit poses with T or P move methods */
//				for(int i = 0; i< 1; i++) {
//					robot.Move(1, new VARIANT(VARENUM.VT_BSTR, "@P T(390,-170,152,-0.8,-0.4,0,0,0,1,1)"), "");
//					robot.Move(1, new VARIANT(VARENUM.VT_BSTR, "@P T(400, 170,132,-0.8,-0.4,0,0,0,1,1)"), "");
//					
//					robot.Move(1, new VARIANT(VARENUM.VT_BSTR, "@P P(415,-170,185,0,1,1,1)"), "");
//					robot.Move(1, new VARIANT(VARENUM.VT_BSTR, "@P P(150,170,105,0,1,1,1)"), "");
//				}
				
				/* Method 3: stream vectors from CSV */
//				for(int i=0; i< vertsR.size()-1; i+=2 ) {
//	                robot.Move(1, new VARIANT(VARENUM.VT_BSTR, "@P P("+ vertsR.get(i+1)+","+ vertsR.get(i)+",185,0,1,1,1)"), "");
//	                System.out.println(i+1);
//	            }
				

			}
			
			/* Release robot handle */
			ctrl.get_Robots().Remove(robot.get_Name());
		}
		
		/* Release controller handle, Stop b-CAP service (Very important in UDP/IP connection) */
		eng.get_Controllers().Remove(ctrl.get_Name());
		
		eng.Release();
	}
}


