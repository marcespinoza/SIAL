/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Clases.Cuota;
import Modelo.CuotaDAO;
import Modelo.DchoPosesionDAO;
import Modelo.FichaControlDAO;
import Modelo.MinutaDAO;
import Utils.RendererTablaCuota;
import Utils.RendererTablaDchoPosesion;
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
import java.awt.Desktop;
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
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcelo
 */
public class ControladorDetalleCuota implements ActionListener, TableModelListener{

       
    RendererTablaCuota r = new RendererTablaCuota();
    RendererTablaDchoPosesion rdp = new RendererTablaDchoPosesion();
    DetalleCuota dc = new DetalleCuota();
    CuotaDAO cd = new CuotaDAO();
    MinutaDAO md = new MinutaDAO();
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
    ResultSet derechoPosesion;
    List<Cuota> detalleCuota = null;
    int baja_logica;
    
    public ControladorDetalleCuota() {
    }
    
    public ControladorDetalleCuota(int nro_cuotas,String apellido, String nombre, String telefono, String barrio,String calle,int numero,int id_control, int baja_logica) {
        this.apellido=apellido;
        this.nombre=nombre;
        this.id_control=id_control;
        this.nro_cuotas=nro_cuotas;
        if(baja_logica==1){
           dc.agregarPagoBtn.setEnabled(false);
           dc.eliminarPagoBtn.setEnabled(false);
           dc.generarReciboBtn.setEnabled(false);
        }
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
        dc.modificarPagoBtn.addActionListener(this);
        dc.eliminarPagoBtn.addActionListener(this);
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
        desactivarBotones();
    }
    
