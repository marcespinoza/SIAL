/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.CuotaDAO;
import Modelo.DchoPosesionDAO;
import Modelo.FichaControlDAO;
import Modelo.RendererTablaCuota;
import Modelo.RendererTablaDchoPosesion;
import Vista.Dialogs.ProgressDialog;
import Vista.Frame.Ventana;
import Vista.Panels.DetalleCuota;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
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
    RendererTablaDchoPosesion rdp = new RendererTablaDchoPosesion();
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
    FileInputStream fileIn = null;
    FileOutputStream fileOut = null;
    File configFile = new File("config.properties");
    Boolean cuota = false;
    Boolean posesion = false;
    public static final String IMG = "src/Imagenes/logo_reporte.png";
    ResultSet detalleCuota, derechoPosesion;
    
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
        dc.tablaDchoPosesion.setDefaultRenderer(Object.class, rdp);
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
        int num_cuota=0;
        derechoPosesion = dp.listarCuenta(id_control);
        DefaultTableModel model = (DefaultTableModel) dc.tablaDchoPosesion.getModel();
        model.setRowCount(0);
        try {
            while(derechoPosesion.next()){
                String fecha = derechoPosesion.getString(1);
                String monto = derechoPosesion.getString(2);
                String gastos = derechoPosesion.getString(3); 
                String cemento_debe = derechoPosesion.getString(4); 
                String cemento_haber = derechoPosesion.getString(5); 
                String cemento_saldo = derechoPosesion.getString(6); 
                String detalle = derechoPosesion.getString(7); 
                dchoPosesion= new Object[] {num_cuota,fecha, monto,gastos,cemento_debe, cemento_haber, cemento_saldo, detalle};                    
                model.addRow(dchoPosesion); 
                num_cuota ++;
            }
            derechoPosesion.beforeFirst();
        }catch(Exception e){
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
            new ControladorAltaPago((Frame) SwingUtilities.getWindowAncestor(dc), id_control, dc.tablaDetallePago.getRowCount(), nro_cuotas);
            llenarTabla(id_control);
            llearTablaDchoPosesion(id_control);
        }
        if(e.getSource() == dc.resumenCliente){
            if(dc.tablaDetallePago.getRowCount()==1 && dc.tablaDchoPosesion.getRowCount()==0){
               JOptionPane.showMessageDialog(null, "No hay datos para mostrar", "Atención", JOptionPane.INFORMATION_MESSAGE, null); }
            else{
                new GenerarResumen().execute();
            }
        }
        if(e.getSource() == dc.generarReciboBtn){
          if(!dc.path.getText().equals("")){
            //----verifica si esta seleccionado alguna fila de alguna de las dos tablas--------//  
              if(cuota || posesion){
              if(cuota){
               int row = dc.tablaDetallePago.getSelectedRow();
               switch(row){
                case 0:
                JOptionPane.showMessageDialog(null, "Cuota no válida", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
                break;
                default:
                new ControladorRecibo((Frame) SwingUtilities.getWindowAncestor(dc), id_control, dc ,row, 1);           
            }
             }else if(posesion){
                 int row = dc.tablaDchoPosesion.getSelectedRow();
                  switch(row){
                case 0:
                JOptionPane.showMessageDialog(null, "Fila no válida", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
                break;
                default:
                 new ControladorRecibo((Frame) SwingUtilities.getWindowAncestor(dc), id_control, dc ,row, 0);  
                }
               }
              }else{
               JOptionPane.showMessageDialog(null, "Seleccione un pago", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
              }
          }else{
            JOptionPane.showMessageDialog(null, "Debe seleccionar ubicación donde guardar el recibo", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
            }
        }
        if(e.getSource() == dc.guardar){
                chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal = chooser.showOpenDialog(chooser);
                if(returnVal==JFileChooser.APPROVE_OPTION){
                f = chooser.getSelectedFile();
                String path = f.getAbsolutePath();
                dc.path.setText(path);
                 try {
                Properties props = new Properties();                
                fileIn = new FileInputStream(configFile);
                props.load(fileIn);
                props.setProperty("pathRecibo", path);
                fileOut = new FileOutputStream(configFile);
                props.store(fileOut, "config");
                } catch (FileNotFoundException ex) {
                 // file does not exist
                } catch (IOException ex) {
                   // I/O error
                }}
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
            //DateFormat fecha1 = new SimpleDateFormat("dd/MM/yyyy");
            Font f=new Font(Font.FontFamily.TIMES_ROMAN,10.0f,0,null);
            ResultSet rs = fcd.obtenerFichaControl(id_control); 
        try {
            rs.next();
            PdfWriter.getInstance(document, new FileOutputStream(new File(dc.path.getText(), "Resumen "+dc.nombreLabel.getText()+" "+ dc.apellidoLabel.getText()+".pdf")));
            document.open();       
            Image image = Image.getInstance(IMG); 
            image.scaleAbsolute(70, 70);
            document.add(new Chunk(image, 0, -55f));
            Chunk titulo = new Chunk("Resumen cliente" );
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
            document.add(new Paragraph("Propiedad: "+rs.getString(6)+" - Mz: "+rs.getString(7)+" - Pc: "+rs.getString(8),f));
            document.add( Chunk.NEWLINE );
            //------Cabeceras de las columnas de las cuotas---------//
            if(dc.tablaDetallePago.getRowCount()!=1){//---Si la tabla tiene 1 fila no imprimo cabeceras----//
            document.add( Chunk.NEWLINE );    
            Chunk cuotas = new Chunk("Cuotas");
            cuotas.setUnderline(0.1f, -2f); 
            Phrase ph2 = new Phrase(cuotas);
            Paragraph ph3 = new Paragraph();
            ph3.add(ph2);
            ph3.setAlignment(Element.ALIGN_CENTER);
            document.add(ph3);
            document.add( Chunk.NEWLINE );
            PdfPTable table = new PdfPTable(5); 
            table.setTotalWidth(new float[]{ 1,1,2,2,2});
            table.setWidthPercentage(100);
            PdfPCell nro_cuota = new PdfPCell(new Paragraph("Nro. cuota",f));
            PdfPCell fecha_pago = new PdfPCell(new Paragraph("Fch. pago",f));
            PdfPCell monto_cuota = new PdfPCell(new Paragraph("Monto",f));
            PdfPCell saldo = new PdfPCell(new Paragraph("Saldo",f));
            PdfPCell cemento_saldo = new PdfPCell(new Paragraph("Cemento Saldo",f));
            nro_cuota.setHorizontalAlignment(Element.ALIGN_CENTER);
            fecha_pago.setHorizontalAlignment(Element.ALIGN_CENTER);
            monto_cuota.setHorizontalAlignment(Element.ALIGN_CENTER);
            saldo.setHorizontalAlignment(Element.ALIGN_CENTER);
            cemento_saldo.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(nro_cuota);
            table.addCell(fecha_pago);
            table.addCell(monto_cuota);
            table.addCell(saldo);
            table.addCell(cemento_saldo);
            document.add(table); 
            detalleCuota.next();
            PdfPTable primerLinea = new PdfPTable(5);            
            primerLinea.setTotalWidth(new float[]{ 1,1,2,2,2});
            primerLinea.setWidthPercentage(100);
            primerLinea.addCell(new PdfPCell(new Paragraph("-",f))).setHorizontalAlignment(Element.ALIGN_CENTER);
            primerLinea.addCell(new PdfPCell(new Paragraph(detalleCuota.getString(2),f))).setHorizontalAlignment(Element.ALIGN_CENTER);
            primerLinea.addCell(new PdfPCell(new Paragraph("Saldo inicial",f))).setHorizontalAlignment(Element.ALIGN_CENTER);
            primerLinea.addCell(new PdfPCell(new Paragraph(detalleCuota.getString(8),f))).setHorizontalAlignment(Element.ALIGN_CENTER);
            primerLinea.addCell(new PdfPCell(new Paragraph(detalleCuota.getString(11),f))).setHorizontalAlignment(Element.ALIGN_CENTER);
            document.add(primerLinea);            
            while(detalleCuota.next()){
                  PdfPTable table2 = new PdfPTable(5);            
                  table2.setTotalWidth(new float[]{ 1,1,2,2,2});
                  table2.setWidthPercentage(100);
                  table2.addCell(new PdfPCell(new Paragraph(detalleCuota.getString(1),f))).setHorizontalAlignment(Element.ALIGN_CENTER);
                  table2.addCell(new PdfPCell(new Paragraph(detalleCuota.getString(2),f))).setHorizontalAlignment(Element.ALIGN_CENTER);
                  table2.addCell(new PdfPCell(new Paragraph(String.valueOf(new BigDecimal(detalleCuota.getString(4)).add(new BigDecimal(detalleCuota.getString(5)))),f))).setHorizontalAlignment(Element.ALIGN_CENTER);
                  table2.addCell(new PdfPCell(new Paragraph(detalleCuota.getString(8),f))).setHorizontalAlignment(Element.ALIGN_CENTER);
                  table2.addCell(new PdfPCell(new Paragraph(detalleCuota.getString(11),f))).setHorizontalAlignment(Element.ALIGN_CENTER);
                  document.add(table2);
              }
            detalleCuota.beforeFirst();
            }
            //------------Cabecera de las columnas de derecho de posesion------------//
            if(dc.tablaDchoPosesion.getRowCount()!=0){//---Veo si la tabla derecho de posesion tiene algun elemento-----//
            document.add( Chunk.NEWLINE );
            Chunk cuotas = new Chunk("Cta. Derecho de Posesión");
            cuotas.setUnderline(0.1f, -2f); 
            Phrase ph2 = new Phrase(cuotas);
            Paragraph ph3 = new Paragraph();
            ph3.add(ph2);
            ph3.setAlignment(Element.ALIGN_CENTER);
            document.add(ph3);
            document.add( Chunk.NEWLINE );
            PdfPTable table2 = new PdfPTable(3); 
            table2.setTotalWidth(new float[]{ 1,1,2});
            table2.setWidthPercentage(100);
            PdfPCell nro_pago = new PdfPCell(new Paragraph("Nro. pago",f));
            PdfPCell fecha = new PdfPCell(new Paragraph("Fch. pago",f));
            PdfPCell monto = new PdfPCell(new Paragraph("Monto",f));
            nro_pago.setHorizontalAlignment(Element.ALIGN_CENTER);
            fecha.setHorizontalAlignment(Element.ALIGN_CENTER);
            monto.setHorizontalAlignment(Element.ALIGN_CENTER);
            table2.addCell(nro_pago);
            table2.addCell(fecha);
            table2.addCell(monto);
            document.add(table2); 
            int i = 1;
            while(derechoPosesion.next()){
                  PdfPTable table3 = new PdfPTable(3);            
                  table3.setTotalWidth(new float[]{ 1,1,2});
                  table3.setWidthPercentage(100);
                  table3.addCell(new PdfPCell(new Paragraph(String.valueOf(i),f))).setHorizontalAlignment(Element.ALIGN_CENTER);
                  table3.addCell(new PdfPCell(new Paragraph(derechoPosesion.getString(1),f))).setHorizontalAlignment(Element.ALIGN_CENTER);
                  table3.addCell(new PdfPCell(new Paragraph(derechoPosesion.getString(2),f))).setHorizontalAlignment(Element.ALIGN_CENTER);
                  document.add(table3);
                  i++;
              }
            derechoPosesion.beforeFirst();
            }
            document.close();
        }            
            catch (DocumentException ex) {
            Logger.getLogger(DetalleCuota.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ControladorDetalleCuota.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorDetalleCuota.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   //-----Hilo para mostrar barra de progreso mientras se crea el resumen de cliente----// 
    public class GenerarResumen extends SwingWorker<Void, Void>{
        
        ProgressDialog pd = new ProgressDialog();

        @Override
        protected Void doInBackground() throws Exception {            
            pd.setVisible(true);
            generarResumenPdf();
            return null;
        }
        @Override
        public void done(){
           pd.dispose();
        }
}
}
