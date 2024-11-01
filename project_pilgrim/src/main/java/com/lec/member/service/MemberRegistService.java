package com.lec.member.service;

import java.sql.Connection;

import com.lec.member.dao.MemberDAO;
import com.lec.member.vo.MemberVO;
import com.lec.db.JDBCUtility;

public class MemberRegistService {

	private MemberRegistService() {}
	private static MemberRegistService memberRegistService = null;
	public static MemberRegistService getInstance() {
		if(memberRegistService == null) memberRegistService = new MemberRegistService();
		return memberRegistService;
	}
	
	
	public boolean registerMember(MemberVO member) {
		
		boolean isWriteSuccess = false;
        Connection conn = null;

        try {
            conn = JDBCUtility.getConnection();
            MemberDAO memberDAO = MemberDAO.getInstance();
            memberDAO.setConnection(conn);

            int insertCount = memberDAO.insert(member);
            
            System.out.println("Insert Count: " + insertCount);

            if (insertCount > 0) {
                JDBCUtility.commit(conn);
                System.out.println("Commit 성공");
                isWriteSuccess = true;
            } else {
                JDBCUtility.rollback(conn);
                System.out.println("Rollback 실행");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JDBCUtility.rollback(conn); // 예외 발생 시 rollback
        } finally {
            JDBCUtility.close(conn, null, null); // 항상 close
        }
        System.out.println("isWriteSuccess:" + isWriteSuccess);
        return isWriteSuccess;
	}
}
