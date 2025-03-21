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
<title>이용후기 글쓰기</title>
</head>

<%@include file="header.jsp"%>
<div class="sub_top">
	<h3>이용후기 글쓰기</h3>
</div>
<div class="container qna_page" align="center">
		<!-- <div class="jumbotron">
			<h3>게시판글쓰기</h3>
			<p>게시판글쓰기 페이지 입니다!! 글을 작성해 주세요!!</p>
		</div>	-->
		
		<form action="reviewWrite.qa" method="post" 
			name="reviewForm" enctype="multipart/form-data">
			
			<div class="form-group input-group">
				<div class="input-group-prepend"><span class="input-group-text"><!-- <i class="fas fa-clipboard"></i>-->게시글 제목</span></div>
				<input type="text" class="form-control" name="title" id="title" required placeholder="제목을 입력하세요."/>
			</div>
			
			<div class="form-group input-group">
				<div class="input-group-prepend"><span class="input-group-text"><!-- <i class="fas fa-comment-dots"></i>--> 게시글 내용</span></div>
				<textarea name="content" id="content" cols="40" rows="15" class="form-control" required placeholder="상세내용을 입력하세요."></textarea>
			</div>
			

			<div class="form-group input-group">
				<div class="input-group-prepend"><span class="input-group-text"><!-- <i class="fas fa-file-alt"></i> -->첨부파일 </span></div>
				<div class="custom-file">
					<label for="file" class="custom-file-label" style="text-align: left; margin:0;"></label>
					<input type="file" name="uploadFiles1" class="uploadFiles" multiple>
				</div>	
			</div>
			
			<div class="form-group input-group">
				<div class="input-group-prepend"><span class="input-group-text"><!-- <i class="fas fa-file-alt"></i> -->첨부파일 </span></div>
				<div class="custom-file">
					<label for="file" class="custom-file-label" style="text-align: left; margin:0;"></label>
					<input type="file" name="uploadFiles2" class="uploadFiles" multiple>
				</div>	
			</div>
			
			<div class="form-group input-group">
				<div class="input-group-prepend"><span class="input-group-text"><!-- <i class="fas fa-file-alt"></i> -->첨부파일 </span></div>
				<div class="custom-file">
					<label for="file" class="custom-file-label" style="text-align: left; margin:0;"></label>
					<input type="file" name="uploadFiles3" class="uploadFiles" multiple>
				</div>	
			</div>
			
			<div class="form-group input-group">
				<div class="input-group-prepend"><span class="input-group-text"><!-- <i class="fas fa-file-alt"></i> -->첨부파일 </span></div>
				<div class="custom-file">
					<label for="file" class="custom-file-label" style="text-align: left; margin:0;"></label>
					<input type="file" name="uploadFiles4" class="uploadFiles" multiple>
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
	$(".uploadFiles").on("change", function() {
	    let files = $(this)[0].files; // 선택한 파일 리스트
	    let fileNames = [];

	    for (let i = 0; i < files.length; i++) {
	        fileNames.push(files[i].name); // 모든 파일명을 배열에 추가
	    }

	    // 선택한 모든 파일명을 표시
	    $(this).siblings(".custom-file-label").addClass("selected").html(fileNames.join(", "));
	});


	</script>
</html>