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
public class TimeSlot implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String from;
    private String to;

    public TimeSlot() {
    }

    public TimeSlot(String id) {
        this.id = id;
    }

    public TimeSlot(String id, String from, String to) {
        this.id = id;
        this.from = from;
        this.to = to;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
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
        if (!(object instanceof TimeSlot)) {
            return false;
        }
        TimeSlot other = (TimeSlot) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return from.substring(0,2)+":"+from.substring(2,4)+" - "+to.substring(0,2)+":"+to.substring(2,4);
    }
    public String toSimpleString() {
        int hour=Integer.valueOf(from.substring(0,2));
        String aOrP=hour>11 ?" AM" :" PM";
        hour=hour>12?hour-12:hour;
        String startFrom=hour+":"+from.substring(2,4);
        startFrom+=aOrP;
        hour=Integer.valueOf(to.substring(0,2));
        aOrP=hour>11 ?" AM" :" PM";
        hour=hour>12?hour-12:hour;
        String endAt=hour+":"+to.substring(2,4);
        aOrP=Integer.valueOf(to)>1199 ?" AM" :" PM";
        endAt+=aOrP;
        return startFrom+" - "+endAt;
    }
    
}
