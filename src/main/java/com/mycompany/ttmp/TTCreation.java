package com.mycompany.ttmp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.mycompany.ttmp.Utility;
import java.util.Arrays;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
     class FacSub
    {
        private String facId;
        private String facName;
        private String subId;
        private String subName;
        int index = -1;
        private HashSet<String> facSchedule;
        private String color = "black";

        int lectReq, lectAssigned = 0;
        public String getSubName() {
            return subName;
        }
        
        public boolean isFree(String dayId, String timeslotId)
        {
            return !facSchedule.contains(dayId+"_"+timeslotId);
        }
        
        public void setSubName(String subName) {
            this.subName = subName;
        }

        public FacSub(String facId, String facName, String subId, String subName,
                int lectReq) {
            this.facId = facId;
            this.facName = facName;
            this.subId = subId;
            this.subName = subName;
            this.lectReq = lectReq;
            this.facSchedule = new HashSet<>();
        }
        public FacSub(String facId, String facName, String subId, String subName,
                int lectReq, String color) {
            this.facId = facId;
            this.facName = facName;
            this.subId = subId;
            this.subName = subName;
            this.lectReq = lectReq;
            this.facSchedule = new HashSet<>();
            this.color = color;
        }
        public FacSub(String facId, String facName, String subId, String subName,
                int lectReq, HashSet<String> facSchedule) {
            this.facId = facId;
            this.facName = facName;
            this.subId = subId;
            this.subName = subName;
            this.lectReq = lectReq;
            this.facSchedule = facSchedule;
        }
        public FacSub(String facId, String facName, String subId, String subName,
                int lectReq,String color, HashSet<String> facSchedule) {
            this.facId = facId;
            this.facName = facName;
            this.subId = subId;
            this.subName = subName;
            this.lectReq = lectReq;
            this.facSchedule = facSchedule;
            this.color = color;
        }
        public FacSub(int index, String facId, String facName, String subId,
                String subName, int lectReq) {
            this.index = index;
            this.facId = facId;
            this.facName = facName;
            this.subId = subId;
            this.subName = subName;
            this.lectReq = lectReq;
            this.facSchedule = new HashSet<>();
        }
        public FacSub(int index, String facId, String facName, String subId,
                String subName, int lectReq, String color) {
            this.index = index;
            this.facId = facId;
            this.facName = facName;
            this.subId = subId;
            this.subName = subName;
            this.lectReq = lectReq;
            this.color = color;
            this.facSchedule = new HashSet<>();
        }
        public FacSub(int index, String facId, String facName, String subId,
                String subName, int lectReq, HashSet<String> facSchedule) {
            this.index = index;
            this.facId = facId;
            this.facName = facName;
            this.subId = subId;
            this.subName = subName;
            this.lectReq = lectReq;
            this.facSchedule = facSchedule;
        }
        public FacSub(int index, String facId, String facName, String subId,
                String subName, int lectReq, HashSet<String> facSchedule, String color) {
            this.index = index;
            this.facId = facId;
            this.facName = facName;
            this.subId = subId;
            this.subName = subName;
            this.lectReq = lectReq;
            this.color = color;
            this.facSchedule = facSchedule;
        }
        
        public FacSub(int index, FacSub facSub)
        {
            this.index = index;
            facId = facSub.getFacId();
            facName = facSub.getFacName();
            subId = facSub.getSubId();
            subName = facSub.getSubName();
            lectReq = facSub.getLectReq();
            lectAssigned = facSub.getLectAssigned();
            this.facSchedule = facSub.facSchedule;
            color = facSub.getColor();
        }
        public FacSub(FacSub facSub)
        {
            index = facSub.index;
            facId = facSub.getFacId();
            facName = facSub.getFacName();
            subId = facSub.getSubId();
            subName = facSub.getSubName();
            lectReq = facSub.getLectReq();
            lectAssigned = facSub.getLectAssigned();
            facSchedule = facSub.facSchedule;
            color =facSub.getColor();
        }

        public int getLectReq() {
            return lectReq;
        }

        public void setLectReq(int lectReq) {
            this.lectReq = lectReq;
        }
        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public int getLectAssigned() {
            return lectAssigned;
        }

        public void incLect() {
            lectAssigned++;
        }
        public void decLect() {
            lectAssigned--;
        }
        public void resetLect() {
            lectAssigned = 0;
        }
        

        public String getFacId() {
            return facId;
        }

    @Override
    public String toString() {
        return "FacSub{" + "facId=" + facId + ", facName=" + facName + ", subId=" + subId + ", subName=" + subName + ", lectReq=" + lectReq + ", lectAssigned=" + lectAssigned + '}';
    }

        public void setFacId(String facId) {
            this.facId = facId;
        }

        public String getFacName() {
            return facName;
        }

        public void setFacName(String facName) {
            this.facName = facName;
        }

        public String getSubId() {
            return subId;
        }

        public void setSubId(String subId) {
            this.subId = subId;
        }
        
        public boolean meetsRequirements()
        {
            return lectAssigned >= lectReq;
        }
    }

