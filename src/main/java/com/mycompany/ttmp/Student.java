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
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Programming
 */
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;
    private String registrationId;
    private String studentName;
    private String Username;
    private String email;
    private String mobileNumber;
    private String permanentAddress;
    private String address;
    private int rollNumber;
    private Date dateOfBirth;
    private Date dateOfRegistration;
    private char gender;
    private String bloodGroup;
    private String parentId;
    private I_Class class1;
    private Collection<ParentDetails> parentDetailsCollection;


    public static String getAvailableID(){
         try(Connection c=SqlConnect.getDatabaseConnection()) {
                Statement s=c.createStatement();
                ResultSet rs=s.executeQuery("select max(substr(`REGISTRATION ID`,2,5)) from `student`");
                rs.next();
                int val=rs.getInt(1)+1;
                String id=String.format("S%05d", val);
                return id;
            } catch (SQLException ex) {
                Logger.getLogger(ParentDetailsControl.class.getName()).log(Level.SEVERE, null, ex);
            }
           return null;
    }
    public static int getAvailableRollNo(){
         try(Connection c=SqlConnect.getDatabaseConnection()) {
                Statement s=c.createStatement();
                ResultSet rs=s.executeQuery("select max(`ROLL NUMBER`) from `student`");
                rs.next();
                int val=rs.getInt(1)+1;
                return val;
                
            } catch (SQLException ex) {
                Logger.getLogger(ParentDetailsControl.class.getName()).log(Level.SEVERE, null, ex);
            }
           return -1;
    }
    private Student(String registrationId) {
        this.registrationId = registrationId;
    }

    private Student(String registrationId, String studentName, String Username,
            String email, String mobileNumber, String permanentAddress,
            String address, int rollNumber, Date dateOfBirth,
            Date dateOfRegistration, char gender, String bloodGroup,
            String parentId) {
        this.registrationId = registrationId;
        this.studentName = studentName;
        this.Username = Username;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.permanentAddress = permanentAddress;
        this.address = address;
        this.rollNumber = rollNumber;
        this.dateOfBirth = dateOfBirth;
        this.dateOfRegistration = dateOfRegistration;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.parentId = parentId;
    }
    
    private Student(String registrationId, String studentName, String Username,
            String email, String mobileNumber, String permanentAddress,
            String address, int rollNumber, Date dateOfBirth,
            Date dateOfRegistration, char gender, String bloodGroup,
            String parentId,I_Class clas) {
        this.registrationId = registrationId;
        this.studentName = studentName;
        this.Username = Username;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.permanentAddress = permanentAddress;
        this.address = address;
        this.rollNumber = rollNumber;
        this.dateOfBirth = dateOfBirth;
        this.dateOfRegistration = dateOfRegistration;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.parentId = parentId;
        this.class1=clas;
    }
    
    public static Student createStudent(String registrationId) {
        return new Student(registrationId);
    }

    public static Student createStudent(String registrationId, String studentName, String Username, String email, String mobileNumber, String permanentAddress, String address, int rollNumber, Date dateOfBirth, Date dateOfRegistration, char gender, String bloodGroup, String parentId) {
        return new Student(registrationId,studentName, Username, email, mobileNumber,permanentAddress,address, rollNumber,dateOfBirth,dateOfRegistration,gender, bloodGroup,parentId);
    }

    public static Student createStudent(String registrationId, String studentName,
            String Username, String email, String mobileNumber,
            String permanentAddress, String address, int rollNumber, 
            Date dateOfBirth, Date dateOfRegistration,
            char gender, String bloodGroup, String parentId,I_Class clas) {
        return new Student(registrationId,studentName, Username, email,
                mobileNumber,permanentAddress,address, rollNumber,dateOfBirth,
                dateOfRegistration,gender, bloodGroup,parentId,clas);
    }
    public static Student createStudent(String registrationId, String studentName,
            String Username, String email, String mobileNumber,
            String permanentAddress, String address, int rollNumber, 
            Date dateOfBirth, Date dateOfRegistration,
            char gender, String bloodGroup, String parentId,String classId) {
        return new Student(registrationId,studentName, Username, email,
                mobileNumber,permanentAddress,address, rollNumber,dateOfBirth,
                dateOfRegistration,gender, bloodGroup,parentId,
                new I_Class(classId,I_ClassControl.getCLassName(classId)));
    }
    
    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    
    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
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

    public int getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }

    public Date getDateOfBirth() {
        String date=dateOfBirth.getDate()+"-"+dateOfBirth.getMonth()+"-"+dateOfBirth.getYear();
        
        System.out.println("--------------------------------------\nDOB:"+
                dateOfBirth.toString()+"\n"
                +"mydate: "+date + "-----------------------------------");
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


    public char getGender() {
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public I_Class getClass1() {
        return class1;
    }

    public void setClass1(I_Class class1) {
        this.class1 = class1;
    }

    public Collection<ParentDetails> getParentDetailsCollection() {
        return parentDetailsCollection;
    }

    public void setParentDetailsCollection(Collection<ParentDetails> parentDetailsCollection) {
        this.parentDetailsCollection = parentDetailsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (registrationId != null ? registrationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        if ((this.registrationId == null && other.registrationId != null) || (this.registrationId != null && !this.registrationId.equals(other.registrationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication4.Student[ registrationId=" + registrationId + " ]";
    }
    
}
