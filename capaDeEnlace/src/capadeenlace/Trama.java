package capadeenlace;

public class Trama {
 private char tipo;
 private char orden=(char)48; // para ver si es una trama reenviada en el caso de perder un ask


 private char ack;
 private long crc;
 private char bandera_fin;
 char []datos=new char [40];

 private char bandera_inicio;

    public String getTramaArmada(){
        return String.valueOf(bandera_inicio)+String.valueOf(orden)+String.valueOf(tipo)+String.valueOf(datos)+String.valueOf(crc)+String.valueOf(bandera_fin);
    }

    public char getAck() {
        return ack;
    }

    public void setAck(char ack) {
        this.ack = ack;
    }

    public char getBandera_fin() {
        return bandera_fin;
    }

    public void setBandera_fin(char bandera_fin) {
        this.bandera_fin = bandera_fin;
    }

    public char getBandera_inicio() {
        return bandera_inicio;
    }

    public void setBandera_inicio(char bandera_inicio) {
        this.bandera_inicio = bandera_inicio;
    }
       public char getOrden() {
        return orden;
    }

    public void setOrden(char orden) {
        this.orden = orden;
    }

    public long getCrc() {
        return crc;
    }

    public void setCrc(long crc) {
        this.crc = crc;
    }



    public char[] getDatos() {
        return datos;
    }

    public void setDatos(char[] datos) {
        this.datos = datos;
    }

 

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

}
