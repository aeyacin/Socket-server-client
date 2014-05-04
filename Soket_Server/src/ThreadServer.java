

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author HALO
 */
public class ThreadServer extends Thread implements Runnable{
    
 static ServerSocket server=null;
 static Socket Client=null;
 String oku;
 static DataOutputStream Gonder=null;
 static DataInputStream Al=null;   
 String Responseline;
public static  ThreadServer1 threadler[] = new ThreadServer1[20];
 
 private int portno;
    public ThreadServer(int port){
        this.portno=port;
        
    }
    

 @Override
    public void run(){
        try {
            
            server = new ServerSocket(portno);
            
            Server.jTextArea1.append("Sunucu Başlatıldı.\n\n");
            
                while(true){
                    try {
                         
                    Client= server.accept();
                    
                     
                     for(int i=0; i<=9; i++){
		    if(threadler[i]==null)
			{
                             
			    (threadler[i] = new ThreadServer1(Client,i)).start(); // Yeni istemci gelince onun için istemciLif oluşturuyor ve yine dinleme moduna geciyor.
			  Server.jTextArea1.append("\nYeni Kullanıcı Bağlandı :"+threadler[i].getName()+"\n");
                          
                          break;
                         			}
                    
		}
                     
                     
                     } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } 
                }
                     
           
        } catch (IOException ex) {
            Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
  
    
    
}
