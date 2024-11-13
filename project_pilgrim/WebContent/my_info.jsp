<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>나의 정보</title>
</head>
<%@include file="header.jsp"%>

<div class="sub_top">
    <h3>나의 정보</h3>
</div>

<div class="container my_page">
    <p>아이디: ${member.member_id}</p>
    <p>비밀번호: ${member.pwd}</p>
    <p>이름: ${member.name}</p>
    <p>전화번호: ${member.phone}</p>
    <p>이메일: ${member.email}</p>
</div>  

<%@include file="footer.jsp"%>
</html>
