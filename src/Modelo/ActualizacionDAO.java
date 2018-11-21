/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Clases.Actualizacion;
import Clases.ClientesPorCriterio;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marceloi7
 */
public class ActualizacionDAO {
    
     Conexion conexion;
    
    public ActualizacionDAO(){
       conexion = new Conexion();
    }
    
    public List<Actualizacion> listaActualizaciones(int id_control){         
         ResultSet rs = null;
         Connection connection = null;
         List<Actualizacion> actualizaciones = new ArrayList<>();
       try {
         connection = conexion.dataSource.getConnection();
         String listar = "SELECT * from actualizacion a where a.id_control='"+id_control+"'";
         Statement st = connection.createStatement();
         rs = st.executeQuery(listar);
         while(rs.next()){
            Actualizacion a = new Actualizacion();
            a.setFecha(rs.getDate(2));
            a.setSaldo_anterior(rs.getBigDecimal(3));
            a.setSaldo_nuevo(rs.getBigDecimal(4));
            actualizaciones.add(a);
        }
       }catch (SQLException ex) {
          System.out.println(ex.getMessage());
       }finally{
         try {
           connection.close();
         } catch (SQLException ex) {
           Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
        return actualizaciones;   
    }
    
}
