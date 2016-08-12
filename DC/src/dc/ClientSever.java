package dc;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

 class ClientServer 
{
 ServerSocket ss;
 Socket s;
 Thread th;
 
 public ClientServer(final String d)
 {
     
      try{
        ss=new ServerSocket(1243);
        while(true)
        {if((s=ss.accept())!=null)
        {
          th=new Thread() {@Override
 public void run()
          {
                            BufferedReader br = null;
                            try {
                                Socket g=s;
                                br = new BufferedReader(new InputStreamReader(g.getInputStream()));
                                String h=br.readLine();
                                System.out.println(d+"\\"+h);
                                FileInputStream fis = new FileInputStream(d+"\\"+h);
                                byte [] buffer= new byte[fis.available()];
                                fis.read(buffer);
                                ObjectOutputStream oos = new ObjectOutputStream(g.getOutputStream()) ;
                                oos.writeObject(buffer);
                                g.close();
                                oos.close();
                                fis.close();
                            } catch (IOException ex) {
                                Logger.getLogger(ClientServer.class.getName()).log(Level.SEVERE, null, ex);
                            } 
          }
          };//start a new thread
          th.setDaemon(true);//set the thread as demon
          th.start();  
       }
            
        }
     }catch(Exception e)
  {
  }
  }
 
 
 
 
 //Main method
static public void Qu(String f,String q,String path)
 {      try {
     
            PrintWriter pw;
          Socket su=new Socket(f,1243);
          pw=new PrintWriter(su.getOutputStream(),true);
         pw.println(q);
         ObjectInputStream ois = new ObjectInputStream(su.getInputStream());
byte[] buffer= (byte[])ois.readObject();
FileOutputStream fos;
if(path!=null)
{
fos = new FileOutputStream(path+"\\"+q);
}
else
    fos=new FileOutputStream(q);
    fos.write(buffer);
fos.close();
}     
 catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientServer.class.getName()).log(Level.SEVERE, null, ex);
        }
 catch (UnknownHostException ex) {
            Logger.getLogger(ClientServer.class.getName()).log(Level.SEVERE, null, ex);
        }
 catch (IOException ex) {
            Logger.getLogger(ClientServer.class.getName()).log(Level.SEVERE, null, ex);
        }
 }
 }