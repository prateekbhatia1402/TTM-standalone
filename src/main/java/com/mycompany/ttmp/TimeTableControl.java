/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ttmp;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Programming
 */
final class TimeTables
{
    String classId;

    HashMap<String,TimeTable> tables ;
    String latest = "";
    String current = "";
    String lastFetched = "";
    
    public String getClassId() {
        return classId;
    }
    TimeTables() {
        this.tables = new HashMap<>();
    }
    TimeTables(String classId)
    {
        //System.out.println("Creating TimeTables Object : "+LocalDateTime.now());
        this.tables = new HashMap<>();
        try(Connection con = SqlConnect.getDatabaseConnection();
                Statement st = con.createStatement())
        {
            String query =
                    "select concat(`class id`,'_000') as `ttid`,`WEF` as `from`,NULL as `till`, case"
                + " when `wef` <= curdate() then true else false end as `current`"
                + ", true as `latest` from schedule where `class id`='"+classId+"' UNION "
                + "select `time table id`,`from`, `to`, case when `from` <= curdate()"
                + " and `to` >= curdate() then true else false end, "
                + "false as `latest` from schedule_records where "
                    + "`class id`='"+classId+"' order by `from` desc;";
            ////System.out.println(query);
            //System.out.println("Executing Query : "+LocalDateTime.now());
            ResultSet rs = st.executeQuery(query);
            //System.out.println("Got Result : "+LocalDateTime.now());
            while(rs.next())
            {
                String tid = rs.getString(1);
                var v = rs.getDate(2);
                LocalDate from = v !=  null ? v.toLocalDate() : null;
                v = rs.getDate(3);
                LocalDate till = v !=  null ? v.toLocalDate() : null;
                boolean current = rs.getBoolean(4);
                boolean latest = rs.getBoolean(5);
                ////System.out.println(tid+" "+from+" "+till+" "+current+" "+latest);
                if (tid.endsWith("_000"))
                    tid = tid.substring(0,tid.indexOf("_"));
                addTimeTable(tid, from, till);
                if (current)
                    setCurrentTimeTable(tid);
                if (latest)
                    setLatestTimeTable(tid);
            }
            //System.out.println("Filled Data : "+LocalDateTime.now());
        } catch (SQLException ex) {
             Logger.getLogger(TimeTableControl.class.getName()).log(Level.SEVERE, null, ex);
         }
        this.classId = classId;
        //System.out.println("Object Created : "+LocalDateTime.now());
    }
    private class TimeTable
    {
        String id;
        LocalDate from;
        LocalDate to;
        ArrayList<Schedule> schedule;
        TimeTable(String id, LocalDate from, LocalDate to)
        {
            this.id = id;
            this.from = from;
            this.to = to;
        }
        TimeTable(String id, LocalDate from, LocalDate to, ArrayList<Schedule> schedule)
        {
            this.id = id;
            this.from = from;
            this.to = to;
            this.schedule = schedule;
        }
        void setSchedule(ArrayList<Schedule> schedule)
        {
            this.schedule = schedule;
        }
        ArrayList<Schedule> getSchedule()
        {
            return schedule;
        }
        boolean hasSchedule()
        {
            return schedule != null && schedule.size() > 0;
        }
        
    }
    void setCurrentTimeTable(String id)
    {
        current = id;
    }
    void setLatestTimeTable(String id)
    {
        latest = id;
    }
    void addTimeTable(String id, LocalDate from, LocalDate to)
    {
        ////System.out.println(id+" "+ from+ " "+to);
        tables.putIfAbsent(id, new TimeTable(id, from, to));
    }
    void addLatestTimeTable(String id, LocalDate from, LocalDate to)
    {
        tables.putIfAbsent(id, new TimeTable(id, from, to));
        latest = id;
    }
    void addCurrentTimeTable(String id, LocalDate from, LocalDate to)
    {
        tables.putIfAbsent(id, new TimeTable(id, from, to));
        current = id;
    }
    
