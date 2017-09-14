/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ClienteDAO;
import Modelo.FichaControlDAO;
import Modelo.LoteDAO;
import Modelo.MinutaDAO;
import Modelo.ReciboDAO;
import Vista.Dialogs.AltaRecibo;
import Vista.Panels.DetalleCuota;
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
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author Marcelo Espinoza
 */
public class ControladorRecibo implements ActionListener{
    
    AltaRecibo ar;
    MinutaDAO md = new MinutaDAO();
    LoteDAO ld = new LoteDAO();
    ClienteDAO cd = new ClienteDAO();
    FichaControlDAO fcd = new FichaControlDAO();
    String apellido_propietario, nombre_propietario, cuit_propietario;
    String nombre_comprador, apellido_comprador, domicilio_comprador;
    String dimension, barrio, tipo_pago;
    int cant_cuotas, manzana, parcela;
    double importe, monto_cuota, gastos_administrativos;
    int id_control, nro_cuota, nro_random;    
    public static final String IMG = "src/Imagenes/logo_reporte.png";
    Random random = new Random();

    public ControladorRecibo(Frame parent, int id_control, int nro_cuota, String tipo_pago) {
        ar = new AltaRecibo(parent, true);
        ar.aceptar.addActionListener(this);
        ar.cancelar.addActionListener(this);
        ar.cuit.setEnabled(false);
        ar.monotributo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ar.monotributo.isSelected()){
                    ar.cuit.setEnabled(true);
                }else{
                    ar.cuit.setEnabled(false);
                }
            }
        });
        nro_random = random.nextInt(100);
        this.nro_cuota=nro_cuota;
        this.id_control=id_control;
        this.tipo_pago=tipo_pago;
        new SwingWorker().execute();
    }
    
    private void datosPropietario(){
        ResultSet rs = ld.obtenerPropietarioxLote(id_control);
        try {
            rs.next();
            apellido_propietario = rs.getString(1);
            nombre_propietario = rs.getString(2);
            //cuit_propietario = rs.getString(3);
            ar.apellido_propietario.setText(apellido_propietario);
            ar.nombre_propietario.setText(nombre_propietario);    
            
        } catch (Exception e) {
            System.out.println(e.getMessage()+"aca");
        }
    }
    
    private void datosComprador(){
        ResultSet rs = cd.clientePorLote(id_control);
        try {
            rs.next();
            apellido_comprador = rs.getString(1);
            nombre_comprador = rs.getString(2);
            domicilio_comprador = rs.getString(3) +" - "+ rs.getString(4) +" "+rs.getString(5);
            ar.apellido_comprador.setText(apellido_comprador);
            ar.nombre_comprador.setText(nombre_comprador);
            ar.domicilio_comprador.setText(domicilio_comprador);
        } catch (Exception e) {
        }
    }
    
    private void datosPropiedad(){
        ResultSet rs = fcd.obtenerFichaControl(id_control);
  
        try {
            rs.next();            
            dimension = rs.getString(1);
            cant_cuotas = rs.getInt(2);
            barrio= rs.getString(6);
            manzana = rs.getInt(7);
            parcela = rs.getInt(8);
            monto_cuota = Double.parseDouble(rs.getString(3));
            gastos_administrativos = Double.parseDouble(rs.getString(4));
            importe = monto_cuota + gastos_administrativos;
            ar.importe.setText(String.valueOf(importe));
            ar.total_pagado.setText(String.valueOf(importe));
            ar.detalle.setText("Paga cuota "+nro_cuota+"/"+cant_cuotas+"\r\nDimension "+dimension +"\r\n"+ barrio +" "+ " Mz. "+manzana +" Pc. "+ parcela);
        } catch (Exception e) {
        }
    }
    
    public void generarRecibo(){
      Document document= new Document(PageSize.A4);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("report.pdf"));
               document.open();
            Image image = Image.getInstance(IMG); 
            PdfPTable table = new PdfPTable(2); // 3 columns.
            table.setHorizontalAlignment(Element.ALIGN_RIGHT); 
            PdfPCell cell1 = new PdfPCell();
            PdfPCell cell3 = new PdfPCell(new Paragraph("Recibo por orden y cuenta de terceros"));
            cell1.setBorder(Rectangle.RIGHT);
            cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell1);
            table.addCell(cell3);
            table.setSpacingAfter(20);
            document.add(table);    
            Paragraph para = new Paragraph("Numero: "+nro_random);
            Paragraph cuit = new Paragraph("CUIT: 23-07431900-9");
             Paragraph ingBrutos = new Paragraph("Ing Brutos: 01-23-07431900-9");
            Paragraph inicAct = new Paragraph("Inic. Act.: 01-08-2015");       
            para.setAlignment(Paragraph.ALIGN_RIGHT);
            cuit.setAlignment(Paragraph.ALIGN_RIGHT);
            ingBrutos.setAlignment(Paragraph.ALIGN_RIGHT);
            inicAct.setAlignment(Paragraph.ALIGN_RIGHT);
            document.add(new Chunk(image, 0, -60f));
            document.add(para);
            document.add(cuit);
            document.add(ingBrutos);
            document.add(inicAct);
            document.add( Chunk.NEWLINE );
            PdfPTable table2 = new PdfPTable(1);
            table2.setWidthPercentage(100);
            PdfPCell cell4 = new PdfPCell(new Paragraph("Propietario: " + ar.apellido_propietario.getText() + " " + ar.nombre_propietario.getText()));
            PdfPCell cell5 = new PdfPCell(new Paragraph("Nombre comprador: " + apellido_comprador +" "+ nombre_comprador));
            PdfPCell cell6 = new PdfPCell(new Paragraph("Domicilio: " + domicilio_comprador));
            cell4.disableBorderSide(2);
            cell5.disableBorderSide(1);
            cell5.disableBorderSide(2);
            cell6.disableBorderSide(1);
            cell6.disableBorderSide(2);
            table2.addCell(cell4);
            table2.addCell(cell5);
            table2.addCell(cell6);
            document.add(table2);
            PdfPTable checkbox = new PdfPTable(5);
            checkbox.setTotalWidth(new float[]{ 1,1,1,1,1});
            checkbox.setWidthPercentage(100);
            Font f=new Font(Font.FontFamily.TIMES_ROMAN,10.0f,0,null);
            PdfPCell check1 = new PdfPCell(new Paragraph("Iva Resp. Inscripto: ",f));
            check1.setPaddingTop(10);
            check1.setBorder(Rectangle.LEFT);
            checkbox.addCell(check1);
            PdfPCell check3 = new PdfPCell(new Paragraph("Resp. No Inscripto: ",f));
            check3.setPaddingTop(10);
            check3.setBorder(Rectangle.NO_BORDER);
            checkbox.addCell(check3);
            PdfPCell check5 = new PdfPCell(new Paragraph("Monotributo: ",f));
            check5.setPaddingTop(10);
            check5.setPaddingBottom(10);
            check5.setBorder(Rectangle.NO_BORDER);
            checkbox.addCell(check5);
            PdfPCell check7 = new PdfPCell(new Paragraph("Exento: ",f));
            check7.setPaddingTop(10);
            check7.setPaddingBottom(10);
            check7.setBorder(Rectangle.NO_BORDER);
            checkbox.addCell(check7);
            PdfPCell check9 = new PdfPCell(new Paragraph("Cons. final: ",f));
            check9.setPaddingTop(10);
            check9.setPaddingBottom(10);
            check9.setBorder(Rectangle.RIGHT);
            checkbox.addCell(check9);
            document.add(checkbox);
            PdfPTable table3 = new PdfPTable(2);
            table3.setTotalWidth(new float[]{ 3,1});
            table3.setWidthPercentage(100);
            PdfPCell detalle = new PdfPCell(new Paragraph("Detalle "));
            PdfPCell importe = new PdfPCell(new Paragraph("Importe "));
            table3.addCell(detalle);
            table3.addCell(importe);
            document.add(table3);
             PdfPTable table4 = new PdfPTable(2);
            table4.setTotalWidth(new float[]{ 3,1});
            table4.setWidthPercentage(100);
            PdfPCell detalle2 = new PdfPCell(new Paragraph(ar.detalle.getText()));
            PdfPCell importe2 = new PdfPCell(new Paragraph(ar.importe.getText()));
            table4.addCell(detalle2);
            table4.addCell(importe2);
            document.add(table4);
            PdfPTable table5 = new PdfPTable(2);
            table5.setTotalWidth(new float[]{ 3,1});
            table5.setWidthPercentage(100);
            //PdfPCell vacio = new PdfPCell(new Paragraph());
            PdfPCell total_pagado = new PdfPCell(new Paragraph("Total pagado a la fecha: "));
            PdfPCell total_variable = new PdfPCell(new Paragraph(ar.total_pagado.getText()));
            //table5.addCell(vacio);
            table5.addCell(total_pagado);
            table5.addCell(total_variable);
            document.add(table5);                        
            document.add( Chunk.NEWLINE );
            document.add(new Paragraph("Son pesos: "+ar.son_pesos.getText())); 
            document.add( Chunk.NEWLINE );
            PdfPTable table6 = new PdfPTable(4);
            table6.setTotalWidth(new float[]{ 1,1,1,1});
            table6.setWidthPercentage(100);
            PdfPCell cell7 = new PdfPCell(new Paragraph("Confeccionó: "));
            cell7.setPaddingTop(10);
            cell7.setBorder(Rectangle.NO_BORDER);
            table6.addCell(cell7);
            PdfPCell cell8 = new PdfPCell(new Paragraph("Cobró: "));
            cell8.setPaddingTop(10);
            cell8.setBorder(Rectangle.NO_BORDER);
            table6.addCell(cell8);
            PdfPCell cell9 = new PdfPCell(new Paragraph("Registró: "));
            cell9.setPaddingTop(10);
            cell9.setPaddingBottom(10);
            cell9.setBorder(Rectangle.NO_BORDER);
            table6.addCell(cell9);
            PdfPCell cell10 = new PdfPCell(new Paragraph("Controló: "));
            cell10.setPaddingTop(10);
            cell10.setPaddingBottom(10);
            cell10.setBorder(Rectangle.NO_BORDER);
            table6.addCell(cell10);
            document.add(table6);
            document.close();
            }catch (DocumentException ex) {
            Logger.getLogger(DetalleCuota.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DetalleCuota.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DetalleCuota.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == ar.aceptar){
            if(validarCampos()){
                ar.dispose();                
                generarRecibo();
                new GenerarMinuta().execute();
            }else{
              JOptionPane.showMessageDialog(null, "Rellene todos los campos", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
            }
        }
        if(e.getSource() == ar.cancelar){
            ar.dispose();
        }
    }
    
      public class SwingWorker extends javax.swing.SwingWorker<Void, Void>{

        @Override
        protected Void doInBackground() throws Exception {
            datosPropiedad();
            datosComprador();
            datosPropietario();
            return null;
        }

       @Override
       public void done() { 
           ar.setVisible(true);
         }    
      }
      
       public class GenerarMinuta extends javax.swing.SwingWorker<Void, Void>{
         
         int id_control;

        @Override
        protected Void doInBackground() throws Exception {      
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            double rendido = importe - gastos_administrativos;
            md.altaMinuta(new java.sql.Date(date.getTime()), apellido_comprador, nombre_comprador, manzana, parcela, importe, rendido, nro_cuota, tipo_pago, nro_random);
            return null;
        }

       @Override
       public void done() { 
           //cc.llenarTabla();
       }
    
}
      
      public Boolean validarCampos(){
          boolean bandera = true;
           if(ar.son_pesos.getText().isEmpty()){
             ar.son_pesos.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
              bandera=false;
         }else{
             ar.son_pesos.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
          }
          return bandera;
      }
    
}
