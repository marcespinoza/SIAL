/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.PropietarioDAO;
import Vista.Dialogs.Configuracion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcelo Espinoza
 */
public class ControladorPropiedades {
    
    Configuracion vistaConfiguracion;
    PropietarioDAO pd = new PropietarioDAO();

    public ControladorPropiedades(Configuracion vistaConfiguracion) {
        this.vistaConfiguracion=vistaConfiguracion;
        llenarCombo();
    }
    
 public void llenarCombo(){
        try {
            ResultSet rs = pd.obtenerPropietarios();
            while (rs.next()) {
                vistaConfiguracion.propiedades.comboPropietarios.addItem(rs.getString(1));
                
            }  } catch (SQLException ex) {
            Logger.getLogger(ControladorPropiedades.class.getName()).log(Level.SEVERE, null, ex);
        }
   
 }   
    
}
