/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Clases.Cuota;
import Clases.FichaDeControl;
import static Controlador.ControladorAltaCuota.log;
import Modelo.ActualizacionEmpleadoDAO;
import Modelo.CuotaDAO;
import Modelo.FichaControlDAO;
import Vista.Dialogs.ActualizarCuota;
import Vista.Dialogs.ActualizarCuotaSaldo;
import Vista.Dialogs.Progress;
import Vista.Frame.Ventana;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Marceloi7
 */
public class ControladorActualizarCuotaSaldo implements ActionListener{
    
    ActualizarCuotaSaldo acs;
    FichaControlDAO fc = new FichaControlDAO();
    ActualizacionEmpleadoDAO ad =new ActualizacionEmpleadoDAO();
    Ventana ventana;
    int id_control, cuota;
    FichaControlDAO fcd = new FichaControlDAO();
    CuotaDAO cd = new CuotaDAO();
    BigDecimal cdc, precio_bc, nueva_cuota, gastos, cuota_pura, nuevo_saldo, cemento_saldo;

    public ControladorActualizarCuotaSaldo(Ventana ventana, int id_control) {
        this.ventana=ventana;
        this.id_control=id_control;
        acs = new ActualizarCuotaSaldo(ventana, true);
        acs.actualizar.addActionListener(this);
        acs.cancelar.addActionListener(this);
        acs.setLocationRelativeTo(null);
        calcularValores();
    }
    
    public void calcularValores(){
         List<Cuota> lcuotas = new ArrayList<>();
         List<Cuota>listaC;
         List<FichaDeControl> listaFichaControl = fcd.obtenerFichaControl(id_control);
         lcuotas = cd.listaDetalleCuotaXsaldo(id_control); 
         BigDecimal cbc =  listaFichaControl.get(0).getCantidad_bc();
         precio_bc = listaFichaControl.get(0).getBolsaCemento();
         cemento_saldo =  lcuotas.get(lcuotas.size()-1).getCemento_saldo();
         nueva_cuota = cbc.multiply(precio_bc);
         gastos = nueva_cuota.subtract((nueva_cuota).divide(new BigDecimal(1.1),2, BigDecimal.ROUND_HALF_UP));
         cuota_pura = nueva_cuota.subtract(gastos);
         //------Calculo el nro de cuota--------//
        cuota = 0;
        int indice = 0;
        listaC = cd.getNrosCuotas(id_control);
        if(!listaC.isEmpty()){
          cuota = listaC.get(indice).getNro_cuota();
          indice = indice + 1;
          while(indice < listaC.size()&& (listaC.get(indice).getNro_cuota()-1==cuota || listaC.get(indice).getNro_cuota()-1 < cuota)){
           cuota=listaC.get(indice).getNro_cuota();
           indice = indice + 1;
          }
        }
        int cant_cuotas = 180-(listaC.size()-1);
        nuevo_saldo = nueva_cuota.multiply(new BigDecimal(cant_cuotas));
        acs.cuotaActualizada.setText(String.valueOf(nueva_cuota.setScale(2, RoundingMode.HALF_UP)));
        acs.saldoActualizado.setText(String.valueOf(nuevo_saldo.setScale(2, RoundingMode.HALF_UP)));
        acs.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== acs.actualizar){
           actualizarSaldo();
        }
        if(e.getSource() == acs.cancelar){
            acs.dispose();
        }
    }

    public void actualizarSaldo(){
         SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           String dateString = format.format( new Date()   );
           Date date = null;  
            try {
            date = format.parse ( dateString );
            } catch (ParseException ex) {
            Logger.getLogger(ControladorAltaCuota.class.getName()).log(Level.SEVERE, null, ex);
             }
        int filas_insertadas = cd.altaCuotaLote(new java.sql.Date(date.getTime()),cuota, "ACTUALIZACION ", cuota_pura, gastos, new BigDecimal(0), BigDecimal.ZERO, nuevo_saldo, new BigDecimal(0), BigDecimal.ZERO, cemento_saldo, "ACTUALIZACION", "", id_control, 1);  
        fc.actualizarValorCuota(gastos, cuota_pura, id_control);
   
         if (filas_insertadas==1) {
               acs.dispose();
               filas_insertadas=0;
               log.info(Ventana.nombreUsuario.getText() + " - Alta pago");
         } 
    }
    
    
}
