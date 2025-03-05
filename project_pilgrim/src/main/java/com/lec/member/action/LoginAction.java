package com.lec.member.action;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lec.member.dao.MemberDAO;
import com.lec.member.service.MemberLoginService;
import com.lec.common.Action;
import com.lec.common.ActionForward;

public class LoginAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) {
	    String member_id = req.getParameter("member_id");
	    String pwd = req.getParameter("pwd");
	    String checkbox = req.getParameter("checkbox"); // 체크박스 체크 여부 확인

	    ActionForward forward = new ActionForward();
	    MemberLoginService loginService = new MemberLoginService();
	    String name = loginService.login(member_id, pwd);

	    // 쿠키 생성 및 저장
	    Cookie cookie = new Cookie("userId", member_id);
	    if (checkbox != null) { // 체크박스가 체크된 경우
	        cookie.setMaxAge(60 * 60 * 24 * 7); // 🔥 쿠키 유효기간 7일 설정
	        res.addCookie(cookie);
	    } else { // 체크박스 체크 해제 시
	        cookie.setMaxAge(0); // 즉시 삭제
	        res.addCookie(cookie);
	    }

	    if (name != null) {  // 로그인 성공
	        HttpSession session = req.getSession();
	        session.setAttribute("member", "ok");
	        session.setAttribute("pwd", pwd);
	        session.setAttribute("member_id", member_id);
	        session.setAttribute("name", name);

	        // 이전 페이지로 이동하거나 기본 페이지 설정
	        String previousPage = (String) session.getAttribute("previousPage");
	        forward.setPath(previousPage != null ? previousPage : "my_page.jsp");
	        forward.setRedirect(true); // 리디렉트 방식 사용
	    } else {  // 로그인 실패
	        req.setAttribute("errorMessage", "로그인 정보가 올바르지 않습니다.");
	        forward.setPath("login.jsp");
	        forward.setRedirect(false); // 포워드 방식 사용
	    }
	    return forward;
	}

}
