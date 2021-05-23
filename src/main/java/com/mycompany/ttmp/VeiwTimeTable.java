/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ttmp;

import java.awt.Color;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author User
 */
public class VeiwTimeTable extends javax.swing.JFrame {
    TimeSlot[] periods;
    Day[] days;
    private boolean changeInProcess = false;
    DefaultTableModel timeTableModel;
    private int selectedIndex=0;
    private int[] selectedRowColumn=new int[2];
    private Object[][] listData; 
    private ArrayList<Object[]> listSearchData; 
    private String [][] timeTable;
    private String [][] myTimeTable;
    private String selectedTid;
    private String myId;
    private HashMap<String,Object> cellInfo = new HashMap<>();
    //private final role roleOfUser=LoginF.getUserRole();
    private final role roleOfUser=role.ADMIN;
    private TableCellRenderer cellRenderer ;
    
    private String timeTableOf;
    DefaultListModel<String> listModel;
    TimeTables timeTables;
    ArrayList<String> timeTablesList = new ArrayList<>();
    ArrayList<ArrayList<Color>> colorData = new ArrayList<>();
    HashMap<String, Color> listColorData = new HashMap<>();

    /**
     * Creates new form VeiwTimeTable
     */
    private void initVariables(){
        periods=TimeSlotControl.getAllSlots();
        days=DayControl.getAllDays();
        timeTableModel=(DefaultTableModel)jTimeTable.getModel();
        timeTableModel.setRowCount(0);
        timeTableModel.setColumnCount(0);
        timeTableModel.addColumn("DAY \\ TIME");
        listModel=new DefaultListModel<>();
        jList1.setModel(listModel);
        for (int i = 0; i < periods.length;i++) {
            cellInfo.putIfAbsent(periods[i].getId(), i);
            timeTableModel.addColumn(periods[i].toSimpleString());
        }
        for (int i = 0; i < days.length; i++) {
            cellInfo.putIfAbsent(days[i].getDayId(), i);
            timeTableModel.addRow(new Object[]{days[i].getDayName()});
        }
        timeTable=new String[days.length][periods.length];
        jTimeTable.setEnabled(true);    
        for (int i = 0; i < days.length; i++)
        {
            ArrayList<Color> colors = new ArrayList<>();
            
            for(int j = 0;j <= periods.length; j++)
                colors.add(Color.white);
            colorData.add(colors);
        }
        var columnModel = jTimeTable.getColumnModel();
        cellRenderer = new CellColorRenderer(colorData);
        for (int i = 1; i<= periods.length; i++)
            columnModel.getColumn(i).setCellRenderer(cellRenderer);
}
    
    public VeiwTimeTable() {
        initComponents();
        jVersionBox.setVisible(false);
        if(roleOfUser != role.ADMIN)
        {
        jUseForCreationButton.setVisible(false);
        }
        jUseForCreationButton.setEnabled(false);
        initVariables();
    }
    public VeiwTimeTable(String id ) {
        initComponents();
        initVariables();           
        jVersionBox.setVisible(false);
        if(roleOfUser != role.ADMIN)
        {
            myId=id;
            jUseForCreationButton.setVisible(false);
        //System.out.println(id);
        jTextField1.setText("my table");
        myTimeTable=new String[days.length][periods.length];
        }
        jUseForCreationButton.setEnabled(false);
        getTimeTable(id);
    }
    private void getTimeTable(String id)
    {
        if (!changeInProcess)
        {
            changeInProcess = true;
//            if (id.startsWith("C"))
//            {
                if (timeTables == null || !timeTables.tablesOf.equals(id))
                {
                    timeTables = TimeTableControl.getTimeTablesList(id);
                    
                    if (roleOfUser == role.ADMIN)
                        jUseForCreationButton.setEnabled(true);
                    updateTable(timeTables.fetchTimeTable(), id);
                    jVersionBox.removeAllItems();
                    timeTablesList = timeTables.getTimeTablesList();
                    timeTablesList.forEach(val -> {
                        String v ;
                        v = timeTables.toPlainString(val);
                        if (roleOfUser == role.ADMIN)
                            v += "["+ val +"]";
                        jVersionBox.addItem(v);
                    });
                    if (timeTablesList.size() > 1)
                        jVersionBox.setVisible(true);
                    else
                    {
                        if (timeTablesList.isEmpty())
                            jUseForCreationButton.setEnabled(false);
                        jVersionBox.setVisible(false);
                    }
                }
//            }
//            else 
//            {
//                jUseForCreationButton.setEnabled(false);
//                jVersionBox.setVisible(false);
//                updateTable(TimeTableControl.getFacultyTimeTable(id),id);
//            }
            changeInProcess = false;
        }
        
    }
    private void updateList(int index){
        listModel.removeAllElements();
        clearTable();
        if(index<1)return;
        if(index==1)
            listData=TimeTableControl.getAssignedFaculty();
        else listData=getClasses();
        if(listData==null)return;
        listSearchData=new ArrayList<>();
        for(Object[] objects: listData){
            String val=objects[1]+" ("+objects[0]+")";
            if((boolean)objects[2]==false)
                val+=" [ NOT YET CREATED ]";
            listSearchData.add(objects);
            listModel.addElement(val);
        }
    }
    
