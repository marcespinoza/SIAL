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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcelo Espinoza
 */
public class PropiedadesDAO {
    
   Conexion conexion; 

    public PropiedadesDAO() {
        conexion = new Conexion();
    }
    
 public ResultSet obtenerPropiedades(String apellidos, String nombres){
          ResultSet rs = null;
     try {
          Connection con = conexion.getConexion();   
          String sql = "SELECT barrio, manzana, parcela, vendido from lote where propietario_apellidos =? AND propietario_nombres =? "; 
          PreparedStatement preparedStatement = con.prepareStatement(sql);
          preparedStatement.setString(1, apellidos);
          preparedStatement.setString(2, nombres);
         rs = preparedStatement.executeQuery();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return rs;
     }  

public void agregarPropiedad(String barrio, String manzana, String parcela, String vendido, String propietario_apellidos, String propietario_nombres, String propietario_cuit){
       try {
           Connection con = conexion.getConexion();
           String query="INSERT INTO lote (barrio, manzana, parcela,vendido, propietario_apellidos, propietario_nombres, propietario_cuit)VALUES(?,?,?,?,?,?,?)  ON DUPLICATE KEY UPDATE barrio = VALUES(barrio), manzana = VALUES(manzana), parcela = VALUES(parcela), vendido = VALUES(vendido), propietario_apellidos=VALUES(propietario_apellidos), propietario_nombres = VALUES(propietario_nombres), propietario_cuit = VALUES(propietario_cuit)";
           PreparedStatement stmt = con.prepareStatement(query);
           stmt.setString(1, barrio);
           stmt.setString(2, manzana);
           stmt.setString(3, parcela);
           stmt.setString(4, vendido);
           stmt.setString(5, propietario_apellidos);
           stmt.setString(6, propietario_nombres);
           stmt.setString(7, propietario_cuit);
           stmt.executeUpdate();
       } catch (SQLException ex) {
           System.out.println(ex.getMessage().toString()+"update");
       }
} 
    
}
