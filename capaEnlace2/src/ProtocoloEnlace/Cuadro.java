package ProtocoloEnlace;

public class Cuadro { 
    public String info; 
    public int seq=0; 
    public int ack=0;
    private boolean timer = false; 
    private int timelimit=Constantes.timeout; 
    private DataGrama datagrama; 
    private int lostframes=0;

    Cuadro(int port) 
    {       datagrama = new DataGrama(port); 
    }

    public String from_physical_layer() 
    {       String s = datagrama.receive();

            int index; 
            index = s.indexOf(" "); 
            seq = (new Integer(s.substring(0,index))).intValue(); 
            s = s.substring(index+1); 
            index = s.indexOf(" "); 
            ack = (new Integer(s.substring(0,index))).intValue(); 
            info = s.substring(index+1); 
            return info; 
    } 
    public void to_physical_layer( 
    	      String remoteAddress, int remotePort) 
    	  {       datagrama.send(remoteAddress, remotePort, 
    	                 "" + seq + " " + ack + " " + info); 
    	   }
    	   public int wait_for_event() 
    	   {  int timelimit = 0; 
    	       if(timer) timelimit = this.timelimit; 
    	       try { datagrama.start_receive(timelimit); } 
    	       catch(Exception e) { return Protocolo.timeout; } 
    	       if(Constantes.lostframes > 0 &&  
    	               ++lostframes % Constantes.lostframes == 0) 
    	              return Protocolo.timeout; 
    	        return Protocolo.frame_arrival; 
    	  }

    	  public void start_timer(int k) 
    	  {  timer = true; 
    	   }

    	   public void stop_timer(int k) 
    	   {   timer = false; 
    	    } 
    	}