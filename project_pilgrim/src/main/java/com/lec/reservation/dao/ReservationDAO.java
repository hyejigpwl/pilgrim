package com.lec.reservation.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.lec.db.JDBCUtility;
import com.lec.reservation.vo.FacilityReservationVO;
import com.lec.reservation.vo.RoomReservationVO;
import com.lec.reservation.vo.SeminarReservationVO;

public class ReservationDAO {

    // Singleton
    private static ReservationDAO reservationDAO;

    private ReservationDAO() {}

    public static ReservationDAO getInstance() {
        if (reservationDAO == null)
        	reservationDAO = new ReservationDAO();
        return reservationDAO;
    }
    
    Connection conn = null;
	DataSource ds = null;

	// DB Connection
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

    
	// 1. 객실예약
 	public int reserveRoom(RoomReservationVO room) {
 		PreparedStatement pstmt = null;
 		ResultSet rs = null;
 		String sql = "insert into room_reservation(reservation_id, member_id, guest_count, checkin_date, checkout_date, room_type, reg_date) "
 				+ " values(?,?,?,?,?,?,now())";
 		int insertCount = 0;
 		int reservation_id=0;

 		try {
 			pstmt = conn.prepareStatement("select max(reservation_id) from room_reservation");
			rs = pstmt.executeQuery();
			if (rs.next())
				reservation_id = rs.getInt(1) + 1;
			
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt(1, reservation_id);
            pstmt.setString(2, room.getMember_id());
 			pstmt.setInt(3, room.getGuest_count());
 			pstmt.setDate(4, new java.sql.Date(room.getCheckin_date().getTime()));
 			pstmt.setDate(5, new java.sql.Date(room.getCheckout_date().getTime()));
 			pstmt.setString(6, room.getRoom_type());
 			insertCount = pstmt.executeUpdate();
 		
 			
 		} catch (Exception e) {
 			System.out.println("객실예약실패!!!");
 			insertCount=0;
 		} finally {
 			JDBCUtility.close(null, pstmt, null);
 		}
 		return insertCount;
 	}
 	
 // 2. 시설예약
  	public int reserveFacility(FacilityReservationVO facility) {
  		PreparedStatement pstmt = null;
  		ResultSet rs = null;
  		String sql = "insert into facility_reservation(reservation_id, member_id, facility_type, checkin_date, checkout_date, reg_date) "
  				+ " values(?,?,?,?,?,now())";
  		int insertCount = 0;
  		int reservation_id=0;

  		try {
  			pstmt = conn.prepareStatement("select max(reservation_id) from facility_reservation");
 			rs = pstmt.executeQuery();
 			if (rs.next())
 				reservation_id = rs.getInt(1) + 1;
 			
             pstmt = conn.prepareStatement(sql);
             
             pstmt.setInt(1, reservation_id);
             pstmt.setString(2, facility.getMember_id());
  			pstmt.setString(3, facility.getFacility_type());
  			pstmt.setDate(4, new java.sql.Date(facility.getCheckin_date().getTime()));
  			pstmt.setDate(5, new java.sql.Date(facility.getCheckout_date().getTime()));
  			insertCount = pstmt.executeUpdate();
  		
  			
  		} catch (Exception e) {
  			System.out.println("객실예약실패!!!");
  			insertCount=0;
  		} finally {
  			JDBCUtility.close(null, pstmt, null);
  		}
  		return insertCount;
  	}

  	
 // 3. 세미나예약
   	public int reserveSeminar(SeminarReservationVO seminar) {
   		PreparedStatement pstmt = null;
   		ResultSet rs = null;
   		String sql = "insert into seminar_reservation(reservation_id, member_id, seminar_type, guest_count, seminar_date, reg_date) "
   				+ " values(?,?,?,?,?,now())";
   		int insertCount = 0;
   		int reservation_id=0;

   		try {
   			pstmt = conn.prepareStatement("select max(reservation_id) from seminar_reservation");
  			rs = pstmt.executeQuery();
  			if (rs.next())
  				reservation_id = rs.getInt(1) + 1;
  			
              pstmt = conn.prepareStatement(sql);
              
              pstmt.setInt(1, reservation_id);
              pstmt.setString(2, seminar.getMember_id());
   			pstmt.setString(3, seminar.getSeminar_type());
   			pstmt.setInt(4, seminar.getGuest_count());
   			pstmt.setDate(5, new java.sql.Date(seminar.getSeminar_date().getTime()));
   			insertCount = pstmt.executeUpdate();
   		
   			
   		} catch (Exception e) {
   			System.out.println("세미나예약실패!!!");
   			insertCount=0;
   			e.printStackTrace();
   		} finally {
   			JDBCUtility.close(null, pstmt, null);
   		}
   		return insertCount;
   	}
 	
