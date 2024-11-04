package com.lec.reservation.action;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lec.common.Action;
import com.lec.common.ActionForward;
import com.lec.reservation.vo.RoomReservationVO;
import com.lec.reservation.service.RoomReservationService;

public class RoomAction implements Action{

	
	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) {
        ActionForward forward = null;
        RoomReservationVO room = new RoomReservationVO();
        
        // 객실예약 설정
        try {
        
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        
        HttpSession session = req.getSession();
        String member_id = (String) session.getAttribute("member_id");
        
        room.setReservation_id(Integer.parseInt(req.getParameter("reservation_id"))); 
        room.setMember_id(member_id);           
        room.setGuest_count(Integer.parseInt(req.getParameter("guest_count")));          
        try {
        	room.setCheckin_date(format.parse(req.getParameter("checkin_date")));
        	room.setCheckout_date(format.parse(req.getParameter("checkout_date")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        room.setRoom_type(req.getParameter("room_type"));       
        
        
        
        // 객실예약 서비스 호출
        RoomReservationService roomReservationService =RoomReservationService.getInstance();
        boolean isWriteSuccess = roomReservationService.reserveRoom(room);
        
     // 객실예약 성공 시 마이페이지 리디렉션
        if (isWriteSuccess) {
            forward = new ActionForward();
            forward.setRedirect(true);
            forward.setPath("my_page.jsp");
        } else { // 실패 시 알림 메시지 표시
            res.setContentType("text/html; charset=utf-8");
            PrintWriter out = res.getWriter();
            out.println("<script>");
            out.println("  alert('객실예약에 실패했습니다!');");
            out.println("  history.back();");
            out.println("</script>");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
        
		return forward;
	}

}
