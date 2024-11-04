package com.lec.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.common.ActionForward;

public interface Action {

	public ActionForward execute(HttpServletRequest req, HttpServletResponse res);
}
