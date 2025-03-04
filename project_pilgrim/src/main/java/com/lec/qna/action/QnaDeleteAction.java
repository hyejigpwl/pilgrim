package com.lec.qna.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lec.qna.service.QnaDeleteService;
import com.lec.qna.service.QnaFileService;
import com.lec.common.Action;
import com.lec.common.ActionForward;

public class QnaDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) {
		
		ActionForward forward = null;
		boolean isWriter = false;
		boolean isDeleteSuccess = false;
		
		HttpSession session = req.getSession();
        String member_id = (String) session.getAttribute("member_id");
        
        // 관리자 여부 확인
        boolean isAdmin = "관리자".equals(member_id);
		
		int p = Integer.parseInt(req.getParameter("p"));
		int bno = Integer.parseInt(req.getParameter("bno"));
		
		QnaDeleteService qnaDeleteService = QnaDeleteService.getInstance();
		isWriter = qnaDeleteService.isQnaWriter(bno, member_id);
		
		String msg = "";
		
		// 작성자이거나 관리자인 경우 삭제 가능
		if(isWriter || isAdmin) {
			isDeleteSuccess = qnaDeleteService.deleteQna(bno);
			
			if(isDeleteSuccess) {
				msg = "게시글이 삭제되었습니다.";
				res.setContentType("text/html; charset=utf-8");
				PrintWriter out;
				try {
					out = res.getWriter();
					out.println("<script>");
					out.println("  alert('" + msg + "');");
					out.println("  location.href='qnaList.qa?p=" + p + "';");
					out.println("</script>");	
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					msg = "게시글 삭제에 실패했습니다.";
					res.setContentType("text/html; charset=utf-8");
					PrintWriter out = res.getWriter();
					out.println("<script>");
					out.println("  alert('" + msg + "');");
					out.println("  history.back();");
					out.println("</script>");					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			// 권한이 없는 경우
			try {
				msg = "게시글을 삭제할 권한이 없습니다!!!";
				res.setContentType("text/html; charset=utf-8");
				PrintWriter out = res.getWriter();
				out.println("<script>");
				out.println("  alert('" + msg + "')");
				out.println("  history.back()");
				out.println("</script>");					
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		return forward;
	}
}
