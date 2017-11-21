/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Marcelo
 */
public class ClienteDAO {
    
    Conexion conexion;
    private Object [] clientes;
    
    public ClienteDAO(){
       conexion = new Conexion();
    }
    
 public void obtenerClientes(){}
 
 public ResultSet clientesPorLotes(){
     ResultSet rs = null;
     Statement st = null;
     Connection con = null;
     try {
          con = conexion.getConexion();
          String listar = "SELECT c.Dni, c.Apellidos, c.Nombres,c.fecha_nacimiento, c.barrio, c.calle, c.numero, c.Telefono1, c.telefono2, c.trabajo, f.Id_control, f.cantidad_cuotas, f.gastos, f.bolsa_cemento, f.lote_Barrio, f.lote_Manzana, f.lote_Parcela FROM (cliente c LEFT JOIN cliente_tiene_ficha_control h ON c.Dni = h.cliente_Dni) left join ficha_control f on f.Id_control=h.ficha_control_Id_control GROUP BY f.Id_control, c.dni, ifnull(f.Id_control, c.Dni)"; 
          st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (SQLException ex) {
          System.out.println(ex.getMessage());
        } 
     return rs;
 }
 
  public ResultSet clientesPorCuotas(double monto){
     ResultSet rs = null;
     try {
          Connection con = conexion.getConexion();
          String listar = "SELECT c.Dni, c.Apellidos, c.Nombres,c.fecha_nacimiento, c.barrio, c.calle, c.numero, c.Telefono1, c.telefono2, c.trabajo, f.Id_control, f.cantidad_cuotas, f.gastos, f.bolsa_cemento, f.lote_Barrio, f.lote_Manzana, f.lote_Parcela FROM cliente c LEFT JOIN ficha_control f ON c.Dni = f.cliente_Dni where f.cuota_pura='"+monto+"'"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return rs;
 }
 
  public ResultSet clientePorLote(int id_control){
     ResultSet rs = null;
     try {
          Connection con = conexion.getConexion();
          String listar = "SELECT c.Apellidos, c.Nombres,c.barrio, c.calle, c.numero FROM cliente c LEFT JOIN cliente_tiene_ficha_control f ON c.Dni = f.cliente_Dni where f.ficha_control_id_control = '"+id_control+"'"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return rs;
     }
  
     public void altaClientesXLotes(int dni, int id_control){
        try {
            Connection con = conexion.getConexion();
            String insertar = "insert into cliente_tiene_ficha_control (cliente_dni, ficha_control_id_control) values (?,?)";
            PreparedStatement stmt = con.prepareStatement(insertar);
            stmt.setInt(1, dni);
            stmt.setInt(2, id_control);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
    }
 
 public int altaCliente(int dni, String apellidos, String nombres, Date fecha_nacimiento, String barrio, String calle, int numero, String telefono1, String telefono2, String trabajo){
     int filasAfectadas=0;
     try {
         Connection con = conexion.getConexion();
         String insertar = "Insert into cliente(dni, apellidos, nombres, fecha_nacimiento, barrio, calle, numero, telefono1, telefono2, trabajo) values ('"+dni+"','"+apellidos+"','"+nombres+"','"+fecha_nacimiento+"','"+barrio+"','"+calle+"','"+numero+"','"+telefono1+"','"+telefono2+"','"+trabajo+"')";
         PreparedStatement ps = con.prepareStatement(insertar);
         filasAfectadas = ps.executeUpdate();         
     } catch (Exception e) { 
         System.out.println(e.getMessage());
     }
     return filasAfectadas;
 }
 public void eliminarCliente(int dni){
     try {
         Connection con = conexion.getConexion();
         String eliminar = "delete from cliente where dni = '"+dni+"'";
         PreparedStatement ps = con.prepareStatement(eliminar);
         ps.executeUpdate();
     } catch (Exception e) {
         System.out.println(e.getMessage());
     }
 }
 public void editarCliente(int dni, String apellidos, String nombres, Date fecha_nacimiento, String barrio, String calle, int numero, String telefono1, String telefono2, String trabajo, int clave_cliente){
        try {
            Connection con = conexion.getConexion();
            String query = "UPDATE cliente SET dni = ?, nombres = ?, apellidos = ?, fecha_nacimiento = ?, barrio = ?, calle = ?, numero = ?, telefono1 = ?, telefono2 = ?, trabajo = ? where dni = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt   (1, dni);
            preparedStmt.setString(2, nombres);
            preparedStmt.setString(3, apellidos);
            preparedStmt.setDate(4, fecha_nacimiento);
            preparedStmt.setString(5, barrio);
            preparedStmt.setString(6, calle);
            preparedStmt.setInt(7, numero);
            preparedStmt.setString(8, telefono1);
            preparedStmt.setString(9, telefono2);
            preparedStmt.setString(10, trabajo);
            preparedStmt.setInt(11, clave_cliente);
            preparedStmt.executeUpdate();      
            preparedStmt.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
 }
 public ResultSet buscarCliente(int dni){
     ResultSet rs = null;
     try {
          Connection con = conexion.getConexion();
          String listar = "SELECT  c.apellidos, c.nombres, c.fecha_nacimiento, c.dni, c.barrio, c.calle, c.numero, c.telefono1, c.telefono2, c.trabajo, r.apellidos, r.nombres, r.telefono, r.parentesco from cliente c inner join referencia r on c.dni=r.cliente_dni where dni = '"+dni+"'"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return rs;
 }
    
}
