package com.lec.qna.service;

import java.sql.Connection;


import com.lec.db.JDBCUtility;
import com.lec.qna.dao.QnaDAO;
import com.lec.qna.vo.QnaVO;

public class QnaReplyService {

	private QnaReplyService() {}
	private static QnaReplyService qnaReplyService = null;
	public static QnaReplyService getInstance() {
		if(qnaReplyService == null) qnaReplyService = new QnaReplyService();
		return qnaReplyService;
	}
	
	/*
	 * public boolean replyQna(QnaVO qna) {
	 * 
	 * boolean isReplySuccss = false; Connection conn = JDBCUtility.getConnection();
	 * QnaDAO qnaDAO = QnaDAO.getInstance(); qnaDAO.setConnection(conn); int
	 * insertCount = qnaDAO.insertReplyQna(qna);
	 * 
	 * if(insertCount > 0) { JDBCUtility.commit(conn); JDBCUtility.close(conn, null,
	 * null); isReplySuccss = true; } else { JDBCUtility.rollback(conn); } return
	 * isReplySuccss; }
	 */
}
