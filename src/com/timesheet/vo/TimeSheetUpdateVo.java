package com.timesheet.vo;

public class TimeSheetUpdateVo {
	private String  clientEmail;
	private TimeSheetEntry currentEntry;
	private int currentEntryDate;
	public String getClientEmail() {
		return clientEmail;
	}
	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}
	public TimeSheetEntry getCurrentEntry() {
		return currentEntry;
	}
	public void setCurrentEntry(TimeSheetEntry currentEntry) {
		this.currentEntry = currentEntry;
	}
	public int getCurrentEntryDate() {
		return currentEntryDate;
	}
	public void setCurrentEntryDate(int currentEntryDate) {
		this.currentEntryDate = currentEntryDate;
	}
}
