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
public class ClassControl {
    private static String TABLE_NAME="class";
    public static String getInsertStatement(int id,I_Class clas){
        return String.format("insert into %s values('%s','%s',);",TABLE_NAME,clas.getId(),clas.getName());
    }
    public static String getSelectFullStatement(int id,String classId){
        return String.format("Select * from %s where id='%s';",TABLE_NAME,classId);
    }
    private static String getSelectFullStatement(String classId){
        return String.format("Select * from %s where id='%s';",TABLE_NAME,classId);
    }
    public static I_Class getClass(String classId){
        try(Connection con=SqlConnect.getDatabaseConnection()){
            String query=getSelectFullStatement(classId);
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery(query);
            if(rs.next())
            {
                String name=rs.getString(2);
                return new I_Class(classId,name);
            }
        }catch (SQLException ex) {
            Logger.getLogger(TimeTableControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
