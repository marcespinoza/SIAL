/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import conexion.Conexion;
import java.math.BigDecimal;
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
public class FichaControlDAO {
    
     Conexion conexion;
    private Object [] clientes;

    public FichaControlDAO() {
        conexion = new Conexion();
    }
    
    public int altaFichaControl(String tipo_compra, String dimension, int cantidad_cuotas, BigDecimal cuota_pura, BigDecimal gastos, BigDecimal bolsa_cemento, String barrio, int manzana, int parcela){
         int id_control = 1;
     try {
          Connection con = conexion.getConexion();
          String insertar = "Insert into ficha_control(tipo_compra, dimension, cantidad_cuotas, cuota_pura, gastos, bolsa_cemento, lote_barrio, lote_manzana, lote_parcela) values ('"+tipo_compra+"','"+dimension+"','"+cantidad_cuotas+"','"+cuota_pura+"','"+gastos+"','"+bolsa_cemento+"','"+barrio+"','"+manzana+"','"+parcela+"')";
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
    
    public ResultSet obtenerFichaControl(){
     ResultSet rs = null;
     try {
          Connection con = conexion.getConexion();
          String listar = "SELECT precio, gastos, bolsa_cemento FROM cliente c LEFT JOIN ficha_control f ON c.Dni = f.Dni"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
        }
     return rs;
 }
    
     public ResultSet obtenerMontoCuotas(){
     ResultSet rs = null;
     try {
          Connection con = conexion.getConexion();
          String listar = "SELECT cuota_pura FROM ficha_control"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
        }
     return rs;
 }
    
    public ResultSet obtenerFichaControl(int id_control){
     ResultSet rs = null;
     try {
          Connection con = conexion.getConexion();
          String listar = "SELECT dimension, cantidad_cuotas, cuota_pura, gastos, bolsa_cemento, lote_barrio, lote_manzana, lote_parcela,dimension , gastos, cuota_pura FROM ficha_control where id_control = '"+id_control+"'"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return rs;
 }
    
    public int obtenerIdControl(){
     ResultSet rs = null;
     int id_control = 0;
     try {
          //numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
        } catch (Exception e) {
        }
     return id_control;
 }
    
 
    
}
