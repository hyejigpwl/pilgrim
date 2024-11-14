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
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css" 
	integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ"
	crossorigin="anonymous">  
<!-- <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/css/bootstrap.min.css" rel="stylesheet"> -->
<script src="assets/js/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="assets/js/skel.min.js"></script>
<script src="assets/js/util.js"></script>
<script src="assets/js/header.js"></script>
<title>예약문의 글쓰기</title>
</head>

<%@include file="header.jsp"%>
<div class="sub_top">
	<h3>예약문의 글쓰기</h3>
</div>
<div class="container qna_page" align="center">
		<!-- <div class="jumbotron">
			<h3>게시판글쓰기</h3>
			<p>게시판글쓰기 페이지 입니다!! 글을 작성해 주세요!!</p>
		</div>	-->
		
		<form action="qnaWrite.qa" method="post" 
			name="qnaForm" enctype="multipart/form-data">
			
			<div class="form-group input-group">
				<div class="input-group-prepend"><span class="input-group-text"><!-- <i class="fas fa-clipboard"></i>-->게시글 제목</span></div>
				<input type="text" class="form-control" name="title" id="title" value="게시글제목" required placeholder="게시글 제목을 입력하세요..."/>
			</div>
			
			<div class="form-group input-group">
				<div class="input-group-prepend"><span class="input-group-text"><!-- <i class="fas fa-comment-dots"></i>--> 게시글 내용</span></div>
				<textarea name="content" id="content" cols="40" rows="15" class="form-control" required placeholder="상세내용을 입력하세요!!!">상세글내용...</textarea>
			</div>
			

			<div class="form-group input-group">
				<div class="input-group-prepend"><span class="input-group-text"><!-- <i class="fas fa-file-alt"></i> -->첨부파일 </span></div>
				<div class="custom-file">
					<label for="file" class="custom-file-label" style="text-align: left; margin:0;"></label>
					<input type="file" class="custom-file-input" id="file" name="file"/>
				</div>	
			</div>
					

			
			<div class="form-group input-group mt-md-5 justify-content-center btn_wrap">
					<!-- <input type="button" class="btn btn-success float-right login-btn ml-sm-2" value="저장"/> -->
					<a href="javascript:history.go(-1)" class="btn btn-success ml-sm-2 float-right button">이전</a>
					<input type="submit" class="btn btn-success float-right login-btn special" value="게시글등록"/>
				</div>		
		</form>
	</div>
	<%@include file="footer.jsp"%>
	<script>
		$(".custom-file-input").on('change', function() {
			let fileName = $(this).val().split('\\').pop(); // 파일명만선택
			// alert(this.value + "\n" + fileName);
			$(this).siblings(".custom-file-label").addClass("selected").html(fileName);
		})
	</script>
</html>