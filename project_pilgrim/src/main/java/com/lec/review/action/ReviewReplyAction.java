package com.lec.review.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lec.common.Action;
import com.lec.common.ActionForward;
import com.lec.review.service.ReviewReplyService;
import com.lec.review.vo.ReviewReplyVO;

public class ReviewReplyAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest req, HttpServletResponse res) {
        ActionForward forward = null;

        // 세션에서 로그인한 사용자 ID 가져오기
        HttpSession session = req.getSession();
        String member_id = (String) session.getAttribute("member_id");

        // URL 파라미터에서 bno, p 가져오기
        int bno = 0;
        int p = 1;
        String pParam = req.getParameter("p");
        String bnoParam = req.getParameter("bno");
        System.out.println(bnoParam);
        if (bnoParam != null) {
            try {
                bno = Integer.parseInt(bnoParam);
                if (pParam != null) {
                    p = Integer.parseInt(pParam);
                }
               
            } catch (NumberFormatException e) {
                e.printStackTrace();
                sendErrorMessage(res, "잘못된 게시글 번호입니다.");
                return null;
            }
        } else {
            sendErrorMessage(res, "게시글 번호가 없습니다.");
            return null;
        }

       
        
        
        
        // 댓글 내용 가져오기
        String content = req.getParameter("content");
        if (content == null || content.trim().isEmpty()) {
            sendErrorMessage(res, "댓글 내용을 입력하세요.");
            return null;
        }

        // 댓글 객체 생성 및 데이터 설정
        ReviewReplyVO reply = new ReviewReplyVO();
        reply.setBno(bno);
        reply.setMember_id(member_id);
        reply.setContent(content);

        
        
        
        
        
        // 댓글 추가 서비스 호출
        ReviewReplyService replyService = ReviewReplyService.getInstance();
        boolean isReplySuccess = replyService.addReply(reply);

        if (isReplySuccess) {
            // 댓글이 성공적으로 추가된 경우, 게시글 상세 페이지로 이동
        	String msg = "댓글이 입력되었습니다.";
			res.setContentType("text/html; charset=utf-8");
			PrintWriter out;
			try {
				out = res.getWriter();
				out.println("<script>");
				out.println("  alert('" + msg + "');");
				out.println("  location.href='reviewDetail.qa?p=" + p + "&bno="+bno+"'");
				out.println("</script>");	
			} catch (IOException e) {
				e.printStackTrace();
			}
        } else {
            sendErrorMessage(res, "댓글 등록에 실패했습니다.");
        }

        return forward;
    }

    // 오류 메시지를 사용자에게 표시하는 메서드
    private void sendErrorMessage(HttpServletResponse res, String message) {
        res.setContentType("text/html; charset=UTF-8");
        try {
            PrintWriter out = res.getWriter();
            out.println("<script>");
            out.println("alert('" + message + "');");
            out.println("history.back();");
            out.println("</script>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
