package com.lec.qna.service;

import java.sql.Connection;


import com.lec.db.JDBCUtility;
import com.lec.qna.dao.QnaDAO;
import com.lec.qna.vo.QnaVO;

public class QnaWriteService {

	private QnaWriteService() {}
	private static QnaWriteService qnaWriteService = null;
	public static QnaWriteService getInstance() {
		if(qnaWriteService == null) qnaWriteService = new QnaWriteService();
		return qnaWriteService;
	}
	
	public boolean registerQna(QnaVO qna) {
		
		boolean isWriteSuccess = false;
			
		Connection conn = JDBCUtility.getConnection();
		QnaDAO qnaDAO = QnaDAO.getInstance();
		qnaDAO.setConnection(conn);
		int insertCount = qnaDAO.insert(qna);
		
		if(insertCount > 0) {
			JDBCUtility.commit(conn);
			JDBCUtility.close(conn, null, null);
			isWriteSuccess = true;
		} else {
			JDBCUtility.rollback(conn);
		}
		return isWriteSuccess;
	}
}
