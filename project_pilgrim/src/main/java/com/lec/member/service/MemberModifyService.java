package com.lec.member.service;

import java.sql.Connection;

import com.lec.member.dao.MemberDAO;
import com.lec.member.vo.MemberVO;
import com.lec.db.JDBCUtility;

public class MemberModifyService {

	private MemberModifyService() {}
	private static MemberModifyService memberModifyService = null;
	public static MemberModifyService getInstance() {
		if(memberModifyService == null) memberModifyService = new MemberModifyService();
		return memberModifyService;
	}
	/*
	 * public boolean isMemberWriter(String member_id, String pw) {
	 * 
	 * boolean isWriter = false; Connection conn = JDBCUtility.getConnection();
	 * MemberDAO memberDAO = MemberDAO.getInstance(); memberDAO.setConnection(conn);
	 * isWriter = memberDAO.isMemberWriter(member_id, pwd); return isWriter; }
	 */
	
	public boolean modifyMember(MemberVO member, String member_id_bf) {
		boolean isModifySuccess = false;
		
		System.out.println(member.getFile());
		
		Connection conn = JDBCUtility.getConnection();
		MemberDAO memberDAO = MemberDAO.getInstance();
		memberDAO.setConnection(conn); 
		int updateCount = memberDAO.updateMember(member, member_id_bf);
		
		if(updateCount > 0) {
			JDBCUtility.commit(conn);
			JDBCUtility.close(conn, null, null);
			isModifySuccess = true;
		} else {
			JDBCUtility.rollback(conn);
		}
			
		return isModifySuccess;
	}
}
