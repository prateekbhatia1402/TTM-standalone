/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ttmp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Programming
 */
public class DayControl {
     public static Day[] getAllDays(){
        Day[] days=null;
        try(Connection con=SqlConnect.getDatabaseConnection()) {
            
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select * from `day` order by `day number`");
            if(rs.last()==false)
                return null;
            int count=rs.getRow();
            days=new Day[count];
            rs.first();
            for(int i=0;i<count;i++,rs.next())
            {
                String dayid=rs.getString("day id");
                String dayname=rs.getString("day name");
                int daynumber=rs.getInt("day number");
                days[i]=new Day(dayid,dayname,daynumber);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TimeSlotControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return days;
    }
}
