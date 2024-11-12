package com.lec.qna.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.tagext.BodyContent;
import javax.sql.DataSource;


import com.lec.db.JDBCUtility;
import com.lec.qna.vo.QnaVO;

public class QnaDAO {

	// Singleton
	private static QnaDAO qnaDao;
	private QnaDAO() {}
	public static QnaDAO getInstance() {
		if(qnaDao == null) qnaDao = new QnaDAO();
		return qnaDao;
	}
	
	Connection conn = null;
	DataSource ds = null;
	
	// DB Connection
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	// 1. 글쓰기
	// 1. 글쓰기
	public int insert(QnaVO qna) {
	    
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String sql = "INSERT INTO qna (member_id, date, title, content, file, view_count) "
	               + "VALUES (?, NOW(), ?, ?, ?, ?)";
	    int insertCount = 0;
	    
	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, qna.getMember_id());
	        pstmt.setString(2, qna.getTitle());
	        pstmt.setString(3, qna.getContent());
	        pstmt.setString(4, qna.getFile());
	        pstmt.setInt(5, qna.getView_count());
	        
	        insertCount = pstmt.executeUpdate();
	        
	    } catch (Exception e) {
	        System.out.println("게시글 등록 실패!!!");
	        System.out.println(qna.getMember_id());
	        e.printStackTrace();
	    } finally {
	        JDBCUtility.close(null, pstmt, rs);
	    }
	    return insertCount;
	}

	
	// 2. 글갯수구하기
	public int selectListCount(String f, String q) {
		
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		String sql = "select count(*) from qna "
				   + " where " + f + " like ? ";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + q + "%");
			rs = pstmt.executeQuery();
			
			if(rs.next()) listCount = rs.getInt(1);
		} catch (Exception e) {
			System.out.println("게시글갯수조회실패!!! " + e.getMessage());
		} finally {
			JDBCUtility.close(conn, pstmt, rs);
		}
		
		return listCount;
	}	
	
	// 3. 글목록조회하기
	public List<QnaVO> selectQnaList(int p, int l, String f, String q) {
		
		QnaVO qna = null;
		List<QnaVO> qnaList = new ArrayList<>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from qna "
		           + " where " + f + " like ?"
		           + " order by bno desc"
		           + " limit ?, " + l;

		
		int startRow = (p-1) * l;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + q + "%");
			pstmt.setInt(2, startRow);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				qna = new QnaVO();
				qna.setBno(rs.getInt("bno"));
				qna.setMember_id(rs.getString("member_id"));
				qna.setDate(rs.getDate("date"));
				qna.setTitle(rs.getString("title"));
				qna.setContent(rs.getString("content"));
				qna.setFile(rs.getString("file"));
				qna.setView_count(rs.getInt("view_count"));
				qnaList.add(qna);
			}
		} catch (Exception e) {
			System.out.println("게시글목록조회실패!!! " + e.getMessage());
		} finally {
			JDBCUtility.close(null, pstmt, rs);
		}
		return qnaList;	
	}

	// 4. 글조회수증가하기
	
	public int updateReadCount(int bno) {
		int view_count = 0;
		PreparedStatement pstmt = null;
		String sql = "update qna set view_count = view_count + 1 "
				   + " where bno = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			view_count = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("게시글조회수 증가 실패!!! " + e.getMessage());
		} finally {
			JDBCUtility.close(null, pstmt, null);
		}	
		return view_count;
	}

	// 5. 글상세보기
	public QnaVO selectQna(int bno) {
		
		QnaVO qna = new QnaVO();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from qna where bno = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				qna.setBno(bno);
				qna.setBno(rs.getInt("bno"));
				qna.setMember_id(rs.getString("member_id"));
				qna.setDate(rs.getDate("date"));
				qna.setTitle(rs.getString("title"));
				qna.setContent(rs.getString("content"));
				qna.setFile(rs.getString("file"));
				qna.setView_count(rs.getInt("view_count"));
			}
		} catch (Exception e) {
			System.out.println("게시글 상세보기 실패!!! " + e.getMessage());
		} finally {
			JDBCUtility.close(conn, pstmt, rs);
		}
		return qna;
	}
	
	// 6. 글작성자확인하기
	public boolean isQnaWriter(int bno, String member_id) {
		
		boolean isWriter = false;
		
		QnaVO qna = new QnaVO();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from qna where bno = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs = pstmt.executeQuery();
			rs.next();
			if(member_id.equals(rs.getString("member_id"))) isWriter = true;
		} catch (Exception e) {
			System.out.println("게시글조회실패!!! " + e.getMessage());
		} finally {
			JDBCUtility.close(null, pstmt, rs);
		}
		return isWriter;
	}

	// 7. 글수정하기
	public int updateQna(QnaVO qna) {
		int updateCount = 0;
		
		PreparedStatement pstmt = null;
		String sql = "update qna set title = ?, content = ?, file = ? "
				   + " where bno = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, qna.getTitle());
			pstmt.setString(2, qna.getContent());
			pstmt.setString(3, qna.getFile());
			pstmt.setInt(4, qna.getBno());
			updateCount = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("게시글 수정 실패!!! " + e.getMessage());
		} finally {
			JDBCUtility.close(null, pstmt, null);
		}
		
		return updateCount;
	}
	
	// 8. 글삭제하기
	public int deleteQna(int bno) {
		int deleteCount = 0;
		
		PreparedStatement pstmt = null;
		String sql = "delete from qna where bno = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			deleteCount = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("게시글 삭제 실패!!! " + e.getMessage());
		} finally {
			JDBCUtility.close(null, pstmt, null);
		}			
		return deleteCount;
	}
	
	// 9. 댓글작성하기
	/*
	 * public int insertReplyQna(QnaVO qna) {
	 * 
	 * int insertCount = 0;
	 * 
	 * PreparedStatement pstmt = null; ResultSet rs = null; String sql =
	 * "update qna set re_seq = re_seq + 1 " + " where re_ref = ? and re_seq > ?";
	 * 
	 * int bno = 0; int re_ref = qna.getRe_ref(); int re_lev = qna.getRe_lev(); int
	 * re_seq = qna.getRe_seq();
	 * 
	 * try { pstmt = conn.prepareStatement("select max(bno) from board"); rs =
	 * pstmt.executeQuery(); if(rs.next()) bno = rs.getInt(1) + 1;
	 * 
	 * // 원글수정 pstmt = conn.prepareStatement(sql); pstmt.setInt(1, re_ref);
	 * pstmt.setInt(2, re_seq); int updateCount = pstmt.executeUpdate();
	 * if(updateCount > 0) JDBCUtility.commit(conn);
	 * 
	 * // 댓글등록 re_lev += 1; re_seq += 1; sql =
	 * "insert into board(bno, writer, pass, subject, content, file, " +
	 * " re_ref, re_lev, re_seq, readcount, crtdate) " +
	 * " values(?,?,?,?,?,?,?,?,?,?,now())";
	 * 
	 * pstmt = conn.prepareStatement(sql); pstmt.setInt(1, bno); pstmt.setString(2,
	 * qna.getWriter()); pstmt.setString(3, qna.getPass()); pstmt.setString(4,
	 * qna.getSubject()); pstmt.setString(5, qna.getContent()); pstmt.setString(6,
	 * ""); pstmt.setInt(7, re_ref); pstmt.setInt(8, re_lev); pstmt.setInt(9,
	 * re_seq); pstmt.setInt(10, 0); insertCount = pstmt.executeUpdate(); } catch
	 * (Exception e) { System.out.println("댓글등록실패!!! "+ e.getMessage()); } finally {
	 * JDBCUtility.close(null, pstmt, rs); } return insertCount; }
	 */
}
