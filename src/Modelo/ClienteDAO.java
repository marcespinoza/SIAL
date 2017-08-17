/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.*;



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
 public void clientesPorLotes(){}
 public void altaCliente(int dni, String apellidos, String nombres, Date fech_nacimiento, String barrio, String calle, int numero, String telefono1, String telefono2, String trabajo){
     String rptaRegistro = null;
     try {
         Connection con = conexion.getConexion();
         String insertar = "Insert into cliente(dni, nombres, apellidos, fecha_nacimiento, barrio, calle, numero, telefono1, telefono2, trabajo) values ('"+dni+"','"+nombres+"','"+apellidos+"','"+fech_nacimiento+"','"+barrio+"','"+calle+"','"+numero+"','"+telefono1+"','"+telefono2+"','"+trabajo+"') ";
         PreparedStatement ps = con.prepareStatement(insertar);
         ps.execute();
     } catch (Exception e) {
     }
 }
 public void eliminarCliente(){}
 public void editarCliente(){}
 public void buscarCliente(){}
    
}
