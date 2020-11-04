/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ttmp;

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
    

}
