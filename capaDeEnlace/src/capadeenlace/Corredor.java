/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package capadeenlace;

/**
 *
 * @author Nazrala
 */
public class Corredor implements Runnable {

    Thread hilo = new Thread(this);

     public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {}
    }

}
