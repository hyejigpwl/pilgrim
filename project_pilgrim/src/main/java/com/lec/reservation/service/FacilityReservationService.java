package com.lec.reservation.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.lec.db.JDBCUtility;
import com.lec.reservation.dao.ReservationDAO;
import com.lec.reservation.vo.FacilityReservationVO;

public class FacilityReservationService {
	private FacilityReservationService() {}
	private static FacilityReservationService facilityReservationService = null;
	public static FacilityReservationService getInstance() {
		if(facilityReservationService == null) facilityReservationService = new FacilityReservationService();
		return facilityReservationService;
	}
	
	
	public boolean reserveFacility (FacilityReservationVO facility) {
		
		boolean isWriteSuccess = false;
        Connection conn = null;

        try {
            conn = JDBCUtility.getConnection();
            ReservationDAO reservationDAO = ReservationDAO.getInstance();
            reservationDAO.setConnection(conn);
            
            

            int insertCount = reservationDAO.reserveFacility(facility);
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
	
	
	
	
	public List<FacilityReservationVO> getReservationsForMember(String member_id) {
	    List<FacilityReservationVO> facilityReservationList = new ArrayList<>();
	    try (
	    	Connection conn = JDBCUtility.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM facility_reservation WHERE member_id = ?")) {
	        
	        pstmt.setString(1, member_id);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	            	FacilityReservationVO reservation = new FacilityReservationVO();
	                reservation.setReservation_id(rs.getInt("reservation_id"));
	                reservation.setMember_id(rs.getString("member_id"));
	                reservation.setFacility_type(rs.getString("facility_type"));
	                reservation.setCheckin_date(rs.getDate("checkin_date"));
	                reservation.setCheckout_date(rs.getDate("checkout_date"));
	                reservation.setReg_date(rs.getDate("reg_date"));
	                facilityReservationList.add(reservation);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return facilityReservationList;
	}

}