    String toPlainString(String id)
    {
        TimeTable tt = tables.get(id);
        String val = tt.from.toString();
               val += tt.to != null ? " to "+tt.to.toString(): " >>";
        if (id.equals(current))
            val += " (Current)";
        if (id.equals(latest))
            val += " (Latest)";
        return val;
    }
    ArrayList<Schedule> fetchCurrentTimeTable(String id)
    {
            //System.out.println("Fetching Current TT : "+LocalDateTime.now());
        try(Connection con = SqlConnect.getDatabaseConnection();
                Statement st = con.createStatement())
        {
        if ("".equals(current))
        {
            //System.out.println("Finding Current TT : "+LocalDateTime.now());
        String query = 
                "select concat(`class id`, '_000') as `ttid`,`WEF` as `from`,NULL as `till`"
                + " from schedule where `wef` <= curdate()"
                + "and `class id`='"+id+"' UNION "
                + "select `time table id`,`from`, `to` "
                + "from schedule_records where `from` <= curdate()"
                + " and `to` >= curdate() and "
                    + "`class id`='"+id+"'";
            //System.out.println("executing Query : "+LocalDateTime.now());
            ResultSet rs = st.executeQuery(query);
            //System.out.println("Query Executed : "+LocalDateTime.now());
            if (!rs.next())
                return null;
            String tid = rs.getString(1);
            if (tid.substring(7, 10).equals("000"))
                tid = tid.substring(0,6);
            var v = rs.getDate(2);
            LocalDate from = v != null ? v.toLocalDate(): null;
            v = rs.getDate(3);
            LocalDate till = v != null ? v.toLocalDate(): null;
            addCurrentTimeTable(tid, from, till);
            //System.out.println("Recognized Current TT : "+LocalDateTime.now());
        }
        } catch (SQLException ex) {
            Logger.getLogger(TimeTables.class.getName()).log(Level.SEVERE, null, ex);
        }
        lastFetched = current;
        return fetchParticularTimeTable(current);
    }
    
    boolean isLatest(String tid)
    {
        return tid.length() < 10 || "000".equals(tid.substring(7, 10));
    }
    ArrayList<String> getTimeTablesList()
    {
        ArrayList<String> records = new ArrayList<>();
        tables.keySet().forEach(ttid -> {
            ////System.out.println("key value : "+ttid);
            records.add(ttid);
        });
        return records;
    }
    ArrayList<Schedule> fetchTimeTable()
    {
        ////System.out.println("-------- In Fetch TT ---------");
        //System.out.println("Fetching TT : "+LocalDateTime.now());
        ArrayList<Schedule> schedule = fetchCurrentTimeTable(classId);
        if (schedule == null || schedule.size() < 1)
        {
            
            //System.out.println("Current TT not found, Fetching Latest : "+LocalDateTime.now());
            ////System.out.println("Curent TT Not Found, going for latest TT ");
            schedule = fetchLatestTimeTable();
        }
        ////System.out.println("returning "+schedule.size()+" records");
            //System.out.println("Got TT : "+LocalDateTime.now());
        return schedule;
    }
    
