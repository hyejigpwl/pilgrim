package com.lec.qna.service;

import java.sql.Connection;

import com.lec.db.JDBCUtility;
import com.lec.qna.dao.QnaDAO;

public class QnaDeleteService {

	private QnaDeleteService() {}
	private static QnaDeleteService qnaDeleteService = null;
	public static QnaDeleteService getInstance() {
		if(qnaDeleteService == null) qnaDeleteService = new QnaDeleteService();
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

	public boolean deleteQna(int bno) {
		
		boolean isDeleteSuccess = false;
		
		Connection conn = JDBCUtility.getConnection();
		QnaDAO qnaDAO = QnaDAO.getInstance();
		qnaDAO.setConnection(conn); 		
		int deleteCount = qnaDAO.deleteQna(bno);
		
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
