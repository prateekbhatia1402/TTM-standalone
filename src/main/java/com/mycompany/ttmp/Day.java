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
public class Day implements Serializable {

    private static final long serialVersionUID = 1L;
    private String dayId;
    private String dayName;
    private int dayNumber;


    public Day(String dayId) {
        this.dayId = dayId;
    }

    public Day(String dayId, String dayName, int dayNumber) {
        this.dayId = dayId;
        this.dayName = dayName;
        this.dayNumber = dayNumber;
    }

    public String getDayId() {
        return dayId;
    }

    public void setDayId(String dayId) {
        this.dayId = dayId;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dayId != null ? dayId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Day)) {
            return false;
        }
        Day other = (Day) object;
        if ((this.dayId == null && other.dayId != null) || (this.dayId != null && !this.dayId.equals(other.dayId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ttmprateek.Day[ dayId=" + dayId + " ]";
    }
    
}
