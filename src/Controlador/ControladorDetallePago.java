/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.DetallePagoDAO;
import Vista.Frame.Ventana;
import Vista.Panels.DetallePago;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcelo
 */
public class ControladorDetallePago implements ActionListener{
    
    DetallePago vistaDetallePago = new DetallePago();
    DetallePagoDAO dp = new DetallePagoDAO();
    private int idControl;
    Object [] detallePago;
    
    public ControladorDetallePago(DetallePago visDetallePago) {
        this.vistaDetallePago = visDetallePago;
        this.dp = dp;
        visDetallePago.volverBtn.addActionListener(this);
        visDetallePago.agregarPagoBtn.addActionListener(this);
    }
    
   public void llenarTabla(JTable jTable, String idControl){
        ResultSet rs = dp.listaDetallePago(idControl);
        DefaultTableModel model = (DefaultTableModel) vistaDetallePago.detallePagoTabla.getModel();
        model.setRowCount(0);
        try {
            while(rs.next()){
                String fecha = rs.getString(1);
                String detalle = rs.getString(2);
                String cuota_pura = rs.getString(3);
                String gastos_admin = rs.getString(4);                
                String debe = rs.getString(5);
                String haber = rs.getString(6);                
                String saldo = rs.getString(7);
                String cemento_debe = rs.getString(8);
                String cemento_haber = rs.getString(9);
                String cemento_saldo = rs.getString(10);
                String observaciones = rs.getString(11);
                String tipo_pago = rs.getString(12);
                System.out.println(rs.getString(8));
                detallePago= new Object[] {fecha, detalle, cuota_pura, gastos_admin, debe, haber, saldo, cemento_debe, cemento_haber,cemento_saldo, observaciones, tipo_pago};                    
                model.addRow(detallePago);   
                model.fireTableDataChanged();
            }
           Ventana.panelPrincipal.removeAll();
           Ventana.panelPrincipal.add(vistaDetallePago);
           Ventana.panelPrincipal.revalidate();
           Ventana.panelPrincipal.repaint();
        }catch(Exception e){}  }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vistaDetallePago.volverBtn){
            CardLayout cl = (CardLayout)(Ventana.panelPrincipal.getLayout());
            cl.previous(Ventana.panelPrincipal);
        }
        if(e.getSource() == vistaDetallePago.agregarPagoBtn){
            llenarTabla(vistaDetallePago.detallePagoTabla, "1");
        }
    }
    
}