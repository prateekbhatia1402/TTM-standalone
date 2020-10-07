package com.mycompany.ttmp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

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

        int lectReq, lectAssigned = 0;
        public String getSubName() {
            return subName;
        }
        
        public boolean isFree(String dayId, String timeslotId)
        {
            if (facSchedule.contains(dayId+"_"+timeslotId))
                return false;
           else
                return true;
        }
        
        public void setSubName(String subName) {
            this.subName = subName;
        }

        public FacSub(String facId, String facName, String subId, String subName, int lectReq) {
            this.facId = facId;
            this.facName = facName;
            this.subId = subId;
            this.subName = subName;
            this.lectReq = lectReq;
            this.facSchedule = new HashSet<>();
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
                String subName, int lectReq, HashSet<String> facSchedule) {
            this.index = index;
            this.facId = facId;
            this.facName = facName;
            this.subId = subId;
            this.subName = subName;
            this.lectReq = lectReq;
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
        }

        public int getLectReq() {
            return lectReq;
        }

        public void setLectReq(int lectReq) {
            this.lectReq = lectReq;
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
public class TTCreation extends javax.swing.JFrame {
    TimeSlot[] periods;
    Day[] days;
    DefaultTableModel timeTableModel;
    DefaultTableModel subjectTableModel;
    private I_Class[] classes;
    private I_Class selectedClass;
    private final String roleOfUser;
    private String[][] facultySubjectDetails;
    private int selectedFacultyIndex=0;
    private String selectedFacultyId = "";
    private int[] selectedRowColumn=new int[2];
    private String [][] timeTable;
    private final HashMap<String, FacSub> allFacSubDetailsMap = new HashMap<>();
    private ArrayList<FacSub> allFacSubDetailsList = new ArrayList<>();
    DefaultListModel<String> listModel;
    /**
     * Creates new form TTCreation
     */
    
    private void initTables(){
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
        //subjects=TimeTableControl.getSubjects(selectedClass.getId());
//        System.out.println(subjects.length);
        var v = TimeTableControl.getFacultySubjectDetails(selectedClass.getId(),
                LocalDate.now().atStartOfDay().plusDays(1).toLocalDate(), null);
        for (int i = 0; i < v.size(); i++)
        {
            var f = new FacSub(i, v.get(i));
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
        allFacSubDetailsList.forEach(facSub -> {
            subjectTableModel.addRow(
                    new Object[]{facSub.getSubName(),
                facSub.getLectReq(),0});
        });
        System.out.println(subjectTableModel.getRowCount());
        initTableData(TimeTableControl.getLatestClassTimeTable(selectedClass.getId()));
        jTimeTable.setEnabled(true);
}
    
    private void initTableData(ArrayList<Schedule> schedule){
        if(schedule==null)
            return;
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
        timeTableModel.setValueAt(facName+
                " "+subjectName
                , day, period+1);
        }
    }
    public TTCreation() {
        initComponents();
        roleOfUser=LoginF.USER_ROLE;
        jFacultySelectButton.setEnabled(false);
        jSubmitButton.setEnabled(false);
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
    
    
    public void sugestSchedule(){
        ArrayList<FacSub> facSubToAssign = new ArrayList<>();
        allFacSubDetailsList.forEach(facSub -> {
            facSubToAssign.add(new FacSub(facSub));
        });
            ArrayList<int[]> allDays = new ArrayList<>();
            for (int i = 0;i < periods.length; i++)
                allDays.add(new int[]{i, 0});
        while(true)
        {
            Random random = new Random();
            int subI = random.nextInt(facSubToAssign.size());
            FacSub facSub = facSubToAssign.get(subI);
           // System.out.println(facSub);
            while(!facSub.meetsRequirements())
            {
                if (allDays.size() < 1)
                    break;
                ArrayList<int[]> daysToChoose = new ArrayList<>();
                int maxv = allDays.get(0)[1], minv = allDays.get(0)[1];
                for (int i = 0; i < allDays.size(); i++)
                {
                    System.out.println("day : "+allDays.get(i)[0]+" "+allDays.get(i)[1]);
                    if (allDays.get(i)[1] > maxv)
                        maxv = allDays.get(i)[1];
                    else if(allDays.get(i)[1] < minv)
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
                    System.out.println("days : "+d[0]+" " + d[1]);
                }
                int dayI = random.nextInt(daysToChoose.size());
                int day = daysToChoose.get(dayI)[0];
                System.out.println("day choosen "+dayI+" "+day);
                int tp = -1;
                boolean assigned = false;
                do
                {
                    tp++;
                    if (timeTable[day][tp] != null && !timeTable[day][tp].isBlank())
                        continue;
                    if (TimeTableControl.isFacultyFree(facSub.getFacId(),
                            days[day].getDayId(), periods[tp].getId()))
                    {
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
                //System.out.println(allDays);
            }
            facSubToAssign.remove(subI);
            if (facSubToAssign.isEmpty())
                break;
        }
    }
    
    private void updateList(){
        if(facultySubjectDetails==null)
            return;
        int n=facultySubjectDetails.length;
        //String facultyList[] =new String[n];
        for(int i=0;i<n;i++)
        {
            listModel.addElement(facultySubjectDetails[i][1]+"("+facultySubjectDetails[i][0]+") : "+
                    facultySubjectDetails[i][3]);
        }
    }
    private void updateList(String dayId, String timeSlotId){
        
        for(var v : allFacSubDetailsList)
        {
            if (v.isFree(dayId, timeSlotId))
            listModel.addElement(v.getFacName()+"("+v.getFacId()+") : "+
                    v.getSubName());
        }
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE))
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jFacultySelectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jClearSelectionButton)
                .addGap(218, 218, 218)
                .addComponent(jSubmitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jButton1)
                .addContainerGap(137, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jClearSelectionButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jFacultySelectButton)
                    .addComponent(jSubmitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
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
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
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
       System.out.println("RC "+row+" "+column);
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
       facultySubjectDetails=TimeTableControl.getAvailableFacultyDetails(timeSlotId,
               dayid,selectedClass.getId());
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
    }
    private String colorCodeValue(String value, String color)
    {
        return "<html><font color='"+color+"'><p>"+value+"</p></font>";
    }
    private void checkConstraints(){
        int c=1;
        subjectTableModel.setRowCount(0);
        String color;
        for(var facSub: allFacSubDetailsList){
            System.out.println(facSub);
            if(!facSub.meetsRequirements())
            {
                c=0;
                color = "red";
            }
            else
            {
                color = "green";
            }
                //System.out.println(allFacSubDetailsMap.get(i).getSubName()+" NOT OK");
                subjectTableModel.addRow(
                new Object[]{
                    colorCodeValue(facSub.getSubName(), color),
                    colorCodeValue(""+facSub.getLectReq(), color),
                    colorCodeValue(""+facSub.getLectAssigned(), color)
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
        if(roleOfUser.equals("admin"))
    Adminhomescreen.getAdminhomescreen();
    else if(roleOfUser.equals("faculty"))
    Facultyhomescreen.getFacultyhomescreen();
    else if(roleOfUser.equals("student"))
    Studenthomescreen.getStudenthomescreen();
    else
    System.exit(0);
        this.dispose();
    }
    
    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        System.out.println("index at listEv "+selectedFacultyIndex);
        if(jList1.getSelectedIndex()<0)
        jFacultySelectButton.setEnabled(false);
        else
        {   System.out.println("val="+selectedFacultyIndex);
            selectedFacultyIndex = jList1.getSelectedIndex();
            String val = jList1.getSelectedValue(); 
            val = val.substring(val.indexOf('(')+1, val.indexOf(')'));
            selectedFacultyId = val;
            jFacultySelectButton.setEnabled(true);
        }
    }//GEN-LAST:event_jList1ValueChanged

    private void jList1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseReleased

    }//GEN-LAST:event_jList1MouseReleased

    private void jClassSelectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jClassSelectButtonActionPerformed
     jClassSelectButton.setEnabled(false);
    jClassBox.setEnabled(false);
    selectedClass=classes[jClassBox.getSelectedIndex()-1];
    initTables();
    }//GEN-LAST:event_jClassSelectButtonActionPerformed

    private void jFacultySelectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFacultySelectButtonActionPerformed
       listModel.clear();
       jClearSelectionButton.setEnabled(true);
       /*
        int index=selectedFacultyIndex;
        System.out.println("index "+index);
        String subjectId=facultySubjectDetails[index][2];
        String subjectName=facultySubjectDetails[index][3];
        String val=facultySubjectDetails[index][0]+"_"+subjectId;
        timeTable[selectedRowColumn[0]][selectedRowColumn[1]-1]=
                val;
        increaseSubCount(subjectId);
        timeTableModel.setValueAt(facultySubjectDetails[index][1]+" "+subjectName
                , selectedRowColumn[0], selectedRowColumn[1]);
      //  increaseSubCount(subject);
       */
       var facSub = allFacSubDetailsMap.get(selectedFacultyId);
       if (facSub == null)
           return;
       String val = facSub.getFacId()+"_"+facSub.getSubId();
        timeTableModel.setValueAt(facSub.getFacName()+" "+facSub.getSubName()
                , selectedRowColumn[0], selectedRowColumn[1]);
       facSub.incLect();
    }//GEN-LAST:event_jFacultySelectButtonActionPerformed

    private void jSubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSubmitButtonActionPerformed
        int count=0;
        String classId=selectedClass.getId();
        ArrayList<Schedule> schedule=new ArrayList<>();
        for(int i=0;i<days.length;i++)
           for(int j=0;j<periods.length;j++)
           {
               if(timeTable[i][j]==null)
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
        String sub=timeTable[selectedRowColumn[0]][selectedRowColumn[1]-1];
        System.out.println("sub: "+sub);
        if(sub==null||sub.equals("null"))
            return;
        sub=sub.substring(sub.indexOf("_")+1);
        System.out.println("sub "+sub);
        decreaseSubCount(sub);
        timeTable[selectedRowColumn[0]][selectedRowColumn[1]-1]=null;
        timeTableModel.setValueAt("", selectedRowColumn[0],selectedRowColumn[1]);
        jClearSelectionButton.setEnabled(false);

    }//GEN-LAST:event_jClearSelectionButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      sugestSchedule();
    }//GEN-LAST:event_jButton1ActionPerformed

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
    private javax.swing.JButton jClearSelectionButton;
    private javax.swing.JButton jFacultySelectButton;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JList<String> jList1;
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
