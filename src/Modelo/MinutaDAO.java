/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Clases.Minuta;
import conexion.Conexion;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
         Connection con = conexion.dataSource.getConnection();
         String insertar = "Insert into minuta(fecha_minuta, apellidos, nombres, manzana, parcela, cobrado, gastos, rendido, nro_cuota, observaciones, categoria, id_Recibo) values ('"+fecha+"','"+apellidos+"','"+nombres+"','"+manzana+"','"+parcela+"','"+cobrado+"','"+gastos+"','"+rendido+"','"+nro_cuota+"','"+observaciones+"','"+categoria+"','"+id_recibo+"') ";
         PreparedStatement ps = con.prepareStatement(insertar);
         filasAfectadas = ps.executeUpdate();         
     } catch (Exception e) { 
         System.out.println(e.getMessage()+"dat");
     }
     return filasAfectadas;
 }
    
    public ResultSet obtenerMinutas(){
     ResultSet rs = null;
     try {
          Connection con = conexion.dataSource.getConnection();
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
          Connection con = conexion.dataSource.getConnection();
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
          Connection con = conexion.dataSource.getConnection();
          String listar = "SELECT DISTINCT(MONTH(Fecha_minuta)),SUM(Cobrado), SUM(Rendido) FROM minuta WHERE (YEAR(Fecha_minuta) BETWEEN '"+añoDesde+"' and '"+añoHasta+"') AND (MONTH(Fecha_minuta) BETWEEN '"+mesDesde+"' and '"+mesHasta+"') GROUP BY MONTH(Fecha_minuta)";
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return rs;
 }
    
    public List obtenerFecha(){
     ResultSet rs = null;
     List<Minuta> minutas = new ArrayList<Minuta>();
     Connection con = null;
     try {
          con = conexion.dataSource.getConnection();
          String listar = "SELECT DISTINCT fecha_minuta FROM Minuta";
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
          while(rs.next()){              
               Minuta minuta = new Minuta();
               minuta.setFechaMinuta(rs.getDate(1));   
               minutas.add(minuta);
            }  
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally{
         try {
             con.close();
         } catch (SQLException ex) {
             Logger.getLogger(MinutaDAO.class.getName()).log(Level.SEVERE, null, ex);
         }
        }
     return minutas;
 }
    
    public List<Minuta> minutasPorFecha(String fecha){
       ResultSet rs;
       Connection connection = null;
       List<Minuta> minutas = new ArrayList<>();
     try {
          connection = conexion.dataSource.getConnection();
          String listar = "SELECT m.fecha_minuta, m.apellidos, m.nombres, m.manzana, m.parcela, m.cobrado, m.gastos, m.rendido, m.nro_cuota, m.observaciones, m.baja, r.nro_recibo from (minuta m INNER JOIN recibo r ON m.id_recibo=r.idRecibo) where m.fecha_minuta = '"+fecha+"'";
          Statement st = connection.createStatement();
          rs = st.executeQuery(listar);
          while (rs.next()) {
                Minuta m = new Minuta();
                m.setFechaMinuta(rs.getDate(1));
                m.setApellidos(rs.getString(2));
                m.setNombres(rs.getString(3));
                m.setManzana(rs.getInt(4));
                m.setParcela(rs.getInt(5));
                m.setCobrado(rs.getBigDecimal(6));
                m.setGastos(rs.getBigDecimal(7));
                m.setRendido(rs.getBigDecimal(8));
                m.setNroCuota(rs.getInt(9));
                m.setObservaciones(rs.getString(10));
                m.setBaja(rs.getInt(11));
                m.setNroRecibo(rs.getInt(12));
                minutas.add(m);
          }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally{
         try {
             connection.close();
         } catch (SQLException ex) {
             Logger.getLogger(MinutaDAO.class.getName()).log(Level.SEVERE, null, ex);
         }
        }
     return minutas;
    }
    
    public ResultSet minutasPorMes(int añoDesde, int mesDesde, int añoHasta, int mesHasta){
     ResultSet rs = null;
     try {
          Connection con = conexion.dataSource.getConnection();
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
          Connection con = conexion.dataSource.getConnection();
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
     
     
     public List<Minuta> minutasPorRango2(Date desde, Date hasta){
       ResultSet rs = null;
       Connection connection = null;
       List<Minuta> minutas = new ArrayList<>();
     try {
          connection = conexion.dataSource.getConnection();
          PreparedStatement statement =
          connection.prepareStatement("SELECT m.fecha_minuta, m.apellidos, m.nombres, m.manzana, m.parcela, m.cobrado, m.gastos, m.rendido, m.nro_cuota, m.observaciones, m.baja from minuta m INNER JOIN recibo r ON m.id_recibo=r.idRecibo WHERE fecha_minuta between ? and ?");
          statement.setDate(1, desde);
          statement.setDate(2, hasta);
          rs = statement.executeQuery();
          while (rs.next()) {
                Minuta m = new Minuta();
                m.setFechaMinuta(rs.getDate(1));
                m.setApellidos(rs.getString(2));
                m.setNombres(rs.getString(3));
                m.setManzana(rs.getInt(4));
                m.setParcela(rs.getInt(5));
                m.setCobrado(rs.getBigDecimal(6));
                m.setGastos(rs.getBigDecimal(7));
                m.setRendido(rs.getBigDecimal(8));
                m.setNroCuota(rs.getInt(9));
                m.setObservaciones(rs.getString(10));
                m.setBaja(rs.getInt(11));
                m.setNroRecibo(rs.getInt(12));
                minutas.add(m);
          }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return minutas;
 }
     
     
   public void eliminarMinuta(int idRecibo){
       
        try {
            Connection con = conexion.dataSource.getConnection();
            String query = "UPDATE minuta SET baja = 1, observaciones = 'Anulado' where id_recibo = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt   (1, idRecibo);
            preparedStmt.executeUpdate();      
            preparedStmt.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(MinutaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }        
 } 
   
    
}
