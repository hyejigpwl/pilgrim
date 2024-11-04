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
            if (insertCount > 0) {
                JDBCUtility.commit(conn);
                isWriteSuccess = true;
            } else {
                JDBCUtility.rollback(conn);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JDBCUtility.rollback(conn); // 예외 발생 시 rollback
        } finally {
            JDBCUtility.close(conn, null, null); // 항상 close
        }
        return isWriteSuccess;
	}
}
