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

<div class="container my_info">
	<div class="my_info_wrap">
		<p class="category profile">
    <c:choose>
        <c:when test="${not empty member.file}">
            <label for="">프로필이미지</label>
            <img id="preview_image" src="${pageContext.request.contextPath}/image?file=${member.file}" alt="프로필 이미지">
        </c:when>
        <c:otherwise>
            <label for="">프로필 이미지를 설정하세요</label>
            <img id="preview_image" src="https://www.du.plus/images/mypage/img-upload.svg" alt="Default 이미지">
        </c:otherwise>
    </c:choose>
    <span class="upload_img_btn">
        <input type="file" name="file" accept=".gif,.jpg,.png" style="display:none">
    </span>
</p>

		<p>아이디: ${member.member_id}</p>
	    <p>비밀번호: ${member.pwd}</p>
	    <p>이름: ${member.name}</p>
	    <p>전화번호: ${member.phone}</p>
	    <p>이메일: ${member.email}</p>
	    <a href="memberModifyForm.mb?member_id=${member_id }" class="button btn btn-success mr-sm-3 login-btn">수정</a>	
	</div>
</div>  

<%@include file="footer.jsp"%>


</html>
