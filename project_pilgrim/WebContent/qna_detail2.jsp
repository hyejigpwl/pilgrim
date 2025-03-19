<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="robots" content="index, follow, max-image-preview:large, max-snippet:-1, max-video-preview:-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="assets/css/main.css">
<link rel="stylesheet" href="assets/css/sub.css">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css" 
	integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ"
	crossorigin="anonymous">  
<!-- <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/css/bootstrap.min.css" rel="stylesheet"> -->
<script src="assets/js/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="assets/js/skel.min.js"></script>
<script src="assets/js/util.js"></script>
<script src="assets/js/header.js"></script>
<title></title>
</head>
<%@include file="header.jsp"%>
	<%-- ${ board }  --%>
	<div class="container mt-sm-5 qna_page" align="center">
		<div class="jumbotron">
			<h3>게시글상세조회</h3>
		</div>
		
		<div class="form-group input-group">
			<div class="input-group-prepend"><span class="input-group-text"><i class="fas fa-user"></i></span></div>
			<p>${ review.getMember_id()}</p>
		</div>	
		
		<div class="form-group input-group">
			<div class="input-group-prepend"><span class="input-group-text"><i class="fas fa-user"></i></span></div>
			<p>${ review.getDate()}</p>
		</div>	
			
		<div class="form-group input-group">
			<div class="input-group-prepend"><span class="input-group-text"><i class="fas fa-clipboard"></i></span></div>
			<p>${ review.getTitle()}</p>
		</div>		
		
		<div class="form-group input-group">
			<div class="input-group-prepend"><span class="input-group-text"><i class="fas fa-comment-dots"></i></span></div>
			<p>${ review.getContent()}</p>
		</div>		
		
		<c:if test="${ review.getFile() != ''  or review.getFile() != null}">
			<div class="form-group input-group">
				<div class="input-group-prepend"><span class="input-group-text"><i class="fas fa-file-alt"></i></span></div>
				<a href="download.qa?bno=${review.getBno() }&fn=${review.getFile()}">${ review.getFile()}</a>
			</div>
		</c:if>
		<br />
		
		<div class="form-group input-group">
	
			<a href="qnaModifyForm.qa?p=${ param.p }&bno=${ review.getBno() }" class="button btn btn-success mr-sm-3 login-btn">수정</a>
			<a href="qnaDelete.qa?p=${ param.p }&bno=${ review.getBno() }" class="button btn btn-success mr-sm-3 login-btn">삭제</a>
			<!-- <a href="qnaReplyForm.do?p=${ param.p }&bno=${ review.getBno() }" class="button btn btn-success mr-sm-3 login-btn">답변</a> -->
			<a href="qnaList.qa?p=${ param.p }" class="button btn btn-success mr-sm-3 login-btn">글목록</a>
		</div>
	</div>
<%@include file="footer.jsp"%>
</html>