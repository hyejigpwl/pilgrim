package com.lec.qna.service;

import java.sql.Connection;

import com.lec.db.JDBCUtility;
import com.lec.qna.dao.QnaDAO;

public class QnaReplyDeleteService {

	private QnaReplyDeleteService() {}
	private static QnaReplyDeleteService qnaDeleteService = null;
	public static QnaReplyDeleteService getInstance() {
		if(qnaDeleteService == null) qnaDeleteService = new QnaReplyDeleteService();
		return qnaDeleteService;
	}
	
	public boolean isQnaWriter(int bno, String member_id) {
		boolean isWriter = false;
		Connection conn = JDBCUtility.getConnection();
		QnaDAO qnaDAO = QnaDAO.getInstance();
		qnaDAO.setConnection(conn);
		isWriter = qnaDAO.isQnaWriter(bno, member_id);
		return isWriter;
	}

	public boolean deleteReply(int replyId) {
	    Connection conn = JDBCUtility.getConnection();
	    QnaDAO dao = QnaDAO.getInstance();
	    dao.setConnection(conn);

	    boolean isDeleteSuccess = dao.deleteReply(replyId);

	    if (isDeleteSuccess) {
	        JDBCUtility.commit(conn);
	    } else {
	        JDBCUtility.rollback(conn);
	    }

	    JDBCUtility.close(conn, null, null);
	    return isDeleteSuccess;
	}

}
