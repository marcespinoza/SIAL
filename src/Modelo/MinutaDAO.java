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
import java.sql.Statement;

/**
 *
 * @author Marcelo Espinoza
 */
public class MinutaDAO {
    
    Conexion conexion;

    public MinutaDAO() {
        conexion = new Conexion();
    }
    
    public int altaMinuta(Date fecha, String apellidos, String nombres, int manzana, int parcela, double cobrado, double rendido, int nro_cuota, String observaciones, int id_recibo){
    int filasAfectadas=0;
     try {
         Connection con = conexion.getConexion();
         String insertar = "Insert into minuta(fecha_minuta, apellidos, nombres, manzana, parcela, cobrado, rendido, nro_cuota, observaciones, recibo_id_recibo) values ('"+fecha+"','"+apellidos+"','"+nombres+"','"+manzana+"','"+parcela+"','"+cobrado+"','"+rendido+"','"+nro_cuota+"','"+observaciones+"','"+id_recibo+"') ";
         PreparedStatement ps = con.prepareStatement(insertar);
         filasAfectadas = ps.executeUpdate();         
     } catch (Exception e) { 
         System.out.println(e.getMessage());
     }
     return filasAfectadas;
 }
    
    public ResultSet obtenerMinutas(){
     ResultSet rs = null;
     try {
          Connection con = conexion.getConexion();
          String listar = "SELECT * FROM Minuta";
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return rs;
 }
    
    public ResultSet obtenerFecha(){
     ResultSet rs = null;
     try {
          Connection con = conexion.getConexion();
          String listar = "SELECT DISTINCT fecha_minuta FROM Minuta";
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return rs;
 }
    
    public ResultSet minutasPorFecha(String fecha){
     ResultSet rs = null;
     try {
          Connection con = conexion.getConexion();
          String listar = "SELECT * FROM Minuta where fecha_minuta = '"+fecha+"'";
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return rs;
 }
    
     public ResultSet MinutasPorRango(Date desde, Date hasta){
     ResultSet rs = null;
     try {
          Connection con = conexion.getConexion();
          PreparedStatement statement =
          con.prepareStatement("SELECT * FROM minuta WHERE fecha_minuta between ? and ?");
          statement.setDate(1, desde);
          statement.setDate(2, hasta);
          rs = statement.executeQuery();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return rs;
 }
    
}
