package ProtocoloEnlace;

public class Protocolo { 
    public final static int frame_arrival = 0; 
    public final static int chksum_err = 1; 
    public final static int timeout = 2;
    public static void to_network_layer(String s) 
    {       System.out.print(s); 
    }

    public static String from_network_layer() 
    {      byte buffer[] = new byte[Constantes.buffersize]; 
            try { System.in.read(buffer); } 
            catch (Exception e) {}; 
            String s = new String(buffer,0); 
            return s; 
    } 
}