    private Object[][] getClasses(){
        I_Class[] classes=TimeTableControl.getClasses();
        Object[][] data = new Object[classes.length][];
        for(int i=0;i<classes.length;i++){
            data[i]=new Object[]{classes[i].getId(),classes[i].getName(),classes[i].getAssignmentStatus()};
        }
        return data;
    }
    
    private void updateList(){
        if(listData==null)return;
        listModel.removeAllElements();
        //resetColorData();
        listSearchData=new ArrayList<>();
        for(Object[] objects: listData){
            String val=objects[1]+" ("+objects[0]+")";
            if((boolean)objects[2]==false)
                val+=" [ NOT YET CREATED ]";
            listSearchData.add(objects);
            listModel.addElement(val);
        }
    }
    
    
    private void updateList(String search){
        listSearchData=new ArrayList<>();
        listModel.removeAllElements();
        if(listData==null)
            return;
        search = search.toLowerCase();
        for(Object[] objects: listData){
            if(((String)objects[0]).toLowerCase().contains(search)==false && ((String)objects[1]).toLowerCase().contains(search)==false)
                    continue;
            listSearchData.add(objects);
            String val=objects[1]+" ("+objects[0]+")";
            if((boolean)objects[2]==false)
                val+=" [ NOT YET CREATED ]";
            listModel.addElement(val);
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

        jButton5 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTimeTable = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jTableOfLabel = new javax.swing.JLabel();
        jVersionBox = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jUseForCreationButton = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();

        jButton5.setText("<-");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel3.setBackground(new java.awt.Color(0, 204, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153), 10));

        jSplitPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153)));

        jPanel1.setBackground(new java.awt.Color(0, 204, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153), 10));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Choose>", "Faculty", "Class" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jTextField1.setText("Search");
        jTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField1MouseClicked(evt);
            }
        });
        jTextField1.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jTextField1InputMethodTextChanged(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jScrollPane1.setBackground(new java.awt.Color(153, 153, 153));

        jList1.setBackground(new java.awt.Color(153, 153, 153));
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
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
            .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE))
        );

        jSplitPane1.setLeftComponent(jPanel1);

        jPanel2.setBackground(new java.awt.Color(0, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153), 10));

        jTimeTable.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jTimeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

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
        jTimeTable.setAlignmentX(1.5F);
        jTimeTable.setAlignmentY(1.5F);
        jTimeTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTimeTable.setRowHeight(60);
        jTimeTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jTimeTable);

        jPanel6.setBackground(new java.awt.Color(0, 204, 204));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153), 8));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 48)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Time Table");

        jPanel7.setBackground(new java.awt.Color(0, 204, 204));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153), 5));

        jTableOfLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTableOfLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jVersionBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jVersionBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jVersionBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jVersionBox, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTableOfLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTableOfLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jVersionBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 612, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 408, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane3)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jSplitPane1.setRightComponent(jPanel2);

        jPanel4.setBackground(new java.awt.Color(0, 204, 204));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153), 10));

        jLabel1.setBackground(new java.awt.Color(153, 153, 0));
        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("View Time Table Details");

        jUseForCreationButton.setText("Use For Creation");
        jUseForCreationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jUseForCreationButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jUseForCreationButton)
                .addGap(32, 32, 32)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jUseForCreationButton)))
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(0, 153, 153));

        jButton6.setText("<-");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 13, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jButton6)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
 
    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        jVersionBox.setVisible(false);
        updateList(jComboBox2.getSelectedIndex());
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged

    }//GEN-LAST:event_jList1ValueChanged

    private void jList1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseReleased
       if(jList1.getSelectedIndex()>=0)
        {   
            //System.out.println("Selected Value("+jList1.getSelectedIndex()+") : "
            //        +LocalDateTime.now().format(DateTimeFormatter.ISO_TIME));
            int selectedIndex = jList1.getSelectedIndex();
            boolean assigned = (boolean) listSearchData.get(selectedIndex)[2];
            if(assigned)
            {
                String id=(String)listSearchData.get(selectedIndex )[0];
                ////System.out.println("id "+id);
                getTimeTable(id);
            }
            else{
                JOptionPane.showMessageDialog(this,"Time Table not created for the selection");
            }
            //System.out.println("completed work : "+LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        }
    }//GEN-LAST:event_jList1MouseReleased

    private void jTextField1InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jTextField1InputMethodTextChanged
    
    }//GEN-LAST:event_jTextField1InputMethodTextChanged

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
    String val=jTextField1.getText();
    if(val==null || val.length()<1||val.equals("null"))
    {
        updateList();
        return;
    }
    if(val.equalsIgnoreCase("my table")){
        if(roleOfUser != role.ADMIN)
        {
            if(myId.contains("C"))
            timeTableOf=ClassControl.getClass(myId).getName();
            else
            timeTableOf=(String)FacultyControl.getSpecificData(
                           myId,new String[]{"name"}) [0];
        //System.out.println(timeTableOf);
        jTableOfLabel.setText(timeTableOf);
            displayTable(myTimeTable);
        }
        else
            JOptionPane.showMessageDialog(this,"admins don't have a time table");
    }
    else{
        updateList(val);
    }
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if(null == roleOfUser)
            System.exit(0);
