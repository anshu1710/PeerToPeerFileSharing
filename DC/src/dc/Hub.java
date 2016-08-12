package dc;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.sql.ResultSet;

public class Hub extends Frame implements Runnable
{
 //Declarations
 TextArea ta;
 ServerSocket ss;
 Socket s;
 
 BufferedReader br;
 Thread th;
 Connector conn;
 
 public Hub()
 {
      conn=new Connector();
       conn.createConnection("localhost","root","anshu","anshu");
       conn.create();
  Frame f=new Frame("Server Side Chatting");//Frame for Server
  f.setLayout(new FlowLayout());//set layout
  f.setBackground(Color.orange);//set background color of the Frame
  ta=new TextArea(12,20);
  ta.setEditable(false);
  ta.setBackground(Color.cyan);
  f.addWindowListener(new W1());//add Window Listener to the Frame
  f.add(ta);//Add TextArea to the frame
  try{
   ss=new ServerSocket(1241);//Socket for server
   while(true)
   {if((s=ss.accept())!=null)
   {
       th=new Thread(this);//start a new thread
  th.setDaemon(true);//set the thread as demon
  th.start();
  setFont(new Font("Arial",Font.BOLD,20));
  f.setSize(200,300);//set the size
  f.setLocation(300,300);//set the location
  f.setVisible(true);
  f.validate();
   }
   }
  }catch(Exception e)
  {
  }
  
 }
 //method required to close the Frame on clicking "X" icon.
 private class W1 extends WindowAdapter
 {
        @Override
  public void windowClosing(WindowEvent we) 
  {
   System.exit(0);
  }
 }
 @Override
 public void run()
 {
      Socket s1;
    s1=s;
  while(true)
  {
   try{
       br=new BufferedReader(new InputStreamReader(s1.getInputStream()));
    String h=br.readLine();
    if("delete".equals(h)==true)
    {conn.deleteip(s1.getInetAddress().toString());
     ta.append("\n IP left:"+s1.getInetAddress().toString()+"\n");}
    if("query".equals(h)==true)
    { h=br.readLine();
       ResultSet rs= conn.query(h);
 PrintWriter  pw=new PrintWriter(s1.getOutputStream(),true);
  while(rs.next())
    {
      pw.println(rs.getString(1).substring(1));
      }
    }   
    if("update".equals(h)==true) 
    { h=br.readLine();
        while(!h.equals("end"))
        {
    conn.insert(s1.getInetAddress().toString(),h);
     h=br.readLine();
      }
                    ta.append("Upload from IP:"+ s1.getInetAddress().toString()+"\n");//Append to TextArea
    System.out.println(h); 
    }
    }catch(Exception e)
   {
   }
  } 
 }
 //Main method
 public static void main(String args[]) 
 {  
  Hub server = new Hub();
 }
} 
