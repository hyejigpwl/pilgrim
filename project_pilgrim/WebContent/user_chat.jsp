<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예약 취소 문의</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<style>
.chatArea {
	/*display:none;*/
	margin:0 auto;
	width:100%;
	max-width: 300px;
	height: 400px;
	border: 1px solid #ccc;
	padding: 10px;
	/*overflow-y: auto;*/
}

textarea {
	width: 100%;
	height: 300px;
	resize: none;
}

.hidden {
	display: none;
}
</style>
</head>
<body>
	<form>
		<div class="chatArea">
			<textarea id="messageTextArea" readonly></textarea>
			<input id="textMessage" type="text" placeholder="메시지를 입력하세요..."
				onkeydown="return enter(event)"> <input type="button"
				value="Send" onclick="sendMessage()"> <!-- <input type="button"
				value="Close" onclick="closeChat()"> -->
		</div>

		<!-- 채팅 아이콘 -->
		<!-- <button id="chatIcon">채팅 열기</button> -->

		<!-- 로그인된 사용자 ID (세션에서 가져오기) -->
		<input type="hidden" id="sessionUserId"
			value="<%=session.getAttribute("member_id")%>">

	</form>
	<script>
	// ✅ WebSocket URL 설정
	// var url = "ws://3.107.192.1:8080/project_pilgrim/userchat";
	var webSocket = null;
	var messageTextArea = document.getElementById("messageTextArea");
	var sessionUserId = document.querySelector("#sessionUserId").value;
	var chatIcon = document.querySelector("#chatIcon");
	var chatArea = document.querySelector(".chatArea");
	var uuid = null;
	var url = "ws://localhost:8080/project_pilgrim/userchat?member_id=" + encodeURIComponent(sessionUserId);

	
	connectWebSocket(); // ✅ WebSocket 연결

	// ✅ WebSocket 연결 함수 (중복 연결 방지)
	function connectWebSocket() {
	    if (webSocket !== null && (webSocket.readyState === WebSocket.OPEN || webSocket.readyState === WebSocket.CONNECTING)) {
	        console.log("⚠️ 이미 WebSocket이 연결되어 있음.");
	        return; // ✅ 중복 연결 방지
	    }

	    webSocket = new WebSocket(url);

	    webSocket.onopen = function () {
	    	console.log("✅ WebSocket 서버 연결됨.");

	        // ✅ 서버에 로그인된 사용자 ID(sessionUserId) 전송
	        if (sessionUserId) {
	            webSocket.send(sessionUserId);
	            console.log("📩 사용자 ID 전송: " + sessionUserId);
	        } else {
	            console.log("🚨 사용자 ID가 없음!");
	        }
	    };

	    webSocket.onclose = function (event) {
	        console.log("⚠️ WebSocket 연결 종료됨. 코드: " + event.code + ", 이유: " + event.reason);
	        webSocket = null; // ✅ WebSocket 객체 초기화
	    };

	    webSocket.onerror = function (error) {
	        console.error("🚨 WebSocket 오류 발생:", error);
	    };

	    webSocket.onmessage = function (message) {
	        console.log("📩 WebSocket 메시지 수신:", message.data);

	        if (message.data.includes("uuid:")) {
	            uuid = message.data.split(":")[1]; // ✅ UUID 저장
	        } else {
                messageTextArea.value += message.data + "\n";
            }
	        
	    };

	}

	// ✅ 메시지 전송 함수
	function sendMessage() {
	    let message = document.getElementById("textMessage").value.trim();

	    if (message === "") {
	        alert("메시지를 입력하세요.");
	        return;
	    }

	    // ✅ 메시지를 WebSocket을 통해 전송
	    webSocket.send(sessionUserId + "," + message);

	    // ✅ 메시지를 채팅창에 출력
	    messageTextArea.value += "(나) : " + message + "\n";
	    document.getElementById("textMessage").value = ""; // 입력창 초기화
	}

	// ✅ 채팅 종료 함수
	function closeChat() {
	    if (confirm("종료 시 채팅 로그는 삭제됩니다.")) {
	        messageTextArea.value += "이용해 주셔서 감사합니다.";
	        // chatArea.style.display = "none"; // ✅ 채팅창 숨기기
	        if (webSocket) {
	            webSocket.close(); // ✅ WebSocket 닫기
	            webSocket = null; // ✅ WebSocket 초기화
	        }
	    }
	}

	// ✅ Enter 키로 메시지 전송
	function enter(event) {
	    if (event.keyCode === 13) {
	        sendMessage();
	        return false;
	    }
	    return true;
	}
</script>

</body>

</html>