   	// 4. 일별 가능한 숙소 개수 파악
   	public List<Map<String, Object>> getAvailableRoomsByDate(String date) {
   	    PreparedStatement pstmt = null;
   	    ResultSet rs = null;
   	    List<Map<String, Object>> availableRoomsList = new ArrayList<>();

   	    try {
   	        String sql = "SELECT ri.room_type, (ri.total_rooms - COALESCE(COUNT(rr.reservation_id), 0)) AS available_rooms " +
   	                     "FROM room_info ri " +
   	                     "LEFT JOIN room_reservation rr ON ri.room_type = rr.room_type " +
   	                     "AND ? >= rr.checkin_date AND ? < rr.checkout_date " +
   	                     "GROUP BY ri.room_type";
   	        Connection conn = JDBCUtility.getConnection();
   	        pstmt = conn.prepareStatement(sql);
   	        pstmt.setString(1, date);
   	        pstmt.setString(2, date);
   	        rs = pstmt.executeQuery();

   	        while (rs.next()) {
   	            Map<String, Object> roomData = new HashMap<>();
   	            roomData.put("room_type", rs.getString("room_type"));
   	            roomData.put("available_rooms", rs.getInt("available_rooms"));
   	            availableRoomsList.add(roomData);
   	        }

   	    } catch (Exception e) {
   	        e.printStackTrace();
   	    } finally {
   	        JDBCUtility.close(null, pstmt, rs);
   	    }

   	    return availableRoomsList;
   	}
   	
  
   	
   	
 // 6. 일별 가능한 시설 개수 파악
   	public List<Map<String, Object>> getAvailableFacilitesByDate(String date) {
   	    PreparedStatement pstmt = null;
   	    ResultSet rs = null;
   	    List<Map<String, Object>> availableFacilitiesList = new ArrayList<>();

   	    try {
   	        String sql = "SELECT ri.facility_type, (ri.total_facilities - COALESCE(COUNT(rr.reservation_id), 0)) AS available_facilities " +
   	                     "FROM facility_info ri " +
   	                     "LEFT JOIN facility_reservation rr ON ri.facility_type = rr.facility_type " +
   	                     "AND ? >= rr.checkin_date AND ? < rr.checkout_date " +
   	                     "GROUP BY ri.facility_type";
   	        Connection conn = JDBCUtility.getConnection();
   	        pstmt = conn.prepareStatement(sql);
   	        pstmt.setString(1, date);
   	        pstmt.setString(2, date);
   	        rs = pstmt.executeQuery();

   	        while (rs.next()) {
   	            Map<String, Object> facilityData = new HashMap<>();
   	            facilityData.put("facility_type", rs.getString("facility_type"));
   	         facilityData.put("available_facilities", rs.getInt("available_facilities"));
   	         availableFacilitiesList.add(facilityData);
   	        }

   	    } catch (Exception e) {
   	        e.printStackTrace();
   	    } finally {
   	        JDBCUtility.close(null, pstmt, rs);
   	    }

   	    return availableFacilitiesList;
   	}

}
