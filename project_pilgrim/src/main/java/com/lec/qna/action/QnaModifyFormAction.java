package com.lec.qna.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.common.Action;
import com.lec.common.ActionForward;
import com.lec.qna.service.QnaDetailService;
import com.lec.qna.vo.QnaFilesVO;
import com.lec.qna.vo.QnaVO;



public class QnaModifyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) {
		
		int p = Integer.parseInt(req.getParameter("p"));
		int bno = Integer.parseInt(req.getParameter("bno"));
		
		QnaDetailService qnaDetailService = QnaDetailService.getInstance();
		QnaVO qna = qnaDetailService.getQna(bno);
		List<QnaFilesVO> fileList = qnaDetailService.getFiles(bno);
		
		ActionForward forward = new ActionForward();
		req.setAttribute("qna", qna);
		req.setAttribute("fileList", fileList);
		forward.setPath(String.format("/qna_modify.jsp?p=%d&bno=%d", p, bno));
		return forward;
	}

}