class FastSelection
{
    int noOfDays, noOfPeriods;
    HashMap<String, HashMap<String, SelectionValue>> selections = new HashMap<>();
    CellValue[][] cellValues;
    void add(String id, int day, int period, SelectionValue value)
    {
        String oldId = cellValues[day][period].id;
        cellValues[day][period] = new CellValue(id, value);
        if (oldId!= null && !oldId.isBlank())
        {
            if (id.equals(oldId))
                selections.get(oldId).replace(day+"_"+period, value);
            else
                selections.get(oldId).remove(day+"_"+period);
        }
        if (id != null && !id.isBlank())
        {
            selections.putIfAbsent(id, new HashMap<>());
            selections.get(id).put(day+"_"+period, value);
        }
    }
    void remove(String id)
    {
        for (var indices : selections.get(id).keySet())
        {
            var day = Integer.valueOf(indices.substring(0, indices.indexOf("_")));
            var period = Integer.valueOf(indices.substring(indices.indexOf("_") + 1));
            cellValues[day][period] = new CellValue();
        }
        selections.get(id).clear();
        
    }
    enum SelectionValue
    {
        None, Select, UnSelect
    }
    FastSelection()
    {
        noOfDays = DayControl.getAllDays().length;
        noOfPeriods = TimeSlotControl.getAllSlots().length;
        selections = new HashMap<>();
        cellValues = new CellValue[noOfDays][noOfPeriods];
        for (int i = 0; i < noOfDays; i++)
            for(int j = 0; j < noOfPeriods; j++)
                cellValues[i][j] = new CellValue();
    }
    void replace(FastSelection fastSelection)
    {
        noOfDays = fastSelection.noOfDays;
        noOfPeriods = fastSelection.noOfDays;
        selections = new HashMap<>();
        fastSelection.selections.keySet().forEach(k -> {
            HashMap<String, FastSelection.SelectionValue> copy = new HashMap<>();
            fastSelection.selections.get(k).keySet().forEach(k2 -> {
                copy.putIfAbsent(k2, fastSelection.selections.get(k).get(k2));
            });
            selections.putIfAbsent(k, copy);
        });
        cellValues = new CellValue[noOfDays][noOfPeriods];
        for(int i = 0; i < noOfDays; i++)
            System.arraycopy(fastSelection.cellValues[i], 0, cellValues[i], 0, noOfPeriods);
    }
    FastSelection(FastSelection fastSelection)
    {
        noOfDays = fastSelection.noOfDays;
        noOfPeriods = fastSelection.noOfDays;
        selections = new HashMap<>();
        fastSelection.selections.keySet().forEach(k -> {
            HashMap<String, FastSelection.SelectionValue> copy = new HashMap<>();
            fastSelection.selections.get(k).keySet().forEach(k2 -> {
                copy.putIfAbsent(k2, fastSelection.selections.get(k).get(k2));
            });
            selections.putIfAbsent(k, copy);
        });
        cellValues = new CellValue[noOfDays][noOfPeriods];
        for(int i = 0; i < noOfDays; i++)
            for(int j = 0; j < noOfPeriods; j++)
                cellValues[i][j] = fastSelection.cellValues[i][j];
    }
    class CellValue
    {
        String id;
        SelectionValue value;
        CellValue(String id, SelectionValue value) {
            if (id == null)
                id = "";
            this.id = id;
            this.value = value;
        }   
        CellValue()
        {
            this.id = "";
            this.value = SelectionValue.None;
        }
    }
}

public class TTCreation extends javax.swing.JFrame {
    TimeSlot[] periods;
    Day[] days;
    DefaultTableModel timeTableModel;
    DefaultTableModel subjectTableModel;
    private I_Class[] classes;
    private I_Class selectedClass;
    private final role roleOfUser;
    private ArrayList<FacSub> availableFacSubDetailsList = new ArrayList<>();
    private int selectedFacSubIndex=0;
    private int[] selectedRowColumn=new int[2];
    private String [][] timeTable;
    private final HashMap<String, FacSub> allFacSubDetailsMap = new HashMap<>();
    private ArrayList<FacSub> allFacSubDetailsList = new ArrayList<>();
    private DefaultListModel<String> listModel;
    private TimeTables timeTables;
    /**
     * Creates new form TTCreation
     */
    private void initTables()
    {
        initTables(null);
    }
    private void initTables(String tid){
        if(timeTables.getTimeTablesList().isEmpty())
            jLoadButton.setVisible(false);
        else
            jLoadButton.setVisible(true);
        periods=TimeSlotControl.getAllSlots();
        days=DayControl.getAllDays();
        timeTableModel=(DefaultTableModel)jTimeTable.getModel();
        timeTableModel.setRowCount(0);
        timeTableModel.setColumnCount(0);
        subjectTableModel=(DefaultTableModel)jSubjectAllotedTable.getModel();
        timeTableModel.addColumn("DAY \\ TIME");
        listModel=new DefaultListModel<>();
        jList1.setModel(listModel);
        for (TimeSlot period : periods) {
            timeTableModel.addColumn(period.toSimpleString());
        }
        for (Day day : days) {
            System.out.println(day.getDayId() + " " + day.getDayName());
            timeTableModel.addRow(new Object[]{day.getDayName()});
        }
        timeTable=new String[days.length][periods.length];
        allFacSubDetailsList.clear();
        allFacSubDetailsMap.clear();
        //subjects=TimeTableControl.getSubjects(selectedClass.getId());
//        System.out.println(subjects.length);
        var v = TimeTableControl.getFacultySubjectDetails(selectedClass.getId(),
                LocalDate.now().atStartOfDay().plusDays(1).toLocalDate(), null);
        for (int i = 0; i < v.size(); i++)
        {
            var f = new FacSub(i, v.get(i));
            f.setColor(Utility.colors[i]);
            allFacSubDetailsMap.put(f.getSubId(), f);
            allFacSubDetailsMap.put(f.getFacId(), f);
            allFacSubDetailsList.add(f);
        }
        if(allFacSubDetailsMap==null || allFacSubDetailsMap.isEmpty()){
            JOptionPane.showMessageDialog(this,"Subjects and teachers have not been assigned for this class,\n"
                    + " please do that using classroom manager before proceeding");
            leavePage();
            return;
        }
            
        System.out.println(subjectTableModel.getRowCount());
        subjectTableModel.setRowCount(0);
        allFacSubDetailsList.forEach(facSub -> {
            subjectTableModel.addRow(
                    new Object[]{Utility.colorCodeValue(facSub.getSubName(),
                            facSub.getColor()),facSub.getLectReq(),0});
        });
        System.out.println(subjectTableModel.getRowCount());
        if (tid == null)
            initTableData(timeTables.fetchLatestTimeTable());
        else
            initTableData(timeTables.fetchParticularTimeTable(tid));
        jTimeTable.setEnabled(true);
}
    
