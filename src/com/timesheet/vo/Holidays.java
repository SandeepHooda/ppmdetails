package com.timesheet.vo;

import java.util.List;

public class Holidays {
	private String _id;
	private List<Holiday> holidays;
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public List<Holiday> getHolidays() {
		return holidays;
	}
	public void setHolidays(List<Holiday> holidays) {
		this.holidays = holidays;
	}

}
