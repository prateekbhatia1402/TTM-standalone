/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ttmp;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class StudentRegBackend {
    
    public static int addStudentToRecords(Student st,ParentDetails pd,Account ac){
        Connection c=null;
        Statement s=null;
        try {
        c=SqlConnect.getDatabaseConnection();
        c.setAutoCommit(false);
        s=c.createStatement();
        String query=AccountControl.getInsertStatement(999, ac);
        s.executeUpdate(query);
        query=ParentDetailsControl.getInsertStatement(999, pd);
        s.executeUpdate(query);
        query=StudentControl.getInsertStatement(999, st);
        System.out.println("query "+query);
        s.executeUpdate(query);
        c.commit();
     } catch (SQLException ex) {
         System.out.println("!!!! message "+ex.getMessage());
         printErrorMessage(ex.getMessage());
            try {
                c.rollback();
                return 1;
            } catch (SQLException ex1) {
                Logger.getLogger(StudentRegBackend.class.getName()).log(Level.SEVERE, null, ex1);
            }
         Logger.getLogger(ParentDetailsControl.class.getName()).log(Level.SEVERE, null, ex);
     }
        return 0;
    }
    public static int addStudentToRecords(Student st,ParentDetails pd,Account ac, 
            int code){
        Connection c=null;
        Statement s=null;
        try {
        c=SqlConnect.getDatabaseConnection();
        c.setAutoCommit(false);
        s=c.createStatement();
        if (code == 6969)
            s.execute("SET FOREIGN_KEY_CHECKS = 0;");
        
        String query=AccountControl.getInsertStatement(999, ac);
        s.executeUpdate(query);
        query=ParentDetailsControl.getInsertStatement(999, pd);
        s.executeUpdate(query);
        query=StudentControl.getInsertStatement(code, st);
        System.out.println("query "+query);
        s.executeUpdate(query);
        if (code == 6969)
            s.execute("SET FOREIGN_KEY_CHECKS = 1;");
        c.commit();
     } catch (SQLException ex) {
         System.out.println("!!!! message "+ex.getMessage());
         printErrorMessage(ex.getMessage());
            try {
                c.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(StudentRegBackend.class.getName()).log(Level.SEVERE, null, ex1);
            }
         Logger.getLogger(ParentDetailsControl.class.getName()).log(Level.SEVERE, null, ex);
     }
        return 0;
    }
    public static int removeStudentFromRecords(Student st){
        try (Connection c = SqlConnect.getDatabaseConnection();
            Statement s = c.createStatement())
        {
            c.setAutoCommit(false);
            String q = StudentControl.getDeleteStatement(st.getRegistrationId());
             s.addBatch(q);
             q = AccountControl.getDeleteStatement(999, st.getUsername());
             s.addBatch(q);
             s.executeBatch();
             return 1;
        } 
        catch (SQLException ex) {
            Logger.getLogger(StudentRegBackend.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    public static int updateStudentDetails(String regId,Student st, String pId,
            ParentDetails pr){
        // returns 0 for error and 1 for success
        try (Connection c = SqlConnect.getDatabaseConnection();
            Statement s = c.createStatement())
        {
            c.setAutoCommit(false);
            String q = StudentControl.getUpdateStatement(st.getRegistrationId()
            , st);
            s.executeUpdate(q);
            q = ParentDetailsControl.getUpdateStatement(pId, pr);
            s.executeUpdate(q);
            c.commit();
            return 1;
        } 
        catch (SQLException ex) {
            Logger.getLogger(StudentRegBackend.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    public static int updateStudentDetails(String regId,Student st){
        // returns 0 for error and positive number for success
        try (Connection c = SqlConnect.getDatabaseConnection();
            Statement s = c.createStatement())
        {
            c.setAutoCommit(false);
            String q = StudentControl.getUpdateStatement(st.getRegistrationId()
            , st);
            return s.executeUpdate(q);
        } 
        catch (SQLException ex) {
            Logger.getLogger(StudentRegBackend.class.getName()).log(Level.SEVERE, null, ex);
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
