/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Clases.ClientesPorCriterio;
import Clases.Propietario;
import Clases.Referencia;
import Modelo.ClienteDAO;
import Modelo.FichaControlDAO;
import Modelo.LoteDAO;
import Modelo.PropietarioDAO;
import Modelo.ReferenciaDAO;
import Utils.RendererAviso;
import Utils.RendererTablaCliente;
import Vista.Dialogs.Cumpleaños;
import Vista.Frame.Ventana;
import Vista.Panels.Clientes;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.Years;

/**
 *
 * @author Marcelo
 */
public class ControladorCliente implements ActionListener, MouseListener, TableModelListener{
    
    RendererTablaCliente r = new RendererTablaCliente();
    Clientes vistaClientes;
    Cumpleaños dialogCumpleaños;
    ClienteDAO cd = new ClienteDAO();
    ReferenciaDAO rd = new ReferenciaDAO();
    FichaControlDAO fd = new FichaControlDAO();
    PropietarioDAO pd = new PropietarioDAO();
    LoteDAO ld = new LoteDAO();
    private ArrayList<String[]> cumpleaños = new ArrayList<String[]>();
    Ventana ventana;
    String barrio;
    int manzana, parcela;
    FileInputStream fileIn = null;
    FileOutputStream fileOut = null;
    private Object [] clientes;
    private String [] detalleCumpleaños;
    private List<Object> cliente = new ArrayList<>();
    private List<Object> referencia = new ArrayList<>();
    File configFile = new File("config.properties");
    public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();
    private String nombres, apellidos;
    static Logger log = Logger.getLogger(ControladorCliente.class.getName());
    
    
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
        this.vistaClientes.comboApellido.addActionListener(this);
        this.vistaClientes.comboNombre.addActionListener(this);
        this.vistaClientes.mostrarTodos.addActionListener(this);
        this.vistaClientes.tablaCliente.getModel().addTableModelListener(this);
        this.vistaClientes.buscarTodos.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e){
              String query=vistaClientes.buscarTodos.getText().toLowerCase();
              filter(query);
            }
         });
        this.vistaClientes.buscarBarrio.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e){
              String query=vistaClientes.buscarBarrio.getText().toLowerCase();
              filtroBarrio(query);
            }
         });
        this.vistaClientes.buscarManzana.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e){
              String query=vistaClientes.buscarManzana.getText().toLowerCase();
              filtroManzana(query);
            }
         });
        this.vistaClientes.buscarParcela.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e){
              String query=vistaClientes.buscarParcela.getText().toLowerCase();
              filtroParcela(query);
            }
         });
        this.vistaClientes.bolsa_cemento.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
               if(e.getKeyCode()==KeyEvent.VK_ENTER){                   
                    Date date = new Date();
                if(!vistaClientes.bolsa_cemento.getText().equals("")){
                  if(!((Integer.parseInt(vistaClientes.bolsa_cemento.getText()))==0)){  
                    int i = vistaClientes.tablaCliente.getSelectedRow();
                    //----Aca solo debo actualizar saldo bolsa de cemento -----//
//                    BigDecimal gastos_ = new BigDecimal(vistaClientes.tablaCliente.getModel().getValueAt(i, 13).toString());
//                    BigDecimal cuota_ = new BigDecimal(vistaClientes.tablaCliente.getModel().getValueAt(i, 22).toString());
//                    BigDecimal bolsa_cemento_ = new BigDecimal(vistaClientes.tablaCliente.getModel().getValueAt(i, 14).toString());
                      BigDecimal nuevo_bolsa_cemento = new BigDecimal(vistaClientes.bolsa_cemento.getText());
//                    BigDecimal nueva_cuota = ((gastos_.add(cuota_)).divide(bolsa_cemento_, 2, RoundingMode.DOWN)).multiply(nuevo_bolsa_cemento);
//                    BigDecimal nuevo_gasto = nueva_cuota.subtract(nueva_cuota.divide(new BigDecimal("1.1"), 2, BigDecimal.ROUND_HALF_UP));
//                    BigDecimal nueva_cuota_pura = nueva_cuota.subtract(nuevo_gasto);
                    fd.actualizarBolsaCemento( nuevo_bolsa_cemento, new java.sql.Date(date.getTime()), vistaClientes.tablaCliente.getModel().getValueAt(i, 11).toString());
                    llenarTabla();
                    vistaClientes.tablaCliente.getSelectionModel().clearSelection();
                    vistaClientes.fch_actualizacion.setText("");
                    vistaClientes.bolsa_cemento.setText("");
                    vistaClientes.advertencia.setText("");
                    ventana.requestFocusInWindow();
                  }else{
                       JOptionPane.showMessageDialog(null, "Ingrese un valor mayor a cero", "Atención", JOptionPane.INFORMATION_MESSAGE, null); 
                  }
                }
                else{
                   JOptionPane.showMessageDialog(null, "Ingrese un valor", "Atención", JOptionPane.INFORMATION_MESSAGE, null); 
                }
               }
            }
            //-----Solo permite ingresar numeros en campo bolsa cemento----//
            @Override
            public void keyTyped(KeyEvent e){
             char vchar = e.getKeyChar();
             if(!(Character.isDigit(vchar))){
              e.consume();
             }
            }
        });
        vistaClientes.datosCliente.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.BLACK), "Datos cliente"));
        vistaClientes.datosReferencia.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.BLACK), "Datos referencia"));
        this.vistaClientes.tablaCliente.setDefaultRenderer(Object.class, r);
        this.vistaClientes.tablaCliente.getColumn("Aviso").setCellRenderer(new RendererAviso());
        llenarComboApellidos();
        llenarTabla();
    }

    public ControladorCliente() {
    }    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource()==vistaClientes.mostrarTodos){
            vistaClientes.comboApellido.setSelectedIndex(0);
            vistaClientes.comboNombre.setSelectedIndex(0);
            apellidos = "";
            llenarTabla();
        }
        
        //=======Eventos sobre comboApellido=====//
         if(e.getSource()==vistaClientes.comboApellido){
          if(vistaClientes.comboApellido.getItemCount()!=0){
            if(!vistaClientes.comboApellido.getSelectedItem().equals("Seleccione")){  
                apellidos =vistaClientes.comboApellido.getSelectedItem().toString();
                llenarComboNombres(apellidos);
            }else{
                 vistaClientes.comboNombre.setSelectedIndex(0);
            }
          } 
        }
         //----------Eventos sobre combo nombre-------//
         if(e.getSource()==vistaClientes.comboNombre){
           if(vistaClientes.comboNombre.getItemCount()!=0){
            if(!vistaClientes.comboNombre.getSelectedItem().equals("Seleccione")){ 
                nombres = vistaClientes.comboNombre.getSelectedItem().toString();
                llenarTabla();
            }
            }
        }
         //-----------Boton cambiar propietario------------//
          if(e.getSource() == vistaClientes.cambiarPropietario){
              int row = vistaClientes.tablaCliente.getSelectedRow();
              //--------Verifico que haya seleccionado alguna fila----------//
              if(row != -1){
                if(vistaClientes.tablaCliente.getValueAt(row, 10) != null){ 
                  String[] options = {"Nuevo", "Existente"};
                  int seleccion = JOptionPane.showOptionDialog(null, "Cambiar propietario..", "Propietario", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                 //---Seleccion es 0 se va a cambiar un cliente actual por un cliente nuevo
                  if(seleccion==0){
                    new ControladorAltaCliente((Ventana) SwingUtilities.getWindowAncestor(vistaClientes), Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(row, 11).toString()),  Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(row, 2).toString()), true);
                    llenarTabla();}
                  if(seleccion==1){
                     new ControladorPanelClientes((Ventana) SwingUtilities.getWindowAncestor(vistaClientes), Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(row, 2).toString()), Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(row, 11).toString()));
                     llenarTabla();}
                  if(seleccion==-1){
                     JOptionPane.getRootFrame().dispose(); 
                  }
                 }else{
                JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente con una propiedad asignada", "Atención", JOptionPane.INFORMATION_MESSAGE, null);    
                }          
            }else{
                JOptionPane.showMessageDialog(null, "Seleccione un propietario de la lista", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
             }}
        //--------Boton agregar propietario-----------//  
        if(e.getSource() == vistaClientes.agregarPropietario){
               int row = vistaClientes.tablaCliente.getSelectedRow();
           if(row != -1){
               System.out.println(vistaClientes.tablaCliente.getValueAt(row, 11));
               if(vistaClientes.tablaCliente.getValueAt(row, 11) != null){          
                  new ControladorAltaCliente((Ventana) SwingUtilities.getWindowAncestor(vistaClientes),  Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(row, 11).toString()), 0, false);
                  llenarTabla();
               }else{
                  JOptionPane.showMessageDialog(null, "Seleccione un cliente con un lote asignado", "Atención", JOptionPane.INFORMATION_MESSAGE, null); 
               }
           }else{
               JOptionPane.showMessageDialog(null, "Seleccione un propietario de la lista", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
           } 
        }
        if(e.getSource() == vistaClientes.agregarBtn){  
            new ControladorAltaCliente((Ventana) SwingUtilities.getWindowAncestor(vistaClientes), 0,0, false);
            llenarTabla();
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
              llenarTabla();
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
                cliente.add(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), i));
            }
            referencia.add(vistaClientes.apellido_referencia.getText());
            referencia.add(vistaClientes.nombre_referencia.getText());
            referencia.add(vistaClientes.telefono_referencia.getText());
            referencia.add(vistaClientes.parentesco.getText());
            new ControladorEditarCliente((Ventana) SwingUtilities.getWindowAncestor(vistaClientes), cliente, referencia);
            llenarTabla();
            }
            else{
                 JOptionPane.showMessageDialog(null, "Seleccione un cliente de la lista", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
            }
          }
        
         if(e.getSource() == vistaClientes.detalleBtn){  
           int row = vistaClientes.tablaCliente.getSelectedRow();
           if(row != -1){
               if(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 10) != null){
                   new ControladorDetalleCuota(Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 12).toString()), vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 0).toString(),vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 1).toString(), vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 3).toString(), vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 5).toString(), vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 6).toString(), Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 7).toString()),  Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 11).toString()),Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 10).toString()));
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
               //-----Controlo si ya tiene asignada una propiedad-----------//
               System.out.println(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row),10)+"propiedad");
               if(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 11)== null){
                   //------Paso el dni para crear la ficha de control---------//
                   new ControladorAsignacionPropiedad((Frame) SwingUtilities.getWindowAncestor(vistaClientes), Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 2).toString()));
                 llenarTabla();
                }else{
                   JOptionPane.showMessageDialog(null, "Cliente con propiedad ya asignada", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
               }
           }else{
               JOptionPane.showMessageDialog(null, "Seleccione un cliente de la lista", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
           }  
        }
        
        
        if(e.getSource()==vistaClientes.bajaBtn){
            int row = vistaClientes.tablaCliente.getSelectedRow();
           if(row != -1){
               if(vistaClientes.tablaCliente.getValueAt(row, 10) != null){
                 int reply = JOptionPane.showConfirmDialog(null, "Dar de baja a "+vistaClientes.tablaCliente.getModel().getValueAt(row, 0)+" "+""+" "+vistaClientes.tablaCliente.getModel().getValueAt(row, 1)+"?",
                 "Advertencia",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
                  if (reply == JOptionPane.YES_OPTION) {  
                    fd.bajaPropietario(Integer.parseInt(vistaClientes.tablaCliente.getValueAt(row, 2).toString()), Integer.parseInt(vistaClientes.tablaCliente.getValueAt(row, 11).toString()));
                    ld.editarPropiedad(0, vistaClientes.tablaCliente.getValueAt(row, 16).toString(), Integer.parseInt(vistaClientes.tablaCliente.getValueAt(row, 17).toString()), Integer.parseInt(vistaClientes.tablaCliente.getValueAt(row, 18).toString()));
                    llenarTabla();
                  }
                   }else{
                     JOptionPane.showMessageDialog(null, "Seleccione un cliente con una propiedad", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
                     }
           }else{
               JOptionPane.showMessageDialog(null, "Seleccione un cliente con propiedad asignada", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
           }
        }
    }
    
    
    public void llenarComboApellidos(){
            FileReader reader = null;
        try {
            //-------Obtengo el propietario seleccionado por default, para mostrar los clientes de ese propietario----//
            reader = new FileReader(configFile);
            Properties props = new Properties();
            props.load(reader);
            apellidos = props.getProperty("apellidoPropietario");
            nombres = props.getProperty("nombrePropietario");
            List<Propietario> propietarios;
            propietarios = pd.obtenerApellidos();
            vistaClientes.comboApellido.removeAllItems();
            vistaClientes.comboApellido.addItem("Seleccione");
            for (int i = 0; i < propietarios.size(); i++) {   
                vistaClientes.comboApellido.addItem(propietarios.get(i).getApellidos());
            }
            } catch (FileNotFoundException ex) {
                java.util.logging.Logger.getLogger(ControladorCliente.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(ControladorCliente.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(ControladorCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
 }   
  public void llenarComboNombres(String apellidos){
        List<Propietario>propietarios = null;        
            propietarios = pd.obtenerNombres(apellidos);
            vistaClientes.comboNombre.removeAllItems();
            vistaClientes.comboNombre.addItem("Seleccione");
            for(int i=0; i< propietarios.size();i++) {
                vistaClientes.comboNombre.addItem(propietarios.get(i).getNombres());
            }
 }
    
    public void llenarTabla(){
        List<ClientesPorCriterio> listaClientes = new ArrayList<>();
        if(apellidos.equals("")){
            listaClientes = cd.clientesPorLotes();}
        else{
           listaClientes = cd.clientesPorPropietarios(apellidos, nombres);
        }        
        DefaultTableModel model = (DefaultTableModel) vistaClientes.tablaCliente.getModel();
        model.setRowCount(0);
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-YYYY");
        ImageIcon icon = new ImageIcon(getClass().getResource("/Imagenes/iconos/alerta.png"));
        JLabel icono;
        String fch_actualizacion = "";
        String actualizar_cemento = "";
        String aviso = "";
        String cumpleaños;
        try {
            if(listaClientes.size()>0){
                for (int i = 0; i < listaClientes.size(); i++) {
                   aviso = "";
                   icono = new JLabel();
                   cumpleaños = "0";
                   int dni = listaClientes.get(i).getDni();
                   String apellidos = listaClientes.get(i).getApellidos();
                   String nombres = listaClientes.get(i).getNombres();
                   Date fecha_nacimiento = listaClientes.get(i).getFecha_nacimiento();
                    //------Controlo dia y mes para saber si es el cumpleaños--------//
                   if(LocalDate.now().getMonthOfYear()==new LocalDate(listaClientes.get(i).getFecha_nacimiento()).getMonthOfYear() && LocalDate.now().getDayOfMonth()==new LocalDate(listaClientes.get(i).getFecha_nacimiento()).getDayOfMonth()){
                    cumpleaños = "1";
                   }
                   String barrio = listaClientes.get(i).getBarrio_cliente();
                   String calle = listaClientes.get(i).getCalle_cliente();
                   int numero = listaClientes.get(i).getNro_cliente();
                   String telefono1 = listaClientes.get(i).getTelefono1();
                   String telefono2 = listaClientes.get(i).getTelefono2();
                   String trabajo = listaClientes.get(i).getTrabajo();
                   int baja = listaClientes.get(i).getBaja();
                   String idControl = listaClientes.get(i).getIdControl();
                   int cantidad_cuotas = listaClientes.get(i).getCantidad_cuotas();
                   BigDecimal gastos = listaClientes.get(i).getGastos();
                   BigDecimal bolsa_cemento = listaClientes.get(i).getBolsa_cemento();
                   if(listaClientes.get(i).getFecha_actualizacion()!=null){
                    fch_actualizacion = sdf.format(listaClientes.get(i).getFecha_actualizacion());
                    //----Controlo si ya paso un año de la ultima fecha de actualizacion de la bolsa de cemento----//
                     if((Years.yearsBetween(new LocalDate(listaClientes.get(i).getFecha_actualizacion()), LocalDate.now())).getYears()>=1){
                         actualizar_cemento = "1";
                     }else{
                          actualizar_cemento = "0";
                     }
                     //-------------------------------------//
                     //------Controla si pasaron 6 meses desde la ultima actualizacion del precio de la bolsa de cemento para calcular amortizacion--------------//
                     if(((Months.monthsBetween(new LocalDate(listaClientes.get(i).getFecha_actualizacion()), LocalDate.now())).getMonths())%5==0 && (Months.monthsBetween(new LocalDate(listaClientes.get(i).getFecha_actualizacion()), LocalDate.now())).getMonths()!=0){
                         icono.setIcon(icon);
                         icono.setHorizontalAlignment(SwingConstants.CENTER);
                     }else{
                         icono.setIcon(null);
                     }
                     //----------------------------//
                }else{
                    fch_actualizacion = "";
                    actualizar_cemento = "0";
                } 
                String barrio_prop = listaClientes.get(i).getBarrio();
                String manzana_prop = listaClientes.get(i).getManzana();
                String parcela_prop = listaClientes.get(i).getParcela();
                String observaciones = listaClientes.get(i).getObservacion();
                BigDecimal cuota_pura = listaClientes.get(i).getCuota_pura();   
                clientes = new Object[] {apellidos, nombres, dni, telefono1, telefono2, barrio, calle, numero, fecha_nacimiento, trabajo, baja, idControl, cantidad_cuotas, gastos, bolsa_cemento, fch_actualizacion, barrio_prop, manzana_prop, parcela_prop, observaciones, actualizar_cemento, cumpleaños, cuota_pura, icono};
                model.addRow(clientes); 
                }
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
        //----Verifico que la tabla de listaClientes tenga al menos una fila--------//
        if(model.getRowCount()!=0){            
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < model.getRowCount(); i++) {
            //----Si tiene un 1 en la columna cumpleaños, es su cumpleaños (y si!!!)--------//
         if(vistaClientes.tablaCliente.getModel().getValueAt(i, 21).toString().equals("1")){
             try {
                 String nombre = vistaClientes.tablaCliente.getModel().getValueAt(i, 1).toString();
                 String apellido = vistaClientes.tablaCliente.getModel().getValueAt(i, 0).toString();
                 String fch_nacimiento = vistaClientes.tablaCliente.getModel().getValueAt(i, 8).toString();
                 String edad = String.valueOf(Years.yearsBetween(new LocalDate(formatter.parse(fch_nacimiento)), LocalDate.now()).getYears());
                 String telefono = vistaClientes.tablaCliente.getModel().getValueAt(i, 3).toString();
                 detalleCumpleaños = new String[] {nombre, apellido, fch_nacimiento, edad, telefono};
                 modeloCumpleaños.addRow(detalleCumpleaños);
             } catch (ParseException ex) {
                 log.debug("Cliente"+ex);
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
        //========Respaldo valores barrio, manzana y parcela por si quiere editarlas=========//
        // barrio = vistaClientes.tablaCliente.getModel().getValueAt(row, 16).toString();
        // manzana = Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(row, 17).toString());
        // parcela = Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(row, 18).toString());
        //=========Fin respaldo===============//
        vistaClientes.barrio.setText(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 5).toString());
        vistaClientes.calle.setText(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 6).toString());
        vistaClientes.numero.setText(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 7).toString());
        vistaClientes.trabajo.setText(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 9).toString());  
        //-------Si no tiene propiedad asignada entonces este valor va a ser nulo--------//
        if(vistaClientes.tablaCliente.getModel().getValueAt(row, 13)!=null){
            //----Muestro valor bolsa de cemento y fecha de actualizacion---------//
            vistaClientes.fch_actualizacion.setText(vistaClientes.tablaCliente.getModel().getValueAt(row, 15).toString());
            vistaClientes.bolsa_cemento.setText(vistaClientes.tablaCliente.getModel().getValueAt(row, 14).toString());
        }else{
            vistaClientes.fch_actualizacion.setText("");
            vistaClientes.bolsa_cemento.setText("");
        }
        //----Si tengo un 1 ha pasado un año o mas y tengo que actualizar precio bolsa de cemento-----//
        if(vistaClientes.tablaCliente.getModel().getValueAt(row, 20).toString().equals("1")){
            vistaClientes.advertencia.setText("-- Actualizar precio bolsa cemento --");
        }else{
            vistaClientes.advertencia.setText("");
        }
         List<Referencia> listaReferencia;
        listaReferencia = new ArrayList<>();
        listaReferencia = rd.obtenerReferencia(Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 2).toString()));
        if(!listaReferencia.isEmpty()){
            for (int i = 0; i < listaReferencia.size(); i++) {
                vistaClientes.apellido_referencia.setText(listaReferencia.get(i).getApellidos());
                vistaClientes.nombre_referencia.setText(listaReferencia.get(i).getNombres());
                vistaClientes.telefono_referencia.setText(listaReferencia.get(i).getTelefono());
                vistaClientes.parentesco.setText(listaReferencia.get(i).getParentesco()); 
            }
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
  
    
    private void filter(String query){
         DefaultTableModel table = (DefaultTableModel) vistaClientes.tablaCliente.getModel();
         TableRowSorter<DefaultTableModel> tr = new TableRowSorter<> (table);
         vistaClientes.tablaCliente.setRowSorter(tr);
         tr.setRowFilter(RowFilter.regexFilter("(?i)" + query));
    }
    
    private void filtroBarrio(String query){
         DefaultTableModel table = (DefaultTableModel) vistaClientes.tablaCliente.getModel();
         TableRowSorter<DefaultTableModel> tr = new TableRowSorter<> (table);
         vistaClientes.tablaCliente.setRowSorter(tr);
         tr.setRowFilter(RowFilter.regexFilter("(?i)" + query,16));
    }
    
     private void filtroParcela(String query){
         DefaultTableModel table = (DefaultTableModel) vistaClientes.tablaCliente.getModel();
         TableRowSorter<DefaultTableModel> tr = new TableRowSorter<> (table);
         vistaClientes.tablaCliente.setRowSorter(tr);
         tr.setRowFilter(RowFilter.regexFilter("(?i)" + query,18));
    }
    private void filtroManzana(String query){
         DefaultTableModel table = (DefaultTableModel) vistaClientes.tablaCliente.getModel();
         TableRowSorter<DefaultTableModel> tr = new TableRowSorter<> (table);
         vistaClientes.tablaCliente.setRowSorter(tr);
         tr.setRowFilter(RowFilter.regexFilter("(?i)" + query,17));
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        int row = vistaClientes.tablaCliente.getSelectedRow();
        if(row!=-1){
          if (e.getType() == TableModelEvent.UPDATE) {
              fd.actualizarObservacion(vistaClientes.tablaCliente.getValueAt(row, 19).toString() , Integer.parseInt(vistaClientes.tablaCliente.getValueAt(row, 11).toString()));
          } 
        }  
    }
    
}
