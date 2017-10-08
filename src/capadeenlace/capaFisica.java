/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * capaFisica.java
 *
 * Created on 09-oct-2012, 10:09:00
 */

package capadeenlace;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.comm.*;
import java.util.*;
import javax.swing.JTextField;
/**
 *
 * @author Nazrala
 */
public class capaFisica extends javax.swing.JFrame implements Runnable,SerialPortEventListener{

Integer velocidad;
Integer bitParada;
String nombrePuerto;
Integer paridad;
Integer longTrama;
public SerialPort puerto;
Enumeration listaPuertos;
public CommPortIdentifier idPuerto;
int estado;
capaEnlace capaenlace = new capaEnlace();
Thread hilo;
OutputStream salida;
InputStream entrada;
static String tramaparamostrar;
   


    /** Creates new form capaFisica */
     private void iniciarElementos(){
      
        String port;
        listaPuertos = CommPortIdentifier.getPortIdentifiers();

        while (listaPuertos.hasMoreElements()) {
            idPuerto = (CommPortIdentifier) listaPuertos.nextElement(); //get next port to check
            if (idPuerto.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                    port = idPuerto.getName();
                    comboPuerto.addItem(port);
            }
         }
        
   }
    public capaFisica() {

        initComponents();
        iniciarElementos();

    }
    public void serialEvent(SerialPortEvent event){
       
        switch (event.getEventType()) {
        case SerialPortEvent.BI:
        case SerialPortEvent.OE:
        case SerialPortEvent.FE:
        case SerialPortEvent.PE:
        case SerialPortEvent.CD:
        case SerialPortEvent.CTS:
        case SerialPortEvent.DSR:
        case SerialPortEvent.RI:
        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
            break;
        case SerialPortEvent.DATA_AVAILABLE:    //analizamos la entrada de datos

            StringBuilder readBuffer = new StringBuilder();
            int c;
            try {
                System.out.println("Recibiendo.....houston");
                //leemos los datos del puerto
                int i=0;
                while (i < 40)  { // las tramas son de 40 caracteres
                    i++;
                    c=entrada.read();
                    readBuffer.append((char)c);
                   
                }
                String recibido = readBuffer.toString();

             System.out.println(recibido);
               
               // pasamos los datos a la capa de enlace
            capaenlace.recibir(this,capaenlace.ReconstruirTrama(recibido));



                //cerramos el flujo de entrada
                
                entrada.close();
            } catch (IOException e) {
                System.out.println("Error al recibir: "+e.getMessage());

            }

            break;
        }
    }

    public String getTextoRecibir() {
        return textoRecibir.getText();
    }

