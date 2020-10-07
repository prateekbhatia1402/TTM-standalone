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
public class AdminControl {

    private static final String TABLE_NAME="admin";
    public static String getInsertStatement(int id,Admin ad){
        if(id==999)
            return String.format("insert into %s values(%s,'%s','%s','%s','%s','%s');",TABLE_NAME,ad.getId(),ad.getName(),ad.getEmail(),ad.getPhoneNumber(),ad.getUsername(),ad.getType());
        else return null;
    }
    public static String getSelectFullStatement(int id,String userid,char type){
        String ans=null;
        if(id==999)
            if(type=='u')ans=String.format("Select * from %s where username='%s';",TABLE_NAME,userid);
            else if (type=='i')ans=String.format("Select * from %s where id=%s;",TABLE_NAME,userid);
        return ans;
    }
    private static String getSelectFullStatement(String userid,char type){
        String ans=null;
            if(type=='u')ans=String.format("Select * from %s where username='%s';",TABLE_NAME,userid);
            else if (type=='i')ans=String.format("Select * from %s where id=%s;",TABLE_NAME,userid);
        return ans;
    }
    public static Admin getAdmin(String userid,char type){
        Admin admin=null;
        String query=getSelectFullStatement(userid,type);
        try {
            try (Connection con = SqlConnect.getDatabaseConnection()) {
                Statement st=con.createStatement();
                ResultSet rs=st.executeQuery(query);
                if(rs.next())
                {
                    String name=rs.getString("NAME");
                    String email=rs.getString("EMAIL");
                    String mobile=rs.getString("PHONE NUMBER");
                    String atype=rs.getString("TYPE");
                    String username=rs.getString("USERNAME");
                    int id=rs.getInt("ID");
                    admin=Admin.createAdmin(id,name,email,mobile,username,atype);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return admin;
    }    
}
