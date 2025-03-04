package com.lec.qna.service;

import java.sql.Connection;
import java.util.List;

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
	
	public boolean modifyQna(QnaVO qna, List<String> files) {
	    boolean isModifySuccess = false;
	    Connection conn = null;

	    try {
	        conn = JDBCUtility.getConnection();
	        QnaDAO qnaDAO = QnaDAO.getInstance();
	        qnaDAO.setConnection(conn);

	        int updateCount = qnaDAO.updateQna(qna, files);

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
