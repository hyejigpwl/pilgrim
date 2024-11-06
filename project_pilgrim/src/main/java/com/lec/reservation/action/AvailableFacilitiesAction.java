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

public class AvailableFacilitiesAction  implements Action {

	@Override
    public ActionForward execute(HttpServletRequest req, HttpServletResponse res){
        String date = req.getParameter("date");

        // 데이터베이스에서 특정 날짜의 예약 가능 방 수 조회
        ReservationDAO dao = ReservationDAO.getInstance();
        List<Map<String, Object>> availableFacilitiesList = dao.getAvailableFacilitesByDate(date);

        // JSON으로 변환
        JSONArray jsonArray = new JSONArray();
        for (Map<String, Object> facilityData : availableFacilitiesList) {
        	JSONObject json = new JSONObject();
            json.put("facility_type", facilityData.get("facility_type"));
            json.put("available_facilities", facilityData.get("available_facilities"));
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
