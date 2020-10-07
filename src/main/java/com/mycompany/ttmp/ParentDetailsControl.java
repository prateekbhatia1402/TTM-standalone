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
 * @author User
 */
public class ParentDetailsControl {
 private static final String TABLE_NAME="`parent details`";
 private static final String[] COLUMN_NAMES_PLN = 
 {
    "`PARENT ID`","`STUDENT REGISTRATION ID`","`FATHER NAME`",
    "`FATHER EMAIL ID`","`FATHER MOBILE NUMBER`","`FATHER DOB`",
    "`MOTHER NAME`","`MOTHER EMAIL`","`MOTHER MOBILE NUMBER`",
    "`MOTHER DOB`","`FAMILY INCOME`"
 };
 private static final String[] COLUMN_NAMES = 
 {
   "`"+COLUMN_NAMES_PLN[0]+  "`",
   "`"+COLUMN_NAMES_PLN[1]+  "`",
   "`"+COLUMN_NAMES_PLN[2]+  "`",
   "`"+COLUMN_NAMES_PLN[3]+  "`",
   "`"+COLUMN_NAMES_PLN[4]+  "`",
   "`"+COLUMN_NAMES_PLN[5]+  "`",
   "`"+COLUMN_NAMES_PLN[6]+  "`",
   "`"+COLUMN_NAMES_PLN[7]+  "`",
   "`"+COLUMN_NAMES_PLN[8]+  "`",
   "`"+COLUMN_NAMES_PLN[9]+  "`",
   "`"+COLUMN_NAMES_PLN[10]+  "`",
 };
    public static String getInsertStatement(int id,ParentDetails pd){
        if(id==999)
        {   
            String fatherDob=pd.getFatherDob().toPlainString();
            System.out.println(fatherDob+" ::");
            String motherDob=pd.getMotherDob().toPlainString();
            System.out.println(fatherDob+" "+motherDob);
            String query=String.format("insert into %s values('%s','%s','%s','%s'"
                    + ",'%s','%s','%s','%s','%s','%s','%s');"
        ,TABLE_NAME,pd.getParentId(),pd.getStudent().getRegistrationId()
        ,pd.getFatherName(),pd.getFatherEmailId(),pd.getFatherMobileNumber()
        ,fatherDob,pd.getMotherName(),pd.getMotherEmail(),pd.getMotherMobileNumber(),
        motherDob,pd.getFamilyIncome());
            System.out.println(query);
        return query;
         }
        else return null;
    }
    
    
    public static String getUpdateStatement(String oldId,ParentDetails pr){
          /*
        
    "`PARENT ID`","`STUDENT REGISTRATION ID`","`FATHER NAME`",
    "`FATHER EMAIL ID`","`FATHER MOBILE NUMBER`","`FATHER DOB`",
    "`MOTHER NAME`","`MOTHER EMAIL`","`MOTHER MOBILE NUMBER`",
    "`MOTHER DOB`","`FAMILY INCOME`"
        */
       String query=String.format("update %s set %s='%s', %s='%s', %s='%s',%s='%s'"
               +", %s='%s', %s='%s', %s='%s', %s='%s', %s='%s' where %s='%s'"
        ,TABLE_NAME,COLUMN_NAMES[2], pr.getFatherName(),
        COLUMN_NAMES[3], pr.getFatherEmailId(),
        COLUMN_NAMES[4], pr.getFatherMobileNumber(),
        COLUMN_NAMES[5], pr.getFatherDob(),
        COLUMN_NAMES[6], pr.getMotherName(),
        COLUMN_NAMES[7], pr.getMotherEmail(),
        COLUMN_NAMES[8], pr.getMotherMobileNumber(),
        COLUMN_NAMES[9], pr.getMotherDob(),
        COLUMN_NAMES[10], pr.getFamilyIncome(),
        COLUMN_NAMES[0], oldId);
            System.out.println(query);
        return query;
    }
    public static String getSelectFullStatement(int id,String parentID){
        if(id==999)return String.format("Select * from %s where %s='%s';",
                TABLE_NAME,COLUMN_NAMES[0],parentID);
        return null;
    }
    private static String getSelectFullStatement(String parentID){
        return String.format("Select * from %s where %s='%s';",
                TABLE_NAME,COLUMN_NAMES[0],parentID);
        
    }
    public static ParentDetails getParentDetails(String parentId){
        ParentDetails parentDetails=null;
        String query=getSelectFullStatement(parentId);
        try (Connection con=SqlConnect.getDatabaseConnection()){
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery(query);
            /*
            
    "`PARENT ID`","`STUDENT REGISTRATION ID`","`FATHER NAME`",
    "`FATHER EMAIL ID`","`FATHER MOBILE NUMBER`","`FATHER DOB`",
    "`MOTHER NAME`","`MOTHER EMAIL`","`MOTHER MOBILE NUMBER`",
    "`MOTHER DOB`","`FAMILY INCOME`"
            */
            if(rs.next())
            {
                String fatherName=rs.getString(COLUMN_NAMES_PLN[2]);
                String fatherEmail=rs.getString(COLUMN_NAMES_PLN[3]);
                String fatherMobile=rs.getString(COLUMN_NAMES_PLN[4]);
                Date fatherDob=new Date(rs.getDate(COLUMN_NAMES_PLN[5]));
                String motherName=rs.getString(COLUMN_NAMES_PLN[6]);
                String motherEmail=rs.getString(COLUMN_NAMES_PLN[7]);
                String motherMobile=rs.getString(COLUMN_NAMES_PLN[8]);
                Date motherDob=new Date(rs.getDate(COLUMN_NAMES_PLN[8]));
                String familyIncome=rs.getString(COLUMN_NAMES_PLN[10]);
                parentDetails=ParentDetails.createParentDetails(parentId, fatherName,
                        fatherEmail, fatherMobile, fatherDob,motherName,
                        motherEmail, motherMobile,motherDob, familyIncome,null);
                return parentDetails;
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentControl.class.getName()).log(Level.SEVERE, null, ex);
        }
       return parentDetails;
    }
    public static ParentDetails getParentDetails(Student student){
       return getParentDetails(student.getParentId());
    }
    
    public static int addParentToRecord(ParentDetails pd){
        String query=getInsertStatement(999, pd);
     try(Connection c=SqlConnect.getDatabaseConnection()) {
         Statement s=c.createStatement();
         s.executeUpdate(query);
     } catch (SQLException ex) {
         Logger.getLogger(ParentDetailsControl.class.getName()).log(Level.SEVERE, null, ex);
     }
        return 0;
    }
}
