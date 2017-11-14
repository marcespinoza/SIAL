/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.MinutaDAO;
import Vista.Panels.DetalleCuota;
import Vista.Panels.Minuta;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcelo Espinoza
 */
public class ControladorMinuta implements MouseListener, ActionListener {
    
    MinutaDAO md = new MinutaDAO();
    Minuta vistaMinuta;
    private Object [] minuta;
    private Object [] fechaMinuta;
    ResultSet resultset = null;
    File f;
    JFileChooser chooser;
    File configFile = new File("config.properties");
    public static final String IMG = "src/Imagenes/logo_reporte.png";

    public ControladorMinuta(Minuta vistaMinuta) {
        this.vistaMinuta=vistaMinuta;
        this.vistaMinuta.tablaFechaMinuta.addMouseListener(this);
        this.vistaMinuta.generarMinuta.addActionListener(this);
        this.vistaMinuta.guardar_en.addActionListener(this);
        this.vistaMinuta.buscar.addActionListener(this);
        cargarPathMinuta();
        llenarTablaFecha();
    }

    
    public void cargarPathMinuta() {
        try {
          FileReader reader = new FileReader(configFile);
          Properties props = new Properties();
          props.load(reader); 
          String pathMinuta = props.getProperty("pathMinuta");
          vistaMinuta.path.setText(pathMinuta);
          reader.close();
         } catch (FileNotFoundException ex) {
        // file does not exist
        } catch (IOException ex) {
        // I/O error
         }
        }   
    
    public void llenarTabla(ResultSet rs){
        resultset = rs;
        DefaultTableModel model = (DefaultTableModel) vistaMinuta.tablaMinuta.getModel();
        model.setRowCount(0);
        try {
            while(rs.next()){
                String fecha_minuta = rs.getString(1);
                String apellidos = rs.getString(2);
                String nombres = rs.getString(3);
                String mzpc = rs.getString(4)+rs.getString(5);  
                String cobrado = rs.getString(6);
                String gastos = rs.getString(7);
                String rendido = rs.getString(8);     
                String nro_cuota = rs.getString(9);
                String observaciones = rs.getString(10);
                String nro_recibo = rs.getString(12);
                minuta = new Object[] {fecha_minuta,nro_recibo, apellidos, nombres, mzpc, cobrado, gastos, rendido, nro_cuota, observaciones};
                model.addRow(minuta);   
            } 
            resultset.beforeFirst();
          
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }}
    
    public void llenarTablaFecha(){
        ResultSet rs = md.obtenerFecha();
        DefaultTableModel model = (DefaultTableModel) vistaMinuta.tablaFechaMinuta.getModel();
        DefaultTableModel model2 = (DefaultTableModel) vistaMinuta.tablaMinuta.getModel();
        model2.setRowCount(0);
        model.setRowCount(0);
        int nro = 1;
        try {
            while(rs.next()){
                String fecha_minuta = rs.getString(1);
                fechaMinuta = new Object[] {nro,fecha_minuta};
                model.addRow(fechaMinuta); 
                nro ++;
            }  
         vistaMinuta.tablaFechaMinuta.getColumnModel().getColumn(0).setPreferredWidth(1);   
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }}

