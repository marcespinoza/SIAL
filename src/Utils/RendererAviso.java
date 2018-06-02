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
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Marcelo Espinoza
 */
public class RendererAviso extends DefaultTableCellRenderer{
    
    ImageIcon icon = new ImageIcon(getClass().getResource("/Imagenes/iconos/warning.png"));
    JLabel jLabel = new JLabel();
//private JLabel component;
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//        
//         if (table.getValueAt(row, 23) != "" && column==23) {
//            jLabel.setIcon(icon);
//        }
         return (Component) value;
    }   
  }