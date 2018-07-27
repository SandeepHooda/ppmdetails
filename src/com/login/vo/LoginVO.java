package com.login.vo;

public class LoginVO {
	
	private String _id;
	private String errorMessage;
	private String clientEmail;
	private String infyEmail;
	private String managerInfyEmail;
	private String emailOTP;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getClientEmail() {
		return clientEmail;
	}

	public void setClientEmail(String clientEmail) {
		this._id = clientEmail;
		this.clientEmail = clientEmail;
	}

	public String getInfyEmail() {
		return infyEmail;
	}

	public void setInfyEmail(String infyEmail) {
		this.infyEmail = infyEmail;
	}

	public String getManagerInfyEmail() {
		return managerInfyEmail;
	}

	public void setManagerInfyEmail(String managerInfyEmail) {
		this.managerInfyEmail = managerInfyEmail;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getEmailOTP() {
		return emailOTP;
	}

	public void setEmailOTP(String emailOTP) {
		this.emailOTP = emailOTP;
	}

	
	
	

}
