/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.CuotaDAO;
import Modelo.DchoPosesionDAO;
import Modelo.FichaControlDAO;
import Clases.LimitadorCaracteres;
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
    FichaControlDAO fc = new FichaControlDAO();
    DchoPosesionDAO dp = new DchoPosesionDAO();
    AltaCuota ac;
    int id_control, nro_cuotas;
    int row_count;
    BigDecimal cuota_total;
    BigDecimal gastos;
    private int filas_insertadas=0;
    static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ControladorCliente.class.getName());

    public ControladorAltaPago(Frame parent, int id_control, int row_count, int nro_cuotas) {
        this.parent = parent;
        this.id_control=id_control;
        this.row_count=row_count;
        this.nro_cuotas=nro_cuotas;
        ac = new AltaCuota(parent, true);
        ac.aviso.setVisible(false);
        ac.tipo_cuota.add(ac.chk_cuota);
        ac.tipo_cuota.add(ac.chk_dcho_posesion);
        ac.tipo_cuota.add(ac.chk_adelanto_cuota);
        ac.chk_cuota.addActionListener(this);
        ac.chk_dcho_posesion.addActionListener(this);
        ac.chk_adelanto_cuota.addActionListener(this);
        ac.chk_cuota.setSelected(true);
        ac.nro_cuota.setDocument(new LimitadorCaracteres(8));
        ac.detallePago.setDocument(new LimitadorCaracteres(40));
        ac.observacionesPago.setDocument(new LimitadorCaracteres(40));
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
        ac.porcentaje_gastos.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                nuevoGasto();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                nuevoGasto();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        ac.interes.addKeyListener(this);
        ac.nro_cuota.addKeyListener(this);
        ac.aceptarBtn.addActionListener(this);
        ac.cancelarBtn.addActionListener(this);
        rellenarCampos();
    }
    
    public void nuevoGasto(){
        if(!ac.cuota_total.getText().equals("")){
            if(!ac.porcentaje_gastos.getText().equals("")){
                BigDecimal gasto;
                BigDecimal cuota_total = new BigDecimal(ac.cuota_total.getText());
                 if(ac.chk_cuota.isSelected()){
                     gasto = (cuota_total.multiply(new BigDecimal(ac.porcentaje_gastos.getText()))).divide(new BigDecimal("100"),2, BigDecimal.ROUND_HALF_UP);
//                    gasto = cuota_total.subtract(cuota_total.divide((new BigDecimal(ac.porcentaje_gastos.getText()).divide(new BigDecimal(100))), 2, BigDecimal.ROUND_HALF_UP));
                 }else{
                   gasto = (cuota_total.multiply(new BigDecimal("10"))).divide(new BigDecimal("100"),2, BigDecimal.ROUND_HALF_UP);
                 }ac.gastos.setText(gasto.toString());
            }else{
                ac.gastos.setText("");
            }
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
            ac.cuota_total.setText(cuota_total.toString());
            nuevoGasto();
            //------Calculo el nro de cuota--------//
            int cuota=0;
            ResultSet rscuota = cd.getNrosCuotas(id_control);
            rscuota.next();
            cuota = rscuota.getInt(1);                     
               while(rscuota.next()&& rscuota.getInt(1)-1==cuota){
                  cuota=rscuota.getInt(1);                           
                  }
            ac.nro_cuota.setText(String.valueOf(cuota+1));
            //---------------------//
            ac.setVisible(true);
            
        } catch (SQLException ex) {
            Logger.getLogger(ControladorAltaPago.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
           if(e.getSource() == ac.aceptarBtn){
              altaPago();
           }
            if(e.getSource() == ac.cancelarBtn){
               ac.dispose();
           }
            if(e.getSource() == ac.chk_cuota){
                nuevoGasto();
                ac.nro_cuota.setEnabled(true);
            }
            if(e.getSource() == ac.chk_dcho_posesion){
                nuevoGasto();
                ac.nro_cuota.setEnabled(false);
            }
            if(e.getSource()==ac.chk_adelanto_cuota){
              ac.nro_cuota.setEnabled(false);
            }            
    }
    
    
    public void calcularValores(BigDecimal ultimo_saldo, BigDecimal cuota_pura, BigDecimal gastos, BigDecimal bolsa_cemento, BigDecimal saldo_bolsa_cemento){  
               Date date = new Date();
               String detalle = ac.detallePago.getText();
               String observaciones = ac.observacionesPago.getText();
               String tipoPago = ac.tipoPago.getSelectedItem().toString();
               BigDecimal haber = cuota_pura.add(gastos);
               BigDecimal saldo_actual = ultimo_saldo.subtract(haber);
               BigDecimal cemento_haber = haber.divide(bolsa_cemento, 2, RoundingMode.DOWN);
               BigDecimal cemento_saldo = saldo_bolsa_cemento.subtract(cemento_haber);
               if(ac.chk_adelanto_cuota.isSelected()){
               //-------Miro si la ultima cuota ya existe, si el lote es de 180 cuotas, miro si ya hizo adelanto de cuotas entonces puede tener la cuota 180------//  
                  if(cd.getUltimaCuota(nro_cuotas)){
                   try {
                       ResultSet rs = cd.getNrosCuotas(id_control);
                       //========Avanzo la cuota cero========//
                       rs.next();
                       int cuota = rs.getInt(1);
                       rs.next();
                       while(rs.getInt(1)-1==cuota && !rs.isAfterLast()){
                           cuota=rs.getInt(1);
                           rs.next();
                       }
                       filas_insertadas = cd.altaCuotaLote(new java.sql.Date(date.getTime()),rs.getInt(1)-1, detalle, cuota_pura, gastos, new BigDecimal(0), haber, saldo_actual, new BigDecimal(0), cemento_haber, cemento_saldo, observaciones, tipoPago, id_control);  
                       
                   } catch (SQLException ex) {                       
                      log.equals(ex.getMessage());
                       Logger.getLogger(ControladorAltaPago.class.getName()).log(Level.SEVERE, null, ex);
                   }
                  }else{
                     filas_insertadas = cd.altaCuotaLote(new java.sql.Date(date.getTime()),nro_cuotas, detalle, cuota_pura, gastos, new BigDecimal(0), haber, saldo_actual, new BigDecimal(0), cemento_haber, cemento_saldo, observaciones, tipoPago, id_control);                    
                  }
               }else{
                   //                       int cuota=0;
//                       ResultSet rs = cd.getNrosCuotas(id_control);
//                       rs.next();
//                       cuota = rs.getInt(1);
//                       //-----Verifico que sea la primer cuota-------//                       
//                         while(rs.next()&& rs.getInt(1)-1==cuota){
//                           cuota=rs.getInt(1);                           
//                         }
//                         ac.nro_cuota.setText(String.valueOf(cuota)+1);
                    filas_insertadas = cd.altaCuotaLote(new java.sql.Date(date.getTime()),Integer.parseInt(ac.nro_cuota.getText()), detalle, cuota_pura, gastos, new BigDecimal(0), haber, saldo_actual, new BigDecimal(0), cemento_haber, cemento_saldo, observaciones, tipoPago, id_control);  
               }
               if (filas_insertadas==1) {
                   ac.dispose();
                   filas_insertadas=0;
               }else{
                   ac.aviso.setVisible(true);
               }
              
    }
    
    public void altaPago(){
      if(ac.chk_dcho_posesion.isSelected()){ 
                if(validarCampos()){
                  Date date = new Date();     
                  ResultSet rs = fc.obtenerFichaControl(id_control);
                  ResultSet dpd = dp.listarCuenta(id_control);
                    try {
                        rs.next();
                        dpd.last();
                        BigDecimal bolsa_cemento = new BigDecimal(rs.getString(5));
                        BigDecimal cemento_saldo = new BigDecimal(dpd.getString(6));
                        BigDecimal cant_bolsa = new BigDecimal(ac.cuota_total.getText()).divide(bolsa_cemento, 2, RoundingMode.DOWN);
                        dp.altaDchoPosesion(new java.sql.Date(date.getTime()), new BigDecimal(ac.cuota_total.getText()),new BigDecimal(ac.gastos.getText()),new BigDecimal(0),cant_bolsa, cemento_saldo.subtract(cant_bolsa),ac.detallePago.getText(), id_control);
                    } catch (SQLException ex) {
                        Logger.getLogger(ControladorAltaPago.class.getName()).log(Level.SEVERE, null, ex);
                    }
                ac.dispose();
                }
             //--------Es cuota comun----------/     
              }else{
               if(validarCampos()){
               ResultSet rs = fc.obtenerFichaControl(id_control);
               ResultSet rs_cuota = cd.listaDetalleCuotaXsaldo(id_control);
               try {
                   rs.next();
                   rs_cuota.last();
                   BigDecimal ultimo_saldo = new BigDecimal(rs_cuota.getString(8));
                   BigDecimal cuota_pura = new BigDecimal(ac.cuota_total.getText()).subtract(new BigDecimal(ac.gastos.getText()));
                   BigDecimal gastos = new BigDecimal(ac.gastos.getText());
                   BigDecimal bolsa_cemento = new BigDecimal(rs.getString(5));
                   BigDecimal ultimo_saldo_bolsa_cemento = new BigDecimal(rs_cuota.getString(11));
                   calcularValores(ultimo_saldo, cuota_pura, gastos, bolsa_cemento, ultimo_saldo_bolsa_cemento);             
                } catch (SQLException ex) {
               }
             }             
          }
    }
    
     public boolean validarCampos(){
        boolean bandera = true;       
        if(ac.cuota_total.getText().isEmpty()){
         ac.cuota_total.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         ac.cuota_total.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(ac.gastos.getText().isEmpty()){
         ac.gastos.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         ac.gastos.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(ac.chk_cuota.isSelected()){
         if(ac.nro_cuota.getText().isEmpty()){
          ac.nro_cuota.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
          bandera=false;
         }else{
           ac.nro_cuota.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
         }
        }
        return bandera;
     }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER){ 
         if(e.getSource()==ac.nro_cuota){
           altaPago();
         }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getSource()==ac.interes){
        String interes = ac.interes.getText();
        if(!interes.equals("")){
          BigDecimal gasto_interes = (cuota_total.multiply(new BigDecimal(interes))).divide(new BigDecimal("100"));   
          BigDecimal nueva_cuota = gasto_interes.add(cuota_total);
          ac.cuota_total.setText(nueva_cuota.toString());
        }else {
          ac.cuota_total.setText(cuota_total.toString());   
        }
    }
    }     
}
