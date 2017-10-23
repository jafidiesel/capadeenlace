
package capadeenlace;
import java.io.*;
import gnu.io.*;
import java.util.zip.CRC32;

public class capaEnlace{
    
    SerialPort elPuerto;
    long tiempo;
    private char banderaInicio ='$';
    private char banderaFin= '$';
    private final char datos = 48;	//caracter 0
    private final char ack = 49;	//caracter 1
    private final char nak = 50;	//caracter 2
    OutputStream salida;
    Emisor capafisica;
    private char ultimoOrden=1;//para no descartar la primer trama que siempre viene con orden 0
    Trama tramaGuardada;

      //variables de control de eventos
    public boolean llego_trama_conf = true;
    public boolean ocurrio_timeout = false;
   

public void setSerialPort(SerialPort puerto){
elPuerto = puerto;
}
public boolean EnviarTrama(Trama trama){
    
    if(llego_trama_conf == true){
        if(trama.getTipo()=='0'){ // ya que es la unica trama que necesita de ack para volver a mandar es la de datos
            llego_trama_conf = false;
        }
        char[]arreglo=trama.getTramaArmada().toCharArray();
        tramaGuardada=trama;
        try {
            salida = elPuerto.getOutputStream();
            for (int i = 0; i < arreglo.length; i++)  {
                salida.write((int)arreglo[i]);

            }
            
        } catch (Exception ex) {
            System.out.println("Error al enviar: "+ex.getMessage());
            return false;
        }
        return true;
           }
    return false;
    
    
}

public Trama ReconstruirTrama(String cadenaRecibida){
    char []datosTrama = new char[cadenaRecibida.length()];
    datosTrama = cadenaRecibida.toCharArray();
    Trama t = new Trama();
    t.setBandera_inicio(datosTrama[0]);
    t.setOrden(datosTrama[1]);
    t.setTipo(datosTrama[2]);
    char [] c = new char[26];

    for (int i = 3, j=0; i <= ((datosTrama.length)-12); i++, j++) { // el 11 sale de 10 caracteres de crc y uno de la fin de bandera
        c[j]= datosTrama[i];

    }
    t.setDatos(c);
    String crc2 = "";
    char[] crc = new char[10];
    for (int i = (datosTrama.length-11),j=0 ; i < (datosTrama.length-1); i++,j++) {
        crc[j] = datosTrama[i];
        crc2 = crc2+datosTrama[i];
    }
    System.out.println(crc2);
    t.setCrc(new Long(Long.parseLong(crc2)));
    t.setBandera_fin(datosTrama[datosTrama.length-1]);
    return t;
}

public void recibir(Receptor ca,Trama laTrama){
    
    if(laTrama.getTipo()=='0'){
        if(laTrama.getOrden() == ultimoOrden){//se perdio el ack descarto la trma reenvio ack
            System.out.println("ultimoorden");
            mandarAck();
       
        }else{    
            long crcRecibido = calcularCRC(String.valueOf(laTrama.getDatos())); // calculo nuevamente el CRC de los datos recibidos
            
            System.out.println(crcRecibido);
            System.out.println(laTrama.getCrc());
        

            if(crcRecibido != laTrama.getCrc()){ // en caso de recibir la trama con errores, crc diferentes
                System.out.println("Error en crc");
                mandarNak();
            }else{ // esta todo ok entonces muestro y mando ack
               mandarAck(); // Mando el ask de confirmacion
               ca.setDetalleDeRecepcion("Mensaje recibido: "+filtroDatos(String.valueOf(laTrama.getDatos()))); // mando los datos sin los asteriscos de relleno
               
            }

        }
    } else if(laTrama.getTipo()=='1'){ // trama tipo ACK
        ca.setDetalleDeRecepcion("Mensaje recibido: "+filtroDatos(String.valueOf(laTrama.getDatos()))); // muestro la confirmacion de ask
        llego_trama_conf = true;
    } else {
       // la trama se envio con errores por lo que se vuelve a enviar tipo NAK
       ca.setDetalleDeRecepcion("Mensaje recibido: "+filtroDatos(String.valueOf(laTrama.getDatos()))); // muestro la confirmacion de que llego con error la trama
       System.out.println(tramaGuardada.getTipo());
       EnviarTrama(tramaGuardada);
    }

}

public void recibir(Emisor ca,Trama laTrama){ 
    
    if(laTrama.getTipo()=='0'){
        if(laTrama.getOrden() == ultimoOrden){//se perdio el ack descarto la trma reenvio ack
            System.out.println("ultimoorden");
            mandarAck();
       
        }else{    
            long crcRecibido = calcularCRC(String.valueOf(laTrama.getDatos())); // calculo nuevamente el CRC de los datos recibidos
            System.out.println(crcRecibido);
            System.out.println(laTrama.getCrc());
        

            if(crcRecibido != laTrama.getCrc()){ // en caso de recibir la trama con errores, crc diferentes
                System.out.println("Error en crc");
                mandarNak();
            }else{ // esta todo ok entonces muestro y mando ack
               mandarAck(); // Mando el ask de confirmacion
               ca.setDetalleDeEnvio(filtroDatos(String.valueOf(laTrama.getDatos()))); // mando los datos sin los asteriscos de relleno
               
            }

        }
    } else if(laTrama.getTipo()=='1'){ // trama tipo ACK
        ca.setDetalleDeEnvio(filtroDatos(String.valueOf(laTrama.getDatos()))); // muestro la confirmacion de ask
        llego_trama_conf = true;
    } else {
       // la trama se envio con errores por lo que se vuelve a enviar tipo NAK
       ca.setDetalleDeEnvio(filtroDatos(String.valueOf(laTrama.getDatos()))); // muestro la confirmacion de que llego con error la trama
       
       System.out.println(tramaGuardada.getTipo());
       EnviarTrama(tramaGuardada);
    }

}
public void mandarAck(){
      Trama tram = new Trama();
      tram =  armarTrama('1',"Confirmación de Trama");
      EnviarTrama(tram);
     
}
public void mandarNak(){
    Trama tram = new Trama();
    tram =  armarTrama('2', "Trama con Error");
    EnviarTrama(tram);
    
}
public long calcularCRC(String cadena){

       final CRC32 crc = new CRC32();
       crc.update(cadena.getBytes());
       if(crc.getValue()>=1000000000) //para que sea siempre 10 digitos
       return crc.getValue();
       else{
       return crc.getValue()+1000000000; // si no llega a los 10 digitos, sumo esto para llegar
    }
}
public String filtroDatos(String datos){ // le saco los asteriscos de relleno
    String fin="";
    for (int i = 0; i < datos.length(); i++) {
        if(datos.charAt(i)!='*')
        fin=fin+datos.charAt(i);
            }
    return fin;

}
public Trama armarTrama(char tipo, String cadena){
        
        String cadenaRellena = RellenarDatos(cadena);
        Trama t = new Trama();
        long crc=calcularCRC(cadenaRellena);
        t.setCrc(crc);
        t.setBandera_inicio(banderaInicio);
        t.setTipo(tipo);
        t.setDatos(cadenaRellena.toCharArray());
        t.setBandera_fin(banderaFin);
        System.out.println(t.getTramaArmada());

        return t;

    }
/*
 * le pusimos 26 porque con los bits de rellenos y el codigo crc, nos quedan 26 bytes para datos
 * entonces cada 26 bytes vamos a tener que cortar el mensaje en tramas
 */
   public String RellenarDatos(String cadena){
       int limite = 26 - cadena.length();
       String cad=cadena;
       for(int i = 0 ; i < limite;i++){
           cad=cad+"*"; // * es el caracter de relleno para mantener todas las trama con 40 caracteres
       }
       return cad;
   }

    void setTiempo(long tiempo) {
        this.tiempo= tiempo;
    }
    long getTiempo() {
        return tiempo;
    }
   
}
