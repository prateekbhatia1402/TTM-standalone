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
import javax.swing.JOptionPane;

/**
 *
 * @author Programming
 */
public class I_ClassControl {
    public static String getCLassName(String classId){
        try(Connection c=SqlConnect.getDatabaseConnection()) {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("select name from class where id='"+classId+"'");
            if(rs.first() == false)
                return null;
            return rs.getString(1);
        } catch (SQLException ex) {
            Logger.getLogger(I_ClassControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static int createClass(I_Class clas)
    {
        if (clas.getId() == null)
            clas.setId(getAvailableID());
         try(Connection c=SqlConnect.getDatabaseConnection()) 
         {
           Statement s = c.createStatement();
           String q = String.format("insert into class values('%s','%s',%d)",
                   clas.getId(),clas.getName(),clas.getRoomId());
           System.out.println(q);
          return s.executeUpdate(q);
         } catch (SQLException ex) {
            Logger.getLogger(I_ClassControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    
    public static int updateClass(String oldId, I_Class clas)
    {
        
         try(Connection c=SqlConnect.getDatabaseConnection()) 
         {
           Statement s = c.createStatement();
          // String q = "update `class_subject_faculty` set room"
           String q = !oldId.equalsIgnoreCase(clas.getId()) ?
                 String.format("update class set NAME='%s',"
                         + "`ROOM ID`=%d,ID='%s' where ID='%s'",
                   clas.getName(),clas.getRoomId(),clas.getId(),oldId)
                   :
                   String.format("update class set NAME='%s',`ROOM ID`=%d where ID='%s'",
                   clas.getName(),clas.getRoomId(),oldId);  
          return s.executeUpdate(q);
         } catch (SQLException ex) {
            Logger.getLogger(I_ClassControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
       public static String getAvailableID(){
         try(Connection c=SqlConnect.getDatabaseConnection()) {
                Statement s=c.createStatement();
                ResultSet rs=s.executeQuery("select max(substr(`id`,2,10)) from `class`");
                rs.next();
                int val=rs.getInt(1)+1;
                String id=String.format("C%05d", val);
                return id;
            } catch (SQLException ex) {
                Logger.getLogger(ParentDetailsControl.class.getName()).log(Level.SEVERE, null, ex);
            }
           return null;
    }
 
       public static int deleteClass(I_Class clas){
         try(Connection c=SqlConnect.getDatabaseConnection()) {
                Statement s=c.createStatement();
                String q = "delete from class where id='"+clas.getId()+"'";
                return s.executeUpdate(q);
            } catch (SQLException ex) {
                String errorMessage =ex.getMessage();
                System.out.println("ERROR MESSAGE : "+errorMessage);
                String illegalDeleteMessageStart = "Cannot delete or update a parent row: a foreign key constraint fails (`TTMPRATEEK`.";
                if (errorMessage.startsWith(illegalDeleteMessageStart))
                {
                 JOptionPane.showMessageDialog(null, "This class cannot be removed because it is active\n"
                         + "it's assigned to students , teachers and / or schedule exists for this class");   
                }
                Logger.getLogger(ParentDetailsControl.class.getName()).log(Level.SEVERE, null, ex);
            }
           return 0;
    }
       
    public static I_Class[] getAllCLass(){
        try (Connection c=SqlConnect.getDatabaseConnection()){
            I_Class classes[];
            Statement s=c.createStatement();
            ResultSet rs=s.executeQuery("select id,name,`room id` from class");
            if(rs.last()==false)
                return null;
            int count=rs.getRow();
            rs.first();
            classes=new I_Class[count];
            for(int i=0;i<count;i++,rs.next())
            {
                String id=rs.getString(1);
                String name=rs.getString(2);
                int room=rs.getInt(3);
                classes[i]=new I_Class(id,name,room);
            }
            return classes;
        } catch (SQLException ex) {
            Logger.getLogger(I_ClassControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }  
    public static I_Class[] searchClass(String param){
        try (Connection c=SqlConnect.getDatabaseConnection()){
            I_Class classes[];
            Statement s=c.createStatement();
            String q = "select id,name,`room id` from class where "
                +"id  like '%"+param+"%'"
                + " or name like '%"+param+
                "%' or `room id` like '%"+param+"%';";
            System.out.println("Q >>> "+q);
            ResultSet rs=s.executeQuery(q);
            if(rs.last()==false)
                return null;
            int count=rs.getRow();
            rs.first();
            classes=new I_Class[count];
            for(int i=0;i<count;i++,rs.next())
            {
                String id=rs.getString(1);
                String name=rs.getString(2);
                int room=rs.getInt(3);
                classes[i]=new I_Class(id,name,room);
            }
            return classes;
        } catch (SQLException ex) {
            Logger.getLogger(I_ClassControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
