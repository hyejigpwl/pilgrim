package com.lec.qna.service;

import java.sql.Connection;
import java.util.List;

import com.lec.qna.dao.QnaDAO;
import com.lec.qna.vo.QnaReplyVO;
import com.lec.db.JDBCUtility;

public class QnaReplyService {
    
    private static QnaReplyService instance = new QnaReplyService();
    
    private QnaReplyService() {}
    
    public static QnaReplyService getInstance() {
        return instance;
    }
    
    public boolean addReply(QnaReplyVO reply) {
        Connection conn = JDBCUtility.getConnection();
        QnaDAO dao = QnaDAO.getInstance();
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
    
    
    
    
    public static List<QnaReplyVO> getReplyList(int bno) {
        Connection conn = JDBCUtility.getConnection();
        QnaDAO dao = QnaDAO.getInstance();
        dao.setConnection(conn);
        
        List<QnaReplyVO> replyList = dao.getRepliesByBno(bno);
        JDBCUtility.close(conn, null, null);
        
        return replyList;
    }
}
