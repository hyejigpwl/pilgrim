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
    // ✅ WebSocket 메시지 수신 처리
webSocket.onmessage = function (message) {
    let node;
    try {
        node = JSON.parse(message.data);
    } catch (e) {
        console.error("🚨 JSON 파싱 오류:", message.data);
        return;
    }
    
    console.log(node);

    if (node.status === "history") { 
        // ✅ 채팅 기록 표시
        displayUserMessage(node.member_id, node.message, node.sender);
    } else if (node.status === "visit") { 
        // ✅ 유저 입장 시 채팅창 생성
        addUserChat(node.member_id);
    } else if (node.status === "message") { 
        // ✅ 새로운 메시지 표시
        displayUserMessage(node.member_id, "(" + node.member_id + ") : " + node.message, node.sender);
    } else if (node.status === "bye") { 
        // ✅ 유저 퇴장 처리
        removeUserChat(node.member_id);
    }
};

// ✅ 기존 채팅 기록 및 새로운 메시지 출력
function displayUserMessage(member_id, message, sender) {
    let $chatBox = $("[data-id='" + member_id + "']");
    
    if ($chatBox.length === 0) {
        addUserChat(member_id); // 채팅창이 없으면 생성
        $chatBox = $("[data-id='" + member_id + "']");
    }

    let log = $chatBox.find(".messageArea").val();
    let prefix = sender === "user" ? "(" + member_id + ") : " : "";
    $chatBox.find(".messageArea").val(log + prefix + message + "\n");
}




    // 유저 채팅창 추가
   function addUserChat(member_id) {
    if ($("[data-id='" + member_id + "']").length > 0) {
        return; // ✅ 이미 존재하는 채팅창이면 추가 안 함
    }

    let chatBox =
        "<div class='chatBox' data-id='" + member_id + "'>" +
            "<h4>사용자 (" + member_id + ")</h4>" +
            "<textarea class='messageArea' readonly></textarea><br>" +
            "<input type='text' class='message' placeholder='메시지 입력...' onkeydown='return enter(event, \"" + member_id + "\")'>" +
            "<button class='sendBtn' onclick='sendMessage(\"" + member_id + "\")'>보내기</button>" +
        "</div>";

    $("#chatList").append(chatBox);
}


    // 유저 채팅창 제거
    function removeUserChat(member_id) {
    	// $("[data-id ='" + member_id + "']").remove();
    }

    // 메시지 전송
    function sendMessage(member_id) {
        let $chatBox = $("[data-id ='" + member_id + "']");
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
        webSocket.send(sessionUserId + "," + message);
        
    }

    // Enter 키 입력 시 메시지 전송
    function enter(event, member_id) {
        if (event.keyCode === 13) {
            sendMessage(member_id);
            return false;
        }
        return true;
    }
</script>

</body>
</html>
