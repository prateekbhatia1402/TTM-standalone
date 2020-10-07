/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ttmp;

import java.util.Calendar;

/**
 *
 * @author Programming
 */
public class Date{
    
    private int year,month,date;
    public Date(int year, int month, int date) {
        this.year=year;
        this.month=month;
        this.date=date;
    }
    
    public Date(long timeInMilli) {
        Calendar cal=Calendar.getInstance();
        cal.setTimeInMillis(timeInMilli);
        year=cal.get(Calendar.YEAR);
        month=cal.get(Calendar.MONTH)+1;
        date=cal.get(Calendar.DATE);
    }
    
    public Date(java.sql.Date date){
        Calendar cal=Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        this.year=cal.get(Calendar.YEAR);
        this.month=cal.get(Calendar.MONTH)+1;
        this.date=cal.get(Calendar.DATE);
    }
    
    public Date() {
        Calendar cal=Calendar.getInstance();
        year=cal.get(Calendar.YEAR);
        month=cal.get(Calendar.MONTH);
        date=cal.get(Calendar.DATE);
    }
     
    public int getYear(){
        return year;
    }
    public int getMonth(){
        return month;
    }
    public int getDate(){
        return date;
    }
    @Override
    public String toString(){
        return String.format("%04d-%02d-%02d",year,month,date);
    }
    public String toPlainString(){
        return String.format("%04d%02d%02d",year,month,date);
    }
    
    public static Date getCurrentDate(){
        return new Date();
    }
}
