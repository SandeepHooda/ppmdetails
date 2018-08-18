package com.login.vo;

public class UpdateObo {
	private boolean hasMyOboRole;
	private String clientEmail;
	private String managerClientEmail;
	public boolean isHasMyOboRole() {
		return hasMyOboRole;
	}
	public void setHasMyOboRole(boolean hasMyOboRole) {
		this.hasMyOboRole = hasMyOboRole;
	}
	public String getClientEmail() {
		return clientEmail;
	}
	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}
	public String getManagerClientEmail() {
		return managerClientEmail;
	}
	public void setManagerClientEmail(String managerClientEmail) {
		this.managerClientEmail = managerClientEmail;
	}

}
