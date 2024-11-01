package com.lec.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import com.lec.db.JDBCUtility;
import com.lec.member.vo.MemberVO;

public class MemberDAO {

    // Singleton
    private static MemberDAO memberDao;

    private MemberDAO() {}

    public static MemberDAO getInstance() {
        if (memberDao == null)
            memberDao = new MemberDAO();
        return memberDao;
    }
    
    Connection conn = null;
	DataSource ds = null;

	// DB Connection
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

    // 1. 로그인 메서드
    public String isMember(String member_id, String pwd) {
    	PreparedStatement pstmt = null;
		ResultSet rs = null;
        String name = null;
        String sql = "SELECT member_id FROM member WHERE member_id = ? AND pwd = ?";
        

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
    
 // 2. 회원등록
 	public int insert(MemberVO member) {
 		PreparedStatement pstmt = null;
 		String sql = "insert into member(member_id, name, pwd, phone, email, reg_date, terms_agreed) "
 				+ " values(?,?,?,?,?,now(),?)";
 		int insertCount = 0;

 		try {
 		// 데이터베이스 연결
            conn = JDBCUtility.getConnection();
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, member.getMember_id());
            pstmt.setString(2, member.getName());
 			pstmt.setString(3, member.getPwd());
 			pstmt.setString(4, member.getPhone());
 			pstmt.setString(5, member.getEmail());
 			pstmt.setInt(6, member.getTerms_agreed());
 			insertCount = pstmt.executeUpdate();
 			
 		} catch (Exception e) {
 			System.out.println("회원등록실패!!!");
 		} finally {
 			JDBCUtility.close(null, pstmt, null);
 		}
 		return insertCount;
 	}

}
