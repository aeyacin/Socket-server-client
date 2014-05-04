

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
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
public class ThreadServer1 extends Thread implements Runnable{
    

 private  Socket Client=null;
 String oku;
 static DataOutputStream Gonder=null;
 static DataInputStream Al=null;   
 private static Object kilit = new Object();
 private static Lock lock = new ReentrantLock();
 String Responseline;
 int ThreadGelen;
 String adres="";
 
 long gonderbeklemesuresi;

 
 private int portno;
    public ThreadServer1(Socket serverclient,int gelenThread){
        this.Client=serverclient;
        this.ThreadGelen=gelenThread;
        this.gonderbeklemesuresi=5000;
    }
    
   
     private String readFile( String file ) throws IOException {
    BufferedReader reader = new BufferedReader( new FileReader (file));
    String         line = null;
    StringBuilder  stringBuilder = new StringBuilder();
    String         ls = System.getProperty("line.separator");
    

    while( ( line = reader.readLine() ) != null ) {
        stringBuilder.append( line );
        stringBuilder.append( ls );
    }

    return stringBuilder.toString();
}
     
        public String sistemTarihiniGetir(String tarihFormati){
 Calendar takvim = Calendar.getInstance();
 SimpleDateFormat sdf = new SimpleDateFormat(tarihFormati);
 return sdf.format(takvim.getTime());
}
 @Override
    public void run(){
      
        
        try {
            
                 try (
                         DataInputStream reading = new DataInputStream(Client.getInputStream())) {
                     switch (reading.readUTF()) {
                         case "Reader":{
                             String Readeradi="";
                                     try {
                                         Gonder = new DataOutputStream(Client.getOutputStream());
                                        Al= new DataInputStream(Client.getInputStream());
                                        Readeradi=Al.readUTF();
                                        adres=Client.getLocalAddress().toString();
                                        Server.jTextArea1.append("Reader Bağlandı.İP Adresi: "+adres+"\n");
                                           Server.jTextArea1.append("Reader Adı: "+Readeradi+"\n");
                   String durdur="";
                    String Gönderi;
                                           while(Gonder!=null){
                                                    
                                          
 synchronized (kilit){   
                      Gönderi= readFile("kayit.server");
         
                     }
   
 Gonder.writeUTF(Gönderi);
            Server.jTextArea1.append("Veri Gönderildi: "+sistemTarihiniGetir("H:mm:ss")+" ->"+Readeradi+"\n");
            ThreadServer1.sleep(gonderbeklemesuresi);
           
                                           }
                                    
                                         
                                           Gonder.close();
                                         Al.close();
                                     } catch (IOException ex) {
                                         Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
                                     } catch (InterruptedException ex) {
                             Logger.getLogger(ThreadServer1.class.getName()).log(Level.SEVERE, null, ex);
                         }
                                 }
                              Client.close();
                              Server.jTextArea1.append(ThreadServer.threadler[this.ThreadGelen].getName()+" Durumu"+":"+ ThreadServer.threadler[this.ThreadGelen].getState()+"\n"); 
                            
                             Server.jTextArea1.append("Reader Ayrıldı.İP Adresi: "+adres+"\n\n");
                       if((this.ThreadGelen)!=0){
                            Server.jTextArea1.append(ThreadServer.threadler[this.ThreadGelen-1].getName()+" Durumu"+":"+ ThreadServer.threadler[this.ThreadGelen-1].getState()+"\n\n"); 
                            
                          }
                          break;
 //*****************************************************************************************************************************                            
                            
                         case "Writer":{
                            String AlınanVeri="";
                             adres=Client.getLocalAddress()+"";
                                     try {
                                         Al = new DataInputStream(Client.getInputStream());
                                    
                                       
                            Server.jTextArea1.append("Writer Bağlandı.İP Adresi: "+Client.getLocalAddress()+"\n");
                                         while ((Responseline = Al.readUTF()) != null) {
                                             AlınanVeri=adres+";"+Responseline;
                                             Server.jTextArea2.append("Gönderen Adres : "+adres+"   "+AlınanVeri+"\n");
                        }
                                         
                                     } catch (IOException ex) {
                                         Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
                                     }
                            
                                    
                                     
        lock.lock();
                         try {
                             //Buraya dosya yazma fonksiyonu çağrılır ve gerekli bilgiler alınarak yazılır
                             FileWriter yazici = new FileWriter("kayit.server",true);
                             BufferedWriter yaz = new BufferedWriter(yazici);
                             yaz.append(AlınanVeri);
                             yaz.newLine();
                             yaz.close();
                             yazici.close();  } finally {
                             
                         }
                         
      lock.unlock();                   
           synchronized (kilit){// Veri okunurken diğer işlemlerin erişimi engeller
                        
                         Server.tablovericek();
                                         }
            
                                 } 
                             Server.jTextArea1.append(ThreadServer.threadler[this.ThreadGelen].getName()+" Durumu"+":"+ ThreadServer.threadler[this.ThreadGelen].getState()+"\n"); 
                             Client.close();
                             Server.jTextArea1.append("Writer Ayrıldı.İP Adresi: "+adres+"\n\n");
                       if((this.ThreadGelen)!=0){
                            Server.jTextArea1.append(ThreadServer.threadler[this.ThreadGelen-1].getName()+" Durumu"+":"+ ThreadServer.threadler[this.ThreadGelen-1].getState()+"\n\n"); 
                            
                          }
                           
                             break;
                             
                             
                     }
                 }
          
         
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
  
    
    
}
