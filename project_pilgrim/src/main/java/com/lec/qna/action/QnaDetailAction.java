package com.lec.qna.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.common.Action;
import com.lec.common.ActionForward;
import com.lec.qna.service.QnaDetailService;
import com.lec.qna.vo.QnaVO;



public class QnaDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) {
		
		// 게시글상세보기
		// select * from board where bno = 10;
		// req -> controller -> action -> service -> dao -> res
		int p = Integer.parseInt(req.getParameter("p"));
		int bno = Integer.parseInt(req.getParameter("bno"));
		
		QnaDetailService qnaDetailService = QnaDetailService.getInstance(); 
		QnaVO qna = qnaDetailService.getQna(bno);
		
		ActionForward forward = new ActionForward();
		req.setAttribute("qna", qna);	
		forward.setPath(String.format("/qna_detail.jsp?p=%d&bno=d", p, bno));
		return forward;
	}

}
