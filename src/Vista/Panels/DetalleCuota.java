/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Panels;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import conexion.Conexion;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcelo
 */
public class DetalleCuota extends javax.swing.JPanel {

    private String idControl;
    private Object [] detalles;
    private String nombre, apellido;
    public static final String IMG = "src/Imagenes/logo_reporte.png";
    
    public DetalleCuota() {
        initComponents();
    }

    public DetalleCuota(String idControl,String apellido, String nombre) {
        this.idControl = idControl;   
        initComponents();
        apellidoLabel.setText(apellido);
        nombreLabel.setText(nombre);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDetallePago = new javax.swing.JTable();
        volverBtn = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        agregarPagoBtn = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        nombreLabel = new javax.swing.JLabel();
        apellidoLabel = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1112, 479));

        tablaDetallePago.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Detalle ", "Cuota pura", "Gastos admin.", "Debe", "Haber", "Saldo", "Cemento debe", "Cemento haber", "Cemento saldo", "Observaciones", "Tipo pago"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaDetallePago);

        volverBtn.setText("Volver");

        jButton2.setText("Generar recibo");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        agregarPagoBtn.setText("Agregar pago");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cliente", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 3, 12))); // NOI18N

        nombreLabel.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N

        apellidoLabel.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(nombreLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(2, 2, 2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(apellidoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(apellidoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(nombreLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(agregarPagoBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(volverBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 674, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(agregarPagoBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(volverBtn))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
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
            PdfPCell cell4 = new PdfPCell(new Paragraph("Propietario:......."));
            PdfPCell cell5 = new PdfPCell(new Paragraph("Nombre comprador:....."));
            cell4.disableBorderSide(2);
            cell5.disableBorderSide(1);
            table2.addCell(cell4);
             table2.addCell(cell5);
             document.add(table2);
            PdfPTable checkbox = new PdfPTable(8);
            checkbox.setTotalWidth(new float[]{ 3,1,3,1,3,1,3,1});
            checkbox.setWidthPercentage(100);
            Font f=new Font(FontFamily.TIMES_ROMAN,10.0f,0,null);
            PdfPCell check1 = new PdfPCell(new Paragraph("Iva Resp. Inscripto",f));
            PdfPCell check2 = new PdfPCell(new Paragraph(""));
            check1.disableBorderSide(4);
            check1.disableBorderSide(3);
            checkbox.addCell(check1);
            checkbox.addCell(check2);
            PdfPCell check3 = new PdfPCell(new Paragraph("Resp. No Inscripto",f));
            PdfPCell check4 = new PdfPCell(new Paragraph(""));
            check3.disableBorderSide(4);
            check3.disableBorderSide(3);
            checkbox.addCell(check3);
            checkbox.addCell(check4);
            PdfPCell check5 = new PdfPCell(new Paragraph("Monotributo",f));
            PdfPCell check6 = new PdfPCell(new Paragraph(""));
            check5.disableBorderSide(4);
            check5.disableBorderSide(3);
            checkbox.addCell(check5);
            checkbox.addCell(check6);
            PdfPCell check7 = new PdfPCell(new Paragraph("Exento",f));
            PdfPCell check8 = new PdfPCell(new Paragraph(""));
            check7.disableBorderSide(4);
            check7.disableBorderSide(3);
            checkbox.addCell(check7);
            checkbox.addCell(check8);
            document.add(checkbox);
            document.close();
            }catch (DocumentException ex) {
            Logger.getLogger(DetalleCuota.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DetalleCuota.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DetalleCuota.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2MouseClicked

    class ParagraphBorder extends PdfPageEventHelper {
    public boolean active = false;
    public void setActive(boolean active) {
        this.active = active;
    }

    public float offset = 5;
    public float startPosition;

    @Override
    public void onParagraph(PdfWriter writer, Document document, float paragraphPosition) {
        this.startPosition = paragraphPosition;
    }

    @Override
    public void onParagraphEnd(PdfWriter writer, Document document, float paragraphPosition) {
        if (active) {
            PdfContentByte cb = writer.getDirectContentUnder();
            cb.rectangle(document.left(), paragraphPosition - offset,
                document.right() - document.left(), startPosition - paragraphPosition);
            cb.stroke();
        }
    }
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton agregarPagoBtn;
    public javax.swing.JLabel apellidoLabel;
    public javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JLabel nombreLabel;
    public javax.swing.JTable tablaDetallePago;
    public javax.swing.JButton volverBtn;
    // End of variables declaration//GEN-END:variables
}