/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ClienteDAO;
import Modelo.ReferenciaDAO;
import Vista.Panels.AltaCliente;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.BorderUIResource;

/**
 *
 * @author Marcelo
 */
public class ControladorAltaCliente implements ActionListener{
    
    private Frame parent;
    AltaCliente ac = new AltaCliente(parent, true);
    ClienteDAO cd = new ClienteDAO();
    ReferenciaDAO rd = new ReferenciaDAO();
    ControladorCliente cc = new ControladorCliente();
    JTable tablaCliente;
    
    
    public ControladorAltaCliente(Frame parent, AltaCliente ac, ClienteDAO cd, JTable tablaCliente){
        this.parent=parent;
        this.ac=ac;
        this.cd=cd;
        this.tablaCliente=tablaCliente;
        this.ac.aceptar.addActionListener(this);
        this.ac.cancelar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == ac.aceptar){
            if(validarCampos()){
            int numReistros = cd.altaCliente(Integer.parseInt(ac.documento.getText()), ac.apellidos.getText(), ac.nombres.getText(), new java.sql.Date(ac.fech_nacimiento.getDate().getTime()), ac.barrio.getText(), ac.calle.getText(), Integer.parseInt(ac.numero.getText()), ac.telefono1.getText(), ac.telefono2.getText(), ac.trabajo.getText());
            rd.altaReferencia(ac.telefonoRef.getText(), ac.apellidosRef.getText(), ac.nombresRef.getText(), ac.parentescoRef.getText(), Integer.parseInt(ac.documento.getText()));
            
            cc.llenarTabla(tablaCliente);
            ac.setVisible(false);
            }
        }
          if(e.getSource() == ac.cancelar){
        }
    }
    
    public boolean validarCampos(){
        boolean bandera = true;
       
        if(ac.apellidos.getText().isEmpty()){
         ac.apellidos.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         ac.apellidos.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(ac.nombres.getText().isEmpty()){
         ac.nombres.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         ac.nombres.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(ac.fech_nacimiento.getDate() == null){
         ac.fech_nacimiento.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         ac.fech_nacimiento.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(ac.documento.getText().isEmpty()){
         ac.documento.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         ac.documento.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(ac.barrio.getText().isEmpty()){
         ac.barrio.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         ac.barrio.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(ac.calle.getText().isEmpty()){
         ac.calle.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         ac.calle.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(ac.numero.getText().isEmpty()){
         ac.numero.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         ac.numero.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(ac.telefono1.getText().isEmpty()){
         ac.telefono1.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         ac.telefono1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(ac.apellidosRef.getText().isEmpty()){
         ac.apellidosRef.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         ac.apellidosRef.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(ac.nombresRef.getText().isEmpty()){
         ac.nombresRef.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         ac.nombresRef.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(ac.telefonoRef.getText().isEmpty()){
         ac.telefonoRef.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         ac.telefonoRef.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(ac.parentescoRef.getText().isEmpty()){
         ac.parentescoRef.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         ac.parentescoRef.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        return bandera;
    }
    
}
