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
<title>객실 예약 페이지</title>
</head>
<body>
<%
		if(session.getAttribute("member")==null)
		{
			response.sendRedirect("login.jsp");
		}
	%>
<%@include file="header.jsp"%>
<div id="container" class="login_page room_rqt_page">
         <div class="login-wrapper">
        <h2>객실 예약</h2>
        <form method="post" action="roomForm.do" id="login-form">
            <label for="guest_count">인원</label>
            <input type="number" id="guest_count" name="guest_count" placeholder="인원 수를 입력하세요" required>

            <label for="checkin_date">입실일</label>
            <input type="date" id="checkin_date" name="checkin_date" placeholder="입실일을 입력하세요" required>
            
            <label for="checkout_date">퇴실일</label>
            <input type="date" id="checkout_date" name="checkout_date" placeholder="퇴실일을 입력하세요" required>

            <label for="room_type">룸 타입</label>
            <select name="room_type">
				<option value="2인온돌" selected>2인온돌</option>
				<option value="2인침대">2인침대</option>
				<option value="4인침대">4인침대</option>
				<option value="VIP룸">VIP룸</option>
				<option value="빌리지가족실">빌리지가족실</option>
			</select>

            <input type="submit" value="예약">
        </form>
    </div>
    </div>
<%@include file="footer.jsp"%>
</body>
</html>