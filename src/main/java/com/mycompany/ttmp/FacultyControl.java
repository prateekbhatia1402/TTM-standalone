package com.mycompany.ttmp;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Programming
 */
public class FacultyControl {
    private static final String TABLE_NAME="`faculty`";
    private static final int QUAL_CODE=1223;
    private static final String[] COLUMN_NAMES_PLN =
    {
    "FACULTY ID",
    "NAME",
    "EMAIL",
    "USERNAME",
    "PERMANENT ADDRESS",
    "CORR. ADDRESS",
    "DATE OF BIRTH",
    "DATE OF REGISTRATION",
    "MOBILE NUMBER",
    "GENDER",
    "BLOOD GROUP",
    "EXPERIENCE",
    "SUBJECT SPECIALITY",
    };
    
    private static final String[] COLUMN_NAMES =
    {
      "`"+COLUMN_NAMES_PLN[0]+"`",
      "`"+COLUMN_NAMES_PLN[1]+"`",
      "`"+COLUMN_NAMES_PLN[2]+"`",
      "`"+COLUMN_NAMES_PLN[3]+"`",
      "`"+COLUMN_NAMES_PLN[4]+"`",
      "`"+COLUMN_NAMES_PLN[5]+"`",
      "`"+COLUMN_NAMES_PLN[6]+"`",
      "`"+COLUMN_NAMES_PLN[7]+"`",
      "`"+COLUMN_NAMES_PLN[8]+"`",
      "`"+COLUMN_NAMES_PLN[9]+"`",
      "`"+COLUMN_NAMES_PLN[10]+"`",
      "`"+COLUMN_NAMES_PLN[11]+"`",
      "`"+COLUMN_NAMES_PLN[12]+"`"          
    };
    private final static String COLUMN_NAMES_STRING = 
            (""+Arrays.toString(COLUMN_NAMES)).substring(1,
                    (""+Arrays.toString(COLUMN_NAMES)).length()-1);
   
    public static String getInsertStatement(int id,Faculty fa){
        if(id==999)
        {  
        return String.format("insert into %s(%s) values"
        + "('%s','%s','%s','%s','%s','%s','%s','%s','%s','%c','%s','%s','%s');"
        ,TABLE_NAME,COLUMN_NAMES_STRING,fa.getFacultyId(),fa.getName(),fa.getEmail(),fa.getUsername(),fa.getPermanentAddress()
        ,fa.getAddress(),fa.getDateOfBirth(),fa.getDateOfRegistration()
        ,fa.getMobileNumber(),fa.getGender(),fa.getBloodGroup(),fa.getExperence(),fa.getSubjectSpeciality())
                +QualificationControl.getInsertStatement(999, fa);
         }
        else return null;
    }
    public static String getUpdateStatement(int id,Faculty fa){
        if(id==999)
        {  
        return String.format("update %s set %s='%s', %s='%s', %s='%s', %s='%s'"
                + ", %s='%s', %s='%s', %s='%s', %s='%s', %s='%c', %s='%s',"
                + " %s='%s', %s='%s' ;"
        ,TABLE_NAME,
        COLUMN_NAMES[1], fa.getName(),
        COLUMN_NAMES[2],fa.getEmail(),
        COLUMN_NAMES[3],fa.getUsername(),
        COLUMN_NAMES[4],fa.getPermanentAddress()
        ,COLUMN_NAMES[5],fa.getAddress(),
        COLUMN_NAMES[6],fa.getDateOfBirth(),
        COLUMN_NAMES[7],fa.getDateOfRegistration()
        ,COLUMN_NAMES[8],fa.getMobileNumber(),
        COLUMN_NAMES[9],fa.getGender(),
        COLUMN_NAMES[10],fa.getBloodGroup(),
        COLUMN_NAMES[11],fa.getExperence(),
        COLUMN_NAMES[12],fa.getSubjectSpeciality())
                +QualificationControl.getInsertStatement(999, fa);
         }
        else return null;
    }
    
    public static String getDeleteStatement(int id, Faculty fa){
        return QualificationControl.getDeleteStatement(id, fa)+
                ";delete from "+TABLE_NAME+" where "+COLUMN_NAMES[0]+" = '"
                +fa.getFacultyId()+"'";
    }
    
    public static String getSelectFullStatement(int id,String facultyId){
        if(id==999)return String.format("Select * from %s where %s='%s';",
                TABLE_NAME,COLUMN_NAMES[0],facultyId);
        return null;
    }
    
    private static String getSelectFullStatement(String facultyId){
        return String.format("Select * from %s where %s='%s';",TABLE_NAME,
                COLUMN_NAMES[0], facultyId);
    }
    private static String getSelectFullStatement(String value,char type){
        String param = type == 'u'?COLUMN_NAMES[3]:COLUMN_NAMES[0];
        return String.format("Select * from %s where %s='%s';",TABLE_NAME,
               param,value);
    }
    private static String getSelectStatement(String facultyId,String columnList[]){
        String columns="";
        for(int i=0;i<columnList.length;i++)
        {
            columns+= columnList[i];
            if(i<columnList.length-1)
                columns+=",";
        }
        return String.format("Select %s from %s where %s='%s';",columns,
                TABLE_NAME,COLUMN_NAMES[0],facultyId);
    }
    
