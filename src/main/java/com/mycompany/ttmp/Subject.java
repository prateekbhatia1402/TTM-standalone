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
public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;
    private String subjectId;
    private String subjectName;
    private int    lecturesRequired;

    public Subject(String subjectId) {
        this.subjectId = subjectId;
    }

    public Subject(String subjectId, String subjectName,int lecturesRequired) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.lecturesRequired=lecturesRequired;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getLecturesRequired() {
        return lecturesRequired;
    }

    public void setLecturesRequired(int lecturesRequired) {
        this.lecturesRequired = lecturesRequired;
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (subjectId != null ? subjectId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Subject)) {
            return false;
        }
        Subject other = (Subject) object;
        if ((this.subjectId == null && other.subjectId != null) || (this.subjectId != null && !this.subjectId.equals(other.subjectId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication4.Subject[ subjectId=" + subjectId + " ]";
    }
    
}
