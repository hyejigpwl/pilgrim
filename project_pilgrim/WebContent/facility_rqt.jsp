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
<title>시설 예약 페이지</title>
</head>
<body>
<%
		if(session.getAttribute("member")==null)
		{
			response.sendRedirect("login.jsp");
		}

		String facilityType = request.getParameter("facility_type");
		String checkinDate = request.getParameter("checkin_date");
	%>
<%@include file="header.jsp"%>
<div id="container" class="login_page room_rqt_page container">
         <div class="login-wrapper">
        <h2>시설 예약</h2>
        <form method="post" action="facilityForm.do" id="login-form">
        
			<p class="seminar_time_title">시설 타입</p>
            <input type="radio" name="facility_type" id="caritas" value="카리타스(350명)" <% if ("카리타스(350명)".equals(facilityType)) out.print("checked"); %>>
            <label for="caritas">카리타스(350명)</label>
            
            
            <input type="radio" name="facility_type" id="getsemane"  value="겟세마네(150명)" <% if ("겟세마네(150명)".equals(facilityType)) out.print("checked"); %>>
            <label for="getsemane">겟세마네(150명)</label>
            
            
            <input type="radio" name="facility_type" id="pides" value="피데스(56명)" <% if ("피데스(56명)".equals(facilityType)) out.print("checked"); %>>
            <label for="pides">피데스(56명)</label>
            
             <input type="radio" name="facility_type" id="spes" value="스페스(56명)" <% if ("스페스(56명)".equals(facilityType)) out.print("checked"); %>>
            <label for="spes">스페스(56명)</label>
            
             <input type="radio" name="facility_type" id="viliage" value="빌리지(50명)" <% if ("빌리지(50명)".equals(facilityType)) out.print("checked"); %>>
            <label for="viliage">빌리지(50명)</label>






            <label for="checkin_date">입실일</label>
            <input type="text" id="checkin_date" name="checkin_date" placeholder="입실일을 입력하세요" required value="<%= checkinDate != null ? checkinDate : "" %>">
            
            <label for="checkout_date">퇴실일</label>
            <input type="text" id="checkout_date" name="checkout_date" placeholder="퇴실일을 입력하세요" required>

            <input type="submit" value="예약">
        </form>
    </div>
    </div>
<%@include file="footer.jsp"%>
<script>
$(document).ready(function() {
	
	// URL 파라미터에서 checkin_date 값을 가져오기
    function getParameterByName(name) {
        let url = window.location.href;
        name = name.replace(/[\[\]]/g, "\\$&");
        let regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(url);
        if (!results) return null;
        if (!results[2]) return '';
        return decodeURIComponent(results[2].replace(/\+/g, " "));
    }

    let checkinDateParam = getParameterByName('checkin_date');
    if (checkinDateParam) {
        // URL 파라미터로 넘어온 checkin_date를 Date 객체로 변환
        let checkinDate = new Date(checkinDateParam);
        let minCheckoutDate = new Date(checkinDate);
        minCheckoutDate.setDate(checkinDate.getDate() + 1);

        // 퇴실일 달력의 최소 날짜 설정
        $("#checkout_date").datepicker({
            dateFormat: 'yy-mm-dd',
            minDate: minCheckoutDate
        });

        // 입실일 필드에도 해당 날짜를 설정 (필요한 경우)
        $("#checkin_date").val(checkinDateParam);
    }

    // 입실일 선택 시 이벤트 처리
    $("#checkin_date").datepicker({
        dateFormat: 'yy-mm-dd',
        onSelect: function(selectedDate) {
            let checkinDate = new Date(selectedDate);
            let minCheckoutDate = new Date(checkinDate);
            minCheckoutDate.setDate(checkinDate.getDate() + 1);

            // 퇴실일 달력의 최소 날짜 설정
            $("#checkout_date").datepicker("option", "minDate", minCheckoutDate);
            $("#checkout_date").val(""); // 이전 날짜가 남아 있을 수 있으므로 초기화
        }
    });

    // 퇴실일 달력 설정
    $("#checkout_date").datepicker({
        dateFormat: 'yy-mm-dd'
    });
    
    
    
    
    
    // 체크된 시설의 예약 가능한 날짜 가져오기
    function fetchAvailableDatesForCheckedFacility() {
        let selectedFacilityType = $("input[name='facility_type']:checked").val();
        // console.log(selectedFacilityType);

        if (selectedFacilityType) {
            // Ajax 요청으로 선택한 시설의 예약 가능한 날짜 가져오기
            $.ajax({
                url: "availableFacilityDates.do",
                method: "GET",
                data: { facility_type: selectedFacilityType },
                dataType: "json",
                success: function(data) {
                	 console.log("Ajax response data:", data); // 응답 데이터 구조 확인
                    let availableDates = data.map(item => item);
                    console.log("Available Dates: " + availableDates);

                    // Datepicker 업데이트
                    $("#checkin_date, #checkout_date").datepicker("destroy").datepicker({
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
    fetchAvailableDatesForCheckedFacility();

    // 시설 타입이 변경될 때 이벤트 발생
    $("input[name='facility_type']").change(function() {
        fetchAvailableDatesForCheckedFacility();
    });
 
});


</script>
</body>
</html>