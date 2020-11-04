
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ttmp;

import java.util.Arrays;
import java.util.Calendar;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class StudentDetails extends javax.swing.JFrame {
private boolean cAddressSame=false;
public static final int PERSONAL_DETAILS_PAGE=0;
public static final int PARENT_DETAILS_PAGE=2;
private Student student;
private boolean editMode=false;
private role roleOfUser;
private void setDetails(Student student){
    System.out.println("P ID "+student.getParentId());
    ParentDetails parents=ParentDetailsControl.getParentDetails(student.getParentId());
    System.out.println(parents);
    String name=student.getStudentName(),fname="",lname="";
    if(name.contains(" ")){
        fname=name.substring(0, name.indexOf(" "));
        lname=name.substring(name.indexOf(" ")+1);
    }
    else fname=name;
    jST_FNameField.setText(fname);
    jST_LNameField.setText(lname);
    jST_UserName.setText(student.getUsername());
    jST_EmailField.setText(student.getEmail());
    jST_MobileField.setText(student.getMobileNumber());
    char gender=student.getGender();
    String bloodGroup=student.getBloodGroup();
    jST_BloodGroupBox.removeAllItems();
    jST_BloodGroupBox.addItem(bloodGroup);
    if(gender=='M' || gender=='m'){
        jST_MaleRButton.setSelected(true);
        jST_FemaleRButton.setSelected(false);
    }
    else {
        jST_FemaleRButton.setSelected(true);
        jST_MaleRButton.setSelected(false);
    }
    String pAddress[]=student.getPermanentAddress().split("@");
    for(int i=0;i<5;i++){
        if(i>=pAddress.length)
            break;
        String dat=pAddress[i];
        if(dat==null || dat.equals("null"))
            dat="";
        switch(i){
            case 0: 
                jST_PAddressArea.setText(dat);
                break;
            case 1: 
                jST_PCityField.setText(dat);
                break;
            case 2: 
                jST_PStateField.setText(dat);
                break;
            case 3:  
                jST_PCountryBox.removeAllItems();
                jST_PCountryBox.addItem(dat);
                break;
            case 4: 
                jST_PPinCodeField.setText(dat);
                break;
        }
    }
     String cAddress[]=student.getAddress().split("@");
    for(int i=0;i<5;i++){
        if(i>=cAddress.length)
            break;
        String dat=cAddress[i];
        if(dat==null || dat.equals("null"))
            dat="";
        switch(i){
            case 0: 
                jST_cAddressArea.setText(dat);
                break;
            case 1: 
                jST_cCityField.setText(dat);
                break;
            case 2: 
                jST_cStateField.setText(dat);
                break;
            case 3: 
                jST_cCountryBox.removeAllItems();
                jST_cCountryBox.addItem(dat);
                break;
            case 4: 
                jST_cPinCodeField.setText(dat);
                break;
        }
    }
   //jST_PCityField.setEnabled(val);
    //jST_PStateField.setEnabled(val);
    //jST_PCountryBox.setEnabled(val);
    //jST_PPinCodeField.setEnabled(val);
    jST_cAddressArea.setText(student.getAddress());
    jLabel17.setText("Date Of Registration : "+student.getDateOfRegistration().toString());
    jLabel28.setText("Registration ID : "+student.getRegistrationId());
    //jST_cCityField.setEnabled(val);
    //jST_cStateField.setEnabled(val);
    //jST_cCountryBox.setEnabled(val);
    //jST_cPinCodeField.setEnabled(val);
    jST_FaNameField.setText(parents.getFatherName());
    jST_FaEmailField.setText(parents.getFatherEmailId());
    jST_FaMoblieField.setText(parents.getFatherMobileNumber());
    initBoxes();
    System.out.println("Date DOB: "+student.getDateOfBirth().getYear()+" "
    +student.getDateOfBirth().getMonth()+" "+String.format("%02d",student.getDateOfBirth().getDate()));
    for (int i = 0; i < jST_YearBox.getItemCount(); i++)
        System.out.print(" "+jST_YearBox.getItemAt(i)+",");
    System.out.println();
    jST_YearBox.setSelectedItem(""+student.getDateOfBirth().getYear());
    jST_YearBox.actionPerformed(null);
    jST_MonthBox.setSelectedIndex(student.getDateOfBirth().getMonth());
    jST_MonthBox.actionPerformed(null);
    jST_DateBox.setSelectedItem(String.format("%02d",student.getDateOfBirth().getDate()));
    jST_DateBox.updateUI();
    
    jST_FaYearBox.setSelectedItem(""+parents.getFatherDob().getYear());
    jST_FaYearBox.actionPerformed(null);
    jST_FaMonthBox.setSelectedIndex(parents.getFatherDob().getMonth());
    jST_FaMonthBox.actionPerformed(null);
    jST_FaDateBox.setSelectedItem(String.format("%02d",parents.getFatherDob().getDate()));
    jST_FaDateBox.updateUI();
    jST_MoNameField.setText(parents.getMotherName());
    jST_MoEmailField.setText(parents.getMotherEmail());
    jST_MoMoblieField.setText(parents.getMotherMobileNumber());
    jST_MoYearBox.setSelectedItem(""+parents.getMotherDob().getYear());
    jST_MoYearBox.actionPerformed(null);
    jST_MoMonthBox.setSelectedIndex(parents.getMotherDob().getMonth());
    jST_MoMonthBox.actionPerformed(null);
    jST_MoDateBox.setSelectedItem(String.format("%02d",parents.getMotherDob().getDate()));
    jST_MoDateBox.updateUI();
    jST_IncomeBox.removeAllItems();
    jST_IncomeBox.addItem(parents.getFamilyIncome());
   /*
    jST_FaYearBox.removeAll();jST_FaYearBox.addItem(""+student.getDateOfBirth().getYear());
    jST_FaMonthBox.removeAll();jST_FaMonthBox.addItem(""+student.getDateOfBirth().getMonth());
    jST_DateBox.removeAll();jST_DateBox.addItem(""+student.getDateOfBirth().getDate());
   jST_MoNameField.setEnabled(val);
   jST_MoEmailField.setEnabled(val);
   jST_MoMoblieField.setEnabled(val);
   jST_MoYearBox.setEnabled(val);
   jST_MoMonthBox.setEnabled(val);
   jST_MoDateBox.setEnabled(val);
   jST_IncomeBox.setEnabled(val); 
   jST_PasswordField.setEnabled(val);
   jST_BloodGroupBox.setEnabled(val);
   */
}

private void setComponentsEnabled(boolean val){
    jST_FNameField.setEnabled(val);
    jST_LNameField.setEnabled(val);
    jST_UserName.setEnabled(val);
    jST_YearBox.setEnabled(val);
    jST_MonthBox.setEnabled(val);
   jST_DateBox.setEnabled(val);
   jST_EmailField.setEnabled(val);
    jST_MobileField.setEnabled(val);
   jST_MaleRButton.setEnabled(val);
   jST_FemaleRButton.setEnabled(val);
   jCheckBox1.setEnabled(val);
    jST_PAddressArea.setEnabled(val);
   jST_PCityField.setEnabled(val);
    jST_PStateField.setEnabled(val);
    jST_PCountryBox.setEnabled(val);
    jST_PPinCodeField.setEnabled(val);
    jST_cAddressArea.setEnabled(val);
    jST_cCityField.setEnabled(val);
    jST_cStateField.setEnabled(val);
    jST_cCountryBox.setEnabled(val);
    jST_cPinCodeField.setEnabled(val);
   jST_FaNameField.setEnabled(val);
   jST_FaEmailField.setEnabled(val);
   jST_FaMoblieField.setEnabled(val);
   jST_FaYearBox.setEnabled(val);
   jST_FaMonthBox.setEnabled(val);
   jST_FaDateBox.setEnabled(val);
   jST_MoNameField.setEnabled(val);
   jST_MoEmailField.setEnabled(val);
   jST_MoMoblieField.setEnabled(val);
   jST_MoYearBox.setEnabled(val);
   jST_MoMonthBox.setEnabled(val);
   jST_MoDateBox.setEnabled(val);
   jST_IncomeBox.setEnabled(val); 
   jST_PasswordField.setEnabled(val);
   jST_BloodGroupBox.setEnabled(val);
}

    /**
     * Creates new form NewStudentReg
     */
    public StudentDetails() {
        initComponents();
        jTabbedPane1.setEnabledAt(1,false);
        jTabbedPane1.setEnabledAt(2,false);
        setComponentsEnabled(false);
        roleOfUser = LoginF.getUserRole();
        //jLabel1.setText("<html><U><B>Student Details</B></U></html>");
    }
    public StudentDetails(Student student) {
        initComponents();
        this.student=student;
        System.out.println(student.getRegistrationId()+"  "+student.getStudentName());
        jTabbedPane1.setEnabledAt(1,false);
        jTabbedPane1.setEnabledAt(2,false);
        roleOfUser=LoginF.getUserRole();
        setComponentsEnabled(false);
        setDetails(student);
        jST_SubmitButton.setVisible(false);
    }
    public StudentDetails(Student student,int INDEX) {
        initComponents();
        jTabbedPane1.setSelectedIndex(INDEX);
        this.student=student;
        System.out.println(student.getRegistrationId()+"  "+student.getStudentName());
        jTabbedPane1.setEnabledAt(1,false);
        jTabbedPane1.setEnabledAt(2,false);
        setComponentsEnabled(false);
        roleOfUser=LoginF.getUserRole();
        setDetails(student);
        jST_SubmitButton.setVisible(false);
    }

    private void initBoxes(){
        
        for(int i=Date.getCurrentDate().getYear()-4,n=0;n<=30;i--,n++)
            jST_YearBox.addItem(""+i);
        for(int i=1;i<=12;i++)
            jST_MonthBox.addItem(String.format("%02d", i));
        for(int i=Date.getCurrentDate().getYear()-10,n=0;n<=70;i--,n++)
            jST_FaYearBox.addItem(""+i);
        for(int i=1;i<=12;i++)
            jST_FaMonthBox.addItem(String.format("%02d", i));
        for(int i=Date.getCurrentDate().getYear()-10,n=0;n<=70;i--,n++)
            jST_MoYearBox.addItem(""+i);
        for(int i=1;i<=12;i++)
            jST_MoMonthBox.addItem(String.format("%02d", i));
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane2 = new javax.swing.JSplitPane();
        jST_FNameField3 = new javax.swing.JTextField();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel6 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jST_FNameField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jST_MaleRButton = new javax.swing.JRadioButton();
        jST_FemaleRButton = new javax.swing.JRadioButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jST_EmailField = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jST_MobileField = new javax.swing.JTextField();
        jST_LNameField = new javax.swing.JTextField();
        jST_PasswordField = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        jST_UserName = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jST_BloodGroupBox = new javax.swing.JComboBox<>();
        jST_YearBox = new javax.swing.JComboBox<>();
        jST_MonthBox = new javax.swing.JComboBox<>();
        jST_DateBox = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jST_ResendialArea = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jST_PAddressArea = new javax.swing.JTextArea();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel18 = new javax.swing.JLabel();
        jST_PCountryBox = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jST_PStateField = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jST_PCityField = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jST_PPinCodeField = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jST_cCountryBox = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jST_cStateField = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jST_cCityField = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jST_cPinCodeField = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jST_cAddressArea = new javax.swing.JTextArea();
        jLabel26 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jST_IncomeBox = new javax.swing.JComboBox<>();
        jST_SubmitButton = new javax.swing.JButton();
        jLabel44 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jST_FaEmailField = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jST_MoEmailField = new javax.swing.JTextField();
        jST_FaMoblieField = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jST_FaNameField = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jST_MoMoblieField = new javax.swing.JTextField();
        jST_MoNameField = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jST_FaYearBox = new javax.swing.JComboBox<>();
        jST_FaMonthBox = new javax.swing.JComboBox<>();
        jST_FaDateBox = new javax.swing.JComboBox<>();
        jST_MoDateBox = new javax.swing.JComboBox<>();
        jST_MoMonthBox = new javax.swing.JComboBox<>();
        jST_MoYearBox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel6.setBackground(new java.awt.Color(0, 204, 204));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153), 10));

        jButton5.setText("<-");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Student Details");

        jPanel1.setBackground(new java.awt.Color(0, 204, 204));

        jButton1.setText("Student's Details");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Student's Address Details");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Parent's Details");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(450, Short.MAX_VALUE))
        );

        jSplitPane1.setLeftComponent(jPanel1);

        jTabbedPane1.setBackground(new java.awt.Color(0, 204, 204));

        jPanel2.setBackground(new java.awt.Color(0, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153), 10));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("First Name       :");

        jLabel3.setText("(Max 30 Characters)");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Date Of Birth   :");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("E-mail ID          :");

        jLabel9.setText("(10 Digit Number)");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Mobile Number:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Gender              :");

        buttonGroup1.add(jST_MaleRButton);
        jST_MaleRButton.setText("Male");
        jST_MaleRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jST_MaleRButtonActionPerformed(evt);
            }
        });

        buttonGroup1.add(jST_FemaleRButton);
        jST_FemaleRButton.setText("Female");
        jST_FemaleRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jST_FemaleRButtonActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Student's Detail");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Last Name        :");

        jLabel13.setText("(Max 30 Characters)");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("Password         :");

        jST_PasswordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jST_PasswordFieldActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("User Name       :");

        jLabel15.setText("(Max 30 Characters)");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Blood Group     :");

        jST_BloodGroupBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Blood Group", "O-", "O+", "A-", "A+", "B-", "B+", "AB-", "AB+" }));
        jST_BloodGroupBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jST_BloodGroupBoxActionPerformed(evt);
            }
        });

        jST_YearBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Year" }));
        jST_YearBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jST_YearBoxActionPerformed(evt);
            }
        });

        jST_MonthBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Month" }));
        jST_MonthBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jST_MonthBoxActionPerformed(evt);
            }
        });

        jST_DateBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Date" }));

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("(Registration Date)");

        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jST_EmailField, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jST_YearBox, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jST_MonthBox, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jST_DateBox, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jST_PasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jST_MaleRButton, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jST_FemaleRButton, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel12))
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jST_UserName, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(jST_LNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(jST_FNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jST_BloodGroupBox, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(200, 200, 200))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jST_MobileField, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))))
            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jST_FNameField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jST_LNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jST_UserName, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(13, 13, 13)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jST_YearBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jST_MonthBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jST_DateBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addComponent(jST_EmailField, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jST_PasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 3, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jST_MaleRButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jST_FemaleRButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jST_BloodGroupBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jST_MobileField, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Student's Details", jPanel2);

        jPanel3.setBackground(new java.awt.Color(0, 204, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153), 10));

        jLabel16.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Address Details");

        jST_ResendialArea.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jST_ResendialArea.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jST_ResendialArea.setText("Residential Address            :");

        jST_PAddressArea.setColumns(20);
        jST_PAddressArea.setRows(5);
        jST_PAddressArea.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jST_PAddressAreaFocusLost(evt);
            }
        });
        jScrollPane1.setViewportView(jST_PAddressArea);

        jLabel18.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Country                        :");

        jST_PCountryBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Country", "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", "The Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cabo Verde", "Cambodia", "Cameroon", "Canada", "Central African Republic", "Chad", "Chile", "China", "Colombia", "Comoros", "Congo", "Democratic Republic of the", "Congo", "Republic of the", "Costa Rica", "Côte d’Ivoire", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor (Timor-Leste)", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Eswatini", "Ethiopia", "Fiji", "Finland", "France", "Gabon", "The Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea", "North", "Korea", "South", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Federated States of", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Myanmar (Burma)", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "North Macedonia", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "Spain", "Sri Lanka", "Sudan", "Sudan", "South", "Suriname", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe" }));
        jST_PCountryBox.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jST_PCountryBoxFocusLost(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("State                             :");

        jST_PStateField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jST_PStateFieldFocusLost(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("City                               :");

        jST_PCityField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jST_PCityFieldFocusLost(evt);
            }
        });
        jST_PCityField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jST_PCityFieldActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText(" PINCODE                    :");

        jST_PPinCodeField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jST_PPinCodeFieldFocusLost(evt);
            }
        });
        jST_PPinCodeField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jST_PPinCodeFieldActionPerformed(evt);
            }
        });

        jST_cCountryBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Country", "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", "The Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cabo Verde", "Cambodia", "Cameroon", "Canada", "Central African Republic", "Chad", "Chile", "China", "Colombia", "Comoros", "Congo", "Democratic Republic of the", "Congo", "Republic of the", "Costa Rica", "Côte d’Ivoire", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor (Timor-Leste)", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Eswatini", "Ethiopia", "Fiji", "Finland", "France", "Gabon", "The Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea", "North", "Korea", "South", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Federated States of", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Myanmar (Burma)", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "North Macedonia", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "Spain", "Sri Lanka", "Sudan", "Sudan", "South", "Suriname", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe" }));

        jLabel22.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("State                                     :");

        jLabel23.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("City                                      :");

        jLabel24.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("Coressponding Address    :");

        jST_cCityField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jST_cCityFieldActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText(" PINCODE                        :");

        jST_cPinCodeField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jST_cPinCodeFieldActionPerformed(evt);
            }
        });

        jST_cAddressArea.setColumns(20);
        jST_cAddressArea.setRows(5);
        jScrollPane2.setViewportView(jST_cAddressArea);

        jLabel26.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Country                              :");

        jCheckBox1.setText("Same");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator2)
            .addComponent(jSeparator3)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jST_PPinCodeField, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jST_ResendialArea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jST_PCountryBox, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jST_PStateField, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jST_PCityField, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jST_cCountryBox, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(1, 1, 1)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jST_cPinCodeField, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jST_cStateField, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jST_cCityField, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(48, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jST_ResendialArea, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jST_PCountryBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jST_PStateField, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jST_PCityField, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jST_PPinCodeField, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jST_cCountryBox)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jST_cStateField, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jST_cCityField, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jST_cPinCodeField, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Student's Address Details", jPanel3);

        jPanel4.setBackground(new java.awt.Color(0, 204, 204));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153), 10));

        jLabel37.setText("(Max 30 Characters)");

        jLabel39.setText("Date Of Birth");

        jLabel40.setText("(If Any)");

        jLabel41.setText("(If Any)");

        jLabel42.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel42.setText("Family Income     :");

        jST_IncomeBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "INCOME", "0-200000", "200000-500000", "500000-1000000", "ABOVE 1000000" }));

        jST_SubmitButton.setText("Edit");
        jST_SubmitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jST_SubmitButtonActionPerformed(evt);
            }
        });

        jLabel44.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel44.setText("Father's Details:");

        jLabel30.setText("(Max 30 Characters)");

        jLabel31.setText("Date Of Birth");

        jLabel32.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("Mother's Details:");

        jLabel45.setText("E-mail ID");

        jST_FaEmailField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jST_FaEmailFieldActionPerformed(evt);
            }
        });

        jLabel46.setText("Mobile Number");

        jLabel33.setText("E-mail ID");

        jLabel47.setText("Father's Name");

        jST_MoEmailField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jST_MoEmailFieldActionPerformed(evt);
            }
        });

        jLabel34.setText("Mobile Number");

        jLabel35.setText("Mother's Name");

        jLabel48.setText("(10 Digit Number)");

        jLabel36.setText("(10 Digit Number)");

        jLabel27.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("Parent's Details");

        jST_FaYearBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Year" }));
        jST_FaYearBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jST_FaYearBoxActionPerformed(evt);
            }
        });

        jST_FaMonthBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Month" }));
        jST_FaMonthBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jST_FaMonthBoxActionPerformed(evt);
            }
        });

        jST_FaDateBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Date" }));

        jST_MoDateBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Date" }));

        jST_MoMonthBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Month" }));
        jST_MoMonthBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jST_MoMonthBoxActionPerformed(evt);
            }
        });

        jST_MoYearBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Year" }));
        jST_MoYearBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jST_MoYearBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator4)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel44, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel35)
                                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jST_FaMoblieField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jST_FaEmailField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jST_FaNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jST_FaYearBox, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jST_FaMonthBox, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jST_FaDateBox, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jST_MoNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jST_MoMoblieField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jST_MoEmailField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jST_MoYearBox, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jST_MoMonthBox, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jST_MoDateBox, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 90, Short.MAX_VALUE))
                    .addComponent(jLabel47, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jST_SubmitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jST_IncomeBox, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel47)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jST_FaNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jST_FaYearBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jST_FaMonthBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jST_FaDateBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jST_FaEmailField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel41)
                            .addComponent(jLabel45))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jST_FaMoblieField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel48)
                            .addComponent(jLabel46))
                        .addGap(16, 16, 16)))
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jST_MoNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel37))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jST_MoYearBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jST_MoMonthBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jST_MoDateBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jST_MoEmailField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel40))
                    .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jST_MoMoblieField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36)
                    .addComponent(jLabel34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jST_IncomeBox, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jST_SubmitButton)
                .addGap(85, 85, 85))
        );

        jTabbedPane1.addTab("Parent's Details", jPanel4);

        jSplitPane1.setRightComponent(jTabbedPane1);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(jButton5)
                .addGap(28, 28, 28)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 707, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton5)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jST_MaleRButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jST_MaleRButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jST_MaleRButtonActionPerformed

    private void jST_PCityFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jST_PCityFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jST_PCityFieldActionPerformed

    private void setCAddress(){
        if(cAddressSame){
            jST_cAddressArea.setText(jST_PAddressArea.getText());
            jST_cStateField.setText(jST_PStateField.getText());
            jST_cCityField.setText(jST_PCityField.getText());
            jST_cPinCodeField.setText(jST_PPinCodeField.getText());
            jST_cCountryBox.setSelectedIndex(jST_PCountryBox.getSelectedIndex());
        }
    }
    private void jST_PPinCodeFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jST_PPinCodeFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jST_PPinCodeFieldActionPerformed

    private void jST_cCityFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jST_cCityFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jST_cCityFieldActionPerformed

    private void jST_cPinCodeFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jST_cPinCodeFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jST_cPinCodeFieldActionPerformed
