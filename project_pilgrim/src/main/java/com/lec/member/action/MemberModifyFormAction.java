package com.lec.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.common.Action;
import com.lec.common.ActionForward;
import com.lec.member.service.MyInfoListService;
import com.lec.member.vo.MemberVO;

public class MemberModifyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) {
		
		String member_id = req.getParameter("member_id");
		String pwd = req.getParameter("pwd");
		System.out.println(member_id);
		
		MyInfoListService myInfoListService = MyInfoListService.getInstance();
		MemberVO member = myInfoListService.getMyInfoList(member_id);
		
		ActionForward forward = new ActionForward();
		req.setAttribute("member", member);
		forward.setPath(String.format("/member_modify.jsp?member_id=%s", member_id));
		return forward;
	}

}
