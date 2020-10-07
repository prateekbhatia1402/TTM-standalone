package com.mycompany.ttmp;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
public class TTM {
    public static void main(String args[]){
         try {
           SqlConnect.getDatabaseConnection();
            new LoginF().setVisible(true);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toLowerCase());
            if(ex.getMessage().toLowerCase().equals(SqlConnect.UNKNOWN_DATABASE_ERROR_MESSAGE))
            {
                try {
                    SqlConnect.resetDatabase();
                } catch (SQLException ex1) {
                    Logger.getLogger(TTM.class.getName()).log(Level.SEVERE, null, ex1);
                }
                SqlConnect.fillDummyData();
                new LoginF().setVisible(true);
           
            }
            else Logger.getLogger(TTM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
