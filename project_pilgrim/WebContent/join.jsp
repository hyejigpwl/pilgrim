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
            <div style="display: flex;">
                <input type="text" id="member_id" name="member_id" placeholder="아이디를 입력하세요" required>
                <button type="button" id="check-id-btn">중복 확인</button>
            </div>

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

<script>
    $(document).ready(function() {
    	$('#check-id-btn').on('click', function() {
    	    const memberId = $('#member_id').val();
    	    if (!memberId) {
    	        alert("아이디를 입력해주세요.");
    	        return;
    	    }

    	    $.ajax({
    	        type: "POST",
    	        url: "checkId.do",
    	        data: { member_id: memberId },
    	        success: function(response) {
    	            if (response.trim() === "true") {
    	                alert("사용 가능한 아이디입니다.");
    	                isIdChecked = true; // 중복 확인 완료
    	            } else {
    	                alert("이미 사용 중인 아이디입니다.");
    	                isIdChecked = false;
    	            }
    	        },
    	        error: function() {
    	            alert("아이디 중복 확인에 실패했습니다. 다시 시도해주세요.");
    	        }
    	    });
    	});
    	
        $('#login-form').on('submit', function(event) {
        	if (!isIdChecked) {
                alert("아이디 중복 확인을 해주세요.");
                return false;
            }
        	
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
            if (!/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,20}$/.test(pwd)) {
                alert("비밀번호는 8-20자의 영문자 및 숫자를 포함해야 합니다.");
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

            // 약관 동의 확인
            if (!$('#terms_agreed').is(':checked')) {
                alert("약관에 동의하셔야 회원가입이 가능합니다.");
                return false;
            }
        });
    });
</script>
</html>