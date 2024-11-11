package com.lec.qna.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.common.Action;
import com.lec.common.ActionForward;
import com.lec.qna.action.*;


@WebServlet("*.qa")
public class QnaController extends HttpServlet {

	Action action = null;
	ActionForward forward = null;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		process(req, res);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		process(req, res);
	}

	private void process(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
	
		req.setCharacterEncoding("utf-8");
		String requestURI = req.getRequestURI();
		String contextPath = req.getContextPath(); 
		String command = requestURI.substring(contextPath.length()+1, requestURI.length()-3); // *.do
		
		RequestDispatcher dispatcher = null;

		// 목록조회/글쓰기/글수정/글삭제/댓글/에러/다운
		if(command.equalsIgnoreCase("qnaWriteForm")) {
			forward = new ActionForward();
			forward.setPath("/qna_write.jsp");
		} else if(command.equalsIgnoreCase("qnaWrite")) {
			action = new QnaWriteAction();
			forward = action.execute(req, res);
		} else if(command.equalsIgnoreCase("qnaList")) {
			action = new QnaListAction();
			forward = action.execute(req, res);
		} else if(command.equalsIgnoreCase("qnaDetail")) {
			action = new QnaDetailAction();
			forward = action.execute(req, res);
		} else if(command.equalsIgnoreCase("qnaModifyForm")) {
			action = new QnaModifyFormAction();
			forward = action.execute(req, res);
		} else if(command.equalsIgnoreCase("qnaModify")) {
			action = new QnaModifyAction();
			forward = action.execute(req, res);
		} else if(command.equalsIgnoreCase("qnaDeleteForm")) {
			action = new QnaDeleteFormAction();
			forward = action.execute(req, res);
		} else if(command.equalsIgnoreCase("qnaDelete")) {
			action = new QnaDeleteAction();
			forward = action.execute(req, res);
		} else if(command.equalsIgnoreCase("qnaReplyForm")) {
			action = new QnaReplyFormAction();
			forward = action.execute(req, res);
		} /*
			 * else if(command.equalsIgnoreCase("boardReply")) { action = new
			 * QnaReplyAction(); forward = action.execute(req, res); }
			 */else if(command.equalsIgnoreCase("download")) {
			forward = new ActionForward();
			forward.setPath("/qna_download.jsp");
		} else if(command.equalsIgnoreCase("error")) {
			forward = new ActionForward();
			forward.setPath("/error.jsp");
		}
		
		if(forward != null) {
			if(forward.isRedirect()) {
				res.sendRedirect(forward.getPath());
			} else {
				dispatcher = req.getRequestDispatcher(forward.getPath());
				dispatcher.forward(req, res);
			}
		}
	}	
}
