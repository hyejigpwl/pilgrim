package com.lec.member.vo;

import java.sql.Connection;
import java.util.Date;
import java.util.Objects;

public class MemberVO {

	private String member_id;
	private String name;
	private String pwd;
	private String phone;
	private String email;
	private Date reg_date;
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
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public int getTerms_agreed() {
		return terms_agreed;
	}
	public void setTerms_agreed(int terms_agreed) {
		this.terms_agreed = terms_agreed;
	}
	@Override
	public int hashCode() {
		return Objects.hash(email, member_id, name, phone, pwd, reg_date, terms_agreed);
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
				&& Objects.equals(name, other.name) && phone == other.phone && Objects.equals(pwd, other.pwd)
				&& Objects.equals(reg_date, other.reg_date) && terms_agreed == other.terms_agreed;
	}
	@Override
	public String toString() {
		return "MemberVO [member_id=" + member_id + ", name=" + name + ", pwd=" + pwd + ", phone=" + phone + ", email="
				+ email + ", reg_date=" + reg_date + ", terms_agreed=" + terms_agreed + "]";
	}
	
	
	
		
	
}
