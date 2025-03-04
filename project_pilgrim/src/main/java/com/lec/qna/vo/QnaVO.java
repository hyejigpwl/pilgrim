package com.lec.qna.vo;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class QnaVO {
    private int bno;
    private String member_id;
    private Date date;
    private String title;
    private String content;
    private int view_count;
    private int reply_count;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getView_count() {
        return view_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }

    public int getReply_count() {
        return reply_count;
    }

    public void setReply_count(int reply_count) {
        this.reply_count = reply_count;
    }

    @Override
    public String toString() {
        return "QnaVO [bno=" + bno + ", member_id=" + member_id + ", date=" + date + ", title=" + title + ", content="
                + content + ", view_count=" + view_count + ", reply_count=" + reply_count + "]";
    }

	@Override
	public int hashCode() {
		return Objects.hash(bno, content, date, member_id, reply_count, title, view_count);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QnaVO other = (QnaVO) obj;
		return bno == other.bno && Objects.equals(content, other.content) && Objects.equals(date, other.date)
				&& Objects.equals(member_id, other.member_id)
				&& reply_count == other.reply_count && Objects.equals(title, other.title)
				&& view_count == other.view_count;
	}
    
    
}
