<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
// 사용자가 왔던 페이지를 세션에 저장
String referer = request.getHeader("referer");
if (referer != null && !referer.contains("login.jsp") && !referer.contains("join.jsp")) { // login.jsp가 아닌 경우에만 저장
	session.setAttribute("previousPage", referer);
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="robots"
	content="index, follow, max-image-preview:large, max-snippet:-1, max-video-preview:-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="assets/css/main.css">
<link rel="stylesheet" href="assets/css/sub.css">
<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/skel.min.js"></script>
<script src="assets/js/util.js"></script>
<script src="assets/js/header.js"></script>
<title>로그인</title>
</head>
<%@include file="header.jsp"%>

<!-- 쿠키에서 userId 가져오기 -->
<%
String cookieValue = "";
Cookie[] cookies = request.getCookies();
if (cookies != null) {
	for (Cookie c : cookies) {
		if ("userId".equals(c.getName())) {
	cookieValue = c.getValue();
	break;
		}
	}
}
%>

<!--  html 전체 영역을 지정하는 container -->
<div id="container" class="login_page container">
	<div class="login-wrapper">
		<h2>Login</h2>
		<form method="post" action="loginForm.mb" id="login-form">
			<input type="text" name="member_id" placeholder="Id"
				value="<%=cookieValue%>"> <input type="password" name="pwd"
				placeholder="Password">

			<!-- 🔥 체크박스 상태 유지 -->
			<input type="checkbox" name="checkbox" id="remember-check"
				<%=!cookieValue.isEmpty() ? "checked" : ""%>> <label
				for="remember-check" style="font-size: .8em; line-height: 20px;">아이디
				저장하기</label>

			<%
			String errorMessage = (String) request.getAttribute("errorMessage");
			if (errorMessage != null) {
			%>
			<p style="color: red;"><%=errorMessage%></p>
			<script>
				document.getElementsByName("member_id")[0].focus();
			</script>
			<%
			}
			%>
			<input type="submit" value="Login">
			<!-- <input type="button" value="Connect with facebook"> -->
			<a href="join.jsp" class="login_btm_a">회원가입</a>
		</form>
	</div>
</div>
<%@include file="footer.jsp"%>
</html>