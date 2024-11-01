<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page trimDirectiveWhitespaces="true" %>
 
 <%
	request.setCharacterEncoding("utf-8");

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
<div id="container" class="calendar">
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
					
				
				
					// 공백 채우기
					for (int i = 1; i < week; i++) {
						out.print("<td class='gray'></td>");
					}
					
				
					// 1일부터 말일까지 출력
					for (int day = 1; day <= lastDay; day++) {
						
						// 오늘 날짜인 경우 'today' 클래스 추가
						String todayClass = (year == ty && month == tm && day == td) ? "today" : "";
						
						// 오늘 이전 날짜는 예약 불가 표시
						if (year < ty || (year == ty && month < tm) || (year == ty && month == tm && day < td)) {
							out.print("<td class='" + todayClass + "'>" + day + "<span class='reservation'>예약불가</span></td>");
						} else {
							out.print("<td class='" + todayClass + "'>" + day);
							
							// <a> 태그로 텍스트 추가 (4가지 종류)
							// 방이 있는 경우와 없는 경우의 텍스트 설정
					        boolean isRoomAvailable = true;
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
					        boolean isFacilityAvailable = true;
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
	  <%
	  
	   // 방 타입별 남은 수량 설정 (예시 값으로 설정)
	    int avail_num_vip = 1;
	    int avail_num_2b = 0;
	    int avail_num_2f = 0;
	    int avail_num_4b = 3;
	    int avail_num_family = 2;
	    
    // 방 타입 정보와 남은 수량을 이차원 배열로 설정
    String[][] rooms = {
        {"VIP룸", "기준인원2명, 최대인원2명", String.valueOf(avail_num_vip)},
        {"2인침대", "기준인원2명, 최대인원2명", String.valueOf(avail_num_2b)},
        {"2인온돌", "기준인원2명, 최대인원4명", String.valueOf(avail_num_2f)},
        {"4인침대", "기준인원4명, 최대인원6명", String.valueOf(avail_num_4b)},
        {"빌리지가족실", "기준인원4명, 최대인원6명", String.valueOf(avail_num_family)}
    };

    // 출력할 때 사용할 변수
    String roomName;
    String capacity;
    int availableCount;
    String roomState;
    String roomClass;
%>

<ul>
<%
    // 배열을 순회하면서 방 정보를 출력
    for (String[] room : rooms) {
        roomName = room[0];
        capacity = room[1];
        availableCount = Integer.parseInt(room[2]);

        // 예약 상태와 CSS 클래스를 설정
        if (availableCount > 0) {
            roomState = availableCount + "실 남음";
            roomClass = "blue";
        } else {
            roomState = "예약마감";
            roomClass = "red";
        }
%>
        <li>
            <a href="room_rqt.jsp">
                <%= roomName %> (<%= capacity %>)-<span class="<%= roomClass %>"><%= roomState %></span>
            </a>
        </li>
<%
    }
%>
</ul>

	    <button class="modalCloseButton">닫기</button>
	  </div>
	</div>
	
	
	
	<div id="modalContainer_facility" class="hidden modalContainer">
	  <div class="modalContent">
	    <p>예약가능시설</p>
	    	  <%
	  
	   // 방 타입별 가능한 시간대 설정 (예시 값으로 설정)
	    int avail_num_ka = 1;
	    int avail_num_ge = 1;
	    int avail_num_pe = 3;
	    int avail_num_se = 3;
	    int avail_num_vil = 3;
	    
    // 방 타입 정보와 남은 수량을 이차원 배열로 설정
    String[][] facilities = {
    	{"카리타스 채플", "350명", String.valueOf(avail_num_ka)},
    	{"겟세마네 채플", "150명", String.valueOf(avail_num_ge)},
        {"피데스 채플", "56명", String.valueOf(avail_num_pe)},
        {"스페스 채플", "56명", String.valueOf(avail_num_se)},
        {"빌리지 채플", "50명", String.valueOf(avail_num_vil)},
    };

    // 출력할 때 사용할 변수
    String facilityName;
    String capacity_f;
    int availableCount_f;
    String facilityState;
    String facilityClass;
%>

<ul>
<%
    // 배열을 순회하면서 방 정보를 출력
    for (String[] facility : facilities) {
    	facilityName = facility[0];
    	capacity_f = facility[1];
    	availableCount_f = Integer.parseInt(facility[2]);

        // 예약 상태와 CSS 클래스를 설정
        if (availableCount_f > 0) {
        	facilityState = availableCount_f + "실 남음";
        	facilityClass = "blue";
        } else {
        	facilityState = "예약마감";
            facilityClass = "red";
        }
%>
        <li>
            <a href="fac_rqt.jsp">
                <%= facilityName %> (<%= capacity_f %>)-<span class="<%= facilityClass %>"><%= facilityState %></span>
            </a>
        </li>
<%
    }
%>
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
		// <-클릭 팝업창
		$(".room_yes").click(function(){
			$("#modalContainer_facility").removeClass("hidden");
		});
		$(".modalCloseButton").click(function(){
			$("#modalContainer_room").addClass("hidden");
			$("#modalContainer_facility").addClass("hidden");
		})
		
		$(".facility_yes").click(function(){
			$("#modalContainer_facility").removeClass("hidden");
		});
		
	});
</script>