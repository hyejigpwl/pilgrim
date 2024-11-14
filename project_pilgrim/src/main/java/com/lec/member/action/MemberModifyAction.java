package com.lec.member.action;

import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lec.member.service.MemberModifyService;
import com.lec.common.Action;
import com.lec.common.ActionForward;
import com.lec.member.vo.MemberVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class MemberModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) {

		ActionForward forward = null;
		MemberVO member = null;
		/*
		 * String realFolder = null; String saveFolder = "D:/hyeji/98.temp/upload"; int
		 * fileSize = 1024*1024*5; // 5mb
		 */		
		ServletContext context = req.getServletContext();
		// boolean isWriter = false;
		boolean isModifySuccess = false;
		
		// 파일전송
		// MultipartRequest multi = null;
		
		 HttpSession session = req.getSession();
	        String member_id_bf = (String) session.getAttribute("member_id");
	        System.out.println(member_id_bf);
		try {
			/*
			 * multi = new MultipartRequest(req, saveFolder , fileSize, "utf-8", new
			 * DefaultFileRenamePolicy());
			 */
			
			String member_id = req.getParameter("member_id");
			System.out.println("member_id : " + member_id);
			// String pwd = req.getParameter("pwd");
			
			member = new MemberVO();
			MemberModifyService memberModifyService = MemberModifyService.getInstance();
			// isWriter = memberModifyService.isMemberWriter(member_id, pwd);
			String msg = "";
		
			
			member.setMember_id(req.getParameter("member_id"));
			member.setPwd(req.getParameter("pwd"));
			member.setName(req.getParameter("name"));
			member.setPhone(req.getParameter("phone"));
			member.setEmail(req.getParameter("email"));
			/*
			 * member.setFile(multi.getOriginalFileName((String)multi.getFileNames().
			 * nextElement()));
			 */
			isModifySuccess = memberModifyService.modifyMember(member,member_id_bf);
			
	// 				isModifySuccess = false;
			
			if (isModifySuccess) {
			    // 세션의 member_id 값을 업데이트된 member_id로 설정
			    session.setAttribute("member_id", member.getMember_id());

			    msg = "회원 정보가 수정되었습니다.";
			    res.setContentType("text/html; charset=utf-8");
			    PrintWriter out = res.getWriter();
			    out.println("<script>");
			    out.println("  alert('" + msg + "');");
			    out.println("  location.href='" + String.format("myInfoList.mb?member_id=%s", member.getMember_id()) + "';");
			    out.println("</script>");
			    out.close(); // PrintWriter를 닫아주는 것이 좋습니다.

			    return null; // 이미 response를 통해 이동하므로 forward 객체는 반환할 필요가 없습니다.
			} else {
			    msg = "회원 수정 실패!!!";
			    res.setContentType("text/html; charset=utf-8");
			    PrintWriter out = res.getWriter();
			    out.println("<script>");
			    out.println("  alert('" + msg + "');");
			    out.println("  history.back();");
			    out.println("</script>");
			    out.close();
			    
			    return null;
			}


		
			
		} catch (Exception e) {
			System.out.println("회원정보수정실패!!!" + e.getMessage());
		} 
	
		return forward;
	}
}
