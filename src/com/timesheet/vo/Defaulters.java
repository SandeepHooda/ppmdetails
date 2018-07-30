package com.timesheet.vo;

import java.util.Set;

public class Defaulters {

	private String managerClientID;
	private Set<String> defaulters;

	public Set<String> getDefaulters() {
		return defaulters;
	}

	public void setDefaulters(Set<String> defaulters) {
		this.defaulters = defaulters;
	}

	public String getManagerClientID() {
		return managerClientID;
	}

	public void setManagerClientID(String managerClientID) {
		this.managerClientID = managerClientID;
	}
	
}
