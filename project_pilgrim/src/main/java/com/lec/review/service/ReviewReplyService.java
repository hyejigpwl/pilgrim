package com.lec.review.service;

import java.sql.Connection;
import java.util.List;

import com.lec.db.JDBCUtility;
import com.lec.review.dao.ReviewDAO;
import com.lec.review.vo.ReviewReplyVO;

public class ReviewReplyService {
    
    private static ReviewReplyService instance = new ReviewReplyService();
    
    private ReviewReplyService() {}
    
    public static ReviewReplyService getInstance() {
        return instance;
    }
    
    public boolean addReply(ReviewReplyVO reply) {
        Connection conn = JDBCUtility.getConnection();
        ReviewDAO dao = ReviewDAO.getInstance();
        dao.setConnection(conn);
        
        boolean isInsertSuccess = dao.insertReply(reply);
        
        if (isInsertSuccess) {
            JDBCUtility.commit(conn);
        } else {
            JDBCUtility.rollback(conn);
        }
        
        JDBCUtility.close(conn, null, null);
        
        return isInsertSuccess;
    }
    
    
    
    
    public List<ReviewReplyVO> getReplyList(int bno) {
        Connection conn = JDBCUtility.getConnection();
        ReviewDAO dao = ReviewDAO.getInstance();
        dao.setConnection(conn);
        
        List<ReviewReplyVO> replyList = dao.getRepliesByBno(bno);
        JDBCUtility.close(conn, null, null);
        
        return replyList;
    }
}
