<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Map"%>
<%@page import="com.lec.reservation.dao.ReservationDAO"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page trimDirectiveWhitespaces="true" %>
 
 <%

 
	request.setCharacterEncoding("utf-8");

	//날짜를 yyyy-MM-dd 형식으로 포맷팅하기 위한 SimpleDateFormat 설정
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	Calendar cal = Calendar.getInstance();
	
	// 시스템 오늘날짜
	int ty = cal.get(Calendar.YEAR);
	int tm = cal.get(Calendar.MONTH)+1;
	int td = cal.get(Calendar.DATE);
	
	int year = cal.get(Calendar.YEAR);
	int month = cal.get(Calendar.MONTH)+1;
	
	// 파라미터 받기
	String sy = request.getParameter("year");
	String sm = request.getParameter("month");
	
	if(sy!=null) { // 넘어온 파라미터가 있으면
		year = Integer.parseInt(sy);
	}
	if(sm!=null) {
		month = Integer.parseInt(sm);
	}
	
	cal.set(year, month-1, 1);
	year = cal.get(Calendar.YEAR);
	month = cal.get(Calendar.MONTH)+1;
	
	int week = cal.get(Calendar.DAY_OF_WEEK); // 1(일)~7(토)
	int lastDay = cal.getActualMaximum(Calendar.DATE);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="robots" content="index, follow, max-image-preview:large, max-snippet:-1, max-video-preview:-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css" />
<link rel="stylesheet" href="assets/css/main.css">
<link rel="stylesheet" href="assets/css/sub.css">
<!-- Scripts -->
<script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
<script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>
<script src="assets/js/jquery.min.js"></script><script src="assets/js/skel.min.js"></script><script src="assets/js/util.js"></script>
<script src="assets/js/header.js"></script>

<title>예약신청</title>
</head>
<%@include file="header.jsp"%>
<div id="container" class="calendar container">
	<ul class="actions two_btn">
		<li><a href="seminar_rqt.jsp" class="button special big">천로역정 세미나 예약</a></li>
		<li><a href="room_rqt.jsp" class="button big">객실 예약</a></li>
		<li><a href="facility_rqt.jsp" class="button big">시설 예약</a></li>
	</ul>

	<div class="title">
		<a href="rvn_rqt.jsp?year=<%=year%>&month=<%=month-1%>">&lt;</a>
		<label><%=year%>년 <%=month%>월</label>
		<a href="rvn_rqt.jsp?year=<%=year%>&month=<%=month+1%>">&gt;</a>
		
	</div>
	
	<table>
		<thead>
			<tr>
				<td>일</td>
				<td>월</td>
				<td>화</td>
				<td>수</td>
				<td>목</td>
				<td>금</td>
				<td>토</td>
			</tr>
		</thead>
		<tbody>
<tr>
				<%
					String room_txt = "객실";
					String seminarA_txt = "천로역정A";
					String seminarB_txt = "천로역정B";
					String seminarC_txt = "천로역정C";
					int psb_num_A = 1;
					int psb_num_B = 1;
					int psb_num_C = 1;
					String psb_txt = "명 가능";
					String psb_arr = "예약 가능";
					String no_txt = "예약 마감";
					String facility_txt = "시설";
					
					ReservationDAO reservationDAO = ReservationDAO.getInstance();
					
					// 미리 예약 가능 데이터 가져오기
					Map<String, Integer> availableRoomsMap = reservationDAO.getAvailableRoomsByMonth(year, month);
					Map<String, Integer> availableFacilitiesMap = reservationDAO.getAvailableFacilitiesByMonth(year, month);
				
					// 공백 채우기
					for (int i = 1; i < week; i++) {
						out.print("<td class='gray'></td>");
					}
					
				
					
					// 1일부터 말일까지 출력
					for (int day = 1; day <= lastDay; day++) {
						
						// 오늘 날짜인 경우 'today' 클래스 추가
						String todayClass = (year == ty && month == tm && day == td) ? "today" : "";
						String date = sdf.format(new GregorianCalendar(year, month - 1, day).getTime());
						// System.out.println(date);
						
						// 오늘 이전 날짜는 예약 불가 표시
						if (year < ty || (year == ty && month < tm) || (year == ty && month == tm && day < td)) {
							out.print("<td class='gray' data-date='" + year + "-" + month + "-" + day + "'>" + day + "<span class='reservation'>예약불가</span></td>");
						} else {
							 out.print("<td class='" + todayClass + "' data-date='" + year + "-" + month + "-" + day + "'>" + day);
							
							// <a> 태그로 텍스트 추가 (4가지 종류)
							// 방이 있는 경우와 없는 경우의 텍스트 설정
					        // 특정 날짜의 총 예약 가능 방 개수를 가져옴
					        
            int availableRooms = availableRoomsMap.getOrDefault(date, 0);
        boolean isRoomAvailable = availableRooms > 0;
            System.out.println(availableRooms);
             // System.out.println(availableRoomsMap);
            //boolean isRoomAvailable = true;
					        if (isRoomAvailable) {
					        	out.print("<a href='#none' class='a_room room_yes'>" + room_txt+ " " + psb_arr + "</a>");
					        } else {
					        	 out.print("<a href='#none' class='a_room a_no'>" + room_txt+ " " + no_txt+ "</a>");
					        }
					        
					        // 세미나 가능한 경우와 없는 경우의 텍스트 설정
					        boolean isAAvailable = true;
					        boolean isBAvailable = false;
					        boolean isCAvailable = false;
					        
					        if(isAAvailable){
					        	out.print("<a href='seminar_rqt.jsp' class='a_seminar'>" + seminarA_txt + " " + psb_num_A + " " + psb_txt + "</a>");
					        }else{
					        	out.print("<a href='#none' class='a_seminar a_no'>" + seminarA_txt + " " + no_txt + "</a>");
					        }
					        
					        if(isBAvailable){
					        	out.print("<a href='seminar_rqt.jsp' class='a_seminar'>" + seminarB_txt + " " + psb_num_B + " " + psb_txt + "</a>");
					        }else{
					        	out.print("<a href='#none' class='a_seminar a_no'>" + seminarB_txt + " " + no_txt + "</a>");
					        }
					        
					        if(isCAvailable){
					        	out.print("<a href='seminar_rqt.jsp' class='a_seminar'>" + seminarC_txt + " " + psb_num_C + " " + psb_txt + "</a>");
					        }else{
					        	out.print("<a href='#none' class='a_seminar a_no'>" + seminarC_txt + " " + no_txt + "</a>");
					        }
							
							
							// 시설이 있는 경우와 없는 경우의 텍스트 설정
					        int availableFacilities = availableFacilitiesMap.getOrDefault(date, 0);
					        System.out.println("Date: " + date + ", Retrieved Facilities: " + availableFacilities);
							
							// System.out.println("Map values:");
/* for (Map.Entry<String, Integer> entry : availableFacilitiesMap.entrySet()) {
    System.out.println("Date: " + entry.getKey() + ", Facilities: " + entry.getValue());
} */


        boolean isFacilityAvailable = availableFacilities > 0;
            // boolean isFacilityAvailable = true;
					        if (isFacilityAvailable) {
					        	out.print("<a href='#none' class='a_facility facility_yes'>" + facility_txt+ " " + psb_arr + "</a>");
					        } else {
					        	 out.print("<a href='#none' class='a_facility a_no'>" + facility_txt+ " " + no_txt+ "</a>");
					        }

							
							out.print("</td>");
						}

						// 주간 순환으로 줄바꿈 처리
						if(lastDay != day && (++week)%7 == 1) {
							out.print("</tr><tr>");
						}
					}

					// 마지막 주 마지막 일자 다음 처리
					int n = 1;
					for(int i = (week-1)%7; i<6; i++) {
						// out.print("<td>&nbsp;</td>");
						out.print("<td class='gray'>"+(n++)+"</td>");
					}
				%>
			</tr>
		</tbody>
	</table>
	
	<!-- 모달창 -->
	<div id="modalContainer_room" class="hidden modalContainer">
	  <div class="modalContent">
	    <p>예약가능객실</p>

