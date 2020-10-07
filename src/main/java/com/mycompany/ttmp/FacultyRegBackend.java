/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ttmp;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class FacultyRegBackend {
    
    public static int addFacultyToRecords(Faculty ft,Account ac){
     Connection c=null;
       try {
        c=SqlConnect.getDatabaseConnection();
        c.setAutoCommit(false);
        Statement s=c.createStatement();
        String query=FacultyControl.getInsertStatement(999, ft);
        String[] queries=query.split(";");
        //System.out.println(Arrays.toString(queries));
        for(String q:queries){
            System.out.println(q+";");
        s.executeUpdate(q+";");
        }
        query=AccountControl.getInsertStatement(999, ac);
        s.executeUpdate(query);
        c.commit();
     } catch (SQLException ex) {
         System.out.println("!!!! message "+ex.getMessage());
         printErrorMessage(ex.getMessage());
         try {
             if(c!=null)
                c.rollback();
         } catch (SQLException ex1) {
             Logger.getLogger(FacultyRegBackend.class.getName()).log(Level.SEVERE, null, ex1);
         }
         Logger.getLogger(ParentDetailsControl.class.getName()).log(Level.SEVERE, null, ex);
     }
        return 0;
    }
    
    public static int removeFacultyFromRecords(Faculty ft){
     Connection c=null;
       try {
        c=SqlConnect.getDatabaseConnection();
        c.setAutoCommit(false);
        Statement s=c.createStatement();
        String query=FacultyControl.getDeleteStatement(999, ft);
        String[] queries=query.split(";");
        //System.out.println(Arrays.toString(queries));
        for(String q:queries){
            System.out.println(q+";");
        s.executeUpdate(q+";");
        }
        query=AccountControl.getDeleteStatement(999, ft.getUsername());
        s.executeUpdate(query);
        c.commit();
     } catch (SQLException ex) {
         System.out.println("!!!! message "+ex.getMessage());
         printErrorMessage(ex.getMessage());
         try {
             if(c!=null)
                c.rollback();
         } catch (SQLException ex1) {
             Logger.getLogger(FacultyRegBackend.class.getName()).log(Level.SEVERE, null, ex1);
         }
         Logger.getLogger(ParentDetailsControl.class.getName()).log(Level.SEVERE, null, ex);
     }
        return 0;
    }
    
    public static int updateFacultyRecords(Faculty f)
    {
        try(Connection c = SqlConnect.getDatabaseConnection();
            Statement s = c.createStatement())
        {
            c.setAutoCommit(true);
            String q = FacultyControl.getUpdateStatement(999, f);
            s.addBatch(q);
            q = QualificationControl.getDeleteStatement(999, f);
            s.addBatch(q);
            q = QualificationControl.getInsertStatement(999, f);
            s.addBatch(q);
            s.executeBatch();
            c.commit();
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(FacultyRegBackend.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    private static void printErrorMessage(String errorMessage){
        String message=null;
        if(errorMessage.toLowerCase().contains("duplicate entry")){
            if(errorMessage.contains("@") && errorMessage.contains("."))
                message="EMAIL ID ALREADY REGISTERED";
            else
                message="USERNAME ALREADY REGISTERED";
            JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
}
