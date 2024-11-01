package com.lec.member.action;

import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.member.service.MemberRegistService;
import com.lec.member.vo.ActionForward;
import com.lec.member.vo.MemberVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class JoinAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest req, HttpServletResponse res) {
        
        ActionForward forward = null;
        MemberVO member = new MemberVO();
        
        // 파일 업로드 관련 설정
        // String saveFolder = "D:/hyeji/98.temp/upload"; // 파일 저장 폴더
        // int fileSize = 1024 * 1024 * 5; // 5MB 제한
        
        // MultipartRequest multi = null;
        
        try {
            // MultipartRequest를 통해 파일 업로드 처리
            // multi = new MultipartRequest(req, saveFolder, fileSize, "utf-8", new DefaultFileRenamePolicy());
            
            // 회원 정보 설정
            member.setMember_id(req.getParameter("member_id")); // 아이디
            member.setPwd(req.getParameter("pwd"));            // 비밀번호
            member.setName(req.getParameter("name"));          // 이름
            member.setPhone(req.getParameter("phone"));        // 전화번호
            member.setEmail(req.getParameter("email"));        // 이메일
            member.setTerms_agreed(req.getParameter("terms_agreed") != null ? 1 : 0); // 약관동의

            // 파일 업로드 처리
            //if (multi.getFile("profile_image") != null) {
            //    String uploadFileName = multi.getOriginalFileName("profile_image");
            //    member.setFile(uploadFileName); // 업로드된 파일 이름 설정
            //}

            // 회원 등록 서비스 호출
            MemberRegistService memberRegistService =MemberRegistService.getInstance();
            boolean isWriteSuccess = memberRegistService.registerMember(member);
            
            // 회원 등록 성공 시 마이페이지 리디렉션
            if (isWriteSuccess) {
            	// 세션에 로그인 상태 저장
                req.getSession().setAttribute("member", "ok");
                req.getSession().setAttribute("member_id", member.getMember_id());
                req.getSession().setAttribute("pwd", member.getPwd());
                req.getSession().setAttribute("name", member.getName());
                
                forward = new ActionForward();
                forward.setRedirect(true);
                forward.setPath("my_page.jsp");
            } else { // 실패 시 알림 메시지 표시
                res.setContentType("text/html; charset=utf-8");
                PrintWriter out = res.getWriter();
                out.println("<script>");
                out.println("  alert('회원 등록에 실패했습니다!');");
                out.println("  history.back();");
                out.println("</script>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return forward;                
    }
}
