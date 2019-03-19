/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import conexion.Conexion;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marceloi7
 */
public class ActualizacionCementoDAO {
    
     Conexion conexion;

    public ActualizacionCementoDAO() {
        conexion = new Conexion();
    }
     
         
     public void actualizarCemento(String id_control, Date fecha, BigDecimal precio_anterior, BigDecimal precio_actualizado) {
         PreparedStatement stmt = null;
         Connection con = null;
        try {
            con = conexion.dataSource.getConnection();
            String insertar = "insert into actualizacion_cemento (id_control, fecha, precio_anterior, precio_actualizado) values (?,?,?,?)";
            stmt = con.prepareStatement(insertar);
            stmt.setString(1, id_control);
            stmt.setDate(2, fecha);
            stmt.setBigDecimal(3, precio_anterior);
            stmt.setBigDecimal(4, precio_actualizado);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }finally{
             try {
                 con.close();
                 if(stmt!=null){
                     stmt.close();
                 }} catch (SQLException ex) {
                 Logger.getLogger(ActualizacionCementoDAO.class.getName()).log(Level.SEVERE, null, ex);
             }
        }
    }
    
}
