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
public class SubjectControl {
  public static Subject[] getAllSubject(){
       Subject[] subjects=null;
        try(Connection con=SqlConnect.getDatabaseConnection()) {
            /*
            
    `SUBJECT ID` VARCHAR(10) NOT NULL,
    `SUBJECT NAME` VARCHAR(25) NOT NULL,
    `LECTURES REQUIRED` INTEGER,
            */
            Statement st=con.createStatement();
            String query="select * from subject";
            ResultSet rs=st.executeQuery(query);
            if(rs.last()==false)
                return null;
            int count=rs.getRow();
            rs.first();
            subjects=new Subject[count];
            for(int i=0;i<count;i++,rs.next()){
                String subId=rs.getString("subject id");
                String subName=rs.getString("subject name");
                int lectRequired=rs.getInt("lectures required");
                subjects[i]=new Subject(subId,subName,lectRequired);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassroomManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return subjects;
    }   
}
