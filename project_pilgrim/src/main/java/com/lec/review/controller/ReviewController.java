package com.lec.review.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lec.common.Action;
import com.lec.common.ActionForward;
import com.lec.review.action.*;

@WebServlet("*.qa")
public class ReviewController extends HttpServlet {

	Action action = null;
	ActionForward forward = null;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		process(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		process(req, res);
	}

	private void process(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");
		String requestURI = req.getRequestURI();
		String contextPath = req.getContextPath();
		String command = requestURI.substring(contextPath.length() + 1, requestURI.length() - 3); // *.do

		RequestDispatcher dispatcher = null;

		forward = new ActionForward();
		// 세션에서 로그인된 사용자 ID 가져오기
		HttpSession session = req.getSession();
		String member_id = (String) session.getAttribute("member_id");

		// 목록조회/글쓰기/글수정/글삭제/댓글/에러/다운
		if (command.equalsIgnoreCase("reviewWriteForm")) {
			if (member_id == null) {
				// 로그인되지 않은 경우 로그인 페이지로 리디렉션
				forward.setRedirect(true);
				forward.setPath("login.jsp");
			} else {
				forward.setPath("/review_write.jsp");
			}
		} else if (command.equalsIgnoreCase("reviewWrite")) {
			action = new ReviewWriteAction();
			forward = action.execute(req, res);
		} else if (command.equalsIgnoreCase("reviewList")) {
			action = new ReviewListAction();
			forward = action.execute(req, res);
		} else if (command.equalsIgnoreCase("reviewDetail")) {
			action = new ReviewDetailAction();
			forward = action.execute(req, res);
		} else if (command.equalsIgnoreCase("reviewModify")) {
			action = new ReviewModifyAction();
			forward = action.execute(req, res);
		} else if (command.equalsIgnoreCase("reviewModifyForm")) {
			action = new ReviewModifyFormAction();
			forward = action.execute(req, res);
		} else if (command.equalsIgnoreCase("reviewDelete")) {
			action = new ReviewDeleteAction();
			forward = action.execute(req, res);
		} else if (command.equalsIgnoreCase("reviewReply")) {
			if (member_id == null) {
				// 로그인되지 않은 경우 로그인 페이지로 리디렉션
				forward.setRedirect(true);
				forward.setPath("login.jsp");
			} else {
				action = new ReviewReplyAction();
				forward = action.execute(req, res);
			}
		} else if (command.equalsIgnoreCase("reviewReplyDelete")) {
			action = new ReviewReplyDeleteAction();
			forward = action.execute(req, res);
		} else if (command.equalsIgnoreCase("download")) {
			forward = new ActionForward();
			forward.setPath("/review_download.jsp");
		} else if (command.equalsIgnoreCase("error")) {
			forward = new ActionForward();
			forward.setPath("/error.jsp");
		}

		if (forward != null) {
			if (forward.isRedirect()) {
				res.sendRedirect(forward.getPath());
			} else {
				dispatcher = req.getRequestDispatcher(forward.getPath());
				dispatcher.forward(req, res);
			}
		}
	}
}
