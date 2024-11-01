package com.lec.member.service;

import com.lec.member.dao.MemberDAO;

public class MemberLoginService {
    public String login(String member_id, String pwd) {
        MemberDAO memberDao = MemberDAO.getInstance();
        return memberDao.isMember(member_id, pwd);
    }
}