private void setModeChanges()
{
    if (editMode)
    {
        jST_SubmitButton.setText("Save Changes");
    }
    else
    {
        jST_SubmitButton.setText("Edit");
    }
    setComponentsEnabled(editMode);
}
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jTabbedPane1.setSelectedIndex(0);                // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jST_PasswordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jST_PasswordFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jST_PasswordFieldActionPerformed

    private void jST_FemaleRButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jST_FemaleRButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jST_FemaleRButtonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jTabbedPane1.setSelectedIndex(1);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        jTabbedPane1.setSelectedIndex(2);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        jTabbedPane1.setEnabledAt(0,true);
        jTabbedPane1.setEnabledAt(1,false);
        jTabbedPane1.setEnabledAt(2,false);       // TODO add your handling code here:
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        jTabbedPane1.setEnabledAt(0,false);
        jTabbedPane1.setEnabledAt(1,true);
        jTabbedPane1.setEnabledAt(2,false);       // TODO add your handling code here:
            // TODO add your handling code here:
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        jTabbedPane1.setEnabledAt(0,false);
        jTabbedPane1.setEnabledAt(1,false);
        jTabbedPane1.setEnabledAt(2,true);       // TODO add your handling code here:        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
if(roleOfUser != role.STUDENT)
    StudentSearch.getStudentSearch();
