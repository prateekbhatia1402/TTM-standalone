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

CREATE TABLE `student` (
  `REGISTRATION ID` varchar(6) NOT NULL,
  `STUDENT NAME` varchar(20) NOT NULL,
  `EMAIL` varchar(35) NOT NULL,
  `USERNAME` varchar(20) NOT NULL,
  `mobile number` varchar(10) DEFAULT NULL,
  `CLASS` varchar(6) DEFAULT NULL,
  `PERMANENT Address` varchar(60) NOT NULL,
  `CORR. ADDRESS` varchar(60) NOT NULL,
  `ROLL NUMBER` int NOT NULL,
  `DATE OF BIRTH` date NOT NULL,
  `date of registration` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `GENDER` char(1) NOT NULL,
  `BLOOD GROUP` varchar(3) DEFAULT NULL,
  `PARENT ID` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`REGISTRATION ID`),
  KEY `USERNAME` (`USERNAME`),
  KEY `PARENT ID` (`PARENT ID`),
  CONSTRAINT `student_ibfk_1` FOREIGN KEY (`USERNAME`) REFERENCES `account` (`USERNAME`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `student_ibfk_2` FOREIGN KEY (`PARENT ID`) REFERENCES `parent details` (`PARENT ID`) ON DELETE CASCADE
);
 */
public class StudentControl {
    private static final String TABLE_NAME="`student`";
    private static final String[] COLUMN_NAMES_PLN =
    { 
        "REGISTRATION ID",
        "STUDENT NAME",
        "EMAIL",
        "USERNAME",        
        "MOBILE NUMBER",
        "CLASS",
        "PERMANENT Address",
        "CORR. ADDRESS",
        "ROLL NUMBER",
        "DATE OF BIRTH",
        "DATE OF REGISTRATION",
        "GENDER",
        "BLOOD GROUP",
        "PARENT ID"  
    };
    private static final String[] COLUMN_NAMES =
    { 
    "`"+COLUMN_NAMES_PLN[0]+"`",
    "`"+COLUMN_NAMES_PLN[1]+"`",
    "`"+COLUMN_NAMES_PLN[2]+"`",
    "`"+COLUMN_NAMES_PLN[3]+"`",
    "`"+COLUMN_NAMES_PLN[4]+"`",
    "`"+COLUMN_NAMES_PLN[5]+"`",
    "`"+COLUMN_NAMES_PLN[6]+"`",
    "`"+COLUMN_NAMES_PLN[7]+"`",
    "`"+COLUMN_NAMES_PLN[8]+"`",
    "`"+COLUMN_NAMES_PLN[9]+"`",
    "`"+COLUMN_NAMES_PLN[10]+"`",
    "`"+COLUMN_NAMES_PLN[11]+"`",
    "`"+COLUMN_NAMES_PLN[12]+"`",
    "`"+COLUMN_NAMES_PLN[13]+"`",
    };
    public static String getInsertStatement(int id,Student st){
        if(id==999 || id == 6969)
        {  
            String clas=null;
            if(st.getClass1()!=null)
                clas=st.getClass1().getId();
            System.out.println(clas);
            String dor = id != 6969 ? "null" : st.getDateOfRegistration().toPlainString();
            String query=String.format("insert into %s(%s) values('%s','%s','%s','%s',"
                + "'%s','%s','%s',%d,'%s','%s','%c','%s','%s','%s');"
        ,TABLE_NAME,COLUMN_NAMES[0]+","+COLUMN_NAMES[1]+","+COLUMN_NAMES[3]+","+
                COLUMN_NAMES[2]+","+COLUMN_NAMES[4]+","+COLUMN_NAMES[6]+","+COLUMN_NAMES[7]+
                    ","+COLUMN_NAMES[8]+","+COLUMN_NAMES[9]+"," + COLUMN_NAMES[10]
      +"," +COLUMN_NAMES[11]+","+COLUMN_NAMES[12]+","+COLUMN_NAMES[13]
               +","+COLUMN_NAMES[5]  
        ,st.getRegistrationId(),st.getStudentName(),st.getUsername()
        ,st.getEmail(),st.getMobileNumber(),
        st.getPermanentAddress(),st.getAddress(),st.getRollNumber(),
        st.getDateOfBirth().toPlainString(), dor,
        st.getGender(),st.getBloodGroup(),st.getParentId(), clas);
            System.out.println(query);
        return query;
         }
        else return null;
    }
    
    public static String getUpdateStatement(String oldId,Student st){
          /*
        
    "`REGISTRATION ID`","`STUDENT NAME`","`EMAIL`","`USERNAME`",
    "`MOBILE NUMBER`","`CLASS`","`PERMANENT Address`",
    "`CORR. ADDRESS`","`ROLL NUMBER`","`DATE OF BIRTH`",
    "`DATE OF REGISTRATION`","`GENDER`","`BLOOD GROUP`",
    "`PARENT ID`"  
        */
            String clas=null;
            if(st.getClass1()!=null)
                clas = st.getClass1().getId();
            System.out.println(clas);
            String query=String.format("update %s set %s='%s', %s='%s'"
                    +    "%s='%s', %s='%s, %s='%s', %s='%s', %s=%d, %s='%s', %s='%s', "
                    + "%s='%c', %s = '%s', %s = '%s' where %s = '%s';"
        ,TABLE_NAME,COLUMN_NAMES[1],st.getStudentName(),COLUMN_NAMES[3],st.getUsername(),
        COLUMN_NAMES[2],st.getEmail(),COLUMN_NAMES[4],st.getMobileNumber(),
        COLUMN_NAMES[6],st.getPermanentAddress(),COLUMN_NAMES[7],
        st.getAddress(),COLUMN_NAMES[8],st.getRollNumber(),COLUMN_NAMES[9],
        st.getDateOfBirth().toPlainString(),COLUMN_NAMES[10],
        st.getDateOfRegistration().toPlainString(),COLUMN_NAMES[11],st.getGender(),
        COLUMN_NAMES[12],st.getBloodGroup(),COLUMN_NAMES[5],clas,COLUMN_NAMES[0],
        oldId);
            System.out.println(query);
        return query;
    }
    
