<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
<title>게시글 상세</title>
</head>
<%@include file="header.jsp"%>
<div class="sub_top">
	<h3>예약문의 및 취소</h3>
</div>
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
						<span>${qna.getDate() }</span>
					</li>
					
					<li class="b-hit-box">
						<span>조회수</span>
						<span>${ qna.getView_count()}</span>
					</li>
				</ul>
			</div>
		</div>
		<c:if test="${ !empty qna.getFile()}">
		<div class="b-file-box">
			<ul>
				<li>
					<a href="download.qa?bno=${qna.getBno() }&fn=${qna.getFile()}">${ qna.getFile()}</a>
				</li>
			</ul>
		</div>
		</c:if>
		<div class="b-content-box">
			${qna.getContent() }
		</div>
		
		
	</div>
</div>


<!--  댓글 -->
<div class="comments-section">
    <!-- 댓글 목록 -->
    <c:forEach var="reply" items="${replyList}">
        <div class="comment">
            <div class="comment-author">${reply.member_id }</div> 
            <div class="comment-date">
                <fmt:formatDate value="${reply.date}" pattern="yyyy-MM-dd HH:mm:ss" timeZone="Asia/Seoul" />
            </div> 
            <div class="comment-content">${reply.content }</div> 
        </div>
        <a href="qnaReplyDelete.qa?p=${ param.p }&bno=${ qna.getBno() }" class="button btn btn-success mr-sm-3 login-btn">삭제</a>
    </c:forEach>

   <!-- 댓글 입력 창 -->
    <div class="comment-input">
        <form action="qnaReply.qa" method="post">
            <!-- 게시글 ID를 서버로 보내기 위한 hidden 필드 -->
            <input type="hidden" name="bno" value="${qna.bno}">
             <input type="hidden" name="p" value="${param.p}">
            <textarea name="content" placeholder="댓글을 입력하세요..." rows="3" required></textarea>
            <button type="submit" class="button small">댓글 달기</button>
        </form>
    </div>
</div>



	<%-- 로그인한 사용자 ID 가져오기 --%>
<%-- 로그인한 사용자 ID 가져오기 --%>
<c:set var="sessionMemberId" value="${sessionScope.member_id}" />
<c:set var="isAdmin" value="${sessionMemberId == '관리자'}" />

<!-- 삭제 버튼: 관리자이거나 작성자 본인인 경우 -->
<c:if test="${ qna.getMember_id() == sessionMemberId || isAdmin }">
    <a href="qnaDelete.qa?p=${ param.p }&bno=${ qna.getBno() }" class="button btn btn-success mr-sm-3 login-btn">삭제</a>
</c:if>

<!-- 수정 버튼: 작성자 본인인 경우에만 -->
<c:if test="${ qna.getMember_id() == sessionMemberId }">
    <a href="qnaModifyForm.qa?p=${ param.p }&bno=${ qna.getBno() }" class="button btn btn-success mr-sm-3 login-btn">수정</a>
</c:if>

<!-- 글 목록 버튼 -->
<a href="qnaList.qa?p=${ param.p }" class="button btn btn-success mr-sm-3 login-btn">글목록</a>





</div>




<%@include file="footer.jsp"%>
</html>