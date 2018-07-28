package com.timesheet.vo;

import java.util.List;

public class TimeSheetVO {
	private String _id;
	private List<AWeekTimeSheet> allTimeSheets;
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public List<AWeekTimeSheet> getAllTimeSheets() {
		return allTimeSheets;
	}
	public void setAllTimeSheets(List<AWeekTimeSheet> allTimeSheets) {
		this.allTimeSheets = allTimeSheets;
	}
 
}
