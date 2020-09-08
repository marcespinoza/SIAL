/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Controlador.ControladorDetalleCuota;
import static Controlador.ControladorDetalleCuota.IMG;
import Vista.Panels.DetalleCuota;
import com.itextpdf.text.BaseColor;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Marceloi7
 */
public class GenerarLista {

    public GenerarLista() {
    }
    
    public static void generarResumenPdf(List<ClientesPorCriterio> listaClientes, int dias){
            JFrame parentFrame = new JFrame(); 
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar en..");  
            File fileToSave = null;
            fileChooser.setSelectedFile(new File("Lista_de_clientes.pdf"));
            int userSelection = fileChooser.showSaveDialog(parentFrame); 
            if (userSelection == JFileChooser.APPROVE_OPTION) {
            fileToSave = fileChooser.getSelectedFile();
            SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-YYYY");
            Document document= new Document(PageSize.A4);
            DateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy");
            java.util.Date date = new java.util.Date();
            Font f=new Font(Font.FontFamily.TIMES_ROMAN,8.0f,0,null);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(new File(fileToSave.getAbsolutePath())));
            document.open();       
            Image image = Image.getInstance(IMG); 
            image.scaleAbsolute(70, 70);
            document.add(new Chunk(image, 0, -55f));
            Chunk titulo = new Chunk("Lista de clientes");
            titulo.setUnderline(0.1f, -2f); 
            Phrase ph1 = new Phrase(titulo);
            Phrase ph2 = new Phrase("    "+dateFormat2.format(date));
            Paragraph ph = new Paragraph();
            ph.add(ph1);
            ph.add(ph2);
            ph.setAlignment(Element.ALIGN_CENTER);
            Paragraph total = new Paragraph("B° Doña Valentina - Total clientes: "+listaClientes.size()+" - Ultima cuota >= "+dias+" días");
            total.setAlignment(Element.ALIGN_CENTER);
            document.add(ph);
            document.add( Chunk.NEWLINE );
            document.add(total);
            document.add( Chunk.NEWLINE );
            //------Cabeceras de las columnas de las cuotas---------//
            if(!listaClientes.isEmpty()){//---Si la tabla tiene 1 fila no imprimo cabeceras----//            
            PdfPTable table = new PdfPTable(8); 
            table.setTotalWidth(new float[]{ 2,2,1,2,1,(float)0.5,1,1});
            table.setWidthPercentage(100);
            PdfPCell nro_cuota = new PdfPCell(new Paragraph("Apellido",f));
            PdfPCell fecha_pago = new PdfPCell(new Paragraph("Nombre/s",f));
            PdfPCell lote = new PdfPCell(new Paragraph("Mz - Pc",f));
            PdfPCell telefono = new PdfPCell(new Paragraph("Tel.",f));
            PdfPCell valorCuota = new PdfPCell(new Paragraph("Cuota",f));
            PdfPCell cemento_saldo = new PdfPCell(new Paragraph("Cuotas",f));
            PdfPCell ultima_cuota = new PdfPCell(new Paragraph("Ultima cuota",f));
            PdfPCell total_cuotas = new PdfPCell(new Paragraph("Tot. Acum $",f));
            nro_cuota.setHorizontalAlignment(Element.ALIGN_CENTER);
            fecha_pago.setHorizontalAlignment(Element.ALIGN_CENTER);
            lote.setHorizontalAlignment(Element.ALIGN_CENTER);
            telefono.setHorizontalAlignment(Element.ALIGN_CENTER);
            valorCuota.setHorizontalAlignment(Element.ALIGN_CENTER);
            cemento_saldo.setHorizontalAlignment(Element.ALIGN_CENTER);
            ultima_cuota.setHorizontalAlignment(Element.ALIGN_CENTER);
            total_cuotas.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(nro_cuota);
            table.addCell(fecha_pago);
            table.addCell(lote);
            table.addCell(telefono);
            table.addCell(valorCuota);
            table.addCell(cemento_saldo);
            table.addCell(ultima_cuota);
            table.addCell(total_cuotas);
            document.add(table);    
            BigDecimal totales = BigDecimal.ZERO;
            for(int i = 0; i < listaClientes.size(); i++){
                if(listaClientes.get(i).baja!=1){
                  PdfPTable table2 = new PdfPTable(8);            
                  table2.setTotalWidth(new float[]{ 2,2,1,2,1,(float)0.5,1,1});
                  table2.setWidthPercentage(100);
                  table2.addCell(new PdfPCell(new Paragraph(String.valueOf(listaClientes.get(i).getApellidos()),f))).setHorizontalAlignment(Element.ALIGN_CENTER);
                  table2.addCell(new PdfPCell(new Paragraph(String.valueOf(listaClientes.get(i).getNombres()),f))).setHorizontalAlignment(Element.ALIGN_CENTER);
                  table2.addCell(new PdfPCell(new Paragraph(String.valueOf(listaClientes.get(i).getManzana()+" - "+listaClientes.get(i).getParcela()),f))).setHorizontalAlignment(Element.ALIGN_CENTER);
                  table2.addCell(new PdfPCell(new Paragraph(String.valueOf(listaClientes.get(i).getTelefono1()+" / "+listaClientes.get(i).getTelefono2()),f))).setHorizontalAlignment(Element.ALIGN_CENTER);
                  table2.addCell(new PdfPCell(new Paragraph(String.valueOf("$ "+listaClientes.get(i).getGastos().add(listaClientes.get(i).getCuota_pura())),f))).setHorizontalAlignment(Element.ALIGN_CENTER);
                  table2.addCell(new PdfPCell(new Paragraph(String.valueOf(listaClientes.get(i).getCuotas()),f))).setHorizontalAlignment(Element.ALIGN_CENTER);
                  table2.addCell(new PdfPCell(new Paragraph(String.valueOf(sdf.format(listaClientes.get(i).getUltimaCuota())),f))).setHorizontalAlignment(Element.ALIGN_CENTER);
                  table2.addCell(new PdfPCell(new Paragraph(String.valueOf(listaClientes.get(i).getTotal()),f))).setHorizontalAlignment(Element.ALIGN_CENTER);
                  totales = totales.add(listaClientes.get(i).getTotal());
                  document.add(table2);
                }
              }
            PdfPTable table3 = new PdfPTable(1);  
            table3.setWidthPercentage(100);
            PdfPCell celltotales = new PdfPCell(new Paragraph("Total: $ "+String.valueOf(totales),f));
            celltotales.setPaddingRight(6);
            celltotales.setIndent(10);
            celltotales.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table3.addCell(celltotales);
            document.add(table3);
            }
            document.close();
        }            
            catch (DocumentException ex) {
            java.util.logging.Logger.getLogger(DetalleCuota.class.getName()).log(Level.SEVERE, null, ex.getMessage());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo generar. Compruebe que no tiene abierto actualmente el archivo"+ex.getMessage());
            java.util.logging.Logger.getLogger(ControladorDetalleCuota.class.getName()).log(Level.SEVERE, null, ex);        
         }
        }
      }
        
}
