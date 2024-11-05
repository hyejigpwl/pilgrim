package com.lec.reservation.vo;

import java.util.Date;
import java.util.Objects;

public class RoomReservationVO {
	private int reservation_id;
	private String member_id;
	private int guest_count;
	private Date checkin_date;
	private Date checkout_date;
	private String room_type;
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
	public int getGuest_count() {
		return guest_count;
	}
	public void setGuest_count(int guest_count) {
		this.guest_count = guest_count;
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
	public String getRoom_type() {
		return room_type;
	}
	public void setRoom_type(String room_type) {
		this.room_type = room_type;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	
	
	
	@Override
	public String toString() {
		return "ReservationVO [reservation_id=" + reservation_id + ", member_id=" + member_id + ", guest_count="
				+ guest_count + ", checkin_date=" + checkin_date + ", checkout_date=" + checkout_date + ", room_type="
				+ room_type + ", reg_date=" + reg_date + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(checkin_date, checkout_date, guest_count, member_id, reg_date, reservation_id, room_type);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoomReservationVO other = (RoomReservationVO) obj;
		return Objects.equals(checkin_date, other.checkin_date) && Objects.equals(checkout_date, other.checkout_date)
				&& guest_count == other.guest_count && Objects.equals(member_id, other.member_id)
				&& Objects.equals(reg_date, other.reg_date) && reservation_id == other.reservation_id
				&& Objects.equals(room_type, other.room_type);
	}
	
	
	
	
}


