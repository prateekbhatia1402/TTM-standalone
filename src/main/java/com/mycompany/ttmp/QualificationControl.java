/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ttmp;

import java.awt.Cursor;
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
public class QualificationControl {
    
    private static final String TABLE_NAME="qualification";
    private static final int QUAL_CODE=1223;
    public static String getInsertStatement(int id,Faculty f){
        if(id==999)
        {
            Qualification[] qualifications=f.getQualifications();
            String fid=f.getFacultyId();
            int count=qualifications.length;
            switch (count) {
                case 0:
                    return null;
                case 1:
                    return String.format("insert into %s values('%s','%s','%s','%s',%f);"
                            ,TABLE_NAME,fid,qualifications[0].getDegree(),
                            qualifications[0].getYear(),qualifications[0].getInstitute(),
                            qualifications[0].getPercentage());
                default:
                    String query=String.format("insert into %s values ",TABLE_NAME);
                    for(int i=0;i<count;i++)
                    {
                        query+=String.format("('%s','%s','%s','%s','%f')",
                                fid,qualifications[i].getDegree(),
                                qualifications[i].getYear(),
                                qualifications[i].getInstitute(),
                                qualifications[i].getPercentage());
                        query+=(count-1==i)?";":",";
                    }
                    return query;
            }
        }
        return null;//String.format("insert into %s values('%s','%s','%s');",TABLE_NAME,f.getUsername(),f.getPassword(),f.getRole());
    }
    public static String getDeleteStatement(int id,Faculty f)
    {
        if(id == 999)
            return "delete from "+TABLE_NAME+" where `FACULTY ID`='"+
                    f.getFacultyId()+"'";
        return "";
    }
    
    private static String getSelectFullStatement(String facultyId){
        return String.format("Select * from %s where `FACULTY ID`='%s';",TABLE_NAME,facultyId);
    }
    public static Qualification[] getQualifications(int code,String facultyId){
        if(code!=QUAL_CODE)
            return null;
        String query=getSelectFullStatement(facultyId);
        try(Connection con=SqlConnect.getDatabaseConnection()) {
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery(query);
            if(rs.last()==false)
                return null;
            int count=rs.getRow();
            Qualification[] qualifications=new Qualification[count];
            rs.first();
            int i=0;
            do{
                String degree=rs.getString("DEGREE");
                String year=rs.getString("YEAR");
                String institute=rs.getString("INSTITUTE");
                float percent=rs.getFloat("PERCENTAGE");
                qualifications[i]=Qualification.createQualification(degree,year,
                        institute, percent);
                i++;
            }
            while(rs.next());
            return qualifications;
        } catch (SQLException ex) {
            Logger.getLogger(QualificationControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
