package ProtocoloEnlace;

import java.net.*;

public class DataGram { 
   private byte buffer[]=new byte[Constants.buffersize+4]; 
   private int remotePort;              // Allow for 1 digit Ack and Seq 
   private String remoteAddress; 
   private DatagramSocket ds = null; 
   private String info;

   DataGram(int localPort) 
   {       try { ds = new DatagramSocket(localPort);} 
            catch (Exception e) {System.out.println("Exception " + e 
                                                         + " port " + localPort ); } 
    }

    public void send(String remoteAddress, int remotePort, String s)  
    {   byte buffer[]=new byte[s.length()]; 
         s.getBytes(0, s.length(), buffer, 0); 
         try { ds.send(new DatagramPacket(buffer, s.length(), 
                InetAddress.getByName(remoteAddress), remotePort)); 
         } 
         catch (Exception e) { System.out.println("Exception " + e); }; 
   }
    public void start_receive(int timeout)  
    throws Exception 
{ ds.setSoTimeout(timeout); 
info = ""; 
DatagramPacket p =  
new DatagramPacket(buffer, buffer.length); 
ds.receive(p);  
info = new String(p.getData(), 0, 0, p.getLength()); 
} 
public String receive()  
{       return info; 
} 
} 