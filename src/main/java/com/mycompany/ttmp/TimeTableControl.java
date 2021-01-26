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
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Programming
 */
final class TimeTables {

    String tablesOf;

    HashMap<String, TimeTable> tables;
    String latest = "";
    String current = "";
    String lastFetched = "";
    boolean updateScheduled = false;
    
    boolean isTablesOfAClass()
    {
        return tablesOf.startsWith("C");
    }

    public boolean isUpdateScheduled() {
        return updateScheduled;
    }

    TimeTables() {
        this.tables = new HashMap<>();
    }

    public String getLatest() {
        return latest;
    }

    public String getCurrent() {
        return current;
    }

    public String getLastFetched() {
        return lastFetched;
    }

    TimeTables(String tablesOf) {
        //System.out.println("Creating TimeTables Object : "+LocalDateTime.now());
        this.tables = new HashMap<>();
        this.tablesOf = tablesOf;
        try (Connection con = SqlConnect.getDatabaseConnection();
                Statement st = con.createStatement()) 
        {
            String query;
            if (isTablesOfAClass())
            {
                query = "select distinct `time table id`, `from`, `to`, `status`"
                    + " from schedule where "
                    + "`class id`='" + tablesOf + "' order by `from` desc;";
            
                ////System.out.println(query);
            //System.out.println("Executing Query : "+LocalDateTime.now());
                ResultSet rs = st.executeQuery(query);
                //System.out.println("Got Result : "+LocalDateTime.now());
                boolean change_scheduled = false;
                while (rs.next()) {
                    String tid = rs.getString(1);
                    var v = rs.getDate(2);
                    LocalDate from = v != null ? v.toLocalDate() : null;
                    v = rs.getDate(3);
                    LocalDate till = v != null ? v.toLocalDate() : null;
                    String sstatus = rs.getString(4).toUpperCase();
                    var status = Schedule.status.valueOf(sstatus);
                    if (status == Schedule.status.OUTGOING || 
                            status == Schedule.status.INCOMING)
                        change_scheduled = true;
                    boolean current = status == Schedule.status.ACTIVE
                            || status == Schedule.status.OUTGOING;
                    boolean latest = status == Schedule.status.ACTIVE
                            || status == Schedule.status.INCOMING;

                    ////System.out.println(tid+" "+from+" "+till+" "+current+" "+latest);

                    addTimeTable(tid, from, till);
                    if (current) {
                        setCurrentTimeTable(tid);
                    }
                    if (latest) {
                        setLatestTimeTable(tid);
                    }
                }
                if (change_scheduled)
                    updateScheduled = true;
            //System.out.println("Filled Data : "+LocalDateTime.now());
            }
            else
            {
                query = "select `time table id`, `from`, `to`, `status`"
                        + " from schedule where `from` <= ? and `to` >= ? and "
                        + "(`faculty id` = '"+tablesOf+"' or concat('', `room id`) = '"+tablesOf+"')";
                PreparedStatement ps = con.prepareStatement(query);
                query = "select "
                        + "distinct `from`, "
                        + "if(`from` <= curdate() and `to` >= curdate(), 1, 0) as `current`, "
                        + "if(`from` >= (select max(`from`) from schedule) , 1, 0) as `latest` "
                        + "from schedule "
                        + "where `from` is not null and "
                        + "(`faculty id`='" + tablesOf + 
                        "' or concat('',`room id`)='" + tablesOf + "')"
                        + " order by `from` desc";
                System.out.println(query);
                ResultSet rs = st.executeQuery(query);
                boolean change_scheduled = false;
                while (rs.next())
                {
                    var v = rs.getDate(1);
                    LocalDate from = v.toLocalDate();
                    String sfrom = from.format(DateTimeFormatter.ISO_DATE);
                    LocalDate to = LocalDate.MAX;
                    boolean current = rs.getBoolean(2);
                    boolean latest = rs.getBoolean(3);
                    ps.setString(1, sfrom);
                    ps.setString(2, sfrom);
                    ResultSet subrs = ps.executeQuery();
                    HashSet<String> tids = new HashSet<>();
                    while (subrs.next()) {
                        String tid = subrs.getString(1);
                        var sv = subrs.getDate(3);
                        LocalDate till = sv != null ? sv.toLocalDate() : null;
                        if (till != null && till.isBefore(to))
                            to = till;
                        String sstatus = subrs.getString(4).toUpperCase();
                        var status = Schedule.status.valueOf(sstatus);
                        if (status == Schedule.status.OUTGOING || 
                                status == Schedule.status.INCOMING)
                            change_scheduled = true;
                        tids.add(tid);
                    }
                    
                    addTimeTable(sfrom,
                            from, to, tids);
                    if (current) {
                        setCurrentTimeTable(sfrom);
                    }
                    if (latest) {
                        setLatestTimeTable(sfrom);
                    }
                }
                
                if (change_scheduled)
                    updateScheduled = true;
                
            }
         } catch (SQLException ex) {
            Logger.getLogger(TimeTableControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("Object Created : "+LocalDateTime.now());
    }

    private class TimeTable {

        String id;
        private LocalDate from;
        private LocalDate to;
        ArrayList<Schedule> schedule;
        private boolean ofClass = true;
        private HashSet<String> classes = new HashSet<>();
        TimeTable(String id, LocalDate from, LocalDate to) {
            this.id = id;
            this.from = from;
            this.to = to;
        }

        TimeTable(String id, LocalDate from, LocalDate to,HashSet<String> classes) {
            this.id = id;
            this.from = from;
            this.to = to;
            this.ofClass = false;
            classes.forEach(c -> {
                this.classes.add(c);
            });
        }
        
        TimeTable(String id, LocalDate from, LocalDate to,
                ArrayList<Schedule> schedule) {
            this.id = id;
            this.from = from;
            this.to = to;
            this.schedule = schedule;
        }
        
        TimeTable(String id, LocalDate from, LocalDate to,HashSet<String> classes,
                ArrayList<Schedule> schedule) {
            this.id = id;
            this.from = from;
            this.to = to;
            this.ofClass = false;
            classes.forEach(c -> {
                this.classes.add(c);
            });
            this.schedule = schedule;
        }

        void setSchedule(ArrayList<Schedule> schedule) {
            this.schedule = schedule;
        }

        ArrayList<Schedule> getSchedule() {
            return schedule;
        }

        boolean hasSchedule() {
            return schedule != null && schedule.size() > 0;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 59 * hash + Objects.hashCode(this.id);
            hash = 59 * hash + Objects.hashCode(this.from);
            hash = 59 * hash + Objects.hashCode(this.to);
            hash = 59 * hash + Objects.hashCode(this.classes);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final TimeTable other = (TimeTable) obj;
            if (!Objects.equals(this.id, other.id)) {
                return false;
            }
            if (!Objects.equals(this.from, other.from)) {
                return false;
            }
            if (!Objects.equals(this.to, other.to)) {
                return false;
            }
            if (!Objects.equals(this.classes, other.classes)) {
                return false;
            }
            return true;
        }

        public HashSet<String> getClasses() {
            return classes;
        }

        public void setClasses(HashSet<String> classes) {
            this.classes = classes;
        }

        public LocalDate getFrom() {
            return from;
        }
        public LocalDate getTo() {
            return to;
        }

        public void setFrom(LocalDate from) {
            this.from = from;
        }


        public void setTo(LocalDate to) {
            this.to = to;
        }


    }

    void setCurrentTimeTable(String id) {
        current = id;
    }

    void setLatestTimeTable(String id) {
        latest = id;
    }

    void addTimeTable(String id, LocalDate from, LocalDate to) {
        tables.putIfAbsent(id, new TimeTable(id, from, to));
    }
    void addTimeTable(String id, LocalDate from, LocalDate to, HashSet<String> tids) {
        tables.putIfAbsent(from.format(DateTimeFormatter.ISO_DATE),
                new TimeTable(id, from, to, tids));
    }

    void addLatestTimeTable(String id, LocalDate from, LocalDate to) {
        tables.putIfAbsent(id, new TimeTable(id, from, to));
        latest = id;
    }
    void addLatestTimeTable(String id, LocalDate from, LocalDate to, HashSet<String> tids) {
        tables.putIfAbsent(from.format(DateTimeFormatter.ISO_DATE),
                new TimeTable(id, from, to, tids));
        latest = id;
    }

    void addCurrentTimeTable(String id, LocalDate from, LocalDate to, HashSet<String> tids) {
        tables.putIfAbsent(from.format(DateTimeFormatter.ISO_DATE),
                new TimeTable(id, from, to, tids));
        current = id;
    }
    void addCurrentTimeTable(String id, LocalDate from, LocalDate to) {
        tables.putIfAbsent(id, new TimeTable(id, from, to));
        current = id;
    }

    String toPlainString(String id) {
        TimeTable tt = tables.get(id);
        String val = tt.getFrom().format(DateTimeFormatter.ofPattern("d-MMM-yyyy"));
        val += (tt.getTo() != null && tt.getFrom().until(tt.getTo()).getYears() < 1) ? " to " + 
                tt.getTo().format(DateTimeFormatter.ofPattern("d-MMM-yyyy")) : " onwards ";
        if (id.equals(current)) {
            val += " (Current)";
        }
        if (id.equals(latest)) {
            val += " (Latest)";
        }
        return val;
    }

    ArrayList<Schedule> fetchCurrentTimeTable(String id) {
        //System.out.println("Fetching Current TT : "+LocalDateTime.now());
        try (Connection con = SqlConnect.getDatabaseConnection();
                Statement st = con.createStatement()) {
            if ("".equals(current)) {
                //System.out.println("Finding Current TT : "+LocalDateTime.now());
                if (isTablesOfAClass())
                {
                    String query
                            = "select `time table id`, `from`, `to` from schedule "
                            + "where (status = 'active' or status ='outgoing') and "
                            + "`class id`='" + id + "'";
                    ResultSet rs = st.executeQuery(query);
                    if (!rs.next()) {
                        return null;
                    }
                    String tid = rs.getString(1);
                    current = tid;
                    var v = rs.getDate(2);
                    LocalDate from = v != null ? v.toLocalDate() : null;
                    v = rs.getDate(3);
                    LocalDate till = v != null ? v.toLocalDate() : null;
                    addCurrentTimeTable(tid, from, till);
                }
                else
                {
                    String query = "select   distinct `from`,  "
                            + "ifnull(  (select min(`to`) from schedule   "
                            + "where `from` is not null and `from` <= curdate() "
                            + "and `to` >= curdate() and (`faculty id`='"+id+"' "
                            + "or concat('',`room id`)='"+id+"')  )"
                            + ", curdate() + interval 1 month       ) as `to`,"
                            + " `time table id`   from schedule  where "
                            + "`from` is not null and `from` <= curdate() "
                            + "and `to` >= curdate() and (`faculty id`='"+id+"' "
                            + "or concat('',`room id`)='"+id+"') "
                            + "order by `from` desc;";
                    System.out.println(query);
                    ResultSet rs = st.executeQuery(query);
                    HashSet<String> tids = new HashSet<>();
                    while(rs.next())
                    {
                        tids.add(rs.getString(3));
                        if(rs.isLast())
                            break;
                    }
                    LocalDate from = rs.getDate(1).toLocalDate();
                    LocalDate to = rs.getDate(1).toLocalDate();
                    tids.add(rs.getString(3));
                    addCurrentTimeTable(from.format(DateTimeFormatter.ISO_DATE),
                            from, to, tids);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TimeTables.class.getName()).log(Level.SEVERE, null, ex);
        }
        lastFetched = current;
        return fetchParticularTimeTable(current);
    }

    boolean isLatest(String tid) {
        return tid.equalsIgnoreCase(latest);
    }

    ArrayList<String> getTimeTablesList() {
        ArrayList<String> records = new ArrayList<>();
        tables.keySet().forEach(ttid -> {
            records.add(ttid);
        });
        return records;
    }

    ArrayList<Schedule> fetchTimeTable() {
        ArrayList<Schedule> schedule = fetchCurrentTimeTable(tablesOf);
        if (schedule == null || schedule.size() < 1) {

            schedule = fetchLatestTimeTable();
        }
        return schedule;
    }

    ArrayList<Schedule> fetchLatestTimeTable() {
        try (Connection con = SqlConnect.getDatabaseConnection();
                Statement st = con.createStatement()) {
            if (latest == null || "".equals(latest)) {
                if (isTablesOfAClass())
                {
                String query
                        = "select `time table id`, `from`, 'to' from schedule "
                        + "where (status = 'active' or status ='incoming') and "
                        + "`class id`='" + tablesOf + "'";
                ResultSet rs = st.executeQuery(query);
                if (!rs.next()) {
                    query = "select `time table id`, `from`, `to`, status from schedule "
                        + "where `from` = (select max(`from`) from schedule "
                            + "where `class id` ='"+tablesOf+"') and "
                        + "`class id`='" + tablesOf + "'";
                    rs = st.executeQuery(query);
                    if (!rs.next())
                        return null;
                }
                String tid = rs.getString(1);
                var v = rs.getDate(2);
                LocalDate from = v != null ? v.toLocalDate() : null;
                v = rs.getDate(3);
                LocalDate till = v != null ? v.toLocalDate() : null;
                addLatestTimeTable(tid, from, till);
                }
                else
                {
//                      query = "select distinct `from`,"
//                        + "if(`from` <= curdate() and `to` >= curdate(), 1, 0) as `current`,"
//                        + "if(`from` >= (select max(`from`) from schedule) , 1, 0) as `latest`,"
//                    + " from schedule where `from` is not null and "
//                    + "(`faculty id`='" + tablesOf + "' "
//                        + "or `room id`=" + tablesOf
//                        + ") order by `from` desc;";
//                
                    
//                    query = "select `time table id`, `from`, `to`, `status`"
//                        + " from schedule where `from` <= ? and `to` >= ? and "
//                        + "(`faculty id` = '"+tablesOf+"' or `room id` = "+tablesOf+")";
////                
//                    
//select   distinct `from`,  ifnull(  (select min(`to`) from schedule   
//where `from` is not null and `from` <= curdate() and `to` >= curdate() 
//and (`faculty id`='F00006' or concat('',`room id`)='F00006')  ), curdate() + interval 1 month       ) as `to`,
// `time table id`   from schedule  where `from` is not null and `from` <= curdate() and `to` >= curdate() 
//and (`faculty id`='F00006' or concat('',`room id`)='F00006') 
//order by `from` desc;
                    
//                    
//                          "select `from`,ifnull(min(`to`),"
//                            + " curdate() + interval 1 years) as `to`, `time table id`"
//                        + " from ( select `from`, `to`, `time table id` from schedule "
//                            + "where `from` is not null and "
//                            + "`from` >= max(`from`)  and "
//                        + "(`faculty id`='" + tablesOf + "' "
//                            + "or concat('', `room id`)='" + tablesOf
//                            + "') order by `from` desc);";
                    
                    String query = "select   distinct `from`,"
                            + "  ifnull(  (select min(`to`) from schedule " +
                            "where `from` is not null and `from` >= "
                            + "(select max(`from`) from schedule "
                            + "where `faculty id`='"+tablesOf+"' or concat('',`room id`)='"+tablesOf+"')"+
                    "and (`faculty id`='"+tablesOf+"' or "
                            + "concat('',`room id`)='"+tablesOf+"')  ),"
                            + " curdate() + interval 1 year) as `to`," +
" `time table id`   from schedule  where `from` is not null and `from` >= (select max(`from`) from schedule "
                            + "where `faculty id`='"+tablesOf+"' or concat('',`room id`)='"+tablesOf+"') " +
"and (`faculty id`='"+tablesOf+"' or concat('',`room id`)='"+tablesOf+"')" +
"order by `from` desc;";
                    System.out.println(query);
                    ResultSet rs = st.executeQuery(query);
                    HashSet<String> tids = new HashSet<>();
                    while(rs.next())
                    {
                        tids.add(rs.getString(3));
                        if(rs.isLast())
                            break;
                    }
                    LocalDate from = rs.getDate(1).toLocalDate();
                    LocalDate to = rs.getDate(1).toLocalDate();
                    tids.add(rs.getString(3));
                    addLatestTimeTable(from.format(DateTimeFormatter.ISO_DATE),
                            from, to, tids);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TimeTables.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fetchParticularTimeTable(latest);
    }

    ArrayList<Schedule> fetchLatestTimeTable(String id) {
        if (latest == null || "".equals(latest)) {
            tablesOf = id;
            if (!tables.containsKey(id)) {
                try (Connection con = SqlConnect.getDatabaseConnection();
                        Statement st = con.createStatement()) {
                    if (isTablesOfAClass())
                    {
                        String query
                                = "select `time table id`, `from`, 'to' from schedule "
                                + "where (status = 'active' or status ='incoming') and "
                                + "`class id`='" + tablesOf + "'";
                        ResultSet rs = st.executeQuery(query);
                        if (!rs.next()) 
                        {
                            return null;
                        }
                        String tid = rs.getString(1);
                        var v = rs.getDate(2);
                        LocalDate from = v != null ? v.toLocalDate() : null;
                        v = rs.getDate(3);
                        LocalDate till = v != null ? v.toLocalDate() : null;
                        addLatestTimeTable(tid, from, till);
                    }
                    else
                    {
    //                      query = "select distinct `from`,"
    //                        + "if(`from` <= curdate() and `to` >= curdate(), 1, 0) as `current`,"
    //                        + "if(`from` >= (select max(`from`) from schedule) , 1, 0) as `latest`,"
    //                    + " from schedule where `from` is not null and "
    //                    + "(`faculty id`='" + tablesOf + "' "
    //                        + "or `room id`=" + tablesOf
    //                        + ") order by `from` desc;";
    //                

    //                    query = "select `time table id`, `from`, `to`, `status`"
    //                        + " from schedule where `from` <= ? and `to` >= ? and "
    //                        + "(`faculty id` = '"+tablesOf+"' or `room id` = "+tablesOf+")";
    //                
                        String query = "select `from`,ifnull(min(`to`),"
                                + " curdate() + interval 1 years) as `to`, `time table id`"
                            + " from ( select `from`, `to`, `time table id` from schedule "
                                + "where `from` is not null and "
                                + "`from` >= max(`from`)  and "
                            + "(`faculty id`='" + tablesOf + "' "
                                + "or concat('', `room id`)='" + tablesOf
                                + "') order by `from` desc);";
                        System.out.println(query);
                        ResultSet rs = st.executeQuery(query);
                        HashSet<String> tids = new HashSet<>();
                        while(rs.next())
                        {
                            tids.add(rs.getString(3));
                            if(rs.isLast())
                                break;
                        }
                        LocalDate from = rs.getDate(1).toLocalDate();
                        LocalDate to = rs.getDate(1).toLocalDate();
                        tids.add(rs.getString(3));
                        addLatestTimeTable(from.format(DateTimeFormatter.ISO_DATE),
                                from, to, tids);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(TimeTables.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return fetchParticularTimeTable(latest);
    }

    ArrayList<Schedule> fetchParticularTimeTable(String id) {
        if (!tables.containsKey(id))
        {
            return new ArrayList<>();
        }
        TimeTable timeTable = tables.get(id);
        if (!timeTable.hasSchedule()) {
            //System.out.println("Latest TT not in memory: "+LocalDateTime.now());
            ////System.out.println("No TT in Cache, fetching from DB");
            ArrayList<Schedule> schedule = new ArrayList<>();
            try (Connection con = SqlConnect.getDatabaseConnection()) {
                Statement st = con.createStatement();
                String query;
                var tids = timeTable.classes.iterator();
                if(isTablesOfAClass())
                {
                    query = "select * from schedule where `time table id`='"+id+"'";
                }
                else
                {
                    
                    query = "select * from schedule "
                            + "where (`faculty id`='"+tablesOf+"' or `room id`='"
                            +tablesOf+"') and "
                            + "`time table id` in (";
                    while(tids.hasNext())
                    {
                        var v = tids.next();
                        query += "'"+v+"'";
                        if (tids.hasNext())
                            query += ",";
                    }
                    query += ")";
                }
                ////System.out.println(query);
                //System.out.println("executing query : "+LocalDateTime.now());
                ResultSet rs = st.executeQuery(query);
                //System.out.println("query executed : "+LocalDateTime.now());
                while (rs.next()) {

                    int sid = rs.getInt(1);
                    String tid = rs.getString(2);
                    var v = rs.getTimestamp(3);
                    LocalDateTime lastUpdated = v != null ? 
                            v.toLocalDateTime(): null;
                    int updatedBy = rs.getInt(4);
                    var val = rs.getDate(5);
                    LocalDate from = val != null ? val.toLocalDate() : null;
                    val = rs.getDate(6);
                    LocalDate to = val != null ? val.toLocalDate() : null;
                    Schedule.status status = Schedule.status.valueOf(rs.getString(7).toUpperCase());
                    String dayId = rs.getString(8);
                    String timeslotId = rs.getString(9);
                    String classId = rs.getString(10);
                    String subjectId = rs.getString(11);
                    String facultyId = rs.getString(12);
                    int roomId = rs.getInt(13);
                    schedule.add(new Schedule(
                            sid, tid, lastUpdated, updatedBy, from, to, status,
                            dayId, timeslotId, classId, subjectId, facultyId, roomId));
                }

                //System.out.println("Got Results : "+LocalDateTime.now());
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(TimeTableControl.class.getName()).log(Level.SEVERE, null, ex);
            }
            ////System.out.println("adding "+schedule.size()+" records");
            //System.out.println("Adding TT to memory: "+LocalDateTime.now());
            timeTable.setSchedule(schedule);
        }
        //System.out.println("Returning Latest TT : "+LocalDateTime.now());
        lastFetched = id;
        return timeTable.getSchedule();
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
    public static I_Class[] getClasses() {
        I_Class[] classes = null;
        try (Connection con = SqlConnect.getDatabaseConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select id,name,case when "
                    + "id in (select distinct `class id` from schedule) then '1'"
                    + " else '0' end from class");
            if (rs.last() == false) {
                return null;
            }
            int count = rs.getRow();
            rs.first();
            classes = new I_Class[count];
            for (int i = 0; i < count; i++, rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                boolean assigned = rs.getBoolean(3);
                classes[i] = new I_Class(id, name, assigned);
            }

        } catch (SQLException ex) {
            Logger.getLogger(TimeTableControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return classes;
    }

    public static Object[][] getAssignedFaculty() {
        Object[][] faculties = null;
        try (Connection con = SqlConnect.getDatabaseConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select `faculty id`,name,case when "
                    + "`faculty id` in (select distinct `faculty id` from schedule) then '1'"
                    + " else '0' end from faculty");
            if (rs.last() == false) {
                return null;
            }
            int count = rs.getRow();
            rs.first();
            faculties = new Object[count][];
            for (int i = 0; i < count; i++, rs.next()) {
                String id = rs.getString("faculty id");
                String name = rs.getString("name");
                boolean assigned = rs.getBoolean(3);
                faculties[i] = new Object[]{id, name, assigned};
            }

        } catch (SQLException ex) {
            Logger.getLogger(TimeTableControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return faculties;
    }

    public static String[][] getAvailableFacultyDetails(String timeSlotId,
            String dayId, String classId, LocalDate wef) {
        String[][] details = null;
        String query = "select f.`faculty id`,f.name,csf.`subject id`,"
                + "`subject name` from faculty f,class_subject_faculty csf,"
                + "subject sub where f.`faculty id`=csf.`faculty id` "
                + "and csf.`subject id`=sub.`subject id` and csf.`class id`="
                + "? and f.`faculty id` "
                + "not in (select s.`faculty id` from schedule s where "
                + "`timeslot id`=? and `day id`=?"
                + " and s.`class id`!=? and from <= ? amd to >= ?)";
        try (Connection con = SqlConnect.getDatabaseConnection()) {
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, classId);
            st.setString(2, timeSlotId);
            st.setString(3, dayId);
            st.setString(4, classId);
            st.setString(5, wef.format(DateTimeFormatter.BASIC_ISO_DATE));
            st.setString(6, wef.format(DateTimeFormatter.BASIC_ISO_DATE));
            ResultSet rs = st.executeQuery();
            if (rs.last() == false) {
                return null;
            }
            int count = rs.getRow();
            rs.first();
            details = new String[count][];
            for (int i = 0; i < count; i++, rs.next()) {
                String id = rs.getString(1);
                String name = rs.getString(2);
                String subId = rs.getString(3);
                String subject = rs.getString(4);
                details[i] = new String[]{id, name, subId, subject};
            }

        } catch (SQLException ex) {
            Logger.getLogger(TimeTableControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return details;
    }

    static boolean isFacultyFree(String facId, String dayId, String timeSlotId,
            LocalDate wef) {

        String query = "select `faculty id` from schedule where "
                + "`timeslot id`=? and `day id`=?"
                + " and `faculty id` = ? and from <= ? and to >= ?";
        try (Connection con = SqlConnect.getDatabaseConnection();
            PreparedStatement st = con.prepareStatement(query);) {
            ////System.out.println(query);
            st.setString(1, timeSlotId);
            st.setString(2, dayId);
            st.setString(3, facId);
            st.setString(4, wef.format(DateTimeFormatter.ISO_DATE));
            st.setString(5, wef.format(DateTimeFormatter.ISO_DATE));
            ResultSet rs = st.executeQuery();
            return !rs.next();
        } catch (Exception exc) {

        }
        return false;
    }

    static ArrayList<FacSub> getFacultySubjectDetails(String classId,
            LocalDate from, LocalDate to) {
        ArrayList<FacSub> details = new ArrayList<>();
        try (Connection con = SqlConnect.getDatabaseConnection()) {
            Statement st = con.createStatement();
            String query = "select f.`faculty id`,f.name,csf.`subject id`,"
                    + "`subject name`,`LECTURES REQUIRED`"
                    + " from faculty f,class_subject_faculty csf,"
                    + "subject sub where f.`faculty id`=csf.`faculty id` "
                    + "and csf.`subject id`=sub.`subject id` and csf.`class id`="
                    + "'" + classId + "'";
            ResultSet rs = st.executeQuery(query);
            if (rs.last() == false) {
                return null;
            }
            int count = rs.getRow();
            rs.first();
            for (int i = 0; i < count; i++, rs.next()) {
                String id = rs.getString(1);
                String name = rs.getString(2);
                String subId = rs.getString(3);
                String subject = rs.getString(4);
                int req = rs.getInt(5);
                ArrayList<Schedule> schedule = getFacultyTimeTable(id, from, to);
                HashSet<String> facSchedule = new HashSet<>();
                for (Schedule s : schedule) {
                    if (!classId.equals(s.getClassId())) {
                        facSchedule.add(s.getDayId() + "_" + s.getTimeslotId());
                    }
                }
                details.add(new FacSub(id, name, subId, subject, req, facSchedule));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TimeTableControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return details;
    }

    static TimeTables getTimeTablesList(String id) {
        //System.out.println("Getting TimeTables("+id+") ID : "+LocalDateTime.now());
        return new TimeTables(id);
    }

    static ArrayList<Schedule> getLatestClassTimeTable(String id) {
        return new TimeTables().fetchLatestTimeTable(id);
    }

    public static ArrayList<Schedule> getFacultyTimeTable(String id, LocalDate from,
            LocalDate to) {
        ArrayList<Schedule> schedule = new ArrayList<>();
        try (Connection con = SqlConnect.getDatabaseConnection()) {
            Statement st = con.createStatement();
            if (to == null) {
                to = LocalDate.now().plusYears(5);
            }
            System.out.println(from+" "+to);
            String query = "select `day id`,`timeslot id`,`class id`,`subject id` "
                    + "from schedule where `faculty id`='" + id + "' and `from` <= '"
                    + to.format(DateTimeFormatter.ISO_DATE) + "'"
                    + "  and `to` >= '"
                    + from.format(DateTimeFormatter.ISO_DATE) + "'";
            System.out.println(query);
            ResultSet rs = st.executeQuery(query);
            if (rs.last() == false) {
                return new ArrayList<>();
            }
            int count = rs.getRow();
            rs.first();
            for (int i = 0; i < count; i++, rs.next()) {
                String dayId = rs.getString(1);
                String timeslotId = rs.getString(2);
                String classId = rs.getString(3);
                String subjectId = rs.getString(4);
                schedule.add(new Schedule(dayId, timeslotId, classId, subjectId,
                        id, -1));
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(TimeTableControl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return schedule;
    }

    public static ArrayList<Schedule> getFacultyTimeTable(String id) {
        ArrayList<Schedule> schedule = new ArrayList<>();
        String today = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        try (Connection con = SqlConnect.getDatabaseConnection()) {
            String query = "select * from schedule where `faculty id` = ? "
                    + " and `from` <= ? and `to` >= ?";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, id);
            st.setString(2, today);
            st.setString(3, today);
            System.out.println(query);
            ResultSet rs = st.executeQuery();
            if (rs.last() == false) {
                return null;
            }
            int count = rs.getRow();
            rs.first();
            for (int i = 0; i < count; i++, rs.next()) {
                    int sid = rs.getInt(1);
                    String tid = rs.getString(2);
                    var v = rs.getTimestamp(3);
                    LocalDateTime lastUpdated = v != null ? 
                            v.toLocalDateTime(): null;
                    int updatedBy = rs.getInt(4);
                    var val = rs.getDate(5);
                    LocalDate from = val != null ? val.toLocalDate() : null;
                    val = rs.getDate(6);
                    LocalDate to = val != null ? val.toLocalDate() : null;
                    Schedule.status status = Schedule.status.valueOf(
                            rs.getString(7).toUpperCase());
                    String dayId = rs.getString(8);
                    String timeslotId = rs.getString(9);
                    String classId = rs.getString(10);
                    String subjectId = rs.getString(11);
                    String facultyId = rs.getString(12);
                    int roomId = rs.getInt(13);
                    schedule.add(new Schedule(
                            sid, tid, lastUpdated, updatedBy, from, to, status,
                            dayId, timeslotId, classId, subjectId, facultyId,
                            roomId));
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(TimeTableControl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return schedule;
    }

    public static int getRoomId(String classId, String subjectId, String facultyId) {
        try (Connection con = SqlConnect.getDatabaseConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select `room id` from class_subject_faculty "
                    + "where `class id`='" + classId + "' and `subject id`='" + subjectId + "' "
                    + "and `faculty id`='" + facultyId + "'");
            if (rs.next()) {
                int roomid = rs.getInt(1);
                con.close();
                return roomid;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TimeTableControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public static final int CRT_SUCCESS = 1;
    public static final  int CRT_FAILED_AD = 2;
    public static final  int CRT_FAILED_ER = 4;
    public static final int CRT_FAILED_SQ = 3;
    
    public static int createTimeTable(String classId, ArrayList<Schedule> schedule) {
        return createTimeTable(classId, schedule, LocalDate.now().plusDays(1));
    }

    public static int createTimeTable(String classId, ArrayList<Schedule> schedule,
            LocalDate wef) {
        if (LoginF.getUserRole() != role.ADMIN)
        {
            return CRT_FAILED_AD;
        }
        Connection con = null;
        try {
            con = SqlConnect.getDatabaseConnection();
            con.setAutoCommit(false);
            Statement st = con.createStatement();
            String q = "select max(substr(`time table id`,8,3)) from schedule"
                    + " where `class id` = ?";
            PreparedStatement psMaxv = con.prepareStatement(q);
            
            psMaxv.setString(1, classId);
            ResultSet vrs = psMaxv.executeQuery();
            vrs.next();
            int v = vrs.getInt(1) + 1;
            String new_ttid = classId + "_" + String.format("%03d", v);
            String ttid = new TimeTables(classId).getCurrent();
            String query = "UPDATE schedule SET `status` = 'outgoing', `to` = ?"
                    + "where `time table id` = ?";
            PreparedStatement psUpdate = con.prepareStatement(query);
            psUpdate.setString(1, wef.minusDays(1).format(
                    java.time.format.DateTimeFormatter.ISO_DATE));
            psUpdate.setString(2, ttid);
            int ans = psUpdate.executeUpdate();
            query = "insert into schedule(`day id`,`timeslot id`,`class id`,"
                    + "`subject id`,`faculty id`,`room id`,`from`,`to`, `status`, `updated by`,"
                    + "`time table id`) values"
                    + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement psInsert = con.prepareStatement(query);
            psInsert.setString(7, wef.
                    format(DateTimeFormatter.BASIC_ISO_DATE));
            psInsert.setString(8, LocalDate.now().plusYears(20).
                    format(DateTimeFormatter.BASIC_ISO_DATE));
            psInsert.setString(9, "incoming");
            psInsert.setInt(10, Integer.valueOf(LoginF.getUserId()));
            psInsert.setString(11, new_ttid);
            for (Schedule scheduleItem : schedule) {
                // System.out.println(ans+" rows affected");
                psInsert.setString(1, scheduleItem.getDayId());
                psInsert.setString(2, scheduleItem.getTimeslotId());
                psInsert.setString(3, scheduleItem.getClassId());
                psInsert.setString(4, scheduleItem.getSubjectId());
                psInsert.setString(5, scheduleItem.getFacultyId());
                psInsert.setInt(6, scheduleItem.getRoomId());
                psInsert.executeUpdate();
            }
            con.commit();
            ScheduleScripts.runUpdateScheduleStatus();
            return CRT_SUCCESS;
        } 
        catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(TimeTableControl.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(TimeTableControl.class.getName()).log(Level.SEVERE, null, ex);
            return CRT_FAILED_SQ;
        }
        catch(Exception ex)
        {
            Logger.getLogger(TimeTableControl.class.getName()).log(Level.SEVERE, null, ex);    
        }
        return CRT_SUCCESS;
    }

    public static Subject[] getSubjects(String classId) {
        try (Connection con = SqlConnect.getDatabaseConnection()) {
            Subject subjects[] = null;
            Statement st = con.createStatement();
            String query = "select * from subject sub where "
                    + "sub.`subject id`in (select r.`subject id` from class_subject_faculty r where `class id`='" + classId + "')";
            //////System.out.println(query);
            ResultSet rs = st.executeQuery(query);
            if (rs.last() == false) {
                return null;
            }
            int count = rs.getRow();
            rs.first();
            subjects = new Subject[count];
            for (int i = 0; i < count; i++, rs.next()) {
                String subId = rs.getString("subject id");
                String subName = rs.getString("subject name");
                int lectRequired = rs.getInt("LECTURES REQUIRED");
                subjects[i] = new Subject(subId, subName, lectRequired);

            }
            con.close();
            return subjects;
        } catch (SQLException ex) {
            Logger.getLogger(TimeTableControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Subject getSubject(String subjectId) {
        try {
            Subject subject;
            try (Connection con = SqlConnect.getDatabaseConnection()) {
                Statement st = con.createStatement();
                String query = "select * from subject where "
                        + "`subject id`='" + subjectId + "'";
                // System.out.println(query);
                ResultSet rs = st.executeQuery(query);
                if (rs.next() == false) {
                    return null;
                }
                String subId = rs.getString("subject id");
                String subName = rs.getString("subject name");
                int lectRequired = rs.getInt("LECTURES REQUIRED");
                subject = new Subject(subId, subName, lectRequired);
            }
            return subject;
        } catch (SQLException ex) {
            Logger.getLogger(TimeTableControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
