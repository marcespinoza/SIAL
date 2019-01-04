/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Clases.Actualizacion;
import Clases.ClientesPorCriterio;
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
            a.setFecha(rs.getDate(3));
            a.setPorcentaje(rs.getByte(4));
            a.setSaldo_anterior(rs.getBigDecimal(5));
            a.setSaldo_nuevo(rs.getBigDecimal(6));
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
    
    public void altaActualizacion(int id_control, Date fecha, byte porcentaje, BigDecimal valor_anterior, BigDecimal valor_nuevo) throws SQLException{
         PreparedStatement stmt = null;
         Connection con = null;
        try {
            con = conexion.dataSource.getConnection();
            String insertar = "insert into actualizacion (id_control, fecha, porcentaje, saldo_anterior, saldo_nuevo) values (?,?,?,?,?)";
            stmt = con.prepareStatement(insertar);
            stmt.setInt(1, id_control);
            stmt.setDate(2, fecha);
            stmt.setByte(3, porcentaje);
            stmt.setBigDecimal(4, valor_anterior);
            stmt.setBigDecimal(5, valor_nuevo);
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
    
}
