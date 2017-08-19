/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Frame;
import Controlador.ControladorCliente;
import Vista.Panels.Clientes;
import conexion.*;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Marcelo
 */
public class Ventana extends javax.swing.JFrame {

    /**
     * Creates new form Principal2
     */
    public Ventana() {
        initComponents();
        inicializarBotones();
        ControladorCliente cc = new ControladorCliente(clientes);
        cc.llenarTabla(clientes.tablaCliente);
        ImageIcon icon = new ImageIcon("src/Imagenes/logo.png_32x32.png");
        this.setIconImage(icon.getImage());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        clientes = new Vista.Panels.Clientes();
        detallePago = new Vista.Panels.DetallePago();
        resumen = new Vista.Panels.Resumen();
        panel_botones = new Vista.Panels.Botones();
        btnLotes = new javax.swing.JButton();
        btnClientes = new javax.swing.JButton();
        btnResumen = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mi Primer Casa");

        panelPrincipal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelPrincipal.setLayout(new java.awt.CardLayout());
        panelPrincipal.add(clientes, "card2");
        panelPrincipal.add(detallePago, "card3");
        panelPrincipal.add(resumen, "card4");

        panel_botones.setBackground(new java.awt.Color(0, 102, 255));
        panel_botones.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnLotes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconos/home.png"))); // NOI18N
        btnLotes.setText("Lotes");
        btnLotes.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconos/man-user.png"))); // NOI18N
        btnClientes.setText("Clientes");
        btnClientes.setMargin(new java.awt.Insets(2, 2, 2, 2));

        btnResumen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconos/resumen.png"))); // NOI18N
        btnResumen.setText("Resumen");
        btnResumen.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnResumen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResumenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_botonesLayout = new javax.swing.GroupLayout(panel_botones);
        panel_botones.setLayout(panel_botonesLayout);
        panel_botonesLayout.setHorizontalGroup(
            panel_botonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_botonesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLotes, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnResumen, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_botonesLayout.setVerticalGroup(
            panel_botonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_botonesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_botonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLotes, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                    .addComponent(btnResumen, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                    .addComponent(btnClientes, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE))
                .addContainerGap())
        );

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Acerca de ..");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 1164, Short.MAX_VALUE)
                    .addComponent(panel_botones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_botones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnResumenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResumenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnResumenActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnLotes;
    private javax.swing.JButton btnResumen;
    private Vista.Panels.Clientes clientes;
    private Vista.Panels.DetallePago detallePago;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    public static javax.swing.JPanel panelPrincipal;
    private Vista.Panels.Botones panel_botones;
    private Vista.Panels.Resumen resumen;
    // End of variables declaration//GEN-END:variables

public void inicializarBotones(){
    btnClientes.setVerticalTextPosition(SwingConstants.BOTTOM);
    btnClientes.setHorizontalTextPosition(SwingConstants.CENTER);
    btnLotes.setVerticalTextPosition(SwingConstants.BOTTOM);
    btnLotes.setHorizontalTextPosition(SwingConstants.CENTER);
    btnResumen.setVerticalTextPosition(SwingConstants.BOTTOM);
    btnResumen.setHorizontalTextPosition(SwingConstants.CENTER);
}
 

    
}

