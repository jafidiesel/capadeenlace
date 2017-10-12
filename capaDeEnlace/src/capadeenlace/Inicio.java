package capadeenlace;

/**
 *
 * @author Chechi
 */
public class Inicio {
    
   public static void main(String arg[]){
       Emisor emisor = new Emisor();
       emisor.setVisible(true);
       emisor.run();
       
       Receptor receptor = new Receptor();
       receptor.setVisible(true);
       receptor.run();
   }
 }
