/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Clases.LimitadorCaracteres;
import Modelo.PropietarioDAO;
import Vista.Dialogs.Configuracion;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.sql.ResultSet;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcelo Espinoza
 */
public class ControladorPropietarios implements  ActionListener, KeyListener{
    
    Configuracion vistaConfiguracion;
    PropietarioDAO pd = new PropietarioDAO();
    private Object [] propietarios;    
    File configFile = new File("config.properties");
    String backup_cuit=null;

    public ControladorPropietarios(Configuracion vistaConfiguracion) {
        this.vistaConfiguracion = vistaConfiguracion;
        vistaConfiguracion.propietarios.tablaPropietarios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
              super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.
              int row = vistaConfiguracion.propietarios.tablaPropietarios.getSelectedRow();
              //-------Si hizo click dos veces guardo ese propietario para mostrar por defecto------------//
              if (e.getClickCount() == 2) {
                System.out.println(vistaConfiguracion.propietarios.tablaPropietarios.getModel().getValueAt(row,1).toString());           
              }
              vistaConfiguracion.propietarios.apellidoTxf.setText(vistaConfiguracion.propietarios.tablaPropietarios.getModel().getValueAt(row,0).toString());
              vistaConfiguracion.propietarios.nombreTxf.setText(vistaConfiguracion.propietarios.tablaPropietarios.getModel().getValueAt(row,1).toString());
              vistaConfiguracion.propietarios.cuitTxf.setText(vistaConfiguracion.propietarios.tablaPropietarios.getModel().getValueAt(row,2).toString());
              vistaConfiguracion.propietarios.nroRecibo.setText(vistaConfiguracion.propietarios.tablaPropietarios.getModel().getValueAt(row,3).toString());
              backup_cuit = vistaConfiguracion.propietarios.tablaPropietarios.getModel().getValueAt(row,2).toString();
            }
            
        });
        vistaConfiguracion.propietarios.agregar.addActionListener(this);
        vistaConfiguracion.propietarios.eliminar.addActionListener(this);
        vistaConfiguracion.propietarios.editar.addActionListener(this);
        vistaConfiguracion.propietarios.limpiar.addActionListener(this);
        vistaConfiguracion.propietarios.apellidoTxf.addKeyListener(this);
        vistaConfiguracion.propietarios.apellidoTxf.setDocument(new LimitadorCaracteres(30));
        vistaConfiguracion.propietarios.nombreTxf.addKeyListener(this);
        vistaConfiguracion.propietarios.nombreTxf.setDocument(new LimitadorCaracteres(30));
        vistaConfiguracion.propietarios.cuitTxf.addKeyListener(this);
        vistaConfiguracion.propietarios.cuitTxf.setDocument(new LimitadorCaracteres(14));
        vistaConfiguracion.propietarios.nroRecibo.addKeyListener(this);
        vistaConfiguracion.propietarios.editar.setEnabled(false);
        vistaConfiguracion.setLocationRelativeTo(null);
        llenarTabla();        
    }
    
    public void llenarTabla(){
        ResultSet rs = pd.obtenerPropietarios();
        DefaultTableModel model = (DefaultTableModel) vistaConfiguracion.propietarios.tablaPropietarios.getModel();        
        model.setRowCount(0);
        try {
            while(rs.next()){
                String apellidos = rs.getString(1);  
                String nombres = rs.getString(2);
                String cuit = rs.getString(3);
                String nroRecibo = rs.getString(4);
                propietarios = new Object[] {apellidos, nombres, cuit, nroRecibo, false};
                model.addRow(propietarios);
            }
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==vistaConfiguracion.propietarios.limpiar){
           limpiarCampos();
        }
        
        if(e.getSource()==vistaConfiguracion.propietarios.editar){
           pd.editarPropietario(vistaConfiguracion.propietarios.apellidoTxf.getText(), vistaConfiguracion.propietarios.nombreTxf.getText(), vistaConfiguracion.propietarios.cuitTxf.getText(),Integer.parseInt(vistaConfiguracion.propietarios.nroRecibo.getText()), backup_cuit);
           llenarTabla();
           new ControladorPropiedades(vistaConfiguracion);
        }
        
        if(e.getSource()==vistaConfiguracion.propietarios.eliminar){
            int row = vistaConfiguracion.propietarios.tablaPropietarios.getSelectedRow();
            if(row==-1){
              JOptionPane.showMessageDialog(null, "Seleccione un propietario", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
            }else{
              ImageIcon icon = new ImageIcon("src/Imagenes/Iconos/warning.png");   
             int reply = JOptionPane.showConfirmDialog(null, "Eliminar a "+vistaConfiguracion.propietarios.tablaPropietarios.getModel().getValueAt(row, 0)+" "+""+" "+vistaConfiguracion.propietarios.tablaPropietarios.getModel().getValueAt(row, 1)+"?",
                     "Advertencia",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
              if (reply == JOptionPane.YES_OPTION) {
               String cuit = vistaConfiguracion.propietarios.tablaPropietarios.getModel().getValueAt(row,2).toString();
               pd.eliminarPropietarios(cuit);
               limpiarCampos();
               llenarTabla();
               new ControladorPropiedades(vistaConfiguracion);
                 }                
            }
        }
        
           if(e.getSource()==vistaConfiguracion.propietarios.agregar){
             if(validarCampos()){
              pd.agregarPropietarios(vistaConfiguracion.propietarios.apellidoTxf.getText(), vistaConfiguracion.propietarios.nombreTxf.getText(), vistaConfiguracion.propietarios.cuitTxf.getText(), vistaConfiguracion.propietarios.nroRecibo.getText());
              llenarTabla();
              limpiarCampos();
              new ControladorPropiedades(vistaConfiguracion);
             }
           }
    }
    
    private void limpiarCampos(){
         vistaConfiguracion.propietarios.apellidoTxf.setText("");
         vistaConfiguracion.propietarios.nombreTxf.setText("");
         vistaConfiguracion.propietarios.cuitTxf.setText("");
         vistaConfiguracion.propietarios.nroRecibo.setText("");
    }
    
     public boolean validarCampos(){
        boolean bandera = true;
       
        if(vistaConfiguracion.propietarios.apellidoTxf.getText().isEmpty()){
         vistaConfiguracion.propietarios.apellidoTxf.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         vistaConfiguracion.propietarios.apellidoTxf.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(vistaConfiguracion.propietarios.nombreTxf.getText().isEmpty()){
         vistaConfiguracion.propietarios.nombreTxf.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         vistaConfiguracion.propietarios.nombreTxf.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(vistaConfiguracion.propietarios.cuitTxf.getText().isEmpty()){
         vistaConfiguracion.propietarios.cuitTxf.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         vistaConfiguracion.propietarios.cuitTxf.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(vistaConfiguracion.propietarios.nroRecibo.getText().isEmpty()){
         vistaConfiguracion.propietarios.nroRecibo.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         vistaConfiguracion.propietarios.nroRecibo.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        return bandera;
     }

    @Override
    public void keyTyped(KeyEvent e) {
           vistaConfiguracion.propietarios.editar.setEnabled(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}