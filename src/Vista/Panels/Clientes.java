/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Panels;

import Controlador.ControladorAltaCliente;
import Controlador.ControladorCliente;
import Vista.Frame.Ventana;
import conexion.Conexion;
import java.awt.CardLayout;
import javax.swing.table.DefaultTableModel;
import static Vista.Frame.Ventana.panelPrincipal;
import java.awt.Frame;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Marcelo
 */
public class Clientes extends javax.swing.JPanel {

        private Object [] clientes;
        private Object [] lote;
        private Conexion conexion;
    /**
     * Creates new form Clientes
     */
    public Clientes() {
        initComponents();
        conexion = new Conexion();
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
        tablaCliente = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        barrio = new javax.swing.JTextField();
        calle = new javax.swing.JTextField();
        numero = new javax.swing.JTextField();
        trabajo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        buscarTodos = new javax.swing.JTextField();
        buscarParcela = new javax.swing.JTextField();
        buscarManzana = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        eliminarBtn = new javax.swing.JButton();
        agregarBtn = new javax.swing.JButton();
        editarBtn = new javax.swing.JButton();

        tablaCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Apellido", "Nombres", "Documento", "Telefono", "Barrio", "Manzana", "Numero", "Trabajo", "Barrio", "Manzana", "Parcela"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaClienteMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaCliente);
        if (tablaCliente.getColumnModel().getColumnCount() > 0) {
            tablaCliente.getColumnModel().getColumn(0).setResizable(false);
            tablaCliente.getColumnModel().getColumn(1).setResizable(false);
            tablaCliente.getColumnModel().getColumn(2).setMinWidth(75);
            tablaCliente.getColumnModel().getColumn(2).setMaxWidth(75);
            tablaCliente.getColumnModel().getColumn(3).setMinWidth(90);
            tablaCliente.getColumnModel().getColumn(3).setMaxWidth(90);
            tablaCliente.getColumnModel().getColumn(4).setResizable(false);
            tablaCliente.getColumnModel().getColumn(5).setResizable(false);
            tablaCliente.getColumnModel().getColumn(6).setResizable(false);
            tablaCliente.getColumnModel().getColumn(7).setResizable(false);
            tablaCliente.getColumnModel().getColumn(8).setMinWidth(140);
            tablaCliente.getColumnModel().getColumn(8).setMaxWidth(140);
            tablaCliente.getColumnModel().getColumn(9).setMinWidth(60);
            tablaCliente.getColumnModel().getColumn(9).setMaxWidth(60);
            tablaCliente.getColumnModel().getColumn(10).setMinWidth(60);
            tablaCliente.getColumnModel().getColumn(10).setMaxWidth(60);
        }

        barrio.setEditable(false);
        barrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                barrioActionPerformed(evt);
            }
        });

        calle.setEditable(false);
        calle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calleActionPerformed(evt);
            }
        });

        numero.setEditable(false);
        numero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numeroActionPerformed(evt);
            }
        });

        trabajo.setEditable(false);
        trabajo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trabajoActionPerformed(evt);
            }
        });

        jLabel1.setText("Calle");

        jLabel3.setText("Numero");

        jLabel2.setText("Barrio");

        jLabel4.setText("Lugar de trabajo");

        jButton1.setText("Detalle de pago");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                        .addComponent(barrio, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(trabajo, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                                .addComponent(calle)
                                .addComponent(numero)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(barrio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(calle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(trabajo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(24, 24, 24)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Buscar", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP));

        buscarTodos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                buscarTodosKeyReleased(evt);
            }
        });

        buscarParcela.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                buscarParcelaKeyReleased(evt);
            }
        });

        buscarManzana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                buscarManzanaKeyReleased(evt);
            }
        });

        jLabel6.setText("Parcela");

        jLabel7.setText("Manzana");

        jLabel8.setText("Todos");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buscarTodos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buscarManzana, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buscarParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buscarTodos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarParcela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarManzana, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jLabel5.setText("Mostrar");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "2.200", "2.750", "3.080" }));

        eliminarBtn.setText("Eliminar");
        eliminarBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eliminarBtnMouseClicked(evt);
            }
        });

        agregarBtn.setText("Agregar");
        agregarBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                agregarBtnMouseClicked(evt);
            }
        });

        editarBtn.setText("Editar");
        editarBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editarBtnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(agregarBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(editarBtn))
                            .addComponent(eliminarBtn))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(agregarBtn)
                            .addComponent(editarBtn))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(eliminarBtn))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tablaClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaClienteMouseClicked
        int row = tablaCliente.getSelectedRow();       
        barrio.setText(tablaCliente.getModel().getValueAt(row, 4).toString());
        calle.setText(tablaCliente.getModel().getValueAt(row, 5).toString());
         numero.setText(tablaCliente.getModel().getValueAt(row, 6).toString());
        trabajo.setText(tablaCliente.getModel().getValueAt(row, 7).toString());
    }//GEN-LAST:event_tablaClienteMouseClicked

    private void barrioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_barrioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_barrioActionPerformed

    private void calleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_calleActionPerformed

    private void numeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numeroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numeroActionPerformed

    private void trabajoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_trabajoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_trabajoActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        DetallePago detallePago = null; 
        panelPrincipal.removeAll();
        panelPrincipal.add(detallePago);
        panelPrincipal.revalidate();
        panelPrincipal.repaint();               
    }//GEN-LAST:event_jButton1MouseClicked

    private void buscarTodosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buscarTodosKeyReleased
        String query=buscarTodos.getText().toLowerCase();
        filter(query);
    }//GEN-LAST:event_buscarTodosKeyReleased

    private void filter(String query){
         DefaultTableModel table = (DefaultTableModel) tablaCliente.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<> (table);
         tablaCliente.setRowSorter(tr);
         tr.setRowFilter(RowFilter.regexFilter("(?i)" + query));
    }
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void agregarBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_agregarBtnMouseClicked
      // AltaCliente ac = new AltaCliente((JFrame)this.getRootPane().getParent(), true);
       //ac.setVisible(true);
    }//GEN-LAST:event_agregarBtnMouseClicked

    private void eliminarBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eliminarBtnMouseClicked
         int row = tablaCliente.getSelectedRowCount();
        if(row==0){
            JOptionPane.showMessageDialog(null, "Seleccione un cliente de la lista", "Atención",JOptionPane.WARNING_MESSAGE); 
        }
    }//GEN-LAST:event_eliminarBtnMouseClicked

    private void editarBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editarBtnMouseClicked
        int row = tablaCliente.getSelectedRowCount();
        if(row==0){
            JOptionPane.showMessageDialog(null, "Seleccione un cliente de la lista", "Atención",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_editarBtnMouseClicked

    private void buscarParcelaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buscarParcelaKeyReleased
         String query=buscarParcela.getText().toLowerCase();
        filtroParcela(query);
    }//GEN-LAST:event_buscarParcelaKeyReleased
  
    private void buscarManzanaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buscarManzanaKeyReleased
         String query=buscarManzana.getText().toLowerCase();
        filtroManzana(query);
    }//GEN-LAST:event_buscarManzanaKeyReleased
    private void filtroParcela(String query){
         DefaultTableModel table = (DefaultTableModel) tablaCliente.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<> (table);
         tablaCliente.setRowSorter(tr);
         tr.setRowFilter(RowFilter.regexFilter("(?i)" + query,10));
    }
    private void filtroManzana(String query){
         DefaultTableModel table = (DefaultTableModel) tablaCliente.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<> (table);
         tablaCliente.setRowSorter(tr);
         tr.setRowFilter(RowFilter.regexFilter("(?i)" + query,9));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton agregarBtn;
    private javax.swing.JTextField barrio;
    public javax.swing.JTextField buscarManzana;
    public javax.swing.JTextField buscarParcela;
    public javax.swing.JTextField buscarTodos;
    private javax.swing.JTextField calle;
    public javax.swing.JButton editarBtn;
    public javax.swing.JButton eliminarBtn;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField numero;
    public javax.swing.JTable tablaCliente;
    private javax.swing.JTextField trabajo;
    // End of variables declaration//GEN-END:variables
}