/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ClienteDAO;
import Modelo.CuotaDAO;
import Modelo.FichaControlDAO;
import Clases.LimitadorCaracteres;
import Modelo.LoteDAO;
import Modelo.MinutaDAO;
import Clases.Propietario;
import Modelo.LimitLinesDocumentListener;
import Modelo.PropietarioDAO;
import Modelo.ReciboDAO;
import Vista.Dialogs.AltaRecibo;
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
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Desktop;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.border.EtchedBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Marcelo Espinoza
 */
public class ControladorRecibo implements ActionListener{
    
    AltaRecibo ar; 
    MinutaDAO md = new MinutaDAO();
    LoteDAO ld = new LoteDAO();
    ClienteDAO cd = new ClienteDAO();
    CuotaDAO cuod = new CuotaDAO();
    ReciboDAO rd = new ReciboDAO();
    PropietarioDAO pd = new PropietarioDAO();
    FichaControlDAO fcd = new FichaControlDAO();
    ControladorDetalleCuota cdc;
    DetalleCuota dc;
    String apellido_propietario, nombre_propietario, cuit_propietario;
    String nombre_comprador, apellido_comprador, domicilio_comprador;
    String dimension, barrio;
    int cant_cuotas, manzana, parcela, row;
    BigDecimal cobrado, cuota_total, gastos_administrativos;
    int id_control, nro_cuota;    
    public static final String IMG = "/Imagenes/logo_reporte.png";
    public static final String IMG2 = "/Imagenes/logo_recibo.png";
    Random random = new Random();
    int tipoPago;
    BigDecimal categoria;
    String resp_insc =""; 
    String resp_no_insc=""; 
    String cons_final=""; 
    String monotributo=""; 
    String exento="";
    File pathRecibo;
    static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ControladorCliente.class.getName());

    public ControladorRecibo(ControladorDetalleCuota cdc, Frame parent, int id_control, DetalleCuota dc, int nro_cuota, int row, int tipoPago) {
        ar = new AltaRecibo(parent, true);
        this.cdc=cdc;
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
        ar.monotributo.setActionCommand("monotributo");
        ar.exento.setActionCommand("exento");
        ar.detalle.getDocument().addDocumentListener(
        new LimitLinesDocumentListener(2));
        ar.detalle.setDocument(new PlainDocument() {
        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (str == null || ar.detalle.getText().length() >= 120) {
            return;
        } 
        super.insertString(offs, str, a);
         }
        });
        ar.apellido_propietario.setDocument(new LimitadorCaracteres(30));
        ar.nombre_propietario.setDocument(new LimitadorCaracteres(30));
        ar.cuit_propietario.setDocument(new LimitadorCaracteres(14));
        ar.apellido_comprador.setDocument(new LimitadorCaracteres(30));
        ar.nombre_comprador.setDocument(new LimitadorCaracteres(30));
        ar.domicilio_comprador.setDocument(new LimitadorCaracteres(40));
        ar.importe.setDocument(new LimitadorCaracteres(7));
        ar.total_pagado.setDocument(new LimitadorCaracteres(7));
        ar.son_pesos.setDocument(new LimitadorCaracteres(50));
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
        this.id_control=id_control;
        this.nro_cuota=nro_cuota;
        new RellenarCampos().execute();
    }
    
    private void datosPropietario(){
        ResultSet rs = ld.obtenerPropietarioxLote(id_control);
         int nroRecibo = 0;
         try {
            rs.next();
            apellido_propietario = rs.getString(1);
            nombre_propietario = rs.getString(2);
            cuit_propietario = rs.getString(3);
            ar.apellido_propietario.setText(apellido_propietario);
            ar.nombre_propietario.setText(nombre_propietario);    
            ar.cuit_propietario.setText(cuit_propietario);
            nroRecibo = pd.obtenerNroRecibo(apellido_propietario, nombre_propietario, cuit_propietario);
            ar.nro_recibo.setText(String.valueOf(nroRecibo));
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
            //------Si es 1 es cuota-------//
            if(tipoPago==1){
             cuota_total = new BigDecimal(dc.tablaDetallePago.getModel().getValueAt(row, 3).toString());
             gastos_administrativos = new BigDecimal(dc.tablaDetallePago.getModel().getValueAt(row, 4).toString());
             cobrado = cuota_total.add(gastos_administrativos) ;
             ar.importe.setText(String.valueOf(cobrado));
             ar.total_pagado.setText(String.valueOf(cobrado));            
             ar.detalle.setText("Paga cuota "+dc.tablaDetallePago.getModel().getValueAt(row, 0).toString()+"/"+cant_cuotas+ " - "+ "Saldo cemento "+dc.tablaDetallePago.getModel().getValueAt(row, 10).toString()+ " Bolsas" +"\r\n"+ barrio +" "+ " Mz. "+manzana +" Pc. "+ parcela+    " - Dimensión "+dimension +" - "+dc.tablaDetallePago.getModel().getValueAt(row, 11).toString());
            //-----Si es 0 es derecho de posesion-------//
            }else if (tipoPago==0){
              ar.detalle.setText("Cta. derecho posesión "+    "\r\nDimension "+dimension +     "\r\n"+ barrio +" "+ " Mz. "+manzana +" Pc. "+ parcela+   "\r\n");
              cuota_total = new BigDecimal(dc.tablaDchoPosesion.getModel().getValueAt(row, 2).toString());
              gastos_administrativos = new BigDecimal(dc.tablaDchoPosesion.getModel().getValueAt(row, 3).toString());
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
            java.util.Date date = new java.util.Date();
        try {
            pathRecibo = new File(dc.path.getText(), "Recibo-"+ar.nro_recibo.getText()+".pdf");
            PdfWriter.getInstance(document, new FileOutputStream(pathRecibo));
            document.open();
            for (int i = 1; i < 4; i++) {               
            Font f=new Font(Font.FontFamily.TIMES_ROMAN,10.0f,0,null);
            Image image = Image.getInstance(getClass().getResource(IMG)); 
            Image image2 = Image.getInstance(getClass().getResource(IMG2));
            image.scaleAbsolute(70, 70);
            image2.scaleAbsolute(25, 38);
            PdfPTable table = new PdfPTable(1); 
            table.setWidthPercentage(57);
            table.setHorizontalAlignment(Element.ALIGN_CENTER); 
            PdfPCell cell3 = new PdfPCell(new Paragraph("Recibo por cuenta y orden de terceros"));
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell3);
            document.add(table); 
            Paragraph para = new Paragraph("Numero: "+ar.nro_recibo.getText(),f);
            Paragraph cuit = new Paragraph("CUIT: 23-07431900-9",f);
            Paragraph ingBrutos = new Paragraph("Ing Brutos: 01-23-07431900-9",f);
            Paragraph inicAct = new Paragraph(fecha1.format(date)+"                                                        Inic. Act.: 01-08-2015",f); 
            switch(i){
                case 1:document.add(new Chunk(image, 0, -55f)); document.add(new Chunk(image2, 190f, -40f));para.setSpacingBefore(-10); break;
                case 2:document.add(new Chunk(image, 0, -38f)); document.add(new Chunk(image2, 190f, -22));para.setSpacingBefore(-26); break;
                case 3:document.add(new Chunk(image, 0, -28f)); document.add(new Chunk(image2, 190f, -22));para.setSpacingBefore(-26); break;
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
            switch (ar.tipo_cliente.getSelection().getActionCommand()){
                    case "resp_insc":resp_insc="X"; break;
                    case "resp_no_insc": resp_no_insc="X"; break;
                    case "cons_final": cons_final="X"; break;
                    case "monotributo": monotributo="X"; break;
                    case "exento": exento="X"; break;
            }            
            PdfPCell check1 = null;
            check1 = new PdfPCell(new Paragraph("Iva Resp. Inscripto:"+resp_insc,f));
            check1.setPaddingTop(8);
            check1.setBorder(Rectangle.LEFT);
            checkbox.addCell(check1);
            PdfPCell check3 = new PdfPCell(new Paragraph("Resp. No Inscripto:"+resp_no_insc,f));
            check3.setPaddingTop(8);
            check3.setBorder(Rectangle.NO_BORDER);
            checkbox.addCell(check3);
            PdfPCell check5 = new PdfPCell(new Paragraph("Monotributo:"+monotributo,f));
            check5.setPaddingTop(8);
            check5.setPaddingBottom(8);
            check5.setBorder(Rectangle.NO_BORDER);
            checkbox.addCell(check5);
            PdfPCell check7 = new PdfPCell(new Paragraph("Exento:"+exento,f));
            check7.setPaddingTop(8);
            check7.setPaddingBottom(8);
            check7.setBorder(Rectangle.NO_BORDER);
            checkbox.addCell(check7);
            PdfPCell check9 = null;
            check9 = new PdfPCell(new Paragraph("Cons. final:"+cons_final,f));           
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
            if(i!=3){
            Paragraph linea_punteada = new Paragraph("------------------------------------------------------------------------------------------------------------------------");
            linea_punteada.setAlignment(Rectangle.ALIGN_CENTER);
            document.add(linea_punteada);}
            }
            document.close();
        }catch (DocumentException ex) {
            Logger.getLogger(DetalleCuota.class.getName()).log(Level.SEVERE, null, ex);
            log.error(ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DetalleCuota.class.getName()).log(Level.SEVERE, null, ex);
            log.error(ex);
        } catch (IOException ex) {
            Logger.getLogger(DetalleCuota.class.getName()).log(Level.SEVERE, null, ex);
            log.error(ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == ar.aceptar){
            if(validarCampos()){                     
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
    
    //---------Hilo para rellenar los campos cuando se muestra el formulario-----//    
      public class RellenarCampos extends javax.swing.SwingWorker<Void, Void>{

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
      
      //----Hilo para genera pdf de las minutas-----//      
       public class GenerarMinuta extends javax.swing.SwingWorker<Void, Void>{
         
       private int id_recibo=0;  
       ProgressDialog progress = new ProgressDialog();

        @Override
        protected Void doInBackground() throws Exception {     
           
            progress.setVisible(true);
            //-----Devuelve id del recibo creado-----//
            id_recibo = rd.altaRecibo(Integer.parseInt(ar.nro_recibo.getText()), apellido_propietario, nombre_propietario);         
            return null;
       }

       @Override
       public void done() { 
            Date date = new Date();
            BigDecimal rendido = cobrado.subtract(gastos_administrativos);
            Ventana.cm.llenarTablaFecha();
            if(tipoPago==1){
                System.out.println("observa"+dc.tablaDetallePago.getModel().getValueAt(row, 11).toString());
              md.altaMinuta(new java.sql.Date(date.getTime()), apellido_comprador, nombre_comprador, manzana, parcela, cobrado, gastos_administrativos, rendido, 
                      Integer.parseInt(dc.tablaDetallePago.getModel().getValueAt(row, 0).toString()), 
                      dc.tablaDetallePago.getModel().getValueAt(row, 14).toString(), 
                      categoria.toString(), 
                      id_recibo);
            }else if(tipoPago==0){
              md.altaMinuta(new java.sql.Date(date.getTime()), apellido_comprador, nombre_comprador, manzana, parcela, cobrado, gastos_administrativos, rendido, Integer.parseInt(dc.tablaDchoPosesion.getModel().getValueAt(row, 0).toString()), dc.tablaDetallePago.getModel().getValueAt(row, 13).toString()+" Dcho. posesión", "Cta. derecho posesión",id_recibo);
            }
            pd.editarNroRecibo(apellido_propietario, nombre_propietario, cuit_propietario, Integer.parseInt(ar.nro_recibo.getText())+1);
            cuod.actualizarNroRecibo(Integer.parseInt(ar.nro_recibo.getText()), id_recibo, nro_cuota, id_control);
            progress.dispose();
            ar.dispose();
           try {
            //-------Abro pdf del recibo-------//   
               Desktop.getDesktop().open(pathRecibo);
           } catch (IOException ex) {
               Logger.getLogger(ControladorRecibo.class.getName()).log(Level.SEVERE, null, ex);
           }
            cdc.llearTablaDchoPosesion(id_control);
            cdc.llenarTabla(id_control);
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
            if(ar.nro_recibo.getText().isEmpty()){
             ar.nro_recibo.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
              bandera=false;
         }else{
             ar.nro_recibo.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
          }
          return bandera;
      }
    
}
