/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ActualizacionCementoDAO;
import Clases.ClientesPorCriterio;
import Clases.Propietario;
import Clases.Referencia;
import Modelo.ClienteDAO;
import Modelo.FichaControlDAO;
import Modelo.LoteDAO;
import Modelo.PropietarioDAO;
import Modelo.ReferenciaDAO;
import Utils.RendererActualizacion;
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
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
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
    ActualizacionCementoDAO acd = new ActualizacionCementoDAO();
    LoteDAO ld = new LoteDAO();
    private ArrayList<String[]> cumpleaños = new ArrayList<String[]>();
    public ArrayList<Object> datosCliente = new ArrayList<>();
    Ventana ventana;
    String barrio;
    int manzana, parcela;
    FileInputStream fileIn = null;
    FileOutputStream fileOut = null;
    private Object [] clientes;
    private List<Object> cliente = new ArrayList<>();
    private List<Object> referencia = new ArrayList<>();
    File configFile = new File("config.properties");
    public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();
    private String nombres, apellidos;
    static Logger log = Logger.getLogger(ControladorCliente.class.getName());
    
    
    public ControladorCliente(Clientes vistaCliente, Ventana ventana){
        this.vistaClientes=vistaCliente;
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
                    String bolsaCemento = vistaClientes.bolsa_cemento.getText();
                if(!bolsaCemento.equals("")){
                  if(!new BigDecimal(bolsaCemento).equals(BigDecimal.ZERO)){  
                    int i = vistaClientes.tablaCliente.getSelectedRow();
                    BigDecimal nuevo_bolsa_cemento = new BigDecimal(vistaClientes.bolsa_cemento.getText());
                    fd.actualizarBolsaCemento( nuevo_bolsa_cemento, new java.sql.Date(date.getTime()), vistaClientes.tablaCliente.getModel().getValueAt(i, 11).toString());
                    acd.actualizarCemento(vistaClientes.tablaCliente.getModel().getValueAt(i, 11).toString(), new java.sql.Date(date.getTime()), new BigDecimal(vistaClientes.tablaCliente.getModel().getValueAt(i, 14).toString()), nuevo_bolsa_cemento);
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
        this.vistaClientes.tablaCliente.getColumn("Actualizacion").setCellRenderer(new RendererActualizacion());         
        llenarComboApellidos();
        desactivarBotones();
    }

    public ControladorCliente() {
    }    
    
     public void desactivarBotones(){
      if(Ventana.labelTipoUsuario.getText().equals("operador")){
            vistaClientes.bolsa_cemento.setEnabled(false);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //-----------Boton mostrar todos los clientes----//
        if(e.getSource()==vistaClientes.mostrarTodos){
            apellidos = ""; 
            vistaClientes.comboApellido.setSelectedIndex(0);
//            vistaClientes.comboNombre.setSelectedIndex(0);
                      
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
                
            }
            llenarTabla();
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
        //-----Boton agregar propietario--//
        if(e.getSource() == vistaClientes.agregarBtn){  
            new ControladorAltaCliente((Ventana) SwingUtilities.getWindowAncestor(vistaClientes), 0,0, false);
            llenarTabla();
         }
        //----------Boton eliminar cliente---------//
        if(e.getSource() == vistaClientes.eliminarBtn){
             int row = vistaClientes.tablaCliente.getSelectedRow(); 
             if(row != -1){
             ImageIcon icon = new ImageIcon("src/Imagenes/Iconos/warning.png");   
             int reply = JOptionPane.showConfirmDialog(null, "Eliminar a "+vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 0)+" "+""+" "+vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 1)+"?",
                     "Advertencia",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
              if (reply == JOptionPane.YES_OPTION) {
                  int dni = Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 2).toString());
                  cd.eliminarCliente(dni);
                  llenarTabla();
               } 
              }
              else{
                 JOptionPane.showMessageDialog(null, "Seleccione un cliente de la lista", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
               }
        }
        //--------Boton editar cliente---------//
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
        //----------Boton detalle de pago------//
         if(e.getSource() == vistaClientes.detalleBtn){  
           int row = vistaClientes.tablaCliente.getSelectedRow();
           if(row != -1){
               if(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 11) != null){
//                   datosCliente.clear();
//                   datosCliente.add(1, Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 12).toString()));
                   new ControladorDetalleCuota(datosCliente, Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 12).toString()), vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 0).toString(),vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 1).toString(), vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 3).toString(), vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 5).toString(), vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 6).toString(), Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 7).toString()),  Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 11).toString()),Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 10).toString()));
               }else{
                  JOptionPane.showMessageDialog(null, "Debe asignar una propiedad para ver los detalles", "Atención", JOptionPane.INFORMATION_MESSAGE, null); 
               }
           }else{
               JOptionPane.showMessageDialog(null, "Seleccione un cliente de la lista", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
           }           
           }
        //---------Boton asignar lote----------//
        if (e.getSource() == vistaClientes.asignarBtn) {
           int row = vistaClientes.tablaCliente.getSelectedRow();
           if(row != -1){
               //-----Controlo si ya tiene asignada una propiedad-----------//
               if(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 11)!=null){
                   int reply = JOptionPane.showConfirmDialog(null, "Desea agregar un nuevo lote a "+vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 0)+" "+vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 1)+"?",
                 "Advertencia",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
                  if (reply == JOptionPane.YES_OPTION) {  
                   //------Paso el dni para crear la ficha de control---------//
                 new ControladorAsignacionPropiedad((Frame) SwingUtilities.getWindowAncestor(vistaClientes), Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 2).toString()));
                 llenarTabla();
                }
               }else{
                 new ControladorAsignacionPropiedad((Frame) SwingUtilities.getWindowAncestor(vistaClientes), Integer.parseInt(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 2).toString()));
                 llenarTabla(); 
               }
           }else{
               JOptionPane.showMessageDialog(null, "Seleccione un cliente de la lista", "Atención", JOptionPane.INFORMATION_MESSAGE, null);
           }  
        }        
        //----------Boton dar de baja cliente-----//
        if(e.getSource()==vistaClientes.bajaBtn){
            int row = vistaClientes.tablaCliente.getSelectedRow();
           if(row != -1){
               if(vistaClientes.tablaCliente.getValueAt(row, 10) != null){
                 int reply = JOptionPane.showConfirmDialog(null, "Dar de baja a "+vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 0)+" "+""+" "+vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 1)+"?",
                 "Advertencia",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
                  if (reply == JOptionPane.YES_OPTION) {  
                    fd.bajaPropietario(Integer.parseInt(vistaClientes.tablaCliente.getValueAt(row, 2).toString()), Integer.parseInt(vistaClientes.tablaCliente.getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 11).toString()));
                    ld.editarPropiedad(0, vistaClientes.tablaCliente.getValueAt(row, 16).toString(), Integer.parseInt(vistaClientes.tablaCliente.getValueAt(row, 17).toString()), Integer.parseInt(vistaClientes.tablaCliente.getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 18).toString()));
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
        try {
            //-------Obtengo el propietario seleccionado por default, para mostrar los clientes de ese propietario----//
            FileReader reader = new FileReader(configFile);
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
            vistaClientes.comboApellido.setSelectedItem(apellidos);
            } catch (FileNotFoundException ex) {
                java.util.logging.Logger.getLogger(ControladorCliente.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(ControladorCliente.class.getName()).log(Level.SEVERE, null, ex);
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
            vistaClientes.comboNombre.setSelectedItem(nombres);
            llenarTabla();
     }
    
     public void llenarTabla(){
         
        List<ClientesPorCriterio> listaClientes;        
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
        String cumpleaños;
        String tipoActualizacion;
        Date date = new Date();
        LocalDate fecha_actual = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        vistaClientes.nroClientes.setText(String.valueOf(listaClientes.size()));
        try {
            if(listaClientes.size()>0){
                for (int i = 0; i < listaClientes.size(); i++) {
                   tipoActualizacion = "Cemento" ;
                   icono = new JLabel();
                   cumpleaños = "0";
                   int dni = listaClientes.get(i).getDni();
                   String apellidos = listaClientes.get(i).getApellidos();
                   String nombres = listaClientes.get(i).getNombres();
                   Date fecha_nacimiento = listaClientes.get(i).getFecha_nacimiento();
                    //------Controlo dia y mes para saber si es el cumpleaños--------//
                    Instant instant = Instant.ofEpochMilli(fecha_nacimiento.getTime());
                    LocalDate fch_nacimiento =  LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
                    if(fecha_actual.getMonthValue() == fch_nacimiento.getMonthValue() && fecha_actual.getDayOfMonth() == fch_nacimiento.getDayOfMonth()){
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
                    Date fechaActualizacion = listaClientes.get(i).getFecha_actualizacion();
                    Instant instant2 = Instant.ofEpochMilli(fechaActualizacion.getTime());
                    LocalDate fecha_actualizacion = LocalDateTime.ofInstant(instant2, ZoneId.systemDefault()).toLocalDate();   
                    fch_actualizacion = sdf.format(listaClientes.get(i).getFecha_actualizacion());
                    //----Controlo si ya paso un año de la ultima fecha de actualizacion de la bolsa de cemento----//
                     if(((Period.between(fecha_actualizacion, LocalDate.now())).getMonths())%6==0 && (Period.between(fecha_actualizacion, LocalDate.now())).getMonths()!=0){
                         actualizar_cemento = "1";
                         icono.setIcon(icon);
                         icono.setHorizontalAlignment(SwingConstants.CENTER);
                     }else{
                          actualizar_cemento = "0";
                          icono.setIcon(null);
                     }
                    }else{
                        fch_actualizacion = "";
                        actualizar_cemento = "0";
                    } 
                    String barrio_prop = listaClientes.get(i).getBarrio();
                    String manzana_prop = listaClientes.get(i).getManzana();
                    String parcela_prop = listaClientes.get(i).getParcela();
                    if(listaClientes.get(i).getBandera_cemento()==0){
                        tipoActualizacion = "Emp. Público";
                    }
                    BigDecimal cuota_pura = listaClientes.get(i).getCuota_pura();   
                    int nroCuota = listaClientes.get(i).getCuotas();
                    String ultimaCuota = listaClientes.get(i).getUltimaCuota();
                    clientes = new Object[] {apellidos, nombres, dni, telefono1, telefono2, barrio, calle, numero, fecha_nacimiento, trabajo, baja, idControl, cantidad_cuotas, gastos, bolsa_cemento, fch_actualizacion, barrio_prop, manzana_prop, parcela_prop, tipoActualizacion, actualizar_cemento, cumpleaños, cuota_pura, icono, nroCuota, ultimaCuota};
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
        //------Si hay cumpleaños en el dia, habilito el boton para mostrar los cumpleaños--------//
        if(modeloCumpleaños.getRowCount()!=0){
            Ventana.btnCumpleaños.setVisible(true);
        }
       }  
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        int row = vistaClientes.tablaCliente.getSelectedRow();  
        vistaClientes.barrio.setText(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 5).toString());
        vistaClientes.calle.setText(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 6).toString());
        vistaClientes.numero.setText(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 7).toString());
        vistaClientes.trabajo.setText(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 9).toString());  
        vistaClientes.telefono.setText(vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 3).toString()+"_"+vistaClientes.tablaCliente.getModel().getValueAt(vistaClientes.tablaCliente.convertRowIndexToModel(row), 4).toString());
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
            vistaClientes.advertencia.setText("Actualizar precio bolsa cemento");
//            vistaClientes.detalleBtn.setEnabled(false);
        }else{
            vistaClientes.advertencia.setText("");
            vistaClientes.detalleBtn.setEnabled(true);
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
        }else{
                vistaClientes.apellido_referencia.setText("");
                vistaClientes.nombre_referencia.setText("");
                vistaClientes.telefono_referencia.setText("");
                vistaClientes.parentesco.setText(""); 
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
