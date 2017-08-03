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
    
    public Connection con;
    public Statement st;
    public ResultSet rs;
    String consulta;
    
    public Conexion(){
        getConexion();
    }
    
     public void getConexion(){
        
   
    try{
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/miprimercasa?zeroDateTimeBehavior=convertToNull","root","");
         // JOptionPane.showMessageDialog(null, "Conectado");
        st =(Statement) con.createStatement();
    }catch (Exception e){
        JOptionPane.showMessageDialog(null, "Error de conexion");
    }
}
    
}
