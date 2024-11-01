package com.lec.member.vo;

import java.sql.Connection;
import java.util.Date;
import java.util.Objects;

public class MemberVO {

	private String member_id;
	private String name;
	private String pw;
	private int phone;
	private String email;
	private Date signup_date;
	private int terms_agreed;
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public int getPhone() {
		return phone;
	}
	public void setPhone(int phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getSignup_date() {
		return signup_date;
	}
	public void setSignup_date(Date signup_date) {
		this.signup_date = signup_date;
	}
	public int getTerms_agreed() {
		return terms_agreed;
	}
	public void setTerms_agreed(int terms_agreed) {
		this.terms_agreed = terms_agreed;
	}
	@Override
	public int hashCode() {
		return Objects.hash(email, member_id, name, phone, pw, signup_date, terms_agreed);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MemberVO other = (MemberVO) obj;
		return Objects.equals(email, other.email) && Objects.equals(member_id, other.member_id)
				&& Objects.equals(name, other.name) && phone == other.phone && Objects.equals(pw, other.pw)
				&& Objects.equals(signup_date, other.signup_date) && terms_agreed == other.terms_agreed;
	}
	@Override
	public String toString() {
		return "MemberVO [member_id=" + member_id + ", name=" + name + ", pw=" + pw + ", phone=" + phone + ", email="
				+ email + ", signup_date=" + signup_date + ", terms_agreed=" + terms_agreed + "]";
	}
	
	
	
		
	
}
