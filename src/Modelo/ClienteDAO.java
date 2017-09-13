/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.*;
import javax.swing.table.DefaultTableModel;



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
     try {
          Connection con = conexion.getConexion();
          String listar = "SELECT c.Dni, c.Apellidos, c.Nombres, c.barrio, c.calle, c.numero, c.Telefono1, c.trabajo, f.Id_control, f.cantidad_cuotas, f.gastos, f.bolsa_cemento, f.lote_Barrio, f.lote_Manzana, f.lote_Parcela FROM cliente c LEFT JOIN ficha_control f ON c.Dni = f.cliente_Dni"; 
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
          String listar = "SELECT c.Apellidos, c.Nombres,c.barrio, c.calle, c.numero FROM cliente c LEFT JOIN ficha_control f ON c.Dni = f.cliente_Dni where f.id_control = '"+id_control+"'"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return rs;
 }
 
 public int altaCliente(int dni, String apellidos, String nombres, Date fech_nacimiento, String barrio, String calle, int numero, String telefono1, String telefono2, String trabajo){
    int filasAfectadas=0;
     try {
         Connection con = conexion.getConexion();
         String insertar = "Insert into cliente(dni, apellidos, nombres, fecha_nacimiento, barrio, calle, numero, telefono1, telefono2, trabajo) values ('"+dni+"','"+apellidos+"','"+nombres+"','"+fech_nacimiento+"','"+barrio+"','"+calle+"','"+numero+"','"+telefono1+"','"+telefono2+"','"+trabajo+"') ";
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
     }
 }
 public void editarCliente(){}
 public ResultSet buscarCliente(int dni){
     ResultSet rs = null;
     try {
          Connection con = conexion.getConexion();
          String listar = "SELECT  nombres, apellidos, barrio, calle, numero from cliente where dni = '"+dni+"'"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return rs;
 }
    
}
