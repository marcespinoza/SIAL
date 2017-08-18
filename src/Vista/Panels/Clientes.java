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
      //  ControladorCliente cac = new ControladorCliente(this);
    /**
     * Creates new form Clientes
     */
    public Clientes() {
        initComponents();
        conexion = new Conexion();
        llenarTablaCliente();
        ocultarColumnas();
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
        cliente = new javax.swing.JTable();
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
        buscar = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        lotes = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        modificarBtn = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        eliminarBtn = new javax.swing.JButton();
        agregarBtn = new javax.swing.JButton();

        cliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Apellido", "Nombres", "Documento", "Telefono", "Barrio", "Calle", "Numero", "Trabajo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        cliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clienteMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(cliente);

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

        buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarActionPerformed(evt);
            }
        });
        buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                buscarKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buscar)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        lotes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "idControl", "Barrio", "Manzana", "Parcela"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(lotes);

        modificarBtn.setText("Modificar");
        modificarBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                modificarBtnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(modificarBtn)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(modificarBtn)
                .addContainerGap(40, Short.MAX_VALUE))
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(agregarBtn)
                        .addGap(18, 18, 18)
                        .addComponent(eliminarBtn))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(77, 77, 77))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(eliminarBtn)
                            .addComponent(agregarBtn))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void clienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clienteMouseClicked
        int row = cliente.getSelectedRow();       
        barrio.setText(cliente.getModel().getValueAt(row, 4).toString());
        calle.setText(cliente.getModel().getValueAt(row, 5).toString());
         numero.setText(cliente.getModel().getValueAt(row, 6).toString());
        trabajo.setText(cliente.getModel().getValueAt(row, 7).toString());
        llenarTablaLotes();
    }//GEN-LAST:event_clienteMouseClicked

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
        int rowClientes = cliente.getSelectedRow();
        if(rowClientes == -1){
         JOptionPane.showMessageDialog(null, "Seleccione un cliente de la lista", "Atención",JOptionPane.WARNING_MESSAGE);
        }else{
        if(lotes.getRowCount()==1){
        detallePago = new DetallePago(lotes.getModel().getValueAt(0, 0).toString(),cliente.getModel().getValueAt(rowClientes, 0).toString(), cliente.getModel().getValueAt(rowClientes, 1).toString());
        }else{    
        int row = lotes.getSelectedRow();
        if(row == -1)
        {
         JOptionPane.showMessageDialog(null, "Seleccione un lote", "Atención",JOptionPane.WARNING_MESSAGE);
        } else {
        detallePago = new DetallePago(lotes.getModel().getValueAt(row, 0).toString(),cliente.getModel().getValueAt(rowClientes, 0).toString(),cliente.getModel().getValueAt(rowClientes, 1).toString());}}
        panelPrincipal.removeAll();
        panelPrincipal.add(detallePago);
        panelPrincipal.revalidate();
        panelPrincipal.repaint();}                
    }//GEN-LAST:event_jButton1MouseClicked

    private void buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buscarKeyReleased
        String query=buscar.getText().toLowerCase();
        filter(query);
    }//GEN-LAST:event_buscarKeyReleased

    private void filter(String query){
         DefaultTableModel table = (DefaultTableModel) cliente.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<> (table);
         cliente.setRowSorter(tr);
         tr.setRowFilter(RowFilter.regexFilter("(?i)" + query));
    }
    
    private void buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarActionPerformed
       
    }//GEN-LAST:event_buscarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void agregarBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_agregarBtnMouseClicked
      // AltaCliente ac = new AltaCliente((JFrame)this.getRootPane().getParent(), true);
       //ac.setVisible(true);
    }//GEN-LAST:event_agregarBtnMouseClicked

    private void eliminarBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eliminarBtnMouseClicked
         int row = cliente.getSelectedRowCount();
        if(row==0){
            JOptionPane.showMessageDialog(null, "Seleccione un cliente de la lista", "Atención",JOptionPane.WARNING_MESSAGE); 
        }
    }//GEN-LAST:event_eliminarBtnMouseClicked

    private void modificarBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_modificarBtnMouseClicked
         int row = cliente.getSelectedRowCount();
        if(row==0){
            JOptionPane.showMessageDialog(null, "Seleccione un cliente de la lista", "Atención",JOptionPane.WARNING_MESSAGE); 
        }
    }//GEN-LAST:event_modificarBtnMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton agregarBtn;
    private javax.swing.JTextField barrio;
    public javax.swing.JTextField buscar;
    private javax.swing.JTextField calle;
    public javax.swing.JTable cliente;
    public javax.swing.JButton eliminarBtn;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable lotes;
    public javax.swing.JButton modificarBtn;
    private javax.swing.JTextField numero;
    private javax.swing.JTextField trabajo;
    // End of variables declaration//GEN-END:variables

     private void llenarTablaCliente() {
      
        try {
            conexion.rs = conexion.st.executeQuery("select * from cliente");
            while(conexion.rs.next()){
                String dni = conexion.rs.getString(1);
                String nombres = conexion.rs.getString(2);
                String apellido = conexion.rs.getString(3);
                String barrio = conexion.rs.getString(4);                
                String calle = conexion.rs.getString(5);
                String numero = conexion.rs.getString(6);                
                String celular = conexion.rs.getString(7);
                String trabajo = conexion.rs.getString(9);
                clientes = new Object[] {apellido, nombres, dni, celular, barrio, calle, numero, trabajo};
                DefaultTableModel model = (DefaultTableModel) cliente.getModel();               
                model.addRow(clientes);                
            }
            //Oculto las columnas que no quiero mostrar
                cliente.getColumnModel().getColumn(4).setMinWidth(0);
                cliente.getColumnModel().getColumn(4).setMaxWidth(0);
                cliente.getColumnModel().getColumn(4).setWidth(0);
                cliente.getColumnModel().getColumn(5).setMinWidth(0);
                cliente.getColumnModel().getColumn(5).setMaxWidth(0);
                cliente.getColumnModel().getColumn(5).setWidth(0);
                cliente.getColumnModel().getColumn(6).setMinWidth(0);
                cliente.getColumnModel().getColumn(6).setMaxWidth(0);
                cliente.getColumnModel().getColumn(6).setWidth(0);
                cliente.getColumnModel().getColumn(7).setMinWidth(0);
                cliente.getColumnModel().getColumn(7).setMaxWidth(0);
                cliente.getColumnModel().getColumn(7).setWidth(0);
        } catch (Exception e) {
        }
    }

    private void llenarTablaLotes() {    
        //Limpio los datos de la tabla
        DefaultTableModel limpiar = (DefaultTableModel)lotes.getModel();
        while (limpiar.getRowCount() > 0){
        for (int i = 0; i < limpiar.getRowCount(); ++i){
            limpiar.removeRow(i);
        }
    }
        //Cargo la segunda tabla con los lotes que pertencen a un cliente seleccionado
        try {
            int row = cliente.getSelectedRow();
            conexion.rs = conexion.st.executeQuery("select * from ficha_control where dni = '"+cliente.getModel().getValueAt(row, 2)+"'");
            while(conexion.rs.next()){
                String idControl = conexion.rs.getString(1);
                String barrio = conexion.rs.getString(4);
                String manzana = conexion.rs.getString(5);
                String parcela = conexion.rs.getString(6);
                lote = new Object[] {idControl, barrio, manzana, parcela};
                DefaultTableModel model = (DefaultTableModel) lotes.getModel();
                model.addRow(lote);
            }
            ocultarColumnas();

        } catch (Exception e) {
        }        
    }
    
    private void ocultarColumnas(){
        lotes.getColumnModel().getColumn(0).setMinWidth(0);
        lotes.getColumnModel().getColumn(0).setMaxWidth(0);
        lotes.getColumnModel().getColumn(0).setWidth(0);
    }

}
