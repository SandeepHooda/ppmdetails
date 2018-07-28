package com.timesheet.facade;



import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
	
	

}