else
    Studenthomescreen.getStudenthomescreen();
        this.dispose();// TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jST_YearBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jST_YearBoxActionPerformed
         if(jST_YearBox.getSelectedIndex()>=0)
        {
            jST_MonthBox.setSelectedIndex(0);
            jST_DateBox.removeAllItems();
            jST_DateBox.addItem("Date");
            jST_DateBox.setSelectedIndex(0);
        }           // TODO add your handling code here:
    }//GEN-LAST:event_jST_YearBoxActionPerformed

    private void jST_MonthBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jST_MonthBoxActionPerformed
         if(jST_YearBox.getSelectedIndex()<1||jST_MonthBox.getSelectedIndex()<1)
        {   jST_DateBox.removeAllItems();
            jST_DateBox.addItem("Date");
            jST_DateBox.setSelectedIndex(0);
            return;
        
        }
        int year,yc=0,mc=30;
            year=Integer.valueOf(String.valueOf(jST_YearBox.getSelectedItem() ) );
        if(year%100==0)
        {
            if(year%400==0)yc=1;
        }
        else if(year%4==0)yc=1;
        else yc=0;
        switch(jST_MonthBox.getSelectedIndex()){
            case 1:case 3:case 5:case 7:case 8:case 10:case 12:mc=31;break;
            case 4:case 6:case 9:case 11:mc=30;break;
            case 2:mc=(yc==0)?28:29;
            break;
        }
        jST_DateBox.removeAllItems();
        jST_DateBox.addItem("DATE");
        for(int i=1;i<=mc;i++)
            jST_DateBox.addItem(String.format("%02d",i));        // TODO add your handling code here:
    }//GEN-LAST:event_jST_MonthBoxActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        cAddressSame=jCheckBox1.isSelected();
        jST_cCountryBox.setEnabled(!cAddressSame);
        jST_cStateField.setEnabled(!cAddressSame);
        jST_cCityField.setEnabled(!cAddressSame);
        jST_cAddressArea.setEnabled(!cAddressSame);
        jST_cPinCodeField.setEnabled(!cAddressSame);
        setCAddress();
        
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jST_PAddressAreaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jST_PAddressAreaFocusLost
setCAddress();
    }//GEN-LAST:event_jST_PAddressAreaFocusLost

    private void jST_PStateFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jST_PStateFieldFocusLost
