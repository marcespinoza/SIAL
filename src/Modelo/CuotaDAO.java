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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcelo
 */
public class CuotaDAO {
    
    Conexion conexion;

    public CuotaDAO() {
        conexion = new Conexion();
    }
    
    public ResultSet listaDetalleCuotaXsaldo(int idControl){
         ResultSet rs = null;
     try {
          Connection con = conexion.dataSource.getConnection();
          String listar = "SELECT nro_cuota, fecha, detalle, cuota_pura, gastos_administrativos, debe, haber, saldo, cemento_debe, cemento_haber, cemento_saldo, observaciones, tipo_pago from linea_control_lote where id_Control = '"+idControl+"' order by saldo desc "; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
        }
     return rs;
    }
    
    public ResultSet listaDetalleCuota(int idControl){
         ResultSet rs = null;
     try {
          Connection con = conexion.dataSource.getConnection();
          String listar = "SELECT nro_cuota, fecha, detalle, cuota_pura, gastos_administrativos, debe, haber, saldo, cemento_debe, cemento_haber, cemento_saldo, observaciones, nro_recibo, id_recibo, tipo_pago from linea_control_lote where id_Control = '"+idControl+"'"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
        }
     return rs;
    }
    
    public int altaCuotaLote(Date fecha_pago,int nro_cuota, String detalle, BigDecimal cuota_pura, BigDecimal gastos, BigDecimal debe, BigDecimal haber, BigDecimal saldo, BigDecimal cemento_debe, BigDecimal cemento_haber, BigDecimal cemento_saldo, String observaciones, String tipo_pago, int id_control){
    int filasAfectadas=0;
     try {
         Connection con = conexion.dataSource.getConnection();
         String insertar = "Insert into linea_control_lote (fecha, nro_cuota, detalle, cuota_pura, gastos_administrativos, debe, haber, saldo, cemento_debe, cemento_haber, cemento_saldo, observaciones, tipo_pago, id_Control) values ('"+fecha_pago+"','"+nro_cuota+"','"+detalle+"','"+cuota_pura+"','"+gastos+"','"+debe+"','"+haber+"','"+saldo+"','"+cemento_debe+"','"+cemento_haber+"','"+cemento_saldo+"','"+observaciones+"','"+tipo_pago+"','"+id_control+"') ";
         PreparedStatement ps = con.prepareStatement(insertar);
         filasAfectadas = ps.executeUpdate();
         
     } catch (Exception e) {  
           System.out.println(e.getMessage());
     }
     return filasAfectadas;
 }  
     
    public int altaCuotaDpto(Date fecha_pago,int nro_cuota, String detalle, BigDecimal cuota_pura, BigDecimal gastos, BigDecimal debe, BigDecimal haber, BigDecimal saldo, BigDecimal cemento_debe, BigDecimal cemento_haber, BigDecimal cemento_saldo, String observaciones, String tipo_pago, int id_control){
    int filasAfectadas=0;
     try {
         Connection con = conexion.dataSource.getConnection();
         String insertar = "Insert into linea_control_dpto (fecha, nro_cuota, detalle, cuota_pura, gastos_administrativos, debe, haber, saldo, cemento_debe, cemento_haber, cemento_saldo, observaciones, tipo_pago, id_Control) values ('"+fecha_pago+"','"+nro_cuota+"','"+detalle+"','"+cuota_pura+"','"+gastos+"','"+debe+"','"+haber+"','"+saldo+"','"+cemento_debe+"','"+cemento_haber+"','"+cemento_saldo+"','"+observaciones+"','"+tipo_pago+"','"+id_control+"') ";
         PreparedStatement ps = con.prepareStatement(insertar);
         filasAfectadas = ps.executeUpdate();
         
     } catch (Exception e) {  
           System.out.println(e.getMessage()+"cuotadao");
     }
     return filasAfectadas;
 }  
    
  public ResultSet getNrosCuotas(int idControl){     
       ResultSet rs = null;
        try {           
            Connection con = conexion.dataSource.getConnection();
            String bandera = "select nro_cuota from linea_control_lote where id_control='"+idControl+"'";
            Statement st = con.createStatement();
            rs = st.executeQuery(bandera);
        } catch (SQLException ex) {
            Logger.getLogger(CuotaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
  }
  
     //-------Consulta si existe la ultima cuota para adelantar-----//   
  public boolean getUltimaCuota(int nroCuotas){
       boolean flag = false;       
       ResultSet rs = null;
        try {           
            Connection con = conexion.dataSource.getConnection();
            String bandera = "select * from linea_control_lote where nro_cuota='"+nroCuotas+"'";
            Statement st = con.createStatement();
            rs = st.executeQuery(bandera);
            flag = rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(CuotaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
  }
  
  //------Actualiza las observaciones------//
  public void actualizarCuota(String observaciones, int nro_cuota, int id_control){
        try {
            Connection con = conexion.dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE linea_control_lote SET observaciones = ? WHERE nro_cuota = ? AND id_control = ?");
            ps.setString(1,observaciones);
            ps.setInt(2,nro_cuota);
            ps.setInt(3,id_control);
            // call executeUpdate to execute our sql update statement
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CuotaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
  
  //------Actualiza monto cuota------//
  public void actualizarMontoCuota(BigDecimal cuota_pura, BigDecimal gastos_administrativos, BigDecimal haber, BigDecimal saldo, BigDecimal cemento_haber, BigDecimal cemento_saldo, int nro_cuota, int id_control){
        try {
            Connection con = conexion.dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE linea_control_lote SET cuota_pura = ?, gastos_administrativos = ?, haber = ?, saldo = ?, cemento_haber = ?, cemento_saldo = ?  WHERE nro_cuota = ? AND id_control = ?");
            ps.setBigDecimal(1,cuota_pura);
            ps.setBigDecimal(2, gastos_administrativos);
            ps.setBigDecimal(3, haber);
            ps.setBigDecimal(4, saldo);
            ps.setBigDecimal(5, cemento_haber);
            ps.setBigDecimal(6, cemento_saldo);
            ps.setInt(7,nro_cuota);
            ps.setInt(8,id_control);
            // call executeUpdate to execute our sql update statement
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CuotaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
  
  public void actualizarNroRecibo(int nro_recibo, int id_recibo, int nro_cuota, int id_control){
        try {
            Connection con = conexion.dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE linea_control_lote SET Nro_recibo = ?, id_recibo=? WHERE nro_cuota = ? AND id_control = ?");
            ps.setInt(1, nro_recibo);
            ps.setInt(2, id_recibo);
            ps.setInt(3,nro_cuota);
            ps.setInt(4,id_control);
            // call executeUpdate to execute our sql update statement
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(CuotaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
  
  public void eliminarCuota(int nro_cuota, int id_control){
       try {
         Connection con = conexion.dataSource.getConnection();
         String eliminar = "delete from linea_control_lote where nro_cuota = '"+nro_cuota+"' and id_control='"+id_control+"'";
         PreparedStatement ps = con.prepareStatement(eliminar);
         ps.executeUpdate();
         ps.close();
         con.close();
     } catch (Exception e) {
         System.out.println(e.getMessage());
     }
  }
    
}
