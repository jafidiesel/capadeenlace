package ProtocoloEnlace;

public class Frame { 
    public String info; 
    public int seq=0; 
    public int ack=0;
    private boolean timer = false; 
    private int timelimit=Constants.timeout; 
    private DataGram datagram; 
    private int lostframes=0;

    Frame(int port) 
    {       datagram = new DataGram(port); 
    }

    public String from_physical_layer() 
    {       String s = datagram.receive();

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
    	  {       datagram.send(remoteAddress, remotePort, 
    	                 "" + seq + " " + ack + " " + info); 
    	   }
    	   public int wait_for_event() 
    	   {  int timelimit = 0; 
    	       if(timer) timelimit = this.timelimit; 
    	       try { datagram.start_receive(timelimit); } 
    	       catch(Exception e) { return Protocol.timeout; } 
    	       if(Constants.lostframes > 0 &&  
    	               ++lostframes % Constants.lostframes == 0) 
    	              return Protocol.timeout; 
    	        return Protocol.frame_arrival; 
    	  }

    	  public void start_timer(int k) 
    	  {  timer = true; 
    	   }

    	   public void stop_timer(int k) 
    	   {   timer = false; 
    	    } 
    	}