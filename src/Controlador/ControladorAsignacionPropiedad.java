/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.CuotaDAO;
import Modelo.FichaControlDAO;
import Modelo.LoteDAO;
import Vista.Dialogs.AsignarPropiedad;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.sql.ResultSet;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author Marcelo
 */
public class ControladorAsignacionPropiedad implements ActionListener{
    
    private Frame parent;    
    AsignarPropiedad vistaAsignarPropiedad ;
    LoteDAO ld = new LoteDAO();
    FichaControlDAO fichaControlDAO = new FichaControlDAO();
    CuotaDAO cuotaDao = new CuotaDAO();
    ControladorCliente cc = new ControladorCliente();
    int dni;

    public ControladorAsignacionPropiedad(Frame parent, int dni) {
        vistaAsignarPropiedad = new AsignarPropiedad(parent, true);
        this.parent=parent;
        this.dni=dni;
        this.vistaAsignarPropiedad.aceptarBtn.addActionListener(this);
        this.vistaAsignarPropiedad.cancelarBtn.addActionListener(this);
        this.vistaAsignarPropiedad.barrio.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                cargarManzanas(vistaAsignarPropiedad.barrio.getSelectedItem().toString());
            }
        });
        this.vistaAsignarPropiedad.manzana.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(vistaAsignarPropiedad.manzana.getSelectedItem() != null){
                cargarParcelas(vistaAsignarPropiedad.barrio.getSelectedItem().toString(), Integer.parseInt(vistaAsignarPropiedad.manzana.getSelectedItem().toString()));
                }
                }
        });
        cargarBarrios();        
        vistaAsignarPropiedad.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
          if(e.getSource() == vistaAsignarPropiedad.aceptarBtn){
              if(validarCampos() == true){                  
                  new SwingWorker().execute();                
                  }
          }
          if(e.getSource() == vistaAsignarPropiedad.cancelarBtn){
             vistaAsignarPropiedad.dispose();
          }
    }
    
    public void cargarBarrios(){
     ResultSet rs = ld.obtenerBarrios();
        try {
            while(rs.next()){
                vistaAsignarPropiedad.barrio.addItem(rs.getString(1));
            }
        } catch (Exception e) {
        }
    }
    public void cargarManzanas(String barrio){
        ResultSet rs = ld.manzanasPorBarrio(barrio);        
        vistaAsignarPropiedad.manzana.removeAllItems();
        try {
            while(rs.next()){
                vistaAsignarPropiedad.manzana.addItem(rs.getString(1));
            }
        } catch (Exception e) {
        }
    }
    public void cargarParcelas(String barrio, int manzana){
         ResultSet rs = ld.parcelasPorManzana(barrio, manzana);
        vistaAsignarPropiedad.parcela.removeAllItems();
        try {
            while(rs.next()){
                vistaAsignarPropiedad.parcela.addItem(rs.getString(1));
            }
        } catch (Exception e) {
        }
    }
     
     public boolean validarCampos(){
        boolean bandera = true;
       
        if(vistaAsignarPropiedad.cantidad_cuotas.getText().isEmpty()){
         vistaAsignarPropiedad.cantidad_cuotas.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         vistaAsignarPropiedad.cantidad_cuotas.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(vistaAsignarPropiedad.cuota_total.getText().isEmpty()){
         vistaAsignarPropiedad.cuota_total.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         vistaAsignarPropiedad.cuota_total.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }        
        if(vistaAsignarPropiedad.dimension.getText().isEmpty()){
         vistaAsignarPropiedad.dimension.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         vistaAsignarPropiedad.dimension.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        } 
        return bandera;
     }
     
     public class SwingWorker extends javax.swing.SwingWorker<Void, Void>{
         
         int id_control;

        @Override
        protected Void doInBackground() throws Exception {            
           double gastos =Double.parseDouble(vistaAsignarPropiedad.cuota_total.getText())-(Double.parseDouble(vistaAsignarPropiedad.cuota_total.getText())/1.1);
            id_control = fichaControlDAO.altaFichaControl(vistaAsignarPropiedad.tipo_propiedad.getSelectedItem().toString(), vistaAsignarPropiedad.dimension.getText(), Integer.parseInt(vistaAsignarPropiedad.cantidad_cuotas.getText()),  Double.parseDouble(vistaAsignarPropiedad.cuota_total.getText())-gastos, gastos, Double.parseDouble(vistaAsignarPropiedad.bolsa_cemento.getText()), dni, String.valueOf(vistaAsignarPropiedad.barrio.getSelectedItem()),Integer.parseInt((String)vistaAsignarPropiedad.manzana.getSelectedItem()), Integer.parseInt((String)vistaAsignarPropiedad.parcela.getSelectedItem()));
            cuotaDao.altaCuota(new Date(System.currentTimeMillis()), 0,"Saldo Inicio", 0, 0, Double.parseDouble(vistaAsignarPropiedad.cantidad_cuotas.getText())*(Double.parseDouble(vistaAsignarPropiedad.cuota_total.getText())), 0, Double.parseDouble(vistaAsignarPropiedad.cantidad_cuotas.getText())*(Double.parseDouble(vistaAsignarPropiedad.cuota_total.getText())), Double.parseDouble(vistaAsignarPropiedad.bolsa_cemento.getText())*Double.parseDouble(vistaAsignarPropiedad.cantidad_cuotas.getText()), 0, Double.parseDouble(vistaAsignarPropiedad.bolsa_cemento.getText())*Double.parseDouble(vistaAsignarPropiedad.cantidad_cuotas.getText()), "", "", id_control);         
            return null;
        }

       @Override
       public void done() { 
           ld.editarPropiedad(vistaAsignarPropiedad.barrio.getSelectedItem().toString(), Integer.parseInt(vistaAsignarPropiedad.manzana.getSelectedItem().toString()) , Integer.parseInt(vistaAsignarPropiedad.parcela.getSelectedItem().toString()));
           vistaAsignarPropiedad.dispose();
       }
    
}
     
}
