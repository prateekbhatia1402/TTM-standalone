/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ttmp;

import java.io.Serializable;
/**
 *
 * @author Programming
 */
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private String role;

    private Account(String username) {
        this.username = username;
    }

    private Account(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public static Account createAccount(String username){
        return new Account(username);
    }
    
    public static Account createAccount(String username,String password,String role){
        return new Account(username,password,role);
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication4.Account[ username=" + username + " ]";
    }
    
}
