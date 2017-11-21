/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ClienteDAO;
import Modelo.FichaControlDAO;
import Modelo.LimitadorCaracteres;
import Modelo.ReferenciaDAO;
import Vista.Dialogs.AltaCliente;
import Vista.Frame.Ventana;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author Marcelo
 */
public class ControladorAltaCliente implements ActionListener, KeyListener{
        
    AltaCliente ac;
    FichaControlDAO fd = new FichaControlDAO();
    ClienteDAO cd = new ClienteDAO();
    ReferenciaDAO rd = new ReferenciaDAO();
    private int id_control, dni=0;
    boolean bandera=false;
    
    public ControladorAltaCliente(Ventana ventana, int id_control,int dni, boolean bandera){
        ac = new AltaCliente(ventana, true);  
        this.id_control=id_control;
        this.dni=dni;
        this.bandera=bandera;
        this.ac.aceptar.addActionListener(this);
        this.ac.cancelar.addActionListener(this);
        this.ac.parentescoRef.addKeyListener(this);
        this.ac.documento.setDocument(new LimitadorCaracteres(8));
        this.ac.apellidos.setDocument(new LimitadorCaracteres(30));
        this.ac.nombres.setDocument(new LimitadorCaracteres(30));
        this.ac.barrio.setDocument(new LimitadorCaracteres(20));
        this.ac.calle.setDocument(new LimitadorCaracteres(25));
        this.ac.numero.setDocument(new LimitadorCaracteres(5));
        this.ac.telefono1.setDocument(new LimitadorCaracteres(12));
        this.ac.telefono2.setDocument(new LimitadorCaracteres(12));        
        this.ac.trabajo.setDocument(new LimitadorCaracteres(20));
        this.ac.apellidosRef.setDocument(new LimitadorCaracteres(30));
        this.ac.nombresRef.setDocument(new LimitadorCaracteres(30));
        this.ac.telefonoRef.setDocument(new LimitadorCaracteres(12));
        this.ac.parentescoRef.setDocument(new LimitadorCaracteres(15));
        ac.setVisible(true);
    }   
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == ac.aceptar){
            if(validarCampos()){ 
            new AltaClienteSwing().execute();
            }
        }
          if(e.getSource() == ac.cancelar){
              ac.setVisible(false);
        }
    }
 
    
    public boolean validarCampos(){
        boolean bandera = true;       
        if(ac.apellidos.getText().isEmpty()){
         ac.apellidos.setBorder(BorderFactory.createLineBorder(Color.RED));
         bandera=false;
        }else{
         ac.apellidos.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(ac.nombres.getText().isEmpty()){
         ac.nombres.setBorder(BorderFactory.createLineBorder(Color.RED));
         bandera=false;
        }else{
         ac.nombres.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(ac.fech_nac.getDate()== null){
         ac.fech_nac.setBorder(BorderFactory.createLineBorder(Color.RED));
         bandera=false;
        }else{
         ac.fech_nac.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(ac.documento.getText().isEmpty()){
         ac.documento.setBorder(BorderFactory.createLineBorder(Color.RED));
         bandera=false;
        }else{
         ac.documento.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(ac.barrio.getText().isEmpty()){
         ac.barrio.setBorder(BorderFactory.createLineBorder(Color.RED));
         bandera=false;
        }else{
         ac.barrio.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(ac.calle.getText().isEmpty()){
         ac.calle.setBorder(BorderFactory.createLineBorder(Color.RED));
         bandera=false;
        }else{
         ac.calle.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(ac.numero.getText().isEmpty()){
         ac.numero.setBorder(BorderFactory.createLineBorder(Color.RED));
         bandera=false;
        }else{
         ac.numero.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(ac.telefono1.getText().isEmpty()){
         ac.telefono1.setBorder(BorderFactory.createLineBorder(Color.RED));
         bandera=false;
        }else{
         ac.telefono1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(ac.apellidosRef.getText().isEmpty()){
         ac.apellidosRef.setBorder(BorderFactory.createLineBorder(Color.RED));
         bandera=false;
        }else{
         ac.apellidosRef.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(ac.nombresRef.getText().isEmpty()){
         ac.nombresRef.setBorder(BorderFactory.createLineBorder(Color.RED));
         bandera=false;
        }else{
         ac.nombresRef.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(ac.telefonoRef.getText().isEmpty()){
         ac.telefonoRef.setBorder(BorderFactory.createLineBorder(Color.RED));
         bandera=false;
        }else{
         ac.telefonoRef.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(ac.parentescoRef.getText().isEmpty()){
         ac.parentescoRef.setBorder(BorderFactory.createLineBorder(Color.RED));
         bandera=false;
        }else{
         ac.parentescoRef.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        return bandera;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_ENTER){
            ac.aceptar.doClick();
    }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    public class AltaClienteSwing extends javax.swing.SwingWorker<Void, Void>{         

        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        
        @Override
        protected Void doInBackground() throws Exception {  
            cd.altaCliente(Integer.parseInt(ac.documento.getText()), ac.apellidos.getText(), ac.nombres.getText(),new java.sql.Date(ac.fech_nac.getDate().getTime()), ac.barrio.getText(), ac.calle.getText(), Integer.parseInt(ac.numero.getText()), ac.telefono1.getText(), ac.telefono2.getText(), ac.trabajo.getText());
            if(bandera){
            fd.cambiarPropietario(Integer.parseInt(ac.documento.getText()), dni, id_control);}
            return null;
        }

       @Override
       public void done() { 
            rd.altaReferencia(ac.telefonoRef.getText(), ac.apellidosRef.getText(), ac.nombresRef.getText(), ac.parentescoRef.getText(), Integer.parseInt(ac.documento.getText()));
            //------Si es id_control es distinto de cero, entonces estoy agregando un propietario mas-----//
            //------a un lote que ya posee un propietario-------------------------------------------------//
            if(id_control!=0 && !bandera){
              cd.altaClientesXLotes(Integer.parseInt(ac.documento.getText()), id_control);
            }
            ac.dispose();   
       }
    
}
    
}
