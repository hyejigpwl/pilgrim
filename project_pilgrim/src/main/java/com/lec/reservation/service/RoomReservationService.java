package com.lec.reservation.service;

import java.sql.Connection;

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
}
