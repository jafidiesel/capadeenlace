package ProtocoloEnlace;

public class sender3 extends Protocol { 
    public static void main(String args[]) throws Exception 
    {  int next_frame_to_send; 
        Frame r = new Frame(777); 
        Frame s = new Frame(666); 
        String buffer; 
        int event;
        next_frame_to_send = 0; 
        buffer = from_network_layer(); 
        while (true) 
        {       s.info = buffer; 
                 s.seq = next_frame_to_send; 
                 s.to_physical_layer("localhost", 888); 
                 r.start_timer(s.seq); 
                 event = r.wait_for_event(); 
                 if (event == frame_arrival) 
                {   r.from_physical_layer(); 
                     if (r.ack == next_frame_to_send) 
                    {   buffer = from_network_layer(); 
                         next_frame_to_send = 
                                   (next_frame_to_send + 1) % 2; 
                     } 
                } 
        } 
    } 
}