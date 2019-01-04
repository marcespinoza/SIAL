/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;

/**
 *
 * @author Marcelo
 */
public class Conexion {
    
    public String db = "miprimercasa";
    public String url = "jdbc:mysql://localhost:3306/miprimercasa?zeroDateTimeBehavior=convertToNull";
    public String user = "root";
    public String root = "MiPrimerCasa";        
    public DataSource dataSource;       
    static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(Conexion.class.getName());  
    
    public Conexion(){
        getConexion();
    }
    
    public void getConexion(){      
     BasicDataSource basicDataSource = new BasicDataSource();
     basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
     basicDataSource.setUsername(user);
     basicDataSource.setPassword(root);
     basicDataSource.setUrl(url); 
     dataSource = basicDataSource;
}
    
}
