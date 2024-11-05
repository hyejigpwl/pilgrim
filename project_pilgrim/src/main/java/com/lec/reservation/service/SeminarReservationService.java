package com.lec.reservation.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.lec.db.JDBCUtility;
import com.lec.reservation.dao.ReservationDAO;
import com.lec.reservation.vo.SeminarReservationVO;

public class SeminarReservationService {
	private SeminarReservationService() {}
	private static SeminarReservationService seminarReservationService = null;
	public static SeminarReservationService getInstance() {
		if(seminarReservationService == null) seminarReservationService = new SeminarReservationService();
		return seminarReservationService;
	}
	
	
	public boolean reserveSeminar (SeminarReservationVO seminar) {
		
		boolean isWriteSuccess = false;
        Connection conn = null;

        try {
            conn = JDBCUtility.getConnection();
            ReservationDAO reservationDAO = ReservationDAO.getInstance();
            reservationDAO.setConnection(conn);
            
            

            int insertCount = reservationDAO.reserveSeminar(seminar);
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
	
	
	
	
	public List<SeminarReservationVO> getReservationsForMember(String member_id) {
	    List<SeminarReservationVO> seminarReservationList = new ArrayList<>();
	    try (
	    	Connection conn = JDBCUtility.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM seminar_reservation WHERE member_id = ?")) {
	        
	        pstmt.setString(1, member_id);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	            	SeminarReservationVO seminarReservation = new SeminarReservationVO();
	            	seminarReservation.setReservation_id(rs.getInt("reservation_id"));
	            	seminarReservation.setMember_id(rs.getString("member_id"));
	            	seminarReservation.setSeminar_type(rs.getString("seminar_type"));
	            	seminarReservation.setGuest_count(rs.getInt("guest_count"));
	            	seminarReservation.setSeminar_date(rs.getDate("seminar_date"));
	            	seminarReservation.setReg_date(rs.getDate("reg_date"));
	                seminarReservationList.add(seminarReservation);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return seminarReservationList;
	}

}


