/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Clases.LimitadorCaracteres;
import Clases.Usuario;
import Modelo.UsuarioDAO;
import Vista.Dialogs.Login;
import Vista.Frame.Ventana;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcelo Espinoza
 */
public class ControladorLogin implements ActionListener, KeyListener, WindowListener{
    
   Login login;  
   Ventana frame;
   UsuarioDAO ud = new UsuarioDAO();
   FileWriter writer;

    public ControladorLogin(Ventana frame) {        
        this.frame = frame;
        login = new Login(frame, true);
        login.usuario.addKeyListener(this);
        login.usuario.setDocument(new LimitadorCaracteres(15));
        login.contraseña.addKeyListener(this);
        login.contraseña.setDocument(new LimitadorCaracteres(15));
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
        login.eyePass.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                login.contraseña.setEchoChar((char)0);
            }
            
    @Override
    public void mouseReleased(MouseEvent e) {
         login.contraseña.setEchoChar('*');
    }
         });
        login.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Usuario usuario = null;
            if(e.getSource() == login.iniciar_sesion){
                String contraseña= new String(login.contraseña.getPassword());
              if(login.usuario.getText().equals("") || contraseña.equals("")){
                  login.aviso.setText("* Ingrese todos los campos");
              }else{
                usuario = ud.validarUsuario(login.usuario.getText(), contraseña, login.tipo_operador.getSelection().getActionCommand());
                    try {
                        if (usuario!=null){
                            Ventana.labelUsuario.setText(usuario.getUsuario());
                            Ventana.labelTipoUsuario.setText(usuario.getTipoUsuario());
                            Ventana.nombreUsuario.setText(usuario.getNombres());
                            Ventana.apellidoUsuario.setText(usuario.getApellidos());    
                            //--------Escribo en el archivo de log quien inició sesion, dia y hora---------//
                            File file = new File("log.txt");
                            if(!file.exists()){
                            file.createNewFile();}
                            FileWriter writer = new FileWriter(file, true);
                            BufferedWriter bw = new BufferedWriter( writer );
                            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                            Calendar cal = Calendar.getInstance();
                            bw.write(usuario.getNombres()+" "+usuario.getApellidos()+" - "+login.tipo_operador.getSelection().getActionCommand()+" - "+dateFormat.format(cal.getTime()));
                            bw.newLine();
                            bw.close();
                            //-------Oculto ventana login y muestro el frame----------//
                            login.dispose();
                            frame.setVisible(true);
                        }else{
                            login.aviso.setText("* Usuario y/o contraseña incorrectos");              
                        }  
                     } catch (IOException ex) {
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
