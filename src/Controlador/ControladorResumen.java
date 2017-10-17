/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.MinutaDAO;
import Vista.Panels.Resumen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;


public class ControladorResumen implements ActionListener{
    
    Resumen vistaResumen;
    private Object [] resumen;
    MinutaDAO md = new MinutaDAO();

    public ControladorResumen(Resumen vistaResumen) {
        this.vistaResumen=vistaResumen;
        this.vistaResumen.buscar.addActionListener(this);
    }
   
    public void llenarTabla(int añoDesde, int mesDesde, int añoHasta, int mesHasta){
        ResultSet rs = null;
        rs = md.minutasPorMes(añoDesde, añoHasta, mesDesde, mesHasta);
        DefaultTableModel model = (DefaultTableModel) vistaResumen.tablaResumen.getModel();
        model.setRowCount(0);
        try {
            while(rs.next()){
                String total = rs.getString(1);
                String cuota = rs.getString(2);
                String mes = rs.getString(3);
                resumen = new Object[] {total, cuota, mes};
                model.addRow(resumen);   
            } 
          
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
   
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vistaResumen.buscar){
            System.out.println(vistaResumen.añoDesde.getYear()+""+vistaResumen.mesDesde.getMonth());
            llenarTabla(vistaResumen.añoDesde.getYear(), vistaResumen.añoHasta.getYear(), vistaResumen.mesDesde.getMonth()+1,vistaResumen.mesHasta.getMonth()+1);
        }
    }
    
}
