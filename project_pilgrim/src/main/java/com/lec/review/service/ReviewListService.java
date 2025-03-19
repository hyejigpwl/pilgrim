package com.lec.review.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


import com.lec.db.JDBCUtility;
import com.lec.review.dao.ReviewDAO;
import com.lec.review.vo.ReviewVO;

public class ReviewListService {

	private  ReviewListService() {}
	private static ReviewListService reviewListService = null;
	public static ReviewListService getInstance() {
		if(reviewListService == null) reviewListService = new ReviewListService();
		return reviewListService;
	}
	public int getListCount(String f, String q) {
		// 글전체갯수 : select count(*) from board where subject like '%%';		
		int listCount = 0;
		Connection conn = JDBCUtility.getConnection();
		ReviewDAO reviewDAO = ReviewDAO.getInstance();		
		reviewDAO.setConnection(conn);		
		listCount = reviewDAO.selectListCount(f, q);	
		return listCount;
	}
	
	public List<ReviewVO> getReviewList(int p, int l, String f, String q) {
		List<ReviewVO> reviewList = new ArrayList<ReviewVO>();
		Connection conn = JDBCUtility.getConnection();
		ReviewDAO reviewDAO = ReviewDAO.getInstance();		
		reviewDAO.setConnection(conn);
		reviewList = reviewDAO.selectReviewList(p, l, f, q);	
		return reviewList;
	}
}
