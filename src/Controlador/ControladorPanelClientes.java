/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ClienteDAO;
import Vista.Dialogs.DialogClientes;
import Vista.Frame.Ventana;
import java.awt.Dialog;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marceloi7
 */
public class ControladorPanelClientes {
    
    DialogClientes vc;
    ClienteDAO cd = new ClienteDAO();
    private Object [] clientes;

    public ControladorPanelClientes(Ventana ventana) {        
        vc = new DialogClientes(ventana, true);
        llenarTabla();
        vc.setLocationRelativeTo(null);
        vc.setVisible(true);        
    }
    
    private void llenarTabla(){
        ResultSet rs;
        rs = cd.clientesPorLotes();
        DefaultTableModel model = (DefaultTableModel) vc.tablaDialogClientes.getModel();
        model.setRowCount(0);
        try {
            while(rs.next()){
                String apellido = rs.getString(3);
                String nombre = rs.getString(3);
                String documento = rs.getString(1);
                clientes = new Object[] {apellido, nombre, documento};
                model.addRow(clientes); 
            }
        }
            catch(Exception e){
                    
                    }
        
    }
}
