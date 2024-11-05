package com.lec.reservation.vo;

import java.util.Date;
import java.util.Objects;

public class FacilityReservationVO {
	private int reservation_id;
	private String member_id;
	private String facility_type;
	private Date checkin_date;
	private Date checkout_date;
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
	public String getFacility_type() {
		return facility_type;
	}
	public void setFacility_type(String facility_type) {
		this.facility_type = facility_type;
	}
	public Date getCheckin_date() {
		return checkin_date;
	}
	public void setCheckin_date(Date checkin_date) {
		this.checkin_date = checkin_date;
	}
	public Date getCheckout_date() {
		return checkout_date;
	}
	public void setCheckout_date(Date checkout_date) {
		this.checkout_date = checkout_date;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	@Override
	public String toString() {
		return "FacilityReservationVO [reservation_id=" + reservation_id + ", member_id=" + member_id
				+ ", facility_type=" + facility_type + ", checkin_date=" + checkin_date + ", checkout_date="
				+ checkout_date + ", reg_date=" + reg_date + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(checkin_date, checkout_date, facility_type, member_id, reg_date, reservation_id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FacilityReservationVO other = (FacilityReservationVO) obj;
		return Objects.equals(checkin_date, other.checkin_date) && Objects.equals(checkout_date, other.checkout_date)
				&& Objects.equals(facility_type, other.facility_type) && Objects.equals(member_id, other.member_id)
				&& Objects.equals(reg_date, other.reg_date) && reservation_id == other.reservation_id;
	}
	
	
	
	
	
	
}


