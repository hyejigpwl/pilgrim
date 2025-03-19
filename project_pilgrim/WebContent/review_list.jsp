<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<title>이용후기</title>
</head>
<%@include file="header.jsp"%>
<div class="sub_top">
	<h3>이용후기</h3>
</div>
<div class="container mt-sm-5 qna_page" align="center">
	<div class="jumbotron">
		<c:if test="${ reviewList.isEmpty() }">
			<h5>
				<p class="bg-danger text-white">등록된 게시글이 없습니다.</p>
			</h5>
		</c:if>
	</div>

	<form action="reviewList.qa" class="form-line">
		<div class="input-group search">
			<select name="f" id="f" class="form-control col-sm-2 mr-sm-2">
				<option ${ param.f == "member_id" ? "selected" : ""}
					value="member_id">작성자</option>
				<option ${ param.f == "title" ? "selected" : ""} value="title">제목</option>
			</select> <input type="text" name="q" class="form-control col-sm-8 mr-sm-2"
				value="${ param.q }" placeholder="검색어를 입력하세요" />
			<button type="submit"
				class="btn btn-primary col-sm-1 mr-sm-2 login-btn search_btn">검색</button>
		</div>
	</form>
	<br class="mt-sm-5" />

	<table class="table table-bordered table-striped table-hover">
		<thead class="table-dark" align="center">
			<tr style="display: none;">
				<th width="5%">번호</th>
				<th width="50%">제목</th>
				<th width="15%">작성자</th>
				<th width="15%">등록일</th>
				<th width="15">조회수</th>
				<!--<th width="10%">파일</th>
					 <th width="15%">삭제</th> -->
			</tr>
		</thead>
		<tbody>
			<c:set var="startNumber" value="${(pageInfo.page - 1) * 10}" />
			<c:forEach var="review" items="${ reviewList }" varStatus="status">
				<tr>
					<td class="bno">${startNumber + status.count}</td>
					<td class="title">
						<!-- 비밀글 설정 --> <%-- <c:set var="sessionMemberId" value="${sessionScope.member_id}" />
						<c:set var="isAdmin" value="${sessionMemberId == '관리자'}" />
			            <c:choose>
			                <c:when test="${ review.getMember_id() == sessionMemberId || isAdmin }">
			                    <!-- ID가 같거나 관리자인 경우 링크 활성화 -->
			                    <a href="reviewDetail.qa?p=${ param.p }&bno=${ review.getBno() }">${ review.getTitle() }</a>
			                </c:when>
			                <c:otherwise>
			                    <!-- ID가 다르고 관리자가 아닌 경우 경고 메시지 -->
			                    <a href="#" onclick="alert('접근권한이 없습니다'); return false;">${ review.getTitle() }</a>
			                </c:otherwise>
			            </c:choose> --%> <a
						href="reviewDetail.qa?p=${ param.p }&bno=${ review.getBno() }">${review.getTitle()}</a>
						<span class="badge badge-danger ml-sm-3">${ review.getReply_count() }</span>
						<c:set var="hasFile" value="false" /> <c:forEach var="file"
							items="${ fileList }">
							<c:if test="${ file.review_bno == review.getBno() }">
								<c:set var="hasFile" value="true" />
							</c:if>
						</c:forEach> <c:if test="${ hasFile }">
							<i class="fas fa-file-download"></i>
						</c:if>

					</td>
					<td class="member_id">${ review.getMember_id() }</td>

					<td class="date">${ review.getDate() }</td>
					
					<td class="count">${review.getView_count()}</td>
				</tr>
			</c:forEach>

		</tbody>
	</table>
	<a href="reviewWriteForm.qa?p=${ param.p }"
		class="button btn btn-primary col-sm-1 login-btn small">글쓰기</a>
</div>
<br />

<div class="pagination_wrap" align="center">
	<ul class="pagination justify-content-center">
		<c:if test="${ pageInfo.getStartPage() != 1 }">
			<li class="page-item page-link"><a href="reviewList.qa?p=1"><i
					class="fas fa-fast-backward"></i></a></li>
			<li class="page-item page-link"><a
				href="reviewList.qa?p=${ pageInfo.getStartPage() - 10 }"><i
					class="fas fa-backward"></i></a></li>
		</c:if>

		<c:forEach var="page_num" begin="${ pageInfo.getStartPage() }"
			end="${ pageInfo.getEndPage() }">
			<li
				class="page-item page-link ${ param.p == page_num ? 'bg-warning' : ''}"><a
				href="reviewList.qa?p=${ page_num }">${ page_num }</a></li>
		</c:forEach>

		<c:if test="${ pageInfo.getEndPage() < pageInfo.getTotalPage() }">
			<li class="page-item page-link"><a
				href="reviewList.qa?p=${ pageInfo.getStartPage() + 10 }""><i
					class="fas fa-forward"></i></a></li>
			<li class="page-item page-link"><a
				href="reviewList.qa?p=${ pageInfo.getTotalPage() }"><i
					class="fas fa-fast-forward"></i></a></li>
		</c:if>

	</ul>
</div>
<%@include file="footer.jsp"%>
</html>