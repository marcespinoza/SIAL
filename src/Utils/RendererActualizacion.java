/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.awt.Color;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Marcelo Espinoza
 */
public class RendererActualizacion extends DefaultTableCellRenderer{
    
    Color verde = new Color(0, 151, 0);
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus,row, column);
        if(table.getValueAt(row, 19).toString().equals("Cemento")){
           c.setForeground(Color.blue);
        }else{
            c.setForeground(verde);
        }
        setHorizontalAlignment(SwingConstants.CENTER);
return this;
    }   
  }