    private static String getSelectFullStatement(String regId){
        return String.format("Select * from %s where %s='%s';"
                ,TABLE_NAME,COLUMN_NAMES[0],regId);
        
    }
    private static String getSelectFullStatement(String param,char type){
        String columnName=type=='u'?COLUMN_NAMES[3]:COLUMN_NAMES[0];
        return String.format("Select * from %s where %s='%s';",TABLE_NAME,columnName,param);
        
    }
    private static String getSelectAllRecordsStatement(){
        return String.format("Select * from %s;",TABLE_NAME);
    }
    private static String getSelectSpecificRecStat(String param){
        return "Select * from "+TABLE_NAME+" where"
                + COLUMN_NAMES[0] + "  like '%"+param+"%'"
                + " or "+COLUMN_NAMES[1]+" like '%"+param+"%' or "
                + COLUMN_NAMES[5]+" like '%"+param+"%';";
        
    }
    private static String getSelectSpecificRecColumnStat(String param,String column){
        return "Select * from "+TABLE_NAME+" where cast("+column+" as char)like '%"+param+"%'";        
    }
    
    private static Student[] doSearch(String query){
        Student[] students=null;
         try(Connection con=SqlConnect.getDatabaseConnection()){
            Statement st=con.createStatement();
            System.out.println(query);
            ResultSet rs=st.executeQuery(query);
            if(rs.last()==false)
                return null;
            int count=rs.getRow();
            students=new Student[count];
            rs.first();
            int i=0;
            do{
                students[i] = getStudentFromResultSet(rs);
                i++;
            }
            while(rs.next());
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return students;
    }
    
    public static Student[] search(String param){
        String query=getSelectSpecificRecStat(param);
        return doSearch(query);
    }
    public static Student[] search(String param,String column){
        String query=getSelectSpecificRecColumnStat(param,column);
        return doSearch(query);
    }
    public static Student[] search(){
        String query=getSelectAllRecordsStatement();
        return doSearch(query);
    }
    
    public static String getUpdateParentStatement(int id,String regId,String parentId){
        if(id==999)return String.format("update %s set %s='%s' where "
                + "%s='%s';",TABLE_NAME,COLUMN_NAMES[13],parentId,
                COLUMN_NAMES[0],regId);
        return null;
    }
    public static String getDeleteStatement(String regId){
        return String.format("DELETE FROM %s WHERE %s='%s'",TABLE_NAME,
                COLUMN_NAMES[0],regId);
    }
    
    public static Student getStudent(String  regId){
        return getStudentDetails(regId, 'i');
    }
    public static Student getStudentViaUsername(String  username){
        return getStudentDetails(username, 'u');
    }
    private static Student getStudentDetails(String param, char type)
    {
        Student student=null;
        String query=getSelectFullStatement(param,type);
        try(Connection con=SqlConnect.getDatabaseConnection()) {
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery(query);
            if(rs.next())
            {
                student = getStudentFromResultSet(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return student;
    }
    
    private static Student getStudentFromResultSet(ResultSet rs)    
    {
        Student student = null;
        try{
                String regId=rs.getString(COLUMN_NAMES_PLN[0]);
                String name=rs.getString(COLUMN_NAMES_PLN[1]);
                String email=rs.getString(COLUMN_NAMES_PLN[2]);
                String username=rs.getString(COLUMN_NAMES_PLN[3]);
                String mobile=rs.getString(COLUMN_NAMES_PLN[4]);
                String pAddress=rs.getString(COLUMN_NAMES_PLN[6]);
                String cAddress=rs.getString(COLUMN_NAMES_PLN[7]);
                int rollNo=rs.getInt(COLUMN_NAMES_PLN[8]);
                Date dob=new Date(rs.getDate(COLUMN_NAMES_PLN[9]));
                Date dor=new Date(rs.getDate(COLUMN_NAMES_PLN[10]));
                char gender=rs.getString(COLUMN_NAMES_PLN[11]).charAt(0);
                String bloodGroup=rs.getString(COLUMN_NAMES_PLN[12]);
                String parentId=rs.getString(COLUMN_NAMES_PLN[13]);
                String classId=rs.getString(COLUMN_NAMES_PLN[5]);
                student = Student.createStudent(regId,name,username,email,mobile,pAddress,cAddress,rollNo
                        ,dob,dor,gender,bloodGroup,parentId,classId);
        }
        catch(SQLException ex){
            Logger.getLogger(StudentControl.class.getName()).log(Level.SEVERE, null, ex);    
        }
        return student;
    }
}
