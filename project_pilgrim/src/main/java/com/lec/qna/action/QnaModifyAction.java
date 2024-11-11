package com.lec.qna.action;

import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.common.Action;
import com.lec.common.ActionForward;
import com.lec.qna.service.QnaModifyService;
import com.lec.qna.vo.QnaVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class QnaModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) {

		ActionForward forward = null;
		QnaVO qna = null;
		String realFolder = null;
		String saveFolder = "D:/hyeji/98.temp/upload";
		int fileSize = 1024*1024*5; // 5mb
		
		ServletContext context = req.getServletContext();
		boolean isWriter = false;
		boolean isModifySuccess = false;
		
		// 파일전송
		MultipartRequest multi = null;
		
		try {
			multi = new MultipartRequest(req, saveFolder
					, fileSize, "utf-8", new DefaultFileRenamePolicy());
			
			int p = Integer.parseInt(multi.getParameter("p"));
			int bno = Integer.parseInt(multi.getParameter("bno"));
			String member_id = multi.getParameter("member_id");
			
			qna = new QnaVO();
			QnaModifyService qnaModifyService = QnaModifyService.getInstance();
			isWriter = qnaModifyService.isQnaWriter(bno, member_id);
			String msg = "";
		
			if(isWriter) {
				qna.setBno(bno);
				qna.setTitle(multi.getParameter("title"));
				qna.setContent(multi.getParameter("content"));
				qna.setFile(multi.getOriginalFileName((String)multi.getFileNames().nextElement()));
				isModifySuccess = qnaModifyService.modifyQna(qna);
				
// 				isModifySuccess = false;
				
				if(isModifySuccess) {
					forward = new ActionForward();
					forward.setRedirect(true);
					forward.setPath(String.format("qnaDetail.qa?p=%d&bno=%d", p, bno));					
				} else {
					msg = "게시글 수정 실패!!!";
					res.setContentType("text/html; charset=utf-8");
					PrintWriter out = res.getWriter();
					out.println("<script>");
					out.println("  alert('" + msg + "')");
					out.println("  history.back()");
					out.println("</script>");
					forward = new ActionForward();
					forward.setRedirect(true);
					forward.setPath("error.do?msg=" + URLEncoder.encode(msg, "utf-8"));	
				}
			} else {
				msg = "게시글을 수정할 권한이 없습니다!\n 비밀번호를 확인하세요!!";
				res.setContentType("text/html; charset=utf-8");
				PrintWriter out = res.getWriter();
				out.println("<script>");
				out.println("  alert('" + msg + "')");
				out.println("  history.back()");
				out.println("</script>");
				forward = new ActionForward();
				forward.setRedirect(true);
				forward.setPath("error.do?msg=" + URLEncoder.encode(msg, "utf-8"));				
			}		
			
		} catch (Exception e) {
			System.out.println("게시글수정실패!!!" + e.getMessage());
		} 
	
		return forward;
	}
}
