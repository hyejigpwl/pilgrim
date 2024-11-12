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
<div class="container">
	<div class="bn-view-common01 type01 ">
	<input type="hidden" name="articleNo" value="50433" />
	<div class="b-main-box">
		<div class="b-top-box">
			<p class="b-title-box">
				<span>${qna.getTitle() }</span>
			</p>
			
			<div class="b-etc-box">
				<ul>
					<li class="b-writer-box">
						<span>작성자</span>
						<span>${qna.getMember_id()}</span>
					</li>
					
					<li class="b-date-box">
						<span>작성일</span>
						<span>${qna.getDate() })</span>
					</li>
					
					<li class="b-hit-box">
						<span>조회수</span>
						<span>${ qna.getView_count()}</span>
					</li>
				</ul>
			</div>
		</div>
	
		<div class="b-file-box">
			<ul>
				<li>
					<a href="download.qa?bno=${qna.getBno() }&fn=${qna.getFile()}">${ qna.getFile()}</a>
				</li>
			</ul>
		</div>
		
		<div class="b-content-box">
			${qna.getContent() }
		</div>
	</div>
</div>

	<a href="qnaModifyForm.qa?p=${ param.p }&bno=${ qna.getBno() }" class="button btn btn-success mr-sm-3 login-btn">수정</a>
			<a href="qnaDelete.qa?p=${ param.p }&bno=${ qna.getBno() }" class="button btn btn-success mr-sm-3 login-btn">삭제</a>
			<!-- <a href="qnaReplyForm.do?p=${ param.p }&bno=${ qna.getBno() }" class="button btn btn-success mr-sm-3 login-btn">답변</a> -->
			<a href="qnaList.qa?p=${ param.p }" class="button btn btn-success mr-sm-3 login-btn">글목록</a>
</div>



<%@include file="footer.jsp"%>
</html>