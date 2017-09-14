/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import static Controlador.ControladorRecibo.IMG;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public ControladorMinuta(Minuta vistaMinuta) {
        this.vistaMinuta=vistaMinuta;
        this.vistaMinuta.tablaFechaMinuta.addMouseListener(this);
        this.vistaMinuta.generarMinuta.addActionListener(this);
        llenarTablaFecha();
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
                String rendido = rs.getString(7);     
                String nro_cuota = rs.getString(8);
                String observaciones = rs.getString(9);
                String nro_recibo = rs.getString(10);
                minuta = new Object[] {fecha_minuta,nro_recibo, apellidos, nombres, mzpc, cobrado, rendido, nro_cuota, observaciones};
                model.addRow(minuta);   
            } 
            resultset.beforeFirst();
          vistaMinuta.tablaFechaMinuta.getColumnModel().getColumn(0).setPreferredWidth(1);
        }catch (SQLException e) {
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

    @Override
    public void actionPerformed(ActionEvent e) {
            if(e.getSource()==vistaMinuta.generarMinuta){
                generarPdf();
            }
    }

    
    public void generarPdf(){
      Document document= new Document(PageSize.A4);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("minuta.pdf"));
               document.open();
            Paragraph titulo = new Paragraph("Minuta diaria");  
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            document.add( Chunk.NEWLINE );
            Paragraph subtitulo = new Paragraph("Fecha: ");  
            subtitulo.setAlignment(Element.ALIGN_CENTER);
            document.add(subtitulo);
            document.add( Chunk.NEWLINE );
            PdfPTable table = new PdfPTable(8);            
            table.setTotalWidth(new float[]{ 2,2,5,2,2,2,2,4});
            table.setWidthPercentage(100);
            Font f=new Font(Font.FontFamily.TIMES_ROMAN,10.0f,0,null);
            PdfPCell orden = new PdfPCell(new Paragraph("ORD",f));
            PdfPCell rbo_nro = new PdfPCell(new Paragraph("Rbo. Nro",f));
            PdfPCell apynom = new PdfPCell(new Paragraph("Apellido y nombre",f));
            PdfPCell mzpc = new PdfPCell(new Paragraph("Mz./Pc.",f));
            PdfPCell cobrado = new PdfPCell(new Paragraph("Cobrado",f));
            PdfPCell rendido = new PdfPCell(new Paragraph("Rendido",f));
            PdfPCell nro_cuota = new PdfPCell(new Paragraph("Cuota Nro.",f));
            PdfPCell observaciones = new PdfPCell(new Paragraph("Observaciones",f));
            table.addCell(orden);            
            table.addCell(rbo_nro);
            table.addCell(apynom);
            table.addCell(mzpc);
            table.addCell(cobrado);
            table.addCell(rendido);
            table.addCell(nro_cuota);
            table.addCell(observaciones);
             document.add(table);  
          try {
              System.out.println(resultset.next());
              int conta = 1;
              while(resultset.next()){
                  PdfPTable table2 = new PdfPTable(8);            
                  table2.setTotalWidth(new float[]{ 2,2,5,2,2,2,2,4});
                  table2.setWidthPercentage(100);
                  table2.addCell(new PdfPCell(new Paragraph(String.valueOf(conta),f)));            
                  table2.addCell(new PdfPCell(new Paragraph(resultset.getString(10),f)));
                  table2.addCell(new PdfPCell(new Paragraph(resultset.getString(2)+" "+resultset.getString(3),f)));
                  table2.addCell(new PdfPCell(new Paragraph(resultset.getString(4)+" "+resultset.getString(5),f)));
                  table2.addCell(new PdfPCell(new Paragraph(resultset.getString(6),f)));
                  table2.addCell(new PdfPCell(new Paragraph(resultset.getString(7),f)));
                  table2.addCell(new PdfPCell(new Paragraph(resultset.getString(8),f)));
                  table2.addCell(new PdfPCell(new Paragraph(resultset.getString(9),f)));
                  document.add(table2);
                  conta ++;
              }
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
