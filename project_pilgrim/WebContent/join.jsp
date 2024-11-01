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
<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/skel.min.js"></script>
<script src="assets/js/util.js"></script>
<script src="assets/js/header.js"></script>
<title>회원가입</title>
</head>	
<%@include file="header.jsp"%>
<!--  html 전체 영역을 지정하는 container -->
    <div id="container" class="login_page join_page">
         <div class="login-wrapper">
        <h2>JOIN</h2>
        <form method="post" action="joinForm.do" id="login-form">
            <!-- 아이디 -->
            <label for="member_id">아이디</label>
            <input type="text" id="member_id" name="member_id" placeholder="아이디를 입력하세요" required>

            <!-- 이름 -->
            <label for="name">이름</label>
            <input type="text" id="name" name="name" placeholder="이름을 입력하세요" required>

            <!-- 비밀번호 -->
            <label for="pwd">비밀번호</label>
            <input type="password" id="pwd" name="pwd" placeholder="비밀번호를 입력하세요" required>

            <!-- 전화번호 -->
            <label for="phone">전화번호</label>
            <input type="text" id="phone" name="phone" placeholder="전화번호를 입력하세요" required>

            <!-- 이메일 -->
            <label for="email">이메일</label>
            <input type="email" id="email" name="email" placeholder="이메일을 입력하세요">



            <!-- 약관 동의 여부 -->
             <label for="terms_content">이용 약관</label>
            <textarea id="terms_content" readonly>
                [이용 약관]

                제 1 조 (목적)
                본 약관은 회사가 제공하는 서비스를 이용함에 있어, 회사와 회원 간의 권리와 의무를 규정하는 것을 목적으로 합니다.

                제 2 조 (회원가입)
                1. 회원은 회사가 정한 양식에 따라 회원 정보를 기입하고 약관에 동의하여 회원가입을 신청합니다.
                2. 회사는 제1항에 따라 회원 가입을 신청한 이용자에게 서비스 이용을 승낙함으로써 회원으로 등록됩니다.

                제 3 조 (서비스 이용)
                1. 회사는 회원에게 아래와 같은 서비스를 제공합니다:
                    - 회원 전용 콘텐츠 제공
                    - 맞춤형 서비스 추천
                    - 기타 회사가 정하는 서비스
                2. 회원은 서비스 이용 시 회사의 이용 정책을 준수해야 합니다.

                제 4 조 (계약 해지)
                1. 회원은 언제든지 회원 탈퇴를 요청할 수 있으며, 회사는 즉시 탈퇴 처리를 진행합니다.
                2. 회사는 회원이 약관을 위반할 경우 사전 통보 없이 회원 자격을 박탈할 수 있습니다.

                [부칙]
                본 약관은 2023년 1월 1일부터 적용됩니다.
            </textarea>
            <input type="checkbox" id="terms_agreed" name="terms_agreed" required>
            <label for="terms_agreed">
            	약관에 동의합니다
            </label>
            <input type="submit" value="JOIN">
        </form>
    </div>
    </div>
<%@include file="footer.jsp"%>
</html>