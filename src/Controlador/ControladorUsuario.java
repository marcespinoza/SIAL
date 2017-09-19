/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.UsuarioDAO;
import Vista.Dialogs.Usuarios;
import Vista.Frame.Ventana;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcelo Espinoza
 */
public class ControladorUsuario implements MouseListener, ActionListener, KeyListener{
    
    Usuarios vistaUsuarios;
    UsuarioDAO ud = new UsuarioDAO();
    private Object [] usuarios;

    public ControladorUsuario(Ventana ventana) {
        vistaUsuarios = new Usuarios(ventana, true);
        vistaUsuarios.tablaUsuarios.addMouseListener(this);
        vistaUsuarios.agregar.addActionListener(this);
        vistaUsuarios.eliminar.addActionListener(this);
        vistaUsuarios.editar.addActionListener(this);
        vistaUsuarios.limpiar.addActionListener(this);
        vistaUsuarios.usuarioTxf.addKeyListener(this);
        vistaUsuarios.contraseñaTxf.addKeyListener(this);
        vistaUsuarios.apellidoTxf.addKeyListener(this);
        vistaUsuarios.nombreTxf.addKeyListener(this);
        vistaUsuarios.tipo_operador.addMouseListener(this);
        vistaUsuarios.editar.setEnabled(false);
        vistaUsuarios.setLocationRelativeTo(null);
        llenarTabla();
        vistaUsuarios.setVisible(true);
        
    }
    
    public void llenarTabla(){
        ResultSet rs = ud.obtenerUsuarios();
        DefaultTableModel model = (DefaultTableModel) vistaUsuarios.tablaUsuarios.getModel();
        model.setRowCount(0);
        try {
            while(rs.next()){
                String usuario = rs.getString(1);
                String contraseña = rs.getString(2);
                String apellidos = rs.getString(3);  
                String nombres = rs.getString(4);
                String tipo_usuario = rs.getString(5);
                usuarios = new Object[] {usuario, contraseña, apellidos, nombres, tipo_usuario};
                model.addRow(usuarios);
            }
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
          int row = vistaUsuarios.tablaUsuarios.getSelectedRow();
          vistaUsuarios.usuarioTxf.setText(vistaUsuarios.tablaUsuarios.getModel().getValueAt(row,0).toString());
          vistaUsuarios.contraseñaTxf.setText(vistaUsuarios.tablaUsuarios.getModel().getValueAt(row,1).toString());
          vistaUsuarios.apellidoTxf.setText(vistaUsuarios.tablaUsuarios.getModel().getValueAt(row,2).toString());
          vistaUsuarios.nombreTxf.setText(vistaUsuarios.tablaUsuarios.getModel().getValueAt(row,3).toString());
          System.out.println(vistaUsuarios.tablaUsuarios.getModel().getValueAt(row,4).toString());
          switch(vistaUsuarios.tablaUsuarios.getModel().getValueAt(row,4).toString()){              
              case "operador":vistaUsuarios.tipo_operador.setSelectedIndex(2);
              break;
              case "administrador":vistaUsuarios.tipo_operador.setSelectedIndex(1);
              break;
              default:vistaUsuarios.tipo_operador.setSelectedIndex(1);
              break;
          }
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
        if(e.getSource()==vistaUsuarios.limpiar){
           limpiarCampos();
        }
        
        if(e.getSource()==vistaUsuarios.editar){
           ud.actualizarUsuario(vistaUsuarios.usuarioTxf.getText(), vistaUsuarios.contraseñaTxf.getText(), vistaUsuarios.apellidoTxf.getText(), vistaUsuarios.nombreTxf.getText(), vistaUsuarios.tipo_operador.getSelectedItem().toString());
           llenarTabla();
        }
        
        if(e.getSource()==vistaUsuarios.eliminar){
            int row = vistaUsuarios.tablaUsuarios.getSelectedRow();
            if(row==-1){
              JOptionPane.showMessageDialog(null, "Seleccione un usuario", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
            }else{
              ImageIcon icon = new ImageIcon("src/Imagenes/Iconos/warning.png");   
             int reply = JOptionPane.showConfirmDialog(null, "Eliminar a "+vistaUsuarios.tablaUsuarios.getModel().getValueAt(row, 2)+" "+""+" "+vistaUsuarios.tablaUsuarios.getModel().getValueAt(row, 3)+"?",
                     "Advertencia",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
              if (reply == JOptionPane.YES_OPTION) {
               String usuario = vistaUsuarios.tablaUsuarios.getModel().getValueAt(row,0).toString();
               ud.eliminarUsuario(usuario);
               llenarTabla();
              llenarTabla();
                 }   
             
            }
        }
        
           if(e.getSource()==vistaUsuarios.agregar){
             if(validarCampos()){
              ud.agregarUsuario(vistaUsuarios.usuarioTxf.getText(), vistaUsuarios.contraseñaTxf.getText(), vistaUsuarios.apellidoTxf.getText(), vistaUsuarios.nombreTxf.getText(), String.valueOf(vistaUsuarios.tipo_operador.getSelectedItem()));
              llenarTabla();
              limpiarCampos();
             }
           }
    }
    
    private void limpiarCampos(){
         vistaUsuarios.usuarioTxf.setText("");
         vistaUsuarios.contraseñaTxf.setText("");
         vistaUsuarios.apellidoTxf.setText("");
         vistaUsuarios.nombreTxf.setText("");
    }
    
     public boolean validarCampos(){
        boolean bandera = true;
       
        if(vistaUsuarios.usuarioTxf.getText().isEmpty()){
         vistaUsuarios.usuarioTxf.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         vistaUsuarios.usuarioTxf.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(vistaUsuarios.contraseñaTxf.getText().isEmpty()){
         vistaUsuarios.contraseñaTxf.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         vistaUsuarios.contraseñaTxf.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(vistaUsuarios.apellidoTxf.getText().isEmpty()){
         vistaUsuarios.apellidoTxf.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         vistaUsuarios.apellidoTxf.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(vistaUsuarios.nombreTxf.getText().isEmpty()){
         vistaUsuarios.nombreTxf.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         vistaUsuarios.nombreTxf.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        return bandera;
     }

    @Override
    public void keyTyped(KeyEvent e) {
           vistaUsuarios.editar.setEnabled(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