else    switch (roleOfUser) {
            case ADMIN:
                Adminhomescreen.getAdminhomescreen();
                break;
            case FACULTY:
                Facultyhomescreen.getFacultyhomescreen();
                break;
            case STUDENT:
                Studenthomescreen.getStudenthomescreen();
                break;
            default:
                System.exit(0);
        }
    this.dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jTextField1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MouseClicked
        if(jTextField1.getText()!=null && jTextField1.getText().equals("Search"))
            jTextField1.setText("");
    }//GEN-LAST:event_jTextField1MouseClicked

    private void jVersionBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jVersionBoxActionPerformed
        if (jVersionBox.getSelectedIndex() < 0)
            return;
        String val = timeTablesList.get(jVersionBox.getSelectedIndex());
        updateTable(timeTables.fetchParticularTimeTable(val), timeTables.tablesOf);
    }//GEN-LAST:event_jVersionBoxActionPerformed

    private void jUseForCreationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jUseForCreationActionPerformed
        if (timeTables == null || ! timeTables.isTablesOfAClass())
        {
            JOptionPane.showMessageDialog(this, "Please select time table of a class first",
                    "Invalid Operation", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        new TTCreation(timeTables, timeTables.lastFetched).setVisible(true);
        this.dispose();    
    }//GEN-LAST:event_jUseForCreationActionPerformed

    private void resetColorData()
    {
        for (int i = 0; i< days.length; i++)
            for (int j = 0;j <= periods.length; j++)
                colorData.get(i).set(j, Color.white);
    }
    private void updateTable(ArrayList<Schedule> schedule,String id){
     //   resetColorData();
        if(schedule==null || schedule.size()<1){
            JOptionPane.showMessageDialog(this, "Time table does not exist"
                    + " for the selection");
            return;
        }
            //System.out.println("Updating table : "+LocalDateTime.now());
            for(int i=0;i<periods.length;i++)
                for(int j=0;j<days.length;j++)
                    timeTable[j][i]="";
            //System.out.println("Reset Completed : "+LocalDateTime.now());
            
        HashMap<String, String> cellColorValues = new HashMap<>();
        if(id.contains("C"))
            timeTableOf=ClassControl.getClass(id).getName();
        else
            timeTableOf=(String)FacultyControl.getSpecificData(
                           id,new String[]{"name"}) [0];
        jTableOfLabel.setText(timeTableOf);
        int colorIndex = 0;
        //System.out.println("Iterating over Records : "+LocalDateTime.now());
        for(Schedule record: schedule)
        {
            ////System.out.println("Startinf of record : "+LocalDateTime.now());
            String dayId=record.getDayId();
            String periodId=record.getTimeslotId();
            int periodIndex=(Integer)cellInfo.get(periodId),
                    dayIndex =(Integer) cellInfo.get(dayId);
            if(periodIndex<0||dayIndex<0)
                continue;
            ////System.out.println("Extracting Data : "+LocalDateTime.now());
            String subId = record.getSubjectId();
            if (!cellInfo.containsKey(subId))
            {
                cellInfo.put(subId,
                        TimeTableControl.getSubject(record.getSubjectId())
                                .getSubjectName());
            }
            if (!cellColorValues.containsKey(subId))
            {
                String color = Utility.colors[colorIndex++];
                cellColorValues.put(subId, color);
            }
        
            String subject = (String)cellInfo.get(subId);
            String subjectColor = cellColorValues.getOrDefault(subId, "#ffffff");
            String val;
            int roomId=record.getRoomId();
            if(id.contains("C"))
            {
                String facId = record.getFacultyId();
                if (!cellInfo.containsKey(facId))
                    cellInfo.put(facId,(String)FacultyControl.getSpecificData(
                           facId,new String[]{"name"}) [0] );
            String facultyName = (String)cellInfo.get(facId);
                colorData.get(
                        Integer.valueOf(cellInfo.get(record.getDayId()).toString())
                ).set(
                        Integer.valueOf(cellInfo.get(record.getTimeslotId()).toString()) + 1
                        ,Color.decode(subjectColor));
                System.out.println(record.getDayId()+ " "+ record.getTimeslotId()+" "+
                        colorData.get(
                        Integer.valueOf(cellInfo.get(record.getDayId()).toString())
                ).get(
                        Integer.valueOf(cellInfo.get(record.getTimeslotId()).toString()) + 1
                        ));
                val=subject+" ("+facultyName+") \n"+
                        "("+roomId+")";
            }
            else{
                String classId = record.getClassId();
                if (!cellInfo.containsKey(classId))
                    cellInfo.put(classId,
                            ClassControl.getClass(record.getClassId()).getName());
            String className = (String)cellInfo.get(classId);
                val= subject+"\n"+className
                        +"("+roomId+")";
                colorData.get(
                        Integer.valueOf(cellInfo.get(record.getDayId()).toString())
                ).set(
                        Integer.valueOf(cellInfo.get(record.getTimeslotId()).toString()) + 1
                        ,Color.decode(subjectColor));
            }
            if(id.equals(myId))
            {
                myTimeTable[dayIndex][periodIndex]=val;
            }
            else{
                timeTable[dayIndex][periodIndex]=val;
            }
            
            ////System.out.println("Record Done : "+LocalDateTime.now());
        }
            //System.out.println("--Records Done-- : "+LocalDateTime.now());
        if(id.equals(myId))
        {
            
        if(id.contains("C"))
            timeTableOf=ClassControl.getClass(id).getName();
        else
            timeTableOf=(String)FacultyControl.getSpecificData(
                           id,new String[]{"name"}) [0];
        //System.out.println(timeTableOf);
        jTableOfLabel.setText(timeTableOf);
            displayTable(myTimeTable);
        }
        else
            displayTable(timeTable);
    }
    private void displayTable(String [][] table){
            //System.out.println("Displaying table : "+LocalDateTime.now());
            for(int i=0;i<periods.length;i++)
                for(int j=0;j<days.length;j++)
                    timeTableModel.setValueAt("", j, i+1);
        for(int i=0;i<periods.length;i++)
            for(int j=0;j<days.length;j++)
            {
                String val=table[j][i];
//                var v = jTimeTable.getCellRenderer(j, i + 1).
//                        getTableCellRendererComponent(
//                                jTimeTable, val, true,
//                                false, j, i + 1);
                //v.setBackground(color);
                //v.setForeground(ocolor);
                if (val == null || val.isBlank() || val.equalsIgnoreCase("free"))
                    colorData.get(j).set(i+1, Color.white);
                timeTableModel.setValueAt(val,j,(i+1));
            }
        //System.out.println("Displayed table : "+LocalDateTime.now());
    }
    private void clearTable(){
            for(int i=0;i<periods.length;i++)
                for(int j=0;j<days.length;j++)
                {
                    colorData.get(j).set(i+1, Color.white);
                    timeTableModel.setValueAt("", j, i+1);
                }
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
            java.util.logging.Logger.getLogger(VeiwTimeTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VeiwTimeTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VeiwTimeTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VeiwTimeTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VeiwTimeTable().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel jTableOfLabel;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable jTimeTable;
    private javax.swing.JButton jUseForCreationButton;
    private javax.swing.JComboBox<String> jVersionBox;
    // End of variables declaration//GEN-END:variables
}
