/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import Controlador.ControladorCliente;
import Vista.Frame.Ventana;
import Vista.Panels.Clientes;

/**
 *
 * @author Marcelo
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Ventana ventana = new Ventana();
        //Clientes vistaClientes = new Clientes();
        //ControladorCliente cc = new ControladorCliente(vistaClientes);
       ventana.setLocationRelativeTo(null);
       ventana.setVisible(true);
    }
    
}
