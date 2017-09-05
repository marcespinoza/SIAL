/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ClienteDAO;
import Modelo.FichaControlDAO;
import Modelo.LoteDAO;
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
import java.sql.Date;
import java.sql.ResultSet;
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
    LoteDAO ld = new LoteDAO();
    ClienteDAO cd = new ClienteDAO();
    FichaControlDAO fcd = new FichaControlDAO();
    String apellido_propietario, nombre_propietario, cuit_propietario;
    String nombre_comprador, apellido_comprador, domicilio_comprador;
    String dimension, barrio;
    int cant_cuotas, manzana, parcela;
    double importe;
    int id_control, nro_cuota;    
    public static final String IMG = "src/Imagenes/logo_reporte.png";

    public ControladorRecibo(Frame parent, int id_control, int nro_cuota) {
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
        this.nro_cuota=nro_cuota;
        this.id_control=id_control;
        new SwingWorker().execute();
    }
    
    private void datosPropietario(){
        ResultSet rs = ld.obtenerPropietarioxLote("La mercedes", 25, 25);
        try {
            rs.next();
            apellido_propietario = rs.getString(1);
            nombre_propietario = rs.getString(2);
            cuit_propietario = rs.getString(3);
            ar.apellido_propietario.setText(apellido_propietario);
            ar.nombre_propietario.setText(nombre_propietario);    
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void datosComprador(){
        ResultSet rs = cd.clientePorLote(id_control);
        try {
            rs.next();
            apellido_comprador = rs.getString(1);
            nombre_comprador = rs.getString(2);
            domicilio_comprador = rs.getString(3) +" "+ rs.getString(4) +" "+rs.getString(5);
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
            importe = Double.parseDouble(rs.getString(3))+ Double.parseDouble(rs.getString(4));
            ar.importe.setText(String.valueOf(importe));
            ar.total_pagado.setText(String.valueOf(importe));
            ar.detalle.setText("Paga cuota "+nro_cuota+"/"+cant_cuotas+" Dimension "+dimension +" "+ barrio +" "+ manzana +" "+ parcela);
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
            Paragraph para = new Paragraph("Numero: ...........");
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
            PdfPCell cell4 = new PdfPCell(new Paragraph("Propietario: " + apellido_propietario + " " + nombre_propietario));
            PdfPCell cell5 = new PdfPCell(new Paragraph("Nombre comprador: " + apellido_comprador +" "+ nombre_comprador));
            PdfPCell cell6 = new PdfPCell(new Paragraph("Domicilio comprador:" + domicilio_comprador));
            cell4.disableBorderSide(2);
            cell5.disableBorderSide(1);
            cell5.disableBorderSide(2);
            cell6.disableBorderSide(1);
            cell6.disableBorderSide(2);
            table2.addCell(cell4);
            table2.addCell(cell5);
            table2.addCell(cell6);
            document.add(table2);
            PdfPTable checkbox = new PdfPTable(4);
            checkbox.setTotalWidth(new float[]{ 1,1,1,1});
            checkbox.setWidthPercentage(100);
            Font f=new Font(Font.FontFamily.TIMES_ROMAN,10.0f,0,null);
            PdfPCell check1 = new PdfPCell(new Paragraph("Iva Resp. Inscripto: ",f));
            check1.setPaddingTop(10);
            check1.setPaddingBottom(10);
            check1.disableBorderSide(2);
            check1.disableBorderSide(3);
            checkbox.addCell(check1);
            PdfPCell check3 = new PdfPCell(new Paragraph("Resp. No Inscripto: ",f));
            check3.setPaddingTop(10);
            check3.setPaddingBottom(10);
             check3.disableBorderSide(1);
            check3.disableBorderSide(2);
            check3.disableBorderSide(4);
            checkbox.addCell(check3);
            PdfPCell check5 = new PdfPCell(new Paragraph("Monotributo: ",f));
            check5.setPaddingTop(10);
            check5.setPaddingBottom(10);
            check5.disableBorderSide(4);
            check5.disableBorderSide(3);
            checkbox.addCell(check5);
            PdfPCell check7 = new PdfPCell(new Paragraph("Exento: ",f));
            check7.setPaddingTop(10);
            check7.setPaddingBottom(10);
            check7.disableBorderSide(4);
            check7.disableBorderSide(3);
            checkbox.addCell(check7);
            document.add(checkbox);
            PdfPTable table3 = new PdfPTable(2);
            table3.setTotalWidth(new float[]{ 3,1});
            table3.setWidthPercentage(100);
            PdfPCell detalle = new PdfPCell(new Paragraph("Detalle"));
            PdfPCell importe = new PdfPCell(new Paragraph("Importe"));
            table3.addCell(detalle);
            table3.addCell(importe);
            document.add(table3);
             PdfPTable table4 = new PdfPTable(2);
            table4.setTotalWidth(new float[]{ 3,1});
            table4.setWidthPercentage(100);
            PdfPCell detalle2 = new PdfPCell(new Paragraph(dimension + barrio ));
            PdfPCell importe2 = new PdfPCell(new Paragraph());
            table4.addCell(detalle2);
            table4.addCell(importe2);
            document.add(table4);
            PdfPTable table5 = new PdfPTable(3);
            table5.setTotalWidth(new float[]{ 1,1,1});
            table5.setWidthPercentage(100);
            PdfPCell vacio = new PdfPCell(new Paragraph());
            PdfPCell total_pagado = new PdfPCell(new Paragraph("Total pagado a la fecha"));
            PdfPCell total_variable = new PdfPCell(new Paragraph());
            table5.addCell(vacio);
            table5.addCell(total_pagado);
            table5.addCell(total_variable);
            document.add(table5);                        
            document.add( Chunk.NEWLINE );
            PdfPTable table6 = new PdfPTable(1);
            table5.setWidthPercentage(100);
            PdfPCell son_pesos = new PdfPCell(new Paragraph("Son pesos:"));
            table6.addCell(son_pesos);
            document.add(new Paragraph("Son pesos:"));  
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
