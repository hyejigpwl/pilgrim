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
<title>게시글 수정</title>
</head>
<%@include file="header.jsp"%>
	<div class="container mt-sm-5" align="center">
		<div class="jumbotron">
			<h3>게시글수정하기</h3>
		</div>
		
		<form action="qnaModify.qa" method="post" 
				name="qnaForm" enctype="multipart/form-data">
			<input type="hidden" name="p" value="${ param.p }"/>
			<input type="hidden" name="bno" value="${ qna.getBno() }"/>
								
			<div class="form-group input-group">
				<div class="input-group-prepend"><span class="input-group-text"><i class="fas fa-clipboard"></i></span></div>
				<input type="text" class="form-control" name="title" id="title" value="${ qna.getTitle() }"/>
			</div>
			
			<div class="form-group input-group">
				<div class="input-group-prepend"><span class="input-group-text"><i class="fas fa-comment-dots"></i></span></div>
				<textarea name="content" id="content" cols="40" rows="15" class="form-control" >${ qna.getContent() }</textarea>
			</div>	
			
			<div class="form-group input-group">
				<c:choose>
					<c:when test="${ !empty qna.getFile() }">
						<c:set var="choose_file" value="${ qna.getFile() }"/>
					</c:when>
					<c:otherwise>
						<c:set var="choose_file" value="업로드할 파일을 선택하세요"/>
					</c:otherwise>
				</c:choose>
				<div class="form-group input-group">
					<div class="input-group-prepend"><span class="input-group-text"><i class="fas fa-file-alt"></i></span></div>
					<div class="custom-file">
						<label for="file" class="custom-file-label" style="text-align: left;">${ choose_file }</label>
						<input type="file" class="custom-file-input" id="file" name="file"/>
					</div>
				</div>
				
				<div class="form-group input-group mt-md-5 justify-content-center">
					<input type="submit" class="btn btn-success float-right login-btn" value="게시글수정"/>
					<input type="reset" class="btn btn-success float-right login-btn ml-sm-2" value="새로고침"/>
					<a href="javascript:history.go(-1)" class="btn btn-success ml-sm-2 float-right button">이전</a>
				</div>				
			</div>		
		</form>
	</div>
		<script>
		$(".custom-file-input").on('change', function() {
			let fileName = $(this).val().split('\\').pop(); // 파일명만선택
			// alert(this.value + "\n" + fileName);
			$(this).siblings(".custom-file-label").addClass("selected").html(fileName);
		})
	</script>
<%@include file="footer.jsp"%>
</html>