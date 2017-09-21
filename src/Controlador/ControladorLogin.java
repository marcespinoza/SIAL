/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.UsuarioDAO;
import Vista.Dialogs.Login;
import Vista.Frame.Ventana;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcelo Espinoza
 */
public class ControladorLogin implements ActionListener, KeyListener, WindowListener{
    
   Login login;  
   Frame frame;
   UsuarioDAO ud = new UsuarioDAO();

    public ControladorLogin(Frame frame) {
        login = new Login(frame, true);
        this.frame = frame;
        login.usuario.addKeyListener(this);
        login.contraseña.addKeyListener(this);
        login.cancelar.addActionListener(this);
        login.iniciar_sesion.addActionListener(this);
        login.tipo_operador.add(login.administradorChk);
        login.tipo_operador.add(login.operadorChk);
        login.operadorChk.setActionCommand("operador");
        login.administradorChk.setActionCommand("administrador");
        login.operadorChk.addActionListener(this);
        login.administradorChk.addActionListener(this);
        login.usuario.requestFocusInWindow();
        login.usuario.setCaretColor(Color.WHITE);
        login.contraseña.setCaretColor(Color.WHITE);
        login.operadorChk.setSelected(true);
        login.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            if(e.getSource() == login.iniciar_sesion){
                String contraseña= new String(login.contraseña.getPassword());
              if(login.usuario.getText().equals("") || contraseña.equals("")){
                  login.aviso.setText("* Ingrese todos los campos");
              }else{
                  ResultSet rs = null;
                rs = ud.validarUsuario(login.usuario.getText(), contraseña, login.tipo_operador.getSelection().getActionCommand());
                    try {
                        if (rs.next()){
                            Ventana.labelUsuario.setText(rs.getString(1));
                            Ventana.labelTipoUsuario.setText(rs.getString(2));
                            Ventana.nombreUsuario.setText(rs.getString(3));
                            Ventana.apellidoUsuario.setText(rs.getString(4));
                            login.dispose();
                            frame.setVisible(true);
                        }else{
                            login.aviso.setText("* Usuario y/o contraseña incorrectos");              
                        }    } catch (SQLException ex) {
                        Logger.getLogger(ControladorLogin.class.getName()).log(Level.SEVERE, null, ex);
                    }
              }
            }
            if(e.getSource() == login.cancelar){
                login.dispose();
                frame.dispose();
            }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
         if (e.getKeyCode()==KeyEvent.VK_ENTER){
            login.iniciar_sesion.doClick();
    }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        frame.dispose();
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
    
}
