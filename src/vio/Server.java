/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vio;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamException;
import com.github.sarxos.webcam.WebcamResolution;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;


public class Server {

public final static int SOCKET_PORT = 13268; 
public static boolean flag=true;
static Socket sock;
static ServerSocket servsock;

public static void endServer(){
flag=false;
}

public static void startServer (String name) throws IOException {
    try{    
            Thread t1=new Thread(){
                @Override
                public void run(){
                    try {
                        new ChatServer().startChat();
                    } catch (Exception ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
            };
            
            t1.start();
        
BufferedInputStream bis = null;
OutputStream os;
flag=true;
ByteArrayOutputStream baos;
servsock = new ServerSocket(SOCKET_PORT);
servsock.setReuseAddress(true);

    Webcam webcam = Webcam.getDefault();
    Dimension[] dim=new Dimension[]{WebcamResolution.VGA.getSize()};
                webcam.setCustomViewSizes(dim);
		webcam.setViewSize(WebcamResolution.VGA.getSize());
//    webcam.setViewSize(WebcamResolution.VGA.getSize());
    webcam.open();
    
    
    
    
    
    new ClientServer().start();
    
    
    
    
    
    
    while(flag)
    {
//        System.out.println("Waiting...");
        sock = servsock.accept();
        sock.setReuseAddress(true);
//        System.out.println("Accepted connection : " + sock);
        BufferedImage bi = webcam.getImage();
        baos=new ByteArrayOutputStream();
        ImageIO.write(bi,"jpg",baos);
        baos.flush();
        byte [] myarray=baos.toByteArray();
        os = sock.getOutputStream();
//        System.out.println("Sending (" + myarray.length + "bytes)");    
        os.write(myarray,0,myarray.length);
        os.flush();
//        System.out.println("Done.");
        if(baos!=null)baos.close();
        if (bis != null) bis.close();
        if (os != null) os.close();
        if (sock!=null) sock.close();
        System.gc();
    }
    if(flag==false)
    {
    webcam.close();
    sock.close();
    servsock.close();
    if(sock.isBound())
           {
               System.out.println("chipak gyi nhi");
           }
    }
}catch(IOException | WebcamException ex)
{
    System.out.println("Exception occured");
   ex.printStackTrace();
    MainWindow.conferenceActive=false;
}
}
}
