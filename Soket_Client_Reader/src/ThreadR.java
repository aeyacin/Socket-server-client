
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

/**
 *
 * @author HALO73-FİL
 */
public class ThreadR extends Thread{
    
    
      Random rnd = new Random();
     long startTime;
 long endTime ; 
 long estimatedTime ; 
 double sonsüre ;
 
long beklemesüresi;
  Socket soket;
    DataInputStream al;
    DataOutputStream Gonder;
    String Mesaj;
    
    
    public ThreadR(){
        
        this.beklemesüresi=5000;
        
        
        
    }
    
    public void run(){
        
           try {
           
            soket= new Socket(ClientReader.jTextField1.getText(),Integer.parseInt(ClientReader.jTextField2.getText()));
            Gonder= new DataOutputStream(soket.getOutputStream());
             al= new DataInputStream(soket.getInputStream());
            Gonder.writeUTF("Reader");
            Gonder.writeUTF(System.getProperty("user.name"));
              
            ClientReader.jTextArea1.append("Sunucuyla Bağlantı Kuruldu\n");
                while( !ClientReader.durdurma){
                     startTime = System.nanoTime(); 
                     Mesaj=  al.readUTF() ;
        
                    ClientReader.jTextArea1.append("Veri Alındı");
                    ClientReader.tablovericek(Mesaj);
                     endTime = System.nanoTime(); 
  estimatedTime = endTime - startTime; // Geçen süreyi nanosaniye cinsinden elde ediyoruz
  sonsüre = (double)estimatedTime/1000000;      
        ClientReader.jTextArea1.append("Alma Süresi: "+sonsüre+"\n\n");
          }
                
                 ClientReader.jTextArea1.append("Veri Alımı Durduruldu");
                al.close();
            
                Gonder.close();
            soket.close();
            
           // jTextArea1.append("\n"+Gönderi+"\n");
        } catch (IOException ex) {
            Logger.getLogger(ClientReader.class.getName()).log(Level.SEVERE, null, ex);
             JOptionPane.showMessageDialog(null,"Sunucuya bağlanma hatası");
        }
        
    }
    
}