<ul>

</ul>

	    <button class="modalCloseButton">닫기</button>
	  </div>
	</div>
	
	
	
	<div id="modalContainer_facility" class="hidden modalContainer">
	  <div class="modalContent">
	    <p>예약가능시설</p>
	    
<ul>

</ul>
	    <div class="btn_wrap">
		    <button class="modalCloseButton">닫기</button>
	    </div>
	  </div>
	</div>
	
</div>
<%@include file="footer.jsp"%>
<script>
$(function(){
    // 객실 예약 가능 정보를 가져오는 함수
    function fetchAvailableRooms(date) {
        $.ajax({
            url: "availableRooms.do",
            method: "GET",
            data: { date: date },
            dataType: "json",
            success: function(data) {
            	
            	 let roomList = '';
            	 data.forEach(room => {
            	     const roomType = room.room_type;
            	     const roomClass = room.available_rooms > 0 ? "blue" : "red";
            	     const roomState = room.available_rooms > 0 ? room.available_rooms+ "실 남음" : "예약마감";
            	     roomList += "<li><a href='room_rqt.jsp'>"+roomType + "- <span class=" + roomClass +">" + roomState + "</span></a></li>";
            	 });
            	 $("#modalContainer_room .modalContent ul").html(roomList);
                $("#modalContainer_room").removeClass("hidden");

            },
            error: function() {
                alert("객실 정보를 불러오는 데 실패했습니다.");
            }
        });
    }

    // 시설 예약 가능 정보를 가져오는 함수
    function fetchAvailableFacilities(date) {
        $.ajax({
            url: "availableFacilities.do",
            method: "GET",
            data: { date: date },
            dataType: "json",
            success: function(data) {
                let facilityList = '';
                data.forEach(facility => {
                    let facilityClass = facility.available_facilities > 0 ? "blue" : "red";
                    let facilityState = facility.available_facilities > 0 ? facility.available_facilities + "실 남음" : "예약마감";
                    facilityList += "<li><a href='facility_rqt.jsp'>"+facility.facility_type+" - <span class=" + facilityClass + ">" +facilityState + "</span></a></li>";
                });
                $("#modalContainer_facility .modalContent ul").html(facilityList);
                $("#modalContainer_facility").removeClass("hidden");
            },
            error: function() {
                alert("시설 정보를 불러오는 데 실패했습니다.");
            }
        });
    }

    // 객실 예약 가능 클릭 시
    $(".room_yes").click(function(event) {
        event.preventDefault(); // 기본 링크 동작 방지
        const selectedDate = $(this).closest("td").data("date");
        if (selectedDate) {
            fetchAvailableRooms(selectedDate);
        } else {
            alert("날짜 정보를 찾을 수 없습니다.");
        }
    });

    // 시설 예약 가능 클릭 시
    $(".facility_yes").click(function(event) {
        event.preventDefault(); // 기본 링크 동작 방지
        const selectedDate = $(this).closest("td").data("date");
        if (selectedDate) {
            fetchAvailableFacilities(selectedDate);
        } else {
            alert("날짜 정보를 찾을 수 없습니다.");
        }
    });

    // 모달 닫기 버튼
    $(".modalCloseButton").click(function(){
        $(".modalContainer").addClass("hidden");
    });
});
</script>