package ProtocoloEnlace;

import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.Objects;

public class DataGrama { 
   private byte buffer[]=new byte[Constantes.buffersize+4]; 
   private int remotePort;              // Allow for 1 digit Ack and Seq 
   private String remoteAddress; 
   private DatagramSocket ds = null; 
   private String info;

   DataGrama(int localPort) 
   {       try { ds = new DatagramSocket(localPort);} 
            catch (Exception e) {System.out.println("Exception " + e 
                                                         + " port " + localPort ); } 
    }

  /* So code that used to say:

        String s;
        :
        :
        int len = 1024;
        byte b[] = new byte[len];
        s.getBytes(0, len, b, 0);

should be replaced by:

        String s;
        :
        :
        byte b[] = s.getBytes();
        int len = b.length;
*/
   
    public void send(String remoteAddress, int remotePort, String s){
        /*byte buffer[]=new byte[s.length()]; 
         s.getBytes(0, s.length(), buffer, 0); 
         */
        
        byte b[] = s.getBytes();
        int len = b.length;
        
         
         try { ds.send(new DatagramPacket(buffer, len, 
                InetAddress.getByName(remoteAddress), remotePort)); 
         } 
         catch (Exception e) { System.out.println("Exception " + e); }; 
   }
    public void start_receive(int timeout)  
        throws Exception { 
            ds.setSoTimeout(timeout); 
            info = ""; 
            DatagramPacket p =  new DatagramPacket(buffer, buffer.length); 
             ds.receive(p);  
             info = new String(p.getData(), 0, 0, p.getLength()); 
        }
    
    public String receive(){
            return info; 
    } 

}
