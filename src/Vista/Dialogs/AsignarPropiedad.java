/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Dialogs;

/**
 *
 * @author Marcelo
 */
public class AsignarPropiedad extends javax.swing.JDialog {

    /**
     * Creates new form AsignarPropiedad
     */
    public AsignarPropiedad(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tipo_actualizacion = new javax.swing.ButtonGroup();
        indice_corrector = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        dimension = new javax.swing.JTextField();
        cuota_total = new javax.swing.JTextField();
        aceptarBtn = new javax.swing.JButton();
        cancelarBtn = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        tipo_propiedad = new javax.swing.JComboBox<>();
        cantidad_cuotas = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        bolsa_cemento = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        barrio = new javax.swing.JComboBox<>();
        manzana = new javax.swing.JComboBox<>();
        parcela = new javax.swing.JComboBox<>();
        apellido_propietario = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        nombre_propietario = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        fch_suscripción = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        bolsaCemento = new javax.swing.JRadioButton();
        empPublico = new javax.swing.JRadioButton();
        jLabel15 = new javax.swing.JLabel();
        mensaje_error = new javax.swing.JLabel();
        cuota_fija = new javax.swing.JRadioButton();
        cuota_fija_vble = new javax.swing.JRadioButton();
        indice_si = new javax.swing.JRadioButton();
        indice_no = new javax.swing.JRadioButton();
        jLabel16 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Asignar propiedad");
        setMaximumSize(new java.awt.Dimension(600, 800));
        setPreferredSize(new java.awt.Dimension(450, 500));
        getContentPane().setLayout(null);

        jLabel1.setText("Barrio/Torre");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(74, 122, 58, 14);

        jLabel2.setText("Manzana/Piso");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(66, 148, 66, 14);

        jLabel3.setText("Parcela/Dpto");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(70, 174, 62, 14);

        jLabel4.setText("Cantidad cuotas");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(54, 226, 78, 14);

        jLabel5.setText("Bolsa de cemento");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(48, 356, 84, 14);
        getContentPane().add(dimension);
        dimension.setBounds(142, 197, 230, 20);

        cuota_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cuota_totalActionPerformed(evt);
            }
        });
        getContentPane().add(cuota_total);
        cuota_total.setBounds(142, 249, 230, 20);

        aceptarBtn.setText("Aceptar");
        getContentPane().add(aceptarBtn);
        aceptarBtn.setBounds(170, 420, 71, 23);

        cancelarBtn.setText("Cancelar");
        getContentPane().add(cancelarBtn);
        cancelarBtn.setBounds(250, 420, 75, 23);

        jLabel8.setText("Dimensión");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(84, 200, 48, 14);

        jLabel9.setText("Tipo propiedad");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(59, 44, 71, 14);

        tipo_propiedad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Terreno", "Departamento" }));
        getContentPane().add(tipo_propiedad);
        tipo_propiedad.setBounds(142, 41, 230, 20);
        getContentPane().add(cantidad_cuotas);
        cantidad_cuotas.setBounds(142, 223, 230, 20);

        jLabel10.setText("Cuota total $");
        getContentPane().add(jLabel10);
        jLabel10.setBounds(69, 252, 63, 14);
        getContentPane().add(bolsa_cemento);
        bolsa_cemento.setBounds(142, 353, 115, 20);

        jLabel13.setText("$");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(134, 356, 6, 14);

        getContentPane().add(barrio);
        barrio.setBounds(142, 119, 230, 20);

        getContentPane().add(manzana);
        manzana.setBounds(142, 145, 230, 20);

        getContentPane().add(parcela);
        parcela.setBounds(142, 171, 230, 20);

        getContentPane().add(apellido_propietario);
        apellido_propietario.setBounds(142, 67, 230, 20);

        jLabel11.setText("Apellido Propietario");
        getContentPane().add(jLabel11);
        jLabel11.setBounds(38, 70, 92, 14);

        getContentPane().add(nombre_propietario);
        nombre_propietario.setBounds(142, 93, 230, 20);

        jLabel12.setText("Nombre Propietario");
        getContentPane().add(jLabel12);
        jLabel12.setBounds(40, 96, 92, 14);
        getContentPane().add(fch_suscripción);
        fch_suscripción.setBounds(142, 383, 139, 20);

        jLabel14.setText("Fecha suscripción");
        getContentPane().add(jLabel14);
        jLabel14.setBounds(48, 383, 84, 14);

        bolsaCemento.setText("Bolsa cemento");
        getContentPane().add(bolsaCemento);
        bolsaCemento.setBounds(240, 280, 95, 23);

        empPublico.setText("Emp. público");
        getContentPane().add(empPublico);
        empPublico.setBounds(140, 280, 85, 20);

        jLabel15.setText("Tipo actualización");
        getContentPane().add(jLabel15);
        jLabel15.setBounds(50, 280, 84, 20);

        mensaje_error.setForeground(new java.awt.Color(255, 0, 0));
        mensaje_error.setPreferredSize(new java.awt.Dimension(0, 17));
        getContentPane().add(mensaje_error);
        mensaje_error.setBounds(0, 427, 231, 17);

        cuota_fija.setText("C. fija");
        getContentPane().add(cuota_fija);
        cuota_fija.setBounds(140, 300, 55, 23);

        cuota_fija_vble.setText("C. fija (Variable)");
        cuota_fija_vble.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cuota_fija_vbleActionPerformed(evt);
            }
        });
        getContentPane().add(cuota_fija_vble);
        cuota_fija_vble.setBounds(240, 300, 103, 23);

        indice_si.setSelected(true);
        indice_si.setText("Si");
        indice_si.setEnabled(false);
        indice_si.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                indice_siActionPerformed(evt);
            }
        });
        getContentPane().add(indice_si);
        indice_si.setBounds(140, 320, 33, 23);

        indice_no.setText("No");
        indice_no.setEnabled(false);
        getContentPane().add(indice_no);
        indice_no.setBounds(180, 320, 39, 23);

        jLabel16.setText("Aplicar indice corrector");
        getContentPane().add(jLabel16);
        jLabel16.setBounds(23, 321, 109, 20);
        getContentPane().add(jSeparator1);
        jSeparator1.setBounds(229, 323, 137, 2);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cuota_fija_vbleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cuota_fija_vbleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cuota_fija_vbleActionPerformed

    private void indice_siActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_indice_siActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_indice_siActionPerformed

    private void cuota_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cuota_totalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cuota_totalActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AsignarPropiedad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AsignarPropiedad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AsignarPropiedad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AsignarPropiedad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AsignarPropiedad dialog = new AsignarPropiedad(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton aceptarBtn;
    public javax.swing.JComboBox<String> apellido_propietario;
    public javax.swing.JComboBox<String> barrio;
    public javax.swing.JRadioButton bolsaCemento;
    public javax.swing.JTextField bolsa_cemento;
    public javax.swing.JButton cancelarBtn;
    public javax.swing.JTextField cantidad_cuotas;
    public javax.swing.JRadioButton cuota_fija;
    public javax.swing.JRadioButton cuota_fija_vble;
    public javax.swing.JTextField cuota_total;
    public javax.swing.JTextField dimension;
    public javax.swing.JRadioButton empPublico;
    public com.toedter.calendar.JDateChooser fch_suscripción;
    public javax.swing.ButtonGroup indice_corrector;
    public javax.swing.JRadioButton indice_no;
    public javax.swing.JRadioButton indice_si;
    private javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    public javax.swing.JComboBox<String> manzana;
    public javax.swing.JLabel mensaje_error;
    public javax.swing.JComboBox<String> nombre_propietario;
    public javax.swing.JComboBox<String> parcela;
    public javax.swing.ButtonGroup tipo_actualizacion;
    public javax.swing.JComboBox<String> tipo_propiedad;
    // End of variables declaration//GEN-END:variables
}
