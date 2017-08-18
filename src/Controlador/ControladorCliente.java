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
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Marcelo
 */
public class ControladorCliente implements ActionListener{
    
    Clientes vistaClientes = new Clientes();
    ClienteDAO cd = new ClienteDAO();
    
    public ControladorCliente(Clientes vistaClientes){
        this.vistaClientes=vistaClientes;
        this.vistaClientes.agregarBtn.addActionListener(this);
        this.vistaClientes.eliminarBtn.addActionListener(this);
        this.vistaClientes.modificarBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vistaClientes.agregarBtn){  
            AltaCliente ac = new AltaCliente((Frame) SwingUtilities.getWindowAncestor(vistaClientes), true);
            ControladorAltaCliente cac = new ControladorAltaCliente((Frame) SwingUtilities.getWindowAncestor(vistaClientes), ac, cd);
            ac.setVisible(true);
    }
        if(e.getSource() == vistaClientes.eliminarBtn){
             int row = vistaClientes.cliente.getSelectedRow(); 
             if(row != -1){
             ImageIcon icon = new ImageIcon("src/Imagenes/Iconos/warning.png");   
             int reply = JOptionPane.showConfirmDialog(null, "Eliminar a "+vistaClientes.cliente.getModel().getValueAt(row, 0)+" "+""+" "+vistaClientes.cliente.getModel().getValueAt(row, 1)+"?",
                     "Advertencia",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
              if (reply == JOptionPane.YES_OPTION) {
              int dni = Integer.parseInt(vistaClientes.cliente.getModel().getValueAt(row, 2).toString());
              cd.eliminarCliente(dni);
                 } 
                  }
                 else{
                 JOptionPane.showMessageDialog(null, "Seleccione un cliente de la lista", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
  
             }
        }
    }
    
}
