/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vista.Dialogs.Configuracion;
import Vista.Frame.Ventana;

/**
 *
 * @author Marcelo Espinoza
 */
public class ControladorConfiguracion {
    
    Configuracion configuracion;

    public ControladorConfiguracion(Ventana parent) {
        configuracion = new Configuracion(parent, true);
        new ControladorPropietarios(configuracion);
        new ControladorUsuario(configuracion);
        new ControladorPropiedades(configuracion);
        configuracion.setVisible(true);
    }
    
}
