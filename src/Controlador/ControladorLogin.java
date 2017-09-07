/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.UsuarioDAO;
import Vista.Dialogs.Login;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;

/**
 *
 * @author Marcelo Espinoza
 */
public class ControladorLogin implements ActionListener, KeyListener{
    
   Login login;  
   Frame frame;
   UsuarioDAO ud = new UsuarioDAO();

    public ControladorLogin(Frame frame) {
        login = new Login(frame, true);
        this.frame = frame;
        login.cancelar.addActionListener(this);
        login.iniciar_sesion.addActionListener(this);
        login.addKeyListener(this);
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
               boolean flag = false;
               flag = ud.validarUsuario(login.usuario.getText(), contraseña, login.tipo_operador.getSelection().getActionCommand());
               if (flag){
                   login.aviso.setText("logincorrecto");
                 login.dispose();
               }else{
                 login.aviso.setText("* Usuario y/o contraseña incorrectos");
               }              
              }
            }
            if(e.getSource() == login.cancelar){
                login.dispose();
                frame.dispose();
            }
                System.out.println("Selected Button: " + login.tipo_operador.getSelection().getActionCommand());

    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
         if (e.getKeyCode()==KeyEvent.VK_ENTER){
            login.iniciar_sesion.doClick();
    }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
