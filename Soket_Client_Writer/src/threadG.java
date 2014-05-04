
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


public class threadG extends Thread implements Runnable{
    
    Random rnd = new Random();
     long startTime;
 long endTime ; 
 long estimatedTime ; 
 double sonsüre ;

long beklemesüresi;
  Socket soket;
    DataInputStream al;
    DataOutputStream Gonder;
    
    public threadG(){
        this.beklemesüresi=2000;
    }
    
    @Override
    public void run(){
         try {
            soket = new Socket(ClientWriter.jTextField1.getText(),Integer.parseInt(ClientWriter.jTextField2.getText()));
               Gonder = new DataOutputStream(soket.getOutputStream());
             Gonder.writeUTF("Writer");
              while(ClientWriter.a){
                 
              startTime = System.nanoTime(); 
               
              
            
               ClientWriter.jTextArea1.append("Sunucuyla bağlantı kuruldu\n");
                Gonder.writeUTF(ClientWriter.jLabel10.getText()+";"+ClientWriter.jLabel11.getText()+";"+ClientWriter.sistemTarihiniGetir("yyyy/MM/dd H:mm:ss")+";"+ClientWriter.ramrandom()+";"+cpurandom()+";"+ClientWriter.DiskInfo()+";");
                
            //   ClientWriter.jTextArea1.append("Veri Gönderildi.\n ");
           endTime = System.nanoTime(); 
  estimatedTime = endTime - startTime; // Geçen süreyi nanosaniye cinsinden elde ediyoruz
  sonsüre = (double)estimatedTime/1000000;      
        ClientWriter.jTextArea1.append("Gönderilme Süresi: "+sonsüre+"\n\n");
          threadG.sleep(beklemesüresi);
              }
             Gonder.close(); 
            soket.close();
            
// TODO add your handling code here:
        } catch (IOException ex) {
            Logger.getLogger(ClientWriter.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null,"Sunucuya bağlanma hatası");
        } catch (InterruptedException ex) {
             Logger.getLogger(threadG.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
      public   int cpurandom(){
        
        return (int) (rnd.nextDouble()*100);
        
    }
        
        
    }
    
   
         
     
    

