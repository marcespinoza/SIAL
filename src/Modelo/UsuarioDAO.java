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
public class UsuarioDAO {

     Conexion conexion;
    
    public UsuarioDAO() {
        conexion = new Conexion();
    }
    
     public boolean validarUsuario(String usuario, String contraseña, String tipo_usuario){
     ResultSet rs = null;
     try {
          Connection con = conexion.getConexion();
         
          String listar = "SELECT * from usuario where usuario = '"+usuario+"' and contraseña = '"+contraseña+"' and tipo_usuario = '"+tipo_usuario+"'"; 
            System.out.println("SELECT * from usuario where usuario = '"+usuario+"' and contraseña = '"+contraseña+"' and tipo_usuario = '"+tipo_usuario+"'");
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
          return rs.first();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return false;
 }
    
}
