package vio;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
 import javax.imageio.ImageIO;

 public class ClientJoin extends Thread {
   
   public final static int SOCKET_PORT = 13268;      
   public final static String SERVER = "127.0.0.1";   

   public final static int FILE_SIZE = 2504522; 

   @Override
   public void run(){
       try {
           int bytesRead;
           int current = 0;
           FileOutputStream fos = null;
           BufferedOutputStream bos = null;
           Socket sock;
           BufferedImage bi=ImageIO.read(new File("c:/Grid/Img.png"));
           
           InputStream is;
           ByteArrayInputStream bais;
           
           byte [] mybytearray=new byte [FILE_SIZE];
           
           
           ConferenceWindowClient f1=new ConferenceWindowClient(bi); 
           
           
           while(true)
           {
               
               sock = new Socket(SERVER, SOCKET_PORT);
               System.out.println("Connecting...");
               
               // receive file
               System.out.println(Runtime.getRuntime().freeMemory());
               is = sock.getInputStream();
               bytesRead = is.read(mybytearray,0,mybytearray.length);
               bais=new ByteArrayInputStream(mybytearray);
               bi=ImageIO.read(bais);
               
//  sfc1.serverImage(bi);


f1.updateFrames(bi);



bais.close();
is.close();

bi.flush();


sock.close();
System.gc();
           }    } catch (IOException ex) {  
           Logger.getLogger(ClientJoin.class.getName()).log(Level.SEVERE, null, ex);
       }
}
}
