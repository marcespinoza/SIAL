/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.MinutaDAO;
import Vista.Panels.Minuta;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcelo Espinoza
 */
public class ControladorMinuta implements MouseListener {
    
    MinutaDAO md = new MinutaDAO();
    Minuta vistaMinuta;
    private Object [] minuta;
     private Object [] fechaMinuta;

    public ControladorMinuta(Minuta minuta) {
        this.vistaMinuta=minuta;
        this.vistaMinuta.tablaFechaMinuta.addMouseListener(this);
        llenarTablaFecha();
    }
    
    public void llenarTabla(ResultSet rs){
        DefaultTableModel model = (DefaultTableModel) vistaMinuta.tablaMinuta.getModel();
        model.setRowCount(0);
        try {
            while(rs.next()){
                String fecha_minuta = rs.getString(1);
                String apellidos = rs.getString(2);
                String nombres = rs.getString(3);
                String mzpc = rs.getString(4)+rs.getString(5);  
                String cobrado = rs.getString(6);
                String rendido = rs.getString(7);     
                String nro_cuota = rs.getString(8);
                String observaciones = rs.getString(9);
                String nro_recibo = rs.getString(10);
                minuta = new Object[] {fecha_minuta,nro_recibo, apellidos, nombres, mzpc, cobrado, rendido, nro_cuota, observaciones};
                model.addRow(minuta);   
            } }catch (SQLException e) {
            System.out.println(e.getMessage());
        }}
    
    public void llenarTablaFecha(){
        ResultSet rs = md.obtenerFecha();
        DefaultTableModel model = (DefaultTableModel) vistaMinuta.tablaFechaMinuta.getModel();
        model.setRowCount(0);
        int nro = 1;
        try {
            while(rs.next()){
                String fecha_minuta = rs.getString(1);
                fechaMinuta = new Object[] {nro,fecha_minuta};
                model.addRow(fechaMinuta); 
                nro ++;
            } 
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }}

    
    public class SwingWorker extends javax.swing.SwingWorker<Void, Void>{
         
         ResultSet rs;

        @Override
        protected Void doInBackground() throws Exception {    
            int row = vistaMinuta.tablaFechaMinuta.getSelectedRow();
            rs = md.obtenerMinutasPorFecha(vistaMinuta.tablaFechaMinuta.getModel().getValueAt(row,1).toString());
            return null;
        }

       @Override
       public void done() { 
           llenarTabla(rs);
       }
    
}
    
    @Override
    public void mouseClicked(MouseEvent e) {
         new SwingWorker().execute();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}
