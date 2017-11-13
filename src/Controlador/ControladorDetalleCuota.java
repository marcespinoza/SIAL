/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import static Controlador.ControladorMinuta.IMG;
import Modelo.CuotaDAO;
import Modelo.DchoPosesionDAO;
import Modelo.FichaControlDAO;
import Modelo.RendererTablaCuota;
import Vista.Frame.Ventana;
import Vista.Panels.DetalleCuota;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.CardLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Marcelo
 */
public class ControladorDetalleCuota implements ActionListener, TableModelListener{
    
    RendererTablaCuota r = new RendererTablaCuota();
    DetalleCuota dc = new DetalleCuota();
    CuotaDAO cd = new CuotaDAO();
    DchoPosesionDAO dp = new DchoPosesionDAO();
    FichaControlDAO fcd = new FichaControlDAO();
    Object [] detallePago;
    Object [] dchoPosesion;
    String apellido;
    String nombre;
    int id_control, nro_cuotas;
    JFileChooser chooser;
    File f;
    File configFile = new File("config.properties");
    Boolean cuota = false;
    Boolean posesion = false;
    public static final String IMG = "src/Imagenes/logo_reporte.png";
    ResultSet detalleCuota;
    
    public ControladorDetalleCuota(int nro_cuotas,String apellido, String nombre, String telefono, String barrio,String calle,int numero,int id_control) {
        this.apellido=apellido;
        this.nombre=nombre;
        this.id_control=id_control;
        this.nro_cuotas=nro_cuotas;
        dc.tablaDetallePago.getModel().addTableModelListener(this);
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
        dc.resumenCliente.addActionListener(this);
        dc.nombreLabel.setText(this.nombre);
        dc.apellidoLabel.setText(this.apellido);
        dc.direccionLabel.setText(barrio +", "+ calle +" "+ numero);
        dc.telefonoLabel.setText(telefono);
        dc.tablaDetallePago.setDefaultRenderer(Object.class, r);
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
                dchoPosesion= new Object[] {num_cuota,fecha, monto,gastos,detalle};                    
                model.addRow(dchoPosesion); 
                num_cuota ++;
            }}catch(Exception e){
        System.out.println(e.getMessage().toString());
        } 
   }  
    
   public void llenarTabla(int idControl){
        detalleCuota = cd.listaDetalleCuota(idControl);
        DefaultTableModel model = (DefaultTableModel) dc.tablaDetallePago.getModel();
        model.setRowCount(0);
        try {
            while(detalleCuota.next()){
                String nro_cuota = detalleCuota.getString(1);
                String fecha = detalleCuota.getString(2);
                String detalle = detalleCuota.getString(3);
                String cuota_pura = detalleCuota.getString(4);
                String gastos_admin = detalleCuota.getString(5);                
                String debe = detalleCuota.getString(6);
                String haber = detalleCuota.getString(7);                
                String saldo = detalleCuota.getString(8);
                String cemento_debe = detalleCuota.getString(9);
                String cemento_haber = detalleCuota.getString(10);
                String cemento_saldo = detalleCuota.getString(11);
                String observaciones = detalleCuota.getString(12);
                String tipo_pago = detalleCuota.getString(13);
                detallePago= new Object[] {nro_cuota, fecha, detalle, cuota_pura, gastos_admin, debe, haber, saldo, cemento_debe, cemento_haber,cemento_saldo, observaciones, tipo_pago};                    
                model.addRow(detallePago); 
            }
            //---------Reseteo resultset para recorrerlo nuevamente cuando quiero---------//
            //---------crear resumen de cliente--------------------------------------------//
            
            detalleCuota.beforeFirst();
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
           ControladorAltaPago cac = new ControladorAltaPago((Frame) SwingUtilities.getWindowAncestor(dc), id_control, dc.tablaDetallePago.getRowCount(), nro_cuotas);
            llenarTabla(id_control);
            llearTablaDchoPosesion(id_control);
        }
        if(e.getSource() == dc.resumenCliente){
            generarResumenPdf();
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

    @Override
    public void tableChanged(TableModelEvent e) {
        //------Solo trata cuando cambia el valor de una celda--------//
         if (e.getType() == TableModelEvent.UPDATE) {
          int row = e.getFirstRow();
          int column = e.getColumn();
          TableModel tableModel = (TableModel)e.getSource();
          Object data = tableModel.getValueAt(row, column);       
          cd.actualizarCuota(data.toString(),Integer.parseInt(dc.tablaDetallePago.getModel().getValueAt(row, 0).toString()) ,id_control);}
    }
    
    private void generarResumenPdf(){
            Document document= new Document(PageSize.A4);
            DateFormat fecha1 = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date date = new java.util.Date();            
            Font f=new Font(Font.FontFamily.TIMES_ROMAN,10.0f,0,null);
            ResultSet rs = fcd.obtenerFichaControl(id_control); 
            
        try {
            rs.next();
            PdfWriter.getInstance(document, new FileOutputStream(new File(dc.path.getText(), "Resumen "+dc.nombreLabel.getText()+" "+ dc.apellidoLabel.getText()+".pdf")));
            document.open();       
            Image image = Image.getInstance(IMG); 
            image.scaleAbsolute(70, 70);
            document.add(new Chunk(image, 0, -55f));
            Chunk titulo = new Chunk("Resumen cliente");
            titulo.setUnderline(0.1f, -2f); 
            Phrase ph1 = new Phrase(titulo);
            Paragraph ph = new Paragraph();
            ph.add(ph1);
            ph.setAlignment(Element.ALIGN_CENTER);
            document.add(ph);
            document.add( Chunk.NEWLINE );
            document.add( Chunk.NEWLINE );
            document.add( Chunk.NEWLINE );
            document.add(new Paragraph("Apellido y nombres: "+dc.nombreLabel.getText()+" "+ dc.apellidoLabel.getText(),f));
            document.add(new Paragraph("Direcciòn: "+dc.direccionLabel.getText(),f));
            document.add( Chunk.NEWLINE );
            document.add(new Paragraph("Propiedad: "+rs.getString(6)+" - Mz: "+rs.getString(7)+" - Pc: "+rs.getString(8),f));
            document.add( Chunk.NEWLINE );
            PdfPTable table = new PdfPTable(3); 
            table.setTotalWidth(new float[]{ 1,1,2});
            table.setWidthPercentage(100);
            PdfPCell nro_cuota = new PdfPCell(new Paragraph("Nro. cuota",f));
            PdfPCell fecha_pago = new PdfPCell(new Paragraph("Fch. pago",f));
            PdfPCell saldo = new PdfPCell(new Paragraph("Saldo",f));
            nro_cuota.setHorizontalAlignment(Element.ALIGN_CENTER);
            fecha_pago.setHorizontalAlignment(Element.ALIGN_CENTER);
            saldo.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(nro_cuota);
            table.addCell(fecha_pago);
            table.addCell(saldo);
            document.add(table); 
            while(detalleCuota.next()){
                  PdfPTable table2 = new PdfPTable(3);            
                  table2.setTotalWidth(new float[]{ 1,1,2});
                  table2.setWidthPercentage(100);
                  table2.addCell(new PdfPCell(new Paragraph(detalleCuota.getString(1),f)));
                  table2.addCell(new PdfPCell(new Paragraph(detalleCuota.getString(2),f)));
                  table2.addCell(new PdfPCell(new Paragraph(detalleCuota.getString(8),f)));
                  document.add(table2);
              }
            document.close();
        }            
            catch (DocumentException ex) {
            Logger.getLogger(DetalleCuota.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ControladorDetalleCuota.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorDetalleCuota.class.getName()).log(Level.SEVERE, null, ex);
        }}
    
    
}
