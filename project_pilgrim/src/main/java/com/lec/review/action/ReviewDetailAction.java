package com.lec.review.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lec.common.Action;
import com.lec.common.ActionForward;
import com.lec.member.service.MyInfoListService;
import com.lec.member.vo.MemberVO;
import com.lec.review.service.ReviewDetailService;
import com.lec.review.service.ReviewReplyService;
import com.lec.review.vo.ReviewFilesVO;
import com.lec.review.vo.ReviewReplyVO;
import com.lec.review.vo.ReviewVO;



public class ReviewDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) {
		
		// 게시글상세보기
		// select * from board where bno = 10;
		// req -> controller -> action -> service -> dao -> res
		int p = Integer.parseInt(req.getParameter("p"));
		int bno = Integer.parseInt(req.getParameter("bno"));
		
		ReviewDetailService reviewDetailService = ReviewDetailService.getInstance(); 
		ReviewVO review = reviewDetailService.getReview(bno);
		List<ReviewFilesVO> fileList = reviewDetailService.getFiles(bno);
		
		 // 작성자 ID 가져오기
        String member_id = review.getMember_id();
 
        MyInfoListService myInfoListService = MyInfoListService.getInstance();
        MemberVO member = myInfoListService.getMyInfoList(member_id);

        // JSP에 전달
        req.setAttribute("member", member);
		
		// 댓글 목록 가져오기
        ReviewReplyService replyService = ReviewReplyService.getInstance();
        List<ReviewReplyVO> replyList = replyService.getReplyList(bno);
        
        // 요청 객체에 댓글 목록 저장
        req.setAttribute("replyList", replyList);
		ActionForward forward = new ActionForward();
		req.setAttribute("review", review);	
		req.setAttribute("fileList", fileList);
		forward.setPath(String.format("/review_detail.jsp?p=%d&bno=d", p, bno));
		return forward;
	}

}
