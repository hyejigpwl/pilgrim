package com.lec.review.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.tagext.BodyContent;
import javax.sql.DataSource;

import com.lec.db.JDBCUtility;
import com.lec.review.service.ReviewFileService;
import com.lec.review.vo.ReviewFilesVO;
import com.lec.review.vo.ReviewReplyVO;
import com.lec.review.vo.ReviewVO;

public class ReviewDAO {

	// Singleton
	private static ReviewDAO reviewDao;

	private ReviewDAO() {
	}

	public static ReviewDAO getInstance() {
		if (reviewDao == null)
			reviewDao = new ReviewDAO();
		return reviewDao;
	}

	Connection conn = null;
	DataSource ds = null;

	// DB Connection
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	// 1. 글쓰기
	public int insert(ReviewVO review, List<String> files) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int insertCount = 0;

		String sql = "INSERT INTO review (member_id, date, title, content, view_count, reply_count) "
				+ "VALUES (?, NOW(), ?, ?, ?, ?)";

		try {
			// ✅ 1. 게시글 저장
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, review.getMember_id());
			pstmt.setString(2, review.getTitle());
			pstmt.setString(3, review.getContent());
			pstmt.setInt(4, review.getView_count());
			pstmt.setInt(5, review.getReply_count());

			int affectedRows = pstmt.executeUpdate();
			if (affectedRows == 0) {
				throw new Exception("게시글 등록 실패!");
			}

