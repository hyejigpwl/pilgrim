package com.lec.reservation.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

import com.lec.common.Action;
import com.lec.common.ActionForward;
import com.lec.reservation.dao.ReservationDAO;

public class AvailableRoomDatesAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest req, HttpServletResponse res) {
        // 클라이언트에서 room_type 파라미터 가져오기
        String roomType = req.getParameter("room_type");

        // 데이터베이스에서 해당 시설의 예약 가능한 날짜 조회
        ReservationDAO dao = ReservationDAO.getInstance();
        List<String> availableDates = dao.getAvailableDatesByRoom(roomType);

        // JSON 형식으로 변환
        JSONArray jsonArray = new JSONArray();
        for (String date : availableDates) {
            jsonArray.add(date);
        }

        // JSON 응답 설정
        res.setContentType("application/json; charset=utf-8");
        try (PrintWriter out = res.getWriter()) {
            out.print(jsonArray.toString());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 포워딩 필요 없음
        return null;
    }
}
