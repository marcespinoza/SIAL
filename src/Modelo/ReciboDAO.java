/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcelo Espinoza
 */
public class ReciboDAO {
    
    Conexion conexion;

    public ReciboDAO() {
        conexion = new Conexion();
    }
    
     public int altaRecibo(int nro_recibo, String apellido_propietario, String nombre_propietario){
         int id_control = 1;
         Timestamp timestamp = new java.sql.Timestamp(new java.util.Date().getTime());
         Connection con = null;
     try {
          con = conexion.dataSource.getConnection();
          String insertar = "Insert into recibo(nro_recibo, apellido_propietario, nombre_propietario, timestamp) values ('"+nro_recibo+"','"+apellido_propietario+"','"+nombre_propietario+"','"+timestamp+"')";
          PreparedStatement ps = con.prepareStatement(insertar, Statement.RETURN_GENERATED_KEYS);
          ps.executeUpdate();  
          ResultSet rs = ps.getGeneratedKeys();  
          id_control = rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally{
             try {
                 con.close();
             } catch (SQLException ex) {
                 Logger.getLogger(ReciboDAO.class.getName()).log(Level.SEVERE, null, ex);
             }
     }
     //********Retorno id_control generado por la inserción********
     return id_control;
    }
    
}
