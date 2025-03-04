package com.lec.member.action;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Enumeration;

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
        String realFolder = null;
        String saveFolder = "Users/hyeji/upload";
        int fileSize = 1024 * 1024 * 5; // 5MB
        
        ServletContext context = req.getServletContext();
        boolean isModifySuccess = false;

        // 파일 전송
        MultipartRequest multi = null;

        HttpSession session = req.getSession();
        String member_id_bf = (String) session.getAttribute("member_id");
        System.out.println(member_id_bf);

        try {
            multi = new MultipartRequest(req, saveFolder, fileSize, "utf-8", new DefaultFileRenamePolicy());

            String member_id = multi.getParameter("member_id");
            System.out.println("member_id : " + member_id);

            member = new MemberVO();
            MemberModifyService memberModifyService = MemberModifyService.getInstance();
            String msg = "";

            // member 정보 설정
            member.setMember_id(multi.getParameter("member_id"));
            member.setPwd(multi.getParameter("pwd"));
            member.setName(multi.getParameter("name"));
            member.setPhone(multi.getParameter("phone"));
            member.setEmail(multi.getParameter("email"));

            // 파일 처리 (파일이 있을 때만 출력)
            Enumeration<?> fileNames = multi.getFileNames();
            while (fileNames.hasMoreElements()) {
                String fileInputName = (String) fileNames.nextElement();
                String uploadedFileName = multi.getFilesystemName(fileInputName);
                System.out.println("file input name: " + fileInputName);
                System.out.println("uploaded file name: " + uploadedFileName);
                
                if (uploadedFileName != null) {
                    member.setFile(uploadedFileName); // 파일이 있을 경우 설정
                } else {
                    member.setFile(null); // 파일이 없을 경우 null 설정
                }
            }

            // 파일과 텍스트 필드 값 디버깅 출력
            System.out.println("파일명 (member.getFile()): " + member.getFile());
            System.out.println("이메일 (member.getEmail()): " + member.getEmail());
            System.out.println("아이디 (member.getMember_id()): " + member.getMember_id());


           
            


            
            
            // 회원 정보 수정 수행
            isModifySuccess = memberModifyService.modifyMember(member, member_id_bf);

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
            System.out.println("회원정보수정실패!!! " + e.getMessage());
        } 

        return forward;
    }
}
