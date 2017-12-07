/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ClienteDAO;
import Modelo.CuotaDAO;
import Modelo.DchoPosesionDAO;
import Modelo.DepartamentoDAO;
import Modelo.FichaControlDAO;
import Modelo.LoteDAO;
import Modelo.PropiedadesDAO;
import Modelo.PropietarioDAO;
import Vista.Dialogs.AsignarPropiedad;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author Marcelo
 */
public class ControladorAsignacionPropiedad implements ActionListener, KeyListener{
    
    private Frame parent;    
    AsignarPropiedad vistaAsignarPropiedad ;
    LoteDAO ld = new LoteDAO();
    DepartamentoDAO dd = new DepartamentoDAO();
    PropietarioDAO pd = new PropietarioDAO();
    DchoPosesionDAO dp = new DchoPosesionDAO();
    FichaControlDAO fichaControlDAO = new FichaControlDAO();
    CuotaDAO cuotaDao = new CuotaDAO();
    ClienteDAO cd = new ClienteDAO();
    ControladorCliente cc = new ControladorCliente();
    int dni;

    public ControladorAsignacionPropiedad(Frame parent, int dni) {
        vistaAsignarPropiedad = new AsignarPropiedad(parent, true);
        this.parent=parent;
        this.dni=dni;
        this.vistaAsignarPropiedad.aceptarBtn.addActionListener(this);
        this.vistaAsignarPropiedad.cancelarBtn.addActionListener(this);
        this.vistaAsignarPropiedad.bolsa_cemento.addKeyListener(this);
        this.vistaAsignarPropiedad.tipo_propiedad.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                llenarComboApellidos(vistaAsignarPropiedad.tipo_propiedad.getSelectedItem().toString());
            }
        });
        this.vistaAsignarPropiedad.apellido_propietario.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
               //-----Solo llamo al evento cuando un item es seleccionado en el combobox, no cuando es cargado o eliminado----//  
              if (e.getStateChange() == ItemEvent.SELECTED) {
                if(vistaAsignarPropiedad.apellido_propietario.getSelectedItem().toString()!=("Seleccione") && vistaAsignarPropiedad.tipo_propiedad.getSelectedItem().toString()!=("Seleccione") );
                 llenarComboNombres(vistaAsignarPropiedad.tipo_propiedad.getSelectedItem().toString(), vistaAsignarPropiedad.apellido_propietario.getSelectedItem().toString());
            }}
        });
        this.vistaAsignarPropiedad.nombre_propietario.addItemListener(new ItemListener() {            
            @Override
            public void itemStateChanged(ItemEvent e) {
                //-----Solo llamo al evento cuando un item es seleccionado, no cuando es cargado o eliminado----//
              if (e.getStateChange() == ItemEvent.SELECTED) {
                switch (vistaAsignarPropiedad.tipo_propiedad.getSelectedItem().toString()){
                case "Terreno": cargarBarrios(vistaAsignarPropiedad.apellido_propietario.getSelectedItem().toString(), vistaAsignarPropiedad.nombre_propietario.getSelectedItem().toString()); break;
                case "Departamento": cargarTorres(vistaAsignarPropiedad.apellido_propietario.getSelectedItem().toString(), vistaAsignarPropiedad.nombre_propietario.getSelectedItem().toString());break;
              }
             }
            }
        });
        this.vistaAsignarPropiedad.barrio.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                cargarManzanas(vistaAsignarPropiedad.barrio.getSelectedItem().toString());
                }
            }
        });
        this.vistaAsignarPropiedad.manzana.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                  if(vistaAsignarPropiedad.manzana.getSelectedItem() != null){
                cargarParcelas(vistaAsignarPropiedad.barrio.getSelectedItem().toString(), Integer.parseInt(vistaAsignarPropiedad.manzana.getSelectedItem().toString()));
                  }
                }
             }
        });     
        llenarComboApellidos(vistaAsignarPropiedad.tipo_propiedad.getSelectedItem().toString());
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
    
     public void llenarComboApellidos(String propiedad){
        try {
            ResultSet rs=null;
            switch (propiedad){
                case "Terreno": rs = pd.obtenerApellidosXLote(); break;
                case "Departamento": rs = pd.obtenerApellidosXDepartamento();break;
            }
            vistaAsignarPropiedad.apellido_propietario.removeAllItems();
            vistaAsignarPropiedad.apellido_propietario.addItem("Seleccione");
            if(rs!=null){
            while (rs.next()) {
                vistaAsignarPropiedad.apellido_propietario.addItem(rs.getString(1));                
            }  
            rs.close();}
        } catch (SQLException ex) {
            Logger.getLogger(ControladorPropiedades.class.getName()).log(Level.SEVERE, null, ex);
        }   
 } 
     
      public void llenarComboNombres(String propiedad, String apellido){
        try {
            ResultSet rs = null;
            switch (propiedad){
                case "Terreno": rs = pd.obtenerNombresXLote(apellido); break;
                case "Departamento": rs = pd.obtenerNombresXDepartamento(apellido);break;
            }
            vistaAsignarPropiedad.nombre_propietario.removeAllItems();
            vistaAsignarPropiedad.nombre_propietario.addItem("Seleccione");
            if(rs!=null){
            while (rs.next()) {
                vistaAsignarPropiedad.nombre_propietario.addItem(rs.getString(1));                
            }  
            rs.close();}
        } catch (SQLException ex) {
            Logger.getLogger(ControladorPropiedades.class.getName()).log(Level.SEVERE, null, ex);
        }   
 } 
    
    public void cargarBarrios(String apellidos, String nombres){
     ResultSet rs = ld.obtenerBarrios(apellidos, nombres);
      vistaAsignarPropiedad.barrio.removeAllItems();
        try {
            if(rs.next()){
                vistaAsignarPropiedad.barrio.addItem(rs.getString(1));
            while(rs.next()){
                vistaAsignarPropiedad.barrio.addItem(rs.getString(1));
            }}else{
               vistaAsignarPropiedad.barrio.addItem("Sin propiedades");
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
    
    public void cargarTorres(String apellidos, String nombres){
     ResultSet rs = dd.obtenerTorres(apellidos, nombres);
      vistaAsignarPropiedad.barrio.removeAllItems();
        try {
            while(rs.next()){
                vistaAsignarPropiedad.barrio.addItem(rs.getString(1));
            }
        } catch (Exception e) {
        }
    }
    public void cargarPisos(String torre){
        ResultSet rs = dd.pisosPortorre(torre);        
        vistaAsignarPropiedad.manzana.removeAllItems();
        try {
            while(rs.next()){
                vistaAsignarPropiedad.manzana.addItem(rs.getString(1));
            }
        } catch (Exception e) {
        }
    }
    public void cargarNroDptos(String torre, int piso){
         ResultSet rs = dd.dptosPorpiso(torre, piso);
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
         if(vistaAsignarPropiedad.bolsa_cemento.getText().isEmpty()){
         vistaAsignarPropiedad.bolsa_cemento.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         vistaAsignarPropiedad.bolsa_cemento.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        if(vistaAsignarPropiedad.fch_suscripción.getDate()== null){
         vistaAsignarPropiedad.fch_suscripción.setBorder(BorderFactory.createLineBorder(Color.RED));
         bandera=false;
        }else{
         vistaAsignarPropiedad.fch_suscripción.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        }
        return bandera;
     }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_ENTER){
            vistaAsignarPropiedad.aceptarBtn.doClick();
         }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
     
     public class SwingWorker extends javax.swing.SwingWorker<Void, Void>{
         
         int id_control;

        @Override
        protected Void doInBackground() throws Exception {            
           BigDecimal gastos =(new BigDecimal(vistaAsignarPropiedad.cuota_total.getText()).subtract(new BigDecimal(vistaAsignarPropiedad.cuota_total.getText()))).divide(new BigDecimal(1.1));
           id_control = fichaControlDAO.altaFichaControl(vistaAsignarPropiedad.tipo_propiedad.getSelectedItem().toString(), vistaAsignarPropiedad.dimension.getText(), Integer.parseInt(vistaAsignarPropiedad.cantidad_cuotas.getText()),  new BigDecimal(vistaAsignarPropiedad.cuota_total.getText()).subtract(gastos), gastos, new BigDecimal(vistaAsignarPropiedad.bolsa_cemento.getText()), new java.sql.Date(vistaAsignarPropiedad.fch_suscripción.getDate().getTime()),String.valueOf(vistaAsignarPropiedad.barrio.getSelectedItem()),Integer.parseInt((String)vistaAsignarPropiedad.manzana.getSelectedItem()), Integer.parseInt((String)vistaAsignarPropiedad.parcela.getSelectedItem()));
           BigDecimal saldo = new BigDecimal(vistaAsignarPropiedad.cantidad_cuotas.getText()).multiply(new BigDecimal(vistaAsignarPropiedad.cuota_total.getText()));
           switch(vistaAsignarPropiedad.tipo_propiedad.getSelectedItem().toString()){
               case "Terreno":cuotaDao.altaCuotaLote(new java.sql.Date(vistaAsignarPropiedad.fch_suscripción.getDate().getTime()), 0,"Saldo Inicio", new BigDecimal(0),new BigDecimal(0), saldo , new BigDecimal(0), saldo, saldo.divide(new BigDecimal(vistaAsignarPropiedad.bolsa_cemento.getText())), new BigDecimal(0), saldo.divide(new BigDecimal(vistaAsignarPropiedad.bolsa_cemento.getText())), "", "", id_control); break;
               case "Departamento":cuotaDao.altaCuotaDpto(new java.sql.Date(vistaAsignarPropiedad.fch_suscripción.getDate().getTime()), 0,"Saldo Inicio", new BigDecimal(0),new BigDecimal(0), saldo , new BigDecimal(0), saldo, saldo.divide(new BigDecimal(vistaAsignarPropiedad.bolsa_cemento.getText())), new BigDecimal(0), saldo.divide(new BigDecimal(vistaAsignarPropiedad.bolsa_cemento.getText())), "", "", id_control);  break;
           }
           dp.altaDchoPosesion(new java.sql.Date(vistaAsignarPropiedad.fch_suscripción.getDate().getTime()), new BigDecimal(0),new BigDecimal(0), new BigDecimal(50000).divide(new BigDecimal(vistaAsignarPropiedad.bolsa_cemento.getText()), 2, RoundingMode.DOWN), new BigDecimal(0),new BigDecimal(50000).divide(new BigDecimal(vistaAsignarPropiedad.bolsa_cemento.getText()), 2, RoundingMode.DOWN), "Saldo Inicio", id_control);
           return null;
        }

       @Override
       public void done() { 
           switch(vistaAsignarPropiedad.tipo_propiedad.getSelectedItem().toString()){
               case "Terreno":ld.editarPropiedad(1, vistaAsignarPropiedad.barrio.getSelectedItem().toString(), Integer.parseInt(vistaAsignarPropiedad.manzana.getSelectedItem().toString()), Integer.parseInt(vistaAsignarPropiedad.parcela.getSelectedItem().toString())); cd.altaClientesXLotes(dni, id_control); break;
               case "Departamento":dd.editarDepartamento(vistaAsignarPropiedad.barrio.getSelectedItem().toString(), Integer.parseInt(vistaAsignarPropiedad.manzana.getSelectedItem().toString()), Integer.parseInt(vistaAsignarPropiedad.parcela.getSelectedItem().toString()));cd.altaClientesXDpto(dni, id_control); break;
           }           
           vistaAsignarPropiedad.dispose();
       }
    
}
     
}
