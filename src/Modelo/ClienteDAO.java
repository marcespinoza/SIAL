/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Clases.ClientesPorCriterio;
import Clases.Lote;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Marcelo
 */
public class ClienteDAO {
    
    Conexion conexion;
    
    public ClienteDAO(){
       conexion = new Conexion();
    }
    
 public void obtenerClientes(){}
 
 public List<ClientesPorCriterio> clientesPorLotes(){
     ResultSet rs = null;
     Connection connection = null;
     List<ClientesPorCriterio> clientesPorLotes = new ArrayList<>();
     try {
          connection = conexion.dataSource.getConnection();
          String listar = "SELECT c.Dni, c.Apellidos, c.Nombres,c.fecha_nacimiento, c.barrio, c.calle, c.numero, c.Telefono1, c.telefono2, c.trabajo, h.baja, f.Id_control, f.cantidad_cuotas, f.gastos, f.bolsa_cemento, f.fecha_actualizacion, f.lote_Barrio, f.lote_Manzana, f.lote_Parcela,f.observacion, f.cuota_pura FROM (cliente c LEFT JOIN cliente_tiene_lote h ON c.Dni = h.cliente_Dni) left join ficha_control_lote f on f.Id_control=h.Id_control GROUP BY f.Id_control, c.dni, ifnull(f.Id_control, c.Dni) order by c.apellidos"; 
          Statement st = connection.createStatement();
          rs = st.executeQuery(listar);
          while (rs.next()) {
            ClientesPorCriterio cpp = new ClientesPorCriterio();
            cpp.setDni(rs.getInt(1));
            cpp.setApellidos(rs.getString(2));
            cpp.setNombres(rs.getString(3));
            cpp.setFecha_nacimiento(rs.getDate(4));
            cpp.setBarrio_cliente(rs.getString(5));
            cpp.setCalle_cliente(rs.getString(6));
            cpp.setNro_cliente(rs.getInt(7));
            cpp.setTelefono1(rs.getString(8));
            cpp.setTelefono2(rs.getString(9));
            cpp.setTrabajo(rs.getString(10));
            cpp.setBaja(rs.getInt(11));
            cpp.setIdControl(rs.getString(12));
            cpp.setCantidad_cuotas(rs.getInt(13));
            cpp.setGastos(rs.getBigDecimal(14));
            cpp.setBolsa_cemento(rs.getBigDecimal(15));
            cpp.setFecha_actualizacion(rs.getDate(16));
            cpp.setBarrio(rs.getString(17));
            cpp.setManzana(rs.getString(18));
            cpp.setParcela(rs.getString(19));
            cpp.setObservacion(rs.getString(20));
            cpp.setCuota_pura(rs.getBigDecimal(21));
            clientesPorLotes.add(cpp);
         }
        } catch (SQLException ex) {
          System.out.println(ex.getMessage());
        }finally{
              try {
                  connection.close();
              } catch (SQLException ex) {
                  Logger.getLogger(PropietarioDAO.class.getName()).log(Level.SEVERE, null, ex);
              }
        }
     return clientesPorLotes;
 }
 
