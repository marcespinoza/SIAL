/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ClienteDAO;
import Vista.Dialogs.AsignarPropiedad;
import Vista.Panels.Clientes;
import Vista.Panels.DetalleCuota;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Marcelo
 */
public class ControladorCliente implements ActionListener, MouseListener{
    
    Clientes vistaClientes = new Clientes();
    ClienteDAO cd = new ClienteDAO();
    private Object [] clientes;
    public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

    
    public ControladorCliente(Clientes vistaClientes){
        this.vistaClientes=vistaClientes;
        this.vistaClientes.agregarBtn.addActionListener(this);
        this.vistaClientes.eliminarBtn.addActionListener(this);
        this.vistaClientes.agregarBtn.addActionListener(this);
        this.vistaClientes.detalleBtn.addActionListener(this);
        this.vistaClientes.asignarBtn.addActionListener(this);
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
            //AltaCliente ac = new AltaCliente((Frame) SwingUtilities.getWindowAncestor(vistaClientes), true);
            ControladorAltaCliente cac = new ControladorAltaCliente((Frame) SwingUtilities.getWindowAncestor(vistaClientes), cd, vistaClientes.tablaCliente);
            //ac.setVisible(true);
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
              llenarTabla();
                 } 
                  }
                 else{
                 JOptionPane.showMessageDialog(null, "Seleccione un cliente de la lista", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
  
             }
        }
        if(e.getSource() == vistaClientes.detalleBtn){  
           int row = vistaClientes.tablaCliente.getSelectedRow();
           if(row != -1){
               if(vistaClientes.tablaCliente.getValueAt(row, 8) != null){
           DetalleCuota vistaDetallePago = new DetalleCuota();
           ControladorDetalleCuota cdp = new ControladorDetalleCuota(vistaDetallePago, vistaClientes.tablaCliente.getModel().getValueAt(row, 0).toString(),vistaClientes.tablaCliente.getModel().getValueAt(row, 1).toString(), Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(row, 8).toString()));
           cdp.llenarTabla(vistaDetallePago.tablaDetallePago, vistaClientes.tablaCliente.getModel().getValueAt(row, 8).toString());           
               }else{
                  JOptionPane.showMessageDialog(null, "Debe asignar una propiedad para ver los detalles", "Atención", JOptionPane.INFORMATION_MESSAGE, null); 
               }}else{
               JOptionPane.showMessageDialog(null, "Seleccione un cliente de la lista", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
           }           
           }
        
        if (e.getSource() == vistaClientes.asignarBtn) {
           int row = vistaClientes.tablaCliente.getSelectedRow();
           if(row != -1){
               if(vistaClientes.tablaCliente.getModel().getValueAt(row, 8)== null){
                   AsignarPropiedad ap = new AsignarPropiedad((Frame) SwingUtilities.getWindowAncestor(vistaClientes), true);
                   ControladorAsignacionPropiedad cap = new ControladorAsignacionPropiedad((Frame) SwingUtilities.getWindowAncestor(vistaClientes), ap, Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(row, 2).toString()));
                   ap.setVisible(true);
                }else{
                   JOptionPane.showMessageDialog(null, "Cliente con propiedad ya asignada", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
               }
           }else{
               JOptionPane.showMessageDialog(null, "Seleccione un cliente de la lista", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
           }  
        }
    }
    
    public void llenarTabla(){
        ResultSet rs = cd.clientesPorLotes();
        DefaultTableModel model = (DefaultTableModel) vistaClientes.tablaCliente.getModel();
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
                String precio = rs.getString(10);
                String gastos = rs.getString(11);
                String bolsa_cemento = rs.getString(12);
                String barrio_prop = rs.getString(13);
                String manzana_prop = rs.getString(14);
                String parcela_prop = rs.getString(15);                
                clientes = new Object[] {apellidos, nombres, dni, telefono, barrio, calle, numero, trabajo, idControl, precio, gastos, bolsa_cemento, barrio_prop, manzana_prop, parcela_prop};
                model.addRow(clientes);   
            }
                pintarFila();
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
                vistaClientes.tablaCliente.getColumnModel().getColumn(9).setMinWidth(0);
                vistaClientes.tablaCliente.getColumnModel().getColumn(9).setMaxWidth(0);
                vistaClientes.tablaCliente.getColumnModel().getColumn(9).setWidth(0);
                vistaClientes.tablaCliente.getColumnModel().getColumn(10).setMinWidth(0);
                vistaClientes.tablaCliente.getColumnModel().getColumn(10).setMaxWidth(0);
                vistaClientes.tablaCliente.getColumnModel().getColumn(10).setWidth(0);
                vistaClientes.tablaCliente.getColumnModel().getColumn(11).setMinWidth(0);
                vistaClientes.tablaCliente.getColumnModel().getColumn(11).setMaxWidth(0);
                vistaClientes.tablaCliente.getColumnModel().getColumn(11).setWidth(0);
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
    
    public void pintarFila(){
         vistaClientes.tablaCliente.setDefaultRenderer(Object.class, new TableCellRenderer() {
             @Override
             public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                 Component component = (JLabel) DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                 Color c = Color.PINK;
                 Object texto = table.getValueAt(row, 8);
                 if(texto == null){
                     component.setBackground(c);
                 }
                 return component;
             }
         });
    }

    
}
