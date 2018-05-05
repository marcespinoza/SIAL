
package Controlador;

import Modelo.CuotaDAO;
import Modelo.FichaControlDAO;
import Vista.Dialogs.ActualizarCuota;
import Vista.Frame.Ventana;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author Marceloi7
 */
public class ControladorActualizarCuota implements ActionListener{
    
    private int id_control, nro_cuota;
    Ventana ventana;
    ActualizarCuota ac;
    FichaControlDAO fc = new FichaControlDAO();
    CuotaDAO cd = new CuotaDAO();
    BigDecimal cuota_pura, gastos;

    public ControladorActualizarCuota(Ventana ventana, int id_control, int nro_cuota, BigDecimal cuota_pura, BigDecimal gastos) {
        this.ventana=ventana;
        this.id_control=id_control;
        ac = new ActualizarCuota(ventana, true);
        ac.aceptarBtn.addActionListener(this);
        ac.cancelarBtn.addActionListener(this);
        this.id_control=id_control;
        this.nro_cuota=nro_cuota;
        this.cuota_pura=cuota_pura;
        this.gastos=gastos;      
        ac.porcentaje_gastos.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char vchar = e.getKeyChar();
                 if(!Character.isDigit(vchar)&&vchar!='.' ){
                  e.consume();
                 } 
            }
            @Override
            public void keyReleased(KeyEvent e) {
                calcularGastos(); //To change body of generated methods, choose Tools | Templates.
            }            
         });
        //-------Evito que ingresen letras------------//
        ac.cuota_total.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char vchar = e.getKeyChar();
                 if(!Character.isDigit(vchar)&&vchar!='.' ){
                  e.consume();
                 } 
            }
            @Override
            public void keyReleased(KeyEvent e) {
                calcularGastos(); //To change body of generated methods, choose Tools | Templates.
            }               
        });
        ac.gastos.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char vchar = e.getKeyChar();
                 if(!Character.isDigit(vchar)&&vchar!='.' ){
                  e.consume();
                 } 
            }
            @Override
            public void keyReleased(KeyEvent e) {
                calcularGastos(); //To change body of generated methods, choose Tools | Templates.
            }   
        });
        //-------------------------//
        asignarValores();
        ac.setLocationRelativeTo(null);
        ac.setVisible(true);
    }
    
    private void calcularGastos(){
      if(!ac.cuota_total.getText().equals("")){
       if(!ac.porcentaje_gastos.getText().equals("")){   
         BigDecimal cuota_total = new BigDecimal(ac.cuota_total.getText());
         System.out.println(cuota_total.multiply(new BigDecimal(ac.porcentaje_gastos.getText()))+"llama");
         BigDecimal gasto = (cuota_total.multiply(new BigDecimal(ac.porcentaje_gastos.getText()))).divide(new BigDecimal("100"),2, BigDecimal.ROUND_HALF_UP);
         ac.gastos.setText(String.valueOf(gasto));
      }else{
         ac.gastos.setText("");
       }
      }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==ac.aceptarBtn){}
        if(e.getSource()==ac.cancelarBtn){
            ac.dispose();
        }
    }
    
    public void actualizarPago(){
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
    
         public void calcularValores(BigDecimal ultimo_saldo, BigDecimal cuota_pura, BigDecimal gastos, BigDecimal bolsa_cemento, BigDecimal saldo_bolsa_cemento){  
               Date date = new Date();
               BigDecimal haber = cuota_pura.add(gastos);
               BigDecimal saldo_actual = ultimo_saldo.subtract(haber);
               BigDecimal cemento_haber = haber.divide(bolsa_cemento, 2, RoundingMode.DOWN);
               BigDecimal cemento_saldo = saldo_bolsa_cemento.subtract(cemento_haber);
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
        return bandera;
     }

    private void asignarValores() {
       ac.cuota_total.setText(String.valueOf(cuota_pura.add(gastos)));
       ac.gastos.setText(String.valueOf(gastos));
    }
    
}
