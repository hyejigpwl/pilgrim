package com.lec.chat.socket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.lec.chat.dao.ChatDAO;
import com.lec.chat.vo.ChatVO;

@ServerEndpoint("/userchat")
public class UserSocket {

	//searchUser 함수의 filter 표현식을 위한 인터페이스
	private interface SearchExpression {
		//람다식을 위한 함수
		boolean expression(User user);
	}
	//서버와 유저간의 접속을 key로 구분하기 위한 이너 클래스
	public class User {
		Session session;
		String key;
		String member_id;
	}
	
	//유저와 서버간의 접속 리스트 -> 동기화처리
	private static List<User> sessionUsers = Collections.synchronizedList(new ArrayList<>());
	//리스트에서 탐색(session)
	private static User getUser(Session session) {
		return searchUser(x -> x.session == session);
	}
	
	//리스트에서 탐색(key)
	static User getUser(String key) {
		return searchUser(x -> x.key.equals(key));
	}
	
	//리스트에서 탐색(member_id)
	static User getUserById(String member_id) {
		return searchUser(x -> x.member_id.equals(member_id));
	}
	
	// 현재 접속중인 User들의 member_id
	public static List<String> getConnectedUserIds() {
	    List<String> memberIds = new ArrayList<>();
	    for (User user : sessionUsers) {
	        if (user.member_id != null) {
	            memberIds.add(user.member_id);
	        }
	    }
	    return memberIds;
	}

	
	
	// ✅ member_id로 해당 유저의 WebSocket key 찾기
	public static String getUserKeyById(String member_id) {
	    for (User user : sessionUsers) {
	        if (user.member_id != null && user.member_id.equals(member_id)) { 
	            return user.key; // ✅ member_id가 일치하는 유저의 key 반환
	        }
	    }
	    return null; // ❌ 존재하지 않으면 null 반환
	}
	
	public static String getUserIdByKey(String key) {
	    User user = getUser(key);
    return (user != null && user.member_id != null) ? user.member_id : "Unknown";
	}

	

	
	//접속 리스트 탐색
	private static User searchUser(SearchExpression func) {
		Optional<User> op = sessionUsers.stream().filter(x -> func.expression(x)).findFirst();		
		if(op.isPresent()) {
			return op.get();
		}
		return null;
	}
	
