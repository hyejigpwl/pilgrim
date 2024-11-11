<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<title>예약문의 및 취소</title>
</head>
<%@include file="header.jsp"%>
<div class="container mt-sm-5" align="center">
		<div class="jumbotron">
			<h3>게시글목록조회</h3>
			<c:if test="${ qnaList.isEmpty() }">
				<h5><p class="bg-danger text-white">등록된 게시글이 없습니다!!!</p></h5>
			</c:if>
		</div>
		
		<form action="qnaList.qa" class="form-line">
			<div class="input-group">
				<select name="f" id="f" class="form-control col-sm-2 mr-sm-2">
					<option ${ param.f == "member_id" ? "selected" : ""} value="member_id">작성자</option>
					<option ${ param.f == "title" ? "selected" : ""} value="title">제목</option>
				</select>
				<input type="text" name="q" class="form-control col-sm-8 mr-sm-2" value="${ param.q }"
					placeholder="검색어를 입력하세요!!!"/>
				<button type="submit" class="btn btn-primary col-sm-1 mr-sm-2 login-btn">검색</button>
				<a href="qnaWriteForm.qa?p=${ param.p }" class="btn btn-success col-sm-1 login-btn">글쓰기</a>
			</div>
		</form>
		<br class="mt-sm-5" />
		
		<table class="table table-bordered table-striped table-hover">
			<thead class="table-dark" align="center">
				<tr>
					<th width="10%">글번호</th>
					<th width="35%">제목</th>
					<th width="10%">작성자</th>
					<th width="10%">등록일</th>
					<th width="25%">파일</th>
					<th width="10%">삭제</th>				
				</tr>
			</thead>
			<tbody>
			<c:forEach var="qna" items="${ qnaList }">
				<tr>
					<td>${ qna.getBno() }</td>
					<td>
						<a href="qnaDetail.qa?p=${ param.p }&bno=${ qna.getBno() }">${ qna.getTitle() }</a>
						<span class="badge badge-danger ml-sm-3"> ${ qna.getView_count() }</span>						
					</td>
					<td>${ qna.getMember_id() }</td>
					<td>${ qna.getDate() }</td>
					<td align="center">
						<c:if test="${ !empty qna.getFile()}">
							<a href="download.qa?bno=${qna.getBno() }&fn=${qna.getFile()}">
								<i class="fas fa-file-download"> ${qna.getFile() }</i>
							</a>
						</c:if>
				
					</td>
					<td align="center">
						<a href="qnaDelete.qa?p=${param.p}&bno=${ qna.getBno() }"><i class="fas fa-trash-alt"></i></a>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
	<br />
	
	<div class="container" align="center">
		<ul class="pagination justify-content-center">
			<c:if test="${ pageInfo.getStartPage() != 1 }">
				<li class="page-item page-link"><a href="qnaList.qa?p=1"><i class="fas fa-fast-backward"></i></a></li>
				<li class="page-item page-link"><a href="qnaList.qa?p=${ pageInfo.getStartPage() - 10 }"><i class="fas fa-backward"></i></a></li>			
			</c:if>
		
			<c:forEach var="page_num" begin="${ pageInfo.getStartPage() }" end="${ pageInfo.getEndPage() }">
				<li class="page-item page-link ${ param.p == page_num ? 'bg-warning' : ''}"><a href="qnaList.qa?p=${ page_num }">${ page_num }</a></li>
			</c:forEach>
		
			<c:if test="${ pageInfo.getEndPage() < pageInfo.getTotalPage() }">
				<li class="page-item page-link"><a href="qnaList.qa?p=${ pageInfo.getStartPage() + 10 }""><i class="fas fa-forward"></i></a></li>
				<li class="page-item page-link"><a href="qnaList.qa?p=${ pageInfo.getTotalPage() }"><i class="fas fa-fast-forward"></i></a></li>
			</c:if>
			
		</ul>
	</div>
<%@include file="footer.jsp"%>
</html>