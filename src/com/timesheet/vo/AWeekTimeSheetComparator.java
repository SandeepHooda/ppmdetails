package com.timesheet.vo;

import java.util.Comparator;

public class AWeekTimeSheetComparator implements Comparator<AWeekTimeSheet> {

	@Override
	public int compare(AWeekTimeSheet o1, AWeekTimeSheet o2) {
		
		return o1.getWeekStartDate()- o2.getWeekStartDate();
	}

}
