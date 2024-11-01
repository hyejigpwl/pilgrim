package com.lec.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lec.member.vo.ActionForward;

public class LogoutAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest req, HttpServletResponse res) {
        HttpSession session = req.getSession();
        
        // 세션 무효화 (로그아웃 처리)
        session.invalidate();
        
        // 로그인 페이지로 리다이렉트 설정
        ActionForward forward = new ActionForward();
        forward.setPath("login.jsp");
        forward.setRedirect(true); // 리다이렉트 방식으로 이동
        return forward;
    }
}
