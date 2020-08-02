///** @file bCapSlvMove.java
// *
// *  @brief b-CAP client program
// *
// *  @version	1.0
// *	@date		2013/2/20
// *	@author		DENSO WAVE (m)
// *
// */
//
///*
//[NOTES]
// This is a sample source code controlling RC8 with SlaveMode.
// Copy and modify this code in accordance with a device and a device version.
// Especially please note timeout and timeout-retry settings.
//*/
//
//import jbCAP.*;
//import jVARIANT.*;
//import painting.*;
//
//import javax.swing.SwingUtilities;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.BorderFactory;
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.Graphics; 
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseMotionListener;
//import java.awt.event.MouseMotionAdapter;
//
//
//
//
//public class bCapSlaveMove extends Thread{
//	public static void main(String[] args){
//		new bCapSlaveMove();
//	}
//	
//	private static final String SERVER_IP_ADDRESS = "192.168.0.2"; /* Your controller IP address */
//	private static final int SERVER_PORT_NUM = 5007;
//	
//	private static final int PERIOD = 100;	/* Period Cycle */
//	private static final int AMPLITUDE = 10;/* Amplitude */
//	
//	private static final int E_BUF_FULL = 0x83201483;
//	
//	public bCapSlaveMove(){
//		Thread th = new Thread(this);
//		th.start();
//	}
//	
//	
//	@Override
//	public void run(){		      
//        
//		bCapEngine eng = new bCapEngine();
//
//		/* Get controller handle */
//		bCapController ctrl = eng.AddController(SERVER_IP_ADDRESS, SERVER_PORT_NUM, true, 1000, 1, "", "CaoProv.DENSO.VRC", SERVER_IP_ADDRESS, "");
//
//		if(eng.HRESULT() >= 0){
//			/* Robot access */
//			bCapRobot robot = ctrl.AddRobot("", "");
//			if(ctrl.HRESULT() >= 0){						        								
//				
//				/* Move to first pose */
////				robot.Move(1, new VARIANT(VARENUM.VT_BSTR, "@E J1"), "");
//				//robot.Speed(1, 80);
//				//int xM = MyPanel.sendX;
//				//int yM = MyPanel.sendY;
//				//robot.Move(1, new VARIANT(VARENUM.VT_BSTR, "@P P("+xM+","+yM+",185,0,1,1,1)"), "");
//				
//				for(int i = 0; i< 20; i++) {
////					robot.Move(1, new VARIANT(VARENUM.VT_BSTR, "@P T(390,-170,152,-0.8,-0.4,0,0,0,1,1)"), "");
////					robot.Move(1, new VARIANT(VARENUM.VT_BSTR, "@P T(390, -170,132,-0.8,-0.4,0,0,0,1,1)"), "");
//					
//					robot.Move(1, new VARIANT(VARENUM.VT_BSTR, "@P P(415,-170,185,0,1,1,1)"), "");
//					robot.Move(1, new VARIANT(VARENUM.VT_BSTR, "@P P(415,-170,105,0,1,1,1)"), "");
////					robot.Move(1, new VARIANT(VARENUM.VT_BSTR, "@P P(150,170,105,0,1,1,1)"), "");
//				}
//				
//
//			}
//			
//			/* Release robot handle */
//			ctrl.get_Robots().Remove(robot.get_Name());
//		}
//		
//		/* Release controller handle, Stop b-CAP service (Very important in UDP/IP connection) */
//		eng.get_Controllers().Remove(ctrl.get_Name());
//		
//		eng.Release();
//	}
//}
//
//
