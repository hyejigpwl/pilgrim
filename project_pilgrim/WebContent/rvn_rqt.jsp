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
<div class="calendar">
	<ul class="actions">
		<li><a href="#" class="button special big">천로역정 세미나 예약</a></li>
		<li><a href="#" class="button big">객실 예약</a></li>
		<li><a href="#" class="button big">시설 예약</a></li>
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
<%
			// 1일 앞 달
			Calendar preCal = (Calendar)cal.clone();
			preCal.add(Calendar.DATE, -(week-1));
			int preDate = preCal.get(Calendar.DATE);
			
			out.print("<tr>");
			// 1일 앞 부분
			for(int i=1; i<week; i++) {
				//out.print("<td>&nbsp;</td>");
				out.print("<td class='gray'>"+(preDate++)+"</td>");
			}
			
			// 1일부터 말일까지 출력
			int lastDay = cal.getActualMaximum(Calendar.DATE);
			String cls;
			String room_txt = "예약가능객실<-클릭";
			String seminarA_txt = "천로역정A";
			String seminarB_txt = "천로역정B";
			String seminarC_txt = "천로역정C";
			String facility_txt = "예약가능시설<-클릭";
			
			String s_cls = "a_seminar";
			String r_cls = "a_room";
			String f_cls = "a_facility";
			String n_cls = "a_no";
			for(int i=1; i<=lastDay; i++) {
				cls = year==ty && month==tm && i==td ? "today":"";
				cls = i<=td?"":"";
				out.print("<td class='"+cls+"'>"+i
						
						+"<a href='#none' class='"+r_cls+"'>"+ room_txt +"</a>"
						+"<a href='#none' class='"+s_cls+"'>"+ seminarA_txt +"</a>"
						+"<a href='#none' class='"+s_cls+"'>"+ seminarB_txt +"</a>"
						+"<a href='#none' class='"+s_cls+"'>"+ seminarC_txt +"</a>"
						+"<a href='#none' class='"+f_cls+"'>"+ facility_txt +"</a>"
						
					
						+"</td>");
				if(lastDay != i && (++week)%7 == 1) {
					out.print("</tr><tr>");
				}
			}
			
			// 마지막 주 마지막 일자 다음 처리
			int n = 1;
			for(int i = (week-1)%7; i<6; i++) {
				// out.print("<td>&nbsp;</td>");
				out.print("<td class='gray'>"+(n++)+"</td>");
			}
			out.print("</tr>");
%>		
		</tbody>
	</table>
</div>
<%@include file="footer.jsp"%>
<script>
	$(function(){
		// header
		$("#header.alt img").attr("src", "assets/images/pilgrim_logo_b.png");
	});
</script>