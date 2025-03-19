<%@page import="com.lec.review.vo.ReviewVO"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<body>
	<%-- ${ board } --%>
	<div class="container" align="center">
		<div class="jumbotron">
			<h3>게시글 삭제하기</h3>
		</div>
	
		<form action="reviewDelete.qa">
			<input type="hidden" name="p" value="${ param.p }"/>
			<input type="hidden" name="bno" value="${ board.getBno() }"/>
			
			<div class="form-group input-group">
				<div class="input-group-prepend"><span class="input-group-text"><i class="fas fa-key"></i></span></div>
				<input type="password" class="form-control" name="pass" id="pass" value="12345" required placeholder="게시글 비밀번호를 입력하세요..."/>						
			</div>
			
			<div class="form-group input-group">
				<input type="submit" class="btn btn-danger mr-sm-3" value="게시글삭제"/>
				<input type="button" class="btn btn-success" 
					value="이전" onclick="javascript:history.go(-1)"/>
			</div>
		</form>
	
	</div>	
	
</body>
</html>










