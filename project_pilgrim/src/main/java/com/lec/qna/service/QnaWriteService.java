package com.lec.qna.service;

import java.sql.Connection;
import java.util.List;

import com.lec.db.JDBCUtility;
import com.lec.qna.dao.QnaDAO;
import com.lec.qna.vo.QnaVO;

public class QnaWriteService {

	private QnaWriteService() {}
	private static QnaWriteService qnaWriteService = null;

	public static QnaWriteService getInstance() {
		if (qnaWriteService == null) {
			qnaWriteService = new QnaWriteService();
		}
		return qnaWriteService;
	}

	public boolean registerQna(QnaVO qna, List<String> files) {
		boolean isWriteSuccess = false;
		Connection conn = null;

		try {
			conn = JDBCUtility.getConnection();
			QnaDAO qnaDAO = QnaDAO.getInstance();
			qnaDAO.setConnection(conn);
			int insertCount = qnaDAO.insert(qna, files);

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