    ArrayList<Schedule> fetchLatestTimeTable()
    {
        ////System.out.println("---- In Fetch Latest TT ---");
            //System.out.println("Fetching Latest TT : "+LocalDateTime.now());
        if (latest == null || "".equals(latest))
        {
            ////System.out.println("updated latest to "+ classId);
            latest = classId;
        }
        TimeTable timeTable = tables.get(latest);
        if (!timeTable.hasSchedule())
        {
            //System.out.println("Latest TT not in memory: "+LocalDateTime.now());
            ////System.out.println("No TT in Cache, fetching from DB");
            ArrayList<Schedule> schedule = new ArrayList<>();
            try(Connection con=SqlConnect.getDatabaseConnection()) {
                Statement st=con.createStatement();
                String query="select * from schedule where `faculty id`='"+latest
                        +"' or `class id`='" +latest+"'";
                ////System.out.println(query);
            //System.out.println("executing query : "+LocalDateTime.now());
                ResultSet rs=st.executeQuery(query);
            //System.out.println("query executed : "+LocalDateTime.now());
                while(rs.next())
                {
                    int sid = rs.getInt(1);
                    var val = rs.getDate(2);
                    LocalDate lastUpdated = val != null ? val.toLocalDate() : null;
                    val = rs.getDate(3);
                    LocalDate wef = val != null ? val.toLocalDate() : null;
                    String dayId=rs.getString(4);
                    String timeslotId=rs.getString(5);
                    String classId=rs.getString(6);
                    String subjectId=rs.getString(7);
                    String facultyId=rs.getString(8);
                    int roomId=rs.getInt(9);
                    schedule.add(new Schedule(sid,wef,dayId,timeslotId,classId,subjectId,facultyId,roomId));
                }
                
            //System.out.println("Got Results : "+LocalDateTime.now());
            con.close();
            }
            catch (SQLException ex) {
            Logger.getLogger(TimeTableControl.class.getName()).log(Level.SEVERE, null, ex);
            }
            ////System.out.println("adding "+schedule.size()+" records");
            //System.out.println("Adding TT to memory: "+LocalDateTime.now());
        timeTable.setSchedule(schedule);
         }
            //System.out.println("Returning Latest TT : "+LocalDateTime.now());
        lastFetched = latest;
        return timeTable.getSchedule();
    }
    
