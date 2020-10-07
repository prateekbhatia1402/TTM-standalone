/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ttmp;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Programming
 */
public class TimeSlotControl {
    public static TimeSlot[] getAllSlots(){
        TimeSlot[] timeslots=null;
        try(Connection con=SqlConnect.getDatabaseConnection()) {
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select * from `time slot`");
            if(rs.last()==false)
                return null;
            int count=rs.getRow();
            timeslots=new TimeSlot[count];
            rs.first();
            for(int i=0;i<count;i++,rs.next())
            {
                String id=rs.getString("id");
                String from=rs.getString("from");
                String to=rs.getString("to");
                timeslots[i]=new TimeSlot(id,from,to);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TimeSlotControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return timeslots;
    }
}
