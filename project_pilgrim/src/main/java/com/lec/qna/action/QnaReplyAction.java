package com.lec.qna.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.common.Action;
import com.lec.common.ActionForward;
import com.lec.qna.service.QnaReplyService;
import com.lec.qna.vo.QnaVO;


/*
 * public class QnaReplyAction implements Action {
 * 
 * @Override public ActionForward execute(HttpServletRequest req,
 * HttpServletResponse res) { ActionForward forward = new ActionForward(); QnaVO
 * qna = new QnaVO();
 * 
 * int p = Integer.parseInt(req.getParameter("p"));
 * 
 * qna.setBno(Integer.parseInt(req.getParameter("bno")));
 * qna.setWriter(req.getParameter("writer"));
 * qna.setPass(req.getParameter("pass"));
 * qna.setSubject(req.getParameter("subject"));
 * qna.setContent(req.getParameter("content"));
 * qna.setRe_ref(Integer.parseInt(req.getParameter("re_ref")));
 * qna.setRe_lev(Integer.parseInt(req.getParameter("re_lev")));
 * qna.setRe_seq(Integer.parseInt(req.getParameter("re_seq")));
 * 
 * QnaReplyService qnaReplyService = QnaReplyService.getInstance(); boolean
 * isReplySuccss = qnaReplyService.replyQna(qna); String msg = "";
 * 
 * if(isReplySuccss) { forward = new ActionForward(); forward.setRedirect(true);
 * forward.setPath("qnaList.qa?p=" + p); } else { try { msg = "댓글 등록 실패!!!";
 * res.setContentType("text/html; charset=utf-8"); PrintWriter out =
 * res.getWriter(); out.println("<script>"); out.println("  alert('" + msg +
 * "')"); out.println("  history.back()"); out.println("</script>"); } catch
 * (Exception e) { e.printStackTrace(); } } return forward; } }
 */
