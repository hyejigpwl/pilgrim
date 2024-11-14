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
            JDBCUtility.close(null, pstmt, rs);
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
            // conn = JDBCUtility.getConnection();
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, member.getMember_id());
            pstmt.setString(2, member.getName());
 			pstmt.setString(3, member.getPwd());
 			pstmt.setString(4, member.getPhone());
 			pstmt.setString(5, member.getEmail());
 			pstmt.setInt(6, member.getTerms_agreed());
 			insertCount = pstmt.executeUpdate();
 			
 			System.out.println("member_id: " + member.getMember_id());
 			System.out.println("name: " + member.getName());
 			System.out.println("pwd: " + member.getPwd());
 			System.out.println("phone: " + member.getPhone());
 			System.out.println("email: " + member.getEmail());
 			System.out.println("file: " + member.getFile()); // file이 제대로 null인지 확인
 			System.out.println("terms_agreed: " + member.getTerms_agreed());

 			
 		} catch (Exception e) {
 			System.out.println("회원등록실패!!!");
 			insertCount=0;
 		} finally {
 			JDBCUtility.close(null, pstmt, null);
 		}
 		return insertCount;
 	}

 	// 3. 아이디 중복확인
 	public boolean isIdAvailable(String member_id) {
 	    PreparedStatement pstmt = null;
 	    ResultSet rs = null;
 	    String sql = "SELECT COUNT(*) FROM member WHERE member_id = ?";
 	    boolean isAvailable = false;

 	    try {
 	    	conn = JDBCUtility.getConnection();
 	        pstmt = conn.prepareStatement(sql);
 	        pstmt.setString(1, member_id);
 	        rs = pstmt.executeQuery();
 	        if (rs.next()) {
 	            isAvailable = rs.getInt(1) == 0; // COUNT가 0이면 사용 가능
 	        }
 	    } catch (Exception e) {
 	        e.printStackTrace();
 	    } finally {
 	        JDBCUtility.close(null, pstmt, rs);
 	    }
 	    return isAvailable;
 	}
 	
 	
 	// 4. 내정보 상세보기
public MemberVO getMyInfo(String member_id) {
		
		MemberVO member = new MemberVO();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from member where member_id = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member_id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				member.setMember_id(member_id);
				member.setName(rs.getString("name"));
				member.setPwd(rs.getString("pwd"));
				member.setPhone(rs.getString("phone"));
				member.setEmail(rs.getString("email"));
				member.setFile(rs.getString("file"));
			}
		} catch (Exception e) {
			System.out.println("회원정보 상세보기 실패!!! " + e.getMessage());
		} finally {
			JDBCUtility.close(conn, pstmt, rs);
		}
		return member;
	}


	// 5. 내정보 수정하기
	public int updateMember(MemberVO member, String member_id_bf) {
		int updateCount = 0;

		PreparedStatement pstmt = null;
		String sql = "update member set member_id=?, pwd = ?, name=?, phone=?, email=?, file=? " + " where member_id=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getMember_id());
			pstmt.setString(2, member.getPwd());
			pstmt.setString(3, member.getName());
			pstmt.setString(4, member.getPhone());
			pstmt.setString(5, member.getEmail());
			pstmt.setString(6, member.getFile());
			pstmt.setString(7, member_id_bf);
			updateCount = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("회원 수정 실패!!! " + e.getMessage());
		} finally {
			JDBCUtility.close(null, pstmt, null);
		}

		return updateCount;
	}
	
	// 6. 글작성자확인하기
		public boolean isMemberWriter(String member_id, String pwd) {

			boolean isWriter = false;

			MemberVO member = new MemberVO();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = "select * from member where member_id = ?";

			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, member_id);
				rs = pstmt.executeQuery();
				rs.next();
				if (pwd.equals(rs.getString("pwd")))
					isWriter = true;
			} catch (Exception e) {
				System.out.println("회원조회실패!!! " + e.getMessage());
			} finally {
				JDBCUtility.close(null, pstmt, rs);
			}
			return isWriter;
		}
 	
}
