package com.lec.qna.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.common.Action;
import com.lec.common.ActionForward;
import com.lec.qna.service.QnaDetailService;
import com.lec.qna.vo.QnaVO;



public class QnaReplyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) {
		ActionForward forward = new ActionForward();
		
		int p = Integer.parseInt(req.getParameter("p"));
		int bno = Integer.parseInt(req.getParameter("bno"));
		
		QnaDetailService qnaDetailService = QnaDetailService.getInstance();
		QnaVO qna = qnaDetailService.getQna(bno);		
		
		req.setAttribute("qna", qna);
		forward.setPath(String.format("/project_pilgrim/qna_reply.jsp?p=%d&bno=%d", p, bno));
		return forward;
	}

}
