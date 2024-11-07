<%@page import="com.lec.reservation.vo.SeminarReservationVO"%>
<%@page import="com.lec.reservation.service.SeminarReservationService"%>
<%@page import="com.lec.reservation.vo.FacilityReservationVO"%>
<%@page import="com.lec.reservation.service.FacilityReservationService"%>
<%@page import="com.lec.reservation.service.RoomReservationService"%>
<%@page import="com.lec.reservation.vo.RoomReservationVO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="robots" content="index, follow, max-image-preview:large, max-snippet:-1, max-video-preview:-1">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="assets/css/main.css">
    <link rel="stylesheet" href="assets/css/sub.css">
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/js/skel.min.js"></script>
    <script src="assets/js/util.js"></script>
    <script src="assets/js/header.js"></script>
    <title>나의 예약</title>
</head>
<%@include file="header.jsp"%>

<%
    // 세션에서 로그인된 사용자 정보 가져오기
    String member_id = (String) session.getAttribute("member_id");
    if (member_id == null) {
        // 로그인되지 않은 경우 로그인 페이지로 리디렉션
        response.sendRedirect("login.jsp");
        return;
    }
    
    // RoomReservationService를 통해 예약 정보 가져오기
    RoomReservationService roomReservationService = RoomReservationService.getInstance();
    List<RoomReservationVO> roomReservationList = roomReservationService.getReservationsForMember(member_id);
    
    // FacilityReservationService 통해 예약 정보 가져오기
    SeminarReservationService seminarReservationService = SeminarReservationService.getInstance();
    List<SeminarReservationVO> seminarReservationList = seminarReservationService.getReservationsForMember(member_id);
    
 	// FacilityReservationService 통해 예약 정보 가져오기
    FacilityReservationService facilityReservationService = FacilityReservationService.getInstance();
    List<FacilityReservationVO> facilityReservationList = facilityReservationService.getReservationsForMember(member_id);
%>

<div id="container" class="container my_page">
	<div class="title_wrap">
		
		<h2><%= member_id %>님 환영합니다.</h2>
		
		<div class="actions two_btn">
			 <a href="#none" class="button big special">나의 정보</a>
		    <!-- 로그아웃 버튼 -->
		    <form action="logout.mb" method="post">
		        <input type="submit" value="Logout">
		    </form>
		</div>
	   
	</div>
    
    <div class="reservation_wrap">
    	<h3>나의 객실예약</h3>
	    <p>
	        <%
	            if (roomReservationList != null && !roomReservationList.isEmpty()) {
	                for (RoomReservationVO roomReservation : roomReservationList) {
	        %>
	                    예약 번호: <%= roomReservation.getReservation_id() %><br>
	                    투숙객 수: <%= roomReservation.getGuest_count() %><br>
	                    체크인 날짜: <%= roomReservation.getCheckin_date() %><br>
	                    체크아웃 날짜: <%= roomReservation.getCheckout_date() %><br>
	                    객실 타입: <%= roomReservation.getRoom_type() %><br>
	                    예약 일시 :  <%= roomReservation.getReg_date() %><br>
	                    <a href="#none" class="res_cancel">예약취소</a>
	                    <hr>
	        <%
	                }
	            } else {
	        %>
	                예약 정보가 없습니다.
	                <a href="room_rqt.jsp" class="res_cancel">예약하기</a>
	                <hr>
	        <%
	            }
	        %>
	    </p>
    </div>
     
    
    
    <div class="reservation_wrap">
    	<h3>나의 천로역정 세미나예약</h3>
	    <p>
	        <%
	            if (seminarReservationList != null && !seminarReservationList.isEmpty()) {
	                for (SeminarReservationVO seminarReservation : seminarReservationList) {
	        %>
	                    예약 번호: <%= seminarReservation.getReservation_id() %><br>
	                    세미나 타임: <%= seminarReservation.getSeminar_type() %><br>
	                    신청 인원: <%= seminarReservation.getGuest_count() %><br>
	                    신청 날짜: <%= seminarReservation.getSeminar_date() %><br>
	                    예약 일시 :  <%= seminarReservation.getReg_date() %><br>
	                    <a href="#none" class="res_cancel">예약취소</a>
	                     <hr>
	        <%
	                }
	            } else {
	        %>
	                예약 정보가 없습니다.
	                <a href="seminar_rqt.jsp" class="res_cancel">예약하기</a>
	                <hr>
	        <%
	            }
	        %>
	    </p>
    </div>
   
    
     <div class="reservation_wrap">
	    <h3>나의 시설예약</h3>
	    <p>
	        <%
	            if (facilityReservationList != null && !facilityReservationList.isEmpty()) {
	                for (FacilityReservationVO facilityReservation : facilityReservationList) {
	        %>
	                    예약 번호: <%= facilityReservation.getReservation_id() %><br>
	                    시설 타입: <%= facilityReservation.getFacility_type() %><br>
	                    체크인 날짜: <%= facilityReservation.getCheckin_date() %><br>
	                    체크아웃 날짜: <%= facilityReservation.getCheckout_date() %><br>
	                    예약 일시 :  <%= facilityReservation.getReg_date() %><br>
	                    <a href="#none" class="res_cancel">예약취소</a>
	                    
	                    <hr>
	        <%
	                }
	            } else {
	        %>
	                예약 정보가 없습니다.
	                <a href="facility_rqt.jsp" class="res_cancel">예약하기</a>
	        <%
	            }
	        %>
	    </p>
    </div>
</div>

<%@include file="footer.jsp"%>
</html>
