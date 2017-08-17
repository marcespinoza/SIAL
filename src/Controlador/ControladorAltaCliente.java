/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ClienteDAO;
import Modelo.ReferenciaDAO;
import Vista.Panels.AltaCliente;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Marcelo
 */
public class ControladorAltaCliente implements ActionListener{
    
    private Frame parent;
    AltaCliente ac = new AltaCliente(parent, true);
    ClienteDAO cd = new ClienteDAO();
    ReferenciaDAO rd = new ReferenciaDAO();
    
    public ControladorAltaCliente(Frame parent, AltaCliente ac, ClienteDAO cd){
        this.parent=parent;
        this.ac=ac;
        this.cd=cd;
        this.ac.aceptar.addActionListener(this);
        this.ac.cancelar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == ac.aceptar){
            cd.altaCliente(Integer.parseInt(ac.documento.getText()), ac.apellidos.getText(), ac.nombres.getText(), new java.sql.Date(ac.fech_nacimiento.getDate().getTime()), ac.barrio.getText(), ac.calle.getText(), Integer.parseInt(ac.numero.getText()), ac.telefono1.getText(), ac.telefono2.getText(), ac.trabajo.getText());
            rd.altaReferencia(ac.telefonoRef.getText(), ac.apellidosRef.getText(), ac.nombresRef.getText(), ac.parentescoRef.getText(), Integer.parseInt(ac.documento.getText()));
        }
          if(e.getSource() == ac.cancelar){
              ac.setVisible(false);
        }
    }
    
}
