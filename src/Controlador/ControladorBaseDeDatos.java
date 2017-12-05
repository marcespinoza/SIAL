/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vista.Dialogs.BaseDeDatos;
import Vista.Frame.Ventana;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcelo Espinoza
 */
public class ControladorBaseDeDatos implements ActionListener{
    
    BaseDeDatos bd;

    public ControladorBaseDeDatos(Ventana ventana) {
        bd = new BaseDeDatos(ventana, true);
        bd.setLocationRelativeTo(null);
        bd.crearRespaldo.addActionListener(this);
        bd.guardarEn.addActionListener(this);
        bd.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==bd.crearRespaldo){
            try {
                DateFormat fecha1 = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date date = new java.util.Date();
                Process p = null;
                Runtime runtime = Runtime.getRuntime();
                p = runtime.exec("C:/xampp/mysql/bin/mysqldump -uroot --add-drop-database -B miprimercasa -r"+"C:/xampp/backup.sql");
                int completo = p.waitFor();
                if(completo==0){
                    System.out.println("Correcto");
                }else{
                    System.out.println("Incorrecto");
                }
            } catch (IOException ex) {
                Logger.getLogger(ControladorBaseDeDatos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(ControladorBaseDeDatos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
