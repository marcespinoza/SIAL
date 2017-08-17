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
         String insertar = "Insert into referencia(telefono, apellidos, nombres, parentesco, dni) values ('"+telefono+"','"+nombres+"','"+apellidos+"','"+parentesco+"','"+dni+"') ";
         PreparedStatement ps = con.prepareStatement(insertar);
         ps.execute();
     } catch (Exception e) {
     }
 }
 public void editarReferencia(){}
 public void buscarReferencia(){}  
    
}
