/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ttmp;

/**
 *
 * @author Programming
 */
public class AccountControl {

    private static String TABLE_NAME="account";
    public static String getInsertStatement(int id,Account ac){
        return String.format("insert into %s values('%s','%s','%s');",TABLE_NAME,ac.getUsername(),ac.getPassword(),ac.getRole());
    }
    public static String getDeleteStatement(int id,String username){
        return String.format("delete from %s where username='%s';",
                TABLE_NAME,username);
    }
    public static String getSelectFullStatement(int id,String username){
        return String.format("Select * from %s where username='%s';",TABLE_NAME,username);
    }
    public static String getUpdatePasswordStatement(int id, Account acc){
        return String.format("update %s set password ='%s' where username = '%s'",
                TABLE_NAME, acc.getPassword(), acc.getUsername());
    }
    
}