  public ResultSet clientesSinLotes(){
     ResultSet rs = null;
     Statement st = null;
     Connection con = null;
     try {
          con = conexion.dataSource.getConnection();
          String listar = "SELECT c.Dni, c.Apellidos, c.Nombres FROM (cliente c left OUTER JOIN cliente_tiene_lote h on c.Dni=h.cliente_Dni) WHERE h.cliente_Dni is null GROUP BY c.dni order by c.apellidos"; 
          st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (SQLException ex) {
          System.out.println(ex.getMessage());
        } 
     return rs;
 }
 
 
  public ResultSet clientePorLote(int id_control){
     ResultSet rs = null;
     try {
          Connection con = conexion.dataSource.getConnection();
          String listar = "SELECT c.Apellidos, c.Nombres,c.barrio, c.calle, c.numero FROM cliente c LEFT JOIN cliente_tiene_lote f ON c.Dni = f.cliente_Dni where f.id_control = '"+id_control+"'"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return rs;
     }
  
     public void altaClientesXLotes(int dni, int id_control) throws SQLException{
         PreparedStatement stmt = null;
         Connection con = null;
        try {
            con = conexion.dataSource.getConnection();
            String insertar = "insert into cliente_tiene_lote (cliente_dni, id_control) values (?,?)";
            stmt = con.prepareStatement(insertar);
            stmt.setInt(1, dni);
            stmt.setInt(2, id_control);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }finally{
            con.close();
            if(stmt!=null){
               stmt.close();
            }
        }
    }
     
     public void altaClientesXDpto(int dni, int id_control){
        try {
            Connection con = conexion.dataSource.getConnection();
            String insertar = "insert into cliente_tiene_dpto (cliente_dni, id_control) values (?,?)";
            PreparedStatement stmt = con.prepareStatement(insertar);
            stmt.setInt(1, dni);
            stmt.setInt(2, id_control);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
    }
 
    public int altaCliente(int dni, String apellidos, String nombres, Date fecha_nacimiento, String barrio, String calle, int numero, String telefono1, String telefono2, String trabajo) throws SQLException{
       int filasAfectadas=0;
       PreparedStatement ps = null;
       Connection con = null;
       try {
         con = conexion.dataSource.getConnection();
         String insertar = "Insert into cliente(dni, apellidos, nombres, fecha_nacimiento, barrio, calle, numero, telefono1, telefono2, trabajo) values ('"+dni+"','"+apellidos+"','"+nombres+"','"+fecha_nacimiento+"','"+barrio+"','"+calle+"','"+numero+"','"+telefono1+"','"+telefono2+"','"+trabajo+"')";
         ps = con.prepareStatement(insertar);
         filasAfectadas = ps.executeUpdate();         
       } catch (SQLException e) { 
          //--------1062 es el codigo de error para claves duplicadas------//
         if(e.getErrorCode() == 1062){
           filasAfectadas = 0; 
       }         
       }finally{
         con.close();
         if(ps!=null){
               ps.close();
         }
     }
     return filasAfectadas;
 }
 
 public void eliminarCliente(int dni){
     try {
         Connection con = conexion.dataSource.getConnection();
         String eliminar = "delete from cliente where dni = '"+dni+"'";
         PreparedStatement ps = con.prepareStatement(eliminar);
         ps.executeUpdate();
     } catch (Exception e) {
         System.out.println(e.getMessage());
     }
 }
 public void editarCliente(int dni, String apellidos, String nombres, Date fecha_nacimiento, String barrio, String calle, int numero, String telefono1, String telefono2, String trabajo, int clave_cliente){
        try {
            Connection con = conexion.dataSource.getConnection();
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
          Connection con = conexion.dataSource.getConnection();
          String listar = "SELECT  c.apellidos, c.nombres, c.fecha_nacimiento, c.dni, c.barrio, c.calle, c.numero, c.telefono1, c.telefono2, c.trabajo, r.apellidos, r.nombres, r.telefono, r.parentesco from cliente c inner join referencia r on c.dni=r.cliente_dni where dni = '"+dni+"'"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return rs;
 }
 
   public List<ClientesPorCriterio> clientesPorPropietarios(String apellido, String nombre){
     ResultSet rs = null;
     Connection connection = null;
     List<ClientesPorCriterio> clientesPorPropietario = new ArrayList<>();
     try {
          connection = conexion.dataSource.getConnection();
          String listar = "SELECT DISTINCT c.Dni, c.Apellidos, c.Nombres,c.fecha_nacimiento, c.barrio, c.calle, c.numero, c.Telefono1, c.telefono2, c.trabajo, cl.baja, f.Id_control, f.cantidad_cuotas, f.gastos, f.bolsa_cemento, f.fecha_actualizacion, f.lote_Barrio, f.lote_Manzana, f.lote_Parcela from ((((cliente c INNER join cliente_tiene_lote cl on c.dni=cl.cliente_dni) INNER join ficha_control_lote f on cl.id_control=f.id_control) INNER join lote l on f.lote_barrio=l.barrio) INNER join lote l2 on l2.manzana=f.lote_manzana) INNER join lote l3 on l3.parcela=f.lote_parcela where l3.propietario_Apellidos='"+apellido+"' and l3.propietario_Nombres='"+nombre+"'"; 
          Statement st = connection.createStatement();
          rs = st.executeQuery(listar);
          while (rs.next()) {
            ClientesPorCriterio cpp = new ClientesPorCriterio();
            cpp.setDni(rs.getInt(1));
            cpp.setApellidos(rs.getString(2));
            cpp.setNombres(rs.getString(3));
            cpp.setFecha_nacimiento(rs.getDate(4));
            cpp.setBarrio_cliente(rs.getString(5));
            cpp.setCalle_cliente(rs.getString(6));
            cpp.setNro_cliente(rs.getInt(7));
            cpp.setTelefono1(rs.getString(8));
            cpp.setTelefono2(rs.getString(9));
            cpp.setTrabajo(rs.getString(10));
            cpp.setBaja(rs.getInt(11));
            cpp.setIdControl(rs.getString(12));
            cpp.setCantidad_cuotas(rs.getInt(13));
            cpp.setGastos(rs.getBigDecimal(14));
            cpp.setBolsa_cemento(rs.getBigDecimal(15));
            cpp.setFecha_actualizacion(rs.getDate(16));
            cpp.setBarrio(rs.getString(17));
            cpp.setManzana(rs.getString(18));
            cpp.setParcela(rs.getString(19));
            clientesPorPropietario.add(cpp);
         }
        } catch (SQLException ex) {
          System.out.println(ex.getMessage());
        }finally{
          try {
              connection.close();
          } catch (SQLException ex) {
              Logger.getLogger(PropietarioDAO.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
     return clientesPorPropietario;
 }

    
}
