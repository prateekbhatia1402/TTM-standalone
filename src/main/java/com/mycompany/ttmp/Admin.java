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
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String email;
    private String phoneNumber;
    private String username;
    private String type;

    private Admin(Integer id) {
        this.id = id;
    }

    private Admin(String username) {
        this.username = username;
    }
    private Admin(Integer id, String name, String email, String phoneNumber, String username, String type) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.type = type;
    }
    private Admin( String name, String email, String phoneNumber, String username, String type) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.type = type;
    }

    public static Admin createAdmin(Integer id) {
        return new Admin(id);
    }

    public static Admin createAdmin(String username) {
        return new Admin(username);
    }
    public static Admin createAdmin(Integer id, String name, String email, String phoneNumber, String username, String type) {
        return new Admin(id,name,email,phoneNumber,username,type);
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Admin)) {
            return false;
        }
        Admin other = (Admin) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication4.Admin[ id=" + id + " ]";
    }
    
}
