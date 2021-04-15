/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Panels;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Component;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Marcelo
 */
public class DetalleCuota extends javax.swing.JPanel {

    public static final String IMG = "src/Imagenes/logo_reporte.png";
    
    public DetalleCuota() {
        initComponents();
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
        tablaDetallePago = new javax.swing.JTable()
        {
            public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int vColIndex) {
                Component c = super.prepareRenderer(renderer, rowIndex, vColIndex);
                if (isCellSelected(rowIndex, vColIndex)) {
                    c.setBackground(Color.yellow);
                }
                return c;
            }
        }
        ;
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaDchoPosesion = new javax.swing.JTable()
        {
            public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int vColIndex) {
                Component c = super.prepareRenderer(renderer, rowIndex, vColIndex);
                if (isCellSelected(rowIndex, vColIndex)) {
                    c.setBackground(Color.yellow);
                }
                return c;
            }
        }
        ;
        guardar = new javax.swing.JButton();
        path = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaActualizacion = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        volverBtn = new javax.swing.JButton();
        resumenCliente = new javax.swing.JButton();
        modificarPagoBtn = new javax.swing.JButton();
        actualizarPagoBtn = new javax.swing.JButton();
        agregarPagoBtn = new javax.swing.JButton();
        eliminarPagoBtn = new javax.swing.JButton();
        generarReciboBtn = new javax.swing.JButton();
        actualizarSaldoBtn = new javax.swing.JButton();
        nya = new javax.swing.JLabel();
        texto_indice_corrector = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1112, 479));

        JTableHeader header = tablaDetallePago.getTableHeader();
        Color micolor = new Color(36,47,65);
        tablaDetallePago.getTableHeader().setOpaque(false);
        header.setForeground(Color.white);
        header.setBackground(micolor);
        tablaDetallePago.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nro Cuota", "Fecha", "Detalle ", "Cuota pura", "Gastos admin.", "Debe", "Haber", "Saldo", "Cemento debe", "Cemento haber", "Cemento saldo", "Observaciones", "Nro recibo", "Id_recibo", "Tipo pago", "ac"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaDetallePago.setSelectionForeground(new java.awt.Color(0, 51, 153));
        jScrollPane1.setViewportView(tablaDetallePago);
        if (tablaDetallePago.getColumnModel().getColumnCount() > 0) {
            tablaDetallePago.getColumnModel().getColumn(13).setMinWidth(0);
            tablaDetallePago.getColumnModel().getColumn(13).setPreferredWidth(0);
            tablaDetallePago.getColumnModel().getColumn(13).setMaxWidth(0);
            tablaDetallePago.getColumnModel().getColumn(15).setMinWidth(0);
            tablaDetallePago.getColumnModel().getColumn(15).setPreferredWidth(0);
            tablaDetallePago.getColumnModel().getColumn(15).setMaxWidth(0);
        }

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cta. derecho posesión", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 3, 12))); // NOI18N

        JTableHeader headerPosesion = tablaDchoPosesion.getTableHeader();
        tablaDchoPosesion.getTableHeader().setOpaque(false);
        headerPosesion.setForeground(Color.white);
        headerPosesion.setBackground(micolor);
        tablaDchoPosesion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nro. cuota", "Fecha", "Monto", "Gastos", "Cto debe", "Cto haber", "Cto Saldo", "Recibo", "Detalle"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaDchoPosesion.setSelectionForeground(new java.awt.Color(0, 51, 153));
        jScrollPane2.setViewportView(tablaDchoPosesion);
        if (tablaDchoPosesion.getColumnModel().getColumnCount() > 0) {
            tablaDchoPosesion.getColumnModel().getColumn(0).setResizable(false);
            tablaDchoPosesion.getColumnModel().getColumn(0).setPreferredWidth(1);
            tablaDchoPosesion.getColumnModel().getColumn(1).setResizable(false);
            tablaDchoPosesion.getColumnModel().getColumn(1).setPreferredWidth(1);
            tablaDchoPosesion.getColumnModel().getColumn(2).setResizable(false);
            tablaDchoPosesion.getColumnModel().getColumn(2).setPreferredWidth(1);
            tablaDchoPosesion.getColumnModel().getColumn(3).setResizable(false);
            tablaDchoPosesion.getColumnModel().getColumn(3).setPreferredWidth(1);
            tablaDchoPosesion.getColumnModel().getColumn(4).setResizable(false);
            tablaDchoPosesion.getColumnModel().getColumn(4).setPreferredWidth(1);
            tablaDchoPosesion.getColumnModel().getColumn(5).setResizable(false);
            tablaDchoPosesion.getColumnModel().getColumn(5).setPreferredWidth(1);
            tablaDchoPosesion.getColumnModel().getColumn(6).setPreferredWidth(1);
            tablaDchoPosesion.getColumnModel().getColumn(7).setResizable(false);
            tablaDchoPosesion.getColumnModel().getColumn(7).setPreferredWidth(1);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 665, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        guardar.setText("Guardar en ..");
        guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarActionPerformed(evt);
            }
        });

        path.setEditable(false);

        tablaActualizacion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "%", "Cuota anterior", "Cuota actualizada"
            }
        ));
        jScrollPane3.setViewportView(tablaActualizacion);
        if (tablaActualizacion.getColumnModel().getColumnCount() > 0) {
            tablaActualizacion.getColumnModel().getColumn(1).setPreferredWidth(1);
        }

        jPanel1.setBackground(new java.awt.Color(36, 47, 65));

        volverBtn.setText("Volver");

        resumenCliente.setText("Resumen cliente");
        resumenCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resumenClienteActionPerformed(evt);
            }
        });

        modificarPagoBtn.setText("Modificar cuota");

        actualizarPagoBtn.setText("Actualizar cuota");
        actualizarPagoBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actualizarPagoBtnActionPerformed(evt);
            }
        });

        agregarPagoBtn.setText("Agregar cuota");

        eliminarPagoBtn.setText("Eliminar cuota");

        generarReciboBtn.setText("Generar recibo");
        generarReciboBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generarReciboBtnActionPerformed(evt);
            }
        });

        actualizarSaldoBtn.setText("Actualizar saldo");
        actualizarSaldoBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actualizarSaldoBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(agregarPagoBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(actualizarSaldoBtn))
                    .addComponent(generarReciboBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(eliminarPagoBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(volverBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resumenCliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(modificarPagoBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(actualizarPagoBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(actualizarPagoBtn)
                    .addComponent(agregarPagoBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(eliminarPagoBtn)
                    .addComponent(modificarPagoBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resumenCliente)
                    .addComponent(generarReciboBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(volverBtn)
                    .addComponent(actualizarSaldoBtn))
                .addContainerGap())
        );

        nya.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        texto_indice_corrector.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        texto_indice_corrector.setForeground(new java.awt.Color(255, 51, 51));
        texto_indice_corrector.setText("NO APLICAR INDICE CORRECTOR");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(guardar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(path, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(nya, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(texto_indice_corrector, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(path, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(guardar)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(texto_indice_corrector, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nya, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                .addGap(6, 6, 6))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void generarReciboBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generarReciboBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_generarReciboBtnActionPerformed

    private void resumenClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resumenClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_resumenClienteActionPerformed

    private void guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_guardarActionPerformed

    private void actualizarPagoBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actualizarPagoBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_actualizarPagoBtnActionPerformed

    private void actualizarSaldoBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actualizarSaldoBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_actualizarSaldoBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton actualizarPagoBtn;
    public javax.swing.JButton actualizarSaldoBtn;
    public javax.swing.JButton agregarPagoBtn;
    public javax.swing.JButton eliminarPagoBtn;
    public javax.swing.JButton generarReciboBtn;
    public javax.swing.JButton guardar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JButton modificarPagoBtn;
    public javax.swing.JLabel nya;
    public javax.swing.JTextField path;
    public javax.swing.JButton resumenCliente;
    public javax.swing.JTable tablaActualizacion;
    public javax.swing.JTable tablaDchoPosesion;
    public javax.swing.JTable tablaDetallePago;
    public javax.swing.JLabel texto_indice_corrector;
    public javax.swing.JButton volverBtn;
    // End of variables declaration//GEN-END:variables
}
