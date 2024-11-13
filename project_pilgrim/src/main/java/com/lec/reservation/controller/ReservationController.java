package com.lec.reservation.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.common.Action;
import com.lec.common.ActionForward;
import com.lec.reservation.action.AvailableFacilitiesAction;
import com.lec.reservation.action.AvailableFacilityDatesAction;
import com.lec.reservation.action.AvailableRoomDatesAction;
import com.lec.reservation.action.AvailableRoomsAction;
import com.lec.reservation.action.AvailableSeminarDatesAction;
import com.lec.reservation.action.FacilityAction;
import com.lec.reservation.action.RoomAction;
import com.lec.reservation.action.SeminarAction;

@WebServlet("*.do")
public class ReservationController extends HttpServlet {

	Action action = null;
	ActionForward forward = null;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		process(req, res);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		process(req, res);
	}

	private void process(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
	
		req.setCharacterEncoding("utf-8");
		String requestURI = req.getRequestURI();
		String contextPath = req.getContextPath(); 
		String command = requestURI.substring(contextPath.length()+1, requestURI.length()-3); // *.do
		
		RequestDispatcher dispatcher = null;

		// 객실예약, 시설예약, 세미나 예약
		if(command.equalsIgnoreCase("roomForm")) {
			action = new RoomAction();
			forward = action.execute(req, res); 
		} else if(command.equalsIgnoreCase("facilityForm")) {
			action = new FacilityAction();
			forward = action.execute(req, res); 
		} else if(command.equalsIgnoreCase("seminarForm")) {
			action = new SeminarAction();
			forward = action.execute(req, res); 
		} else if(command.equalsIgnoreCase("availableRooms")) {
			action = new AvailableRoomsAction();
			forward = action.execute(req, res); 
		} else if(command.equalsIgnoreCase("availableFacilities")) {
			action = new AvailableFacilitiesAction();
			forward = action.execute(req, res); 
		} else if(command.equalsIgnoreCase("availableFacilityDates")) {
			action = new AvailableFacilityDatesAction();
			forward = action.execute(req, res); 
		}  else if(command.equalsIgnoreCase("availableRoomDates")) {
			action = new AvailableRoomDatesAction();
			forward = action.execute(req, res); 
		}  else if(command.equalsIgnoreCase("availableSeminarDates")) {
			action = new AvailableSeminarDatesAction();
			forward = action.execute(req, res); 
		} 
		
		
		if(forward != null) {
			if(forward.isRedirect()) {
				res.sendRedirect(forward.getPath());
			} else {
				dispatcher = req.getRequestDispatcher(forward.getPath());
				dispatcher.forward(req, res);
			}
		}
	}	
}
