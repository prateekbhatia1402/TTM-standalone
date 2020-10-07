/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ttmp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author prateek
 */
public class RoomManager {
    
    public static ArrayList<Room> getAllRooms(){
        ArrayList<Room> rooms = new ArrayList<>();
        try (Connection con=SqlConnect.getDatabaseConnection()){
            
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select id,`SITTING CAPACITY` , "
                    + "case when "
                    + "id in (select distinct `room id` from class) then '1'"
                    + " else '0' end from room");
            if(rs.last()==false)
                return rooms;
            int count=rs.getRow();
            rs.first();
            for(int i=0;i<count;i++,rs.next()){
                int id=rs.getInt(1);
                int cap=rs.getInt(2);
                boolean assigned = rs.getBoolean(3);
                rooms.add(new Room(id, cap, assigned));
            }
                    
        } catch (SQLException ex) {
            Logger.getLogger(TimeTableControl.class. getName()).log(Level.SEVERE, null, ex);
        }
        return rooms;
    }
    
    public static ArrayList<Room> getAvailableRooms(){
        ArrayList<Room> all_rooms = getAllRooms(), rooms = new ArrayList<>();
        for(Room room : all_rooms)
            if (!room.isAssigned())
                rooms.add(room);
        return rooms;
    }
    public static boolean idExists(int id)
    {
        try(Connection c = SqlConnect.getDatabaseConnection())
        {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("select * from room where id="+id);
            if (rs.next())
                return true;
        } catch (SQLException ex) {
            Logger.getLogger(RoomManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    
    public static int createRoom(Room room){
        if (idExists(room.getId()))
        {
            JOptionPane.showMessageDialog(null, "Duplicate room id");
            return 0;
        }
        try(Connection c = SqlConnect.getDatabaseConnection())
        {
            Statement s = c.createStatement();
            String q = String.format("insert into room values(%d,%d)",room.getId(),
            room.getSittingCapacity());
            System.out.println(q);
            return s.executeUpdate(q);
        } catch (SQLException ex) {
            Logger.getLogger(RoomManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public static Room getRoom(int roomId)
    {
        Room room = null;
      try (Connection con=SqlConnect.getDatabaseConnection()){
            
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select id,`SITTING CAPACITY` "
                    + " from room where id = "+roomId);
            if(rs.last() == false)
                return room;
            rs.first();
                int id=rs.getInt(1);
                int cap=rs.getInt(2);
                room = new Room(id, cap);
                    
        } catch (SQLException ex) {
            Logger.getLogger(TimeTableControl.class. getName()).log(Level.SEVERE, null, ex);
        }
        return room;
    }
    public static int updateRoom(int oldId, Room room){
        try(Connection c = SqlConnect.getDatabaseConnection())
        {
            String q = oldId == room.getId() ?
                    String.format("update room set `SITTING CAPACITY`=%d where id = %d",
                    room.getSittingCapacity(),room.getId())
                    :
                    String.format("update room set `SITTING CAPACITY`=%d "
                            + ", ID = %d where id = %d",
                    room.getSittingCapacity(),room.getId(), oldId);
            System.out.println(q);
            Statement s = c.createStatement();
            return  s.executeUpdate(q);
        } catch (SQLException ex) {
            Logger.getLogger(RoomManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    public static int deleteRoom(Room room){
        try(Connection c = SqlConnect.getDatabaseConnection())
        {
            String q = String.format("delete from room where id = %d",room.getId());
            Statement s = c.createStatement();
            System.out.println(q);
            return s.executeUpdate(q);
        } catch (SQLException ex) {
            Logger.getLogger(RoomManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
