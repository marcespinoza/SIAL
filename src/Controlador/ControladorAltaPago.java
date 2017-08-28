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
import java.util.Date;

/**
 *
 * @author Marcelo
 */
public class ControladorAltaPago implements ActionListener{
    
    Frame parent;
    CuotaDAO cd = new CuotaDAO();
    AltaCuota ac;
    int id_control;

    public ControladorAltaPago(Frame parent, int id_control) {
        this.parent = parent;
        this.id_control=this.id_control;
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
               try {
                   while(rs.next()){
                        double precio = Double.parseDouble(rs.getString(1));
                        double gastos = Double.parseDouble(rs.getString(2));
                        double bolsa_cemento = Double.parseDouble(rs.getString(3));
                        calcularValores(precio, gastos, bolsa_cemento);
                   }
                 
               } catch (SQLException ex) {
               }
           }
            if(e.getSource() == ac.cancelarBtn){
               ac.dispose();
           }
    }
    
    public void calcularValores(double precio, double gastos, double bolsa_cemento){          
               Date date = ac.fechaPago.getDate();
               String detalle = ac.detallePago.getText();
               String observaciones = ac.observacionesPago.getText();
               String tipoPago = ac.tipoPago.getSelectedItem().toString();
    }
        
}
