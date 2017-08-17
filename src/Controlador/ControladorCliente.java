/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ClienteDAO;
import Vista.Panels.AltaCliente;
import Vista.Panels.Clientes;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author Marcelo
 */
public class ControladorCliente implements ActionListener{
    
    Clientes vistaClientes = new Clientes();
    
    public ControladorCliente(Clientes vistaClientes){
        this.vistaClientes=vistaClientes;
        this.vistaClientes.agregarBtn.addActionListener(this);
        this.vistaClientes.eliminarBtn.addActionListener(this);
        this.vistaClientes.modificarBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vistaClientes.agregarBtn){  
            ClienteDAO cd = new ClienteDAO();
            AltaCliente ac = new AltaCliente((Frame) SwingUtilities.getWindowAncestor(vistaClientes), true);
            ControladorAltaCliente cac = new ControladorAltaCliente((Frame) SwingUtilities.getWindowAncestor(vistaClientes), ac, cd);
            ac.setVisible(true);
    }}
    
}
