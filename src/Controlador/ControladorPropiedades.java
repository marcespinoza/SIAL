/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.PropiedadesDAO;
import Modelo.PropietarioDAO;
import Vista.Dialogs.Configuracion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcelo Espinoza
 */
public class ControladorPropiedades implements ActionListener{
    
    Configuracion vistaConfiguracion;
    PropietarioDAO pd = new PropietarioDAO();
    PropiedadesDAO prod = new PropiedadesDAO();
    private Object [] propiedades;
    String apellidos, nombres, cuit;

    public ControladorPropiedades(Configuracion vistaConfiguracion) {
        this.vistaConfiguracion=vistaConfiguracion;
        this.vistaConfiguracion.propiedades.comboApellido.addActionListener(this);
        this.vistaConfiguracion.propiedades.comboNombres.addActionListener(this);
        this.vistaConfiguracion.propiedades.agregar.addActionListener(this);
        this.vistaConfiguracion.propiedades.eliminar.addActionListener(this);
        //ocultarColumnas();
        llenarComboApellidos();
    }
    
 public void llenarComboApellidos(){
        try {
            ResultSet rs = pd.obtenerApellidos();
            while (rs.next()) {
                vistaConfiguracion.propiedades.comboApellido.addItem(rs.getString(1));                
            }  } catch (SQLException ex) {
            Logger.getLogger(ControladorPropiedades.class.getName()).log(Level.SEVERE, null, ex);
        }   
 }   
  public void llenarComboNombres(String apellidos){
        try {
            ResultSet rs = pd.obtenerNombres(apellidos);
            while (rs.next()) {
                vistaConfiguracion.propiedades.comboNombres.addItem(rs.getString(1));
            }  } catch (SQLException ex) {
            Logger.getLogger(ControladorPropiedades.class.getName()).log(Level.SEVERE, null, ex);
        }   
 }
 
  public void llenarTabla(){
        ResultSet rs = prod.obtenerPropiedades(apellidos, nombres);
        DefaultTableModel model = (DefaultTableModel) vistaConfiguracion.propiedades.tablaPropiedades.getModel();
        model.setRowCount(0);
        try {
            while(rs.next()){
                System.out.println(rs.getString(1)+"de");
                String barrio = rs.getString(1);  
                String manzana = rs.getString(2);
                String parcela = rs.getString(3);
                String vendido = rs.getString(4);
                propiedades = new Object[] {barrio, manzana, parcela, vendido};
                model.addRow(propiedades);
            }
          
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==vistaConfiguracion.propiedades.comboApellido){
            if(!vistaConfiguracion.propiedades.comboApellido.getSelectedItem().equals("Seleccione")){  
                apellidos =vistaConfiguracion.propiedades.comboApellido.getSelectedItem().toString();
                llenarComboNombres(apellidos);
            }
        }
         if(e.getSource()==vistaConfiguracion.propiedades.comboNombres){
            if(!vistaConfiguracion.propiedades.comboNombres.getSelectedItem().equals("Seleccione")){ 
                nombres = vistaConfiguracion.propiedades.comboNombres.getSelectedItem().toString();
                llenarTabla();
            }
        }
        if(e.getSource()==vistaConfiguracion.propiedades.agregar){
            if(!vistaConfiguracion.propiedades.comboApellido.getSelectedItem().equals("Seleccione")){  
               if(!vistaConfiguracion.propiedades.comboNombres.getSelectedItem().equals("Seleccione")){  
                   //prod.agregarPropiedad(barrio, manzana, parcela, vendido, vistaConfiguracion.propiedades.comboApellido.getSelectedItem(), vistaConfiguracion.propiedades.comboNombres.getSelectedItem(), propietario_cuit);
            }
            }
        }
        if(e.getSource()==vistaConfiguracion.propiedades.eliminar){}
    }
    
    private void ocultarColumnas(){
          vistaConfiguracion.propiedades.tablaPropiedades.getColumnModel().getColumn(4).setMinWidth(0);
          vistaConfiguracion.propiedades.tablaPropiedades.getColumnModel().getColumn(4).setMaxWidth(0);
          vistaConfiguracion.propiedades.tablaPropiedades.getColumnModel().getColumn(4).setWidth(0);
          vistaConfiguracion.propiedades.tablaPropiedades.getColumnModel().getColumn(5).setMinWidth(0);
          vistaConfiguracion.propiedades.tablaPropiedades.getColumnModel().getColumn(5).setMaxWidth(0);
          vistaConfiguracion.propiedades.tablaPropiedades.getColumnModel().getColumn(5).setWidth(0); 
          vistaConfiguracion.propiedades.tablaPropiedades.getColumnModel().getColumn(6).setMinWidth(0);
          vistaConfiguracion.propiedades.tablaPropiedades.getColumnModel().getColumn(6).setMaxWidth(0);
          vistaConfiguracion.propiedades.tablaPropiedades.getColumnModel().getColumn(6).setWidth(0); 
    }
    
}
