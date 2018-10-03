/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Clases.FichaDeControl;
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
public class FichaControlDAO {
    
     Conexion conexion;

    public FichaControlDAO() {
        conexion = new Conexion();
    }
    
    public int altaFichaControl(String tipo_compra, String dimension, int cantidad_cuotas, BigDecimal cuota_pura, BigDecimal gastos, BigDecimal bolsa_cemento, Date fch_actualizacion, String barrio, int manzana, int parcela){
         int id_control = 1;
     try {
          Connection con = conexion.dataSource.getConnection();
          String insertar = "Insert into ficha_control_lote(tipo_compra, dimension, cantidad_cuotas, cuota_pura, gastos, bolsa_cemento, fecha_actualizacion, lote_barrio, lote_manzana, lote_parcela) values ('"+tipo_compra+"','"+dimension+"','"+cantidad_cuotas+"','"+cuota_pura+"','"+gastos+"','"+bolsa_cemento+"', '"+fch_actualizacion+"','"+barrio+"','"+manzana+"','"+parcela+"')";
          PreparedStatement ps = con.prepareStatement(insertar, Statement.RETURN_GENERATED_KEYS);
          ps.executeUpdate();  
          ResultSet rs = ps.getGeneratedKeys();  
          id_control = rs.next() ? rs.getInt(1) : 0;
        } catch (Exception e) {
            System.out.println(e.getMessage().toString()+"Alta ficha");
        }
     //********Retorno id_control generado por la inserción********
     return id_control;
    }
    
    public List<FichaDeControl> obtenerFichaControl(){
         ResultSet rs = null;
         Connection connection = null;
         List<FichaDeControl> listaFichaControl = new ArrayList<>();
     try {
          connection = conexion.dataSource.getConnection();
          String listar = "SELECT precio, gastos, bolsa_cemento FROM cliente c LEFT JOIN ficha_control_lote f ON c.Dni = f.Dni"; 
          Statement st = connection.createStatement();
          rs = st.executeQuery(listar);
          while(rs.next()){
              FichaDeControl fc = new FichaDeControl();
              fc.setPrecio(rs.getBigDecimal(1));
              fc.setGastos(rs.getBigDecimal(2));
              fc.setBolsaCemento(rs.getBigDecimal(3));
              listaFichaControl.add(fc);
          }
        } catch (Exception e) {
            Logger.getLogger(PropietarioDAO.class.getName()).log(Level.SEVERE, null, e);
        }finally{
          try {
              connection.close();
          } catch (SQLException ex) {
              Logger.getLogger(PropietarioDAO.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
     return listaFichaControl;
 }
    
     public ResultSet obtenerMontoCuotas(){
     ResultSet rs = null;
     try {
          Connection con = conexion.dataSource.getConnection();
          String listar = "SELECT cuota_pura FROM ficha_control_lote"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
        }
     return rs;
 }
    
    public List<FichaDeControl> obtenerFichaControl(int id_control){
         ResultSet rs = null;
         Connection connection = null;
         List<FichaDeControl> listaFichaControl = new ArrayList<>();
     try {
          connection = conexion.dataSource.getConnection();
          String listar = "SELECT dimension, cantidad_cuotas, cuota_pura, gastos, bolsa_cemento, lote_barrio, lote_manzana, lote_parcela FROM ficha_control_lote where id_control = '"+id_control+"'"; 
          Statement st = connection.createStatement();
          rs = st.executeQuery(listar);
          while(rs.next()){
              FichaDeControl fc = new FichaDeControl();
              fc.setDimension(rs.getString(1));
              fc.setCantidadCuotas(rs.getInt(2));
              fc.setCuotaPura(rs.getBigDecimal(3));
              fc.setGastos(rs.getBigDecimal(4));
              fc.setBolsaCemento(rs.getBigDecimal(5));
              fc.setBarrio(rs.getString(6));
              fc.setManzana(rs.getInt(7));
              fc.setParcela(rs.getInt(8));
              listaFichaControl.add(fc);
          }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return listaFichaControl;
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
    
  public void cambiarPropietario(int nuevo_dni, int viejo_dni, int id_ficha_control){
        try {
            Connection con = conexion.dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE cliente_tiene_lote SET cliente_dni = ? WHERE cliente_dni = ? AND id_control = ?");
            ps.setInt(1,nuevo_dni);
            ps.setInt(2,viejo_dni);
            ps.setInt(3,id_ficha_control);
            // call executeUpdate to execute our sql update statement
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(CuotaDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
  }
  
  //---------Baja logica de un cliente----------//
  public void bajaPropietario(int dni, int id_ficha_control){
        try {
            Connection con = conexion.dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE cliente_tiene_lote SET baja = 1 WHERE cliente_dni = ? AND id_control = ?");
            ps.setInt(1,dni);
            ps.setInt(2,id_ficha_control);
            // call executeUpdate to execute our sql update statement
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(CuotaDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
  }
  
   public void actualizarBolsaCemento( BigDecimal precio_cemento, Date fecha_actualizacion, String id_control){
        try {
            Connection con = conexion.dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE ficha_control_lote SET bolsa_cemento = ?, fecha_actualizacion = ? WHERE id_control = ?");
            ps.setBigDecimal(1, precio_cemento);
            ps.setDate(2, fecha_actualizacion);
            ps.setString(3, id_control);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(CuotaDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
  }
   
    public void actualizarObservacion(String observacion, int id_control){
        try {
            Connection con = conexion.dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE ficha_control_lote SET observacion = ? WHERE id_control = ?");
            ps.setString(1,observacion);
            ps.setInt(2,id_control);
            ps.executeUpdate();            
            ps.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CuotaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
    
}
