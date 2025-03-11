package com.lec.chat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

}
