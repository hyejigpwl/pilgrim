<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="robots"
	content="index, follow, max-image-preview:large, max-snippet:-1, max-video-preview:-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="assets/css/main.css">
<link rel="stylesheet" href="assets/css/sub.css">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.7.0/css/all.css"
	integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ"
	crossorigin="anonymous">
<!-- <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/css/bootstrap.min.css" rel="stylesheet"> -->
<script src="assets/js/jquery.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="assets/js/skel.min.js"></script>
<script src="assets/js/util.js"></script>
<script src="assets/js/header.js"></script>
<title>게시글 수정</title>
</head>
<%@include file="header.jsp"%>
<div class="sub_top">
	<h3>예약문의 수정하기</h3>
</div>
<div class="container qna_page" align="center">


	<form action="qnaModify.qa" method="post" name="qnaForm"
		enctype="multipart/form-data" onsubmit="prepareFileList()">
		<input type="hidden" name="p" value="${ param.p }" /> <input
			type="hidden" name="bno" value="${ qna.getBno() }" />

		<div class="form-group input-group">
			<div class="input-group-prepend">
				<span class="input-group-text">게시글 제목</span>
			</div>
			<input type="text" class="form-control" name="title" id="title"
				value="${ qna.getTitle() }" />
		</div>

		<div class="form-group input-group">
			<div class="input-group-prepend">
				<span class="input-group-text">게시글 내용</span>
			</div>
			<textarea name="content" id="content" cols="40" rows="15"
				class="form-control">${ qna.getContent() }</textarea>
		</div>



		<c:choose>
			<c:when test="${not empty fileList}">
				<c:forEach var="file" items="${fileList}" varStatus="status">
					<div class="form-group input-group">
						<div class="input-group-prepend">
							<span class="input-group-text">첨부파일 </span>
						</div>
						<div class="custom-file">
							<label for="file${status.index}" class="custom-file-label"
								style="text-align: left;"> ${file.fileName} </label> <input
								type="file" class="custom-file-input" id="file${status.index}"
								name="uploadFiles${status.index}" multiple />
						</div>
					</div>
				</c:forEach>

				<!-- 파일 개수가 4개보다 적으면 빈 input 추가 -->
				<c:forEach begin="${fileList.size()}" end="3" varStatus="status">
					<div class="form-group input-group">
						<div class="input-group-prepend">
							<span class="input-group-text">첨부파일 </span>
						</div>
						<div class="custom-file">
							<label for="file${fileList.size() + status.index}"
								class="custom-file-label" style="text-align: left;">
								업로드할 파일을 선택하세요 </label> <input type="file" class="custom-file-input"
								id="file${fileList.size() + status.index}"
								name="uploadFiles${fileList.size() +status.index}" multiple />
						</div>
					</div>
				</c:forEach>

			</c:when>
			<c:otherwise>
				<!-- 파일이 없을 경우 기본적으로 4개 생성 -->
				<c:forEach begin="0" end="3" varStatus="status">
					<div class="custom-file">
						<label for="file${status.index}" class="custom-file-label"
							style="text-align: left;"> 업로드할 파일을 선택하세요 </label> <input
							type="file" class="custom-file-input" id="file${status.index}"
							name="uploadFiles${status.index}" multiple />
					</div>
				</c:forEach>
			</c:otherwise>
		</c:choose>

		<input type="hidden" id="fileListInput" name="fileList" />


		<div
			class="form-group input-group mt-md-5 justify-content-center btn_wrap">
			<input type="reset"
				class="btn btn-success float-right login-btn ml-sm-2" value="새로고침" />
			<a href="javascript:history.go(-1)"
				class="btn btn-success ml-sm-2 float-right button">이전</a> <input
				type="submit" class="btn btn-success float-right login-btn special"
				value="게시글수정" />
		</div>
	</form>
</div>
<script>

$(".custom-file-input").on(
		'change',
		function() {
			let fileName = $(this).val().split('\\').pop(); // 파일명만선택
			// alert(this.value + "\n" + fileName);
			$(this).siblings(".custom-file-label").addClass("selected")
					.html(fileName);
		})
		
function prepareFileList() {
    let fileLabels = document.querySelectorAll('.custom-file-label'); // 모든 파일 라벨 가져오기
    let fileList = [];

    fileLabels.forEach(label => {
        let fileName = label.innerHTML.trim();
        if (fileName !== "업로드할 파일을 선택하세요") { // 빈 값 방지
            fileList.push(fileName);
        }
        console.log(fileName);
    });

    // 🔥 숨겨진 input에 값 저장
    document.getElementById('fileListInput').value = fileList.join(',');

    console.log("📂 서버로 전송할 파일 리스트:", fileList);
}

	
</script>
<%@include file="footer.jsp"%>
</html>