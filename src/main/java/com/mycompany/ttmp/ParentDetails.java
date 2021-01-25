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
public class ParentDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    private String parentId;
    private String fatherName;
    private String fatherEmailId;
    private String fatherMobileNumber;
    private Date fatherDob;
    private String motherName;
    private String motherEmail;
    private String motherMobileNumber;
    private Date motherDob;
    private String familyIncome;
    private Student student;


    private ParentDetails(String parentId) {
        this.parentId = parentId;
    }

    private ParentDetails(String parentId, String fatherName, 
            String fatherEmailId, String fatherMobileNumber,Date fatherDob , 
            String motherName, String motherEmail, String motherMobileNumber, 
            Date motherDob,String familyIncome,Student student) {
        this.parentId = parentId;
        this.fatherName = fatherName;
        this.fatherEmailId = fatherEmailId;
        this.fatherMobileNumber = fatherMobileNumber;
        this.fatherDob=fatherDob;
        this.motherName = motherName;
        this.motherEmail = motherEmail;
        this.motherMobileNumber = motherMobileNumber;
        this.motherDob=motherDob;
        this.familyIncome=familyIncome;
        this.student=student;
    }

    public static ParentDetails createParentDetails(String parentId) {
        return new ParentDetails(parentId);
    }

    public static ParentDetails createParentDetails(String parentId, String fatherName, String fatherEmailId, String fatherMobileNumber,Date fatherDob , String motherName, String motherEmail, String motherMobileNumber, Date motherDob,String familyIncome,Student student) {
        return new ParentDetails( parentId, fatherName,  fatherEmailId, fatherMobileNumber,   fatherDob, motherName,  motherEmail, motherMobileNumber, motherDob,familyIncome,student);
    }
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFatherEmailId() {
        return fatherEmailId;
    }

    public void setFatherEmailId(String fatherEmailId) {
        this.fatherEmailId = fatherEmailId;
    }

    public String getFatherMobileNumber() {
        return fatherMobileNumber;
    }

    public void setFatherMobileNumber(String fatherMobileNumber) {
        this.fatherMobileNumber = fatherMobileNumber;
    }
    
    public Date getFatherDob() {
        return fatherDob;
    }

    public void setFatherDob(Date date){
        this.fatherDob=date;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }


    public String getMotherEmail() {
        return motherEmail;
    }

    public void setMotherEmail(String motherEmail) {
        this.motherEmail = motherEmail;
    }

    public String getMotherMobileNumber() {
        return motherMobileNumber;
    }

    public void setMotherMobileNumber(String motherMobileNumber) {
        this.motherMobileNumber = motherMobileNumber;
    }

    public Date getMotherDob() {
        return motherDob;
    }

    public void setMotherDob(Date date){
        this.motherDob=date;
    }
    public String getFamilyIncome() {
        return familyIncome;
    }

    public void setFamilyIncome(String familyIncome) {
        this.familyIncome = familyIncome;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (parentId != null ? parentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParentDetails)) {
            return false;
        }
        ParentDetails other = (ParentDetails) object;
        if ((this.parentId == null && other.parentId != null) || (this.parentId != null && !this.parentId.equals(other.parentId))) {
            return false;
        }
        return true;
    }

    public static String getAvailableID(){
         try(Connection c=SqlConnect.getDatabaseConnection())
         {
                Statement s=c.createStatement();
                ResultSet rs=s.executeQuery("select max(substr(`parent id`,2,9)) from `parent details`");
                rs.next();
                int val=rs.getInt(1)+1;
                String id=String.format("P%09d", val);
                return id;
                
            } catch (SQLException ex) {
                Logger.getLogger(ParentDetailsControl.class.getName()).log(Level.SEVERE, null, ex);
            }
           return null;
    }
    @Override
    public String toString() {
        return "javaapplication4.ParentDetails[ parentId=" + parentId + " ]";
    }
    
    
}
