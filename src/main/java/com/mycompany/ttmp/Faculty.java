/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ttmp;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Programming
 */
public class Faculty implements Serializable {
    public static String getAvailableID(){
         try(Connection c=SqlConnect.getDatabaseConnection()) {
                Statement s=c.createStatement();
                ResultSet rs=s.executeQuery("select max(substr(`faculty id`,2,9)) from `faculty`");
                rs.next();
                int val=rs.getInt(1)+1;
                String id=String.format("F%09d", val);
                return id;
                
            } catch (SQLException ex) {
                Logger.getLogger(ParentDetailsControl.class.getName()).log(Level.SEVERE, null, ex);
            }
           return null;
    }

    private static final long serialVersionUID = 1L;
    private String facultyId;
    private String name;
    private String email;
    private String permanentAddress;
    private String address;
    private Date dateOfBirth;
    private Date dateOfRegistration;
    private String mobileNumber;
    private char gender;
    private String bloodGroup;
    private String experence;
    private String subjectSpeciality;
    private String username;
    private Qualification[] qualifications;

    private Faculty(String facultyId) {
        this.facultyId = facultyId;
    }

    private Faculty(String facultyId, String name, String email, 
            String username,String permanentAddress, String address,
            Date dateOfBirth, Date dateOfRegistration, String mobileNumber, 
            char gender, String bloodGroup, String experence, String subjectSpeciality) {
        this.facultyId = facultyId;
        this.name = name;
        this.email = email;
        this.permanentAddress = permanentAddress;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.dateOfRegistration = dateOfRegistration;
        this.mobileNumber = mobileNumber;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.experence = experence;
        this.subjectSpeciality = subjectSpeciality;
        this.username=username;
    }
    
    private Faculty(String facultyId, String name, String email, 
            String username,String permanentAddress, String address,
            Date dateOfBirth, Date dateOfRegistration, String mobileNumber, 
            char gender, String bloodGroup, String experence,
            String subjectSpeciality,Qualification[] qualifications) {
        this.facultyId = facultyId;
        this.name = name;
        this.email = email;
        this.permanentAddress = permanentAddress;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.dateOfRegistration = dateOfRegistration;
        this.mobileNumber = mobileNumber;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.experence = experence;
        this.subjectSpeciality = subjectSpeciality;
        this.qualifications=qualifications;
        this.username=username;
    }
    public static Faculty createFaculty(String facultyId) {
        return new Faculty(facultyId);
    }

    public static Faculty createFaculty(String facultyId, String name, String email, 
            String username,String permanentAddress, String address,
            Date dateOfBirth, Date dateOfRegistration, String mobileNumber, 
            char gender, String bloodGroup, String experence, String subjectSpeciality
            ,Qualification[] qualifications) {
       return new Faculty(facultyId,name,email, 
            username,permanentAddress,address,
            dateOfBirth,dateOfRegistration,mobileNumber, 
            gender,bloodGroup,experence,subjectSpeciality,qualifications);
    }
    public static Faculty createFaculty(String facultyId, String name, String email, 
            String username,String permanentAddress, String address,
            Date dateOfBirth, Date dateOfRegistration, String mobileNumber, 
            char gender, String bloodGroup, String experence, String subjectSpeciality) {
       return new Faculty(facultyId,name,email, 
            username,permanentAddress,address,
            dateOfBirth,dateOfRegistration,mobileNumber, 
            gender,bloodGroup,experence,subjectSpeciality);
    }

    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
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
    public Qualification[] getQualifications() {
        return qualifications;
    }

    public void setQualifications(Qualification[] qualifications) {
        this.qualifications = qualifications;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(Date dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getExperence() {
        return experence;
    }

    public void setExperence(String experence) {
        this.experence = experence;
    }

    public String getSubjectSpeciality() {
        return subjectSpeciality;
    }

    public void setSubjectSpeciality(String subjectSpeciality) {
        this.subjectSpeciality = subjectSpeciality;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (facultyId != null ? facultyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Faculty)) {
            return false;
        }
        Faculty other = (Faculty) object;
        if ((this.facultyId == null && other.facultyId != null) || (this.facultyId != null && !this.facultyId.equals(other.facultyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication4.Faculty[ facultyId=" + facultyId + " ]";
    }
    
}
