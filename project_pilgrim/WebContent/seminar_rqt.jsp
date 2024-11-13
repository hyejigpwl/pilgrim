<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="robots" content="index, follow, max-image-preview:large, max-snippet:-1, max-video-preview:-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.14.1/themes/base/jquery-ui.min.css" integrity="sha512-TFee0335YRJoyiqz8hA8KV3P0tXa5CpRBSoM0Wnkn7JoJx1kaq1yXL/rb8YFpWXkMOjRcv5txv+C6UluttluCQ==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<link rel="stylesheet" href="assets/css/main.css">
<link rel="stylesheet" href="assets/css/sub.css">
<script src="assets/js/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.14.1/jquery-ui.min.js" integrity="sha512-MSOo1aY+3pXCOCdGAYoBZ6YGI0aragoQsg1mKKBHXCYPIWxamwOE7Drh+N5CPgGI5SA9IEKJiPjdfqWFWmZtRA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="assets/js/skel.min.js"></script>
<script src="assets/js/util.js"></script>
<script src="assets/js/header.js"></script>
<title>천로역정 세미나 예약 페이지</title>
</head>
<body>
<%
		if(session.getAttribute("member")==null)
		{
			response.sendRedirect("login.jsp");
		}

		String seminarType = request.getParameter("seminar_type");
		String seminarDate = request.getParameter("seminar_date");
	%>
<%@include file="header.jsp"%>

<div id="container" class="login_page room_rqt_page container">
         <div class="login-wrapper">
        <h2>천로역정 세미나 예약</h2>
        <form method="post" action="seminarForm.do" id="login-form">
        
        	<p class="seminar_time_title">세미나 신청 타임</p>
            <input type="radio" name="seminar_type" id="seminar_typeA" value="천로역정 A타임" <% if ("천로역정 A타임".equals(seminarType)) out.print("checked"); %>>
            <label for="seminar_typeA">천로역정 A타임(10:00~12:00 / 10,000원)</label>
            
            
            <input type="radio" name="seminar_type" id="seminar_typeB"  value="천로역정 B타임" <% if ("천로역정 B타임".equals(seminarType)) out.print("checked"); %>>
            <label for="seminar_typeB">천로역정 B타임(12:30~15:20 점심포함 / 15,000원)</label>
            
            
            <input type="radio" name="seminar_type" id="seminar_typeC" value="천로역정 C타임" <% if ("천로역정 C타임".equals(seminarType)) out.print("checked"); %>>
            <label for="seminar_typeC">천로역정 C타임(14:30~16:30 / 10,000원)</label>

            <label for="seminar_date">세미나 신청 날짜 (월~토 중 선택 / 주일 휴강)</label>
            <input type="text" id="seminar_date" name="seminar_date" placeholder="원하는 날짜를 입력하세요" required value="<%= seminarDate != null ? seminarDate : "" %>">

			 <label for="guest_count">세미나 신청 인원</label>
            <input type="number" id="guest_count" name="guest_count" placeholder="신청인원을 입력하세요" required>
            <input type="submit" value="예약">
        </form>
    </div>
    </div>
<%@include file="footer.jsp"%>
<script>
$(document).ready(function() {
	 // 체크된 시설의 예약 가능한 날짜 가져오기
    function fetchAvailableDatesForCheckedSeminar() {
        let selectedSeminarType = $("input[name='seminar_type']:checked").val();
        // console.log(selectedFacilityType);

        if (selectedSeminarType) {
            // Ajax 요청으로 선택한 시설의 예약 가능한 날짜 가져오기
            $.ajax({
                url: "availableSeminarDates.do",
                method: "GET",
                data: { seminar_type: selectedSeminarType },
                dataType: "json",
                success: function(data) {
                	 console.log("Ajax response data:", data); // 응답 데이터 구조 확인
                    let availableDates = data.map(item => item);
                    console.log("seminarAvailable Dates: " + availableDates);

                    // Datepicker 업데이트
                    $("#seminar_date").datepicker("destroy").datepicker({
                        dateFormat: 'yy-mm-dd',
                        beforeShowDay: function(date) {
                            let formattedDate = $.datepicker.formatDate('yy-mm-dd', date);
                            if (availableDates.includes(formattedDate)) {
                                return [true, "", "예약 가능"];
                            } else {
                                return [false, "", "예약 불가"];
                            }
                        }
                    });
                    
                    // console.log(data);
                },
                error: function() {
                    alert("예약 가능한 날짜 정보를 불러오는 데 실패했습니다.");
                }
            });
        }
    }

    // 페이지 로드 시 초기 호출
    fetchAvailableDatesForCheckedSeminar();

    // 시설 타입이 변경될 때 이벤트 발생
    $("input[name='seminar_type']").change(function() {
    	fetchAvailableDatesForCheckedSeminar();
    });
    
    
 // 입실일 선택 시 이벤트 처리
    $("#seminar_date").datepicker({
        dateFormat: 'yy-mm-dd'
    });


 
	
});
</script>
</html>