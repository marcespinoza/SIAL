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
import Modelo.PropietarioDAO;
import Vista.Dialogs.AltaRecibo;
import Vista.Frame.Ventana;
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
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
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
    PropietarioDAO pd = new PropietarioDAO();
    FichaControlDAO fcd = new FichaControlDAO();
    DetalleCuota dc = new DetalleCuota();
    String apellido_propietario, nombre_propietario, cuit_propietario, nro_recibo_propietario;
    String nombre_comprador, apellido_comprador, domicilio_comprador;
    String dimension, barrio;
    int cant_cuotas, manzana, parcela, row;
    BigDecimal cobrado, cuota_total, gastos_administrativos;
    int id_control, nro_cuota, nro_random;    
    public static final String IMG = "src/Imagenes/logo_reporte.png";
    public static final String IMG2 = "src/Imagenes/logo_recibo.png";
    Random random = new Random();
    int tipoPago;
    BigDecimal categoria;

    public ControladorRecibo(Frame parent, int id_control, DetalleCuota dc, int row, int tipoPago) {
        ar = new AltaRecibo(parent, true);
        this.dc=dc;
        this.row=row;
        this.tipoPago=tipoPago;
        ar.aceptar.addActionListener(this);
        ar.cancelar.addActionListener(this);
        ar.cuit.setEnabled(false);
        ar.tipo_cliente.add(ar.resp_insc);
        ar.tipo_cliente.add(ar.resp_no_insc);
        ar.tipo_cliente.add(ar.cons_final);
        ar.tipo_cliente.add(ar.monotributo);
        ar.tipo_cliente.add(ar.exento);
        ar.resp_insc.setActionCommand("resp_insc");
        ar.resp_no_insc.setActionCommand("resp_no_insc");
        ar.cons_final.setActionCommand("cons_final");
        ar.monotributo.setActionCommand("cons_final");
        ar.exento.setActionCommand("exento");
        ar.cons_final.setSelected(true);
        ar.monotributo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    ar.cuit.setEnabled(true);                
            }
        });
        ar.cons_final.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    ar.cuit.setEnabled(false);                
            }
        });
        ar.resp_insc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    ar.cuit.setEnabled(false);                
            }
        });
        ar.resp_no_insc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    ar.cuit.setEnabled(false);                
            }
        });
        ar.exento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    ar.cuit.setEnabled(false);                
            }
        });
        ar.cons_final.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    ar.cuit.setEnabled(false);                
            }
        });
        nro_random = random.nextInt(100);
        this.id_control=id_control;
        new SwingWorker().execute();
    }
    
    private void datosPropietario(){
        ResultSet rs = ld.obtenerPropietarioxLote(id_control);
        try {
            rs.next();
            apellido_propietario = rs.getString(1);
            nombre_propietario = rs.getString(2);
            cuit_propietario = rs.getString(3);
            nro_recibo_propietario = rs.getString(4);
            ar.apellido_propietario.setText(apellido_propietario);
            ar.nombre_propietario.setText(nombre_propietario);    
            ar.cuit_propietario.setText(cuit_propietario);
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
            categoria = new BigDecimal(rs.getString(3)).add(new BigDecimal(rs.getString(4)));
            barrio= rs.getString(6);
            manzana = rs.getInt(7);
            parcela = rs.getInt(8);
            if(tipoPago==1){
             cuota_total = new BigDecimal(dc.tablaDetallePago.getModel().getValueAt(row, 3).toString());
             gastos_administrativos = new BigDecimal(dc.tablaDetallePago.getModel().getValueAt(row, 4).toString());
             cobrado = cuota_total.add(gastos_administrativos) ;
             ar.importe.setText(String.valueOf(cobrado));
             ar.total_pagado.setText(String.valueOf(cobrado));            
             ar.detalle.setText("Paga cuota "+dc.tablaDetallePago.getModel().getValueAt(row, 0).toString()+"/"+cant_cuotas+    "\r\nDimension "+dimension +     "\r\n"+ barrio +" "+ " Mz. "+manzana +" Pc. "+ parcela+   "\r\n"+dc.tablaDetallePago.getModel().getValueAt(row, 2).toString());
            }else if (tipoPago==0){
              ar.detalle.setText("Cta. derecho posesión "+    "\r\nDimension "+dimension +     "\r\n"+ barrio +" "+ " Mz. "+manzana +" Pc. "+ parcela+   "\r\n");
              cuota_total = new BigDecimal(dc.tablaDchoPosesion.getModel().getValueAt(row, 2).toString());
              gastos_administrativos = new BigDecimal(dc.tablaDetallePago.getModel().getValueAt(row, 3).toString());
              cobrado = cuota_total.add(gastos_administrativos) ;
              ar.importe.setText(String.valueOf(cobrado));
              ar.total_pagado.setText(String.valueOf(cobrado)); 
            }
            } catch (Exception e) {
        }
    }
    
    public void generarRecibo(){
            Document document= new Document(PageSize.A4);
            DateFormat fecha1 = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat fecha2 = new SimpleDateFormat("dd.MM.yyyy");
            java.util.Date date = new java.util.Date();
            String nombre_pdf = fecha2.format(date)+".pdf";
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(new File(dc.path.getText(), "Recibo-"+nro_recibo_propietario+".pdf")));
            document.open();
            for (int i = 1; i < 3; i++) {               
            Font f=new Font(Font.FontFamily.TIMES_ROMAN,10.0f,0,null);
            Image image = Image.getInstance(IMG); 
            Image image2 = Image.getInstance(IMG2);
            image.scaleAbsolute(70, 70);
            image2.scaleAbsolute(25, 38);
            PdfPTable table = new PdfPTable(1); 
            table.setWidthPercentage(57);
            table.setHorizontalAlignment(Element.ALIGN_CENTER); 
            PdfPCell cell3 = new PdfPCell(new Paragraph("Recibo por cuenta y orden de terceros"));
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell3);
            document.add(table); 
            Paragraph para = new Paragraph("Numero: "+nro_recibo_propietario,f);
            Paragraph cuit = new Paragraph("CUIT: 23-07431900-9",f);
            Paragraph ingBrutos = new Paragraph("Ing Brutos: 01-23-07431900-9",f);
            Paragraph inicAct = new Paragraph(fecha1.format(date)+"                                                        Inic. Act.: 01-08-2015",f); 
            switch(i){
                case 1:document.add(new Chunk(image, 0, -55f)); document.add(new Chunk(image2, 190f, -40f));para.setSpacingBefore(-10); break;
                case 2:document.add(new Chunk(image, 0, -38f)); document.add(new Chunk(image2, 190f, -22));para.setSpacingBefore(-26); break;
            }                         
            para.setAlignment(Paragraph.ALIGN_RIGHT);
            cuit.setAlignment(Paragraph.ALIGN_RIGHT);
            ingBrutos.setAlignment(Paragraph.ALIGN_RIGHT);
            inicAct.setAlignment(Paragraph.ALIGN_RIGHT);
            inicAct.setSpacingAfter(5);
            document.add(para);
            document.add(cuit);
            document.add(ingBrutos);
            document.add(inicAct);
            PdfPTable table2 = new PdfPTable(1);
            table2.setWidthPercentage(100);
            PdfPCell cell4 = new PdfPCell(new Paragraph("Propietario: " + ar.apellido_propietario.getText() + " " + ar.nombre_propietario.getText()+"                                     "+"CUIT: "+ar.cuit_propietario.getText(),f));
            PdfPCell cell5 = new PdfPCell(new Paragraph("Nombre comprador: " + apellido_comprador +" "+ nombre_comprador,f));
            PdfPCell cell6 = new PdfPCell(new Paragraph("Domicilio: " + domicilio_comprador,f));
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
            PdfPCell check1 = null;
            if(ar.tipo_cliente.getSelection().getActionCommand().equals("resp_insc")){
            check1 = new PdfPCell(new Paragraph("Iva Resp. Inscripto: X",f));}else{
            check1 = new PdfPCell(new Paragraph("Iva Resp. Inscripto: ",f));
            }
            check1.setPaddingTop(8);
            check1.setBorder(Rectangle.LEFT);
            checkbox.addCell(check1);
            PdfPCell check3 = new PdfPCell(new Paragraph("Resp. No Inscripto: ",f));
            check3.setPaddingTop(8);
            check3.setBorder(Rectangle.NO_BORDER);
            checkbox.addCell(check3);
            PdfPCell check5 = new PdfPCell(new Paragraph("Monotributo: ",f));
            check5.setPaddingTop(8);
            check5.setPaddingBottom(8);
            check5.setBorder(Rectangle.NO_BORDER);
            checkbox.addCell(check5);
            PdfPCell check7 = new PdfPCell(new Paragraph("Exento: ",f));
            check7.setPaddingTop(8);
            check7.setPaddingBottom(8);
            check7.setBorder(Rectangle.NO_BORDER);
            checkbox.addCell(check7);
            PdfPCell check9 = null;
            if(ar.tipo_cliente.getSelection().getActionCommand().equals("cons_final")){
            check9 = new PdfPCell(new Paragraph("Cons. final: X",f));}else{
            check9 = new PdfPCell(new Paragraph("Cons. final: ",f));
            }
            check9.setPaddingTop(8);
            check9.setPaddingBottom(8);
            check9.setBorder(Rectangle.RIGHT);
            checkbox.addCell(check9);
            document.add(checkbox);
            PdfPTable table3 = new PdfPTable(2);
            table3.setTotalWidth(new float[]{ 3,1});
            table3.setWidthPercentage(100);
            PdfPCell detalle = new PdfPCell(new Paragraph("Detalle ",f));
            PdfPCell importe = new PdfPCell(new Paragraph("Importe ",f));
            table3.addCell(detalle);
            table3.addCell(importe);
            document.add(table3);
             PdfPTable table4 = new PdfPTable(2);
            table4.setTotalWidth(new float[]{ 3,1});
            table4.setWidthPercentage(100);
            PdfPCell detalle2 = new PdfPCell(new Paragraph(ar.detalle.getText(),f));
            PdfPCell importe2 = new PdfPCell(new Paragraph(ar.importe.getText(),f));
            table4.addCell(detalle2);
            table4.addCell(importe2);
            document.add(table4);
            PdfPTable table5 = new PdfPTable(2);
            table5.setTotalWidth(new float[]{ 3,1});
            table5.setWidthPercentage(100);
            PdfPCell total_pagado = new PdfPCell(new Paragraph("Total pagado a la fecha: ",f));
            PdfPCell total_variable = new PdfPCell(new Paragraph(ar.total_pagado.getText(),f));
            table5.addCell(total_pagado);
            table5.addCell(total_variable);
            document.add(table5);             
            document.add(new Paragraph("Son pesos: "+ar.son_pesos.getText(),f)); 
            PdfPTable table6 = new PdfPTable(3);
            table6.setTotalWidth(new float[]{ 1,1,1});
            table6.setWidthPercentage(100);
            PdfPCell cell7 = new PdfPCell(new Paragraph("Cobró: "+ Ventana.nombreUsuario.getText()+" "+Ventana.apellidoUsuario.getText(),f));
            cell7.setPaddingTop(5);
            cell7.setBorder(Rectangle.NO_BORDER);
            table6.addCell(cell7);
            PdfPCell cell8 = new PdfPCell(new Paragraph("Supervisó: ",f));
            cell8.setPaddingTop(5);
            cell8.setBorder(Rectangle.NO_BORDER);
            table6.addCell(cell8);
            PdfPCell cell9 = new PdfPCell(new Paragraph("Controló: ",f));
            cell9.setPaddingTop(5);
            cell9.setPaddingBottom(5);
            cell9.setBorder(Rectangle.NO_BORDER);
            table6.addCell(cell9);            
            document.add(table6);
            document.add(new Paragraph("Efectivo/otros recepcionado ",f)); 
            Paragraph linea_punteada = new Paragraph("------------------------------------------------------------------------------------------------------------------------");
            linea_punteada.setAlignment(Rectangle.ALIGN_CENTER);
            document.add(linea_punteada);
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
            Date date = new Date();
            BigDecimal rendido = cobrado.subtract(gastos_administrativos);
            if(tipoPago==1){
              md.altaMinuta(new java.sql.Date(date.getTime()), apellido_comprador, nombre_comprador, manzana, parcela, cobrado, gastos_administrativos, rendido, Integer.parseInt(dc.tablaDetallePago.getModel().getValueAt(row, 0).toString()), dc.tablaDetallePago.getModel().getValueAt(row, 12).toString(), categoria.toString(), Integer.parseInt(nro_recibo_propietario));
            }else if(tipoPago==0){
              md.altaMinuta(new java.sql.Date(date.getTime()), apellido_comprador, nombre_comprador, manzana, parcela, cobrado, gastos_administrativos, rendido, Integer.parseInt(dc.tablaDchoPosesion.getModel().getValueAt(row, 0).toString()), dc.tablaDetallePago.getModel().getValueAt(row, 12).toString(), "Cta. derecho posesión",Integer.parseInt(nro_recibo_propietario));
            }return null;
             }

       @Override
       public void done() { 
            new ControladorMinuta(Minuta.getInstance());
            pd.editarNroRecibo(apellido_propietario, nombre_propietario, cuit_propietario, Integer.parseInt(nro_recibo_propietario)+1);
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
