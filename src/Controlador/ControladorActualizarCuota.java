/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Clases.FichaDeControl;
import Modelo.FichaControlDAO;
import Vista.Dialogs.ActualizarCuota;
import Vista.Frame.Ventana;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Marceloi7
 */
public class ControladorActualizarCuota implements ActionListener{
    
    ActualizarCuota ac;
    FichaControlDAO fc = new FichaControlDAO();
    Ventana ventana;
    int id_control;

    public ControladorActualizarCuota(Ventana ventana, int id_control) {
        this.ventana=ventana;
        this.id_control=id_control;
        ac = new ActualizarCuota(ventana, true);
        ac.setLocationRelativeTo(null);
        ac.cancelar.addActionListener(this);
        ac.actualizar.addActionListener(this);
        ac.porcentaje.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {   
             nuevoGasto();}
            @Override
            public void removeUpdate(DocumentEvent e) {
             nuevoGasto();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
            }
         });
        rellenarCampos();
    }

    private void rellenarCampos() {
        List<FichaDeControl> listaFichaControl;
        listaFichaControl = fc.obtenerFichaControl(id_control);
        BigDecimal cuota = (listaFichaControl.get(0).getCuotaPura()).add(listaFichaControl.get(0).getGastos());
        ac.valor_actual.setText(cuota.toString());
        ac.setVisible(true);
    }   
    
    public void nuevoGasto(){
        if(!ac.valor_actual.getText().equals("")){
            if(!ac.porcentaje.getText().equals("")){
                BigDecimal aumento;
                BigDecimal cuota_total = new BigDecimal(ac.valor_actual.getText());   
                aumento = ((cuota_total.multiply(new BigDecimal(ac.porcentaje.getText()))).divide(new BigDecimal("100"),2, BigDecimal.ROUND_HALF_UP)).add(cuota_total);
//                gasto = (cuota_total.multiply(new BigDecimal("10"))).divide(new BigDecimal("100"),2, BigDecimal.ROUND_HALF_UP);
                ac.valor_actualizado.setText(aumento.toString());
            }else{
                ac.valor_actualizado.setText("");
            }
            }else{
                ac.valor_actualizado.setText("");
             }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
         if(e.getSource() == ac.actualizar){
             
           }
            if(e.getSource() == ac.cancelar){
               ac.dispose();
           }
    }
    
}
