/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ttmp;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author prateek
 */
public class ScheduleScripts {
    
    
    static void runUpdateScheduleStatus()
    {    
        Integer a = 5;
        ScheduleScripts s = new ScheduleScripts();
        s.runUpdateScheduleStatusTask();
    }
    
    private void runUpdateScheduleStatusTask()
    {
        new Thread(new UpdateScheduleStatusThread()).start();
    }
    private class UpdateScheduleStatusThread implements Runnable
    {
        
        @Override
        public void run() {
            updateScheduleStatus();
        }
        
    private void updateScheduleStatus()
    {
        try(Connection con = SqlConnect.getDatabaseConnection();
                Statement st = con.createStatement();)
        {
            String query = "UPDATE schedule set status = 'inactive' "
                    + "WHERE `to` < curdate() AND status = 'outgoing'";
            st.executeUpdate(query);
            query = "UPDATE schedule set status = 'active' "
                    + "WHERE `from` <= curdate() AND status = 'incoming'";
            st.executeUpdate(query);
        } 
        catch (SQLException ex) {
            Logger.getLogger(ScheduleScripts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    }
}
