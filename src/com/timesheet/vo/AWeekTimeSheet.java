package com.timesheet.vo;

import java.util.ArrayList;
import java.util.List;

public class AWeekTimeSheet {

	private String emailID;//used in csv export;
	private String clientManagerName;//used in csv export;
	private int weekStartDate;
	private List<TimeSheetEntry> timeSheetEntry = new ArrayList<TimeSheetEntry>();
	public int getWeekStartDate() {
		return weekStartDate;
	}
	public void setWeekStartDate(int weekStartDate) {
		this.weekStartDate = weekStartDate;
	}
	public List<TimeSheetEntry> getTimeSheetEntry() {
		return timeSheetEntry;
	}
	public void setTimeSheetEntry(List<TimeSheetEntry> timeSheetEntry) {
		this.timeSheetEntry = timeSheetEntry;
	}
	public String getEmailID() {
		return emailID;
	}
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}
	public String getClientManagerName() {
		return clientManagerName;
	}
	public void setClientManagerName(String clientManagerName) {
		this.clientManagerName = clientManagerName;
	}
}
