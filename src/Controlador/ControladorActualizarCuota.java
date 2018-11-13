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
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Marceloi7
 */
public class ControladorActualizarCuota {
    
    ActualizarCuota ac;
    FichaControlDAO fc = new FichaControlDAO();
    Frame frame;
    int id_control;

    public ControladorActualizarCuota(Frame frame, int id_control) {
        this.frame=frame;
        this.id_control=id_control;
        ac = new ActualizarCuota(frame, true);
        ac.setLocationRelativeTo(null);
        ac.setVisible(true);
        rellenarCampos();
    }

    private void rellenarCampos() {
        List<FichaDeControl> listaFichaControl;
        listaFichaControl = fc.obtenerFichaControl(id_control);
        BigDecimal cuota = (listaFichaControl.get(0).getCuotaPura()).add(listaFichaControl.get(0).getGastos());
        System.out.println(cuota.toString());
        ac.valor_actual.setText("kn");
    }   
}
