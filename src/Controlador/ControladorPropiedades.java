/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.DepartamentoDAO;
import Modelo.LoteDAO;
import Modelo.PropiedadesDAO;
import Modelo.PropietarioDAO;
import Vista.Dialogs.Configuracion;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcelo Espinoza
 */
public class ControladorPropiedades implements ActionListener{
    
    Configuracion vista;
    PropietarioDAO pd = new PropietarioDAO();
    PropiedadesDAO prod = new PropiedadesDAO();
    LoteDAO ld = new LoteDAO();
    DepartamentoDAO dd = new DepartamentoDAO();
    private Object [] propiedades;
    String apellidos, nombres, cuit, propiedad;

    public ControladorPropiedades(Configuracion vistaConfiguracion) {
        this.vista=vistaConfiguracion;
        this.vista.propiedades.comboApellido.addActionListener(this);
        this.vista.propiedades.comboNombres.addActionListener(this);
        this.vista.propiedades.agregar.addActionListener(this);
        this.vista.propiedades.eliminar.addActionListener(this);
        this.vista.propiedades.comboPropiedad.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                llenarComboApellidos();
                vista.propiedades.tablaPropiedades.removeAll();
            }
        });
        this.vista.propiedades.pc.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
             if(e.getKeyCode()==KeyEvent.VK_ENTER){
                 if(validarCampos()){
            if(!vista.propiedades.comboApellido.getSelectedItem().equals("Seleccione")){  
               if(!vista.propiedades.comboNombres.getSelectedItem().equals("Seleccione")){  
                switch(vista.propiedades.comboPropiedad.getSelectedItem().toString()){   
                        case "Terreno": ld.agregarLote(vista.propiedades.barrio.getText(), vista.propiedades.mz.getText(), vista.propiedades.pc.getText(), vista.propiedades.comboApellido.getSelectedItem().toString(), vista.propiedades.comboNombres.getSelectedItem().toString(), vista.propiedades.cuit.getText(),vista.propiedades.nroRecibo.getText());
                        case "Departamento":dd.agregarDepartamento(vista.propiedades.barrio.getText(), vista.propiedades.mz.getText(), vista.propiedades.pc.getText(), vista.propiedades.comboApellido.getSelectedItem().toString(), vista.propiedades.comboNombres.getSelectedItem().toString(), vista.propiedades.cuit.getText(),vista.propiedades.nroRecibo.getText());    
                }
             }
            }
            llenarTabla();}
             }
            }
         });
        llenarComboApellidos();
    }
    
 public void llenarComboApellidos(){
        try {
            ResultSet rs = null;
            rs = pd.obtenerApellidos();
            vista.propiedades.comboApellido.removeAllItems();
            vista.propiedades.comboApellido.addItem("Seleccione");
            if(rs!=null){
            while (rs.next()) {
                vista.propiedades.comboApellido.addItem(rs.getString(1));                
            } } } catch (SQLException ex) {
            Logger.getLogger(ControladorPropiedades.class.getName()).log(Level.SEVERE, null, ex);
        }   
 }   
  public void llenarComboNombres(String apellidos){
        try {
            ResultSet rs = pd.obtenerNombres(apellidos);
            vista.propiedades.comboNombres.removeAllItems();
            vista.propiedades.comboNombres.addItem("Seleccione");
            while (rs.next()) {
                vista.propiedades.comboNombres.addItem(rs.getString(1));
            }  } catch (SQLException ex) {
            Logger.getLogger(ControladorPropiedades.class.getName()).log(Level.SEVERE, null, ex);
        }   
 }
 
  public void llenarTabla(){
      ResultSet rs = null;
      try {
        ResultSet cuit = pd.obtenerCuit(apellidos, nombres);
        cuit.next();
        vista.propiedades.cuit.setText(cuit.getString(1));
        vista.propiedades.nroRecibo.setText(cuit.getString(2));
        switch(vista.propiedades.comboPropiedad.getSelectedItem().toString()){
            case"Terreno":rs = ld.obtenerLotes(apellidos, nombres);break;
            case"Departamento":rs = dd.obtenerDepartamentos(apellidos, nombres);break;}
        DefaultTableModel model = (DefaultTableModel) vista.propiedades.tablaPropiedades.getModel();
        model.setRowCount(0);        
            while(rs.next()){
                String barrio = rs.getString(1);  
                String manzana = rs.getString(2);
                String parcela = rs.getString(3);
                if(rs.getString(4).equals("0")){
                  propiedades = new Object[] {barrio, manzana, parcela, "Libre"};}
                else{
                   propiedades = new Object[] {barrio, manzana, parcela, "Vendido"};  
                }
                model.addRow(propiedades);
            }
         rs.previous();
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource()==vista.propiedades.comboApellido){
          if(vista.propiedades.comboApellido.getItemCount()!=0){
            if(!vista.propiedades.comboApellido.getSelectedItem().equals("Seleccione")){  
                apellidos =vista.propiedades.comboApellido.getSelectedItem().toString();
                llenarComboNombres(apellidos);
            }
          } 
        }
         if(e.getSource()==vista.propiedades.comboNombres){
           if(vista.propiedades.comboNombres.getItemCount()!=0){
            if(!vista.propiedades.comboNombres.getSelectedItem().equals("Seleccione")){ 
                nombres = vista.propiedades.comboNombres.getSelectedItem().toString();
                llenarTabla();
            }
            }
        }
        if(e.getSource()==vista.propiedades.agregar){
            if(validarCampos()){
            if(!vista.propiedades.comboApellido.getSelectedItem().equals("Seleccione")){  
               if(!vista.propiedades.comboNombres.getSelectedItem().equals("Seleccione")){  
                switch(vista.propiedades.comboPropiedad.getSelectedItem().toString()){   
                        case "Terreno": ld.agregarLote(vista.propiedades.barrio.getText(), vista.propiedades.mz.getText(), vista.propiedades.pc.getText(), vista.propiedades.comboApellido.getSelectedItem().toString(), vista.propiedades.comboNombres.getSelectedItem().toString(), vista.propiedades.cuit.getText(),vista.propiedades.nroRecibo.getText());
                        case "Departamento":dd.agregarDepartamento(vista.propiedades.barrio.getText(), vista.propiedades.mz.getText(), vista.propiedades.pc.getText(), vista.propiedades.comboApellido.getSelectedItem().toString(), vista.propiedades.comboNombres.getSelectedItem().toString(), vista.propiedades.cuit.getText(),vista.propiedades.nroRecibo.getText());    
                }
             }
            }
            llenarTabla();}
        }
        if(e.getSource()==vista.propiedades.eliminar){
            int row=vista.propiedades.tablaPropiedades.getSelectedRow();
            if(row != -1){
                ImageIcon icon = new ImageIcon("src/Imagenes/Iconos/warning.png");   
             int reply = JOptionPane.showConfirmDialog(null, "Eliminar a "+vista.propiedades.tablaPropiedades.getModel().getValueAt(row, 0)+" "+vista.propiedades.tablaPropiedades.getModel().getValueAt(row, 1)+" "+vista.propiedades.tablaPropiedades.getModel().getValueAt(row, 2)+"?",
                     "Advertencia",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
              if (reply == JOptionPane.YES_OPTION) {
               ld.eliminarLote(vista.propiedades.tablaPropiedades.getModel().getValueAt(row, 0).toString(), Integer.parseInt(vista.propiedades.tablaPropiedades.getModel().getValueAt(row, 1).toString()), Integer.parseInt(vista.propiedades.tablaPropiedades.getModel().getValueAt(row, 2).toString()));
               llenarTabla();}
            }else{
              JOptionPane.showMessageDialog(null, "Seleccione una propiedad", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
            }
        }
    }
    
    public boolean validarCampos(){
        boolean bandera = true;       
        if(vista.propiedades.barrio.getText().isEmpty()){
         vista.propiedades.barrio.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         vista.propiedades.barrio.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));        
        }
        if(vista.propiedades.mz.getText().isEmpty()){
         vista.propiedades.mz.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         vista.propiedades.mz.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));        
        }
        if(vista.propiedades.pc.getText().isEmpty()){
         vista.propiedades.pc.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
         bandera=false;
        }else{
         vista.propiedades.pc.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));        
        }
         return bandera;
    }
    
}
