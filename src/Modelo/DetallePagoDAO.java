/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Marcelo
 */
public class DetallePagoDAO {
    
    Conexion conexion;

    public DetallePagoDAO() {
        conexion = new Conexion();
    }
    
    public ResultSet listaDetallePago(String idControl){
         ResultSet rs = null;
     try {
          Connection con = conexion.getConexion();
          String listar = "SELECT fecha, detalle, cuota_pura, gastos_administrativos, debe, haber, saldo, cemento_debe, cemento_haber, cemento_saldo, observaciones, tipo_pago from linea_control where idControl = '"+idControl+"' "; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
        }
     return rs;
    }
    
}
