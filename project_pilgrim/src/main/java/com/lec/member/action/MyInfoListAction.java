package com.lec.member.action;

import javax.servlet.http.*;
import com.lec.member.service.MyInfoListService;
import com.lec.member.vo.MemberVO;
import com.lec.common.Action;
import com.lec.common.ActionForward;

public class MyInfoListAction implements Action {
    @Override
    public ActionForward execute(HttpServletRequest req, HttpServletResponse res) {
        
        // 세션에서 로그인된 사용자 ID 가져오기
        HttpSession session = req.getSession();
        String member_id = (String) session.getAttribute("member_id");
        
        if (member_id == null) {
            // 로그인되지 않은 경우 로그인 페이지로 리디렉션
            ActionForward forward = new ActionForward();
            forward.setRedirect(true);
            forward.setPath("login.jsp");
            return forward;
        }

        // 내 정보 가져오기
        MyInfoListService myInfoListService = MyInfoListService.getInstance();
        MemberVO member = myInfoListService.getMyInfoList(member_id);

        // JSP에 전달
        req.setAttribute("member", member);

        // 페이지 포워딩 설정
        ActionForward forward = new ActionForward();
        forward.setPath("/my_info.jsp");
        return forward;
    }
}
