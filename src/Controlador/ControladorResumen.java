/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.MinutaDAO;
import Vista.Panels.Resumen;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


public class ControladorResumen implements ActionListener{
    
    Resumen vistaResumen;
    private Object [] resumen;
    MinutaDAO md = new MinutaDAO();
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();  

    public ControladorResumen(Resumen vistaResumen) {
        this.vistaResumen=vistaResumen;
        this.vistaResumen.buscar.addActionListener(this);
    }
   
    public void llenarTabla(int añoDesde, int mesDesde, int añoHasta, int mesHasta){
        ResultSet rs = null;
        ResultSet rs2 = null;
        rs = md.minutasPorMes(añoDesde, añoHasta, mesDesde, mesHasta);
        rs2 = md.obtenerMinutasXCategoria(añoDesde, añoHasta, mesDesde, mesHasta);
        DefaultTableModel model = (DefaultTableModel) vistaResumen.tablaResumen.getModel();
        model.setRowCount(0);
        try {
            //--------Minutas discriminados por monto de cuota---------//
            while(rs2.next()){
                String categoria = rs2.getString(1);
                String total = rs2.getString(2);
                String mes = rs2.getString(3);
                resumen = new Object[] {categoria, total, mes};
                dataset.addValue(new BigDecimal(total), categoria, mes); 
                model.addRow(resumen);   
            } 
            barras();      
          
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
     public void barras(){ 
      JFreeChart chart = ChartFactory.createBarChart( 
    "", // El titulo de la gráfica 
    "Mes", // Etiqueta de categoria 
    "Valor", // Etiqueta de valores 
    dataset, // Datos 
    PlotOrientation.VERTICAL, // orientacion 
    true, // Incluye Leyenda 
    true, // Incluye tooltips 
    false // URLs? 
    ); 
        ChartPanel chartpanel = new ChartPanel(chart);
        chartpanel.setDomainZoomable(true);         
        vistaResumen.freeChart.removeAll();
        vistaResumen.freeChart.setLayout(new java.awt.BorderLayout());
        vistaResumen.freeChart.add(chartpanel, BorderLayout.CENTER);
        vistaResumen.freeChart.revalidate();
        vistaResumen.freeChart.repaint();
    }
   
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vistaResumen.buscar){
            llenarTabla(vistaResumen.añoDesde.getYear(), vistaResumen.añoHasta.getYear(), vistaResumen.mesDesde.getMonth()+1,vistaResumen.mesHasta.getMonth()+1);
        }
    }
    
}