setCAddress();
    }//GEN-LAST:event_jST_PStateFieldFocusLost

    private void jST_PCityFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jST_PCityFieldFocusLost
setCAddress();
    }//GEN-LAST:event_jST_PCityFieldFocusLost

    private void jST_PPinCodeFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jST_PPinCodeFieldFocusLost
setCAddress();
    }//GEN-LAST:event_jST_PPinCodeFieldFocusLost

    private void jST_PCountryBoxFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jST_PCountryBoxFocusLost
if(jST_PCountryBox.getSelectedIndex()>=0){
    setCAddress();
}
    }//GEN-LAST:event_jST_PCountryBoxFocusLost

    private void jST_BloodGroupBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jST_BloodGroupBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jST_BloodGroupBoxActionPerformed

    private void jST_MoYearBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jST_MoYearBoxActionPerformed
        if(jST_MoYearBox.getSelectedIndex()>=0)
        {
            jST_MoMonthBox.setSelectedIndex(0);
            jST_MoDateBox.removeAllItems();
            jST_MoDateBox.addItem("Date");
            jST_MoDateBox.setSelectedIndex(0);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jST_MoYearBoxActionPerformed

    private void jST_MoMonthBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jST_MoMonthBoxActionPerformed
        if(jST_MoYearBox.getSelectedIndex()<1||jST_MoMonthBox.getSelectedIndex()<1)
        {   jST_MoDateBox.removeAllItems();
            jST_MoDateBox.addItem("Date");
            jST_MoDateBox.setSelectedIndex(0);
            return;

        }
        int year,yc=0,mc=30;
        year=Integer.valueOf(String.valueOf(jST_MoYearBox.getSelectedItem() ) );
        if(year%100==0)
        {
            if(year%400==0)yc=1;
        }
        else if(year%4==0)yc=1;
        else yc=0;
        switch(jST_MoMonthBox.getSelectedIndex()){
            case 1:case 3:case 5:case 7:case 8:case 10:case 12:mc=31;break;
            case 4:case 6:case 9:case 11:mc=30;break;
            case 2:mc=(yc==0)?28:29;
            break;
        }
        jST_MoDateBox.removeAllItems();
        jST_MoDateBox.addItem("DATE");
        for(int i=1;i<=mc;i++)
        jST_MoDateBox.addItem(String.format("%02d",i));        // TODO add your handling code here:
    }//GEN-LAST:event_jST_MoMonthBoxActionPerformed

    private void jST_FaMonthBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jST_FaMonthBoxActionPerformed
        if(jST_FaYearBox.getSelectedIndex()<1||jST_FaMonthBox.getSelectedIndex()<1)
        {   jST_FaDateBox.removeAllItems();
            jST_FaDateBox.addItem("Date");
            jST_FaDateBox.setSelectedIndex(0);
            return;

        }
        int year,yc=0,mc=30;
        year=Integer.valueOf(String.valueOf(jST_FaYearBox.getSelectedItem() ) );
        if(year%100==0)
        {
            if(year%400==0)yc=1;
        }
        else if(year%4==0)yc=1;
        else yc=0;
        switch(jST_FaMonthBox.getSelectedIndex()){
            case 1:case 3:case 5:case 7:case 8:case 10:case 12:mc=31;break;
            case 4:case 6:case 9:case 11:mc=30;break;
            case 2:mc=(yc==0)?28:29;
            break;
        }
        jST_FaDateBox.removeAllItems();
        jST_FaDateBox.addItem("DATE");
        for(int i=1;i<=mc;i++)
        jST_FaDateBox.addItem(String.format("%02d",i));        // TODO add your handling code here:
    }//GEN-LAST:event_jST_FaMonthBoxActionPerformed

    private void jST_FaYearBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jST_FaYearBoxActionPerformed
        if(jST_FaYearBox.getSelectedIndex()>=0)
        {
            jST_FaMonthBox.setSelectedIndex(0);
            jST_FaDateBox.removeAllItems();
            jST_FaDateBox.addItem("Date");
            jST_FaDateBox.setSelectedIndex(0);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jST_FaYearBoxActionPerformed

    private void jST_MoEmailFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jST_MoEmailFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jST_MoEmailFieldActionPerformed

    private void jST_FaEmailFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jST_FaEmailFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jST_FaEmailFieldActionPerformed

    private void jST_SubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jST_SubmitButtonActionPerformed
        if (editMode)
        {
            editMode = false;
        }
        else
        {
            editMode = true;
        }
        setModeChanges();
    }//GEN-LAST:event_jST_SubmitButtonActionPerformed

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
            java.util.logging.Logger.getLogger(StudentDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudentDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudentDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StudentDetails().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JComboBox<String> jST_BloodGroupBox;
    private javax.swing.JComboBox<String> jST_DateBox;
    private javax.swing.JTextField jST_EmailField;
    private javax.swing.JTextField jST_FNameField;
    private javax.swing.JTextField jST_FNameField3;
    private javax.swing.JComboBox<String> jST_FaDateBox;
    private javax.swing.JTextField jST_FaEmailField;
    private javax.swing.JTextField jST_FaMoblieField;
    private javax.swing.JComboBox<String> jST_FaMonthBox;
    private javax.swing.JTextField jST_FaNameField;
    private javax.swing.JComboBox<String> jST_FaYearBox;
    private javax.swing.JRadioButton jST_FemaleRButton;
    private javax.swing.JComboBox<String> jST_IncomeBox;
    private javax.swing.JTextField jST_LNameField;
    private javax.swing.JRadioButton jST_MaleRButton;
    private javax.swing.JComboBox<String> jST_MoDateBox;
    private javax.swing.JTextField jST_MoEmailField;
    private javax.swing.JTextField jST_MoMoblieField;
    private javax.swing.JComboBox<String> jST_MoMonthBox;
    private javax.swing.JTextField jST_MoNameField;
    private javax.swing.JComboBox<String> jST_MoYearBox;
    private javax.swing.JTextField jST_MobileField;
    private javax.swing.JComboBox<String> jST_MonthBox;
    private javax.swing.JTextArea jST_PAddressArea;
    private javax.swing.JTextField jST_PCityField;
    private javax.swing.JComboBox<String> jST_PCountryBox;
    private javax.swing.JTextField jST_PPinCodeField;
    private javax.swing.JTextField jST_PStateField;
    private javax.swing.JPasswordField jST_PasswordField;
    private javax.swing.JLabel jST_ResendialArea;
    private javax.swing.JButton jST_SubmitButton;
    private javax.swing.JTextField jST_UserName;
    private javax.swing.JComboBox<String> jST_YearBox;
    private javax.swing.JTextArea jST_cAddressArea;
    private javax.swing.JTextField jST_cCityField;
    private javax.swing.JComboBox<String> jST_cCountryBox;
    private javax.swing.JTextField jST_cPinCodeField;
    private javax.swing.JTextField jST_cStateField;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
