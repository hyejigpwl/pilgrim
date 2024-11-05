package com.lec.reservation.action;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lec.common.Action;
import com.lec.common.ActionForward;
import com.lec.reservation.service.SeminarReservationService;
import com.lec.reservation.vo.SeminarReservationVO;

public class SeminarAction implements Action{

	
	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) {
        ActionForward forward = null;
        SeminarReservationVO seminar = new SeminarReservationVO();
        
        // 세미나예약 설정
        try {
        
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        
        HttpSession session = req.getSession();
        String member_id = (String) session.getAttribute("member_id");
        
        // room.setReservation_id(Integer.parseInt(req.getParameter("reservation_id"))); 
        seminar.setMember_id(member_id);           
        seminar.setSeminar_type(req.getParameter("seminar_type"));   
        seminar.setGuest_count(Integer.parseInt(req.getParameter("guest_count")));
        try {
        	seminar.setSeminar_date(format.parse(req.getParameter("seminar_date")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
   
        
        
        
        // 세미나예약 서비스 호출
        SeminarReservationService seminarReservationService =SeminarReservationService.getInstance();
        boolean isWriteSuccess = seminarReservationService.reserveSeminar(seminar);
        
     // 세미나예약 성공 시 마이페이지 리디렉션
        if (isWriteSuccess) {
            forward = new ActionForward();
            forward.setRedirect(true);
            forward.setPath("my_page.jsp");
        } else { // 실패 시 알림 메시지 표시
            res.setContentType("text/html; charset=utf-8");
            PrintWriter out = res.getWriter();
            out.println("<script>");
            out.println("  alert('세미나예약에 실패했습니다!');");
            out.println("  history.back();");
            out.println("</script>");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
        
		return forward;
	}

}

