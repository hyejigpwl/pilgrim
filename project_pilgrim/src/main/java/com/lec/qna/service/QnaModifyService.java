package com.lec.qna.service;

import java.sql.Connection;


import com.lec.db.JDBCUtility;
import com.lec.qna.dao.QnaDAO;
import com.lec.qna.vo.QnaVO;

public class QnaModifyService {

	private QnaModifyService() {}
	private static QnaModifyService qnaModifyService = null;
	public static QnaModifyService getInstance() {
		if(qnaModifyService == null) qnaModifyService = new QnaModifyService();
		return qnaModifyService;
	}
	public boolean isQnaWriter(int bno, String member_id) {
		
		boolean isWriter = false;
		Connection conn = JDBCUtility.getConnection();
		QnaDAO qnaDAO = QnaDAO.getInstance();
		qnaDAO.setConnection(conn);
		isWriter = qnaDAO.isQnaWriter(bno, member_id);
		return isWriter;
	}
	
	public boolean modifyQna(QnaVO qna) {
		boolean isModifySuccess = false;
		
		Connection conn = JDBCUtility.getConnection();
		QnaDAO qnaDAO = QnaDAO.getInstance();
		qnaDAO.setConnection(conn);  	
		int updateCount = qnaDAO.updateQna(qna);
		
		if(updateCount > 0) {
			JDBCUtility.commit(conn);
			JDBCUtility.close(conn, null, null);
			isModifySuccess = true;
		} else {
			JDBCUtility.rollback(conn);
		}
			
		return isModifySuccess;
	}
}
