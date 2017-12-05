/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ClienteDAO;
import Modelo.FichaControlDAO;
import Modelo.LoteDAO;
import Modelo.ReferenciaDAO;
import Modelo.RendererTablaCliente;
import Vista.Dialogs.Cumpleaños;
import Vista.Frame.Ventana;
import Vista.Panels.Clientes;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.joda.time.LocalDate;
import org.joda.time.Years;

/**
 *
 * @author Marcelo
 */
public class ControladorCliente implements ActionListener, MouseListener{
    
    RendererTablaCliente r = new RendererTablaCliente();
    Clientes vistaClientes;
    Cumpleaños dialogCumpleaños;
    ClienteDAO cd = new ClienteDAO();
    ReferenciaDAO rd = new ReferenciaDAO();
    FichaControlDAO fd = new FichaControlDAO();
    LoteDAO ld = new LoteDAO();
    private ArrayList<String[]> cumpleaños = new ArrayList<String[]>();
    Ventana ventana;
    private Object [] clientes;
    private String [] detalleCumpleaños;
    private List<Object> cliente = new ArrayList<Object>();
    private List<Object> referencia = new ArrayList<Object>();
    public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

    
    public ControladorCliente(Ventana ventana, Clientes vistaClientes){
        this.vistaClientes=vistaClientes;
        this.ventana=ventana;
        this.vistaClientes.agregarBtn.addActionListener(this);
        this.vistaClientes.eliminarBtn.addActionListener(this);
        this.vistaClientes.editarBtn.addActionListener(this);
        this.vistaClientes.detalleBtn.addActionListener(this);
        this.vistaClientes.asignarBtn.addActionListener(this);
        this.vistaClientes.bajaBtn.addActionListener(this);
        this.vistaClientes.tablaCliente.addMouseListener(this);
        this.vistaClientes.agregarPropietario.addActionListener(this);
        this.vistaClientes.cambiarPropietario.addActionListener(this);
        this.vistaClientes.comboCuotas.addActionListener(this);
        this.vistaClientes.bolsa_cemento.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
               if(e.getKeyCode()==KeyEvent.VK_ENTER){                   
                    Date date = new Date();
                if(!vistaClientes.bolsa_cemento.getText().equals("")){                    
                    int i = vistaClientes.tablaCliente.getSelectedRow();
                    fd.actualizarBolsaCemento( new BigDecimal(vistaClientes.bolsa_cemento.getText()), new java.sql.Date(date.getTime()), vistaClientes.tablaCliente.getModel().getValueAt(i, 10).toString());
                     if(vistaClientes.comboCuotas.getSelectedItem().equals("Todos")){
                       llenarTabla(0);}
                     else{
                        llenarTabla(Double.parseDouble(vistaClientes.comboCuotas.getSelectedItem().toString()));
                     }
                     ventana.requestFocusInWindow();
                }
                else{
                   JOptionPane.showMessageDialog(null, "Ingrese un valor", "Atención", JOptionPane.INFORMATION_MESSAGE, null); 
                }
               }
            }
        });
        vistaClientes.datosCliente.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.BLACK), "Datos cliente"));
        vistaClientes.datosReferencia.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.BLACK), "Datos referencia"));
        this.vistaClientes.tablaCliente.setDefaultRenderer(Object.class, r);
        llenarComboCuotas();
    }

    public ControladorCliente() {
    }    
    
    @Override
    public void actionPerformed(ActionEvent e) {
          if(e.getSource() == vistaClientes.cambiarPropietario){
              int row = vistaClientes.tablaCliente.getSelectedRow();
              //--------Verifico que haya seleccionado alguna fila----------//
              if(row != -1){
                if(vistaClientes.tablaCliente.getValueAt(row, 10) != null){  
                new ControladorAltaCliente((Ventana) SwingUtilities.getWindowAncestor(vistaClientes), Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(row, 10).toString()),  Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(row, 2).toString()), true);
                if(vistaClientes.comboCuotas.getSelectedItem().equals("Todos")){
                llenarTabla(0);}
            else{
                llenarTabla(Double.parseDouble(vistaClientes.comboCuotas.getSelectedItem().toString()));
            }
                 }          
            }else{
                JOptionPane.showMessageDialog(null, "Seleccione un propietario de la lista", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
             }}
        if(e.getSource() == vistaClientes.agregarPropietario){
                int row = vistaClientes.tablaCliente.getSelectedRow();
           if(row != -1){
               if(vistaClientes.tablaCliente.getValueAt(row, 10) != null){
                  new ControladorAltaCliente((Ventana) SwingUtilities.getWindowAncestor(vistaClientes),  Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(row, 10).toString()), 0, false);
                  if(vistaClientes.comboCuotas.getSelectedItem().equals("Todos")){
                    llenarTabla(0);}
                    else{
                   llenarTabla(Double.parseDouble(vistaClientes.comboCuotas.getSelectedItem().toString()));
                     }
               }else{
                  JOptionPane.showMessageDialog(null, "Debe asignar una propiedad", "Atención", JOptionPane.INFORMATION_MESSAGE, null); 
               }}else{
               JOptionPane.showMessageDialog(null, "Seleccione un propietario de la lista", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
           } 
        }
        if(e.getSource() == vistaClientes.agregarBtn){  
            new ControladorAltaCliente((Ventana) SwingUtilities.getWindowAncestor(vistaClientes), 0,0, false);
            if(vistaClientes.comboCuotas.getSelectedItem().equals("Todos")){
                 llenarTabla(0);}
            else{
                llenarTabla(Double.parseDouble(vistaClientes.comboCuotas.getSelectedItem().toString()));
            }
         }
        if(e.getSource() == vistaClientes.eliminarBtn){
             int row = vistaClientes.tablaCliente.getSelectedRow(); 
             if(row != -1){
             ImageIcon icon = new ImageIcon("src/Imagenes/Iconos/warning.png");   
             int reply = JOptionPane.showConfirmDialog(null, "Eliminar a "+vistaClientes.tablaCliente.getModel().getValueAt(row, 0)+" "+""+" "+vistaClientes.tablaCliente.getModel().getValueAt(row, 1)+"?",
                     "Advertencia",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
              if (reply == JOptionPane.YES_OPTION) {
              int dni = Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(row, 2).toString());
              cd.eliminarCliente(dni);
              if(vistaClientes.comboCuotas.getSelectedItem().equals("Todos")){
                 llenarTabla(0);
              }
              else{
                llenarTabla(Double.parseDouble(vistaClientes.comboCuotas.getSelectedItem().toString()));
              }
               } 
                }
              else{
                 JOptionPane.showMessageDialog(null, "Seleccione un cliente de la lista", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
               }
        }
        if(e.getSource() == vistaClientes.editarBtn){
            int row = vistaClientes.tablaCliente.getSelectedRow();
            if(row!=-1){
            cliente.clear();
            referencia.clear();
            for (int i = 0; i < 14; i++) {
                cliente.add(vistaClientes.tablaCliente.getModel().getValueAt(row, i));
            }
            referencia.add(vistaClientes.apellido_referencia.getText());
            referencia.add(vistaClientes.nombre_referencia.getText());
            referencia.add(vistaClientes.telefono_referencia.getText());
            referencia.add(vistaClientes.parentesco.getText());
            new ControladorEditarCliente((Ventana) SwingUtilities.getWindowAncestor(vistaClientes), cliente, referencia);
            if(vistaClientes.comboCuotas.getSelectedItem().equals("Todos")){
            llenarTabla(0);}
            else{
                llenarTabla(Double.parseDouble(vistaClientes.comboCuotas.getSelectedItem().toString()));
            }
            }
            else{
                 JOptionPane.showMessageDialog(null, "Seleccione un cliente de la lista", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
            }
          }
        
         if(e.getSource() == vistaClientes.detalleBtn){  
           int row = vistaClientes.tablaCliente.getSelectedRow();
           if(row != -1){
               if(vistaClientes.tablaCliente.getValueAt(row, 10) != null){
                   new ControladorDetalleCuota(Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(row, 11).toString()), vistaClientes.tablaCliente.getModel().getValueAt(row, 0).toString(),vistaClientes.tablaCliente.getModel().getValueAt(row, 1).toString(), vistaClientes.tablaCliente.getModel().getValueAt(row, 3).toString(), vistaClientes.tablaCliente.getModel().getValueAt(row, 5).toString(), vistaClientes.tablaCliente.getModel().getValueAt(row, 6).toString(), Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(row, 7).toString()),  Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(row, 10).toString()));
               }else{
                  JOptionPane.showMessageDialog(null, "Debe asignar una propiedad para ver los detalles", "Atención", JOptionPane.INFORMATION_MESSAGE, null); 
               }
           }else{
               JOptionPane.showMessageDialog(null, "Seleccione un cliente de la lista", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
           }           
           }
        
        if (e.getSource() == vistaClientes.asignarBtn) {
           int row = vistaClientes.tablaCliente.getSelectedRow();
           if(row != -1){
               //-----Controlo si ya tiene asignada una propiedad
               if(vistaClientes.tablaCliente.getModel().getValueAt(row, 10)== null){
                   //------Paso el dni para crear la ficha de control---------//
                   new ControladorAsignacionPropiedad((Frame) SwingUtilities.getWindowAncestor(vistaClientes), Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(row, 2).toString()));
                 if(vistaClientes.comboCuotas.getSelectedItem().equals("Todos")){
                   llenarTabla(0);}
                   else{
                   llenarTabla(Double.parseDouble(vistaClientes.comboCuotas.getSelectedItem().toString()));
                 }
                   llenarComboCuotas();
                }else{
                   JOptionPane.showMessageDialog(null, "Cliente con propiedad ya asignada", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
               }
           }else{
               JOptionPane.showMessageDialog(null, "Seleccione un cliente de la lista", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
           }  
        }
        
        if(e.getSource()== vistaClientes.comboCuotas){
            if(vistaClientes.comboCuotas.getItemCount()!=0){
            if(vistaClientes.comboCuotas.getSelectedItem().equals("Todos")){
                  llenarTabla(0);}
                   else{
                 llenarTabla(Double.parseDouble(vistaClientes.comboCuotas.getSelectedItem().toString()));
                  }
           }
        }
        
        if(e.getSource()==vistaClientes.bajaBtn){
            int row = vistaClientes.tablaCliente.getSelectedRow();
           if(row != -1){
               if(vistaClientes.tablaCliente.getValueAt(row, 10) != null){
                  fd.bajaPropietario(Integer.parseInt(vistaClientes.tablaCliente.getValueAt(row, 2).toString()), Integer.parseInt(vistaClientes.tablaCliente.getValueAt(row, 11).toString()));
                  ld.editarPropiedad(0, vistaClientes.tablaCliente.getValueAt(row, 16).toString(), Integer.parseInt(vistaClientes.tablaCliente.getValueAt(row, 17).toString()), Integer.parseInt(vistaClientes.tablaCliente.getValueAt(row, 18).toString()));
                   if(vistaClientes.comboCuotas.getSelectedItem().equals("Todos")){
                    llenarTabla(0);}
                   else{
                    llenarTabla(Double.parseDouble(vistaClientes.comboCuotas.getSelectedItem().toString()));
                   }
               }else{
                   JOptionPane.showMessageDialog(null, "Seleccione un cliente con una propiedad", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
               }
           }else{
               JOptionPane.showMessageDialog(null, "Seleccione un cliente con propiedad asignada", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
           }
        }
    }
    
    public void llenarTabla(double monto){
        ResultSet rs;
        if(monto==0){
            rs = cd.clientesPorLotes();}
        else{
            rs = cd.clientesPorCuotas(monto);
        }        
        DefaultTableModel model = (DefaultTableModel) vistaClientes.tablaCliente.getModel();
        model.setRowCount(0);
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-YYYY");
        String fch_actualizacion = "";
        String actualizar_cemento = "";
        String cumpleaños;
        try {
            while(rs.next()){
                cumpleaños = "0";
                String dni = rs.getString(1);
                String apellidos = rs.getString(2);
                String nombres = rs.getString(3);
                String fecha_nacimiento = rs.getString(4);
                //------Controlo dia y mes para saber si es el cumpleaños--------//
                if(LocalDate.now().getMonthOfYear()==new LocalDate(rs.getDate(4)).getMonthOfYear() && LocalDate.now().getDayOfMonth()==new LocalDate(rs.getDate(4)).getDayOfMonth()){
                 cumpleaños = "1";
                }
                String barrio = rs.getString(5);                
                String calle = rs.getString(6);
                String numero = rs.getString(7);                
                String telefono1 = rs.getString(8);
                String telefono2 = rs.getString(9);
                String trabajo = rs.getString(10);
                String  baja = rs.getString(11);
                String idControl = rs.getString(12);
                String cantidad_cuotas = rs.getString(13);
                String gastos = rs.getString(14);
                String bolsa_cemento = rs.getString(15);
                if(rs.getDate(16)!=null){
                    fch_actualizacion = sdf.format(rs.getDate(16));
                    //----Controlo si ya paso un año de la ultima fecha de actualizacion de la bolsa de cemento----//
                     if((Years.yearsBetween(new LocalDate(rs.getDate(16)), LocalDate.now())).getYears()>=1){
                         actualizar_cemento = "1";
                     }else{
                          actualizar_cemento = "0";
                     }
                }else{
                    fch_actualizacion = "";
                    actualizar_cemento = "0";
                }               
                String barrio_prop = rs.getString(17);
                String manzana_prop = rs.getString(18);
                String parcela_prop = rs.getString(19);  
                clientes = new Object[] {apellidos, nombres, dni, telefono1, telefono2, barrio, calle, numero, fecha_nacimiento, trabajo, baja, idControl, cantidad_cuotas, gastos, bolsa_cemento, fch_actualizacion, barrio_prop, manzana_prop, parcela_prop, actualizar_cemento, cumpleaños};
                model.addRow(clientes);   
             }
            controlCumpleaños();
                
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    private void controlCumpleaños(){
        //-----Limpio la lista de cumpleaños------//
        dialogCumpleaños = Cumpleaños.getInstance(ventana, true);
        DefaultTableModel model = (DefaultTableModel) vistaClientes.tablaCliente.getModel();
        DefaultTableModel modeloCumpleaños = (DefaultTableModel) dialogCumpleaños.tablaCumpleaños.getModel();
        modeloCumpleaños.setRowCount(0);
        //----Verifico que la tabla de clientes tenga al menos una fila--------//
        if(model.getRowCount()!=0){            
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < model.getRowCount(); i++) {
            //----Si tiene un 1 en la columna cumpleaños, es su cumpleaños (y si!!!)--------//
         if(vistaClientes.tablaCliente.getModel().getValueAt(i, 19).toString().equals("1")){
             try {
                 String nombre = vistaClientes.tablaCliente.getModel().getValueAt(i, 1).toString();
                 String apellido = vistaClientes.tablaCliente.getModel().getValueAt(i, 0).toString();
                 String fch_nacimiento = vistaClientes.tablaCliente.getModel().getValueAt(i, 8).toString();
                 String edad = Years.yearsBetween(new LocalDate(formatter.parse(fch_nacimiento)), LocalDate.now()).toString();
                 String telefono = vistaClientes.tablaCliente.getModel().getValueAt(i, 3).toString();
                 detalleCumpleaños = new String[] {nombre, apellido, fch_nacimiento, edad, telefono};
                 modeloCumpleaños.addRow(detalleCumpleaños);
             } catch (ParseException ex) {
                 Logger.getLogger(ControladorCliente.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
        }  
        //------Si hay cumpleaños en el dia, habilito el boton para mostrar los cumpleaños--------//
        if(modeloCumpleaños.getRowCount()!=0){
            Ventana.btnCumpleaños.setVisible(true);
        }
       }  
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        int row = vistaClientes.tablaCliente.getSelectedRow();       
        vistaClientes.barrio.setText(vistaClientes.tablaCliente.getModel().getValueAt(row, 5).toString());
        vistaClientes.calle.setText(vistaClientes.tablaCliente.getModel().getValueAt(row, 6).toString());
        vistaClientes.numero.setText(vistaClientes.tablaCliente.getModel().getValueAt(row, 7).toString());
        vistaClientes.trabajo.setText(vistaClientes.tablaCliente.getModel().getValueAt(row, 9).toString());  
        //-------Si no tiene propiedad asignada entonces este valor va a ser nulo--------//
        if(vistaClientes.tablaCliente.getModel().getValueAt(row, 13)!=null){
            //----Muestro valor bolsa de cemento y fecha de actualizacion---------//
            vistaClientes.fch_actualizacion.setText(vistaClientes.tablaCliente.getModel().getValueAt(row, 14).toString());
            vistaClientes.bolsa_cemento.setText(vistaClientes.tablaCliente.getModel().getValueAt(row, 13).toString());
        }else{
            vistaClientes.fch_actualizacion.setText("");
            vistaClientes.bolsa_cemento.setText("");
        }
        //----Si tengo un 1 ha pasado un año o mas y tengo que actualizar precio bolsa de cemento-----//
        if(vistaClientes.tablaCliente.getModel().getValueAt(row, 19).toString().equals("1")){
            vistaClientes.advertencia.setText("-- Actualizar precio bolsa cemento --");
        }else{
            vistaClientes.advertencia.setText("");
        }
        ResultSet rs = rd.obtenerReferencia(Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(row,2).toString()));
        try {
            while(rs.next()){
                vistaClientes.apellido_referencia.setText(rs.getString(1));
                vistaClientes.nombre_referencia.setText(rs.getString(2));
                vistaClientes.telefono_referencia.setText(rs.getString(3));
                vistaClientes.parentesco.setText(rs.getString(4));                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    

    @Override
    public void mousePressed(MouseEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void llenarComboCuotas(){
        try {
            ResultSet rs = fd.obtenerMontoCuotas();
            vistaClientes.comboCuotas.removeAllItems();
            vistaClientes.comboCuotas.addItem("Todos");
            while (rs.next()) {
                vistaClientes.comboCuotas.addItem(rs.getString(1));
            }  
            } catch (SQLException ex) {
            Logger.getLogger(ControladorPropiedades.class.getName()).log(Level.SEVERE, null, ex);
        }   
 }
    
    public void pintarFila(){
         vistaClientes.tablaCliente.setDefaultRenderer(Object.class, new TableCellRenderer() {
             @Override
             public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                 Component component = (JLabel) DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                 Color c = Color.PINK;
                 if(table.getValueAt(row, 10) == null){
                     component.setBackground(c);
                 }
                 //vistaClientes.tablaCliente.setSelectionBackground(Color.orange);
                 return component;
             }
         });
    }

    
}
