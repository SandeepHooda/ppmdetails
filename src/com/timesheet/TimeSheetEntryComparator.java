package com.timesheet;

import java.util.Comparator;

import com.timesheet.vo.TimeSheetEntry;

public class TimeSheetEntryComparator  implements Comparator<TimeSheetEntry>{

	@Override
	public int compare(TimeSheetEntry o1, TimeSheetEntry o2) {
		
		return o2.getEditVersion() - o1.getEditVersion() ;
	}

}