    private void clearTable()
    {
        timeTable = new String[days.length][periods.length];
        timeTableModel.setRowCount(0);
        for (Day day : days) {
            System.out.println(day.getDayId() + " " + day.getDayName());
            timeTableModel.addRow(new Object[]{day.getDayName()});
        }
            
        subjectTableModel.setRowCount(0);
        allFacSubDetailsList.forEach(facSub -> {
            facSub.resetLect();
            subjectTableModel.addRow(
                    new Object[]{Utility.colorCodeValue(facSub.getSubName(),
                            facSub.getColor()),facSub.getLectReq(),0});
        });
    }
    
    private void initTableData(ArrayList<Schedule> schedule){
        if(schedule==null || schedule.isEmpty())
            return;
        clearTable();
        for(Schedule record : schedule){
            int period=-1,day=-1;
            
            for(int i=0;i<periods.length;i++)
                if(periods[i].getId().equalsIgnoreCase(record.getTimeslotId())){
                    period=i;
                    break;
                }
            for(int i=0;i<days.length;i++)
                if(days[i].getDayId().equalsIgnoreCase(record.getDayId())){
                    day=i;
                    break;
                }
            if(period<0 || day <0)
                  continue;
            String subjectName=null,subjectId=record.getSubjectId(),facultyId
                    =record.getFacultyId();
            String fac_sub=facultyId+"_"+subjectId;
            String facName = "";
            var facSub = allFacSubDetailsMap.get(subjectId);
            facName = facSub.getFacName();
            subjectName = facSub.getSubName();
            timeTable[day][period]=fac_sub;
            increaseSubCount(subjectId);
        timeTableModel.setValueAt(
                Utility.colorCodeValue(facName + " " + subjectName,
                        facSub.getColor()), day, period+1);
        }
    }
    public TTCreation() {
        initComponents();
        roleOfUser=LoginF.getUserRole();
        jFacultySelectButton.setEnabled(false);
        jSubmitButton.setEnabled(false);
        jFastSelectButton.setEnabled(false);
        classes=TimeTableControl.getClasses();
        jClassBox.removeAllItems();
        jClassBox.addItem("select class");
        for (I_Class classe : classes) {
            jClassBox.addItem(classe.getName()+(classe.getAssignmentStatus()? "[ UPDATE ]":"[ CREATE ]"));
        }
        jClassSelectButton.setEnabled(false);
        jClearSelectionButton.setEnabled(false);
        jTimeTable.setEnabled(false);
    }
    
    public TTCreation(TimeTables timeTables, String tid) {
        initComponents();
        roleOfUser=LoginF.getUserRole();
        jFacultySelectButton.setEnabled(false);
        jSubmitButton.setEnabled(false);
        jFastSelectButton.setEnabled(false);
        classes=TimeTableControl.getClasses();
        jClassBox.removeAllItems();
        this.timeTables = timeTables;
        selectedClass = ClassControl.getClass(timeTables.classId);
        jClassBox.addItem("select class");
        String svalue = "";
        for (I_Class classe : classes) {
            String value = classe.getName()+
                    (classe.getAssignmentStatus()? "[ UPDATE ]":"[ CREATE ]");
            jClassBox.addItem(value);
            if (timeTables.classId.equals(classe.getId()))
                svalue = value;
        }
        jClassSelectButton.setEnabled(false);
        jClassBox.setSelectedItem(svalue);
        jClassBox.setEnabled(false);
        jClearSelectionButton.setEnabled(false);
        jTimeTable.setEnabled(false);
        initTables(tid);
    }
    private boolean requirementsMet(ArrayList<FacSub> facSubList)
    {
      return  facSubList.stream().allMatch(v -> (v.meetsRequirements()));
    }
    private ArrayList<FacSub> getAvailableFaculties(ArrayList<FacSub> facSubList,
            String dayId, String timeslotId)
    {
        return getAvailableFaculties(facSubList, dayId, timeslotId, false);
    }
    private ArrayList<FacSub> getAvailableFaculties(ArrayList<FacSub> facSubList,
            String dayId, String timeslotId,boolean log)
    {
        if(log)
            System.out.println("-----------GetAvailableFaculty----------");
        ArrayList<FacSub> availableFaculties = new ArrayList<>();
        for (var facSub : facSubList)
        {
            if(log)
                System.out.println(facSub.getFacId()+"(free, reqMet) : "+
                        facSub.isFree(dayId, timeslotId)+" , "
                + facSub.meetsRequirements());
            if (facSub.isFree(dayId, timeslotId) && !facSub.meetsRequirements())
            {
                if (log)
                    System.out.println(facSub.getFacId()+" Available");
                availableFaculties.add(facSub);
            }
            else{
                if (log)
                    System.out.println(facSub.getFacId()+"Not Available");
            }
        }
        if(log)
            System.out.println("----------------------------------");
        return availableFaculties;
    }
    