    public void setTextoRecibir(String textoRecibir) {
        this.textoRecibir.setText(textoRecibir);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane2 = new javax.swing.JTabbedPane();
        configuracion = new javax.swing.JPanel();
        comboPuerto = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        comboVelocidad = new javax.swing.JComboBox();
        abrir = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        comboParidad = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tiempoAcuse = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        comboParada = new javax.swing.JComboBox();
        comboLong = new javax.swing.JComboBox();
        mensaje = new javax.swing.JPanel();
        textoEnviar = new javax.swing.JTextField();
        textoRecibir = new javax.swing.JTextField();
        enviar = new javax.swing.JButton();
        detalle = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        comboPuerto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboPuertoActionPerformed(evt);
            }
        });

        jLabel1.setText("Puerto:");

        jLabel2.setText("Velocidad:");

        comboVelocidad.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1200", "2400", "4800", "9600", "19200", "38400", "57600", "115200", "460800" }));
        comboVelocidad.setSelectedIndex(3);

        abrir.setText("Abrir Puerto");
        abrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abrirActionPerformed(evt);
            }
        });

        jLabel3.setText("Pridad:");

        comboParidad.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Par", "Impar" }));
        comboParidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboParidadActionPerformed(evt);
            }
        });

        jLabel4.setText("Bits por tramas: ");

        jLabel5.setText("Timpo de espera de acuse (ms):");

        tiempoAcuse.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tiempoAcuse.setText("800");

        jLabel6.setText("Bits de parada:");

        comboParada.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2" }));
        comboParada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboParadaActionPerformed(evt);
            }
        });

        comboLong.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "5", "6", "7", "8" }));
        comboLong.setSelectedIndex(3);

        javax.swing.GroupLayout configuracionLayout = new javax.swing.GroupLayout(configuracion);
        configuracion.setLayout(configuracionLayout);
        configuracionLayout.setHorizontalGroup(
            configuracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(configuracionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(configuracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(configuracionLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tiempoAcuse, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(configuracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, configuracionLayout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(comboPuerto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(comboVelocidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(abrir))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, configuracionLayout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(comboParidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(comboLong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(comboParada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        configuracionLayout.setVerticalGroup(
            configuracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(configuracionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(configuracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(comboPuerto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(comboVelocidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(abrir))
                .addGap(18, 18, 18)
                .addGroup(configuracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(comboParidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(comboParada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboLong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(configuracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tiempoAcuse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(256, 256, 256))
        );

        jTabbedPane2.addTab("Configuración", configuracion);

        textoEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoEnviarActionPerformed(evt);
            }
        });

        textoRecibir.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        textoRecibir.setAlignmentX(0.0F);
        textoRecibir.setAlignmentY(0.0F);
        textoRecibir.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textoRecibir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoRecibirActionPerformed(evt);
            }
        });

        enviar.setText("Enviar");
        enviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enviarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mensajeLayout = new javax.swing.GroupLayout(mensaje);
        mensaje.setLayout(mensajeLayout);
        mensajeLayout.setHorizontalGroup(
            mensajeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mensajeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mensajeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mensajeLayout.createSequentialGroup()
                        .addComponent(textoRecibir, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mensajeLayout.createSequentialGroup()
                        .addComponent(textoEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(enviar)
                        .addGap(12, 12, 12))))
        );
        mensajeLayout.setVerticalGroup(
            mensajeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mensajeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mensajeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textoEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(enviar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textoRecibir, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Mensaje", mensaje);

        javax.swing.GroupLayout detalleLayout = new javax.swing.GroupLayout(detalle);
        detalle.setLayout(detalleLayout);
        detalleLayout.setHorizontalGroup(
            detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 396, Short.MAX_VALUE)
        );
        detalleLayout.setVerticalGroup(
            detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 354, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Detalle", detalle);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comboPuertoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPuertoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboPuertoActionPerformed

    private void abrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abrirActionPerformed
        try {
            nombrePuerto=comboPuerto.getSelectedItem().toString();
            idPuerto=CommPortIdentifier.getPortIdentifier(nombrePuerto);

         

          if(!idPuerto.isCurrentlyOwned()){
        
          
          velocidad=Integer.parseInt(comboVelocidad.getSelectedItem().toString());
          bitParada=Integer.parseInt(comboParada.getSelectedItem().toString());
          if(comboParidad.getSelectedItem().toString().equals("Par")){paridad=2;}else{paridad=1;}
          longTrama=Integer.parseInt(comboLong.getSelectedItem().toString());
          puerto = (SerialPort) idPuerto.open("Capa de Enlace", 2000);
          puerto.setSerialPortParams(velocidad, longTrama, bitParada, paridad);
           entrada = puerto.getInputStream();
           puerto.addEventListener(this);
           puerto.notifyOnDataAvailable(true);// para que nos notifique sobre eventos

         hilo = new Thread(this); //corro un hilo para recibir datos
         hilo.start();
      

          System.out.println("Abri el puerto: "+puerto.getName());

              }
        else{
                puerto.close();
                System.out.println("Cerre el puerto: "+puerto.getName());
        }
        }
        catch (Exception ex) {
            System.out.println("Error al abrir el puerto: "+ex.getMessage());
        }
        capaenlace.setSerialPort(puerto);
        
    }//GEN-LAST:event_abrirActionPerformed

    private void comboParidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboParidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboParidadActionPerformed

    private void comboParadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboParadaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboParadaActionPerformed

    private void textoEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoEnviarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textoEnviarActionPerformed

    private void enviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enviarActionPerformed
//        Trama t=new Trama();
//        t=capaenlace.armarTrama((char)48, textoEnviar.getText());
//
//     char [] arreglo = t.getTotal().toCharArray();
//            try {
//                salida = puerto.getOutputStream();
//                 for (int i = 0; i < arreglo.length; i++)  {
//                salida.write((int)arreglo[i]); // se envia la trama
//                     System.out.println(arreglo[i]);}
//            } catch (IOException ex) {
//                Logger.getLogger(capaFisica.class.getName()).log(Level.SEVERE, null, ex);
//            }



capaenlace.EnviarTrama(capaenlace.armarTrama('0', textoEnviar.getText()));

    }//GEN-LAST:event_enviarActionPerformed

    private void textoRecibirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoRecibirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textoRecibirActionPerformed
  public SerialPort getPuerto(){
return puerto;
}
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new capaFisica().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton abrir;
    private javax.swing.JComboBox comboLong;
    private javax.swing.JComboBox comboParada;
    private javax.swing.JComboBox comboParidad;
    private javax.swing.JComboBox comboPuerto;
    private javax.swing.JComboBox comboVelocidad;
    private javax.swing.JPanel configuracion;
    private javax.swing.JPanel detalle;
    private javax.swing.JButton enviar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JPanel mensaje;
    private javax.swing.JTextField textoEnviar;
    private javax.swing.JTextField textoRecibir;
    private javax.swing.JTextField tiempoAcuse;
    // End of variables declaration//GEN-END:variables

    public void run() {
      try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {e.printStackTrace();}
    }

}
