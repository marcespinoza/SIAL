/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ClienteDAO;
import Modelo.ReferenciaDAO;
import Modelo.RendererTablaCliente;
import Vista.Frame.Ventana;
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
import java.sql.SQLException;
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
    
    RendererTablaCliente r = new RendererTablaCliente();
    Clientes vistaClientes = new Clientes();
    ClienteDAO cd = new ClienteDAO();
    ReferenciaDAO rd = new ReferenciaDAO();
    Ventana ventana;
    private Object [] clientes;
    public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

    
    public ControladorCliente(Ventana ventana, Clientes vistaClientes){
        this.vistaClientes=vistaClientes;
        this.ventana=ventana;
        this.vistaClientes.agregarBtn.addActionListener(this);
        this.vistaClientes.eliminarBtn.addActionListener(this);
        this.vistaClientes.editarBtn.addActionListener(this);
        this.vistaClientes.agregarBtn.addActionListener(this);
        this.vistaClientes.detalleBtn.addActionListener(this);
        this.vistaClientes.asignarBtn.addActionListener(this);
        this.vistaClientes.tablaCliente.addMouseListener(this);
        vistaClientes.datosCliente.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.BLACK), "Datos cliente"));
        vistaClientes.datosReferencia.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.BLACK), "Datos referencia"));
        this.vistaClientes.tablaCliente.setDefaultRenderer(Object.class, r);
        desactivarBotones();
    }

    public ControladorCliente() {
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vistaClientes.agregarBtn){  
            new ControladorAltaCliente((Frame) SwingUtilities.getWindowAncestor(vistaClientes), cd, vistaClientes.tablaCliente);
            llenarTabla();
         }
        if(e.getSource() == vistaClientes.eliminarBtn){
             int row = vistaClientes.tablaCliente.getSelectedRow(); 
             if(row != -1){
             ImageIcon icon = new ImageIcon("src/Imagenes/Iconos/warning.png");   
             int reply = JOptionPane.showConfirmDialog(null, "Eliminar a "+vistaClientes.tablaCliente.getModel().getValueAt(row, 0)+" "+""+" "+vistaClientes.tablaCliente.getModel().getValueAt(row, 1)+"?",
                     "Advertencia",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
              if (reply == JOptionPane.YES_OPTION) {
              int dni = Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(row, 2).toString());
              System.out.println(dni);
              cd.eliminarCliente(dni);
              llenarTabla();
                 } 
                  }
                 else{
                 JOptionPane.showMessageDialog(null, "Seleccione un cliente de la lista", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
  
             }
        }
        if(e.getSource() == vistaClientes.editarBtn){
           llenarTabla();
        }
        
        if(e.getSource() == vistaClientes.detalleBtn){  
           int row = vistaClientes.tablaCliente.getSelectedRow();
           if(row != -1){
               if(vistaClientes.tablaCliente.getValueAt(row, 8) != null){
                   DetalleCuota vistaDetallePago = new DetalleCuota();
                   ControladorDetalleCuota cdp = new ControladorDetalleCuota(vistaDetallePago, vistaClientes.tablaCliente.getModel().getValueAt(row, 0).toString(),vistaClientes.tablaCliente.getModel().getValueAt(row, 1).toString(), vistaClientes.tablaCliente.getModel().getValueAt(row, 3).toString(), vistaClientes.tablaCliente.getModel().getValueAt(row, 4).toString(), vistaClientes.tablaCliente.getModel().getValueAt(row, 5).toString(), Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(row, 6).toString()),  Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(row, 8).toString()));
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
                   //AsignarPropiedad ap = new AsignarPropiedad((Frame) SwingUtilities.getWindowAncestor(vistaClientes), true);
                   ControladorAsignacionPropiedad cap = new ControladorAsignacionPropiedad((Frame) SwingUtilities.getWindowAncestor(vistaClientes), Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(row, 2).toString()));
                   //ap.setVisible(true);
                  llenarTabla();
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
                String cantidad_cuotas = rs.getString(10);
                String gastos = rs.getString(11);
                String bolsa_cemento = rs.getString(12);
                String barrio_prop = rs.getString(13);
                String manzana_prop = rs.getString(14);
                String parcela_prop = rs.getString(15);  
                clientes = new Object[] {apellidos, nombres, dni, telefono, barrio, calle, numero, trabajo, idControl, cantidad_cuotas, gastos, bolsa_cemento, barrio_prop, manzana_prop, parcela_prop};
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
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = vistaClientes.tablaCliente.getSelectedRow();       
        vistaClientes.barrio.setText(vistaClientes.tablaCliente.getModel().getValueAt(row, 4).toString());
        vistaClientes.calle.setText(vistaClientes.tablaCliente.getModel().getValueAt(row, 5).toString());
        vistaClientes.numero.setText(vistaClientes.tablaCliente.getModel().getValueAt(row, 6).toString());
        vistaClientes.trabajo.setText(vistaClientes.tablaCliente.getModel().getValueAt(row, 7).toString());  
        ResultSet rs = rd.obtenerReferencia(Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(row,2).toString()));
        try {
            while(rs.next()){
                vistaClientes.apellido_referencia.setText(rs.getString(1));
                vistaClientes.nombre_referencia.setText(rs.getString(2));
                vistaClientes.telefono_referencia.setText(rs.getString(3));
                vistaClientes.parentesco.setText(rs.getString(4));                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void desactivarBotones(){        
        if(Ventana.labelTipoUsuario.getText().equals("operador")){
            vistaClientes.editarBtn.setEnabled(false);
            vistaClientes.eliminarBtn.setEnabled(false);
        }
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
                 if(table.getValueAt(row, 8) == null){
                     component.setBackground(c);
                 }
                 return component;
             }
         });
    }

    
}
