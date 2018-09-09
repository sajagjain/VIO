/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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


 public class ClientServer extends Thread{
   
   public final static int SOCKET_PORT = 13268;      
   public final static String SERVER = "localhost";   

   public final static int FILE_SIZE = 250452; 
   public static boolean flag=true;
   static Socket sock;
   
   public static void endClient()
   {
   flag=false;
   }
   
   @Override
   public void run(){
       try {
           int bytesRead;
           int current = 0;
           FileOutputStream fos = null;
           BufferedOutputStream bos = null;
           
           BufferedImage bi=ImageIO.read(new File("c:/Grid/Img.png"));
          
           InputStream is;
           ByteArrayInputStream bais;
           
           byte [] mybytearray=new byte [FILE_SIZE];
           
           new AudioClient().captureAudio();
           
           flag=true;
          
           ConferenceWindowServer f = new ConferenceWindowServer(bi) ;
           
           
           bi=null;
           
//           System.out.println("bi="+bi);
           while(flag)
           {
               
               sock = new Socket(SERVER, SOCKET_PORT);
//               System.out.println("Connecting...");
               
               // receive file
//               System.out.println(Runtime.getRuntime().freeMemory());
               is = sock.getInputStream();
               bytesRead = is.read(mybytearray,0,mybytearray.length);
               bais=new ByteArrayInputStream(mybytearray);
               bi=ImageIO.read(bais);
               
//  sfc1.serverImage(bi);
                if(bi==null)
                {
                    System.out.println("frame null");
                    
                }
               f.updateFrames(bi);
                



bais.close();
is.close();

bi.flush();


sock.close();
System.gc();
           }    } catch (IOException ex) {  
           Logger.getLogger(ClientServer.class.getName()).log(Level.SEVERE, null, ex);
       }
}
}
