/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import Controlador.ControladorLogin;
import Vista.Dialogs.Login;
import Vista.Frame.Ventana;
import javax.swing.JFrame;


        
public class Main {

 private static ControladorLogin cl; 
    
    public static void main(String[] args) {
        Ventana ventana = new Ventana();      
        ventana.setExtendedState(JFrame.MAXIMIZED_BOTH); 
       // ventana.setVisible(true);
    }
    
}
