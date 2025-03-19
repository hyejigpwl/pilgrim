package com.lec.review.service;

import java.sql.Connection;
import java.util.List;

import com.lec.db.JDBCUtility;
import com.lec.review.dao.ReviewDAO;
import com.lec.review.vo.ReviewFilesVO;
import com.lec.review.vo.ReviewVO;

public class ReviewDetailService {

	private ReviewDetailService() {}
	private static ReviewDetailService reviewDetailService = null;
	public static ReviewDetailService getInstance() {
		if(reviewDetailService == null) reviewDetailService = new ReviewDetailService();
		return reviewDetailService;
	}

	public ReviewVO getReview(int bno) {
		ReviewVO review = null;
		
		Connection conn = JDBCUtility.getConnection();
		ReviewDAO reviewDAO = ReviewDAO.getInstance();
		reviewDAO.setConnection(conn);
		int readCount = reviewDAO.updateReadCount(bno);
		if(readCount > 0) JDBCUtility.commit(conn); else JDBCUtility.rollback(conn);
		review = reviewDAO.selectReview(bno);
		return review;
	}
	
	 // ✅ 특정 게시글의 파일 리스트 조회
    public List<ReviewFilesVO> getFiles(int bno) {
        Connection conn = JDBCUtility.getConnection();
        ReviewFileService fileService = ReviewFileService.getInstance();
        List<ReviewFilesVO> fileList = fileService.getFilesByBno(bno);
        JDBCUtility.close(conn,null,null);
        return fileList;
    }
}
