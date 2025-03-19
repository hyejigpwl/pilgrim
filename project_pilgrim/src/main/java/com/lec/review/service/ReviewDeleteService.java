package com.lec.review.service;

import java.sql.Connection;

import com.lec.db.JDBCUtility;
import com.lec.review.dao.ReviewDAO;

public class ReviewDeleteService {

	private ReviewDeleteService() {}
	private static ReviewDeleteService reviewDeleteService = null;
	public static ReviewDeleteService getInstance() {
		if(reviewDeleteService == null) reviewDeleteService = new ReviewDeleteService();
		return reviewDeleteService;
	}
	
	public boolean isReviewWriter(int bno, String member_id) {
		boolean isWriter = false;
		Connection conn = JDBCUtility.getConnection();
		ReviewDAO reviewDAO = ReviewDAO.getInstance();
		reviewDAO.setConnection(conn);
		isWriter = reviewDAO.isReviewWriter(bno, member_id);
		return isWriter;
	}

	public boolean deleteReview(int bno) {
		
		boolean isDeleteSuccess = false;
		
		Connection conn = JDBCUtility.getConnection();
		ReviewDAO reviewDAO = ReviewDAO.getInstance();
		reviewDAO.setConnection(conn); 		
		int deleteCount = reviewDAO.deleteReview(bno);
		
		if(deleteCount > 0) {
			JDBCUtility.commit(conn);
			JDBCUtility.close(conn, null, null);
			isDeleteSuccess = true;
		} else {
			JDBCUtility.rollback(conn);
		}
		return isDeleteSuccess;
	}

}
