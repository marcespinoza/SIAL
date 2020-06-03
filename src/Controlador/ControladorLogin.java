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
import conexion.Conexion;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcelo Espinoza
 */
public class ControladorLogin implements ActionListener, KeyListener{
    
   Login login;  
   Ventana frame;
   UsuarioDAO ud = new UsuarioDAO();
   FileWriter writer;
   static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(Conexion.class.getName());  
   
    Logger extAppLogger= Logger.getLogger("errorAppender"); 
    public ControladorLogin() {        
        login = new Login( true);  
        login.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e); //To change body of generated methods, choose Tools | Templates.
                login.dispose();
            }
            
        });
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
        Calendar cal = Calendar.getInstance();
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
                            frame = new Ventana();
                            log.info(usuario.getNombres()+" "+usuario.getApellidos()+ " - Inicio sesión");
                            frame.labelUsuario.setText(usuario.getUsuario());
                            frame.labelTipoUsuario.setText(usuario.getTipoUsuario());
                            frame.nombreUsuario.setText(usuario.getNombres());
                            frame.apellidoUsuario.setText(usuario.getApellidos());    
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
    
}
