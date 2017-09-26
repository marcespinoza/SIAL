/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.CuotaDAO;
import Modelo.RendererTablaCuota;
import Vista.Frame.Ventana;
import Vista.Panels.DetalleCuota;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcelo
 */
public class ControladorDetalleCuota implements ActionListener{
    
    RendererTablaCuota r = new RendererTablaCuota();
    DetalleCuota dc = new DetalleCuota();
    CuotaDAO dp = new CuotaDAO();
    Object [] detallePago;
    String apellido;
    String nombre;
    int id_control;
    
    public ControladorDetalleCuota(DetalleCuota dc, String apellido, String nombre, String telefono, String barrio,String calle,int numero,int id_control) {
        this.dc = dc;
        this.apellido=apellido;
        this.nombre=nombre;
        this.id_control=id_control;
        dc.volverBtn.addActionListener(this);
        dc.agregarPagoBtn.addActionListener(this);
        dc.generarReciboBtn.addActionListener(this);
        dc.nombreLabel.setText(this.nombre);
        dc.apellidoLabel.setText(this.apellido);
        dc.direccionLabel.setText(barrio +", "+ calle +" "+ numero);
        dc.telefonoLabel.setText(telefono);
        this.dc.tablaDetallePago.setDefaultRenderer(Object.class, r);
        llenarTabla(id_control);
    }
    
   public void llenarTabla(int idControl){
       int num_cuota=0;
        ResultSet rs = dp.listaDetalleCuota(idControl);
        DefaultTableModel model = (DefaultTableModel) dc.tablaDetallePago.getModel();
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
                detallePago= new Object[] {num_cuota, fecha, detalle, cuota_pura, gastos_admin, debe, haber, saldo, cemento_debe, cemento_haber,cemento_saldo, observaciones, tipo_pago};                    
                model.addRow(detallePago); 
                num_cuota ++;
            }
             CardLayout cl = (CardLayout)(Ventana.panelPrincipal.getLayout());
             Ventana.panelPrincipal.add(dc, "Detalle_pago");
             cl.show(Ventana.panelPrincipal, "Detalle_pago");
        }catch(Exception e){
        System.out.println(e.getMessage().toString());}  }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == dc.volverBtn){
            CardLayout cl = (CardLayout)(Ventana.panelPrincipal.getLayout());
            cl.next(Ventana.panelPrincipal);
        }
        if(e.getSource() == dc.agregarPagoBtn){
           ControladorAltaPago cac = new ControladorAltaPago((Frame) SwingUtilities.getWindowAncestor(dc), id_control, dc.tablaDetallePago.getRowCount());
            llenarTabla(id_control);
        }
        if(e.getSource() == dc.generarReciboBtn){
            
            int row = dc.tablaDetallePago.getSelectedRow();
            switch(row){
                case -1:
                JOptionPane.showMessageDialog(null, "Seleccione un pago", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
                break;
                case 0:
                JOptionPane.showMessageDialog(null, "Fila no válida", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
                break;
                default:
                 new ControladorRecibo((Frame) SwingUtilities.getWindowAncestor(dc), id_control, dc ,row);           
            }
           
        }
    }
    
}
