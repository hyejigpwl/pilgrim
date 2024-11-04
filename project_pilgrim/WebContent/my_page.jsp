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
<title>나의 예약</title>
</head>
<%@include file="header.jsp"%>
<%
		String re = (String)session.getAttribute("member");
	
		if(re==null)
		{
			response.sendRedirect("login.jsp");
			return;
		}
		
		request.setCharacterEncoding("utf-8");
		String member_id = (String)session.getAttribute("member_id");
		
		
		
	%>
	<h2><%= member_id %> 회원님 환영합니다.</h2>
	
	<!-- 로그아웃 버튼 -->
    <form action="logout.mb" method="post">
        <input type="submit" value="Logout">
    </form>
<%@include file="footer.jsp"%>
</html>