    private boolean canBeAssigned(ArrayList<FacSub> facSubList,
            String dayId, String timeslotId)
    {
        for (var facSub : facSubList)
        {
            if (facSub.isFree(dayId, timeslotId) )
            {
             return true;   
            }
        }
        return false;
    }
    public void sugestSchedule(){
        ArrayList<FacSub> facSubToAssign = new ArrayList<>();
        allFacSubDetailsList.forEach(facSub -> {
            facSubToAssign.add(new FacSub(facSub));
        });
            ArrayList<int[]> allDays = new ArrayList<>();
            for (int i = 0;i < days.length; i++)
            {
                int k = 0;
                while(true)
                {
                    if (k == periods.length || 
                        (timeTable[i][k]== null || timeTable[i][k].isBlank()
                            ))
                        break;
                    k++;
                }
                allDays.add(new int[]{i, k});
            }
        while(!requirementsMet(facSubToAssign))
        {
            Random random = new Random();
                if (allDays.size() < 1)
                    break;
                ArrayList<int[]> daysToChoose = new ArrayList<>();
                int maxv = allDays.get(0)[1], minv = allDays.get(0)[1];
                for (int i = 1; i < allDays.size(); i++)
                {
                    //System.out.println("day : "+allDays.get(i)[0]+" "+allDays.get(i)[1]);
                    if (allDays.get(i)[1] > maxv)
                        maxv = allDays.get(i)[1];
                    if(allDays.get(i)[1] < minv)
                        minv = allDays.get(i)[1];
                }
                if ((maxv - minv) >= 1)
                {
                    for (int[] d : allDays)
                    {
                        if (d[1] != maxv)
                            daysToChoose.add(d);
                    }
                }
                else
                    daysToChoose = new ArrayList(allDays);
                for (var d : daysToChoose)
                {
                    //System.out.println("days : "+d[0]+" " + d[1]);
                }
                int dayI = random.nextInt(daysToChoose.size());
                int day = daysToChoose.get(dayI)[0];
                //System.out.println("day choosen "+dayI+" "+day);
                int tp = -1;
                boolean assigned = false;
                int initday = day;
                do
                {
                    tp++;
                    if (tp == periods.length)
                    {
                        tp = 0;
                        day = ++day % days.length;
                        if (initday == day)
                            break;
                    }
                    if (timeTable[day][tp] != null && !timeTable[day][tp].isBlank())
                        continue;
                    ArrayList<FacSub> facultyToChoose = getAvailableFaculties(facSubToAssign,
                            days[day].getDayId(),  periods[tp].getId());
                    if (facultyToChoose.size() < 1)
                    {
                        if (requirementsMet(facSubToAssign))
                            break;
                        continue;
                    }
                    int subI = random.nextInt(facultyToChoose.size());
                    FacSub facSub = facultyToChoose.get(subI);
                        String fac_sub=facSub.getFacId()+"_"+facSub.getSubId();
                        timeTable[day][tp]=fac_sub;
                        increaseSubCount(facSub.getSubId());
                        facSub.incLect();
                        timeTableModel.setValueAt(
                           facSub.getFacName()+
                        " "+facSub.getSubName(), day, tp+1);
                        assigned = true;
                        allDays.get(allDays.indexOf(daysToChoose.get(dayI)))[1]++;
                }
                while(!assigned);
                //System.out.println(allDays);
                ArrayList<int[]> indexToRemove = new ArrayList<>();
                allDays.stream().filter(d -> (d[1] >= periods.length)).forEachOrdered(d -> {
                    indexToRemove.add(d);
                });
                indexToRemove.forEach(i -> {
                    allDays.remove(i);
                });
                
        }
    }
    public void sugestSchedulev2(){
        ArrayList<FacSub> facSubToAssign = new ArrayList<>();
        allFacSubDetailsList.forEach(facSub -> {
            facSubToAssign.add(new FacSub(facSub));
        });
        ArrayList<int[]> periodsList = new ArrayList<>();
        for (int i = 0;i < periods.length; i++)
        {
            ArrayList<Integer> data = new ArrayList<>();
            for (int j = 0;j < days.length; j++)
            {
                if (timeTable[j][i] == null || timeTable[j][i].isBlank())
                    data.add(j);
            }
            Collections.shuffle(data);
            for (var d : data)
            {
                periodsList.add(new int[]{d, i});
            }
        }
        Random random = new Random();
        for (var periodData : periodsList)
        {
            int day = periodData[0], period = periodData[1];
            System.out.println("Assigning for : "+day+" "+period);
            ArrayList<FacSub> facultyToChoose = getAvailableFaculties(facSubToAssign,
            days[day].getDayId(),  periods[period].getId());
                    if (facultyToChoose.size() < 1)
                    {
                        getAvailableFaculties(facSubToAssign,
                            days[day].getDayId(),  periods[period].getId(), true);
                        System.out.println("Assignment : Skipped\n");
                        if (requirementsMet(facSubToAssign))
                            break;
                        continue;
                    }
                    int subI = random.nextInt(facultyToChoose.size());
                    FacSub facSub = facultyToChoose.get(subI);
                        String fac_sub=facSub.getFacId()+"_"+facSub.getSubId();
                        timeTable[day][period]=fac_sub;
                        increaseSubCount(facSub.getSubId());
                        facSub.incLect();
                        timeTableModel.setValueAt(
                           facSub.getFacName()+
                        " "+facSub.getSubName(), day, period+1);
                        System.out.println("Assignment : "+fac_sub+"\n");
            if(requirementsMet(facSubToAssign))
                break;
        }
    }
    String[][] backtrackSchedule(int index, String[][] tt,
            ArrayList<int[]> periodsList, ArrayList<FacSub> facSubToAssign)
    {
        System.out.println("in bcktrck : "+index);
        if (requirementsMet(facSubToAssign))
        {
            System.out.println("Task Complete");
            return tt;
        }
        if (index >= periodsList.size())
        {
            System.out.println("Task Failed");
            return null;
        }
        int day = periodsList.get(index)[0], period = periodsList.get(index)[1];
        System.out.println("for "+day+","+period);
        ArrayList<FacSub> facultyToChoose = getAvailableFaculties(facSubToAssign,
            days[day].getDayId(),  periods[period].getId());
                    if (facultyToChoose.size() < 1)
                    {
                        System.out.println("No Faculty Available");
                        if(canBeAssigned(facSubToAssign, days[day].getDayId(), 
                                periods[period].getId()))
                        {
                            System.out.println("Can be Assigned , bakctracking");
                            return null;
                        }
                        else {
                            System.out.println("Cannot be assigned, moving on");
                            return backtrackSchedule(index + 1, tt, 
                    periodsList, facSubToAssign);
                        }
                    }
        Collections.shuffle(facultyToChoose);
        for (var fac: facultyToChoose)
        {
            System.out.println("trying " + fac.getFacId());
            tt[day][period] = fac.getFacId()+"_"+fac.getSubId();
            fac.incLect();
            String[][] result = backtrackSchedule(index + 1, tt, 
                    periodsList, facSubToAssign);
            if (result != null)
            {
                System.out.println("Found sol with "+day+","+period+" "+fac.getFacId());
                return result;
            }
            tt[day][period] = null;
            fac.decLect();
        }
        return null;
    }
    public void sugestSchedulev3(){
        System.out.println("-------------[ suggestv3 ]-----------------------");
        ArrayList<FacSub> facSubToAssign = new ArrayList<>();
        System.out.println("allfacsub");
        allFacSubDetailsList.forEach(facSub -> {
            System.out.println(facSub.getSubName() + " " + facSub.getLectAssigned());
            facSubToAssign.add(new FacSub(facSub));
        });
        System.out.println("facSubToAssign");
        facSubToAssign.forEach(facSub -> {System.out.println(facSub.getSubName()
                + " " + facSub.getLectAssigned());
                });
        ArrayList<int[]> periodsList = new ArrayList<>();
        for (int i = 0;i < periods.length; i++)
        {
            ArrayList<Integer> data = new ArrayList<>();
            for (int j = 0;j < days.length; j++)
            {
                if (timeTable[j][i] == null || timeTable[j][i].isBlank())
                    data.add(j);
            }
            Collections.shuffle(data);
            for (var d : data)
            {
                periodsList.add(new int[]{d, i});
            }
        }
        
        String [][] tt = new String[days.length][periods.length];
        for (int d = 0; d < days.length; d++)
            System.arraycopy(timeTable[d], 0, tt[d], 0, periods.length);
        tt = backtrackSchedule(0, tt, periodsList, facSubToAssign);
        if (tt == null)
            JOptionPane.showMessageDialog(this,"Not Possible With Current Constraints");
        else
        {
            allFacSubDetailsList.forEach(facSub -> facSub.resetLect());
            for (int d = 0; d < days.length; d++)
                for (int p = 0; p < periods.length; p++)
                {
                    String val = tt[d][p];
                    timeTable[d][p] = val;
                    if (val != null && !val.isBlank() && !val.equalsIgnoreCase("free"))
                    {
                        updateCell(d, p, val.substring(val.indexOf("_") + 1));
                    }
                }
        }
    }
    private void updateCell(int day, int period, String subId)
    {
        if (subId.equalsIgnoreCase("free"))
        {
            timeTable[day][period] = "free";
            timeTableModel.setValueAt("FREE", day, period + 1);          
            return;
        }
        var facSub = allFacSubDetailsMap.get(subId);
        updateCell(day, period, facSub);
    }
    private void updateCell(int day, int period, FacSub facSub)
    {
        timeTable[day][period] = facSub.getFacId()+"_"+facSub.getSubId();
        increaseSubCount(facSub.getSubId());
        String val = facSub.getFacName()+" "+facSub.getSubName();
        timeTableModel.setValueAt(Utility.colorCodeValue(val, facSub.getColor()),
                day, period + 1);
    }
    private void updateList(){
        if(allFacSubDetailsList == null || allFacSubDetailsList.isEmpty())
            return;
        availableFacSubDetailsList.clear();
        for(var v : allFacSubDetailsList)
        {
            listModel.addElement(Utility.colorCodeValue(v.getFacName()+"("+v.getFacId()+") : "+
                    v.getSubName(), v.getColor()));
            availableFacSubDetailsList.add(v);
        }
        listModel.addElement("FREE");
    }
    private void updateList(String dayId, String timeSlotId){
        
        availableFacSubDetailsList.clear();
        for(var v : allFacSubDetailsList)
        {
            if (v.isFree(dayId, timeSlotId))
            {
                listModel.addElement(Utility.colorCodeValue(v.getFacName()+"("+v.getFacId()+") : "+
                    v.getSubName(), v.getColor()));
                availableFacSubDetailsList.add(v);
            }
        }
        listModel.addElement("FREE");
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jFastSelectButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTimeTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jSubjectAllotedTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jSubmitButton = new javax.swing.JButton();
        jFacultySelectButton = new javax.swing.JButton();
        jClearSelectionButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jClearAllButton = new javax.swing.JButton();
        jLoadButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jClassSelectButton = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jClassBox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 255, 204));
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102), 10));

        jTextField1.setFont(new java.awt.Font("Sitka Heading", 0, 18)); // NOI18N
        jTextField1.setText("(Search Faculty And Subjects)");
        jTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField1MouseClicked(evt);
            }
        });
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jList1.setBackground(new java.awt.Color(153, 153, 153));
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jList1MouseReleased(evt);
            }
        });
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jFastSelectButton.setText("Select Multiple");
        jFastSelectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFastSelectButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jFastSelectButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jFastSelectButton, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addGap(65, 65, 65))
        );

        jSplitPane1.setLeftComponent(jPanel1);

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102), 10));

        jTimeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Monday", null, null, null, null, null, null, null},
                {"Tuesday", null, null, null, null, null, null, null},
                {"Wednesday", null, null, null, null, null, null, null},
                {"Thursday", null, null, null, null, null, null, null},
                {"Friday", null, null, null, null, null, null, null},
                {"Saturday", null, null, null, null, null, null, null},
                {"Sunday", null, null, null, null, null, null, null}
            },
            new String [] {
                "Day/Time", "9:00 AM- 10:00 AM", "10:00 AM- 11:00 AM", "11:00 AM- 12:00 PM", "12:00 PM- 1:00 PM", "1:00 PM- 2:00 PM", "2:00 PM- 3:00 PM", "3:00 PM- 4:00 PM"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTimeTable.setRowHeight(28);
        jTimeTable.getTableHeader().setReorderingAllowed(false);
        jTimeTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTimeTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTimeTable);

        jLabel2.setBackground(new java.awt.Color(0, 102, 102));
        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Time Table");

        jSubjectAllotedTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SUBJECT", "LECTURES REQUIRED", "LECTURES ALLOTED"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jSubjectAllotedTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(jSubjectAllotedTable);

        jPanel3.setBackground(new java.awt.Color(0, 153, 153));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102), 10));

        jSubmitButton.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jSubmitButton.setText("Submit");
        jSubmitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSubmitButtonActionPerformed(evt);
            }
        });

        jFacultySelectButton.setText("Select Faculty");
        jFacultySelectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFacultySelectButtonActionPerformed(evt);
            }
        });

        jClearSelectionButton.setText("CLEAR SELECTION");
        jClearSelectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jClearSelectionButtonActionPerformed(evt);
            }
        });

        jButton1.setText("automate");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jClearAllButton.setText("CLEAR ALL");
        jClearAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jClearAllButtonActionPerformed(evt);
            }
        });

        jLoadButton.setText("Load Data");
        jLoadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLoadButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jFacultySelectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jClearSelectionButton)
                .addGap(35, 35, 35)
                .addComponent(jClearAllButton)
                .addGap(113, 113, 113)
                .addComponent(jSubmitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jLoadButton)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jClearSelectionButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jFacultySelectButton)
                    .addComponent(jSubmitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jClearAllButton)
                    .addComponent(jLoadButton))
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jSplitPane1.setRightComponent(jPanel2);

        jPanel4.setBackground(new java.awt.Color(0, 153, 153));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102), 10));
        jPanel4.setForeground(new java.awt.Color(0, 153, 153));

        jLabel3.setBackground(new java.awt.Color(0, 153, 153));
        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Time Table Creation");
        jLabel3.setOpaque(true);

        jClassSelectButton.setText("Select");
        jClassSelectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jClassSelectButtonActionPerformed(evt);
            }
        });

        jButton5.setText("<-");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jClassBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jClassBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jClassBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jClassSelectButton)
                    .addComponent(jClassBox, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 970, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jClassBox, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(jClassSelectButton))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MouseClicked
        if(jTextField1.getText().equals("(Search Faculty And Subjects)"))
        {
            jTextField1.setText("");
        } // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1MouseClicked

    private void jClassBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jClassBoxActionPerformed
