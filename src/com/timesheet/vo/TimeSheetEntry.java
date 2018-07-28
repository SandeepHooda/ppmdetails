package com.timesheet.vo;

public class TimeSheetEntry {

	private int billableHours;
	private int nonBillableHours;
	private String remarks;
	private int editVersion;
	public int getBillableHours() {
		return billableHours;
	}
	public void setBillableHours(int billableHours) {
		this.billableHours = billableHours;
	}
	public int getNonBillableHours() {
		return nonBillableHours;
	}
	public void setNonBillableHours(int nonBillableHours) {
		this.nonBillableHours = nonBillableHours;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getEditVersion() {
		return editVersion;
	}
	public void setEditVersion(int editVersion) {
		this.editVersion = editVersion;
	}
}
