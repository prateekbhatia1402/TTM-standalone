/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ttmp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author Programming
 */
public class ClassroomManager {
    public static I_Class[] getUnassignedClasses_Fac(){
        I_Class[] classes=null;
        try (Connection con=SqlConnect.getDatabaseConnection()){
            
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select id,name from class where id not in"
                    + " (select distinct `class id` from class_subject_faculty)");
            if(rs.last()==false)
                return null;
            int count=rs.getRow();
            rs.first();
            classes=new I_Class[count];
            for(int i=0;i<count;i++,rs.next()){
                String id=rs.getString("id");
                String name=rs.getString("name");
                classes[i]=new I_Class(id,name);
            }
                    
        } catch (SQLException ex) {
            Logger.getLogger(TimeTableControl.class. getName()).log(Level.SEVERE, null, ex);
        }
        return classes;
    }
    
    public static void assignFaculty(I_Class selClass,String facultyId,String subId,int roomId)
    {
        try(Connection con=SqlConnect.getDatabaseConnection()) {
            
            Statement st=con.createStatement();
            st.executeQuery(String.format("insert into class_subject_faculty(`class id`,`faculty id`,"
                    + "`subject id`,'room id') values('%s','%s','%s',%d)",selClass.getId(),facultyId,subId,roomId));
        } catch (SQLException ex) {
            Logger.getLogger(ClassroomManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    public static boolean isAssigned(String classId)
    {
        try(Connection con = SqlConnect.getDatabaseConnection();)
        {
            PreparedStatement st = con.prepareStatement("SELECT EXISTS("
                    + "select `class id` from class_subject_faculty where `class id` = ?)");
            st.setString(1, classId);
            ResultSet rs = st.executeQuery();
            rs.next();
            return rs.getBoolean(1);
        } catch (SQLException ex) {
            Logger.getLogger(ClassroomManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static void addToRecords(ClassSubjectFaculty[] records){
        Connection con=null;
        if(records==null || records.length<1)
            return;
        try {
            con=SqlConnect.getDatabaseConnection();
            con.setAutoCommit(false);
            Statement st=con.createStatement();
            String classId = records[0].getClassId();
            st.executeUpdate("delete from schedule where `class id`='"+classId+"' "
                    + "and status='incoming'");
            int res = st.executeUpdate("update schedule set status = 'inactive', `to`=curdate()"
                    + " where `class id`='"+classId+"' "
                    + "and (status='outgoing' or status='active')");
            st.executeUpdate("delete from class_subject_faculty where `class id`='"
                    + classId +"'");
            for(ClassSubjectFaculty record:records){
                st.executeUpdate(String.format("insert into class_subject_faculty(`class id`,`faculty id`,"
                        + "`subject id`,`room id`) values('%s','%s','%s',%d)",record.getClassId(),
                        record.getFacultyId(),record.getSubjectId(),record.getRoomId()));
            }
            con.commit();
            JOptionPane.showMessageDialog(null, "Records saved successfully");
            if (res > 0)
            {
                JOptionPane.showMessageDialog(null, "Time Table of this class is discontinued\n"
                        + "Kindly create a new one", "Create Time Table", 
                        JOptionPane.INFORMATION_MESSAGE);
                var timeTables = new TimeTables(classId);
                new TTCreation(timeTables, timeTables.latest).setVisible(true);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Something went wrong");
            try {
                con.rollback();
                
            } catch (SQLException ex1) {
                Logger.getLogger(ClassroomManager.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(ClassroomManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClassroomManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static Object[][] getRecords(String classId){
        Object[][] records=null;
        try(Connection con=SqlConnect.getDatabaseConnection()){
            Statement st=con.createStatement();
            String query="select `class id`,csf.`faculty id`,"
                    + "f.`name`,csf.`subject id`,s.`subject name`,csf.`room id`"
                    + " from class_subject_faculty csf,subject s,faculty f where `class id`"
                    + "='"+classId+"' and csf.`faculty id`=f.`faculty id`"
                            + " and csf.`subject id`=s.`subject id`";
            System.out.println(query);
            ResultSet rs=st.executeQuery(query);
            if(!rs.last())
                return null;
            int count=rs.getRow();
            records= new Object[count][];
            rs.first();
            for(int i=0;i<count;i++,rs.next()){
                String cId=rs.getString(1);
                String facultyId=rs.getString(2);
                String facultyName=rs.getString(3);
                String subjectId=rs.getString(4);
                String subjectName=rs.getString(5);
                int roomId=rs.getInt(6);
                records[i]=new Object[]{cId,facultyId,facultyName,subjectId,subjectName,roomId};
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassroomManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return records;
    }
}
