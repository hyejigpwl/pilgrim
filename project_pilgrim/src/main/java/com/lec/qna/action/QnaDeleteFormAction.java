package com.lec.qna.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.common.Action;
import com.lec.common.ActionForward;
import com.lec.qna.service.QnaDetailService;
import com.lec.qna.vo.QnaVO;



public class QnaDeleteFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) {

		int p = Integer.parseInt(req.getParameter("p"));
		int bno = Integer.parseInt(req.getParameter("bno"));
		
		QnaDetailService qnaDetailService = QnaDetailService.getInstance();
		QnaVO board = qnaDetailService.getQna(bno);
		
		ActionForward forward = new ActionForward();
		req.setAttribute("board", board);
		forward.setPath(String.format("/project_pilgrim/qna_delete.jsp?p=%d&bno=%d", p, bno));
		return forward;
	}

}
