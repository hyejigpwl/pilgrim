package com.lec.reservation.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.lec.db.JDBCUtility;
import com.lec.reservation.dao.ReservationDAO;
import com.lec.reservation.vo.RoomReservationVO;

public class RoomReservationService {
	private RoomReservationService() {}
	private static RoomReservationService roomReservationService = null;
	public static RoomReservationService getInstance() {
		if(roomReservationService == null) roomReservationService = new RoomReservationService();
		return roomReservationService;
	}
	
	
	public boolean reserveRoom (RoomReservationVO room) {
		
		boolean isWriteSuccess = false;
        Connection conn = null;

        try {
            conn = JDBCUtility.getConnection();
            ReservationDAO reservationDAO = ReservationDAO.getInstance();
            reservationDAO.setConnection(conn);
            
            

            int insertCount = reservationDAO.reserveRoom(room);
            if (insertCount > 0) {
                JDBCUtility.commit(conn);
                isWriteSuccess = true;
            } else {
                JDBCUtility.rollback(conn);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JDBCUtility.rollback(conn); // 예외 발생 시 rollback
        } finally {
            JDBCUtility.close(conn, null, null); // 항상 close
        }
        return isWriteSuccess;
	}
	
	
	
	
	public List<RoomReservationVO> getReservationsForMember(String member_id) {
	    List<RoomReservationVO> roomReservationList = new ArrayList<>();
	    try (
	    	Connection conn = JDBCUtility.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM room_reservation WHERE member_id = ?")) {
	        
	        pstmt.setString(1, member_id);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                RoomReservationVO reservation = new RoomReservationVO();
	                reservation.setReservation_id(rs.getInt("reservation_id"));
	                reservation.setMember_id(rs.getString("member_id"));
	                reservation.setGuest_count(rs.getInt("guest_count"));
	                reservation.setCheckin_date(rs.getDate("checkin_date"));
	                reservation.setCheckout_date(rs.getDate("checkout_date"));
	                reservation.setRoom_type(rs.getString("room_type"));
	                reservation.setReg_date(rs.getDate("reg_date"));
	                roomReservationList.add(reservation);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return roomReservationList;
	}

}


