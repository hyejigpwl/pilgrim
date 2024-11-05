<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<title>천로역정 세미나 예약 페이지</title>
</head>
<body>
<%
		if(session.getAttribute("member")==null)
		{
			response.sendRedirect("login.jsp");
		}
	%>
<%@include file="header.jsp"%>
<div id="container" class="login_page room_rqt_page container">
         <div class="login-wrapper">
        <h2>천로역정 세미나 예약</h2>
        <form method="post" action="seminarForm.do" id="login-form">
        
        	<p class="seminar_time_title">세미나 신청 타임</p>
            <input type="radio" name="seminar_type" id="seminar_typeA" value="천로역정 A타임">
            <label for="seminar_typeA">천로역정 A타임(10:00~12:00)</label>
            
            
            <input type="radio" name="seminar_type" id="seminar_typeB"  value="천로역정 B타임">
            <label for="seminar_typeB">천로역정 B타임(12:30~15:20 점심포함)</label>
            
            
            <input type="radio" name="seminar_type" id="seminar_typeC" value="천로역정 C타임">
            <label for="seminar_typeC">천로역정 C타임(14:30~16:30)</label>



            <label for="guest_count">세미나 신청 인원</label>
            <input type="number" id="guest_count" name="guest_count" placeholder="신청인원을 입력하세요" required>
            
            <label for="seminar_date">세미나 신청 날짜 (월~토 중 선택 / 주일 휴강)</label>
            <input type="date" id="seminar_date" name="seminar_date" placeholder="원하는 날짜를 입력하세요" required>

            <input type="submit" value="예약">
        </form>
    </div>
    </div>
<%@include file="footer.jsp"%>
</body>
</html>