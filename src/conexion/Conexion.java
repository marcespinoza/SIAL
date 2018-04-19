/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import Controlador.ControladorCliente;
import java.net.URL;
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
import org.apache.log4j.PropertyConfigurator;

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
    static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ControladorCliente.class.getName());
    URL url2 = getClass().getResource("log4j.properties");    
    
    public Conexion(){
        getConexion();
    }
    
    public Connection getConexion(){    
     PropertyConfigurator.configure(url2);      
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
        log.info("Conexion exitosa");
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