    @Override
    public void actionPerformed(ActionEvent e) {
            if(e.getSource()==vistaMinuta.generarMinuta){
                if(!vistaMinuta.path.getText().equals("")){
                  if(vistaMinuta.tablaFechaMinuta.getSelectedRow()==-1){
                       JOptionPane.showMessageDialog(null, "Selecciona una minuta", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
                  }else{
                      generarPdf();
                }}else{
                 JOptionPane.showMessageDialog(null, "Debe seleccionar ubicación donde guardar la minuta", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
                }                
            }
            
            if(e.getSource() == vistaMinuta.guardar_en){
                chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.showOpenDialog(null);
                f = chooser.getSelectedFile();
                String path = f.getAbsolutePath();
                vistaMinuta.path.setText(path);
                try {
                Properties props = new Properties();
                props.setProperty("pathMinuta", path);
                FileWriter writer = new FileWriter(configFile);
                props.store(writer, "config");
                writer.close();
                } catch (FileNotFoundException ex) {
                 // file does not exist
                } catch (IOException ex) {
                   // I/O error
                }
                }
            
            if(e.getSource() == vistaMinuta.buscar){
                SimpleDateFormat dcn = new SimpleDateFormat("dd-MM-yyyy");
                 String desde = dcn.format(vistaMinuta.minutaDesde.getDate() );
                 String date = dcn.format(vistaMinuta.minutaHasta.getDate() );
                ResultSet rs = md.minutasPorRango(new java.sql.Date(vistaMinuta.minutaDesde.getDate().getTime()) , new java.sql.Date(vistaMinuta.minutaHasta.getDate().getTime()));
                llenarTabla(rs);
            }
    }

    
    public void generarPdf(){
      Document document= new Document(PageSize.A4);
      DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
      DateFormat dateFormat2 = new SimpleDateFormat("dd.MM.yyyy");
      java.util.Date date = new java.util.Date();
        try {
            
            PdfWriter.getInstance(document, new FileOutputStream(new File(vistaMinuta.path.getText(), "Minuta-"+dateFormat2.format(date)+".pdf")));
            document.open();
            Image image = Image.getInstance(IMG); 
            image.scaleAbsolute(70, 70);
            Font f=new Font(Font.FontFamily.TIMES_ROMAN,10.0f,0,null); 
            document.add(new Chunk(image, 0, -55f));
            Paragraph titulo = new Paragraph("Minuta diaria");  
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            document.add( Chunk.NEWLINE );
            Paragraph subtitulo = new Paragraph("Fecha: "+dateFormat.format(date));  
            subtitulo.setAlignment(Element.ALIGN_CENTER);
            document.add(subtitulo);
            document.add( Chunk.NEWLINE );
            PdfPTable table = new PdfPTable(9);            
            table.setTotalWidth(new float[]{ 1,2,5,2,2,2,2,2,4});
            table.setWidthPercentage(100);
            PdfPCell orden = new PdfPCell(new Paragraph("ORD",f));
            PdfPCell rbo_nro = new PdfPCell(new Paragraph("Rbo. Nro",f));
            PdfPCell apynom = new PdfPCell(new Paragraph("Apellido y nombre",f));
            PdfPCell mzpc = new PdfPCell(new Paragraph("Mz./Pc.",f));
            PdfPCell cobrado = new PdfPCell(new Paragraph("Cobrado",f));
            PdfPCell gastos = new PdfPCell(new Paragraph("Gastos. Adm.",f));
            PdfPCell rendido = new PdfPCell(new Paragraph("Rendido",f));
            PdfPCell nro_cuota = new PdfPCell(new Paragraph("Cuota Nro.",f));
            PdfPCell observaciones = new PdfPCell(new Paragraph("Observaciones",f));  
            orden.setHorizontalAlignment(Element.ALIGN_CENTER);
            rbo_nro.setHorizontalAlignment(Element.ALIGN_CENTER);
            apynom.setHorizontalAlignment(Element.ALIGN_CENTER);
            mzpc.setHorizontalAlignment(Element.ALIGN_CENTER);
            cobrado.setHorizontalAlignment(Element.ALIGN_CENTER);
            gastos.setHorizontalAlignment(Element.ALIGN_CENTER);
            rendido.setHorizontalAlignment(Element.ALIGN_CENTER);
            nro_cuota.setHorizontalAlignment(Element.ALIGN_CENTER);
            observaciones.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(orden);            
            table.addCell(rbo_nro);
            table.addCell(apynom);
            table.addCell(mzpc);
            table.addCell(cobrado);
            table.addCell(gastos);
            table.addCell(rendido);
            table.addCell(nro_cuota);
            table.addCell(observaciones);
             document.add(table);  
          try {
              int conta = 1;
              double acumulador_rendido = 0;
              double deposito = 0;
              double t_debito = 0;
              double t_credito = 0;
              double total = 0;
              while(resultset.next()){
                  PdfPTable table2 = new PdfPTable(9);            
                  table2.setTotalWidth(new float[]{ 1,2,5,2,2,2,2,2,4});
                  table2.setWidthPercentage(100);
                  table2.addCell(new PdfPCell(new Paragraph(String.valueOf(conta),f)));    
                  PdfPCell detalle_nro_cuota = new PdfPCell(new Paragraph(resultset.getString(12),f));
                  detalle_nro_cuota.setHorizontalAlignment(Element.ALIGN_CENTER);
                  table2.addCell(detalle_nro_cuota);
                  table2.addCell(new PdfPCell(new Paragraph(resultset.getString(2)+" "+resultset.getString(3),f)));
                  table2.addCell(new PdfPCell(new Paragraph("Mz."+resultset.getString(4)+"-"+resultset.getString(5),f)));
                  table2.addCell(new PdfPCell(new Paragraph(resultset.getString(6),f)));
                  table2.addCell(new PdfPCell(new Paragraph(resultset.getString(7),f)));
                  table2.addCell(new PdfPCell(new Paragraph(resultset.getString(8),f)));
                  table2.addCell(new PdfPCell(new Paragraph(resultset.getString(9),f)));
                  table2.addCell(new PdfPCell(new Paragraph(resultset.getString(10),f)));
                  document.add(table2);
                  acumulador_rendido = acumulador_rendido + Double.parseDouble(resultset.getString(8));
                  switch(resultset.getString(9)){
                      case "Dpto. Bancario":deposito = deposito + Double.parseDouble(resultset.getString(7));break; 
                      case "Tarjeta credito":t_debito = t_debito + Double.parseDouble(resultset.getString(7));break;
                      case "Tarjeta debito":t_credito = t_credito + Double.parseDouble(resultset.getString(7));break;
                  }
                  conta ++;
              }
            total = acumulador_rendido - (deposito + t_credito + t_debito);
            PdfPTable tablaa = new PdfPTable(2);
            tablaa.setWidthPercentage(50);
            tablaa.setSpacingBefore(10f);
            PdfPCell nuevo = new PdfPCell(new Paragraph("Total rendido $", f));
            PdfPCell nuevo2 = new PdfPCell(new Paragraph(String.valueOf(acumulador_rendido), f));
            nuevo2.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tablaa.addCell(nuevo);
            tablaa.addCell(nuevo2);
            document.add(tablaa);
            if(deposito != 0){
                 PdfPTable dpto_bancario = new PdfPTable(2);
                 dpto_bancario.setWidthPercentage(50);
                 PdfPCell dpto1 = new PdfPCell(new Paragraph("Depósito bancario $", f));
                 PdfPCell dpto2 = new PdfPCell(new Paragraph("- "+String.valueOf(deposito), f));
                 dpto2.setHorizontalAlignment(Element.ALIGN_RIGHT);
                 dpto_bancario.addCell(dpto1);
                 dpto_bancario.addCell(dpto2);
                 document.add(dpto_bancario); 
            }   
            //-----Total final------//
            PdfPTable total_final = new PdfPTable(2);
            total_final.setWidthPercentage(50);
            PdfPCell total1 = new PdfPCell(new Paragraph("Total $", f));
            PdfPCell total2 = new PdfPCell(new Paragraph(String.valueOf(total), f));
            total1.setBorder(Rectangle.NO_BORDER);
            total2.setBorder(Rectangle.NO_BORDER);
            total2.setHorizontalAlignment(Element.ALIGN_RIGHT);
            total_final.addCell(total1);
            total_final.addCell(total2);
            document.add(total_final);
            document.add( Chunk.NEWLINE );
            Paragraph total_efectivo = new Paragraph("Total efectivo: ", f);
            document.add(total_efectivo);
            Paragraph son_pesos = new Paragraph("Son pesos: ", f);
            document.add(son_pesos);
            //---------Pie pagina-------//
            PdfPTable pie = new PdfPTable(2);
            pie.setWidthPercentage(50);
            pie.setSpacingBefore(10f);
            PdfPCell confecciono = new PdfPCell(new Paragraph("Confeccionó", f));
            PdfPCell conformidad = new PdfPCell(new Paragraph("Conformidad", f));
            confecciono.setBorder(Rectangle.NO_BORDER);
            conformidad.setBorder(Rectangle.NO_BORDER);
            conformidad.setHorizontalAlignment(Element.ALIGN_RIGHT);
            pie.addCell(confecciono);
            pie.addCell(conformidad);
            document.add(pie);
          } catch (SQLException ex) {
              Logger.getLogger(ControladorMinuta.class.getName()).log(Level.SEVERE, null, ex);
          }   
            document.close();
            }catch (DocumentException ex) {
            Logger.getLogger(DetalleCuota.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DetalleCuota.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DetalleCuota.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
        
    public class SwingWorker extends javax.swing.SwingWorker<Void, Void>{
         
         ResultSet rs;

        @Override
        protected Void doInBackground() throws Exception {    
            int row = vistaMinuta.tablaFechaMinuta.getSelectedRow();
            rs = md.minutasPorFecha(vistaMinuta.tablaFechaMinuta.getModel().getValueAt(row,1).toString());
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
