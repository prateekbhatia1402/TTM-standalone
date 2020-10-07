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
public class ClassSubjectFaculty implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String classId;
    private String facultyId;
    private String subjectId;
    private int roomId;

    public ClassSubjectFaculty() {
    }

    public ClassSubjectFaculty(Integer id) {
        this.id = id;
    }

    public ClassSubjectFaculty(Integer id, String classId, String facultyId, String subjectId, int roomId) {
        this.id = id;
        this.classId = classId;
        this.facultyId = facultyId;
        this.subjectId = subjectId;
        this.roomId = roomId;
    }
    public ClassSubjectFaculty( String classId, String facultyId, String subjectId, int roomId) {
        this.id = -5;
        this.classId = classId;
        this.facultyId = facultyId;
        this.subjectId = subjectId;
        this.roomId = roomId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }


    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
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
        if (!(object instanceof ClassSubjectFaculty)) {
            return false;
        }
        ClassSubjectFaculty other = (ClassSubjectFaculty) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication4.ClassSubjectFaculty[ id=" + id + " ]";
    }
    
}
