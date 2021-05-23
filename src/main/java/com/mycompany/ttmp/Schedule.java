/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ttmp;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
/**
 *
 * @author Programming
 */
public class Schedule implements Serializable {

    public String getTimeTableId() {
        return timeTableId;
    }

    public void setTimeTableId(String timeTableId) {
        this.timeTableId = timeTableId;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public status getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(status currentStatus) {
        this.currentStatus = currentStatus;
    }
    public enum status{
        ACTIVE, INACTIVE, INCOMING, OUTGOING
    }

    private static final long serialVersionUID = 1L;
    private int scheduleId;
    private String timeTableId;
    private LocalDateTime lastUpdated;
    private int updatedBy;
    private LocalDate from;
    private LocalDate till;
    private status currentStatus;
    private String dayId;
    private String timeslotId;
    private String classId;
    private String subjectId;
    private String facultyId;
    private int roomId;


    public Schedule( String dayId, String timeslotId, String classId,
            String subjectId, String facultyId, int roomId) {
         this.dayId = dayId;
        this.timeslotId = timeslotId;
        this.classId = classId;
        this.subjectId = subjectId;
        this.facultyId = facultyId;
        this.roomId = roomId;
    }
    
     Schedule( int id, String ttid, LocalDateTime updatedOn, int updatedBy,
            LocalDate from, LocalDate till, status currentStatus,
            String dayId, String timeslotId, String classId, String subjectId,
            String facultyId, int roomId) {
        this.scheduleId=id;
        this.timeTableId = ttid;
        this.lastUpdated = updatedOn;
        this.updatedBy = updatedBy;
        this.from = from;
        this.till = till;
        this.currentStatus = currentStatus;
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

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTill() {
        return till;
    }

    public void setTill(LocalDate till) {
        this.till = till;
    }

    }
