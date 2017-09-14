/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.CuotaDAO;
import Modelo.RendererTablaCuota;
import Vista.Frame.Ventana;
import Vista.Dialogs.AltaCuota;
import Vista.Panels.Clientes;
import Vista.Panels.DetalleCuota;
import java.awt.CardLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcelo
 */
public class ControladorDetalleCuota implements ActionListener{
    
    RendererTablaCuota r = new RendererTablaCuota();
    DetalleCuota vistaDetallePago = new DetalleCuota();
    CuotaDAO dp = new CuotaDAO();
    Object [] detallePago;
    String apellido;
    String nombre;
    int id_control;
    
    public ControladorDetalleCuota(DetalleCuota vistaDetallePago, String apellido, String nombre, String telefono, String barrio,String calle,int numero,int id_control) {
        this.vistaDetallePago = vistaDetallePago;
        this.apellido=apellido;
        this.nombre=nombre;
        this.id_control=id_control;
        vistaDetallePago.volverBtn.addActionListener(this);
        vistaDetallePago.agregarPagoBtn.addActionListener(this);
        vistaDetallePago.generarReciboBtn.addActionListener(this);
        vistaDetallePago.nombreLabel.setText(this.nombre);
        vistaDetallePago.apellidoLabel.setText(this.apellido);
        vistaDetallePago.direccionLabel.setText(barrio +", "+ calle +" "+ numero);
        vistaDetallePago.telefonoLabel.setText(telefono);
        this.vistaDetallePago.tablaDetallePago.setDefaultRenderer(Object.class, r);
        llenarTabla(id_control);
    }
    
   public void llenarTabla(int idControl){
       int num_cuota=0;
        ResultSet rs = dp.listaDetalleCuota(idControl);
        DefaultTableModel model = (DefaultTableModel) vistaDetallePago.tablaDetallePago.getModel();
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
             //cl.previous(Ventana.panelPrincipal);
             Ventana.panelPrincipal.add(vistaDetallePago, "Detalle_pago");
             cl.show(Ventana.panelPrincipal, "Detalle_pago");
        }catch(Exception e){
        System.out.println(e.getMessage().toString());}  }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vistaDetallePago.volverBtn){
            CardLayout cl = (CardLayout)(Ventana.panelPrincipal.getLayout());
            cl.next(Ventana.panelPrincipal);
        }
        if(e.getSource() == vistaDetallePago.agregarPagoBtn){
           ControladorAltaPago cac = new ControladorAltaPago((Frame) SwingUtilities.getWindowAncestor(vistaDetallePago), id_control, vistaDetallePago.tablaDetallePago.getRowCount());
            llenarTabla(id_control);
        }
        if(e.getSource() == vistaDetallePago.generarReciboBtn){
            
            int row = vistaDetallePago.tablaDetallePago.getSelectedRow();
            switch(row){
                case -1:
                JOptionPane.showMessageDialog(null, "Seleccione un pago", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
                break;
                case 0:
                JOptionPane.showMessageDialog(null, "Fila no válida", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
                break;
                default:
                int nro_cuota = Integer.parseInt((vistaDetallePago.tablaDetallePago.getValueAt(vistaDetallePago.tablaDetallePago.getModel().getRowCount()-1, 0)).toString());
                 new ControladorRecibo((Frame) SwingUtilities.getWindowAncestor(vistaDetallePago), id_control, nro_cuota, vistaDetallePago.tablaDetallePago.getModel().getValueAt(row, 12).toString());           
            }
           
        }
    }
    
}
