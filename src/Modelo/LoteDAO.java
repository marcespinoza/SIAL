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
public class LoteDAO {
    
      Conexion conexion;

    public LoteDAO() {
        conexion = new Conexion();
    }
    
     public ResultSet obtenerPropietarioxLote(String barrio, int manzana, int parcela){
     ResultSet rs = null;
     try {
          Connection con = conexion.getConexion();
          String listar = "SELECT propietario_Apellidos, propietario_nombres, propietario_cuit  from lote where barrio = '"+barrio+"' and manzana='"+manzana+"' and parcela = '"+parcela+"'"; 
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
          String listar = "SELECT barrio from lote"; 
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
          String listar = "SELECT manzana from lote where barrio = '"+barrio+"'"; 
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
          String listar = "SELECT parcela from lote where barrio = '"+barrio+"' and manzana = '"+manzana+"'"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }
     return rs;
 }
    
}
