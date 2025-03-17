package com.lec.chat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lec.chat.vo.ChatVO;
import com.lec.db.JDBCUtility;

public class ChatDAO {
    
	public void userChatCreate(ChatVO chatVO) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    
	    try {
	        conn = JDBCUtility.getConnection();
	        if (conn == null) {
	            System.out.println("🚨 DB 연결 실패!");
	            return;
	        }

	        String sql = "INSERT INTO chat_table (member_id, sender, msg, uuid, created_at) VALUES (?, ?, ?, ?, NOW())";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, chatVO.getMember_id()); // ✅ 이미 설정된 값 사용
	        pstmt.setString(2, chatVO.getSender());
	        pstmt.setString(3, chatVO.getMsg());
	        pstmt.setString(4, chatVO.getUuid());

	        int result = pstmt.executeUpdate(); // 🔹 INSERT 실행 결과 (1이면 성공)
	        if (result > 0) {
	            System.out.println("✅ DB 저장 완료: " + chatVO.toString());
	            JDBCUtility.commit(conn);
	        } else {
	            System.out.println("🚨 DB 저장 실패: " + chatVO.toString());
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        JDBCUtility.rollback(conn);
	    } finally {
	        JDBCUtility.close(conn, pstmt, null);
	    }
	}
	
	public List<ChatVO> getChatHistoryByMemberId(String member_id) {
	    List<ChatVO> chatList = new ArrayList<>();
	    String sql = "SELECT * FROM chat_table WHERE member_id = ? ORDER BY created_at ASC"; // 시간순 정렬
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    
	    try {
	    	conn = JDBCUtility.getConnection();
	    	pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, member_id);
	        ResultSet rs = pstmt.executeQuery();
	        
	        while (rs.next()) {
	            ChatVO chat = new ChatVO();
	            chat.setMember_id(rs.getString("member_id"));
	            chat.setSender(rs.getString("sender"));
	            chat.setMsg(rs.getString("msg"));
	            chat.setUuid(rs.getString("uuid")); // 필요하면 유지
	            chatList.add(chat);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return chatList;
	}


}