			// ✅ 2. 방금 삽입된 게시글의 bno 가져오기
			int bno = 0;
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				bno = rs.getInt(1);
			}

			// ✅ 3. 파일 저장 (review_files 테이블)
			if (bno > 0 && files != null && !files.isEmpty()) {
				ReviewFileService fileService = ReviewFileService.getInstance();
				fileService.saveFiles(bno, files, conn); // ✅ 파일 저장 실행
				System.out.println(files);
			}

			insertCount = affectedRows;

		} catch (Exception e) {
			System.out.println("게시글 등록 실패!!!");
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
		String sql = "select count(*) from review " + " where " + f + " like ? ";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + q + "%");
			rs = pstmt.executeQuery();

			if (rs.next())
				listCount = rs.getInt(1);
		} catch (Exception e) {
			System.out.println("게시글갯수조회실패!!! " + e.getMessage());
		} finally {
			JDBCUtility.close(conn, pstmt, rs);
		}

		return listCount;
	}

	// 3. 글목록조회하기
	public List<ReviewVO> selectReviewList(int p, int l, String f, String q) {

		ReviewVO review = null;
		List<ReviewVO> reviewList = new ArrayList<>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from review " + " where " + f + " like ?" + " order by bno desc" + " limit ?, " + l;

		int startRow = (p - 1) * l;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + q + "%");
			pstmt.setInt(2, startRow);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				review = new ReviewVO();
				review.setBno(rs.getInt("bno"));
				review.setMember_id(rs.getString("member_id"));
				review.setDate(rs.getDate("date"));
				review.setTitle(rs.getString("title"));
				review.setContent(rs.getString("content"));
				review.setView_count(rs.getInt("view_count"));
				review.setReply_count(rs.getInt("reply_count"));
				reviewList.add(review);
			}
		} catch (Exception e) {
			System.out.println("게시글목록조회실패!!! " + e.getMessage());
		} finally {
			JDBCUtility.close(null, pstmt, rs);
		}
		return reviewList;
	}

	// 4. 글조회수증가하기

	public int updateReadCount(int bno) {
		int view_count = 0;
		PreparedStatement pstmt = null;
		String sql = "update review set view_count = view_count + 1 " + " where bno = ?";
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
	public ReviewVO selectReview(int bno) {
		ReviewVO review = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM review WHERE bno = ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				review = new ReviewVO();
				review.setBno(rs.getInt("bno"));
				review.setMember_id(rs.getString("member_id"));
				review.setDate(rs.getDate("date"));
				review.setTitle(rs.getString("title"));
				review.setContent(rs.getString("content"));
				review.setView_count(rs.getInt("view_count"));
				review.setReply_count(rs.getInt("reply_count"));
			}

			// ✅ 2. 파일 정보 조회 (별도 조회 후 리턴)
			if (review != null) {
				ReviewFileService fileService = ReviewFileService.getInstance();
				List<ReviewFilesVO> fileList = fileService.getFilesByBno(bno);
			}

		} catch (Exception e) {
			System.out.println("게시글 상세보기 실패!!! " + e.getMessage());
		} finally {
			JDBCUtility.close(conn, pstmt, rs);
		}
		return review;
	}

	// 6. 글작성자확인하기
	public boolean isReviewWriter(int bno, String member_id) {

		boolean isWriter = false;

		ReviewVO review = new ReviewVO();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from review where bno = ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs = pstmt.executeQuery();
			rs.next();
			if (member_id.equals(rs.getString("member_id")))
				isWriter = true;
		} catch (Exception e) {
			System.out.println("게시글조회실패!!! " + e.getMessage());
		} finally {
			JDBCUtility.close(null, pstmt, rs);
		}
		return isWriter;
	}

	// 7. 글수정하기
	public int updateReview(ReviewVO review, List<String> files) {
		int updateCount = 0;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "update review set title = ?, content = ? " + " where bno = ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, review.getTitle());
			pstmt.setString(2, review.getContent());
			pstmt.setInt(3, review.getBno());
			
			int affectedRows = pstmt.executeUpdate();

			// ✅ 2. 방금 삽입된 게시글의 bno 가져오기
			int bno = review.getBno();

			// ✅ 3. 파일 저장 (review_files 테이블)
			if (bno > 0 && files != null && !files.isEmpty()) {
				ReviewFileService fileService = ReviewFileService.getInstance();
				fileService.modifyFiles(bno, files, conn); // ✅ 파일 저장 실행
			}

			updateCount = affectedRows;
		} catch (Exception e) {
			System.out.println("게시글 수정 실패!!! " + e.getMessage());
		} finally {
			JDBCUtility.close(null, pstmt, null);
		}

		return updateCount;
	}

	// 8. 글삭제하기
	public int deleteReview(int bno) {
		int deleteCount = 0;

		PreparedStatement pstmt = null;
		String sql = "delete from review where bno = ?";

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
	public boolean insertReply(ReviewReplyVO reply) {
		PreparedStatement pstmt = null;
		boolean isInsertSuccess = false;
		String insertReplySQL = "INSERT INTO review_reply (bno, member_id, content, date) VALUES (?, ?, ?, NOW())";
		String updateReplyCountSQL = "UPDATE review SET reply_count = (SELECT COUNT(*) FROM review_reply WHERE bno = ?) WHERE bno = ?";

		try {
			conn.setAutoCommit(false); // 트랜잭션 시작

			// 댓글 추가
			pstmt = conn.prepareStatement(insertReplySQL);
			pstmt.setInt(1, reply.getBno());
			pstmt.setString(2, reply.getMember_id());
			pstmt.setString(3, reply.getContent());
			int result = pstmt.executeUpdate();

			if (result > 0) {
				// 댓글 추가 성공 시 댓글 수 계산 후 업데이트
				pstmt = conn.prepareStatement(updateReplyCountSQL);
				pstmt.setInt(1, reply.getBno());
				pstmt.setInt(2, reply.getBno());
				int updateResult = pstmt.executeUpdate();

				if (updateResult > 0) {
					conn.commit(); // 두 작업 모두 성공 시 커밋
					isInsertSuccess = true;
				} else {
					conn.rollback(); // 댓글 수 업데이트 실패 시 롤백
				}
			} else {
				conn.rollback(); // 댓글 추가 실패 시 롤백
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback(); // 예외 발생 시 롤백
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} finally {
			JDBCUtility.close(null, pstmt, null);
			try {
				conn.setAutoCommit(true); // 자동 커밋 모드로 복구
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return isInsertSuccess;
	}

	// 10. 댓글 목록보기
	public List<ReviewReplyVO> getRepliesByBno(int bno) {
		List<ReviewReplyVO> replyList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT r.comment_id, r.member_id, r.content, r.date, m.file FROM review_reply r JOIN member m ON r.member_id = m.member_id WHERE r.bno = ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ReviewReplyVO reply = new ReviewReplyVO();
				reply.setComment_id(rs.getInt("comment_id"));
				// reply.setBno(rs.getInt("bno"));
				reply.setMember_id(rs.getString("member_id"));
				reply.setContent(rs.getString("content"));
				reply.setDate(rs.getTimestamp("date"));
				reply.setFile(rs.getString("file"));
				replyList.add(reply);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtility.close(null, pstmt, rs);
		}
		return replyList;
	}

	public boolean deleteReply(int bno, int replyId) {
		boolean isSuccess = false;
		String deleteReplySQL = "DELETE FROM review_reply WHERE comment_id = ?";
		String updateReplyCountSQL = "UPDATE review SET reply_count = (SELECT COUNT(*) FROM review_reply WHERE bno = ?) WHERE bno = ?";

		try {
			conn.setAutoCommit(false); // 트랜잭션 시작

			// 댓글 삭제
			try (PreparedStatement pstmt = conn.prepareStatement(deleteReplySQL)) {
				System.out.println("삭제 ID" + replyId);
				pstmt.setInt(1, replyId);
				int deleteCount = pstmt.executeUpdate();
				System.out.println("deleteCount: " + deleteCount); // 디버그용 로그

				if (deleteCount > 0) {
					// 댓글 삭제 성공 후 reply_count 업데이트
					try (PreparedStatement updatePstmt = conn.prepareStatement(updateReplyCountSQL)) {
						updatePstmt.setInt(1, bno);
						updatePstmt.setInt(2, bno);
						int updateCount = updatePstmt.executeUpdate();
						System.out.println("updateCount: " + updateCount); // 디버그용 로그

						if (updateCount > 0) {
							conn.commit(); // 두 작업 모두 성공 시 커밋
							isSuccess = true;
						} else {
							System.out.println("reply_count 업데이트 실패");
							conn.rollback(); // reply_count 업데이트 실패 시 롤백
						}
					}
				} else {
					System.out.println("댓글 삭제 실패");
					conn.rollback(); // 댓글 삭제 실패 시 롤백
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback(); // 예외 발생 시 롤백
				System.out.println("트랜잭션 롤백됨");
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true); // 자동 커밋 모드로 복구
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return isSuccess;
	}

}
