/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.FichaControlDAO;
import Vista.Dialogs.AsignarPropiedad;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author Marcelo
 */
public class ControladorAsignacionPropiedad implements ActionListener{
    
    private Frame parent;    
    AsignarPropiedad vistaAsignarPropiedad ;
    FichaControlDAO fichaControlDAO = new FichaControlDAO();
    ControladorCliente cc = new ControladorCliente();
    int dni;

    public ControladorAsignacionPropiedad(Frame parent, AsignarPropiedad vistaAsignarPropiedad, int dni) {
        this.vistaAsignarPropiedad=vistaAsignarPropiedad;
        this.parent=parent;
        this.dni=dni;
        this.vistaAsignarPropiedad.aceptarBtn.addActionListener(this);
        this.vistaAsignarPropiedad.cancelarBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
          if(e.getSource() == vistaAsignarPropiedad.aceptarBtn){
              if(validarCampos() == true){
            fichaControlDAO.altaFichaControl(vistaAsignarPropiedad.tipo_propiedad.getSelectedItem().toString(), vistaAsignarPropiedad.dimension.getText(), Double.parseDouble(vistaAsignarPropiedad.precio.getText()), Double.parseDouble(vistaAsignarPropiedad.gastos_admin.getText()), Double.parseDouble(vistaAsignarPropiedad.bolsa_cemento.getText()), dni, vistaAsignarPropiedad.barrio.getText(),Integer.parseInt(vistaAsignarPropiedad.manzana.getText()), Integer.parseInt(vistaAsignarPropiedad.parcela.getText()));
            vistaAsignarPropiedad.dispose();
            cc.llenarTabla();}
          }
          if(e.getSource() == vistaAsignarPropiedad.cancelarBtn){
             vistaAsignarPropiedad.dispose();
          }
    }
     
     public boolean validarCampos(){
        boolean bandera = true;
       
        if(vistaAsignarPropiedad.precio.getText().isEmpty()){
         vistaAsignarPropiedad.precio.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         vistaAsignarPropiedad.precio.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(vistaAsignarPropiedad.bolsa_cemento.getText().isEmpty()){
         vistaAsignarPropiedad.bolsa_cemento.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         vistaAsignarPropiedad.bolsa_cemento.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
         if(vistaAsignarPropiedad.gastos_admin.getText().isEmpty()){
         vistaAsignarPropiedad.gastos_admin.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         vistaAsignarPropiedad.gastos_admin.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(vistaAsignarPropiedad.dimension.getText().isEmpty()){
         vistaAsignarPropiedad.dimension.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         vistaAsignarPropiedad.dimension.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        } 
        return bandera;
     }
}
