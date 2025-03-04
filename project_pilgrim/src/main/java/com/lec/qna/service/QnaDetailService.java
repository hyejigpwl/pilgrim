package com.lec.qna.service;

import java.sql.Connection;
import java.util.List;

import com.lec.db.JDBCUtility;
import com.lec.qna.dao.QnaDAO;
import com.lec.qna.vo.QnaFilesVO;
import com.lec.qna.vo.QnaVO;

public class QnaDetailService {

	private QnaDetailService() {}
	private static QnaDetailService qnaDetailService = null;
	public static QnaDetailService getInstance() {
		if(qnaDetailService == null) qnaDetailService = new QnaDetailService();
		return qnaDetailService;
	}

	public QnaVO getQna(int bno) {
		QnaVO qna = null;
		
		Connection conn = JDBCUtility.getConnection();
		QnaDAO qnaDAO = QnaDAO.getInstance();
		qnaDAO.setConnection(conn);
		int readCount = qnaDAO.updateReadCount(bno);
		if(readCount > 0) JDBCUtility.commit(conn); else JDBCUtility.rollback(conn);
		qna = qnaDAO.selectQna(bno);
		return qna;
	}
	
	 // ✅ 특정 게시글의 파일 리스트 조회
    public List<QnaFilesVO> getFiles(int bno) {
        Connection conn = JDBCUtility.getConnection();
        QnaFileService fileService = QnaFileService.getInstance();
        List<QnaFilesVO> fileList = fileService.getFilesByBno(bno);
        JDBCUtility.close(conn,null,null);
        return fileList;
    }
}
