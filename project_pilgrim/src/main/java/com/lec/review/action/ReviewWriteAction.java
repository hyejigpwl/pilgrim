package com.lec.review.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lec.common.Action;
import com.lec.common.ActionForward;
import com.lec.review.service.ReviewFileService;
import com.lec.review.service.ReviewWriteService;
import com.lec.review.vo.ReviewVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class ReviewWriteAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest req, HttpServletResponse res) {

        ActionForward forward = null;
        // String saveFolder = "/Users/hyeji/upload"; // 파일 저장 경로
        String saveFolder = "C:\\hyeji\\upload\\";
        int fileSize = 1024 * 1024 * 10; // 10MB 제한

        HttpSession session = req.getSession();
        String member_id = (String) session.getAttribute("member_id");

        try {
            // MultipartRequest 생성 (파일 업로드 처리)
            MultipartRequest multi = new MultipartRequest(req, saveFolder, fileSize, "utf-8", new DefaultFileRenamePolicy());

            // review 게시글 정보 저장
            ReviewVO review = new ReviewVO();
            review.setMember_id(member_id);
            review.setTitle(multi.getParameter("title"));
            review.setContent(multi.getParameter("content"));

            // ✅ 다중 파일 리스트 변환
            List<String> fileList = new ArrayList<>();
            Enumeration<?> fileNames = multi.getFileNames();
            while (fileNames.hasMoreElements()) {
                String fileParam = (String) fileNames.nextElement();
                String fileName = multi.getOriginalFileName(fileParam);
                if (fileName != null) {
                    fileList.add(fileName);
                    System.out.println(fileList);
                }
            }

            // ✅ 게시글 저장 + 파일 저장 (files 리스트 전달)
            ReviewWriteService reviewWriteService = ReviewWriteService.getInstance();
            boolean isSuccess = reviewWriteService.registerReview(review, fileList);  // 🔥 수정: 파일 리스트 전달

            if (isSuccess) { // 게시글 저장 성공
                res.setContentType("text/html; charset=utf-8");
                PrintWriter out = res.getWriter();
                out.println("<script>");
                out.println("  alert('게시글이 작성되었습니다.');");
                out.println("  location.href='reviewList.qa';"); // 게시글 목록 페이지로 이동
                out.println("</script>");
                System.out.println(fileList);
            } else {
                // ❌ 게시글 등록 실패 시 에러 메시지
                res.setContentType("text/html; charset=utf-8");
                PrintWriter out = res.getWriter();
                out.println("<script>");
                out.println("  alert('게시글 등록이 실패했습니다!');");
                out.println("  history.back();"); // 이전 페이지로 이동
                out.println("</script>");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return forward;
    }
}
