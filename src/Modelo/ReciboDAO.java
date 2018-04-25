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
import java.sql.Statement;

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
     try {
          Connection con = conexion.dataSource.getConnection();
          String insertar = "Insert into recibo(nro_recibo, apellido_propietario, nombre_propietario) values ('"+nro_recibo+"','"+apellido_propietario+"','"+nombre_propietario+"')";
          PreparedStatement ps = con.prepareStatement(insertar, Statement.RETURN_GENERATED_KEYS);
          ps.executeUpdate();  
          ResultSet rs = ps.getGeneratedKeys();  
          id_control = rs.next() ? rs.getInt(1) : 0;
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }
     //********Retorno id_control generado por la inserción********
     return id_control;
    }
    
}
