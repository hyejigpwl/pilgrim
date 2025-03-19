package com.lec.review.service;

import java.sql.Connection;
import java.util.List;

import com.lec.db.JDBCUtility;
import com.lec.review.dao.ReviewDAO;
import com.lec.review.vo.ReviewVO;

public class ReviewModifyService {

	private ReviewModifyService() {}
	private static ReviewModifyService reviewModifyService = null;
	public static ReviewModifyService getInstance() {
		if(reviewModifyService == null) reviewModifyService = new ReviewModifyService();
		return reviewModifyService;
	}
	public boolean isReviewWriter(int bno, String member_id) {
		
		boolean isWriter = false;
		Connection conn = JDBCUtility.getConnection();
		ReviewDAO reviewDAO = ReviewDAO.getInstance();
		reviewDAO.setConnection(conn);
		isWriter = reviewDAO.isReviewWriter(bno, member_id);
		return isWriter;
	}
	
	public boolean modifyReview(ReviewVO review, List<String> files) {
	    boolean isModifySuccess = false;
	    Connection conn = null;

	    try {
	        conn = JDBCUtility.getConnection();
	        ReviewDAO reviewDAO = ReviewDAO.getInstance();
	        reviewDAO.setConnection(conn);

	        int updateCount = reviewDAO.updateReview(review, files);

	        if (updateCount > 0) {
	            JDBCUtility.commit(conn);
	            isModifySuccess = true;
	        } else {
	            JDBCUtility.rollback(conn);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        JDBCUtility.close(conn, null, null);
	    }

	    return isModifySuccess;
	}

}
