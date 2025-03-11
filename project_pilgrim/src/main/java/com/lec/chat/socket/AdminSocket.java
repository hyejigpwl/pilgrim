package com.lec.chat.socket;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.lec.chat.dao.ChatDAO;
import com.lec.chat.socket.UserSocket.User;
import com.lec.chat.vo.ChatVO;

@ServerEndpoint("/adminchat")
public class AdminSocket {
	//admin 한명 (둘 이상의 세션에서 접속을 하면 마지막 세션만 작동) -> 추가 가능
	private static Session admin = null;
	
	//접속
	@OnOpen
	public void handleOpen(Session userSession) throws IOException {
		//운영자 유저의 세션을 바꾼다.
		admin = userSession;
		//기존에 접속해 있는 유저의 정보를 운영자 client로 보낸다.
		for(String key : UserSocket.getUserKeys()) {
			//전송
			visit(key);
		}
	}
	//메세지 보낼 때
	@OnMessage
	public void handleMessage(String message, Session userSession) throws IOException {
	    // ✅ 메시지 형식 확인
	    if (message == null || !message.contains(",")) {
	        System.out.println("🚨 잘못된 메시지 형식: " + message);
	        return; // 데이터가 올바르지 않으면 처리하지 않음
	    }

	    String[] split = message.split(",", 2);

	    // ✅ 예외 방지: splitMessage 길이가 2가 안되면 오류 발생 가능
	    if (split.length < 2) {
	        System.out.println("🚨 메시지 분할 실패: " + message);
	        return; // 메시지가 잘못된 경우 종료
	    }

	    String key = split[0].trim(); 
	    String msg = split[1].trim();       // ✅ 메시지 내용
	    String member_id = UserSocket.getUserIdByKey(key);


	    if (key == null) {
	        System.out.println("🚨 해당 사용자 [" + key + "]의 WebSocket 세션을 찾을 수 없음.");
	        return;
	    }

	    System.out.println("📩 메시지 수신 | 사용자: " + key + " | 내용: " + msg);

	    // ✅ 유저에게 메시지 전송
	    UserSocket.sendMessage(key, msg);

	    // ✅ 메시지를 DB에 저장
	    ChatDAO chatDao = new ChatDAO();
	    ChatVO chatVO = new ChatVO();
	    chatVO.setMember_id(member_id);
	    chatVO.setSender("admin"); 
	    chatVO.setMsg(msg);
	    chatVO.setUuid(key);

	    chatDao.userChatCreate(chatVO);
	    System.out.println("✅ 메시지 DB 저장 완료: " + chatVO.toString());
	}

	
	//접속종료
	@OnClose
	public void handleClose(Session userSession) {
		admin = null;
	}
	
	//admin view로
	private static void send(String message) {
		if (admin != null) {
			try {
				admin.getBasicRemote().sendText(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//아래의 조건들로 방생성, 방종료, 메세지전송
	//유저 입장
	public static void visit(String key) {		
		send("{\"status\":\"visit\", \"key\":\"" + key + "\"}");
	}
	
	// ✅ 유저 메시지 보낼 때 member_id 추가
	public static void sendMessage(String key, String message) {
	    User user = UserSocket.getUser(key); // 해당 유저 정보 가져오기
	    String member_id = (user != null && user.member_id != null) ? user.member_id : "Unknown"; // member_id 가져오기

	    send("{\"status\":\"message\", \"key\":\"" + key + "\", \"message\":\"" + message + "\", \"member_id\":\"" + member_id + "\"}");
	}


	
	//유저 나감
	public static void bye(String key) {
		send("{\"status\":\"bye\", \"key\":\"" + key + "\"}");
	}
}

