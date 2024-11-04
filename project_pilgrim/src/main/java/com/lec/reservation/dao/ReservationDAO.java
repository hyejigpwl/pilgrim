package com.lec.reservation.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import com.lec.db.JDBCUtility;
import com.lec.reservation.vo.RoomReservationVO;

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
 		String sql = "insert into room_reservation(reservation_id, member_id, guest_count, checkin_date, checkout_date, room_type) "
 				+ " values(?,?,?,?,?,?)";
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
 			
 			 System.out.println("Check-in Date: " + room.getCheckin_date().getTime());
             System.out.println("Check-out Date: " + room.getCheckout_date().getTime());
 			
 		} catch (Exception e) {
 			System.out.println("회원등록실패!!!");
 			insertCount=0;
 		} finally {
 			JDBCUtility.close(null, pstmt, null);
 		}
 		return insertCount;
 	}

 	
}
