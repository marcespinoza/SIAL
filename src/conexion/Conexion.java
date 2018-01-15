/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Marcelo
 */
public class Conexion {
    
    public Connection con = null;
    public Statement st;
    public ResultSet rs;
    String consulta;
    private static Conexion conexion;
    
    public Conexion(){
        getConexion();
    }
    
    public Connection getConexion(){      
   
     try{
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/miprimercasa?zeroDateTimeBehavior=convertToNull","root","");
        st =(Statement) con.createStatement();
     }catch (Exception e){
        JOptionPane.showMessageDialog(null, "Error de conexion");
        System.out.println(e.getMessage());
     }
     return con; 
}
    
}
