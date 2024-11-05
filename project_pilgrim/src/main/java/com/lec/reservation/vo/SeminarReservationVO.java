package com.lec.reservation.vo;

import java.util.Date;
import java.util.Objects;

public class SeminarReservationVO {
	private int reservation_id;
	private String member_id;
	private String seminar_type;
	private int guest_count;
	private Date seminar_date;
	private Date reg_date;
	public int getReservation_id() {
		return reservation_id;
	}
	public void setReservation_id(int reservation_id) {
		this.reservation_id = reservation_id;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getSeminar_type() {
		return seminar_type;
	}
	public void setSeminar_type(String seminar_type) {
		this.seminar_type = seminar_type;
	}
	public int getGuest_count() {
		return guest_count;
	}
	public void setGuest_count(int guest_count) {
		this.guest_count = guest_count;
	}
	public Date getSeminar_date() {
		return seminar_date;
	}
	public void setSeminar_date(Date seminar_date) {
		this.seminar_date = seminar_date;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	@Override
	public int hashCode() {
		return Objects.hash(guest_count, member_id, reg_date, reservation_id, seminar_date, seminar_type);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SeminarReservationVO other = (SeminarReservationVO) obj;
		return guest_count == other.guest_count && Objects.equals(member_id, other.member_id)
				&& Objects.equals(reg_date, other.reg_date) && reservation_id == other.reservation_id
				&& Objects.equals(seminar_date, other.seminar_date) && Objects.equals(seminar_type, other.seminar_type);
	}
	@Override
	public String toString() {
		return "SeminarReservationVO [reservation_id=" + reservation_id + ", member_id=" + member_id + ", seminar_type="
				+ seminar_type + ", guest_count=" + guest_count + ", seminar_date=" + seminar_date + ", reg_date="
				+ reg_date + "]";
	}
	
	
	
	
}
