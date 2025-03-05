package com.lec.qna.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lec.common.Action;
import com.lec.common.ActionForward;
import com.lec.qna.service.QnaModifyService;
import com.lec.qna.service.QnaFileService;
import com.lec.qna.vo.QnaVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class QnaModifyAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest req, HttpServletResponse res) {

        ActionForward forward = null;
        String saveFolder = "/Users/hyeji/upload"; // 파일 저장 경로
        int fileSize = 1024 * 1024 * 10; // 10MB 제한

        boolean isWriter = false;

        try {
            // ✅ 1. 파일 업로드 처리
            MultipartRequest multi = new MultipartRequest(req, saveFolder, fileSize, "utf-8", new DefaultFileRenamePolicy());

            int p = Integer.parseInt(multi.getParameter("p")); // 페이지 번호
            int bno = Integer.parseInt(multi.getParameter("bno")); // 게시글 번호

            HttpSession session = req.getSession();
            String member_id = (String) session.getAttribute("member_id");

            // ✅ 2. 작성자 확인
            QnaModifyService qnaModifyService = QnaModifyService.getInstance();
            isWriter = qnaModifyService.isQnaWriter(bno, member_id);

            if (!isWriter) {
                sendAlert(res, "게시글을 수정할 권한이 없습니다.", "history.back()");
                return forward;
            }

            // ✅ 3. 게시글 정보 업데이트
            QnaVO qna = new QnaVO();
            qna.setBno(bno);
            qna.setTitle(multi.getParameter("title"));
            qna.setContent(multi.getParameter("content"));

            // 파일명 수집
            String[] fileListArray = multi.getParameter("fileList") != null ? 
                                     multi.getParameter("fileList").split(",") : new String[0];

            List<String> fileList = new ArrayList<>(Arrays.asList(fileListArray));

            // ✅ 최종 파일 리스트 확인
            System.out.println("📂 최종 파일 리스트: " + fileList);

            
            boolean isSuccess = qnaModifyService.modifyQna(qna,fileList);
            if(isSuccess) {
            	sendAlert(res, "게시글이 수정되었습니다.",String.format("qnaDetail.qa?p=%d&bno=%d",p,bno));
            }

        } catch (Exception e) {
            System.out.println("게시글 수정 실패: " + e.getMessage());
        }

        return forward;
    }

    // ✅ JavaScript alert 메시지를 출력하는 메서드
    private void sendAlert(HttpServletResponse res, String message, String redirectUrl) throws IOException {
        res.setContentType("text/html; charset=utf-8");
        PrintWriter out = res.getWriter();
        out.println("<script>");
        out.println("  alert('" + message + "');");
        if ("history.back()".equals(redirectUrl)) {
            out.println("  history.back();");
        } else {
            out.println("  location.href='" + redirectUrl + "';");
        }
        out.println("</script>");
        out.close();
    }
}
