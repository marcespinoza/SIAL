/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vista.Frame.Ventana;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Marcelo Espinoza
 */
public class ControladorBotones implements ActionListener{
    
    Ventana ventana;
 
    public ControladorBotones(Ventana ventana) {
           this.ventana=ventana;
           Ventana.btnMinuta.addActionListener(this);
           Ventana.btnClientes.addActionListener(this);
           Ventana.btnResumen.addActionListener(this);
           Ventana.btnCumpleaños.addActionListener(this);
           Ventana.btnCumpleaños.setVisible(false);
           Ventana.cerrarSesion.addActionListener(this);
           this.ventana.btnAyuda.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==Ventana.btnMinuta){
         CardLayout cl = (CardLayout)(Ventana.panelPrincipal.getLayout());
         cl.show(Ventana.panelPrincipal, "Minuta");
        }
        
        if(e.getSource()==Ventana.btnResumen){
         CardLayout cl = (CardLayout)(Ventana.panelPrincipal.getLayout());
         cl.show(Ventana.panelPrincipal, "Resumen");
        }
        
        if(e.getSource()==Ventana.btnClientes){
         CardLayout cl = (CardLayout)(Ventana.panelPrincipal.getLayout());
         cl.show(Ventana.panelPrincipal, "card2");
        }
        
        if(e.getSource()==Ventana.btnCumpleaños){
         new ControladorCumpleaños(ventana);
        }
        
        if(e.getSource()==Ventana.cerrarSesion){
          ventana.dispose();
          new Ventana().setVisible(true);
        }
        
        if(e.getSource()==Ventana.btnAyuda){
            new ControladorAyuda(ventana);
        }
    }    
    
}
