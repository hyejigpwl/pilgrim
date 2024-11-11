package com.lec.qna.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


import com.lec.db.JDBCUtility;
import com.lec.qna.dao.QnaDAO;
import com.lec.qna.vo.QnaVO;

public class QnaListService {

	private  QnaListService() {}
	private static QnaListService qnaListService = null;
	public static QnaListService getInstance() {
		if(qnaListService == null) qnaListService = new QnaListService();
		return qnaListService;
	}
	public int getListCount(String f, String q) {
		// 글전체갯수 : select count(*) from board where subject like '%%';		
		int listCount = 0;
		Connection conn = JDBCUtility.getConnection();
		QnaDAO qnaDAO = QnaDAO.getInstance();		
		qnaDAO.setConnection(conn);		
		listCount = qnaDAO.selectListCount(f, q);	
		return listCount;
	}
	
	public List<QnaVO> getQnaList(int p, int l, String f, String q) {
		List<QnaVO> qnaList = new ArrayList<QnaVO>();
		Connection conn = JDBCUtility.getConnection();
		QnaDAO qnaDAO = QnaDAO.getInstance();		
		qnaDAO.setConnection(conn);
		qnaList = qnaDAO.selectQnaList(p, l, f, q);	
		return qnaList;
	}
}
