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
<title>로그인</title>
</head>
<%@include file="header.jsp"%>
<!--  html 전체 영역을 지정하는 container -->
    <div id="container" class="login_page">
         <div class="login-wrapper">
        <h2>Login</h2>
        <form method="post" action="login_ok.jsp" id="login-form">
            <input type="text" name="member_id" placeholder="Id">
            <input type="password" name="pwd" placeholder="Password">
             <input type="checkbox" id="remember-check">
            <label for="remember-check" style="font-size:.8em">
            	아이디 저장하기
            </label>
            <input type="submit" value="Login">
        </form>
    </div>
    </div>
<%@include file="footer.jsp"%>
</html>