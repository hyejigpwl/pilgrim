package com.lec.member.vo;

public class ActionForward {

	private boolean isRedirect = false;
	private String path = null;
	
	public boolean isRedirect() {
		return isRedirect;
	}
	
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	
	public String getPath() {
		return path;  // boardList.mb?p=10&id=아이디&n=이름&fn=파일명
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	

}
