package com.lec.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.lec.db.JDBCUtility;

public class MemberDAO {

    // Singleton
    private static MemberDAO memberDao;

    private MemberDAO() {}

    public static MemberDAO getInstance() {
        if (memberDao == null)
            memberDao = new MemberDAO();
        return memberDao;
    }

    // 1. 로그인 메서드
    public String isMember(String member_id, String pwd) {
        String name = null;
        String sql = "SELECT member_id FROM member WHERE member_id = ? AND pwd = ?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // 데이터베이스 연결
            conn = JDBCUtility.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, member_id);
            pstmt.setString(2, pwd);
            
            rs = pstmt.executeQuery();

            if (rs.next()) {
                name = rs.getString("member_id");  // 결과에서 이름 가져오기
            }
            
            // 커밋하기
            JDBCUtility.commit(conn);

        } catch (Exception e) {
            e.printStackTrace();
            // 예외 발생 시 롤백
            JDBCUtility.rollback(conn);
        } finally {
            // 자원 해제
            JDBCUtility.close(conn, pstmt, rs);
        }
        
        return name;
    }
}
