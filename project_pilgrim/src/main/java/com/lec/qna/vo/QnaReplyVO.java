package com.lec.qna.vo;

import java.util.Date;
import java.util.Objects;

public class QnaReplyVO {
	private int comment_id;
	private int bno;
	private String member_id;
	private String content;
	private Date date;
	public int getComment_id() {
		return comment_id;
	}
	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public int hashCode() {
		return Objects.hash(bno, comment_id, content, date, member_id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QnaReplyVO other = (QnaReplyVO) obj;
		return bno == other.bno && comment_id == other.comment_id && Objects.equals(content, other.content)
				&& Objects.equals(date, other.date) && Objects.equals(member_id, other.member_id);
	}
	@Override
	public String toString() {
		return "QnaReplyVO [comment_id=" + comment_id + ", bno=" + bno + ", member_id=" + member_id + ", content="
				+ content + ", date=" + date + "]";
	}
	
	
	
	
}