    public void desactivarBotones(){
      if(Ventana.labelTipoUsuario.getText().equals("operador")){
            dc.eliminarPagoBtn.setEnabled(false);
            dc.modificarPagoBtn.setEnabled(false);
        }
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
        SimpleDateFormat input = new SimpleDateFormat("dd-MM-YYYY");
        if(detalleCuota!=null){
            for(int i = 0; i < detalleCuota.size(); i++){
                int nro_cuota = detalleCuota.get(i).getNro_cuota();
                String fecha = input.format(detalleCuota.get(i).getFecha());
                String detalle = detalleCuota.get(i).getDetalle();
                BigDecimal cuota_pura = detalleCuota.get(i).getCuota_pura();
                BigDecimal gastos_admin = detalleCuota.get(i).getGastos_administrativos();
                BigDecimal debe = detalleCuota.get(i).getDebe();
                BigDecimal haber = detalleCuota.get(i).getHaber();
                BigDecimal saldo = detalleCuota.get(i).getSaldo();
                BigDecimal cemento_debe = detalleCuota.get(i).getCemente_debe();
                BigDecimal cemento_haber = detalleCuota.get(i).getCemento_haber();
                BigDecimal cemento_saldo = detalleCuota.get(i).getCemento_saldo();
                String observaciones = detalleCuota.get(i).getObservaciones();
                int nro_recibo = detalleCuota.get(i).getNro_recibo();
                int id_recibo = detalleCuota.get(i).getId_recibo();
                String tipo_pago = detalleCuota.get(i).getTipo_pago();
                detallePago= new Object[] {nro_cuota, fecha, detalle, cuota_pura, gastos_admin, debe, haber, saldo, cemento_debe, cemento_haber,cemento_saldo, observaciones, nro_recibo, id_recibo, tipo_pago};                    
                model.addRow(detallePago); 
            }
            //---------Reseteo resultset para recorrerlo nuevamente cuando quiero---------//
            //---------crear resumen de cliente--------------------------------------------//
            CardLayout cl = (CardLayout)(Ventana.panelPrincipal.getLayout());
            Ventana.panelPrincipal.add(dc, "Detalle_pago");
            cl.show(Ventana.panelPrincipal, "Detalle_pago");
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
        if(e.getSource() == dc.modificarPagoBtn){
            int row = dc.tablaDetallePago.getSelectedRow();             
             if(row==-1){
                JOptionPane.showMessageDialog(null, "Seleccione una cuota", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
             }else if(row==0){
                 JOptionPane.showMessageDialog(null, "Cuota no valida", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
             }else if(row!=-1){
            new ControladorActualizarCuota((Ventana) SwingUtilities.getWindowAncestor(dc), id_control, Integer.parseInt(dc.tablaDetallePago.getModel().getValueAt(row, 0).toString()), new BigDecimal(dc.tablaDetallePago.getModel().getValueAt(row, 3).toString()),new BigDecimal(dc.tablaDetallePago.getModel().getValueAt(row, 4).toString()));
            llenarTabla(id_control);
            llearTablaDchoPosesion(id_control);}
        }
        if(e.getSource()==dc.eliminarPagoBtn){
             int row = dc.tablaDetallePago.getSelectedRow();             
             if(row==-1){
                JOptionPane.showMessageDialog(null, "Seleccione una cuota", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
             }else if(row==0){
                 JOptionPane.showMessageDialog(null, "Cuota no valida", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
             }else if(row!=-1){
                 int nro_cuota = Integer.parseInt(dc.tablaDetallePago.getModel().getValueAt(row, 0).toString());
                 int id_recibo = Integer.parseInt(dc.tablaDetallePago.getModel().getValueAt(row, 13).toString());
                 ImageIcon icon = new ImageIcon("/Imagenes/Iconos/warning.png"); 
                 int reply = JOptionPane.showConfirmDialog(null, "Eliminar cuota numero "+dc.tablaDetallePago.getModel().getValueAt(row, 0)+"?",
                     "Advertencia",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
              if (reply == JOptionPane.YES_OPTION) {
                  cd.eliminarCuota(nro_cuota, id_control);
                  md.eliminarMinuta(id_recibo);
                  llenarTabla(id_control);
               } 
            }
             
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
                case 0: JOptionPane.showMessageDialog(null, "Cuota no válida", "Atención", JOptionPane.INFORMATION_MESSAGE, null); break;
                case -1: JOptionPane.showMessageDialog(null, "Seleccione un pago", "Atención", JOptionPane.INFORMATION_MESSAGE, null); break;
                default:
                new ControladorRecibo(this,(Frame) SwingUtilities.getWindowAncestor(dc), id_control, dc ,Integer.parseInt(dc.tablaDetallePago.getModel().getValueAt(row, 0).toString()),row, 1);    
                break;
            }
             }else if(posesion){
                 int row = dc.tablaDchoPosesion.getSelectedRow();
                  switch(row){
                case 0:
                JOptionPane.showMessageDialog(null, "Fila no válida", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
                break;
                default:
                 new ControladorRecibo(this,(Frame) SwingUtilities.getWindowAncestor(dc), id_control, dc , Integer.parseInt(dc.tablaDchoPosesion.getModel().getValueAt(row, 0).toString()),row, 0);  
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
                }
            }
        }        
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        //------Solo trata cuando cambia el valor de una celda--------//
        int row = dc.tablaDetallePago.getSelectedRow();
        if(row!=1){
         if (e.getType() == TableModelEvent.UPDATE) {
             cd.actualizarCuota(dc.tablaDetallePago.getModel().getValueAt(row, 11).toString(), Integer.parseInt(dc.tablaDetallePago.getModel().getValueAt(row, 0).toString()), id_control);
         }
        }
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
            PdfPCell fecha_pago = new PdfPCell(new Paragraph("Fecha pago",f));
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
            PdfPTable primerLinea = new PdfPTable(5);            
            primerLinea.setTotalWidth(new float[]{ 1,1,2,2,2});
            primerLinea.setWidthPercentage(100);
            primerLinea.addCell(new PdfPCell(new Paragraph("-",f))).setHorizontalAlignment(Element.ALIGN_CENTER);
            primerLinea.addCell(new PdfPCell(new Paragraph(String.valueOf(detalleCuota.get(0).getFecha()),f))).setHorizontalAlignment(Element.ALIGN_CENTER);
            primerLinea.addCell(new PdfPCell(new Paragraph("Saldo inicial",f))).setHorizontalAlignment(Element.ALIGN_CENTER);
            primerLinea.addCell(new PdfPCell(new Paragraph(String.valueOf(detalleCuota.get(0).getSaldo()),f))).setHorizontalAlignment(Element.ALIGN_CENTER);
            primerLinea.addCell(new PdfPCell(new Paragraph(String.valueOf(detalleCuota.get(0).getCemento_saldo()),f))).setHorizontalAlignment(Element.ALIGN_CENTER);
            document.add(primerLinea);            
            for(int i = 1; i < detalleCuota.size(); i++){
                  PdfPTable table2 = new PdfPTable(5);            
                  table2.setTotalWidth(new float[]{ 1,1,2,2,2});
                  table2.setWidthPercentage(100);
                  table2.addCell(new PdfPCell(new Paragraph(String.valueOf(detalleCuota.get(i).getNro_cuota()),f))).setHorizontalAlignment(Element.ALIGN_CENTER);
                  table2.addCell(new PdfPCell(new Paragraph(String.valueOf(detalleCuota.get(i).getFecha()),f))).setHorizontalAlignment(Element.ALIGN_CENTER);
                  table2.addCell(new PdfPCell(new Paragraph(String.valueOf(detalleCuota.get(i).getCuota_pura().add(detalleCuota.get(i).getGastos_administrativos())),f))).setHorizontalAlignment(Element.ALIGN_CENTER);
                  table2.addCell(new PdfPCell(new Paragraph(String.valueOf(detalleCuota.get(i).getSaldo()),f))).setHorizontalAlignment(Element.ALIGN_CENTER);
                  table2.addCell(new PdfPCell(new Paragraph(String.valueOf(detalleCuota.get(i).getCemento_saldo()),f))).setHorizontalAlignment(Element.ALIGN_CENTER);
                  document.add(table2);
              }
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
            Logger.getLogger(DetalleCuota.class.getName()).log(Level.SEVERE, null, ex.getMessage());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo generar. Compruebe que no tiene abierto actualmente el archivo");
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
           try {
            //-------Abro pdf del recibo-------//   
               Desktop.getDesktop().open(new File(dc.path.getText(), "Resumen "+dc.nombreLabel.getText()+" "+ dc.apellidoLabel.getText()+".pdf"));
           } catch (IOException ex) {
               Logger.getLogger(ControladorRecibo.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
}
}
