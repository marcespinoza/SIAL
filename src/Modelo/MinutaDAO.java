/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import conexion.Conexion;
import java.math.BigDecimal;
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
    
    public int altaMinuta(Date fecha, String apellidos, String nombres, int manzana, int parcela, BigDecimal cobrado, BigDecimal gastos, BigDecimal rendido, int nro_cuota, String observaciones, String categoria, int id_recibo){
    int filasAfectadas=0;
     try {
         Connection con = conexion.getConexion();
         String insertar = "Insert into minuta(fecha_minuta, apellidos, nombres, manzana, parcela, cobrado, gastos, rendido, nro_cuota, observaciones, categoria, recibo_idRecibo) values ('"+fecha+"','"+apellidos+"','"+nombres+"','"+manzana+"','"+parcela+"','"+cobrado+"','"+gastos+"','"+rendido+"','"+nro_cuota+"','"+observaciones+"','"+categoria+"','"+id_recibo+"') ";
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
    
    public ResultSet obtenerMinutasXCategoria(int añoDesde, int mesDesde, int añoHasta, int mesHasta){
     ResultSet rs = null;
     try {
          Connection con = conexion.getConexion();
          String listar = "SELECT Categoria, SUM(Cobrado), MONTH(Fecha_minuta) FROM minuta WHERE (YEAR(Fecha_minuta) BETWEEN '"+añoDesde+"' and '"+añoHasta+"') AND (MONTH(Fecha_minuta) BETWEEN '"+mesDesde+"' and '"+mesHasta+"') GROUP BY Categoria";
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return rs;
 }
    
    public ResultSet obtenerMinutasXMes(int añoDesde, int mesDesde, int añoHasta, int mesHasta){
     ResultSet rs = null;
     try {
          Connection con = conexion.getConexion();
          String listar = "SELECT DISTINCT(MONTH(Fecha_minuta)),SUM(Cobrado), SUM(Rendido) FROM minuta WHERE (YEAR(Fecha_minuta) BETWEEN '"+añoDesde+"' and '"+añoHasta+"') AND (MONTH(Fecha_minuta) BETWEEN '"+mesDesde+"' and '"+mesHasta+"') GROUP BY MONTH(Fecha_minuta)";
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
    
    public ResultSet minutasPorMes(int añoDesde, int mesDesde, int añoHasta, int mesHasta){
     ResultSet rs = null;
     try {
          Connection con = conexion.getConexion();
          String listar = "SELECT SUM(Cobrado), MONTH(Fecha_minuta), observaciones from minuta WHERE (YEAR(Fecha_minuta) BETWEEN '"+añoDesde+"' and '"+añoHasta+"') AND (MONTH(Fecha_minuta) BETWEEN '"+mesDesde+"' and '"+mesHasta+"')  group by Observaciones, MONTH(Fecha_minuta)";
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return rs;
    }
    
     public ResultSet minutasPorRango(Date desde, Date hasta){
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
