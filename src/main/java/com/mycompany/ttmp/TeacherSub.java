/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ttmp;

import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class TeacherSub extends javax.swing.JFrame {

    /**
     * Creates new form TeacherSub
     */
    private DefaultListModel subjectListModel;
    private final DefaultListModel facultyListModel;
    private final DefaultTableModel model;
    private final I_Class[] classes;
    private I_Class selectedClass;
    private final Faculty[] faculties;
    private final Subject[] subjects;
    private final ArrayList<Subject> selectedSubjects = new ArrayList<>();
    private ArrayList<Faculty> availableFaculties = new ArrayList<>();
    private ArrayList<Subject> availableSubjects = new ArrayList<>();
    private int selectedFacultyIndex = -1;
    private int selectedSubjectIndex = -1;
    private int newRowNumber = -1;
    private int selectedRow = -1;
    private boolean canAddRow = true;
    private boolean rowEdit = false;
    private boolean subjectsLocked = false;
    public static final int CREATE_MODE = 1;
    public static final int EDIT_MODE = 2;
    public static final int CREATE_OR_EDIT_MODE = 12;
    public static final int READ_MODE = 3;
    private int currentMode;
    private int modePermission;

    public TeacherSub() {
        initComponents();
        subjectListModel = new DefaultListModel<>();
        facultyListModel = new DefaultListModel<>();
        jFacultyList.setModel(facultyListModel);
        jSubjectList.setModel(subjectListModel);
        model = (DefaultTableModel) jTable1.getModel();
        classes = I_ClassControl.getAllCLass();
        faculties = FacultyControl.getAllFaculty();
        subjects = SubjectControl.getAllSubject();
        jClassBox.removeAllItems();
        jClassBox.addItem("Select Class");
        for (I_Class clas : classes) {
            jClassBox.addItem(clas.getName());
        }
        jAddButton.setText("Add New Row");
        jEditRowButton.setEnabled(false);

    }

    public TeacherSub(String classId, int pmode, int modePermission) {
        initComponents();
        currentMode = pmode;
        this.modePermission = modePermission;
        subjectListModel = new DefaultListModel<>();
        facultyListModel = new DefaultListModel<>();
        jFacultyList.setModel(facultyListModel);
        jSubjectList.setModel(subjectListModel);
        model = (DefaultTableModel) jTable1.getModel();
        classes = TimeTableControl.getClasses();
        faculties = FacultyControl.getAllFaculty();
        subjects = SubjectControl.getAllSubject();
        jClassBox.removeAllItems();
        jClassBox.addItem("Select Class");
        for (I_Class clas : classes) {
            jClassBox.addItem(clas.getName());
        }
        jAddButton.setText("Add New Row");
        for (I_Class clas : classes) {
            if (clas.getId().equals(classId)) {
                jClassBox.setSelectedItem(clas.getName());
                selectedClass = clas;
                break;
            }
        }
        if (selectedClass.getAssignmentStatus()) {
            subjectsLocked = true;
        }
        doModeChanges();
        jClassBoxActionPerformed(null);
    }

    private void doModeChanges() {
        
        switch (currentMode) {
            case READ_MODE:
                jAddButton.setEnabled(false);
                jRemoveButton.setEnabled(false);
                jRemoveAllButton.setEnabled(false);
                jEditRowButton.setEnabled(false);
                jSaveButton.setEnabled(false);
                if (modePermission == EDIT_MODE || modePermission == CREATE_OR_EDIT_MODE) {
                    jEditButton.setEnabled(true);
                } else {
                    jEditButton.setEnabled(false);
                }
                break;
            case CREATE_MODE:
                jAddButton.setEnabled(true);
                jRemoveButton.setEnabled(true);
                jRemoveAllButton.setEnabled(true);
                jSaveButton.setEnabled(false);
                jEditRowButton.setEnabled(false);
                jEditButton.setEnabled(false);
                break;
            case EDIT_MODE:
            case CREATE_OR_EDIT_MODE:
            
                if (subjectsLocked && 
                        new TimeTables(selectedClass.getId()).updateScheduled) 
                {
                    int res = JOptionPane.showConfirmDialog(this, "this class has a time table "
                            + "change scheduled.\n"
                            + "if you choose to update assignments anyway, the current scheduled update will be discarded and deleted.\n"
                            + "Are you sure you want to continue?\n\n"
                            + "P.S. this message may also appear while you try to save the changes",
                            "Change Scheduled", JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.WARNING_MESSAGE);
                    if (res != JOptionPane.YES_OPTION) {
                        break;
                    }
                }
                jRemoveButton.setEnabled(!subjectsLocked);
                jRemoveAllButton.setEnabled(!subjectsLocked);
                jAddButton.setEnabled(!subjectsLocked);
                jSaveButton.setEnabled(false);
                jEditRowButton.setEnabled(false);
                jEditButton.setEnabled(false);
                break;
            default:
                System.exit(1);
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jClassBox = new javax.swing.JComboBox<>();
        jEditButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jRemoveAllButton = new javax.swing.JButton();
        jRemoveButton = new javax.swing.JButton();
        jSaveButton = new javax.swing.JButton();
        jEditRowButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jFacultyList = new javax.swing.JList<>();
        jAddButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jSubjectList = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(0, 204, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153), 10));

        jPanel2.setBackground(new java.awt.Color(0, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153), 10));

        jLabel2.setBackground(new java.awt.Color(0, 255, 255));
        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 0, 153));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Faculty Subject Records");
        jLabel2.setOpaque(true);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(0, 153, 153));

        jClassBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Class", "6", "7", "8" }));
        jClassBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jClassBoxMouseClicked(evt);
            }
        });
        jClassBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jClassBoxActionPerformed(evt);
            }
        });

        jEditButton.setText("Edit");
        jEditButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jEditButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jClassBox, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jEditButton, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jClassBox, javax.swing.GroupLayout.PREFERRED_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(jEditButton))
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(0, 255, 255));

        jRemoveAllButton.setText("Remove all Items");
        jRemoveAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRemoveAllButtonActionPerformed(evt);
            }
        });

        jRemoveButton.setText("Remove Data");
        jRemoveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRemoveButtonActionPerformed(evt);
            }
        });

        jSaveButton.setText("SAVE");
        jSaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSaveButtonActionPerformed(evt);
            }
        });

        jEditRowButton.setText("Edit Row");
        jEditRowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jEditRowButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jRemoveAllButton, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jRemoveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jEditRowButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSaveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSaveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jRemoveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jEditRowButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jRemoveAllButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jLabel1.setBackground(new java.awt.Color(0, 255, 255));
        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Faculty Subject Records");
        jLabel1.setOpaque(true);

        jPanel5.setBackground(new java.awt.Color(0, 153, 153));

        jButton1.setText("<-");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jButton1)
                .addGap(0, 10, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jButton1)
                .addGap(0, 10, Short.MAX_VALUE))
        );

        jSplitPane1.setDividerLocation(150);
        jSplitPane1.setDividerSize(3);

        jFacultyList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jFacultyList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jFacultyListMouseReleased(evt);
            }
        });
        jFacultyList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jFacultyListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jFacultyList);

        jAddButton.setText("ADD");
        jAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAddButtonActionPerformed(evt);
            }
        });

        jSubjectList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jSubjectList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jSubjectListMouseReleased(evt);
            }
        });
        jSubjectList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jSubjectListValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(jSubjectList);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addComponent(jScrollPane3)
            .addComponent(jAddButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jAddButton, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jSplitPane1.setLeftComponent(jPanel7);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "S.No.", "Faculty Name", "Faculty ID", "Subject", "Subject ID", "Class", "Room ID", "Select"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTable1MouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        jSplitPane1.setRightComponent(jScrollPane2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 732, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jClassBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jClassBoxActionPerformed
        int index = jClassBox.getSelectedIndex();
        if (index < 0) {
            return;
        }
        if (index == 0)
            jLabel1.setText("Faculty Subject Records");
        else {
            jLabel1.setText("Records Of " + classes[index - 1].getName() + "th Class");
            Object[][] records = ClassroomManager.getRecords(classes[index - 1].getId());
            if (records != null) {
                model.setRowCount(0);
                selectedSubjects.clear();
                for (int i = 0; i < records.length; i++) {
                    selectedSubjects.add(new Subject("" + records[i][3],
                            "" + records[i][4], -1));
                    model.addRow(new Object[]{i + 1, records[i][2], records[i][1],
                        records[i][4], records[i][3], records[i][0], records[i][5], false});
                }
            } else {
                if (currentMode == CREATE_MODE) {
                    return;
                } else if (modePermission == CREATE_MODE || modePermission == CREATE_OR_EDIT_MODE) {
                    currentMode = modePermission;
                    doModeChanges();
                } else {
                    JOptionPane.showMessageDialog(this, "NO RECORDS FOUND , PLEASE ADD RECORDS BEFORE VIEWING");
                    this.dispose();
                }
            }
            jClassBox.setEnabled(false);
        }
    }//GEN-LAST:event_jClassBoxActionPerformed

    private void jAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jAddButtonActionPerformed
        if (currentMode == READ_MODE) {
            return;
        }
        if (canAddRow) {
            if (rowEdit) {
                rowEdit = false;
                Faculty faculty = availableFaculties.get(selectedFacultyIndex);
                Subject subject = availableSubjects.get(selectedSubjectIndex);
                selectedSubjects.remove(new Subject("" + model.getValueAt(selectedRow, 4)));
                model.setValueAt(faculty.getName(), selectedRow, 1);
                model.setValueAt(faculty.getFacultyId(), selectedRow, 2);
                model.setValueAt(subject.getSubjectName(), selectedRow, 3);
                model.setValueAt(subject.getSubjectId(), selectedRow, 4);
                selectedSubjects.add(subject);
                canAddRow = true;
                jAddButton.setText("Add New Row");
                if (!subjectsLocked) {
                    jAddButton.setEnabled(true);
                } else {
                    jAddButton.setEnabled(false);
                }
                jSaveButton.setEnabled(true);
                jEditRowButton.setText("Edit Row");
                jEditRowButton.setEnabled(false);
                subjectListModel.removeAllElements();
                facultyListModel.removeAllElements();
            } else {
                System.out.println(subjects.length + " " + selectedSubjects.size());
                if (subjects.length <= selectedSubjects.size()) {
                    JOptionPane.showMessageDialog(this, "Alls subjects already added");
                    return;
                }
                model.addRow(new Object[]{model.getRowCount() + 1, "",
                    "", "", "", classes[jClassBox.getSelectedIndex() - 1].getName(),
                    classes[jClassBox.getSelectedIndex() - 1].getRoomId(), false}
                );
                canAddRow = false;
                newRowNumber = jTable1.getRowCount() - 1;
                jAddButton.setText("Add Data");
                jAddButton.setEnabled(false);
                jSaveButton.setEnabled(false);
                updateLists();
            }
        } else {
            Faculty faculty = availableFaculties.get(selectedFacultyIndex);
            Subject subject = availableSubjects.get(selectedSubjectIndex);
            model.setValueAt(faculty.getName(), newRowNumber, 1);
            model.setValueAt(faculty.getFacultyId(), newRowNumber, 2);
            model.setValueAt(subject.getSubjectName(), newRowNumber, 3);
            model.setValueAt(subject.getSubjectId(), newRowNumber, 4);
            selectedSubjects.add(subject);
            canAddRow = true;
            jAddButton.setText("Add New Row");
            if (!subjectsLocked) {
                jAddButton.setEnabled(true);
            } else {
                jAddButton.setEnabled(false);
            }
            subjectListModel.removeAllElements();
            facultyListModel.removeAllElements();
            jSaveButton.setEnabled(true);
        }
    }//GEN-LAST:event_jAddButtonActionPerformed

    private void jClassBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jClassBoxMouseClicked

    }//GEN-LAST:event_jClassBoxMouseClicked

    private void jTable1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseReleased
        int row = jTable1.getSelectedRow(), column = jTable1.getSelectedColumn();
        if (row < 0 || currentMode == READ_MODE) {
            return;
        }
        if (!canAddRow) {
            jAddButton.setEnabled(false);
        } else {
            selectedRow = row;
            jEditRowButton.setEnabled(true);
        }
        //updateLists(); 
    }//GEN-LAST:event_jTable1MouseReleased

    private void jFacultyListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFacultyListMouseReleased

    }//GEN-LAST:event_jFacultyListMouseReleased

    private void jSubjectListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSubjectListMouseReleased

    }//GEN-LAST:event_jSubjectListMouseReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jRemoveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRemoveButtonActionPerformed
        /*
    if(!canAddRow)
    {
        JOptionPane.showMessageDialog(this,"please complete row addition operation first");
        return;
    }
         */
        removeRows();
    }//GEN-LAST:event_jRemoveButtonActionPerformed

    private void jRemoveAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRemoveAllButtonActionPerformed
        for (int i = 0; i < model.getRowCount(); i++) {
            model.setValueAt(true, i, 7);
        }
        removeRows();
    }//GEN-LAST:event_jRemoveAllButtonActionPerformed

    private void jSubjectListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jSubjectListValueChanged
        if (currentMode == READ_MODE) {
            return;
        }

        int index = jSubjectList.getSelectedIndex();
        if (index >= 0) {
            selectedSubjectIndex = index;
            if (selectedFacultyIndex >= 0) {
                jAddButton.setEnabled(true);
            }
        } else {
            selectedSubjectIndex = -1;
            if (!canAddRow || rowEdit) {
                jAddButton.setEnabled(false);
            }
        }
    }//GEN-LAST:event_jSubjectListValueChanged

    private void jFacultyListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jFacultyListValueChanged
        if (currentMode == READ_MODE) {
            return;
        }

        int index = jFacultyList.getSelectedIndex();
        if (index >= 0) {
            selectedFacultyIndex = index;
            if (selectedSubjectIndex >= 0) {
                jAddButton.setEnabled(true);
            }
        } else {
            selectedFacultyIndex = -1;
            if (!canAddRow || rowEdit) {
                jAddButton.setEnabled(false);
            }
        }
    }//GEN-LAST:event_jFacultyListValueChanged

    private void jSaveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSaveButtonActionPerformed
        if (currentMode == READ_MODE) {
            return;
        }
        String msg = "";
        if (subjectsLocked) 
        {
            if (new TimeTables(selectedClass.getId()).updateScheduled) 
            {
            
                msg += "this class has a time table "
                        + "change scheduled.\n"
                        + "if you choose to update assignments anyway, "
                        + "the current scheduled update will be discarded and deleted.\n\n";
            }
            
                msg += "this class has a time table \n"
                        + "if you choose to update assignments anyway, "
                        + "the current time table will be discontinued and you would have to schedule a new one.\n\n";
        }
        msg += "Are you sure you want to save this";
        
        int res = JOptionPane.showConfirmDialog(this, msg,
                "Change Scheduled", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE);
        if (res != JOptionPane.YES_OPTION) 
        {
            return;
        }
        ClassSubjectFaculty[] records = new ClassSubjectFaculty[model.getRowCount()];
        for (int i = 0; i < model.getRowCount(); i++) {
            records[i] = new ClassSubjectFaculty(classes[jClassBox.getSelectedIndex() - 1].getId(),
                    "" + model.getValueAt(i, 2), "" + model.getValueAt(i, 4), Integer.valueOf("" + model.getValueAt(i, 6)));
        }
        ClassroomManager.addToRecords(records);
        this.dispose();
        
    }//GEN-LAST:event_jSaveButtonActionPerformed

    private void jEditRowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jEditRowButtonActionPerformed
        if (currentMode == READ_MODE) {
            return;
        }

        if (rowEdit) {
            rowEdit = false;
            selectedRow = -1;
            jAddButton.setText("Add New Row");
            jEditRowButton.setText("Edit Row");
            jEditRowButton.setEnabled(false);
            if (!subjectsLocked) {
                jAddButton.setEnabled(true);
            }
            jSaveButton.setEnabled(true);
            subjectListModel.removeAllElements();
            facultyListModel.removeAllElements();
        } else {
            rowEdit = true;
            jAddButton.setText("Update");
            jAddButton.setEnabled(false);
            jSaveButton.setEnabled(false);
            jEditRowButton.setText("Cancel");
            updateLists();
            String facultyListValue = "" + model.getValueAt(selectedRow, 1) + "("
                    + model.getValueAt(selectedRow, 2) + ")";
            String subjectListValue = "" + model.getValueAt(selectedRow, 3) + "("
                    + model.getValueAt(selectedRow, 4) + ")";
            jFacultyList.setSelectedValue(facultyListValue, true);
            jSubjectList.setSelectedValue(subjectListValue, true);
        }
    }//GEN-LAST:event_jEditRowButtonActionPerformed

    private void jEditButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jEditButtonActionPerformed
        if (modePermission == READ_MODE || modePermission == CREATE_MODE) {
            return;
        }

        currentMode = EDIT_MODE;
        doModeChanges();
    }//GEN-LAST:event_jEditButtonActionPerformed

    private void removeRows() {
        int removed = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            if ((boolean) model.getValueAt(i, 7) == true) {
                System.out.println(new Subject("" + model.getValueAt(i, 4)));
                selectedSubjects.remove(new Subject("" + model.getValueAt(i, 4)));
                if (i == model.getRowCount() - 1 && canAddRow == false) {
                    canAddRow = true;
                    jAddButton.setText("Add New Row");
                    jAddButton.setEnabled(true);
                    subjectListModel.removeAllElements();
                    facultyListModel.removeAllElements();
                }
                model.removeRow(i);
                removed++;
                i--;
            }
        }
        if (removed > 0) {
        } else {
            JOptionPane.showMessageDialog(this, "please select rows to remove first");
        }
    }

    private void updateLists() {
        subjectListModel.removeAllElements();
        facultyListModel.removeAllElements();
        availableFaculties = new ArrayList<>();
        availableSubjects = new ArrayList<>();
        if (!subjectsLocked) {
            for (Subject subject : subjects) {
                if ((!rowEdit && selectedSubjects.contains(subject))
                        || (rowEdit && selectedSubjects.contains(subject) && subject.equals(new Subject("" + model.getValueAt(selectedRow, 4))) == false)) {
                    continue;
                }
                availableSubjects.add(subject);
                subjectListModel.addElement(subject.getSubjectName() + "(" + subject.getSubjectId() + ")");
            }
        } else {
            for (Subject subject : subjects) {
                if ((rowEdit && selectedSubjects.contains(subject)
                        && subject.equals(new Subject("" + model.getValueAt(selectedRow, 4))))) {
                    availableSubjects.add(subject);
                    subjectListModel.addElement(subject.getSubjectName() + "(" + subject.getSubjectId() + ")");
                    break;
                }
            }

//            selectedSubjects.contains(subject) 
//                    && subject.equals(new Subject(""+model.getValueAt(selectedRow,4)))            
        }
        for (Faculty faculty : faculties) {
            availableFaculties.add(faculty);
            facultyListModel.addElement(faculty.getName() + "(" + faculty.getFacultyId() + ")");
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TeacherSub().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jAddButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jClassBox;
    private javax.swing.JButton jEditButton;
    private javax.swing.JButton jEditRowButton;
    private javax.swing.JList<String> jFacultyList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JButton jRemoveAllButton;
    private javax.swing.JButton jRemoveButton;
    private javax.swing.JButton jSaveButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JList<String> jSubjectList;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
