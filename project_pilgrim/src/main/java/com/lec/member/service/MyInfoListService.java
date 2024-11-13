package com.lec.member.service;

import java.sql.Connection;

import com.lec.db.JDBCUtility;
import com.lec.member.dao.MemberDAO;
import com.lec.member.vo.MemberVO;

public class MyInfoListService {

	
	private MyInfoListService() {}
	private static MyInfoListService myInfoListService = null;
	public static MyInfoListService getInstance() {
		if(myInfoListService == null) myInfoListService = new MyInfoListService();
		return myInfoListService;
	}

	public MemberVO getMyInfoList(String member_id) {
		MemberVO member = null;
		
		Connection conn = JDBCUtility.getConnection();
		MemberDAO memberDAO = MemberDAO.getInstance();
		memberDAO.setConnection(conn);
		member = memberDAO.getMyInfo(member_id);
		return member;
	}
	
	
	
	
	
	
	
}
