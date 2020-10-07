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
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private int sittingCapacity;
    private boolean assigned = false;

    public Room() {
    }

    public Room(Integer id) {
        this.id = id;
    }

    public Room(Integer id, int sittingCapacity) {
        this.id = id;
        this.sittingCapacity = sittingCapacity;
    }

    public Room(Integer id, int sittingCapacity, boolean assigned) {
        this.id = id;
        this.sittingCapacity = sittingCapacity;
        this.assigned = assigned;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }


    public int getSittingCapacity() {
        return sittingCapacity;
    }

    public void setSittingCapacity(int sittingCapacity) {
        this.sittingCapacity = sittingCapacity;
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
        if (!(object instanceof Room)) {
            return false;
        }
        Room other = (Room) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication4.Room[ id=" + id + " ]";
    }
    
}
