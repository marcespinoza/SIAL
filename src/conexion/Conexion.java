/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import javax.swing.JOptionPane;
import org.apache.commons.dbcp.BasicDataSource;

/**
 *
 * @author Marcelo
 */
public class Conexion {
    
    public Connection con = null;
    public Statement st;
    public ResultSet rs;
    public String consulta;
    public String db = "miprimercasa";
    public String url = "jdbc:mysql://localhost:3306/miprimercasa?zeroDateTimeBehavior=convertToNull";
    public String user = "root";
    public String root = "";        
    public DataSource dataSource;        
    
    
    public Conexion(){
        getConexion();
    }
    
    public Connection getConexion(){    
          
     BasicDataSource basicDataSource = new BasicDataSource();
     basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
     basicDataSource.setUsername(user);
     basicDataSource.setPassword(root);
     basicDataSource.setUrl(url); 
     try{
        Class.forName("com.mysql.jdbc.Driver");
        con = basicDataSource.getConnection();
        //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/miprimercasa?zeroDateTimeBehavior=convertToNull","root","");
        st =(Statement) con.createStatement();
     }catch (Exception e){
        JOptionPane.showMessageDialog(null, "Error de conexion");
        System.out.println(e.getMessage());
     }
     return con; 
}
    
    public void cerrarConexion(){
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
