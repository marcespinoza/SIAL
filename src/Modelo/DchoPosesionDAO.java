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
          Connection con = conexion.dataSource.getConnection();
          String listar = "SELECT fecha, monto,gastos,cemento_debe, cemento_haber, cemento_saldo, detalle from derecho_posesion where id_control='"+id_control+"'"; 
          Statement st = con.createStatement();
          rs = st.executeQuery(listar);
        } catch (Exception e) {
        }
     return rs;
    }
    
    public void altaDchoPosesion(Date date, BigDecimal monto, BigDecimal gastos, BigDecimal cementoDebe, BigDecimal cementoHaber, BigDecimal cementoSaldo, String detalle, int id_control){
        try {
            Connection con = conexion.dataSource.getConnection();
            String query = " insert into derecho_posesion (fecha, monto,gastos,cemento_debe, cemento_haber, cemento_saldo, detalle, id_control)"
                    + " values (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setDate(1, date);
            preparedStmt.setBigDecimal(2, monto);
            preparedStmt.setBigDecimal(3, gastos);
            preparedStmt.setBigDecimal(4, cementoDebe);
            preparedStmt.setBigDecimal(5, cementoHaber);
            preparedStmt.setBigDecimal(6, cementoSaldo);
            preparedStmt.setString(7, detalle);
            preparedStmt.setInt(8, id_control);
            preparedStmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DchoPosesionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    
}
