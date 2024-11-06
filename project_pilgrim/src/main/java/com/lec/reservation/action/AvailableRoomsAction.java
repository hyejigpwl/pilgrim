package com.lec.reservation.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.lec.common.Action;
import com.lec.common.ActionForward;
import com.lec.reservation.dao.ReservationDAO;

public class AvailableRoomsAction  implements Action {

	@Override
    public ActionForward execute(HttpServletRequest req, HttpServletResponse res){
        String date = req.getParameter("date");

        // 데이터베이스에서 특정 날짜의 예약 가능 방 수 조회
        ReservationDAO dao = ReservationDAO.getInstance();
        List<Map<String, Object>> availableRoomsList = dao.getAvailableRoomsByDate(date);

        // JSON으로 변환
        JSONArray jsonArray = new JSONArray();
        for (Map<String, Object> roomData : availableRoomsList) {
        	JSONObject json = new JSONObject();
            json.put("room_type", roomData.get("room_type"));
            json.put("available_rooms", roomData.get("available_rooms"));
            jsonArray.add(json);
        }

        // JSON 응답 설정
        res.setContentType("application/json; charset=utf-8");
        PrintWriter out;
		try {
			out = res.getWriter();
			out.print(jsonArray.toString());
	        out.flush();
	        out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

        return null; // JSON 응답을 반환했으므로 포워딩 불필요
    }
}
