package com.lec.qna.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lec.common.Action;
import com.lec.common.ActionForward;
import com.lec.member.service.MyInfoListService;
import com.lec.member.vo.MemberVO;
import com.lec.qna.service.QnaDetailService;
import com.lec.qna.service.QnaReplyService;
import com.lec.qna.vo.QnaFilesVO;
import com.lec.qna.vo.QnaReplyVO;
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
		List<QnaFilesVO> fileList = qnaDetailService.getFiles(bno);
		
		 // 작성자 ID 가져오기
        String member_id = qna.getMember_id();
 
        MyInfoListService myInfoListService = MyInfoListService.getInstance();
        MemberVO member = myInfoListService.getMyInfoList(member_id);

        // JSP에 전달
        req.setAttribute("member", member);
		
		// 댓글 목록 가져오기
        QnaReplyService replyService = QnaReplyService.getInstance();
        List<QnaReplyVO> replyList = replyService.getReplyList(bno);
        
        // 요청 객체에 댓글 목록 저장
        req.setAttribute("replyList", replyList);
		ActionForward forward = new ActionForward();
		req.setAttribute("qna", qna);	
		req.setAttribute("fileList", fileList);
		forward.setPath(String.format("/qna_detail.jsp?p=%d&bno=d", p, bno));
		return forward;
	}

}
