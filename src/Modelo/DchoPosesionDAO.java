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
 * @author Marcelo Espinoza
 */
public class DchoPosesionDAO {
    
    Conexion conexion;

    public DchoPosesionDAO() {
        conexion = new Conexion();
    }
    
    public ResultSet listarCuenta(int id_control){
        ResultSet rs = null;
     try {
          Connection con = conexion.getConexion();
          String listar = "SELECT fecha, monto, detalle from derecho_posesion where id_control='"+id_control+"'"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
        }
     return rs;
    }
    
    public void altaDchoPosesion(Date date, double monto, String detalle, int id_control){
        try {
            Connection con = conexion.getConexion();
            String query = " insert into derecho_posesion (fecha, monto, detalle, id_control)"
                    + " values (?, ?, ?, ?)";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setDate(1, date);
            preparedStmt.setDouble(2, monto);
            preparedStmt.setString(3, detalle);
            preparedStmt.setInt(4, id_control);
            preparedStmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DchoPosesionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    
}
