/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ttmp;

import java.io.Serializable;
import java.time.LocalDate;
/**
 *
 * @author Programming
 */
public class Schedule implements Serializable {

    private static final long serialVersionUID = 1L;
    private int scheduleId;
    private String dayId;
    private String timeslotId;
    private String classId;
    private String subjectId;
    private String facultyId;
    private LocalDate lastUpdated;
    private LocalDate from;
    private LocalDate till;
    private int roomId;
    private LocalDate wef;


    public Schedule( String dayId, String timeslotId, String classId, String subjectId, String facultyId, int roomId) {
         this.dayId = dayId;
        this.timeslotId = timeslotId;
        this.classId = classId;
        this.subjectId = subjectId;
        this.facultyId = facultyId;
        this.roomId = roomId;
    }

    public Schedule( int id,LocalDate from,LocalDate till,String dayId,
            String timeslotId, String classId, String subjectId, 
            String facultyId, int roomId)
    {
        this.scheduleId = id;
        this.from = from;
        this.till = till;
        this.dayId = dayId;
        this.timeslotId = timeslotId;
        this.classId = classId;
        this.subjectId = subjectId;
        this.facultyId = facultyId;
        this.roomId = roomId;
    }
    public Schedule( int id, LocalDate updatedOn, String dayId, String timeslotId, String classId, String subjectId, String facultyId, int roomId) {
        this.scheduleId=id;
        this.lastUpdated = updatedOn;
        this.dayId = dayId;
        this.timeslotId = timeslotId;
        this.classId = classId;
        this.subjectId = subjectId;
        this.facultyId = facultyId;
        this.roomId = roomId;
    }
    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int id) {
        this.scheduleId = id;
    }
    public String getDayId() {
        return dayId;
    }

    public void setDayId(String dayId) {
        this.dayId = dayId;
    }

    public String getTimeslotId() {
        return timeslotId;
    }

    public void setTimeslotId(String timeslotId) {
        this.timeslotId = timeslotId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public LocalDate getFrom() {
        return from == null ? wef : from;
    }

    public void setFrom(LocalDate from) {
        if (this.from != null)
        this.from = from;
        else wef = from;
    }

    public LocalDate getTill() {
        return till;
    }

    public void setTill(LocalDate till) {
        this.till = till;
    }

    public LocalDate getWef() {
        return wef;
    }

    public void setWef(LocalDate wef) {
        this.wef = wef;
    }


    
}
