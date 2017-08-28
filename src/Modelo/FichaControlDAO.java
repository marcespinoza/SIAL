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
public class FichaControlDAO {
    
     Conexion conexion;
    private Object [] clientes;

    public FichaControlDAO() {
        conexion = new Conexion();
    }
    
    public int altaFichaControl(String tipo_compra, String dimension, double precio, double gastos, double bolsa_cemento, int dni, String barrio, int manzana, int parcela){
         int filasAfectadas = 0;
     try {
          Connection con = conexion.getConexion();
          String insertar = "Insert into ficha_control(tipo_compra, dimension, precio, gastos, bolsa_cemento, dni, barrio, manzana, parcela) values ('"+tipo_compra+"','"+dimension+"','"+precio+"','"+gastos+"','"+bolsa_cemento+"','"+dni+"','"+barrio+"','"+manzana+"','"+parcela+"') ";
           PreparedStatement ps = con.prepareStatement(insertar);
          filasAfectadas = ps.executeUpdate();
          System.out.println(filasAfectadas);
        } catch (Exception e) {
        }
     return filasAfectadas;
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
    
    public ResultSet obtenerFichaControl(int id_control){
     ResultSet rs = null;
     try {
          Connection con = conexion.getConexion();
          String listar = "SELECT precio, gastos, bolsa_cemento FROM ficha_control where id_control = '"+id_control+"'"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
        }
     return rs;
 }
    
}
