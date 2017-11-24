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
public class PropietarioDAO {

     Conexion conexion;
    
    public PropietarioDAO() {
        conexion = new Conexion();
    }
    
     public ResultSet validarPropietarios(String usuario, String contraseña, String tipo_usuario){
     ResultSet rs = null;
     try {
          Connection con = conexion.getConexion();         
          String listar = "SELECT usuario, tipo_usuario, nombres, apellidos from usuario where usuario = '"+usuario+"' and contraseña = '"+contraseña+"' and tipo_usuario = '"+tipo_usuario+"'"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return rs;
 }
     
     public void editarPropietario(String apellidos, String nombres, String cuit, int nro_recibo, String backup_cuit){
         try {
             Connection con = conexion.getConexion();
             PreparedStatement pstm = con.prepareStatement("UPDATE propietario set apellidos= ? , nombres= ? , cuit= ? , nro_recibo= ? where cuit= ? ");             
             pstm.setString(1,apellidos);
             pstm.setString(2,nombres);
             pstm.setString(3,cuit);
             pstm.setInt(4, nro_recibo);
             pstm.setString(5,backup_cuit);
             pstm.executeUpdate();
         } catch (SQLException ex) {
             Logger.getLogger(PropietarioDAO.class.getName()).log(Level.SEVERE, null, ex);
             System.out.println(ex.getMessage());
         }
     }
     
     public void editarNroRecibo(String apellidos, String nombres, String cuit, int nro_recibo){
         try {
             Connection con = conexion.getConexion();
             PreparedStatement pstm = con.prepareStatement("update propietario set nro_recibo= ? where apellidos= ? and nombres=? and cuit=? ");             
             pstm.setInt(1,nro_recibo);
             pstm.setString(2,apellidos);
             pstm.setString(3,nombres);
             pstm.setString(4, cuit);
             pstm.executeUpdate();
         } catch (SQLException ex) {
             Logger.getLogger(PropietarioDAO.class.getName()).log(Level.SEVERE, null, ex);
         }
     }
     
     public ResultSet obtenerPropietarios(){
          ResultSet rs = null;
     try {
          Connection con = conexion.getConexion();         
          String listar = "SELECT apellidos, nombres, cuit, nro_recibo from propietario"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return rs;
     }
        
      public ResultSet obtenerApellidosXLote(){
          ResultSet rs = null;
     try {
          Connection con = conexion.getConexion();         
          String listar = "SELECT DISTINCT propietario_apellidos from lote"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return rs;
     }      
     
      public ResultSet obtenerApellidosXDepartamento(){
          ResultSet rs = null;
     try {
          Connection con = conexion.getConexion();         
          String listar = "SELECT DISTINCT propietario_apellidos from departamento"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return rs;
     }
      
        public ResultSet obtenerNombresXLote(String apellido){
          ResultSet rs = null;
     try {
          Connection con = conexion.getConexion();         
          String listar = "SELECT DISTINCT propietario_nombres from lote where propietario_apellidos='"+apellido+"'"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return rs;
     }      
     
      public ResultSet obtenerNombresXDepartamento(String apellido){
          ResultSet rs = null;
     try {
          Connection con = conexion.getConexion();         
          String listar = "SELECT DISTINCT propietario_nombres from departamento where propietario_apellidos='"+apellido+"'"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return rs;
     }
      
       public ResultSet obtenerApellidos(){
          ResultSet rs = null;
     try {
          Connection con = conexion.getConexion();         
          String listar = "SELECT apellidos from propietario"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return rs;
     }
     
       public ResultSet obtenerNombres(String apellidos){
          ResultSet rs = null;
     try {
          Connection con = conexion.getConexion();         
          String listar = "SELECT nombres, cuit from propietario where apellidos='"+apellidos+"'"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return rs;
     }  
       
        public ResultSet obtenerCuit(String apellidos, String nombres){
          ResultSet rs = null;
     try {
          Connection con = conexion.getConexion();  
          String listar = "SELECT cuit, nro_recibo from propietario where apellidos='"+apellidos+"' and nombres = '"+nombres+"'"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return rs;
     }  
       
     public void eliminarPropietarios(String cuit){
         try {
         Connection con = conexion.getConexion();
         String eliminar = "delete from propietario where cuit='"+cuit+"'";
         PreparedStatement ps = con.prepareStatement(eliminar);
         ps.executeUpdate();
     } catch (Exception e) {
         System.out.println(e.getMessage());
     }
     }
     public int agregarPropietarios(String apellidos, String nombres, String cuit, String nroRecibo){
          int filasAfectadas=0;
     try {
         Connection con = conexion.getConexion();
         String insertar = "Insert into propietario(apellidos, nombres, cuit, nro_recibo) values ('"+apellidos+"','"+nombres+"','"+cuit+"','"+nroRecibo+"') ";
         PreparedStatement ps = con.prepareStatement(insertar);
         filasAfectadas = ps.executeUpdate();         
     } catch (Exception e) { 
         System.out.println(e.getMessage());
     }
     return filasAfectadas;
     }
    
}
