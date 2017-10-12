/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.Date;
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
public class LoteDAO {
    
      Conexion conexion;

    public LoteDAO() {
        conexion = new Conexion();
    }
    
    public void editarPropiedad(String barrio,int manzana, int parcela){
        try {
            Connection con = conexion.getConexion();
            String query = "UPDATE lote SET vendido = ? where barrio = ? and manzana =? and parcela=?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt   (1, 1);
            preparedStmt.setString(2, barrio);
            preparedStmt.setInt(3, manzana);
            preparedStmt.setInt(4, parcela);
            preparedStmt.executeUpdate();      
            preparedStmt.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
 }
    
     public ResultSet obtenerPropietarioxLote(int id_control){
     ResultSet rs = null;
     try {
          Connection con = conexion.getConexion();
          String listar = "SELECT l.propietario_Apellidos, l.propietario_Nombres, l.propietario_cuit FROM lote l inner JOIN ficha_control f where f.Lote_Manzana=l.Manzana and f.Id_control='"+id_control+"'";
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }
     return rs;
 }
     
      public ResultSet obtenerBarrios(){
     ResultSet rs = null;
     try {
          Connection con = conexion.getConexion();
          String listar = "SELECT barrio from lote where vendido=0 "; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }
     return rs;
 }
            
      public ResultSet manzanasPorBarrio(String barrio){
     ResultSet rs = null;
     try {
          Connection con = conexion.getConexion();
          String listar = "SELECT manzana from lote where barrio = '"+barrio+"' and vendido=0"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }
     return rs;
 }
      
      public ResultSet parcelasPorManzana(String barrio, int manzana){
     ResultSet rs = null;
     try {
          Connection con = conexion.getConexion();
          String listar = "SELECT parcela from lote where barrio = '"+barrio+"' and manzana = '"+manzana+"' and vendido=0"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }
     return rs;
 }
      
      public void eliminarLote(String barrio, int manzana, int parcela){
     try {
         Connection con = conexion.getConexion();
         System.out.println(barrio+manzana+parcela);
         String eliminar = "delete from lote where barrio = ? and manzana = ? and parcela = ?";
         PreparedStatement ps = con.prepareStatement(eliminar);
         ps.setString(1, barrio);
         ps.setInt(2, manzana);
         ps.setInt(3, parcela);
         ps.executeUpdate();
     } catch (Exception e) {
         System.out.println(e.getMessage());
     }
 }
      
      public ResultSet obtenerPropiedades(String apellidos, String nombres){
          ResultSet rs = null;
     try {
          Connection con = conexion.getConexion();   
          String sql = "SELECT barrio, manzana, parcela, vendido, propietario_cuit from lote where propietario_apellidos =? AND propietario_nombres =? "; 
          PreparedStatement preparedStatement = con.prepareStatement(sql);
          preparedStatement.setString(1, apellidos);
          preparedStatement.setString(2, nombres);
         rs = preparedStatement.executeQuery();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return rs;
     }  

    public void agregarPropiedad(String barrio, String manzana, String parcela, String propietario_apellidos, String propietario_nombres, String propietario_cuit){
       try {
           Connection con = conexion.getConexion();
           String query="INSERT INTO lote (barrio, manzana, parcela, propietario_apellidos, propietario_nombres, propietario_cuit)VALUES(?,?,?,?,?,?)  ON DUPLICATE KEY UPDATE barrio = VALUES(barrio), manzana = VALUES(manzana), parcela = VALUES(parcela),  propietario_apellidos=VALUES(propietario_apellidos), propietario_nombres = VALUES(propietario_nombres), propietario_cuit = VALUES(propietario_cuit)";
           PreparedStatement stmt = con.prepareStatement(query);
           stmt.setString(1, barrio);
           stmt.setString(2, manzana);
           stmt.setString(3, parcela);
           stmt.setString(4, propietario_apellidos);
           stmt.setString(5, propietario_nombres);
           stmt.setString(6, propietario_cuit);
           stmt.executeUpdate();
       } catch (SQLException ex) {
           System.out.println(ex.getMessage().toString());
       }
} 
    
}