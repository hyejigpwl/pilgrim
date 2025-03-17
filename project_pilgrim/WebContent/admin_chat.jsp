<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>관리자 채팅</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<style>
.chatContainer {
	width: 100%;
	max-width: 800px;
	margin: 20px auto;
}

.chatBox {
	border: 1px solid #ccc;
	padding: 10px;
	margin-bottom: 10px;
}

.messageArea {
	width: 100%;
	height: 200px;
	resize: none;
	background: #f9f9f9;
	border: 1px solid #ccc;
	padding: 5px;
}
</style>
</head>
<body>

	<div class="chatContainer">
		<h2>관리자 채팅</h2>
		<div id="chatList"></div>
	</div>
	<!-- 로그인된 사용자 ID (세션에서 가져오기) -->
	<input type="hidden" id="sessionUserId"
		value="<%=session.getAttribute("member_id")%>">

	<!-- WebSocket을 통한 실시간 채팅 -->
	<script>
	var sessionUserId = document.querySelector("#sessionUserId").value;
    // var webSocket = new WebSocket("ws://3.107.192.1:8080/project_pilgrim/adminchat");
    var webSocket = new WebSocket(`ws://localhost:8080/project_pilgrim/adminchat?member_id=${sessionUserId}`);

    webSocket.onopen = function () {
        console.log("관리자 채팅 서버에 연결됨.");
    };

    webSocket.onclose = function () {
        console.log("연결 종료됨.");
    };

    webSocket.onerror = function (error) {
        console.error("WebSocket 오류 발생:", error);
    };

    // 서버에서 메시지 수신 처리
    webSocket.onmessage = function (message) {
	    let node = JSON.parse(message.data);
	    console.log(node);
	
	    if (node.status === "visit") { // 유저 입장
	        addUserChat(node.key, node.member_id);
	    } else if (node.status === "message") { // 유저 메시지 도착
	        displayUserMessage(node.key, node.message, node.member_id);
	    } else if (node.status === "bye") { // 유저 퇴장
	        removeUserChat(node.key);
	    }
	};


    // 유저 채팅창 추가
    function addUserChat(key, member_id) {
    	let chatBox =
            "<div class='chatBox' data-key='" + key + "'>" +
                "<h4>사용자 (" + member_id+ ")</h4>" +
                "<textarea class='messageArea' readonly></textarea><br>" +
                "<input type='text' class='message' placeholder='메시지 입력...' onkeydown='return enter(event, \"" + key +"\")'>" +
                "<button class='sendBtn' onclick='sendMessage(\"" + key + "\")'>보내기</button>" +
            "</div>";

        $("#chatList").append(chatBox);
    }

    // 메시지 표시
	function displayUserMessage(key, message, member_id) {
	    let $chatBox = $("[data-key ='" + key + "']");
	    let log = $chatBox.find(".messageArea").val();
	    $chatBox.find(".messageArea").val(log + "(" + member_id+ ") : " + message + "\n");
	}

    // 유저 채팅창 제거
    function removeUserChat(key) {
    	$("[data-key ='" + key + "']").remove();
    }

    // 메시지 전송
    function sendMessage(key) {
        let $chatBox = $("[data-key ='" + key + "']");
        let message = $chatBox.find(".message").val();

        if (message === "") {
            alert("메시지를 입력하세요.");
            return;
        }

        let log = $chatBox.find(".messageArea").val();
        $chatBox.find(".messageArea").val(log + "(관리자) : " + message + "\n");
        $chatBox.find(".message").val("");
        message = "(관리자) : " + message;

        // ✅ WebSocket을 통해 유저의 key와 함께 메시지 전송
        webSocket.send(sessionUserId + "," + message+ "," + key);
        
    }

    // Enter 키 입력 시 메시지 전송
    function enter(event, key) {
        if (event.keyCode === 13) {
            sendMessage(key);
            return false;
        }
        return true;
    }
</script>

</body>
</html>
