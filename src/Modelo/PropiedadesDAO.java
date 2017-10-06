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
 * @author Marcelo Espinoza
 */
public class PropiedadesDAO {
    
   Conexion conexion; 

    public PropiedadesDAO() {
        conexion = new Conexion();
    }
    
 public ResultSet obtenerPropiedades(){
          ResultSet rs = null;
     try {
          Connection con = conexion.getConexion();         
          String listar = "SELECT apellidos, nombres, cuit from propietario"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return rs;
     }   
    
}
