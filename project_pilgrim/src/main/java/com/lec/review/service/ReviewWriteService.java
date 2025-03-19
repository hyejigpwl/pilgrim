package com.lec.review.service;

import java.sql.Connection;
import java.util.List;

import com.lec.db.JDBCUtility;
import com.lec.review.dao.ReviewDAO;
import com.lec.review.vo.ReviewVO;

public class ReviewWriteService {

	private ReviewWriteService() {}
	private static ReviewWriteService reviewWriteService = null;

	public static ReviewWriteService getInstance() {
		if (reviewWriteService == null) {
			reviewWriteService = new ReviewWriteService();
		}
		return reviewWriteService;
	}

	public boolean registerReview(ReviewVO review, List<String> files) {
		boolean isWriteSuccess = false;
		Connection conn = null;

		try {
			conn = JDBCUtility.getConnection();
			ReviewDAO reviewDAO = ReviewDAO.getInstance();
			reviewDAO.setConnection(conn);
			int insertCount = reviewDAO.insert(review, files);

			if (insertCount > 0) {
				JDBCUtility.commit(conn);
				isWriteSuccess = true;
			} else {
				JDBCUtility.rollback(conn);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// ✅ 트랜잭션 성공/실패 여부와 관계없이 연결을 닫음
			JDBCUtility.close(conn, null, null);
		}

		return isWriteSuccess;
	}
}
