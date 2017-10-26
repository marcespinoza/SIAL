/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.PropietarioDAO;
import Vista.Dialogs.Configuracion;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcelo Espinoza
 */
public class ControladorPropietarios implements MouseListener, ActionListener, KeyListener{
    
    Configuracion vistaConfiguracion;
    PropietarioDAO pd = new PropietarioDAO();
    private Object [] propietarios;    
    File configFile = new File("config.properties");

    public ControladorPropietarios(Configuracion vistaConfiguracion) {
        this.vistaConfiguracion = vistaConfiguracion;
        vistaConfiguracion.propietarios.tablaPropietarios.addMouseListener(this);
        vistaConfiguracion.propietarios.agregar.addActionListener(this);
        vistaConfiguracion.propietarios.eliminar.addActionListener(this);
        vistaConfiguracion.propietarios.editar.addActionListener(this);
        vistaConfiguracion.propietarios.limpiar.addActionListener(this);
        vistaConfiguracion.propietarios.apellidoTxf.addKeyListener(this);
        vistaConfiguracion.propietarios.nombreTxf.addKeyListener(this);
        vistaConfiguracion.propietarios.cuitTxf.addKeyListener(this);
        vistaConfiguracion.propietarios.nroRecibo.addKeyListener(this);
        vistaConfiguracion.propietarios.editar.setEnabled(false);
        vistaConfiguracion.setLocationRelativeTo(null);
        llenarTabla();        
    }
    
    private void guardarNroRecibo(){
        try {
                Properties props = new Properties();
                props.setProperty("pathMinuta", vistaConfiguracion.propietarios.nroRecibo.getText());
                FileWriter writer = new FileWriter(configFile);
                props.store(writer, "config");
                writer.close();
                } catch (FileNotFoundException ex) {
                 // file does not exist
                } catch (IOException ex) {
                   // I/O error
                }
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
                propietarios = new Object[] {apellidos, nombres, cuit, nroRecibo};
                model.addRow(propietarios);
            }
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
          int row = vistaConfiguracion.propietarios.tablaPropietarios.getSelectedRow();
          vistaConfiguracion.propietarios.apellidoTxf.setText(vistaConfiguracion.propietarios.tablaPropietarios.getModel().getValueAt(row,0).toString());
          vistaConfiguracion.propietarios.nombreTxf.setText(vistaConfiguracion.propietarios.tablaPropietarios.getModel().getValueAt(row,1).toString());
          vistaConfiguracion.propietarios.cuitTxf.setText(vistaConfiguracion.propietarios.tablaPropietarios.getModel().getValueAt(row,2).toString());
          vistaConfiguracion.propietarios.nroRecibo.setText(vistaConfiguracion.propietarios.tablaPropietarios.getModel().getValueAt(row,3).toString());
     }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==vistaConfiguracion.propietarios.limpiar){
           limpiarCampos();
        }
        
        if(e.getSource()==vistaConfiguracion.propietarios.editar){
           pd.editarPropietario(vistaConfiguracion.propietarios.apellidoTxf.getText(), vistaConfiguracion.propietarios.nombreTxf.getText(), vistaConfiguracion.propietarios.cuitTxf.getText(),Integer.parseInt(vistaConfiguracion.propietarios.nroRecibo.getText()));
           llenarTabla();
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