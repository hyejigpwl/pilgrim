<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="robots" content="index, follow, max-image-preview:large, max-snippet:-1, max-video-preview:-1">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="assets/css/main.css">
    <link rel="stylesheet" href="assets/css/sub.css">
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/js/skel.min.js"></script>
    <script src="assets/js/util.js"></script>
    <script src="assets/js/header.js"></script>
    <title>나의 정보 수정하기</title>
</head>
<%@include file="header.jsp"%>

<div class="sub_top">
    <h3>나의 정보</h3>
</div>

	<div class="container qna_page" align="center">
		<form action="memberModify.mb" method="post" 
				id="memberForm">
			
			<div class="form-group input-group">
				<div class="input-group-prepend"><span class="input-group-text">아이디</span></div>
				<input type="text" class="form-control" name="member_id" id="member_id" value="${ member.getMember_id() }"  required/>
			</div>
			
				
			<div class="form-group input-group">
				<div class="input-group-prepend"><span class="input-group-text">비밀번호</span></div>
				<input type="password" class="form-control" name="pwd" id="pwd" value="${ member.getPwd() }" required/>
			</div>
								
			<div class="form-group input-group">
				<div class="input-group-prepend"><span class="input-group-text">이름</span></div>
				<input type="text" class="form-control" name="name" id="name" value="${ member.getName() }" required/>
			</div>
			
			<div class="form-group input-group">
				<div class="input-group-prepend"><span class="input-group-text">핸드폰</span></div>
				<input type="text" class="form-control" name="phone" id="phone" value="${ member.getPhone() }" required/>
			</div>
			
			<div class="form-group input-group">
				<div class="input-group-prepend"><span class="input-group-text">이메일</span></div>
				<input type="email" class="form-control" name="email" id="email" value="${member.getEmail() }" />
			</div>
		
			
			<%-- <div class="form-group input-group">
				<c:choose>
					<c:when test="${ !empty member.getFile() }">
						<c:set var="choose_file" value="${ member.getFile() }"/>
					</c:when>
					<c:otherwise>
						<c:set var="choose_file" value="업로드할 파일을 선택하세요!!"/>
					</c:otherwise>
				</c:choose>
				<div class="form-group input-group">
					<div class="input-group-prepend"><span class="input-group-text"><i class="fas fa-file-alt"></i></span></div>
					<div class="custom-file">
						<label for="file" class="custom-file-label" style="text-align: left;">${ choose_file }</label>
						<input type="file" class="custom-file-input" id="file" name="file"/>
					</div>
				</div> --%>
				
			<div class="form-group input-group mt-md-5 justify-content-center btn_wrap">
				
				<input type="reset" class="btn btn-success float-right login-btn ml-sm-2" value="새로고침"/>
				<a href="javascript:history.go(-1)" class="button btn btn-success ml-sm-2 float-right">이전</a>
				<input type="submit" class="btn btn-success special float-right login-btn" value="회원정보 수정"/>
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
	
	<script>
    $(document).ready(function() {
    	
    	
        $('#memberForm').on('submit', function(event) {
        
            // 아이디 유효성 검사
            const memberId = $('#member_id').val();
            if (!/^[a-zA-Z0-9_]{5,20}$/.test(memberId)) {
                alert("아이디는 5-20자의 영문자, 숫자 및 밑줄(_)만 가능합니다.");
                $('#member_id').focus();
                return false;
            }

            // 이름 유효성 검사
            const name = $('#name').val();
            if (!/^[가-힣a-zA-Z]{2,10}$/.test(name)) {
                alert("이름은 2-10자의 한글 또는 영문자만 가능합니다.");
                $('#name').focus();
                return false;
            }

            // 비밀번호 유효성 검사
            const pwd = $('#pwd').val();
			if (!/^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,20}$/.test(pwd)) {
			    alert("비밀번호는 8-20자의 영문자, 숫자 및 특수문자(!@#$%^&*)를 포함해야 합니다.");
			    $('#pwd').focus();
			    return false;
			}


            // 전화번호 유효성 검사
            const phone = $('#phone').val();
            if (!/^\d{3}\d{3,4}\d{4}$/.test(phone)) {
                alert("전화번호는 '01012345678' 형식으로 입력해주세요.");
                $('#phone').focus();
                return false;
            }

            // 이메일 유효성 검사
            const email = $('#email').val();
            if (email && !/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(email)) {
                alert("유효한 이메일 주소를 입력해주세요.");
                $('#email').focus();
                return false;
            }

        });
    });
</script>
<%@include file="footer.jsp"%>
</html>