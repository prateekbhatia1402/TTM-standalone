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
public class LoginControl {
    
    public static int NO_ISSUE=0;
    public static int NO_RECORD_FOUND=1;
    public static int INVALID_PASSWORD=2;
    public static int CONNECTION_ISSUE=3;
    public static Object[] login(String username,String password){
        Account account=null;
        String query=AccountControl.getSelectFullStatement(999, username);
        try (Connection con=SqlConnect.getDatabaseConnection()){
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery(query);
            if(rs.next()){
                String dpassword=rs.getString("password");
                if(dpassword.equals(password)==false)
                {
                    return new Object[]{account,INVALID_PASSWORD};
                }
                String role=rs.getString("role");
                account=Account.createAccount(username,password,role);
                return new Object[]{account,NO_ISSUE};
            }
            else{
                return new Object[]{account,NO_RECORD_FOUND};
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);
            return new Object[]{null,CONNECTION_ISSUE};
        }
    }
    
    public static String getErrorMessage(int errorCode){
        String message="";
        switch(errorCode){
            case 1:message="INVALID USERNAME";break;
            case 2:message="INVALID PASSWORD";break;
            case 3:message="CONNECTION ISSUE , TRY AGAIN LATER";break;
            case 4:message="";break;
            default: message="SOMETHING WENT WRONG , TRY AGAIN LATER";
        }
        return message;
    }
}
