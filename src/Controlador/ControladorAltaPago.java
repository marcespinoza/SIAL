/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.CuotaDAO;
import Modelo.FichaControlDAO;
import Vista.Dialogs.AltaCuota;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author Marcelo
 */
public class ControladorAltaPago implements ActionListener, KeyListener{
    
    Frame parent;
    CuotaDAO cd = new CuotaDAO();
    AltaCuota ac;
    int id_control;
    int row_count;
    double cuota_total;
    double gastos;

    public ControladorAltaPago(Frame parent, int id_control, int row_count) {
        this.parent = parent;
        this.id_control=id_control;
        this.row_count=row_count;
        ac = new AltaCuota(parent, true);
        ac.interes.addKeyListener(this);
        ac.aceptarBtn.addActionListener(this);
        ac.cancelarBtn.addActionListener(this);
        rellenarCampos();
    }
    
    public void rellenarCampos(){
        try {
            FichaControlDAO fcd = new FichaControlDAO();
            ResultSet rs = fcd.obtenerFichaControl(id_control);
            rs.next();
            cuota_total = Double.parseDouble(rs.getString(3))+Double.parseDouble(rs.getString(4));
            gastos = Double.parseDouble(rs.getString(4));
            ac.cuota_total.setText(Double.toString(cuota_total));
            ac.gastos.setText(Double.toString(gastos));
            ac.setVisible(true);
            
        } catch (SQLException ex) {
            Logger.getLogger(ControladorAltaPago.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
           if(e.getSource() == ac.aceptarBtn){
               if(validarCampos()){
               FichaControlDAO fcd = new FichaControlDAO();
               ResultSet rs = fcd.obtenerFichaControl(id_control);
               CuotaDAO cd = new CuotaDAO();
               ResultSet rs_cuota = cd.listaDetalleCuota(id_control);
               try {
                   rs.next();
                   rs_cuota.last();
                   double ultimo_saldo = Double.parseDouble(rs_cuota.getString(7));
                   double cuota_pura = Double.parseDouble(ac.cuota_total.getText())-Double.parseDouble(ac.gastos.getText());
                   double gastos = Double.parseDouble(ac.gastos.getText());
                   double bolsa_cemento = Double.parseDouble(rs.getString(5));
                   double ultimo_saldo_bolsa_cemento = Double.parseDouble(rs_cuota.getString(10));
                   calcularValores(ultimo_saldo, cuota_pura, gastos, bolsa_cemento, ultimo_saldo_bolsa_cemento);                
                
                } catch (SQLException ex) {
               }}
           }
            if(e.getSource() == ac.cancelarBtn){
               ac.dispose();
           }
            
    }
    
    public void calcularValores(double ultimo_saldo, double cuota_pura, double gastos,double bolsa_cemento, double saldo_bolsa_cemento){          
               DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
               Date date = new Date();
               String detalle = ac.detallePago.getText();
               String observaciones = ac.observacionesPago.getText();
               String tipoPago = ac.tipoPago.getSelectedItem().toString();
               double haber = cuota_pura;
               double saldo_actual = ultimo_saldo - haber;
               double cemento_haber = bolsa_cemento;
               double cemento_saldo = saldo_bolsa_cemento - bolsa_cemento;
               cd.altaCuota(new java.sql.Date(date.getTime()),0, detalle, cuota_pura, gastos, 0, haber, saldo_actual, 0, cemento_haber, cemento_saldo, observaciones, tipoPago, id_control);
               ac.dispose();
    }
    
     public boolean validarCampos(){
        boolean bandera = true;       
        if(ac.cuota_total.getText().isEmpty()){
         ac.cuota_total.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         ac.cuota_total.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        return bandera;
     }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        String interes = ac.interes.getText();
        if(!interes.equals("")){
        double gasto_interes = ((cuota_total*Double.parseDouble(interes))/100);   
        double nueva_cuota = gasto_interes+cuota_total;
        double nuevo_gasto = (nueva_cuota*10)/100;
        ac.cuota_total.setText(Double.toString(nueva_cuota));
        ac.gastos.setText(Double.toString(nuevo_gasto));
        }
        else {
            ac.cuota_total.setText(Double.toString(cuota_total));  
            ac.gastos.setText(Double.toString(gastos));  
        }
    }
        
}
