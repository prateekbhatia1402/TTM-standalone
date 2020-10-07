/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ttmp;

/**
 *
 * @author Programming
 * 
CREATE TABLE `qualification` (
    `FACULTY ID` VARCHAR(10) NOT NULL,
    `DEGREE` VARCHAR(10) NOT NULL,
    `YEAR` CHAR(4) NOT NULL,
    `PERCENTAGE` NUMBER(5,2) 
);
 */
public class Qualification {
    
    private String degree;
    private String year;
    private String institute;
    private float percentage;
   private Qualification(String degree,String year,String institute,float percentage){
    this.degree=degree;
    this.year=year;
    this.institute=institute;
    this.percentage=percentage;
   }
   
   public static Qualification createQualification(String degree,String year,String institute,float percentage){
       return new Qualification(degree,year,institute,percentage);
   }
    public String getDegree(){
        return degree;
    }
    
    public void setDegree(String degree){
        this.degree=degree;
    }
    public String getYear(){
        return year;
    }
    
    public void setYear(String year){
        this.year=year;
    }

    public String getInstitute(){
        return institute;
    }
    
    public void setInstitute(String institute){
        this.institute=institute;
    }
    
    public float getPercentage(){
        return percentage;
    }
    
    public void setPercentage(float percentage){
        this.percentage=percentage;
    }
    @Override
    public String toString(){
        return getDegree()+" "+getYear()+" "+getInstitute()+" "+getPercentage();
    }
}
