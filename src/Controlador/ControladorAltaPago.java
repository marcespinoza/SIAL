/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.CuotaDAO;
import Modelo.DchoPosesionDAO;
import Modelo.FichaControlDAO;
import Vista.Dialogs.AltaCuota;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Marcelo
 */
public class ControladorAltaPago implements ActionListener, KeyListener{
    
    Frame parent;
    CuotaDAO cd = new CuotaDAO();
    DchoPosesionDAO dp = new DchoPosesionDAO();
    AltaCuota ac;
    int id_control;
    int row_count;
    BigDecimal cuota_total;
    BigDecimal gastos;

    public ControladorAltaPago(Frame parent, int id_control, int row_count) {
        this.parent = parent;
        this.id_control=id_control;
        this.row_count=row_count;
        ac = new AltaCuota(parent, true);
        ac.tipo_cuenta.add(ac.chk_cuota);
        ac.tipo_cuenta.add(ac.chk_dcho_posesion);
        ac.chk_cuota.addActionListener(this);
        ac.chk_dcho_posesion.addActionListener(this);
        ac.chk_cuota.setSelected(true);
        ac.cuota_total.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
             nuevoGasto();
            }
             }
        );
        ac.cuota_total.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {    
             nuevoGasto();}
            @Override
            public void removeUpdate(DocumentEvent e) {
                nuevoGasto();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
            }
         });
        ac.interes.addKeyListener(this);
        ac.aceptarBtn.addActionListener(this);
        ac.cancelarBtn.addActionListener(this);
        rellenarCampos();
    }
    
    public void nuevoGasto(){
        if(!ac.cuota_total.getText().equals("")){   
                BigDecimal gasto;
                 BigDecimal cuota_total = new BigDecimal(ac.cuota_total.getText());
                 if(ac.chk_cuota.isSelected()){
                 gasto = cuota_total.subtract(cuota_total.divide(new BigDecimal("1.1"), 2, BigDecimal.ROUND_HALF_UP));
                 }else{
                 gasto = (cuota_total.multiply(new BigDecimal("10"))).divide(new BigDecimal("100"),2, BigDecimal.ROUND_HALF_UP);
                 }ac.gastos.setText(gasto.toString());
            }else{
                ac.gastos.setText("");
             }
    }
    
    public void rellenarCampos(){
        try {
            FichaControlDAO fcd = new FichaControlDAO();
            ResultSet rs = fcd.obtenerFichaControl(id_control);
            rs.next();
            cuota_total = new BigDecimal (rs.getString(3)).add(new BigDecimal(rs.getString(4)));
            gastos = new BigDecimal(rs.getString(4));
            ac.cuota_total.setText(cuota_total.toString());
            ac.gastos.setText(gastos.toString());
            ac.setVisible(true);
            
        } catch (SQLException ex) {
            Logger.getLogger(ControladorAltaPago.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
           if(e.getSource() == ac.aceptarBtn){
              if(ac.chk_cuota.isSelected()){
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
               }
               }
              }else{                  
                   DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                   Date date = new Date(); 
                  dp.altaDchoPosesion(new java.sql.Date(date.getTime()), Double.parseDouble(ac.cuota_total.getText()), ac.detallePago.getText(), id_control);
                  ac.dispose();
              }
           }
            if(e.getSource() == ac.cancelarBtn){
               ac.dispose();
           }
            if(e.getSource() == ac.chk_cuota){
                nuevoGasto();
            }
            if(e.getSource() == ac.chk_dcho_posesion){
                nuevoGasto();
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
       // if(e.getSource()==ac.cuota_total){
        String interes = ac.interes.getText();
        if(!interes.equals("")){
        BigDecimal gasto_interes = (cuota_total.multiply(new BigDecimal(interes))).divide(new BigDecimal("100"));   
        BigDecimal nueva_cuota = gasto_interes.add(cuota_total);
        //BigDecimal nuevo_gasto = (nueva_cuota.multiply(new BigDecimal("10"))).divide(new BigDecimal("100"));
        ac.cuota_total.setText(nueva_cuota.toString());
        //ac.gastos.setText(nuevo_gasto.toString());
        }
        else {
            ac.cuota_total.setText(cuota_total.toString());  
            ac.gastos.setText(gastos.toString());  
        }
    }
    //}     
}
