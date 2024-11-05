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
<title>시설 예약 페이지</title>
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
        <h2>시설 예약</h2>
        <form method="post" action="facilityForm.do" id="login-form">
        
        	<label for="facility_type">룸 타입</label>
            <select name="facility_type">
				<option value="카리타스(350명)" selected>카리타스(350명)</option>
				<option value="겟세마네(150명)">겟세마네(150명)</option>
				<option value="피데스(56명)">피데스(56명)</option>
				<option value="스페스(56명)">스페스(56명)</option>
				<option value="빌리지(50명)">빌리지(50명)</option>
			</select>

            <label for="checkin_date">입실일</label>
            <input type="date" id="checkin_date" name="checkin_date" placeholder="입실일을 입력하세요" required>
            
            <label for="checkout_date">퇴실일</label>
            <input type="date" id="checkout_date" name="checkout_date" placeholder="퇴실일을 입력하세요" required>

            <input type="submit" value="예약">
        </form>
    </div>
    </div>
<%@include file="footer.jsp"%>
</body>
</html>