/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ttmp;

/**
 *
 * @author prateek
 */
public class Utility {

    public static String [] colors = {"blue", "fuchsia", "olive", "navy",
    "teal", "yellow", "aqua", "orange"};
    public static enum ColorType{BG, FG};
    public static String defaultBgColor = "white";
    public static String defaultFgColor = "black";    
    public static String colorCodeValue(String value, String color)
    {
        return colorCodeValue(value, color, defaultFgColor);
    }
    public static String colorCodeValue(String value, String color, ColorType ctype)
    {
        if (ColorType.FG == ctype)
            return colorCodeValue(value, defaultBgColor, color);
        else 
            return colorCodeValue(value, color, defaultFgColor);
    }
    public static String colorCodeValue(String value, String bgcolor,String fgcolor)
    {
        return "<html><font color='"+fgcolor+"'><p bgcolor='"
                +bgcolor+"'>"+value+"</p></font>";
    }
    

}