if(jClassBox.getSelectedIndex()>0)
    jClassSelectButton.setEnabled(true);
else
    jClassSelectButton.setEnabled(false);
    }//GEN-LAST:event_jClassBoxActionPerformed

    private void jTimeTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTimeTableMouseClicked
       int column=jTimeTable.getSelectedColumn();
       int row=jTimeTable.getSelectedRow();
      // System.out.println("RC "+row+" "+column);
       listModel.clear();
      /// jList1.setData;
       if(row<0 || column <1)
           return;
       selectedRowColumn[0]=row;
       selectedRowColumn[1]=column;
       String val=""+timeTableModel.getValueAt(row, column);
       //System.out.println(val);
       if(val==null || val.length()<1 ||val.equals("null"))
       {String dayid=days[row].getDayId();
           jClearSelectionButton.setEnabled(false);
       String timeSlotId=periods[column-1].getId();
     //  facultySubjectDetails=TimeTableControl.getAvailableFacultyDetails(timeSlotId,
     //          dayid,selectedClass.getId());
       updateList(dayid, timeSlotId);
       }
       else{
           jClearSelectionButton.setEnabled(true);
       }
    }//GEN-LAST:event_jTimeTableMouseClicked
   
    private void increaseSubCount(String subjectId){
        changeSubCount(subjectId, 1);
    }
    private void decreaseSubCount(String subjectId){
        changeSubCount(subjectId, -1);
    }
    private void changeSubCount(String subjectId, int val)
    {
        if (subjectId == null || subjectId.isBlank() || subjectId.equalsIgnoreCase("free"))
            return;
        System.out.println("-----ChangeSubCount----");
        System.out.println("subjectId "+subjectId);
        var facSub = allFacSubDetailsMap.get(subjectId);
        int index= facSub.index;
        System.out.println("index : "+index);
        if (val < 0)
            facSub.decLect();
        else 
            facSub.incLect();
        subjectTableModel.setValueAt(facSub.getLectAssigned()
                , index, 2);
        checkConstraints();
        System.out.println("---------------------------");
    }
    
    private void checkConstraints(){
        int c=1;
        subjectTableModel.setRowCount(0);
        String color;
        for(var facSub: allFacSubDetailsList){
           // System.out.println(facSub);
            if(!facSub.meetsRequirements())
            {
                c=0;
                color = "#ff0000";
            }
            else
            {
                color = "#00ff00";
            }
                //System.out.println(allFacSubDetailsMap.get(i).getSubName()+" NOT OK");
                subjectTableModel.addRow(
                new Object[]{
                    Utility.colorCodeValue(facSub.getSubName(), facSub.getColor()),
                    Utility.colorCodeValue(""+facSub.getLectReq(), color),
                    Utility.colorCodeValue(""+facSub.getLectAssigned(), color)
                }
                );
        }
        if(c==1)
            jSubmitButton.setEnabled(true);
        else
            jSubmitButton.setEnabled(false);
    }
    
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        leavePage();// TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void leavePage(){
        Adminhomescreen.getAdminhomescreen();
        if(roleOfUser == role.ADMIN)
    Adminhomescreen.getAdminhomescreen();
    else if(roleOfUser == role.FACULTY)
    Facultyhomescreen.getFacultyhomescreen();
    else if(roleOfUser == role.STUDENT)
    Studenthomescreen.getStudenthomescreen();
    else
    System.exit(0);
        this.dispose();
    }
    
    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        System.out.println("index at listEv "+selectedFacSubIndex);
        if(jList1.getSelectedIndex()<0)
        jFacultySelectButton.setEnabled(false);
        else
        {   System.out.println("val="+selectedFacSubIndex);
            selectedFacSubIndex = jList1.getSelectedIndex();
            jFacultySelectButton.setEnabled(true);
        }
    }//GEN-LAST:event_jList1ValueChanged

    private void jList1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseReleased

    }//GEN-LAST:event_jList1MouseReleased

    private void jClassSelectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jClassSelectButtonActionPerformed
     jClassSelectButton.setEnabled(false);
    jClassBox.setEnabled(false);
    selectedClass=classes[jClassBox.getSelectedIndex()-1];
    jFastSelectButton.setEnabled(true);
    timeTables = new TimeTables(selectedClass.getId());
    initTables();
    }//GEN-LAST:event_jClassSelectButtonActionPerformed

    private void jFacultySelectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFacultySelectButtonActionPerformed
       listModel.clear();
       jClearSelectionButton.setEnabled(true);
       if (selectedFacSubIndex == availableFacSubDetailsList.size())
       {
            updateCell(selectedRowColumn[0], selectedRowColumn[1]-1, "FREE");
            return;
       }
       var facSub = availableFacSubDetailsList.get(selectedFacSubIndex);
       if (facSub == null)
           return;
       updateCell(selectedRowColumn[0], selectedRowColumn[1]-1, facSub);
    }//GEN-LAST:event_jFacultySelectButtonActionPerformed

    private void jSubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSubmitButtonActionPerformed
        int count=0;
        String classId=selectedClass.getId();
        ArrayList<Schedule> schedule=new ArrayList<>();
        for(int i=0;i<days.length;i++)
           for(int j=0;j<periods.length;j++)
           {
               if(timeTable[i][j]== null || timeTable[i][j].isBlank() || 
                       timeTable[i][j].equalsIgnoreCase("free"))
                   continue;
               count++;
               String timeslotId = periods[j].getId();
               String dayId = days[i].getDayId();
               String fac_sub = timeTable[i][j];
               String facultyId = fac_sub.substring(0, fac_sub.indexOf("_"));
               String subjectId = fac_sub.substring(fac_sub.indexOf("_")+1);
              int roomId=TimeTableControl.getRoomId(classId, subjectId, facultyId);
              schedule.add(new Schedule(dayId,timeslotId,classId,subjectId,facultyId,roomId));
           }
       TimeTableControl.createTimeTable(schedule);
       leavePage();
    }//GEN-LAST:event_jSubmitButtonActionPerformed

    private void jClearSelectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jClearSelectionButtonActionPerformed
        clearSelection(selectedRowColumn[0], selectedRowColumn[1]-1);
        jClearSelectionButton.setEnabled(false);
    }//GEN-LAST:event_jClearSelectionButtonActionPerformed
    private void clearSelection(int day, int period, boolean editLecturesCount)
    {
        if (editLecturesCount)
        {
            String sub=timeTable[day][period];
            System.out.println("sub: "+sub);
            if(sub==null||sub.equals("null"))
                return;
            if (!sub.equalsIgnoreCase("free"))
            {
                sub = sub.substring(sub.indexOf("_")+1);
                System.out.println("sub "+sub);
                decreaseSubCount(sub);
            }
        }
        timeTable[day][period]=null;
        timeTableModel.setValueAt("", day,period + 1);
    }
    private void clearSelection(int day, int period)
    {
        clearSelection(day, period, true);
    }
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      sugestSchedulev3();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jClearAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jClearAllButtonActionPerformed
    clearTable();
    }//GEN-LAST:event_jClearAllButtonActionPerformed