    public static Object[] getSpecificData(String facultyId,String columns[]){
        String query=getSelectStatement(facultyId,columns);
        try (Connection con=SqlConnect.getDatabaseConnection()){
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery(query);
            if(rs.last()==false)
                return null;
            int count=rs.getRow();
            rs.first();
            Object data[]=new Object[count];
            for(int i=0;i<count;i++)
                data[i]=rs.getObject(i+1);
            return data;
        } catch (SQLException ex) {
            Logger.getLogger(FacultyControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    private static Faculty[] doSearch(String query){
        Faculty[] faculties=null;
         try(Connection con=SqlConnect.getDatabaseConnection()){
            Statement st=con.createStatement();
            System.out.println(query);
            ResultSet rs=st.executeQuery(query);
            if(rs.last()==false)
                return null;
            int count=rs.getRow();
            faculties=new Faculty[count];
            rs.first();
            int i=0;
            do{
                faculties[i] =getFaculty(rs);
                i++;
            }
            while(rs.next());
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
        return faculties;
    }
    
    public static Faculty[] search(String param){
        String query=getSelectSpecificRecStat(param);
        return doSearch(query);
    }
    public static Faculty[] search(String param,String column){
        String query=getSelectSpecificRecColumnStat(param,column);
        return doSearch(query);
    }
    public static Faculty[] search(){
        String query=getSelectAllRecordsStatement();
        return doSearch(query);
    }
        private static String getSelectAllRecordsStatement(){
        return String.format("Select * from %s;",TABLE_NAME);
    }
        
    private static String getSelectSpecificRecStat(String param){
        return "Select * from "+TABLE_NAME+" where "
                + COLUMN_NAMES[0]+"  like '%"+param+"%'"
                + " or "+COLUMN_NAMES[1]+" like '%"+param+
                "%' or "+COLUMN_NAMES[2]+" like '%"+param+"%';";
        
    }
    private static String getSelectSpecificRecColumnStat(String param,String column){
        return "Select * from "+TABLE_NAME+
                " where cast("+column+" as char)like '%"+param+"%'";
        
    }
    

    
    public static Faculty getFaculty(String facultyId){
        Faculty faculty=null;
        String query=getSelectFullStatement(facultyId);
        try(Connection con=SqlConnect.getDatabaseConnection()) {
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery(query);
            if(rs.next())
            {
                faculty = getFaculty(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return faculty;
    }    
    public static Faculty getFacultyViaUsername(String  username){
        Faculty faculty=null;
        String query=getSelectFullStatement(username,'u');
        try(Connection con=SqlConnect.getDatabaseConnection()) {
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery(query);
            if(rs.next())
            {
                faculty = getFaculty(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return faculty;
    }
    public static Faculty[] getAllFaculty(){
        Faculty faculties[]=null;
        String query=getSelectAllRecordsStatement();
        try(Connection con=SqlConnect.getDatabaseConnection()) {
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery(query);
            if(rs.last()==false)
                return null;
            int count=rs.getRow();
            rs.first();
            faculties=new Faculty[count];
            for(int i=0;i<count;i++,rs.next()){
                faculties[i] = getFaculty(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return faculties;
    }    
    
    private static Faculty getFaculty(ResultSet rs)
    {
        Faculty faculty = null;
        try{
                String facultyId=rs.getString(COLUMN_NAMES_PLN[0]);
                String name=rs.getString(COLUMN_NAMES_PLN[1]);
                String email=rs.getString(COLUMN_NAMES_PLN[2]);
                String username=rs.getString(COLUMN_NAMES_PLN[3]);
                String mobile = rs.getString(COLUMN_NAMES_PLN[8]);
                String pAddress=rs.getString(COLUMN_NAMES_PLN[4]);
                String cAddress=rs.getString(COLUMN_NAMES_PLN[5]);
                Date dob=new Date(rs.getDate(COLUMN_NAMES_PLN[6]));
                Date dor=new Date(rs.getDate(COLUMN_NAMES_PLN[7]));
                char gender=rs.getString(COLUMN_NAMES_PLN[9]).charAt(0);
                String bloodGroup=rs.getString(COLUMN_NAMES_PLN[10]);
                String experience=rs.getString(COLUMN_NAMES_PLN[11]);
                String subject=rs.getString(COLUMN_NAMES_PLN[12]);
                Qualification[] qualifications=QualificationControl.getQualifications(QUAL_CODE,facultyId);
                faculty =Faculty.createFaculty(facultyId,name,email,username,
                        pAddress,cAddress,dob,dor,mobile,gender,bloodGroup,
                        experience,subject,qualifications);
        }
        catch(SQLException ex)
        {
            Logger.getLogger(StudentControl.class.getName()).log(Level.SEVERE, null, ex);
            
        }
                return faculty;
    }
}
