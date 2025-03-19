package com.lec.review.service;

import java.sql.Connection;

import com.lec.db.JDBCUtility;
import com.lec.review.dao.ReviewDAO;

public class ReviewReplyDeleteService {

	private ReviewReplyDeleteService() {}
	private static ReviewReplyDeleteService qnaDeleteService = null;
	public static ReviewReplyDeleteService getInstance() {
		if(qnaDeleteService == null) qnaDeleteService = new ReviewReplyDeleteService();
		return qnaDeleteService;
	}
	
	public boolean isQnaWriter(int bno, String member_id) {
		boolean isWriter = false;
		Connection conn = JDBCUtility.getConnection();
		ReviewDAO qnaDAO = ReviewDAO.getInstance();
		qnaDAO.setConnection(conn);
		isWriter = qnaDAO.isReviewWriter(bno, member_id);
		return isWriter;
	}

	public boolean deleteReply(int bno, int replyId) {
	    Connection conn = JDBCUtility.getConnection();
	    ReviewDAO dao = ReviewDAO.getInstance();
	    dao.setConnection(conn);

	    boolean isDeleteSuccess = dao.deleteReply(bno, replyId);

	    if (isDeleteSuccess) {
	        JDBCUtility.commit(conn);
	    } else {
	        JDBCUtility.rollback(conn);
	    }

	    JDBCUtility.close(conn, null, null);
	    return isDeleteSuccess;
	}

}
