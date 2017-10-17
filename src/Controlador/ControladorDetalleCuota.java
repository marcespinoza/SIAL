/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.CuotaDAO;
import Modelo.DchoPosesionDAO;
import Modelo.RendererTablaCuota;
import Vista.Frame.Ventana;
import Vista.Panels.DetalleCuota;
import java.awt.CardLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Properties;
import javax.swing.JFileChooser;
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
    CuotaDAO cd = new CuotaDAO();
    DchoPosesionDAO dp = new DchoPosesionDAO();
    Object [] detallePago;
    Object [] dchoPosesion;
    String apellido;
    String nombre;
    int id_control;
    JFileChooser chooser;
    File f;
    File configFile = new File("config.properties");
    Boolean cuota = false;
    Boolean posesion = false;
    
    public ControladorDetalleCuota(DetalleCuota dc, String apellido, String nombre, String telefono, String barrio,String calle,int numero,int id_control) {
        this.dc = dc;
        this.apellido=apellido;
        this.nombre=nombre;
        this.id_control=id_control;
        dc.tablaDchoPosesion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
             posesion = true;
             cuota = false;
             dc.tablaDetallePago.getSelectionModel().clearSelection();
        }
        });
        dc.tablaDetallePago.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
             posesion = false;
             cuota=true;
             dc.tablaDchoPosesion.getSelectionModel().clearSelection();
        }
        });
        dc.guardar.addActionListener(this);
        dc.volverBtn.addActionListener(this);
        dc.agregarPagoBtn.addActionListener(this);
        dc.generarReciboBtn.addActionListener(this);
        dc.nombreLabel.setText(this.nombre);
        dc.apellidoLabel.setText(this.apellido);
        dc.direccionLabel.setText(barrio +", "+ calle +" "+ numero);
        dc.telefonoLabel.setText(telefono);
        this.dc.tablaDetallePago.setDefaultRenderer(Object.class, r);
        llenarTabla(id_control);
        llearTablaDchoPosesion(id_control);
        cargarPathMinuta();
    }
    
    public void cargarPathMinuta() {
        try {
          FileReader reader = new FileReader(configFile);
          Properties props = new Properties();
            props.load(reader); 
          String pathMinuta = props.getProperty("pathRecibo");
          dc.path.setText(pathMinuta);
         reader.close();
     } catch (FileNotFoundException ex) {
    // file does not exist
     } catch (IOException ex) {
    // I/O error
     }
     } 
    
   public void llearTablaDchoPosesion(int id_control){
       int num_cuota=1;
        ResultSet rs = dp.listarCuenta(id_control);
        DefaultTableModel model = (DefaultTableModel) dc.tablaDchoPosesion.getModel();
        model.setRowCount(0);
        try {
            while(rs.next()){
                String fecha = rs.getString(1);
                String monto = rs.getString(2);
                String gastos = rs.getString(3);    
                String detalle = rs.getString(4); 
                dchoPosesion= new Object[] {num_cuota,fecha, monto,gastos, detalle};                    
                model.addRow(dchoPosesion); 
                num_cuota ++;
            }}catch(Exception e){
        System.out.println(e.getMessage().toString());
        } 
   }  
    
   public void llenarTabla(int idControl){
       int num_cuota=0;
        ResultSet rs = cd.listaDetalleCuota(idControl);
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
             System.out.println(e.getMessage().toString());
        } 
   }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == dc.volverBtn){
            CardLayout cl = (CardLayout)(Ventana.panelPrincipal.getLayout());
            cl.next(Ventana.panelPrincipal);
        }
        if(e.getSource() == dc.agregarPagoBtn){
           ControladorAltaPago cac = new ControladorAltaPago((Frame) SwingUtilities.getWindowAncestor(dc), id_control, dc.tablaDetallePago.getRowCount());
            llenarTabla(id_control);
            llearTablaDchoPosesion(id_control);
        }
        if(e.getSource() == dc.generarReciboBtn){
          if(!dc.path.getText().equals("")){        
             if(cuota){
            int row = dc.tablaDetallePago.getSelectedRow();
            switch(row){
                case -1:
                JOptionPane.showMessageDialog(null, "Seleccione una cuota", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
                break;
                case 0:
                JOptionPane.showMessageDialog(null, "Cuota no válida", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
                break;
                default:
                 new ControladorRecibo((Frame) SwingUtilities.getWindowAncestor(dc), id_control, dc ,row, 1);           
            }
             }else if(posesion){
                  int row = dc.tablaDchoPosesion.getSelectedRow();
                 new ControladorRecibo((Frame) SwingUtilities.getWindowAncestor(dc), id_control, dc ,row, 0);  
             }
          }else{
            JOptionPane.showMessageDialog(null, "Debe seleccionar ubicación donde guardar el recibo", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
            }
        }
        if(e.getSource() == dc.guardar){
            chooser = new JFileChooser();
                 chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.showOpenDialog(chooser);
                f = chooser.getSelectedFile();
                String path = f.getAbsolutePath();
                dc.path.setText(path);
                 try {
                Properties props = new Properties();
                props.setProperty("pathRecibo", path);
                FileWriter writer = new FileWriter(configFile);
                props.store(writer, "config");
                writer.close();
                } catch (FileNotFoundException ex) {
                 // file does not exist
                } catch (IOException ex) {
                   // I/O error
                }
        }
        
    }
    
}
