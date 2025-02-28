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
<title>객실 예약 페이지</title>
</head>
<body>
<%
		if(session.getAttribute("member")==null)
		{
			response.sendRedirect("login.jsp");
		}

		String roomType = request.getParameter("room_type");
		String checkinDate = request.getParameter("checkin_date");
	%>
<%@include file="header.jsp"%>
<div id="container" class="login_page room_rqt_page container">
         <div class="login-wrapper">
        <h2>객실 예약</h2>
        <form method="post" action="roomForm.do" id="login-form">
        
        	<p class="seminar_time_title">룸 타입</p>
            <input type="radio" name="room_type" id="2인온돌" value="2인온돌" <% if ("2인온돌".equals(roomType)) out.print("checked"); %> data-price="70000">
            <label for="2인온돌">2인온돌</label>
            
            
            <input type="radio" name="room_type" id="2인침대"  value="2인침대" <% if ("2인침대".equals(roomType)) out.print("checked"); %> data-price="80000">
            <label for="2인침대">2인침대</label>
            
                  <input type="radio" name="room_type" id="4인침대"  value="4인침대" <% if ("4인침대".equals(roomType)) out.print("checked"); %> data-price="150000">
            <label for="4인침대">4인침대</label>
            
                  <input type="radio" name="room_type" id="VIP룸"  value="VIP룸" <% if ("VIP룸".equals(roomType)) out.print("checked"); %> data-price="150000">
            <label for="VIP룸">VIP룸(2인)</label>
            
                  <input type="radio" name="room_type" id="빌리지가족실"  value="빌리지가족실" <% if ("빌리지가족실".equals(roomType)) out.print("checked"); %> data-price="150000">
            <label for="빌리지가족실">빌리지가족실(6인)</label>
            
            <label for="guest_count">인원</label>
            <input type="number" id="guest_count" name="guest_count" placeholder="인원 수를 입력하세요" required>
            
            <label for="checkin_date">입실일</label>
            <input type="text" id="checkin_date" name="checkin_date" placeholder="입실일을 입력하세요" required value="<%= checkinDate != null ? checkinDate : "" %>">

			 <label for="checkout_date">퇴실일</label>
            <input type="text" id="checkout_date" name="checkout_date" placeholder="퇴실일을 입력하세요" required>
			
			
            
            <!-- <label for="guest_count">총 금액</label>
            <input type="text" id="guest_count" name="guest_count" placeholder="인원 수를 입력하세요" disabled> -->
            
           

            <input type="submit" value="예약">
        </form>
    </div>
    </div>
<%@include file="footer.jsp"%>
<script>
//총 금액 계산 함수
/* function calculateTotalPrice() {
    // 각 요소 가져오기
    const checkinDate = new Date(document.getElementById('checkin_date').value);
    const checkoutDate = new Date(document.getElementById('checkout_date').value);
    const roomType = document.querySelector('input[name="room_type"]:checked');
    
    if (!checkinDate || !checkoutDate || !roomType) {
        document.getElementById('total_price').value = ''; // 총 금액 초기화
        return;
    }
    
    // 하루당 가격 가져오기 (각 room_type의 가격은 데이터 속성으로 설정)
    const roomPrice = parseInt(roomType.dataset.price, 10);
    
    // 날짜 차이 계산 (퇴실일 - 입실일)
    const dayDifference = (checkoutDate - checkinDate) / (1000 * 60 * 60 * 24);
    
    if (dayDifference > 0) {
        const totalPrice = roomPrice * dayDifference;
        document.getElementById('total_price').value = totalPrice.toLocaleString() + ' 원'; // 천 단위 표시
    } else {
        document.getElementById('total_price').value = ''; // 잘못된 날짜 입력 시 초기화
        alert('입실일과 퇴실일을 올바르게 선택해주세요.');
    }
}

// 이벤트 리스너 추가
document.getElementById('checkin_date').addEventListener('change', calculateTotalPrice);
document.getElementById('checkout_date').addEventListener('change', calculateTotalPrice);
document.querySelectorAll('input[name="room_type"]').forEach(room => {
    room.addEventListener('change', calculateTotalPrice);
});
 */
 $(document).ready(function() {
		
		
	    // 체크된 시설의 예약 가능한 날짜 가져오기
	    function fetchAvailableDatesForCheckedRoom() {
	        let selectedRoomType = $("input[name='room_type']:checked").val();
	        // console.log(selectedFacilityType);

	        if (selectedRoomType) {
	            // Ajax 요청으로 선택한 시설의 예약 가능한 날짜 가져오기
	            $.ajax({
	                url: "availableRoomDates.do",
	                method: "GET",
	                data: { room_type: selectedRoomType },
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
	    fetchAvailableDatesForCheckedRoom();

	    // 시설 타입이 변경될 때 이벤트 발생
	    $("input[name='room_type']").change(function() {
	    	fetchAvailableDatesForCheckedRoom();
	    });
	    
	    
	    
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
	        let checkinDate = new Date(checkinDateParam);
	        let minCheckoutDate = new Date(checkinDate);
	        minCheckoutDate.setDate(checkinDate.getDate() + 1);

	        $("#checkout_date").datepicker("option", "minDate", minCheckoutDate);
	        $("#checkin_date").val(checkinDateParam);
	    }

	    // 입실일 선택 시 이벤트 처리
	    $("#checkin_date").datepicker({
	        dateFormat: 'yy-mm-dd',
	        onSelect: function(selectedDate) {
	            let checkinDate = new Date(selectedDate);
	            let minCheckoutDate = new Date(checkinDate);
	            minCheckoutDate.setDate(checkinDate.getDate() + 1);

	            $("#checkout_date").datepicker("option", "minDate", minCheckoutDate);
	            $("#checkout_date").val(""); // 이전 날짜가 남아 있을 수 있으므로 초기화
	        }
	    });

	    // 퇴실일 달력 설정
	    $("#checkout_date").datepicker({
	        dateFormat: 'yy-mm-dd'
	    });
	 
	});

 
 
</script>
</body>
</html>