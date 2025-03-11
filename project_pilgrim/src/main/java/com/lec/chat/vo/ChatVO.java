package com.lec.chat.vo;

import java.io.Serializable;

public class ChatVO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String member_id; // 사용자 ID
    private String sender;    // 메시지 보낸 사람
    private String msg;       // 메시지 내용
    private String uuid;      // 메시지 고유 ID

    // 기본 생성자
    public ChatVO() {}

    public ChatVO(int id, String member_id, String sender, String msg, String uuid) {
        this.id = id;
    	this.member_id = member_id;
        this.sender = sender;
        this.msg = msg;
        this.uuid = uuid;
    }

    public String getId() {
        return member_id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "ChatVO [id=" + id + ", member_id=" + member_id + ", sender=" + sender + ", msg=" + msg + ", uuid=" + uuid + "]";
    }
}

