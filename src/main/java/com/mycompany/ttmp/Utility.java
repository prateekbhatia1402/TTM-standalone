/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ttmp;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.UIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;


class BooleanRenderer extends JCheckBox implements TableCellRenderer, UIResource 
{

        private static final Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);
        ArrayList<ArrayList<Color>> colorData;
        public BooleanRenderer(ArrayList colorData) {
            super();
            setHorizontalAlignment(JLabel.CENTER);
            setBorderPainted(true);
            setOpaque(true);
            this.colorData = colorData;
            
            System.out.println(Arrays.deepToString(colorData.toArray()));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                super.setBackground(table.getSelectionBackground());
            } else {
                Color color = colorData.get(row).get(column);
                setBackground(color);
                setForeground(Utility.getOtherColorAsColor(color));
            }
            setSelected((value != null && ((Boolean) value).booleanValue()));

            if (hasFocus) {
                setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
            } else {
                setBorder(noFocusBorder);
            }

            return this;
        }
    }

class CellColorRenderer extends DefaultTableCellRenderer {
        ArrayList<ArrayList<Color>> colorData;
        public CellColorRenderer(ArrayList colorData) {
            super();
            setHorizontalAlignment(JLabel.CENTER);
            setOpaque(true);
            this.colorData = colorData;
            
            System.out.println(Arrays.deepToString(colorData.toArray()));
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column)   {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

             if (isSelected) {
                setForeground(table.getSelectionForeground());
                super.setBackground(table.getSelectionBackground());
            } else {
                Color color = colorData.get(row).get(column);
                setBackground(color);
                setForeground(Utility.getOtherColorAsColor(color));
            }
            return cellComponent;
        }
    }

class ColumnCellColorRenderer extends DefaultTableCellRenderer {
        ArrayList<Color> colorData;
        public ColumnCellColorRenderer(ArrayList colorData) {
            super();
            setHorizontalAlignment(JLabel.CENTER);
            setOpaque(true);
            this.colorData = colorData;
            
            System.out.println(Arrays.deepToString(colorData.toArray()));
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column)   {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

             if (isSelected) {
                setForeground(table.getSelectionForeground());
                super.setBackground(table.getSelectionBackground());
            } else {
                Color color = colorData.get(row);
                setBackground(color);
                setForeground(Utility.getOtherColorAsColor(color));
            }
            return cellComponent;
        }
    }

class ListCellRenderer extends DefaultListCellRenderer
{
    HashMap<String, Color> colorData;
    ListCellRenderer(HashMap colorData)
    {
        super();
        this.colorData = colorData;
    }
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index,
            boolean isSelected, boolean hasFocus)
    {
        Component c = super.getListCellRendererComponent(list, value, index, isSelected, hasFocus);
        Color color = colorData.getOrDefault(value.toString(), Color.white);
        c.setBackground(color);
        c.setForeground(Utility.getOtherColorAsColor(color));
        return c;
    }
}

public class Utility {

    public static String [] colors = {"#ad00ff", "#ff00ff", "#808000", "#000080",
    "#008080", "#ffff00", "#00ffff", "#ffa500"};
    public static enum ColorType{BG, FG};
    public static String defaultBgColor = "white";
    public static String defaultFgColor = "black";    
    private static ColorType defaultType = ColorType.BG;
    public static String colorCodeValue(String value, String color)
    {
        return colorCodeValue(value, color, defaultType);
    }
    public static String colorCodeValue(String value, String color, ColorType ctype)
    {
        int r = Integer.parseInt(color.substring(1,3), 16);
        int g = Integer.parseInt(color.substring(3,5), 16);
        int b = Integer.parseInt(color.substring(5,7), 16);
        String d = ( 0.299 * r + 0.587 * g + 0.114 * b) / 255 > 0.5 ? "00" : "ff";
        //int avg = (r + g + b) / 3;
        String otherColor = "#"+d+d+d;
        //if (avg > 0x7f)
        //    otherColor = "#000000";
        if (ColorType.FG == ctype)
        {
            return colorCodeValue(value, otherColor, color);
        }
        else 
        {
            return colorCodeValue(value, color, otherColor);
        }
    }
    public static String colorCodeValue(String value, String bgcolor,String fgcolor)
    {
        return "<html><font color='"+fgcolor+"'><p bgcolor='"
                +bgcolor+"'>"+value+"</p></font>";
    }
    public static String getOtherColor(String color)
    {
        int r = Integer.parseInt(color.substring(1,3), 16);
        int g = Integer.parseInt(color.substring(3,5), 16);
        int b = Integer.parseInt(color.substring(5,7), 16);
        String d = ( 0.299 * r + 0.587 * g + 0.114 * b) / 255 > 0.5 ? "00" : "ff";
        //int avg = (r + g + b) / 3;
        String otherColor = "#"+d+d+d;
        return otherColor;
    
    }

    public static String getOtherColor(Color color)
    {
        
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        String d = ( 0.299 * r + 0.587 * g + 0.114 * b) / 255 > 0.5 ? "00" : "ff";
        //int avg = (r + g + b) / 3;
        String otherColor = "#"+d+d+d;
        return otherColor;
        
}

    public static Color getOtherColorAsColor(Color color)
    {
        
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        Color otherColor = ( 0.299 * r + 0.587 * g + 0.114 * b) / 255 > 0.5 ? 
                new Color(0, 0, 0): new Color(255, 255, 255);
        
        return otherColor;
        
    }
}
