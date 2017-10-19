/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Frame;
import Controlador.ControladorCliente;
import Controlador.ControladorBotones;
import Controlador.ControladorConfiguracion;
import Controlador.ControladorLogin;
import Controlador.ControladorMinuta;
import Controlador.ControladorResumen;
import Controlador.ControladorUsuario;
import Vista.Panels.Minuta;
import Vista.Panels.Resumen;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import sun.java2d.SunGraphicsEnvironment;


/**
 *
 * @author Marcelo
 */
public class Ventana extends javax.swing.JFrame implements ActionListener{
    
    ControladorLogin cl;
    Minuta vistaMinuta = Minuta.getInstance();
    Resumen vistaResumen = Resumen.getInstance();
    ControladorMinuta cm;
    ControladorResumen cr;

    public Ventana() {
        initComponents();
        GraphicsConfiguration config = this.getGraphicsConfiguration();
        Rectangle usableBounds = SunGraphicsEnvironment.getUsableBounds(config.getDevice());
        setMaximizedBounds(usableBounds);
        setExtendedState(MAXIMIZED_BOTH);        
        ImageIcon icon = new ImageIcon("src/Imagenes/logo.png_32x32.png");
        this.setIconImage(icon.getImage());
        cl = new ControladorLogin(this);
        cm = new ControladorMinuta(vistaMinuta);
        cr = new ControladorResumen(vistaResumen);
        panelPrincipal.add(vistaMinuta, "Minuta");
        panelPrincipal.add(vistaResumen, "Resumen");
        configuracion.addActionListener(this);
        about.addActionListener(this);
        inicializarPaneles();
    }
    
    public void inicializarPaneles(){
        inicializarBotones();        
        ControladorCliente cc = new ControladorCliente(this, clientes);
        //-------Controlador para manejar botones superiores - Clientes,Minutas---------//
        ControladorBotones cb = new ControladorBotones(this);
        cc.llenarTabla(0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenuBar3 = new javax.swing.JMenuBar();
        jMenu4 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        panelPrincipal = new javax.swing.JPanel();
        clientes = new Vista.Panels.Clientes();
        detallePago = new Vista.Panels.DetalleCuota();
        resumen = new Vista.Panels.Resumen();
        minuta = new Vista.Panels.Minuta();
        panelBotones1 = new Vista.Panels.PanelBotones();
        btnClientes = new javax.swing.JButton();
        btnResumen = new javax.swing.JButton();
        btnMinuta = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        labelUsuario = new javax.swing.JLabel();
        labelTipoUsuario = new javax.swing.JLabel();
        apellidoUsuario = new javax.swing.JLabel();
        nombreUsuario = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        MenuInicio = new javax.swing.JMenu();
        cerrarSesion = new javax.swing.JMenuItem();
        info = new javax.swing.JMenu();
        configuracion = new javax.swing.JMenuItem();
        about = new javax.swing.JMenuItem();

        jMenu1.setText("File");
        jMenuBar2.add(jMenu1);

        jMenu3.setText("Edit");
        jMenuBar2.add(jMenu3);

        jMenu4.setText("File");
        jMenuBar3.add(jMenu4);

        jMenu5.setText("Edit");
        jMenuBar3.add(jMenu5);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mi Primer Casa");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        panelPrincipal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelPrincipal.setLayout(new java.awt.CardLayout());
        panelPrincipal.add(clientes, "card2");
        panelPrincipal.add(detallePago, "card3");
        panelPrincipal.add(resumen, "card4");

        javax.swing.GroupLayout minutaLayout = new javax.swing.GroupLayout(minuta);
        minuta.setLayout(minutaLayout);
        minutaLayout.setHorizontalGroup(
            minutaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1167, Short.MAX_VALUE)
        );
        minutaLayout.setVerticalGroup(
            minutaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 537, Short.MAX_VALUE)
        );

        panelPrincipal.add(minuta, "card5");

        panelBotones1.setBackground(new java.awt.Color(36, 47, 65));

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

