/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ttmp;

import java.io.Serializable;
import java.util.Collection;
/**
 *
 * @author Programming
 */
public class I_Class implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private int roomId;
    private boolean isAssigned=false;
    private Collection<Student> studentCollection;


    public I_Class(String id) {
        this.id = id;
    }

    public I_Class(String id, String name,int roomId) {
        this.id = id;
        this.name = name;
        this.roomId=roomId;
    }
    public I_Class(String id, String name) {
        this.id = id;
        this.name = name;
    }
    public I_Class(String id, String name,boolean isAssigned) {
        this.id = id;
        this.name = name;
        this.isAssigned=isAssigned;
    }
    public I_Class(String id, String name,int roomId,boolean isAssigned) {
        this.id = id;
        this.name = name;
        this.isAssigned=isAssigned;
        this.roomId=roomId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    
    public boolean getAssignmentStatus() {
        return isAssigned;
    }

    public void setAssignmentStatusAs(boolean isAssigned) {
        this.isAssigned = isAssigned;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Student> getStudentCollection() {
        return studentCollection;
    }

    public void setStudentCollection(Collection<Student> studentCollection) {
        this.studentCollection = studentCollection;
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
        if (!(object instanceof I_Class)) {
            return false;
        }
        I_Class other = (I_Class) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication4.Class[ id=" + id + " ]";
    }
    
}
