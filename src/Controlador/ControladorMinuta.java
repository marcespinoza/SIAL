/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Clases.Minuta;
import Modelo.MinutaDAO;
import Vista.Dialogs.ProgressDialog;
import Vista.Panels.DetalleCuota;
import Vista.Panels.MinutaVista;
import com.itextpdf.text.BaseColor;
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
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcelo Espinoza
 */
public class ControladorMinuta implements MouseListener, ActionListener {
    
    MinutaDAO md = new MinutaDAO();
    MinutaVista vistaMinuta;
    private Object [] minuta;
    private Object [] fechaMinuta;
    ResultSet resultset = null;
    File f;
    JFileChooser chooser;
    FileInputStream fileIn = null;
    FileOutputStream fileOut = null;
    File configFile = new File("config.properties");
    public static final String IMG = "/Imagenes/logo_reporte.png";
    File pathMinuta;
    static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ControladorCliente.class.getName());

    public ControladorMinuta(MinutaVista vistaMinuta) {
        this.vistaMinuta=vistaMinuta;
        this.vistaMinuta.tablaFechaMinuta.addMouseListener(this);
        this.vistaMinuta.generarMinuta.addActionListener(this);
        this.vistaMinuta.guardar_en.addActionListener(this);
        this.vistaMinuta.buscar.addActionListener(this);
        vistaMinuta.totalCobrado.setText("0");
        vistaMinuta.totalRendido.setText("0");
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
                String baja = rs.getString(11);
                minuta = new Object[] {fecha_minuta,apellidos, nombres, mzpc, cobrado, gastos, rendido, nro_cuota, observaciones, baja};
                model.addRow(minuta);   
            } 
            resultset.beforeFirst();
            totalCobrado();
            totalRendido();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }}
    
    public void llenarTablaFecha(){
        List<Minuta> minutas = null;
        minutas = md.obtenerFecha();
        DefaultTableModel model = (DefaultTableModel) vistaMinuta.tablaFechaMinuta.getModel();
        DefaultTableModel model2 = (DefaultTableModel) vistaMinuta.tablaMinuta.getModel();
        model2.setRowCount(0);
        model.setRowCount(0);
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        int nro = 1;
        for (int i = 0; i < minutas.size(); i++) {
            Date fecha= minutas.get(i).getFechaMinuta();
            fechaMinuta = new Object[] {nro,df.format(fecha)};
            model.addRow(fechaMinuta);
            nro ++;
        }
            
        
        vistaMinuta.tablaFechaMinuta.getColumnModel().getColumn(0).setPreferredWidth(1); 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            if(e.getSource()==vistaMinuta.generarMinuta){
                if(!vistaMinuta.path.getText().equals("")){
                  if(vistaMinuta.tablaFechaMinuta.getSelectedRow()==-1){
                       JOptionPane.showMessageDialog(null, "Selecciona una minuta", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
                  }else{
                      new GenerarPdfMinuta().execute();
                }}else{
                 JOptionPane.showMessageDialog(null, "Debe seleccionar ubicación donde guardar la minuta", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
                }                
            }
            
            if(e.getSource() == vistaMinuta.guardar_en){
                chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal = chooser.showOpenDialog(chooser);
                //------Verifico que aprete boton Aceptar------//
                if(returnVal==JFileChooser.APPROVE_OPTION){
                f = chooser.getSelectedFile();
                String path = f.getAbsolutePath();
                vistaMinuta.path.setText(path);
                try {
                Properties props = new Properties();                
                fileIn = new FileInputStream(configFile);
                props.load(fileIn);
                props.setProperty("pathMinuta", path);
                fileOut = new FileOutputStream(configFile);
                props.store(fileOut, "config");
                } catch (FileNotFoundException ex) {
                 // file does not exist
                } catch (IOException ex) {
                   // I/O error
                }
                }
            }
            
            if(e.getSource() == vistaMinuta.buscar){
                SimpleDateFormat dcn = new SimpleDateFormat("dd-MM-yyyy");
                 String desde = dcn.format(vistaMinuta.minutaDesde.getDate() );
                 String date = dcn.format(vistaMinuta.minutaHasta.getDate() );
                ResultSet rs = md.minutasPorRango2(new java.sql.Date(vistaMinuta.minutaDesde.getDate().getTime()) , new java.sql.Date(vistaMinuta.minutaHasta.getDate().getTime()));
                llenarTabla(rs);
            }
    }

    
    public void generarPdf(){
      Document document= new Document(PageSize.A4);
      DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
      DateFormat dateFormat2 = new SimpleDateFormat("dd.MM.yyyy");
      java.util.Date date = new java.util.Date();
        try {
            pathMinuta = new File(vistaMinuta.path.getText(), "Minuta - "+dateFormat2.format(date)+".pdf");
            PdfWriter.getInstance(document, new FileOutputStream(pathMinuta));
            document.open();
            Image image = Image.getInstance(getClass().getResource(IMG)); 
            image.scaleAbsolute(70, 70);
            Font f=new Font(Font.FontFamily.TIMES_ROMAN,10.0f,0,null); 
            Font f2=new Font(Font.FontFamily.TIMES_ROMAN,9.0f,0,null); 
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
            table.setTotalWidth(new float[]{ 1,2,4,2,2,2,2,2,4});
            table.setWidthPercentage(100);
            PdfPCell orden = new PdfPCell(new Paragraph("ORD",f2));
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
              double acumulador_cobrado = 0;
              double acumulador_gastos = 0;
              double acumulador_rendido = 0;              
              double deposito = 0;
              double t_debito = 0;
              double t_credito = 0;
              double total = 0;
              while(resultset.next()){
                  PdfPTable table2 = new PdfPTable(9);            
                  table2.setTotalWidth(new float[]{ 1,2,4,2,2,2,2,2,4});
                  table2.setWidthPercentage(100);
                  PdfPCell nro_orden = new PdfPCell(new Paragraph(String.valueOf(conta),f));
                  nro_orden.setHorizontalAlignment(Element.ALIGN_CENTER);
                  table2.addCell(new PdfPCell(nro_orden));    
                  PdfPCell nro_recibo = new PdfPCell(new Paragraph(resultset.getString(12),f));
                  nro_recibo.setHorizontalAlignment(Element.ALIGN_CENTER);
                  table2.addCell(nro_recibo);      
                  PdfPCell nyap = new PdfPCell(new Paragraph(resultset.getString(2)+" "+resultset.getString(3),f));
                  nyap.setHorizontalAlignment(Element.ALIGN_CENTER);
                  table2.addCell(nyap);
                  PdfPCell mz_pc = new PdfPCell(new Paragraph(resultset.getString(4)+"/"+resultset.getString(5),f));
                  mz_pc.setHorizontalAlignment(Element.ALIGN_CENTER);
                  table2.addCell(mz_pc);
                  table2.addCell(new PdfPCell(new Paragraph("$ "+resultset.getString(6),f)));
                  table2.addCell(new PdfPCell(new Paragraph("$ "+resultset.getString(7),f)));
                  table2.addCell(new PdfPCell(new Paragraph("$ "+resultset.getString(8),f)));
                  PdfPCell nrocuota = new PdfPCell(new Paragraph(resultset.getString(9),f));
                  nrocuota.setHorizontalAlignment(Element.ALIGN_CENTER);
                  table2.addCell(nrocuota);
                  table2.addCell(new PdfPCell(new Paragraph(resultset.getString(10),f)));
                  document.add(table2);
                  if(Integer.parseInt(resultset.getString(11))!=1){
                   acumulador_cobrado = acumulador_cobrado + resultset.getDouble(6);
                   acumulador_gastos = acumulador_gastos + resultset.getDouble(7);
                   
                  switch(resultset.getString(10).toString()){                     
                      case "Dpto. Bancario":deposito = deposito + resultset.getDouble(8);break; 
                      case "Tarjeta credito":t_debito = t_debito + resultset.getDouble(8);break;
                      case "Tarjeta debito":t_credito = t_credito + resultset.getDouble(8);break;
                      case "Efectivo":acumulador_rendido = acumulador_rendido + resultset.getDouble(8);break;
                  }}
                  conta ++;
              }
            //------Linea Totales------//
            PdfPTable tableTotales = new PdfPTable(9);            
            tableTotales.setTotalWidth(new float[]{ 1,2,4,2,2,2,2,2,4});
            tableTotales.setWidthPercentage(100);  
            PdfPCell cellcobrado = new PdfPCell(new Paragraph("$ "+String.format ("%.2f",acumulador_cobrado), f));
            PdfPCell cellgastos = new PdfPCell(new Paragraph("$ "+String.format ("%.2f",acumulador_gastos), f));
            PdfPCell cellrendido = new PdfPCell(new Paragraph("$ "+String.format ("%.2f",acumulador_rendido), f));
            PdfPCell cellEmpty = new PdfPCell(new Paragraph("",f));
            cellEmpty.setUseVariableBorders(true);
            cellEmpty.setBorderColorBottom(BaseColor.WHITE);
            cellEmpty.setBorderColorLeft(BaseColor.WHITE);
            cellEmpty.setBorderColorRight(BaseColor.WHITE);
            tableTotales.addCell(cellEmpty);
            tableTotales.addCell(cellEmpty);
            tableTotales.addCell(cellEmpty);
            tableTotales.addCell(cellEmpty);
            tableTotales.addCell(cellcobrado);
            tableTotales.addCell(cellgastos);
            tableTotales.addCell(cellrendido);
            tableTotales.addCell(cellEmpty);
            tableTotales.addCell(cellEmpty);
            document.add(tableTotales);
            //-------------//
            total = acumulador_rendido +deposito + t_credito + t_debito;
            PdfPTable tablaa = new PdfPTable(2);
            tablaa.setWidthPercentage(50);
            tablaa.setSpacingBefore(10f);
            PdfPCell nuevo = new PdfPCell(new Paragraph("Total rendido $", f));
            PdfPCell nuevo2 = new PdfPCell(new Paragraph(String.format ("%.2f",acumulador_rendido), f));
            nuevo2.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tablaa.addCell(nuevo);
            tablaa.addCell(nuevo2);
            document.add(tablaa);
            if(deposito != 0){
                 PdfPTable dpto_bancario = new PdfPTable(2);
                 dpto_bancario.setWidthPercentage(50);
                 PdfPCell dpto1 = new PdfPCell(new Paragraph("Depósito bancario $", f));
                 PdfPCell dpto2 = new PdfPCell(new Paragraph(String.format ("%.2f",deposito), f));
                 dpto2.setHorizontalAlignment(Element.ALIGN_RIGHT);
                 dpto_bancario.addCell(dpto1);
                 dpto_bancario.addCell(dpto2);
                 document.add(dpto_bancario); 
            }   
            //-----Total final------//
            PdfPTable total_final = new PdfPTable(2);
            total_final.setWidthPercentage(50);
            PdfPCell total1 = new PdfPCell(new Paragraph("Total $", f));
            PdfPCell total2 = new PdfPCell(new Paragraph(String.format ("%.2f",total), f));
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
            //---------Tabla de billetes-----------//
            PdfPTable tablaBilletes = new PdfPTable(3);
            tablaBilletes.setWidthPercentage(50);
            tablaBilletes.setSpacingBefore(10f);
            PdfPCell billetes1 = new PdfPCell(new Paragraph("Cant.", f));
            PdfPCell billetes2 = new PdfPCell(new Paragraph("Valor unitario", f));
            PdfPCell billetes3 = new PdfPCell(new Paragraph("Valor total", f));
            tablaBilletes.addCell(billetes1);
            tablaBilletes.addCell(billetes2);
            tablaBilletes.addCell(billetes3);            
            document.add(tablaBilletes);
            for (int i = 1; i < 11; i++) {
                //---Agrego celdas vacias----//
                PdfPTable tablaBilletes2 = new PdfPTable(3);
                tablaBilletes2.setWidthPercentage(50);
                PdfPCell empty1 = new PdfPCell(new Paragraph("\n", f));
                PdfPCell empty2 = null;
                switch (i) {
                case 1:  empty2 = new PdfPCell(new Paragraph("1000", f));  break;
                case 2:  empty2 = new PdfPCell(new Paragraph("500", f));  break;
                case 3:  empty2 = new PdfPCell(new Paragraph("200", f));  break;
                case 4:  empty2 = new PdfPCell(new Paragraph("100", f));  break;
                case 5:  empty2 = new PdfPCell(new Paragraph("50", f));  break;
                case 6:  empty2 = new PdfPCell(new Paragraph("20", f));  break;
                case 7:  empty2 = new PdfPCell(new Paragraph("10", f));  break;
                case 8:  empty2 = new PdfPCell(new Paragraph("5", f));  break;
                case 9:  empty2 = new PdfPCell(new Paragraph("2", f));  break;
                case 10: empty2 = new PdfPCell(new Paragraph("TOTAL", f));  break;
                  }
                PdfPCell empty3 = new PdfPCell(new Paragraph("\n", f));
                tablaBilletes2.addCell(empty1);
                tablaBilletes2.addCell(empty2);
                tablaBilletes2.addCell(empty3);            
                document.add(tablaBilletes2);  
             }
          } catch (SQLException ex) {
              Logger.getLogger(ControladorMinuta.class.getName()).log(Level.SEVERE, null, ex);
          }   
            document.close();
            }catch (DocumentException ex) {
            Logger.getLogger(DetalleCuota.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DetalleCuota.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "No se puedo generar el documento."+"\r\n"+ "Verifique no lo tiene abierto actualmente.", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
        } catch (IOException ex) {
            Logger.getLogger(DetalleCuota.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
        
    public class MinutasPorFecha extends javax.swing.SwingWorker<Void, Void>{
         
         ResultSet rs;
         DateFormat sdfr = new SimpleDateFormat("dd-MM-yyyy");

        @Override
        protected Void doInBackground() throws Exception {    
            int row = vistaMinuta.tablaFechaMinuta.getSelectedRow();    
            Date date = sdfr.parse(vistaMinuta.tablaFechaMinuta.getModel().getValueAt(row,1).toString());
            SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
            rs = md.minutasPorFecha(input.format(date));
            return null;
        }

       @Override
       public void done() { 
           llenarTabla(rs);
       }
    
     }
    
    private void totalCobrado(){
        BigDecimal total=new BigDecimal(0);
        for (int i = 0; i < vistaMinuta.tablaMinuta.getRowCount(); i++) {
         //----Las minutas que estan dadas de baja no las sumo----//   
          if(Integer.parseInt(vistaMinuta.tablaMinuta.getValueAt(i, 9).toString())==0){   
            total=total.add(new BigDecimal(vistaMinuta.tablaMinuta.getValueAt(i, 4).toString()));
          }
        }
        vistaMinuta.totalCobrado.setText("$ " + String.valueOf(total));
    }
    private void totalRendido(){
          BigDecimal total=new BigDecimal(0);
        for (int i = 0; i < vistaMinuta.tablaMinuta.getRowCount(); i++) {
           //----Las minutas que estan dadas de baja no las sumo----//     
           if(Integer.parseInt(vistaMinuta.tablaMinuta.getValueAt(i, 9).toString())==0){ 
            total=total.add(new BigDecimal(vistaMinuta.tablaMinuta.getValueAt(i, 6).toString()));
           }
        }
        vistaMinuta.totalRendido.setText("$ " + String.valueOf(total));
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
         new MinutasPorFecha().execute();         
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
    
    //-----Hilo para mostrar barra de progreso mientras se crea el resumen de cliente----// 
    public class GenerarPdfMinuta extends SwingWorker<Void, Void>{
        
        ProgressDialog pd = new ProgressDialog();

        @Override
        protected Void doInBackground() throws Exception {            
            pd.setVisible(true);
            generarPdf();
            return null;
        }
        @Override
        public void done(){
           pd.dispose();
            try {
            //--------Abro el pdf de la minuta creada----//
              Desktop.getDesktop().open(pathMinuta);
            } catch (IOException ex) {
                log.error(ex.getMessage());
                Logger.getLogger(ControladorMinuta.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
}
    
}
