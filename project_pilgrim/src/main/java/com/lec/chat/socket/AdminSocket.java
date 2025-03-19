package com.lec.chat.socket;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
	// admin 한명 (둘 이상의 세션에서 접속을 하면 마지막 세션만 작동) -> 추가 가능
	private static Session admin = null;

	// 접속
	@OnOpen
	public void handleOpen(Session userSession) throws IOException {
	    admin = userSession;

	    // ✅ 현재 접속 중인 모든 유저의 `member_id` 가져오기
	    List<String> connectedUsers = UserSocket.getConnectedUserIds();

	    for (String member_id : connectedUsers) {
	        // ✅ 해당 유저의 모든 채팅 내역 가져옴
	        ChatDAO chatDao = new ChatDAO();
	        List<ChatVO> chatHistory = chatDao.getChatHistoryByMemberId(member_id);

	        // ✅ 각 채팅 메시지를 개별적으로 전송
	        for (ChatVO chat : chatHistory) {
	            history(chat.getMember_id(), chat.getSender(), chat.getMsg());
	        }

	        // ✅ 유저 입장 상태 전송
	        visit(member_id);
	        
	    }
	}


	// 메세지 보낼 때
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

		String member_id = split[0].trim();
		String msg = split[1].trim();

		if (member_id == null) {
			System.out.println("🚨 해당 사용자 [" + member_id + "]의 WebSocket 세션을 찾을 수 없음.");
			return;
		}

		System.out.println("📩 메시지 수신 | 사용자: " + member_id + " | 내용: " + msg);

		// ✅ 유저에게 메시지 전송
		UserSocket.sendMessage(member_id, msg);

		// ✅ 메시지를 DB에 저장
		ChatDAO chatDao = new ChatDAO();
		ChatVO chatVO = new ChatVO();
		chatVO.setMember_id(member_id);
		chatVO.setSender("admin");
		chatVO.setMsg(msg);
		chatVO.setUuid("");

		chatDao.userChatCreate(chatVO);
		System.out.println("✅ 메시지 DB 저장 완료: " + chatVO.toString());
	}

	// 접속종료
	@OnClose
	public void handleClose(Session userSession) {
		admin = null;
	}

	// admin view로
	private static void send(String message) {
		if (admin != null) {
			try {
				admin.getBasicRemote().sendText(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 아래의 조건들로 방생성, 방종료, 메세지전송
	// 유저 입장
	public static void visit(String member_id) {
		send("{\"status\":\"visit\", \"member_id\":\"" + member_id + "\"}");
	}
	
	public static void history(String member_id, String sender, String message) {
	    send("{\"status\":\"history\", \"member_id\":\"" + member_id + 
	         "\", \"message\":\"" + message + 
	         "\", \"sender\":\"" + sender + "\"}");
	}

	// ✅ 유저 메시지 보낼 때 member_id 추가
	public static void sendMessage(String member_id, String message, String sender) {
		User user = UserSocket.getUserById(member_id); // 해당 유저 정보 가져오기
		String memberId = (user != null && user.member_id != null) ? user.member_id : "Unknown"; // member_id 가져오기
		
		send("{\"status\":\"message\", \"member_id\":\"" + memberId + "\", \"message\":\"" + message + "\", \"sender\":\""
				+ sender + "\"}");
	}

	// 유저 나감
	public static void bye(String member_id) {
		send("{\"status\":\"bye\", \"member_id\":\"" + member_id + "\"}");
	}
}
