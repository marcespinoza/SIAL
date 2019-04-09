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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
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
    List<Minuta> listaMinutas = new ArrayList<Minuta>();
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
    
    public void llenarTabla(List<Minuta> minutas){
        listaMinutas = minutas;
        DefaultTableModel model = (DefaultTableModel) vistaMinuta.tablaMinuta.getModel();
        model.setRowCount(0);
        if(!minutas.isEmpty()){
            for (int i = 0; i < listaMinutas.size(); i++) {               
                Date fecha_minuta = listaMinutas.get(i).getFechaMinuta();
                String apellidos = listaMinutas.get(i).getApellidos();
                String nombres = listaMinutas.get(i).getNombres();
                String mzpc = String.valueOf(listaMinutas.get(i).getManzana()) +" - "+ String.valueOf(listaMinutas.get(i).getParcela());
                BigDecimal cobrado = listaMinutas.get(i).getCobrado();
                BigDecimal gastos = listaMinutas.get(i).getGastos();
                BigDecimal rendido = listaMinutas.get(i).getRendido();
                int nro_recibo = listaMinutas.get(i).getNroRecibo();
                int nro_cuota = listaMinutas.get(i).getNroCuota();
                String observaciones = listaMinutas.get(i).getObservaciones();
                int baja = listaMinutas.get(i).getBaja();
                minuta = new Object[] {fecha_minuta,apellidos, nombres, mzpc, cobrado, gastos, rendido, nro_recibo, nro_cuota, observaciones, baja};
                model.addRow(minuta);   
            } 
        } 
            totalCobrado();
            totalRendido();        
        }
    
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
                  if(listaMinutas.isEmpty()){
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
                List<Minuta> listaMinutas = md.minutasPorRango2(new java.sql.Date(vistaMinuta.minutaDesde.getDate().getTime()) , new java.sql.Date(vistaMinuta.minutaHasta.getDate().getTime()));
                llenarTabla(listaMinutas);
            }
    }

    
    public void generarPdf(){
      Document document= new Document(PageSize.A4);
      DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
      DateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy");
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
            Paragraph titulo = new Paragraph("Minuta general"+"                      "+dateFormat.format(date));  
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            document.add( Chunk.NEWLINE );
            Paragraph subtitulo = null; 
            if((vistaMinuta.minutaDesde.getDate())!=null){ 
                subtitulo = new Paragraph(dateFormat2.format(vistaMinuta.minutaDesde.getDate().getTime())+"  a  "+dateFormat2.format(vistaMinuta.minutaHasta.getDate().getTime()));
            }else{
                int row = vistaMinuta.tablaFechaMinuta.getSelectedRow();  
                subtitulo = new Paragraph(vistaMinuta.tablaFechaMinuta.getModel().getValueAt(row,1).toString());
            }              
            subtitulo.setAlignment(Element.ALIGN_CENTER);
            document.add(subtitulo);
            document.add( Chunk.NEWLINE );
            PdfPTable table = new PdfPTable(9);            
            table.setTotalWidth(new float[]{1,2,4,2,2,2,2,2,3});
            table.setWidthPercentage(100);
            PdfPCell orden = new PdfPCell(new Paragraph("ORD",f2));
            PdfPCell rbo_nro = new PdfPCell(new Paragraph("Rbo. Nro",f));
            PdfPCell apynom = new PdfPCell(new Paragraph("Apellido y nombre",f));
            PdfPCell mzpc = new PdfPCell(new Paragraph("Mz./Pc.",f));
            PdfPCell cobrado = new PdfPCell(new Paragraph("Cobrado",f));
            PdfPCell gastos = new PdfPCell(new Paragraph("Gastos. Adm.",f));
            PdfPCell rendido = new PdfPCell(new Paragraph("Rendido",f));
            PdfPCell nro_cuota = new PdfPCell(new Paragraph("Cuota",f));
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
          //-------Itero-----------//
              int conta = 1;
              BigDecimal acumulador_cobrado = BigDecimal.ZERO;
              BigDecimal acumulador_gastos = BigDecimal.ZERO;
              BigDecimal acumulador_rendido = BigDecimal.ZERO;             
              BigDecimal deposito = BigDecimal.ZERO;
              BigDecimal t_debito = BigDecimal.ZERO;
              BigDecimal t_credito = BigDecimal.ZERO;
              BigDecimal efectivo = BigDecimal.ZERO;
              for (int i = 0; i < listaMinutas.size(); i++) {                 
                  PdfPTable table2 = new PdfPTable(9);            
                  table2.setTotalWidth(new float[]{ 1,2,4,2,2,2,2,2,3});
                  table2.setWidthPercentage(100);
                  PdfPCell nro_orden = new PdfPCell(new Paragraph(String.valueOf(conta),f));
                  nro_orden.setHorizontalAlignment(Element.ALIGN_CENTER);
                  table2.addCell(new PdfPCell(nro_orden));    
                  PdfPCell nro_recibo = new PdfPCell(new Paragraph(String.valueOf(listaMinutas.get(i).getNroRecibo()),f));
                  nro_recibo.setHorizontalAlignment(Element.ALIGN_CENTER);
                  table2.addCell(nro_recibo);      
                  PdfPCell nyap = new PdfPCell(new Paragraph(listaMinutas.get(i).getApellidos()+" "+listaMinutas.get(i).getNombres(),f));
                  nyap.setHorizontalAlignment(Element.ALIGN_CENTER);
                  table2.addCell(nyap);
                  PdfPCell mz_pc = new PdfPCell(new Paragraph(String.valueOf(listaMinutas.get(i).getManzana())+"/"+String.valueOf(listaMinutas.get(i).getParcela()),f));
                  mz_pc.setHorizontalAlignment(Element.ALIGN_CENTER);
                  table2.addCell(mz_pc);
                  table2.addCell(new PdfPCell(new Paragraph("$ "+String.valueOf(listaMinutas.get(i).getCobrado()),f)));
                  table2.addCell(new PdfPCell(new Paragraph("$ "+String.valueOf(listaMinutas.get(i).getGastos()),f)));
                  table2.addCell(new PdfPCell(new Paragraph("$ "+String.valueOf(listaMinutas.get(i).getRendido()),f)));
                  PdfPCell nrocuota = new PdfPCell(new Paragraph(String.valueOf(listaMinutas.get(i).getNroCuota()),f));
                  nrocuota.setHorizontalAlignment(Element.ALIGN_CENTER);
                  table2.addCell(nrocuota);
                  table2.addCell(new PdfPCell(new Paragraph(listaMinutas.get(i).getObservaciones(),f)));
                  document.add(table2);
                  //-------Controlo que el cliente no este dado de baja------//
                  if(listaMinutas.get(i).getBaja()!=1){
                     acumulador_cobrado = acumulador_cobrado.add(listaMinutas.get(i).getCobrado());
                     acumulador_gastos = acumulador_gastos.add(listaMinutas.get(i).getGastos());  
                     acumulador_rendido = acumulador_rendido.add(listaMinutas.get(i).getRendido());
                      switch(listaMinutas.get(i).getObservaciones()){                     
                          case "Dpto. Bancario":deposito = deposito.add(listaMinutas.get(i).getRendido());break; 
                          case "Tarjeta credito":t_debito = t_debito.add(listaMinutas.get(i).getRendido());break;
                          case "Tarjeta debito":t_credito = t_credito.add(listaMinutas.get(i).getRendido());break;
                          case "Efectivo":efectivo = efectivo.add(listaMinutas.get(i).getRendido());break;
                      }
                  }                 
                  conta ++;
              }
            //------Linea Totales------//
            PdfPTable tableTotales = new PdfPTable(9);            
            tableTotales.setTotalWidth(new float[]{ 1,2,4,2,2,2,2,2,3});
            tableTotales.setWidthPercentage(100);  
            Font ftotal=new Font(Font.FontFamily.TIMES_ROMAN,9.0f,0,null); 
            PdfPCell cellcobrado = new PdfPCell(new Paragraph("$ "+String.format ("%.2f",acumulador_cobrado), ftotal));
            PdfPCell cellgastos = new PdfPCell(new Paragraph("$ "+String.format ("%.2f",acumulador_gastos), ftotal));
            PdfPCell cellrendido = new PdfPCell(new Paragraph("$ "+String.format ("%.2f",acumulador_rendido), ftotal));
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
            PdfPTable tablaa = new PdfPTable(2);
            tablaa.setWidthPercentage(50);
            tablaa.setSpacingBefore(10f);
            PdfPCell nuevo = new PdfPCell(new Paragraph("Efectivo $", f));
            PdfPCell nuevo2 = new PdfPCell(new Paragraph(String.format ("%.2f",efectivo), f));
            nuevo2.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tablaa.addCell(nuevo);
            tablaa.addCell(nuevo2);
            document.add(tablaa);
            if(deposito.compareTo(BigDecimal.ZERO) != 0){
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
            PdfPCell total2 = new PdfPCell(new Paragraph(String.format ("%.2f",acumulador_rendido), f));
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
            PdfPTable pie = new PdfPTable(3);
            pie.setWidthPercentage(90);
            pie.setSpacingBefore(10f);
            PdfPCell confecciono = new PdfPCell(new Paragraph("Confeccionó", f));
            PdfPCell controlo = new PdfPCell(new Paragraph("Controló", f));
            PdfPCell conformidad = new PdfPCell(new Paragraph("Conformidad", f));
            confecciono.setBorder(Rectangle.NO_BORDER);
            controlo.setBorder(Rectangle.NO_BORDER);
            controlo.setHorizontalAlignment(Element.ALIGN_LEFT);
            conformidad.setBorder(Rectangle.NO_BORDER);
            conformidad.setHorizontalAlignment(Element.ALIGN_LEFT);
            pie.addCell(confecciono);
            pie.addCell(controlo);
            pie.addCell(conformidad);
            document.add(pie);
            document.add( Chunk.NEWLINE );
            //---------Tabla de billetes-----------//
            PdfPTable tablaBilletes2 = new PdfPTable(3);
            tablaBilletes2.setWidthPercentage(50);
            tablaBilletes2.setSpacingBefore(10f);
            PdfPCell billetes1 = new PdfPCell(new Paragraph("Cant.", f));
            PdfPCell billetes2 = new PdfPCell(new Paragraph("Valor unitario", f));
            PdfPCell billetes3 = new PdfPCell(new Paragraph("Valor total", f));
            tablaBilletes2.addCell(billetes1);
            tablaBilletes2.addCell(billetes2);
            tablaBilletes2.addCell(billetes3);            
            for (int i = 1; i < 11; i++) {
                //---Agrego celdas vacias----//                
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
             }             
             PdfPTable nesting = new PdfPTable(1);
             nesting.setWidthPercentage(50);
             PdfPCell cell = new PdfPCell(tablaBilletes2);
             cell.setBorder(PdfPCell.NO_BORDER);
             nesting.addCell(cell);
             document.add(nesting); 
            document.close();
            }catch (DocumentException ex) {
            Logger.getLogger(DetalleCuota.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DetalleCuota.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "No se pudo generar el documento."+"\r\n"+ "Verifique que no lo tiene abierto actualmente.", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
        } catch (IOException ex) {
            Logger.getLogger(DetalleCuota.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
        
    public class MinutasPorFecha extends javax.swing.SwingWorker<Void, Void>{
         
         List<Minuta> minutas = null;
         DateFormat sdfr = new SimpleDateFormat("dd-MM-yyyy");

        @Override
        protected Void doInBackground() throws Exception {    
            int row = vistaMinuta.tablaFechaMinuta.getSelectedRow();    
            Date date = sdfr.parse(vistaMinuta.tablaFechaMinuta.getModel().getValueAt(row,1).toString());
            SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
            minutas = md.minutasPorFecha(input.format(date));
            return null;
        }

       @Override
       public void done() { 
           llenarTabla(minutas);
       }
    
     }
    
    private void totalCobrado(){
        BigDecimal total=new BigDecimal(0);
        for (int i = 0; i < vistaMinuta.tablaMinuta.getRowCount(); i++) {
         //----Las minutas que estan dadas de baja no las sumo----//   
          if(Integer.parseInt(vistaMinuta.tablaMinuta.getValueAt(i, 10).toString())==0){   
            total=total.add(new BigDecimal(vistaMinuta.tablaMinuta.getValueAt(i, 4).toString()));
          }
        }
        vistaMinuta.totalCobrado.setText("$ " + String.valueOf(total));
    }
    private void totalRendido(){
          BigDecimal total=new BigDecimal(0);
        for (int i = 0; i < vistaMinuta.tablaMinuta.getRowCount(); i++) {
           //----Las minutas que estan dadas de baja no las sumo----//     
           if(Integer.parseInt(vistaMinuta.tablaMinuta.getValueAt(i, 10).toString())==0 ){ 
            total=total.add(new BigDecimal(vistaMinuta.tablaMinuta.getValueAt(i, 6).toString()));
           }
        }
        vistaMinuta.totalRendido.setText("$ " + String.valueOf(total));
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        //---------Limpio los campos de los calendarios si selecciona una minuta diaria-------//
         vistaMinuta.minutaDesde.setCalendar(null);
         vistaMinuta.minutaHasta.setCalendar(null);
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