	@OnOpen
	public void handleOpen(Session userSession) throws IOException {    
	    // ✅ WebSocket 요청에서 member_id 추출
	    String query = userSession.getQueryString();
	    Optional<String> optionalMemberId = Optional.empty();

	    if (query != null && query.contains("member_id=")) {
	    	String[] pair = query.split("=");
	    	optionalMemberId = Optional.of(pair[1]);
	    }
	    
	    final String member_id = optionalMemberId.orElse(null);

	    User user;
	    
        // ✅ 새로운 사용자면 새로운 User 객체 생성 (uuid 새로 생성)
        user = new User();
        user.key = UUID.randomUUID().toString().replace("-", "");
        user.session = userSession;
        user.member_id = member_id;
        sessionUsers.add(user);
        System.out.println("✅ 새 사용자 접속 - member_id: " + user.member_id + ", uuid: " + user.key);
	    

	    user.session.getBasicRemote().sendText("uuid:" + user.key);
	    // AdminSocket.visit(user.member_id);
	    

	    // ✅ WebSocket 연결 시 이전 채팅 기록 전송
	    if (user.member_id != null) {
	        ChatDAO chatDao = new ChatDAO();
	        List<ChatVO> chatHistory = chatDao.getChatHistoryByMemberId(user.member_id);

	        for (ChatVO chat : chatHistory) {
	            String formattedMessage = (chat.getSender().equals("user") ? "(나) : " : "") + chat.getMsg();
	            
	            user.session.getBasicRemote().sendText(formattedMessage);
	            AdminSocket.history(user.member_id, chat.getSender(), chat.getMsg());
	        }
	    }
	}


	
	//JS에서 전달받을 때
	@OnMessage
	public void handleMessage(String message, Session userSession) throws IOException {
	    User user = getUser(userSession);
	    
	    if (user != null) {
	        // 🔹 메시지 형식: "member_id, message_content"
	        String[] splitMessage = message.split(",", 2); 
	        
	        // 클라이언트에서 `sessionUserId + "," + message.value` 형태로 보냈기 때문에 분리
	        if (splitMessage.length < 2) {
	            System.out.println("🚨 잘못된 메시지 형식: " + splitMessage);
	            return;
	        }
	        
	        String member_id = splitMessage[0]; // 사용자 ID
	        String msg = splitMessage[1]; // 메시지 내용

	        System.out.println("🔹 메시지 수신 - 사용자: " + member_id + " | 내용: " + msg);
	        
	        // ✅ 처음 메시지를 받으면 member_id를 저장
            if (user.member_id == null) {
                user.member_id = member_id;
                System.out.println("✅ 사용자 ID 저장 완료: " + member_id);
            }
            
	        // 🔹 관리자에게 메시지 전달
	        AdminSocket.sendMessage(user.member_id, msg, user.key); // ✅ 메시지만 전달하도록 수정
	        
	        // 🔹 메시지를 DB에 저장
	        ChatDAO chatDao = new ChatDAO();
	        ChatVO chatVO = new ChatVO();
	        chatVO.setMember_id(member_id); // JS에서 전달된 사용자 ID
	        chatVO.setSender("user"); // 보낸 사람 (기본값: "user")
	        chatVO.setMsg(msg); // 메시지 내용
	        chatVO.setUuid(user.key); // WebSocket 세션의 UUID (변경 가능)

	        chatDao.userChatCreate(chatVO);
	        System.out.println("✅ 메시지 DB 저장 완료: " + chatVO.toString());
	    }
	}


	
	//종료 시
	@OnClose
	public void handleClose(Session userSession) {
	    User user = getUser(userSession);
	    if (user != null) {
	    	 System.out.println("🛑 WebSocket 세션 종료 (Key: " + user.key + ", ID: " + user.member_id + ")");
	        AdminSocket.bye(user.member_id); // ✅ 관리자에게 유저 퇴장 알림
	        sessionUsers.remove(user); // ✅ 세션 리스트에서 제거
	        try {
	            if (user.session.isOpen()) { // ✅ 닫힌 세션이 아니면 닫기
	                user.session.close();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
	


	
	//운영자 -> user 메세지
	public static void sendMessage(String member_id, String message) {
	    User user = getUserById(member_id);
	    if (user != null) {
	        try {
	            if (user.session.isOpen()) { // ✅ WebSocket 세션이 열려 있는지 확인
	                user.session.getBasicRemote().sendText(message);
	            } else {
	                System.out.println("⚠️ WebSocket 세션이 이미 닫혀 있음, 메시지 전송 불가: " + member_id);
	            }
	        } catch (IOException e) {
	            System.err.println("🚨 WebSocket 메시지 전송 오류 (Broken pipe 가능성): " + e.getMessage());
	            e.printStackTrace();
	        }
	    } else {
	        System.out.println("⚠️ WebSocket 세션을 찾을 수 없음: " + member_id);
	    }
	}

	
	//유저 UK get -> admin에 보낼용도
	public static String[] getUserKeys() {
		String[] ret = new String[sessionUsers.size()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = sessionUsers.get(i).key;
		
		}
		return ret;
	}
	
	  // ✅ WebSocket 오류 핸들링 추가
	@OnError
	public void handleError(Session session, Throwable throwable) {
	    System.err.println("🚨 WebSocket 오류 발생: " + throwable.getMessage());
	    throwable.printStackTrace();

	    try {
	        if (session.isOpen()) {
	            session.close(); // ✅ 오류 발생 시 안전하게 세션 닫기
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

    
    
}