        btnMinuta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconos/minuta.png"))); // NOI18N
        btnMinuta.setText("Minuta");
        btnMinuta.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnMinuta.setMaximumSize(new java.awt.Dimension(92, 34));
        btnMinuta.setMinimumSize(new java.awt.Dimension(92, 34));
        btnMinuta.setName(""); // NOI18N
        btnMinuta.setPreferredSize(new java.awt.Dimension(92, 34));
        btnMinuta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMinutaActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 153));
        jLabel1.setText("Usuario:");

        labelUsuario.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        labelUsuario.setForeground(new java.awt.Color(255, 255, 255));

        labelTipoUsuario.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        labelTipoUsuario.setForeground(new java.awt.Color(255, 255, 255));

        apellidoUsuario.setText("jLabel3");

        nombreUsuario.setText("jLabel2");

        javax.swing.GroupLayout panelBotones1Layout = new javax.swing.GroupLayout(panelBotones1);
        panelBotones1.setLayout(panelBotones1Layout);
        panelBotones1Layout.setHorizontalGroup(
            panelBotones1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotones1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnResumen, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMinuta, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(nombreUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(apellidoUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBotones1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelTipoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        panelBotones1Layout.setVerticalGroup(
            panelBotones1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotones1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBotones1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBotones1Layout.createSequentialGroup()
                        .addGroup(panelBotones1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnClientes, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                            .addComponent(btnResumen, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                            .addComponent(btnMinuta, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE))
                        .addGap(22, 22, 22))
                    .addGroup(panelBotones1Layout.createSequentialGroup()
                        .addGroup(panelBotones1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelBotones1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(apellidoUsuario)
                                .addComponent(nombreUsuario)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelTipoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        MenuInicio.setText("Inicio");

        cerrarSesion.setText("Cerrar sesión");
        cerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cerrarSesionActionPerformed(evt);
            }
        });
        MenuInicio.add(cerrarSesion);

        jMenuBar1.add(MenuInicio);

        info.setText("Opciones");

        configuracion.setText("Configuración");
        info.add(configuracion);

        about.setText("Acerca de ..");
        info.add(about);

        jMenuBar1.add(info);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 1171, Short.MAX_VALUE)
                    .addComponent(panelBotones1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelBotones1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 541, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnResumenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResumenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnResumenActionPerformed

    private void btnMinutaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMinutaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMinutaActionPerformed

    private void cerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerrarSesionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cerrarSesionActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
       //this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }//GEN-LAST:event_formWindowOpened

    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JMenu MenuInicio;
    public javax.swing.JMenuItem about;
    public static javax.swing.JLabel apellidoUsuario;
    public static javax.swing.JButton btnClientes;
    public static javax.swing.JButton btnMinuta;
    public static javax.swing.JButton btnResumen;
    public static javax.swing.JMenuItem cerrarSesion;
    private Vista.Panels.Clientes clientes;
    public javax.swing.JMenuItem configuracion;
    private Vista.Panels.DetalleCuota detallePago;
    public javax.swing.JMenu info;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuBar jMenuBar3;
    public static javax.swing.JLabel labelTipoUsuario;
    public static javax.swing.JLabel labelUsuario;
    private Vista.Panels.Minuta minuta;
    public static javax.swing.JLabel nombreUsuario;
    private Vista.Panels.PanelBotones panelBotones1;
    public static javax.swing.JPanel panelPrincipal;
    private Vista.Panels.Resumen resumen;
    // End of variables declaration//GEN-END:variables

public void inicializarBotones(){
    btnClientes.setVerticalTextPosition(SwingConstants.BOTTOM);
    btnClientes.setHorizontalTextPosition(SwingConstants.CENTER);
    btnResumen.setVerticalTextPosition(SwingConstants.BOTTOM);
    btnResumen.setHorizontalTextPosition(SwingConstants.CENTER);    
    btnMinuta.setVerticalTextPosition(SwingConstants.BOTTOM);
    btnMinuta.setHorizontalTextPosition(SwingConstants.CENTER);
}

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(configuracion)){
             new ControladorConfiguracion(this);
        }
        if(e.getSource().equals(about)){
          ImageIcon icon = new ImageIcon("src/Imagenes/Iconos/about.png");   
             JOptionPane.showMessageDialog(null, "Desarrollado por Marcelo Espinoza \n marceloespinoza00@gmail.com","Acerca de ..", HEIGHT, icon);
        }
    }
     
}

