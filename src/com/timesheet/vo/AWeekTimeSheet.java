package com.timesheet.vo;

import java.util.ArrayList;
import java.util.List;

public class AWeekTimeSheet {

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
}
