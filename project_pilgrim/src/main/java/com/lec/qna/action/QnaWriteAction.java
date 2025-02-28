package com.lec.qna.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lec.common.Action;
import com.lec.common.ActionForward;
import com.lec.qna.service.QnaWriteService;
import com.lec.qna.vo.QnaVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class QnaWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) {
		
		// System.out.println("1. 게시글등록하기");
		ActionForward forward = null;
		QnaVO qna = null;
		String realFolder = null;
		String saveFolder = "C:/hyeji/upload";
		int fileSize = 1024*1024*5; // 5mb
		
		ServletContext context = req.getServletContext();
		realFolder = context.getRealPath("upload");
		
		MultipartRequest multi = null;
		
		HttpSession session = req.getSession();
        String member_id = (String) session.getAttribute("member_id");
		
		try {
			multi = new MultipartRequest(req, saveFolder
					, fileSize, "utf-8", new DefaultFileRenamePolicy());
			
			qna = new QnaVO();
			qna.setMember_id(member_id);
			qna.setTitle(multi.getParameter("title"));
			qna.setContent(multi.getParameter("content"));
			qna.setFile(multi.getOriginalFileName((String) multi.getFileNames().nextElement()));
			
			// 첨부파일이 여러개일 경우
			// Enumeration files = multi.getFileNames();
			// String fn = "";
			// if(files.hasMoreElements()) {
		    //		fn = (String) files.nextElement();
			//	System.out.println(multi.getOriginalFileName(fn));
			// }
			
			QnaWriteService qnaWriteService = QnaWriteService.getInstance();
		boolean isWriteSuccess = qnaWriteService.registerQna(qna);
			
			if(isWriteSuccess) {
				String msg = "게시글이 작성되었습니다.";
				res.setContentType("text/html; charset=utf-8");
				PrintWriter out;
				try {
					 out = res.getWriter();
			            out.println("<script>");
			            out.println("  alert('" + msg + "');");
			            out.println("  location.href='qnaList.qa';");
			            out.println("</script>");	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				res.setContentType("text/html; charset=utf-8");
				PrintWriter out = res.getWriter();
				out.println("<script>");
				out.println("  alert('게시글등록이 실패 했습니다!!')");
				out.println("  history.back()");
				out.println("</script>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return forward;				
	}
}
