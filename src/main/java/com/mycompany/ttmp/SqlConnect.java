/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ttmp;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;

import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;

/**
 *
 * @author Programming
 */
public class SqlConnect {
    private static final String DRIVER_NAME = "java.sql.DriverManager";
    private static final String DATABASE_NAME = "TTMPRATEEKv2";
    public static final String UNKNOWN_DATABASE_ERROR_MESSAGE = "unknown database 'ttmprateekv2'";

    static
    {
        try
        {
            Class.forName(DRIVER_NAME);
            System.out.println("*** Driver loaded");
        }
        catch(Exception e)
        {
            System.out.println("*** Error : "+e.toString());
            System.out.println("*** ");
            System.out.println("*** Error : ");
            e.printStackTrace();
        }

    }

    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "prateek";
    private static final String PASSWORD = "Prat1*ek";
    private static String INSTRUCTIONS = new String();

    public static Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    public static Connection getDatabaseConnection() throws SQLException
    {
        return DriverManager.getConnection(URL+DATABASE_NAME, USER, PASSWORD);
    }
    

    public static void resetDatabase() throws SQLException
    {
        String s            = new String();
        StringBuffer sb = new StringBuffer();

        try
        {
            InputStream is=TTM.class.getResourceAsStream("/com/mycompany/backend.sql");
            
            System.out.println(is);
            InputStreamReader ir = new InputStreamReader(is);
            // be sure to not have line starting with "--" or "/*" or any other non aplhabetical character

            BufferedReader br = new BufferedReader(ir);

            while((s = br.readLine()) != null)
            {
                sb.append(s);
            }
            br.close();

            // here is our splitter ! We use ";" as a delimiter for each request
            // then we are sure to have well formed statements
            String[] inst = sb.toString().split(";");

            Connection c = SqlConnect.getConnection();
            Statement st = c.createStatement();

            for(int i = 0; i<inst.length; i++)
            {
                // we ensure that there is no spaces before or after the request string
                // in order to not execute empty statements
                if(!inst[i].trim().equals(""))
                {
                    System.out.println(">>"+inst[i]);
                    st.executeUpdate(inst[i]);
                }
            }
  
        }
        catch(Exception e)
        {
            System.out.println("*** Error : "+e.toString());
            System.out.println("*** ");
            System.out.println("*** Error : ");
            e.printStackTrace();
            System.out.println("################################################");
            System.out.println(sb.toString());
        }

    }    
    public static void fillDummyData(){
 try(Connection c=SqlConnect.getDatabaseConnection()) {
            Statement st = c.createStatement();
            st.execute("start transaction;");
            String query;
            st.execute(AdminControl.getInsertStatement(999, Admin.createAdmin(1001,"Prateek Bhatia","pb@g.in","9876543210","prateek","dev")));
            st.execute(AdminControl.getInsertStatement(999, Admin.createAdmin(1002,"Dev Garg","dg@p.in","8976543210","gamingfever","dev")));
            query=AccountControl.getInsertStatement(999, Account.createAccount("prateek", "12345678", "admin"));
            st.execute(query);
            st.execute(AccountControl.getInsertStatement(999, Account.createAccount("gamingfever", "12345678", "admin")));
            st.executeUpdate("insert into room values (101,60),(102,60),(103,60),(104,45)");
            st.executeUpdate("insert into class values ('C00001','6',101),('C00002','7',102),('C00003','8',103)");
            
   Student student=Student.createStudent("S00001","dev","devgarg","devgarg@gmail.com",
           "9988765432","model town@delhi@delhi@india@110023","model town@delhi@delhi@india@110023",1,
           new Date(2000,02,03),new Date(2020,02,17),'M',"B+",null,"C00001");
   ParentDetails parent=ParentDetails.createParentDetails("P000000001","raj", "raj@gmail.com","9915123456",new Date(1996,02,03),"rani",
           "rani@gmail.com", "9988776655",new Date(1968,02,03), "2000000-50000000",student);
   student.setParentId(parent.getParentId());
   Account account=Account.createAccount(student.getUsername(),"123456","student");
   StudentRegBackend.addStudentToRecords(student, parent,account,6969);
   
   student=Student.createStudent("S00002","devg","devgar","devgarg771@gmail.com",
           "998876342","vijay vihar@delhi@@@","vijay vihar @delhi@@@",2,
           new Date(2002,12,03),new Date(2020,02,16),'M',"B-",null,"C00001");
   parent=ParentDetails.createParentDetails("P000000002","raja", "raja@gmail.com","9915123111",new Date(1976,10,03),"rama",
           "rama@gmail.com", "9982376655",new Date(1960,02,03), "1000000-20000000",student);
   student.setParentId(parent.getParentId());
   account=Account.createAccount(student.getUsername(),"123456","student");
   StudentRegBackend.addStudentToRecords(student, parent,account,6969);
   
   student=Student.createStudent("S00003","devga","devgarg1","devgarg770@gmail.com",
           "9988465482","vijay@delhi@@@","Rohini@delhi@@@",3,
           new Date(1999,12,03),new Date(2019,02,16),'F',"B-",null,"C00001");
   parent=ParentDetails.createParentDetails("P000000003","raju", "raju@gmail.com","9915123942",new Date(1970,10,03),"LOL",
           "LOL@gmail.com", "9982323655",new Date(1960,02,13), "1000000-20000000",student);
   student.setParentId(parent.getParentId());
   account=Account.createAccount(student.getUsername(),"123456","student");
   StudentRegBackend.addStudentToRecords(student, parent,account,6969);
   
   student=Student.createStudent("S00004","prateek","par","par112@gmail.com",
           "9988723344","vijay@delhi@@@","vijay@delhi@@@",4,
           new Date(2012,12,03),new Date(2019,02,16),'F',"B-",null,"C00001");
   parent=ParentDetails.createParentDetails("P000000004","raja", "raja1@gmail.com","9915123110",new Date(1991,10,03),"LOLI",
           "LOLI@gmail.com", "9982376565",new Date(1961,12,23), "1000000-20000000",student);
   student.setParentId(parent.getParentId());
   account=Account.createAccount(student.getUsername(),"123456","student");
   StudentRegBackend.addStudentToRecords(student, parent,account,6969);
  
   student=Student.createStudent("S00005","dev1","devgarg3","devgarg775@gmail.com",
           "998876342","abcd@delhi@@@","abcd@delhi@@@",5,
           new Date(2000,02,26),new Date(2020,12,06),'M',"B-",null,"C00001");
   parent=ParentDetails.createParentDetails("P000000005","ramu", "ramu@gmail.com","9915121111",new Date(1976,10,03),"rama",
           "rama@gmail.com", "9982376655",new Date(1960,02,03), "1000000-20000000",student);
   student.setParentId(parent.getParentId());
   account=Account.createAccount(student.getUsername(),"123456","student");
   StudentRegBackend.addStudentToRecords(student, parent,account,6969);
  
   
   /* 
            Faculty
            'FACULTY ID` VARCHAR(10) NOT NULL,
            `NAME` VARCHAR(20) NOT NULL,
            `EMAIL` VARCHAR(20) NOT NULL,
            `USERNAME` VARCHAR(20) NOT NULL,
            `PERMANENT ADDRESS` VARCHAR(60) NOT NULL,
            `CORR. ADDRESS` VARCHAR(60) NOT NULL,
            `DATE OF BIRTH` DATE NOT NULL,
            `DATE OF REGISTRATION` DATE NOT NULL,
            `MOBILE NUMBER` VARCHAR(10) NOT NULL,`
            `GENDER` CHAR(1) NOT NULL,
            `BLOOD GROUP` VARCHAR(3) NOT NULL,
            `EXPERENCE` VARCHAR(50) NOT NULL,
            `SUBJECT SPECIALITY` VARCHAR(10) NOT NULL,
            PRIMARY KEY (`FACULTY ID` 
   
            Qualifications
              FACULTY ID` VARCHAR(10) NOT NULL,
               `DEGREE` VARCHAR(10) NOT NULL,
               `YEAR` CHAR(4) NOT NULL,
               `INSTITUTE` VARCHAR(20)NOT NULL,
               `PERCENTAGE` DECIMAL(5,2)
   */
            Qualification quali[] = {Qualification.createQualification("MBA","2000","Harvard", 90)};
            Faculty faculty = Faculty.createFaculty("F00001","Bhawana","Bhawana123@gmail.com","MAT","Rohini @Delhi@@@","Rohini @Delhi@@@",
                    new Date(1989,02,13),new Date(2015,12,20),"8288219230",'F',"AB+","5 Years in Harvard University","G.K.",quali);
             account=Account.createAccount(faculty.getUsername(),"123456789","faculty");
             FacultyRegBackend.addFacultyToRecords(faculty,account);
            quali = new Qualification[]{Qualification.createQualification("BBA","2001","Harvard", 90)};
            faculty = Faculty.createFaculty("F00002","Ritu","Ritu123@gmail.com","PUB","Rohini @Delhi@@@","Rohini @Delhi@@@",
                    new Date(1990,12,11),new Date(2016,02,20),"8288211230",'F',"B+","10 Years in Xian University","Computer",quali);
             account=Account.createAccount(faculty.getUsername(),"123456789","faculty");
             FacultyRegBackend.addFacultyToRecords(faculty,account);
            quali = new Qualification[]{Qualification.createQualification("BCA","2012","Harvard", 90)};
            faculty = Faculty.createFaculty("F00003","Shruti","Shruti123@gmail.com","PBKB","Rohini @Delhi@@@","Rohini @Delhi@@@",
                    new Date(1919,02,13),new Date(2010,12,20),"8288213000",'F',"AB+","2 Years in Harvard University","Maths",quali);
             account=Account.createAccount(faculty.getUsername(),"123456789","faculty");
             FacultyRegBackend.addFacultyToRecords(faculty,account);
            quali = new Qualification[]{Qualification.createQualification("PHD","2000","Harvard", 90)};
            faculty = Faculty.createFaculty("F00004","Bhawana","Bhawana123@gmail.com","MATAT","Rohini @Delhi@@@","Rohini @Delhi@@@",
                    new Date(1989,02,13),new Date(2015,12,20),"8288219230",'F',"AB+","5 Years in Harvard University","Punjabi",quali);
             account=Account.createAccount(faculty.getUsername(),"123456789","faculty");
             FacultyRegBackend.addFacultyToRecords(faculty,account);
            quali = new Qualification[]{Qualification.createQualification("MBA","2000","Harvard", 90)};
            faculty = Faculty.createFaculty("F00005","Roteek","Roteek123@gmail.com","PUBG","Rohini @Delhi@@@","Rohini @Delhi@@@",
                    new Date(1989,12,13),new Date(2015,12,20),"8288219230",'F',"AB+","5 Years in Harvard University","Hindi",quali);
             account=Account.createAccount(faculty.getUsername(),"123456789","faculty");
             FacultyRegBackend.addFacultyToRecords(faculty,account);
            quali= new Qualification[]{Qualification.createQualification("BBA LLB","2000","Harvard", 90)};
            faculty = Faculty.createFaculty("F00006","Bhavesh","Bhavesh123@gmail.com","POLI","Rohini @Delhi@@@","Rohini @Delhi@@@",
                    new Date(1989,02,13),new Date(2015,12,3),"8288219230",'M',"A+","5 Years in Harvard University","SOCIAL SCIENCE",quali);
             account=Account.createAccount(faculty.getUsername(),"123456789","faculty");
             FacultyRegBackend.addFacultyToRecords(faculty,account);
            quali = new Qualification[]{Qualification.createQualification("MBA","2000","Harvard", 90)};
            faculty = Faculty.createFaculty("F00007","Dheeraj","Dheeraj123@gmail.com","MAM","Rohini@Delhi@@@","Rohini@Delhi@@@",
                    new Date(1989,02,13),new Date(2015,12,20),"8288219230",'F',"A-","5 Years in Harvard University","English",quali);
             account=Account.createAccount(faculty.getUsername(),"123456789","faculty");
             FacultyRegBackend.addFacultyToRecords(faculty,account);
            quali= new Qualification[]{Qualification.createQualification("MBA","2000","Harvard", 90)};
            faculty = Faculty.createFaculty("F00008","Prateek","Prteek123@gmail.com","PUT","Rohini@Delhi@@@","Rohini@Delhi@@@",
                    new Date(1989,02,13),new Date(2015,12,20),"8288219230",'M',"AB+","5 Years in Harvard University","Science",quali);
             account=Account.createAccount(faculty.getUsername(),"123456789","faculty");
             FacultyRegBackend.addFacultyToRecords(faculty,account);
            /*
                Class 
                `ID` VARCHAR(10) NOT NULL,
                `NAME` VARCHAR(20) NOT NULL,
             */
            /*
                `ID` VARCHAR(10) NOT NULL,
    `FROM` TIME NOT NULL,
    `TO` TIME NOT NULL,
            */
            st.executeUpdate("insert into `time slot` values ('0900_1000','0900','1000'),"
                    + "('1000_1100','1000','1100'),('1100_1200','1100','1200'),"
                    + "('1200_1300','1200','1300'),('1300_1400','1300','1400'),"
                    + "('1400_1500','1400','1500')");
            
            /*  TABLE `DAY`
              `DAY ID` CHAR(2) NOT NULL,
    `DAY NAME` INTEGER NOT NULL,
    `DAY NUMBER` INTEGER(1) NOT NULL,
  
            */
            st.executeUpdate("insert into day values ('MO','MONDAY',1),"
                    + "('TU','TUESDAY',2),('WE','WEDNESDAY',3),('TH','THUSDAY',4),"
                    + "('FR','FRIDAY',5),('SA','SATURDAY',6)");
            
            /*
            
CREATE TABLE `subject` (
    `SUBJECT ID` VARCHAR(10) NOT NULL,
    `SUBJECT NAME` VARCHAR(10) NOT NULL,
    `LECTURES REQUIRED` INTEGER,
    PRIMARY KEY (`SUBJECT ID`)
);
            */
            st.executeUpdate("insert into subject values('ENG_06','ENGLISH',4)");
            st.executeUpdate("insert into subject values('HIN_06','HINDI',4)");
            st.executeUpdate("insert into subject values('MAT_06','MATHS',4)");
            st.executeUpdate("insert into subject values('SST_06','SOCIAL SCIENCE',4)");
            st.executeUpdate("insert into subject values('SCI_06','SCIENCE',4)");
            st.executeUpdate("insert into subject values('PUN_06','PUNJABI',2)");
            st.executeUpdate("insert into subject values('COM_06','COMPUTER',2)");
            st.executeUpdate("insert into subject values('G.K_06','G.K.',1)");
            st.executeUpdate("insert into class_subject_faculty(`class id`,`faculty id`,"
                    + "`subject id`,`room id`) values"
                    + "('C00001','F00005','ENG_06',101),('C00001','F00006','HIN_06',101),"
                    + "('C00001','F00004','MAT_06',101),('C00001','F00003','SST_06',101),"
                    + "('C00001','F00002','SCI_06',101),('C00001','F00001','PUN_06',101),"
                    + "('C00001','F00007','COM_06',101),('C00001','F00008','G.K_06',101)");
            st.execute("commit;"); 
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
