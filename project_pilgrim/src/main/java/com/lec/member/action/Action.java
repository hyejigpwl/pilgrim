package com.lec.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.member.vo.ActionForward;

public interface Action {

	public ActionForward execute(HttpServletRequest req, HttpServletResponse res);
}
