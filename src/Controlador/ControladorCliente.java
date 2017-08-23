/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ClienteDAO;
import Vista.Frame.Ventana;
import Vista.Panels.AltaCliente;
import Vista.Panels.Clientes;
import Vista.Panels.DetallePago;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcelo
 */
public class ControladorCliente implements ActionListener, MouseListener{
    
    Clientes vistaClientes = new Clientes();
    ClienteDAO cd = new ClienteDAO();
    private Object [] clientes;
    
    public ControladorCliente(Clientes vistaClientes){
        this.vistaClientes=vistaClientes;
        this.vistaClientes.agregarBtn.addActionListener(this);
        this.vistaClientes.eliminarBtn.addActionListener(this);
        this.vistaClientes.agregarBtn.addActionListener(this);
        this.vistaClientes.detalleBtn.addActionListener(this);
        this.vistaClientes.tablaCliente.addMouseListener(this);
        vistaClientes.datosCliente.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.BLACK), "Datos cliente"));
        vistaClientes.datosReferencia.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.BLACK), "Datos referencia"));
    }

    public ControladorCliente() {
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vistaClientes.agregarBtn){  
            AltaCliente ac = new AltaCliente((Frame) SwingUtilities.getWindowAncestor(vistaClientes), true);
            ControladorAltaCliente cac = new ControladorAltaCliente((Frame) SwingUtilities.getWindowAncestor(vistaClientes), ac, cd, vistaClientes.tablaCliente);
            ac.setVisible(true);
         }
        if(e.getSource() == vistaClientes.eliminarBtn){
             int row = vistaClientes.tablaCliente.getSelectedRow(); 
             if(row != -1){
             ImageIcon icon = new ImageIcon("src/Imagenes/Iconos/warning.png");   
             int reply = JOptionPane.showConfirmDialog(null, "Eliminar a "+vistaClientes.tablaCliente.getModel().getValueAt(row, 0)+" "+""+" "+vistaClientes.tablaCliente.getModel().getValueAt(row, 1)+"?",
                     "Advertencia",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
              if (reply == JOptionPane.YES_OPTION) {
              int dni = Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(row, 2).toString());
              cd.eliminarCliente(dni);
                  llenarTabla(vistaClientes.tablaCliente);
                 } 
                  }
                 else{
                 JOptionPane.showMessageDialog(null, "Seleccione un cliente de la lista", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
  
             }
        }
        if(e.getSource() == vistaClientes.detalleBtn){
            
    DetallePago vistaDetallePago = new DetallePago();
    ControladorDetallePago cdp = new ControladorDetallePago(vistaDetallePago);
           int row = vistaClientes.tablaCliente.getSelectedRow();
           cdp.llenarTabla(vistaDetallePago.detallePagoTabla, vistaClientes.tablaCliente.getModel().getValueAt(row, 8).toString());
           
         }
    }
    
    public void llenarTabla(JTable jTable){
        ResultSet rs = cd.clientesPorLotes();
        DefaultTableModel model = (DefaultTableModel) jTable.getModel();
        model.setRowCount(0);
        try {
            while(rs.next()){
                String dni = rs.getString(1);
                String apellidos = rs.getString(2);
                String nombres = rs.getString(3);
                String barrio = rs.getString(4);                
                String calle = rs.getString(5);
                String numero = rs.getString(6);                
                String telefono = rs.getString(7);
                String trabajo = rs.getString(8);
                String idControl = rs.getString(9);
                String barrio_prop = rs.getString(10);
                String manzana_prop = rs.getString(11);
                String parcela_prop = rs.getString(12);
                
                clientes = new Object[] {apellidos, nombres, dni, telefono, barrio, calle, numero, trabajo, idControl, barrio_prop, manzana_prop, parcela_prop};
                            
                model.addRow(clientes);   
            }
                
                vistaClientes.tablaCliente.getColumnModel().getColumn(4).setMinWidth(0);
                vistaClientes.tablaCliente.getColumnModel().getColumn(4).setMaxWidth(0);
                vistaClientes.tablaCliente.getColumnModel().getColumn(4).setWidth(0);
                vistaClientes.tablaCliente.getColumnModel().getColumn(5).setMinWidth(0);
                vistaClientes.tablaCliente.getColumnModel().getColumn(5).setMaxWidth(0);
                vistaClientes.tablaCliente.getColumnModel().getColumn(5).setWidth(0);
                vistaClientes.tablaCliente.getColumnModel().getColumn(6).setMinWidth(0);
                vistaClientes.tablaCliente.getColumnModel().getColumn(6).setMaxWidth(0);
                vistaClientes.tablaCliente.getColumnModel().getColumn(6).setWidth(0);
                vistaClientes.tablaCliente.getColumnModel().getColumn(7).setMinWidth(0);
                vistaClientes.tablaCliente.getColumnModel().getColumn(7).setMaxWidth(0);
                vistaClientes.tablaCliente.getColumnModel().getColumn(7).setWidth(0);
                vistaClientes.tablaCliente.getColumnModel().getColumn(8).setMinWidth(0);
                vistaClientes.tablaCliente.getColumnModel().getColumn(8).setMaxWidth(0);
                vistaClientes.tablaCliente.getColumnModel().getColumn(8).setWidth(0);
        } catch (Exception e) {
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = vistaClientes.tablaCliente.getSelectedRow();       
        vistaClientes.barrio.setText(vistaClientes.tablaCliente.getModel().getValueAt(row, 4).toString());
        vistaClientes.calle.setText(vistaClientes.tablaCliente.getModel().getValueAt(row, 5).toString());
        vistaClientes.numero.setText(vistaClientes.tablaCliente.getModel().getValueAt(row, 6).toString());
        vistaClientes.trabajo.setText(vistaClientes.tablaCliente.getModel().getValueAt(row, 7).toString());   
    }

    @Override
    public void mousePressed(MouseEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
