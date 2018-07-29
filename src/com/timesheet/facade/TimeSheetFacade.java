package com.timesheet.facade;



import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import com.communication.email.EmailAddess;
import com.communication.email.EmailVO;
import com.communication.email.MailService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.login.vo.EmailOTP;
import com.login.vo.LoginVO;
import com.product.Response.ResponseStatus;
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
	public EmailOTP sendMessage( String from,String message) {
		boolean includeInactive = false;
		List<LoginVO> reporteesList = new TimeSheetFacade().getReportees(from, includeInactive);
		String messageSentTo = message+"  This message has been sent to ";
		
		for (LoginVO aReportee: reporteesList) {
			sendEmailMessage(aReportee.get_id(),  message, from);
			messageSentTo += " "+aReportee.get_id();
		}
		EmailOTP status = new EmailOTP();
		status.set_id(messageSentTo);
		return status;
	}
	
	private void sendEmailMessage(String toAddress, String message, String fromAddress ) {
		
		
		EmailVO emalVO = new EmailVO();
		emalVO.setUserName("personal.reminder.notification@gmail.com");
		emalVO.setPassword(MailService.key);
		emalVO.setSubject("Please fill your timesheet details in advance");
		
		emalVO.setHtmlContent(message+" <br/><br/>"
				+ "https://ppmdetails.appspot.com/");
		EmailAddess from = new EmailAddess();
		from.setLabel(fromAddress);
		from.setAddress(emalVO.getUserName());
		
		List<EmailAddess> receipients = new ArrayList<>();
		EmailAddess to = new EmailAddess();
		to.setAddress(toAddress);
		emalVO.setFromAddress(from);
		receipients.add(to);
		emalVO.setToAddress(receipients);
		MailService.sendSimpleMail(emalVO);
		
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
	
	 public List<LoginVO>  getReportees(String managerClientEmail, boolean includeInactive) {
	    	String managerJson = MangoDB.getDocumentWithQuery("ppm","registered-users", managerClientEmail, null, true, MangoDB.mlabKeySonu, null) ;
			Gson  json = new Gson();
			LoginVO manager= json.fromJson(managerJson, new TypeToken<LoginVO>() {}.getType());
			
			
			String reeporteeQuery = "{\"managerInfyEmail\":\""+manager.getManagerInfyEmail()+"\"}";
			try {
				reeporteeQuery =URLEncoder.encode(reeporteeQuery, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				
				e.printStackTrace();
			}
			reeporteeQuery = "&q="+reeporteeQuery;
			String reporteeJson = "["+MangoDB.getDocumentWithQuery("ppm","registered-users", null, null, false, MangoDB.mlabKeySonu, reeporteeQuery)+"]" ;
			
			List<LoginVO> reporteeDBList= json.fromJson(reporteeJson, new TypeToken<List<LoginVO>>() {}.getType());
			List<LoginVO> reportees = new ArrayList<LoginVO>();
			if (null != reporteeDBList && reporteeDBList.indexOf("null") <0) {
				for (LoginVO aReportee: reporteeDBList) {
					if (includeInactive || !aReportee.isInactive() ) {
						reportees.add(aReportee);
					}
					
				}
			}
			
			
			return reportees;
	    }

}
