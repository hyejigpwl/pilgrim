package com.lec.review.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.common.Action;
import com.lec.common.ActionForward;
import com.lec.review.service.ReviewDetailService;
import com.lec.review.vo.ReviewFilesVO;
import com.lec.review.vo.ReviewVO;



public class ReviewModifyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) {
		
		int p = Integer.parseInt(req.getParameter("p"));
		int bno = Integer.parseInt(req.getParameter("bno"));
		
		ReviewDetailService reviewDetailService = ReviewDetailService.getInstance();
		ReviewVO review = reviewDetailService.getReview(bno);
		List<ReviewFilesVO> fileList = reviewDetailService.getFiles(bno);
		
		ActionForward forward = new ActionForward();
		req.setAttribute("review", review);
		req.setAttribute("fileList", fileList);
		forward.setPath(String.format("/review_modify.jsp?p=%d&bno=%d", p, bno));
		return forward;
	}

}