FastSelection fastSelection;
    private void jFastSelectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFastSelectButtonActionPerformed
        this.setVisible(false);
        fastSelection = new FastSelection();
        for (int i = 0;i < days.length; i++)
            for(int j = 0; j < periods.length; j++)
            {
                var id = timeTable[i][j];
                FastSelection.SelectionValue value;
                if (id == null || id.isBlank())
                    value = FastSelection.SelectionValue.None;
                else
                {
                    if (!id.equalsIgnoreCase("free"))
                        id = id.substring(id.indexOf('_') + 1);
                    value = FastSelection.SelectionValue.Select;
                }
                fastSelection.add(id, i, j, value);
            }
        new Condition(allFacSubDetailsList, fastSelection, this).setVisible(true);
    }//GEN-LAST:event_jFastSelectButtonActionPerformed

    private void jLoadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLoadButtonActionPerformed
    ArrayList<String> data = timeTables.getTimeTablesList();
    if (data.size() < 1)
    {
        JOptionPane.showMessageDialog(this, "no data found");
        return;
    }
    ArrayList<String> values = new ArrayList();
    for (var v : data)
    {
        values.add(timeTables.toPlainString(v) + "["+v+"]");
    }
    var r = JOptionPane.showInputDialog(this, "select one", "Select",
            JOptionPane.QUESTION_MESSAGE, null, values.toArray(), null);
    if (r == null)
        return;
    String result = r.toString();
    String id = result.substring(result.indexOf('[')+1, result.indexOf(']'));
        initTableData(timeTables.fetchParticularTimeTable(id));
    }//GEN-LAST:event_jLoadButtonActionPerformed
    
    void applyFastSelection()
    {
        allFacSubDetailsList.forEach(v->v.resetLect());
        for (int i = 0; i < days.length; i++)
            for (int j = 0; j < periods.length; j++)
            {
                var cell = fastSelection.cellValues[i][j];
                var id = cell.id;
                if (cell.value == FastSelection.SelectionValue.Select)
                {
                   // if (!id.equalsIgnoreCase(getSubID(timeTable[i][j])))
                    //{
                    //    decreaseSubCount(getSubID(timeTable[i][j]));
                        updateCell(i, j, id);
                   // }
                }
                else if(id.equalsIgnoreCase(getSubID(timeTable[i][j])))
                    clearSelection(i, j, false);
            }
    }
    
    private String getSubID(String id)
    {
        if (id == null || id.isBlank())
            return null;
        else if (id.equalsIgnoreCase("free"))
            return id;
        else
            return id.substring(id.indexOf("_") + 1);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TTCreation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TTCreation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TTCreation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TTCreation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TTCreation().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jClassBox;
    private javax.swing.JButton jClassSelectButton;
    private javax.swing.JButton jClearAllButton;
    private javax.swing.JButton jClearSelectionButton;
    private javax.swing.JButton jFacultySelectButton;
    private javax.swing.JButton jFastSelectButton;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JList<String> jList1;
    private javax.swing.JButton jLoadButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jSubjectAllotedTable;
    private javax.swing.JButton jSubmitButton;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable jTimeTable;
    // End of variables declaration//GEN-END:variables
}
