/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ttmp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

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
    private String myId;
    private HashMap<String,Object> cellInfo = new HashMap<>();
    private final String roleOfUser=LoginF.USER_ROLE;
    private String timeTableOf;
    DefaultListModel<String> listModel;
    TimeTables timeTables;
    ArrayList<String> timeTablesList = new ArrayList<>();

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
}
    
    public VeiwTimeTable() {
        initComponents();
        jSubmitButton1.setVisible(false);
        initVariables();
    }
    public VeiwTimeTable(String id ) {
        initComponents();
        jSubmitButton1.setVisible(false);
        initVariables();           
        if(roleOfUser.equalsIgnoreCase("admin"))
        {
            myId=id;
        //System.out.println(id);
        jTextField1.setText("my table");
        myTimeTable=new String[days.length][periods.length];
        }
        getTimeTable(id);
    }
    private void getTimeTable(String id)
    {
        if (!changeInProcess)
        {
            changeInProcess = true;
            if (id.startsWith("C"))
            {
                //System.out.println("Class TT is requested : "+LocalDateTime.now());
                if (timeTables == null || !timeTables.getClassId().equals(id))
                {
                    //System.out.println("New Class is selected : "+LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
                    timeTables = TimeTableControl.getTimeTablesList(id);
                    //System.out.println("Got TimeTablesList : "+LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
                    updateTable(timeTables.fetchTimeTable(), id);
                    //System.out.println("DisplayedTimeTable : "+LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
                    jVersionBox.removeAllItems();
                    timeTablesList = timeTables.getTimeTablesList();
                    //System.out.println("Got TT's as List : "+LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
                    timeTablesList.forEach(val -> {
                        String v ;
                        v = timeTables.toPlainString(val);
                        jVersionBox.addItem(v);
                    });
                    //System.out.println("FilledVersionBox : "+LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
                    if (timeTablesList.size() > 1)
                        jVersionBox.setVisible(true);
                    else
                        jVersionBox.setVisible(false);
                }
            }
            else 
            {
                //System.out.println("Faculty TT is requested : "+LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
                jVersionBox.setVisible(false);
                updateTable(TimeTableControl.getFacultyTimeTable(id),id);
                //System.out.println("Displayed TT : "+LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
            }
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
        jSubmitButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jSubmitButton1 = new javax.swing.JButton();
        jTableOfLabel = new javax.swing.JLabel();
        jVersionBox = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE))
        );

        jSplitPane1.setLeftComponent(jPanel1);

        jPanel2.setBackground(new java.awt.Color(0, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153), 10));

        jTimeTable.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jTimeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Monday", null, null, null, null, null, null, null},
                {"Tuesday", null, null, null, null, null, null, null},
                {"Wednesday", null, null, null, null, null, null, null},
                {"Thursday", null, null, null, null, null, null, null},
                {"Friday", null, null, null, null, null, null, null},
                {"Saturday", null, null, null, null, null, null, null}
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

        jSubmitButton.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jSubmitButton.setText("OK");
        jSubmitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSubmitButtonActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 48)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Time Table");

        jSubmitButton1.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jSubmitButton1.setText("OK");
        jSubmitButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSubmitButton1ActionPerformed(evt);
            }
        });

        jTableOfLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTableOfLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jVersionBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jVersionBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jVersionBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 567, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jVersionBox, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTableOfLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jSubmitButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(240, 240, 240)
                .addComponent(jSubmitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1040, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTableOfLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jVersionBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSubmitButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSubmitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jSplitPane1.setRightComponent(jPanel2);

        jPanel4.setBackground(new java.awt.Color(0, 204, 204));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153), 10));

        jLabel1.setBackground(new java.awt.Color(153, 153, 0));
        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("View Time Table Details");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(114, 114, 114)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void jSubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSubmitButtonActionPerformed
 
    }//GEN-LAST:event_jSubmitButtonActionPerformed

    private void jSubmitButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSubmitButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jSubmitButton1ActionPerformed

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
        if(roleOfUser.equalsIgnoreCase("admin")==false)
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
        if(roleOfUser.equals("admin"))
    Adminhomescreen.getAdminhomescreen();
else if(roleOfUser.equals("faculty"))
    Facultyhomescreen.getFacultyhomescreen();
else if(roleOfUser.equals("student"))
    Studenthomescreen.getStudenthomescreen();
else
    System.exit(0);
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
        String id = val.contains("_") ? val.substring(0, val.indexOf("_")): val;
        updateTable(timeTables.fetchParticularTimeTable(val), id);
    }//GEN-LAST:event_jVersionBoxActionPerformed

    
    private void updateTable(ArrayList<Schedule> schedule,String id){
        if(schedule==null || schedule.size()<1){
            JOptionPane.showMessageDialog(this, "Time table does not exist for the selection");
            return;
        }
            //System.out.println("Updating table : "+LocalDateTime.now());
            for(int i=0;i<periods.length;i++)
                for(int j=0;j<days.length;j++)
                    timeTable[j][i]="";
            //System.out.println("Reset Completed : "+LocalDateTime.now());
            
        if(id.contains("C"))
            timeTableOf=ClassControl.getClass(id).getName();
        else
            timeTableOf=(String)FacultyControl.getSpecificData(
                           id,new String[]{"name"}) [0];
        jTableOfLabel.setText(timeTableOf);
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
                cellInfo.put(subId,
                        TimeTableControl.getSubject(record.getSubjectId())
                                .getSubjectName());
            String subject = (String)cellInfo.get(subId);
            String val;
            int roomId=record.getRoomId();
            if(id.contains("C"))
            {
                String facId = record.getFacultyId();
                if (!cellInfo.containsKey(facId))
                    cellInfo.put(facId,(String)FacultyControl.getSpecificData(
                           facId,new String[]{"name"}) [0] );
            String facultyName = (String)cellInfo.get(facId);
                val="<html>"+subject+" ("+facultyName+") <br>"+"("+roomId+")</html>";
            }
            else{
                String classId = record.getClassId();
                if (!cellInfo.containsKey(classId))
                    cellInfo.put(classId,
                            ClassControl.getClass(record.getClassId()).getName());
            String className = (String)cellInfo.get(classId);
                val="<html>"+subject+"<br>"+className+"("+roomId+")</html>";
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
                timeTableModel.setValueAt(val,j,(i+1));
            }
        //System.out.println("Displayed table : "+LocalDateTime.now());
    }
    private void clearTable(){
            for(int i=0;i<periods.length;i++)
                for(int j=0;j<days.length;j++)
                    timeTableModel.setValueAt("", j, i+1);
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JButton jSubmitButton;
    private javax.swing.JButton jSubmitButton1;
    private javax.swing.JLabel jTableOfLabel;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable jTimeTable;
    private javax.swing.JComboBox<String> jVersionBox;
    // End of variables declaration//GEN-END:variables
}
