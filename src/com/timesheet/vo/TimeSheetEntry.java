package com.timesheet.vo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeSheetEntry {

	private int billableHours;
	private int nonBillableHours;
	private String remarks;
	private int editVersion;
	private long updateTime;
	private String updateTime_Str;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
	static {
		sdf.setTimeZone(TimeZone.getTimeZone("IST"));
	}
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
	public long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
		this.updateTime_Str = sdf.format(new Date(updateTime))+" "+sdf.getTimeZone().getID();
	}
	public String getUpdateTime_Str() {
		return updateTime_Str;
	}
}
