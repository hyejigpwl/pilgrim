package com.lec.member.action;

import java.io.IOException;

import javax.servlet.http.*;

import com.lec.member.dao.MemberDAO;
import com.lec.member.vo.ActionForward;

public class CheckIdAction implements Action {
    @Override
    public ActionForward execute(HttpServletRequest req, HttpServletResponse res){
        String member_id = req.getParameter("member_id");
        MemberDAO memberDAO = MemberDAO.getInstance();
        
        // 아이디 중복 여부 확인
        boolean isAvailable = memberDAO.isIdAvailable(member_id);

        // "true" 또는 "false" 문자열로 응답
        res.setContentType("text/plain");
        res.setCharacterEncoding("UTF-8");
        try {
			res.getWriter().write(Boolean.toString(isAvailable));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // 페이지 이동이 필요 없으므로 null 반환
        return null;
        
    }
}
