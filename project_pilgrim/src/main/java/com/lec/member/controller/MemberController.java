package com.lec.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.member.action.*;
import com.lec.common.Action;
import com.lec.common.ActionForward;

@WebServlet("*.mb")
public class MemberController extends HttpServlet {

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

		// 로그인, 로그아웃, 회원가입, 아이디 중복확인
		if(command.equalsIgnoreCase("loginForm")) {
			action = new LoginAction();
			forward = action.execute(req, res); 
		}else if (command.equalsIgnoreCase("logout")) {
	        action = new LogoutAction();
	        forward = action.execute(req, res);
	    }else if (command.equalsIgnoreCase("joinForm")) {
	        action = new JoinAction();
	        forward = action.execute(req, res);
	    }else if (command.equalsIgnoreCase("checkId")) { 
            action = new CheckIdAction();
            forward = action.execute(req, res);
        }else if (command.equalsIgnoreCase("myInfoList")) { 
            action = new MyInfoListAction();
            forward = action.execute(req, res);
        }
		
		// 목록조회/회원등록/회원수정/회원삭제/에러/다운
		/*if(command.equalsIgnoreCase("memberWriteForm")) {
			forward = new ActionForward();
			forward.setPath("/member/member_write.jsp");
		} else if(command.equalsIgnoreCase("memberWrite")) {
			action = new MemberWriteAction();
			forward = action.execute(req, res);
		} else if(command.equalsIgnoreCase("memberList")) {
			action = new MemberListAction();
			forward = action.execute(req, res);
		} else if(command.equalsIgnoreCase("memberDetail")) {
			action = new MemberDetailAction();
			forward = action.execute(req, res);
		} else if(command.equalsIgnoreCase("memberModifyForm")) {
			action = new MemberModifyFormAction();
			forward = action.execute(req, res);
		} else if(command.equalsIgnoreCase("memberModify")) {
			action = new MemberModifyAction();
			forward = action.execute(req, res);
		} else if(command.equalsIgnoreCase("memberDeleteForm")) {
			action = new MemberDeleteFormAction();
			forward = action.execute(req, res);
		} else if(command.equalsIgnoreCase("memberDelete")) {
			action = new MemberDeleteAction();
			forward = action.execute(req, res);
		} else if(command.equalsIgnoreCase("download")) {
			forward = new ActionForward();
			forward.setPath("/member/member_download.jsp");
		} else if(command.equalsIgnoreCase("error")) {
			forward = new ActionForward();
			forward.setPath("/member/error.jsp");
		}*/
		
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