    ArrayList<Schedule> fetchLatestTimeTable(String id)
    {
        if (latest == null || "".equals(latest))
        {
            classId = id;
            if (!tables.containsKey(id))
            {
                try(Connection con = SqlConnect.getDatabaseConnection();
                        Statement st = con.createStatement())
                {
                 String q = "select `WEF` from schedule where `class id`='"+id+"'";
                 ResultSet rs = st.executeQuery(q);
                 if (rs.next())
                 {
                     addLatestTimeTable(id, rs.getDate(1).toLocalDate(), null);
                 }
                 else
                 {
                     latest = "false";
                 }
                } catch (SQLException ex) {
                    Logger.getLogger(TimeTables.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (latest.equals("false"))
            return new ArrayList<>();
        TimeTable timeTable = tables.get(latest);
        if (!timeTable.hasSchedule())
        {
            ArrayList<Schedule> schedule = new ArrayList<>();
            LocalDate today = LocalDate.now();
            try(Connection con=SqlConnect.getDatabaseConnection()) {
                Statement st=con.createStatement();
                String query="select * from schedule where `faculty id`='"+latest
                        +"' or "
                    + "`class id`='" + latest + "'";
                ////System.out.println(query);
                ResultSet rs=st.executeQuery(query);
                while(rs.next())
                {
                    int sid = rs.getInt(1);
                    var val = rs.getDate(2);
                    LocalDate lastUpdated = val != null ? val.toLocalDate() : null;
                    val = rs.getDate(3);
                    LocalDate wef = val != null ? val.toLocalDate() : null;
                    String dayId=rs.getString(4);
                    String timeslotId=rs.getString(5);
                    String classId=rs.getString(6);
                    String subjectId=rs.getString(7);
                    String facultyId=rs.getString(8);
                    int roomId=rs.getInt(9);
                    schedule.add(new Schedule(sid,wef,dayId,timeslotId,classId,subjectId,facultyId,roomId));
                }
            con.close();
            }
            catch (SQLException ex) {
            Logger.getLogger(TimeTableControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        timeTable.setSchedule(schedule);
         }
        lastFetched = latest;
        return timeTable.getSchedule();
    }
    
    ArrayList<Schedule> fetchParticularTimeTable(String tid)
    {
        //System.out.println("Fetching TT("+tid+") : "+LocalDateTime.now());
        if(isLatest(tid))
            return fetchLatestTimeTable();
        TimeTable timeTable = tables.get(tid);
        if (!timeTable.hasSchedule())
        {
            //System.out.println("TT not in memory: "+LocalDateTime.now());
            ArrayList<Schedule> schedule = new ArrayList<>();
            try(Connection con=SqlConnect.getDatabaseConnection();
                Statement st=con.createStatement();) {
            
            String query = "select * from schedule_records where `time table id`='"+tid+"'";
            ////System.out.println(query);
            //System.out.println("Executing Query : "+LocalDateTime.now());
            ResultSet rs = st.executeQuery(query);
            //System.out.println("Query Executed : "+LocalDateTime.now());
            while(rs.next())
            {
                int sid=rs.getInt(1);
                var val = rs.getDate(3);
                LocalDate wef = val != null ? val.toLocalDate() : null;
                val = rs.getDate(4);
                LocalDate to = val != null ? val.toLocalDate() : null;
                String dayId=rs.getString(5);
                String timeslotId=rs.getString(6);
                String classId=rs.getString(7);
                String subjectId=rs.getString(8);
                String facultyId=rs.getString(9);
                int roomId=rs.getInt(10);
                schedule.add(new Schedule(sid,wef,to,dayId,timeslotId,classId,subjectId,facultyId,roomId));
            }
            //System.out.println("Got Results : "+LocalDateTime.now());
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(TimeTableControl.class.getName()).log(Level.SEVERE, null, ex);
        }
            //System.out.println("TT added to memory: "+LocalDateTime.now());
        timeTable.setSchedule(schedule);
        }
            //System.out.println("Returning TT : "+LocalDateTime.now());
            lastFetched = tid;
        return timeTable.schedule;
    }
}

public class TimeTableControl {/*
    public static I_Class[] getUnassignedClasses(){
        I_Class[] classes=null;
        try(Connection con=SqlConnect.getDatabaseConnection()) {
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select id,name from class where id not in"
                    + " (select distinct `class id` from schedule)");
            if(rs.last()==false)
                return null;
            int count=rs.getRow();
            rs.first();
            classes=new I_Class[count];
            for(int i=0;i<count;i++,rs.next()){
                String id=rs.getString("id");
                String name=rs.getString("name");
                classes[i]=new I_Class(id,name);
            }
                    
        } catch (SQLException ex) {
            Logger.getLogger(TimeTableControl.class. getName()).log(Level.SEVERE, null, ex);
        }
        return classes;
    }*/
     public static I_Class[] getClasses(){
        I_Class[] classes=null;
        try(Connection con=SqlConnect.getDatabaseConnection()) {
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select id,name,case when "
                    + "id in (select distinct `class id` from schedule) then '1'"
                    + " else '0' end from class");
            if(rs.last()==false)
                return null;
            int count=rs.getRow();
            rs.first();
            classes=new I_Class[count];
            for(int i=0;i<count;i++,rs.next()){
                String id=rs.getString("id");
                String name=rs.getString("name");
                boolean assigned=rs.getBoolean(3);
                classes[i]=new I_Class(id,name,assigned);
            }
                    
        } catch (SQLException ex) {
            Logger.getLogger(TimeTableControl.class. getName()).log(Level.SEVERE, null, ex);
        }
        return classes;
    }
    public static Object[][] getAssignedFaculty(){
        Object[][] faculties=null;
        try(Connection con=SqlConnect.getDatabaseConnection()) {
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select `faculty id`,name,case when "
                    + "`faculty id` in (select distinct `faculty id` from schedule) then '1'"
                    + " else '0' end from faculty");
            if(rs.last()==false)
                return null;
            int count=rs.getRow();
            rs.first();
            faculties=new Object[count][];
            for(int i=0;i<count;i++,rs.next()){
                String id=rs.getString("faculty id");
                String name=rs.getString("name");
                boolean assigned=rs.getBoolean(3);
                faculties[i]=new Object[]{id,name,assigned};
            }
                    
        } catch (SQLException ex) {
            Logger.getLogger(TimeTableControl.class. getName()).log(Level.SEVERE, null, ex);
        }
        return faculties;
    }
    public static String[][] getAvailableFacultyDetails(String timeSlotId,String dayId
    ,String classId){
        String [][] details=null;
        try(Connection con=SqlConnect.getDatabaseConnection()) {
            Statement st=con.createStatement();
            String query="select f.`faculty id`,f.name,csf.`subject id`,"
                    + "`subject name` from faculty f,class_subject_faculty csf,"
                    + "subject sub where f.`faculty id`=csf.`faculty id` "
                    + "and csf.`subject id`=sub.`subject id` and csf.`class id`="
                    + "'"+classId+"' and f.`faculty id` "
                    + "not in (select s.`faculty id` from schedule s where "
                    + "`timeslot id`='"+timeSlotId+"' and `day id`='"+dayId+"'"
                    + " and s.`class id`!='"+classId+"')";
       //     //////System.out.println(query);
            ResultSet rs=st.executeQuery(query);
            if(rs.last()==false)
                return null;
            int count=rs.getRow();
            rs.first();
            details=new String[count][];
            for(int i=0;i<count;i++,rs.next()){
                String id=rs.getString(1);
                String name=rs.getString(2);
                String subId=rs.getString(3);
                String subject=rs.getString(4);
                details[i]=new String[]{id,name,subId,subject};
            }
                    
        } catch (SQLException ex) {
            Logger.getLogger(TimeTableControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return details;
    }
    
    static boolean isFacultyFree(String facId, String dayId, String timeSlotId)
    {
        try(Connection con=SqlConnect.getDatabaseConnection())
        {
            Statement st=con.createStatement();
            String query="select `faculty id` from schedule where "
                    + "`timeslot id`='"+timeSlotId+"' and `day id`='"+dayId+"'"
                    + " and `faculty id` = '"+facId+"'";
            ////System.out.println(query);
            ResultSet rs = st.executeQuery(query);
            return !rs.next();
        }
        catch(Exception exc)
        {
            
        }
        return false;
    }
    
     static ArrayList<FacSub> getFacultySubjectDetails(String classId,
             LocalDate from, LocalDate to){
        ArrayList<FacSub> details=new ArrayList<>();
        try(Connection con=SqlConnect.getDatabaseConnection()) {
            Statement st=con.createStatement();
            String query = "select f.`faculty id`,f.name,csf.`subject id`,"
                    + "`subject name`,`LECTURES REQUIRED`"
                    + " from faculty f,class_subject_faculty csf,"
                    + "subject sub where f.`faculty id`=csf.`faculty id` "
                    + "and csf.`subject id`=sub.`subject id` and csf.`class id`="
                    + "'"+classId+"'";
            ResultSet rs=st.executeQuery(query);
            if(rs.last()==false)
                return null;
            int count=rs.getRow();
            rs.first();
            for(int i=0;i<count;i++,rs.next()){
                String id=rs.getString(1);
                String name=rs.getString(2);
                String subId=rs.getString(3);
                String subject=rs.getString(4);
                int req = rs.getInt(5);
                ArrayList<Schedule> schedule = getFacultyTimeTable(id, from, to);
                HashSet<String> facSchedule = new HashSet<>();
                for (Schedule s: schedule)
                {
                    if(!classId.equals(s.getClassId()))
                    {
                        facSchedule.add(s.getDayId()+"_"+s.getTimeslotId());
                    }
                }
                details.add(new FacSub(id, name, subId, subject, req, facSchedule));   
            }
        } catch (SQLException ex) {
            Logger.getLogger(TimeTableControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return details;
    }
    
     static TimeTables getTimeTablesList(String id)
    {
        //System.out.println("Getting TimeTables("+id+") ID : "+LocalDateTime.now());
        return new TimeTables(id);
    }
    
    static ArrayList<Schedule> getLatestClassTimeTable(String id)
    {
        return new TimeTables().fetchLatestTimeTable(id);
    }
     
    public static ArrayList<Schedule> getFacultyTimeTable(String id, LocalDate from,
            LocalDate to){
        ArrayList<Schedule> schedule = new ArrayList<>();
        try(Connection con=SqlConnect.getDatabaseConnection()) {
            Statement st=con.createStatement();
            if (to == null)
                to = LocalDate.now().plusYears(20);
        String query="select `day id`,`timeslot id`,`class id`,`subject id` "
                + "from schedule where `faculty id`='"+id+"' and wef <= '"+
                to.format(DateTimeFormatter.ISO_DATE)+"'"
                + " UNION select `day id`,`timeslot id`,`class id`,`subject id`"
                + " from schedule_records where `faculty id`='"+id+"' and `to` >= '"
                +from.format(DateTimeFormatter.ISO_DATE)+"'";
            ////System.out.println(query);
            ResultSet rs=st.executeQuery(query);
            if (rs.last() == false)
                return null;
            int count=rs.getRow();
            rs.first();
            for(int i=0;i<count;i++,rs.next()){
                String dayId=rs.getString(1);
                String timeslotId=rs.getString(2);
                String classId=rs.getString(3);
                String subjectId=rs.getString(4);
                schedule.add(new Schedule(dayId, timeslotId, classId, subjectId,
                        id, -1));
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(TimeTableControl.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return schedule;
    }
    public static ArrayList<Schedule> getFacultyTimeTable(String id){
        ArrayList<Schedule> schedule = new ArrayList<>();
        try(Connection con=SqlConnect.getDatabaseConnection()) {
            Statement st=con.createStatement();
        String query="select * from schedule where `faculty id`='"+id+"'";
            ////System.out.println(query);
            ResultSet rs=st.executeQuery(query);
            if (rs.last() == false)
                return null;
            int count=rs.getRow();
            rs.first();
            for(int i=0;i<count;i++,rs.next()){
                int sid = rs.getInt(1);
                var val = rs.getDate(2);
                LocalDate lastUpdated = val != null ? val.toLocalDate() : null;
                val = rs.getDate(3);
                LocalDate wef = val != null ? val.toLocalDate() : null;
                String dayId=rs.getString(4);
                String timeslotId=rs.getString(5);
                String classId=rs.getString(6);
                String subjectId=rs.getString(7);
                String facultyId=rs.getString(8);
                int roomId=rs.getInt(9);
                schedule.add(new Schedule(sid,wef,dayId,timeslotId,classId,subjectId,facultyId,roomId));
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(TimeTableControl.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return schedule;
    }
    
    public static int getRoomId(String classId,String subjectId,String facultyId){
        try(Connection con=SqlConnect.getDatabaseConnection()) {
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select `room id` from class_subject_faculty "
                    + "where `class id`='"+classId+"' and `subject id`='"+subjectId+"' "
                    + "and `faculty id`='"+facultyId+"'");
            if(rs.next())
            {
                int roomid = rs.getInt(1);
                con.close();
                return roomid;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TimeTableControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public static void createTimeTable(ArrayList<Schedule> schedule){
        Connection con=null;
        try{
            con=SqlConnect.getDatabaseConnection();
            con.setAutoCommit(false);
            Statement st=con.createStatement();
        String q = "select max(substr(`time table id`,8,3)) from schedule_records";
        PreparedStatement psMaxv = con.prepareStatement(q);
        String query = "INSERT INTO `schedule_records`(`time table id`,"
        + "`day id`,`timeslot id`,"
        + "`class id`,`subject id`,`faculty id`,`room id`,`from`,`to`)"
        + " select ?,`day id`,`timeslot id`,"
        + "`class id`,`subject id`,`faculty id`,`room id`,`wef`,sysdate()"
        + " from schedule where `class id`=?";
        PreparedStatement psUpdate = con.prepareStatement(query);
        query = "insert into schedule(`day id`,`timeslot id`,`class id`,"
              + "`subject id`,`faculty id`,`room id`,`wef`,`last updated`) values"
                    + "(?,?,?,?,?,?,?,?)";
        PreparedStatement psInsert = con.prepareStatement(query);
        HashSet<String> classes=new HashSet();
        for(Schedule scheduleItem : schedule){
            String classId=scheduleItem.getClassId();
            ////System.out.println(classId);
            if(!classes.contains(classId)){
                ResultSet vrs = psMaxv.executeQuery();
                vrs.next();
                int v = vrs.getInt(1) + 1;
                String ttid = classId + "_" + String.format("%03d", v);
                ////System.out.println("trying to delete class "+classId);
                psUpdate.setString(1, ttid);
                psUpdate.setString(2, classId);
                int ans=psUpdate.executeUpdate();
                ////System.out.println(ans+" rows tranferred");
                query = "delete from schedule where `class id`='"+classId+"';";
                ans=st.executeUpdate(query);
                ////System.out.println(ans+" rows affected");
                classes.add(classId);
            }
            psInsert.setString(1, scheduleItem.getDayId());
            psInsert.setString(2, scheduleItem.getTimeslotId());
            psInsert.setString(3, scheduleItem.getClassId());
            psInsert.setString(4, scheduleItem.getSubjectId());
            psInsert.setString(5, scheduleItem.getFacultyId());
            psInsert.setInt(6, scheduleItem.getRoomId());
            psInsert.setString(7, LocalDate.now().atStartOfDay().plusDays(1).
                            format(DateTimeFormatter.BASIC_ISO_DATE));
            psInsert.setString(8, LocalDate.now().atStartOfDay().
                            format(DateTimeFormatter.BASIC_ISO_DATE));
            psInsert.executeUpdate();
        }
        con.commit();
        JOptionPane.showMessageDialog(null,"RECORDS ADDED SUCCESSFULLY");
        } catch (SQLException ex) {
            try {
                con.rollback();
        JOptionPane.showMessageDialog(null,"SOMETHING WENT WRONG");
            } catch (SQLException ex1) {
                Logger.getLogger(TimeTableControl.class.getName()).log(Level.SEVERE, null, ex1);
            }
            JOptionPane.showMessageDialog(null,"SOMETHING WENT WRONG");
            Logger.getLogger(TimeTableControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Subject[] getSubjects(String classId){
        try(Connection con=SqlConnect.getDatabaseConnection()) {
            Subject subjects[]=null;
            Statement st=con.createStatement();
            String query="select * from subject sub where "
                    + "sub.`subject id`in (select r.`subject id` from class_subject_faculty r where `class id`='"+classId+"')";
            //////System.out.println(query);
            ResultSet rs=st.executeQuery(query);
            if(rs.last()==false)
                return null;
            int count=rs.getRow();
            rs.first();
            subjects=new Subject[count];
            for(int i=0;i<count;i++,rs.next())
            {
                String subId=rs.getString("subject id");
                String subName=rs.getString("subject name");
                int lectRequired=rs.getInt("LECTURES REQUIRED");
                subjects[i]=new Subject(subId,subName,lectRequired);
                
            }   
            con.close();
            return subjects; 
        } catch (SQLException ex) {
            Logger.getLogger(TimeTableControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static Subject getSubject(String subjectId){
        try {
            Subject subject;
            try (Connection con = SqlConnect.getDatabaseConnection()) {
                Statement st=con.createStatement();
                String query="select * from subject where "
                        + "`subject id`='"+subjectId+"'";
                //////System.out.println(query);
                ResultSet rs=st.executeQuery(query);
                if(rs.next()==false)
                    return null;
                String subId=rs.getString("subject id");
                String subName=rs.getString("subject name");
                int lectRequired = rs.getInt("LECTURES REQUIRED");
                subject=new Subject(subId,subName,lectRequired);
            }
               return subject; 
        } catch (SQLException ex) {
            Logger.getLogger(TimeTableControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
