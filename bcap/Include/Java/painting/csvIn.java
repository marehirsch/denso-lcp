package painting;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class csvIn {
	
	public ArrayList<String> verts = new ArrayList<String>();
	public String[] fields;
	
 
 public static void main(String[] args) {
  // load a csv print file	 
  String filePath = "C:\\Users\\Retouch Admin\\Downloads\\halfPyramid_lcp_adjusted.csv";    
  System.out.println("starting read user.csv file");
 }
 
 
 public void readCsv() {
  BufferedReader reader = null;
  
  try {
   String line = "";
   reader = new BufferedReader(new FileReader("C:\\Users\\Retouch Admin\\Downloads\\halfPyramid_lcp_adjusted.csv"));
   reader.readLine();
   
   // add all vertices to the list "verts"
   while((line = reader.readLine()) != null) {
	   fields = line.split(",");
    
	   if(fields.length > 0) {
		   verts.add(fields[0]);
		   verts.add(fields[2]);
	   }
   }

   
  } catch (Exception ex) {
   ex.printStackTrace();
  } finally {
   try {
    reader.close();
   } catch (Exception e) {
    e.printStackTrace();
   }
  }
  
 }
}