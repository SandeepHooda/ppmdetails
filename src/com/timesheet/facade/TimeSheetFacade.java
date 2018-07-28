package com.timesheet.facade;



import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.timesheet.vo.AWeekTimeSheet;
import com.timesheet.vo.TimeSheetEntry;
import com.timesheet.vo.TimeSheetVO;

import mangodb.MangoDB;

public class TimeSheetFacade {
	private static final Logger log = Logger.getLogger(TimeSheetFacade.class.getName());
	
	public TimeSheetVO getUsertimeSheets(String  clientEmail) {
		clientEmail =clientEmail.toLowerCase();
		TimeSheetVO userAllTimeSheet = new TimeSheetVO();
		Gson  json = new Gson();
		
		String userAllTimeSheetJson = MangoDB.getDocumentWithQuery("ppm","timesheets", clientEmail, null, true, MangoDB.mlabKeySonu, null);
		if (userAllTimeSheetJson != null && userAllTimeSheetJson.indexOf(clientEmail) > 0) {
			userAllTimeSheet = json.fromJson(userAllTimeSheetJson, new TypeToken<TimeSheetVO>() {}.getType());
		}
		
		
		return userAllTimeSheet;
	}
	
	public TimeSheetVO submitTimeSheet(String  clientEmail,TimeSheetEntry currentEntry, int currentEntryDate) {
		clientEmail =clientEmail.toLowerCase();
		TimeSheetVO userAllTimeSheet = new TimeSheetVO();
		Gson  json = new Gson();
		
		String userAllTimeSheetJson = MangoDB.getDocumentWithQuery("ppm","timesheets", clientEmail, null, true, MangoDB.mlabKeySonu, null);
		
			userAllTimeSheet = json.fromJson(userAllTimeSheetJson, new TypeToken<TimeSheetVO>() {}.getType());
			
			if (userAllTimeSheet == null || userAllTimeSheet.getAllTimeSheets() == null) {//First time sheet by user
				AWeekTimeSheet aWeekTimeSheet = new AWeekTimeSheet();
				aWeekTimeSheet.setWeekStartDate(currentEntryDate);
				aWeekTimeSheet.getTimeSheetEntry().add(currentEntry);
				userAllTimeSheet = new TimeSheetVO();
				userAllTimeSheet.set_id(clientEmail);
				userAllTimeSheet.getAllTimeSheets().add(aWeekTimeSheet);
			}else {//User has few time sheets already submitted
				boolean editingPreviousEntry = false;
				for ( AWeekTimeSheet aWeekEntry: userAllTimeSheet.getAllTimeSheets()) {
					 
					if(aWeekEntry.getWeekStartDate() == currentEntryDate) {//editing the already filled entry
						editingPreviousEntry = true;
						List<TimeSheetEntry> previousEntriesForthatWeek =aWeekEntry.getTimeSheetEntry();
						int maxVersionID = 0;
						for (TimeSheetEntry aEntry: previousEntriesForthatWeek) {
							if (maxVersionID < aEntry.getEditVersion() ) {
								maxVersionID = aEntry.getEditVersion();
							}
						}
						
						currentEntry.setEditVersion(maxVersionID+1);
						aWeekEntry.getTimeSheetEntry().add(currentEntry);
						break;
					}
				}
				if (!editingPreviousEntry) {//Fresh entry for a week
					AWeekTimeSheet aWeekTimeSheet = new AWeekTimeSheet();
					aWeekTimeSheet.setWeekStartDate(currentEntryDate);
					aWeekTimeSheet.getTimeSheetEntry().add(currentEntry);
					userAllTimeSheet = new TimeSheetVO();
					userAllTimeSheet.set_id(clientEmail);
					userAllTimeSheet.getAllTimeSheets().add(aWeekTimeSheet);
				}
			}
			
		
		 userAllTimeSheetJson = json.toJson(userAllTimeSheet, new TypeToken<TimeSheetVO>() {}.getType());
		 MangoDB.createNewDocumentInCollection("ppm","timesheets", userAllTimeSheetJson , MangoDB.mlabKeySonu);
		
		return userAllTimeSheet;
	}
	
	

}
