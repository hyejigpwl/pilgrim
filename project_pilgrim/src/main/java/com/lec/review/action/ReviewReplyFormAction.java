package com.lec.review.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.common.Action;
import com.lec.common.ActionForward;
import com.lec.review.service.ReviewDetailService;
import com.lec.review.vo.ReviewVO;



public class ReviewReplyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) {
		ActionForward forward = new ActionForward();
		
		int p = Integer.parseInt(req.getParameter("p"));
		int bno = Integer.parseInt(req.getParameter("bno"));
		
		ReviewDetailService qnaDetailService = ReviewDetailService.getInstance();
		ReviewVO qna = qnaDetailService.getReview(bno);		
		
		req.setAttribute("qna", qna);
		forward.setPath(String.format("/project_pilgrim/review_reply.jsp?p=%d&bno=%d", p, bno));
		return forward;
	}

}
