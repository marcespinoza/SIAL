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
 * @author Marcelo
 */
public class ReferenciaDAO {
    
    Conexion conexion;
    
    public ReferenciaDAO(){
     conexion = new Conexion();
    }
    
  public void obtenerReferencias(){}
 public void altaReferencia(String telefono, String apellidos, String nombres, String parentesco, int dni){
     String rptaRegistro = null;
     try {
         Connection con = conexion.getConexion();
         String insertar = "Insert into referencia(telefono, apellidos, nombres, parentesco, cliente_dni) values ('"+telefono+"','"+nombres+"','"+apellidos+"','"+parentesco+"','"+dni+"') ";
         PreparedStatement ps = con.prepareStatement(insertar);
         ps.execute();
     } catch (Exception e) {
         System.out.println(e.getMessage());
     }
 }
 public void editarReferencia(){}
 public ResultSet obtenerReferencia(int dni){
     ResultSet rs = null;
     try {
          Connection con = conexion.getConexion();
          String listar = "SELECT apellidos, nombres, telefono, parentesco from referencia where cliente_dni = '"+dni+"' "; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return rs;

 }  
    
}
