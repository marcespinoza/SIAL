/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Marcelo Espinoza
 */
public class ReciboDAO {
    
    Conexion conexion;

    public ReciboDAO() {
        conexion = new Conexion();
    }
    
     public int altaRecibo(String tipo_compra, String dimension, int cantidad_cuotas, double cuota_pura, double gastos, double bolsa_cemento, int dni, String barrio, int manzana, int parcela){
         int id_control = 1;
     try {
          Connection con = conexion.getConexion();
          String insertar = "Insert into ficha_control(tipo_compra, dimension, cantidad_cuotas, cuota_pura, gastos, bolsa_cemento, cliente_dni, lote_barrio, lote_manzana, lote_parcela) values ('"+tipo_compra+"','"+dimension+"','"+cantidad_cuotas+"','"+cuota_pura+"','"+gastos+"','"+bolsa_cemento+"','"+dni+"','"+barrio+"','"+manzana+"','"+parcela+"')";
          PreparedStatement ps = con.prepareStatement(insertar, Statement.RETURN_GENERATED_KEYS);
          ps.executeUpdate();  
          ResultSet rs = ps.getGeneratedKeys();  
          id_control = rs.next() ? rs.getInt(1) : 0;
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }
     //********Retorno id_control generado por la inserción********
     return id_control;
    }
    
}
