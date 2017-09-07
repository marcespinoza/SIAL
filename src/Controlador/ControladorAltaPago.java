/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.CuotaDAO;
import Modelo.FichaControlDAO;
import Vista.Dialogs.AltaCuota;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Marcelo
 */
public class ControladorAltaPago implements ActionListener{
    
    Frame parent;
    CuotaDAO cd = new CuotaDAO();
    AltaCuota ac;
    int id_control;
    int row_count;

    public ControladorAltaPago(Frame parent, int id_control, int row_count) {
        this.parent = parent;
        this.id_control=id_control;
        this.row_count=row_count;
        this.ac = new AltaCuota(parent, true);
        this.ac.aceptarBtn.addActionListener(this);
        this.ac.cancelarBtn.addActionListener(this);
        ac.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
           if(e.getSource() == ac.aceptarBtn){
               FichaControlDAO fcd = new FichaControlDAO();
               ResultSet rs = fcd.obtenerFichaControl(id_control);
               CuotaDAO cd = new CuotaDAO();
               ResultSet rs_cuota = cd.listaDetalleCuota(id_control);
               try {
                   rs.next();
                   rs_cuota.last();
                   double ultimo_saldo = Double.parseDouble(rs_cuota.getString(7));
                   double cuota_pura = Double.parseDouble(rs.getString(2));
                   double gastos = Double.parseDouble(rs.getString(3));
                   double bolsa_cemento = Double.parseDouble(rs.getString(4));
                   double ultimo_saldo_bolsa_cemento = Double.parseDouble(rs_cuota.getString(10));
                   calcularValores(ultimo_saldo, cuota_pura, gastos,bolsa_cemento, ultimo_saldo_bolsa_cemento);                  
                 
               } catch (SQLException ex) {
               }
           }
            if(e.getSource() == ac.cancelarBtn){
               ac.dispose();
           }
    }
    
    public void calcularValores(double ultimo_saldo, double cuota_pura, double gastos,double bolsa_cemento, double saldo_bolsa_cemento){          
               long fecha_pago = ac.fechaPago.getDate().getTime();
               String detalle = ac.detallePago.getText();
               String observaciones = ac.observacionesPago.getText();
               String tipoPago = ac.tipoPago.getSelectedItem().toString();
               double haber = cuota_pura + gastos;
               double saldo_actual = ultimo_saldo - haber;
               double cemento_haber = bolsa_cemento;
               double cemento_saldo = saldo_bolsa_cemento - bolsa_cemento;
               cd.altaCuota(new java.sql.Date(fecha_pago),0, detalle, cuota_pura, gastos, 0, haber, saldo_actual, 0, cemento_haber, cemento_saldo, observaciones, tipoPago, id_control);
               ac.dispose();
    }
        
}
