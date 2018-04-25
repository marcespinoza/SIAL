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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

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
         Connection con = conexion.dataSource.getConnection();
         String insertar = "Insert into referencia (telefono, apellidos, nombres, parentesco, cliente_dni) values ('"+telefono+"','"+apellidos+"','"+nombres+"','"+parentesco+"','"+dni+"') ";
         PreparedStatement ps = con.prepareStatement(insertar);
         ps.execute();
     } catch (Exception e) {
         System.out.println(e.getMessage());
     }
 }
 public void editarReferencia(int dni, String telefono,String apellidos, String nombres,  String parentesco, String clave_referencia){
        try {
            Connection con = conexion.dataSource.getConnection();
            String query = "UPDATE referencia SET cliente_dni = ?, "
                    + " apellidos = ?, "
                    + " nombres = ?, "
                    + " telefono = ?, "
                    + " parentesco = ? "
                    + " WHERE telefono = '"+clave_referencia+"'";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, dni);
            preparedStmt.setString(2, apellidos);
            preparedStmt.setString(3, nombres);
            preparedStmt.setString(4, telefono);
            preparedStmt.setString(5, parentesco);
            preparedStmt.executeUpdate();            
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReferenciaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
 }
 public ResultSet obtenerReferencia(int dni){
     ResultSet rs = null;
     try {
          Connection con = conexion.dataSource.getConnection();
          String listar = "SELECT apellidos, nombres, telefono, parentesco from referencia where cliente_dni = '"+dni+"' "; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return rs;

 }  
    
}
