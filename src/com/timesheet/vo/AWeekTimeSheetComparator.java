package com.timesheet.vo;

import java.util.Comparator;

public class AWeekTimeSheetComparator implements Comparator<AWeekTimeSheet> {

	@Override
	public int compare(AWeekTimeSheet o1, AWeekTimeSheet o2) {
		if ( o1.getWeekStartDate() == o2.getWeekStartDate()) {
			if( o1.getClientManagerName().compareTo(o2.getClientManagerName()) > 0) {
				return 1;
			}else {
				return -1;
			}
		}else {
			return o1.getWeekStartDate()- o2.getWeekStartDate();
		}
		
	}

}
