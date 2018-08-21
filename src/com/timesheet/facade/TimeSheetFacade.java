package com.timesheet.facade;



import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
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
import com.timesheet.vo.AWeekTimeSheet;
import com.timesheet.vo.AWeekTimeSheetComparator;
import com.timesheet.vo.Defaulters;
import com.timesheet.vo.Holidays;
import com.timesheet.vo.TimeSheetEntry;
import com.timesheet.vo.TimeSheetVO;

import mangodb.MangoDB;

public class TimeSheetFacade {
	private static final Logger log = Logger.getLogger(TimeSheetFacade.class.getName());
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	 private String getLatestMonday() {
	    	Calendar date = new GregorianCalendar();
	    	//date.add(Calendar.DATE, -2);
			int day = date.get(Calendar.DAY_OF_WEEK);
			int weeksToGoBack = 2;
			if (day == Calendar.FRIDAY ||day == Calendar.SATURDAY ||day == Calendar.SUNDAY ) {
				weeksToGoBack--;
			}
			//Mast timesheet recent period
			while(true) {
				if (date.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
					weeksToGoBack--;
					int month = date.get(Calendar.MONTH);
					month++;
					 day =  date.get(Calendar.DATE);
					String monday = ""+date.get(Calendar.YEAR);
					if (month<10) {
						monday +="0"+month;
					}else {
						monday += month;
					}
					if (day<10) {
						monday +="0"+day;
					}else {
						monday += day;
					}
					if (weeksToGoBack <=0) {
						System.out.println(monday);
						return monday;
						
					}
					
				}
				date.add(Calendar.DATE, -1);
				
				
			}
	    }
	
	 public Defaulters getDefaulterListForManager(String managerClientEmail){
		 Defaulters defaulter = new Defaulters();
		 String loginVoJson = MangoDB.getDocumentWithQuery("ppm","registered-users", managerClientEmail, null, true, MangoDB.mlabKeySonu, null);
		 Gson json = new Gson();
		 LoginVO loginVo = json.fromJson(loginVoJson, new TypeToken<LoginVO>() {}.getType());
		 defaulter.setDefaulters(getDefaulterList( loginVo.getManagerInfyEmail()));
		 return defaulter;
	 }
	public Set<String> getDefaulterList(String managerInfyID){
		
		int recentMonday = Integer.parseInt(getLatestMonday());
		
		String allTimeShetsJson = "["+ MangoDB.getDocumentWithQuery("ppm","timesheets", null, null, false, MangoDB.mlabKeySonu, null)+"]" ;
		Gson  json = new Gson();
		List<TimeSheetVO> allTimeShets = json.fromJson(allTimeShetsJson, new TypeToken<List<TimeSheetVO>>() {}.getType());
		
		String query = "{\"inactive\":false}";
		try {
			query =URLEncoder.encode(query, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		query = "&q="+query;
		String activeUsersJson = "["+ MangoDB.getDocumentWithQuery("ppm","registered-users", null, null, false, MangoDB.mlabKeySonu, query)+"]" ;
		json = new Gson();
		List<LoginVO> activeUsers = json.fromJson(activeUsersJson, new TypeToken<List<LoginVO>>() {}.getType());
		Set<String> activeUsersSet = new HashSet<String>();
		for (LoginVO aUser:activeUsers ) {
			activeUsersSet.add(aUser.get_id());
		}
		
		
		Set<String> defaulters = new HashSet<String>();
		if (managerInfyID == null ) {
			defaulters.addAll(activeUsersSet);//All the defaulters
		}else {
			for (LoginVO aUser:activeUsers ) {
				if(managerInfyID.equalsIgnoreCase(aUser.getManagerInfyEmail())) {
					defaulters.add(aUser.get_id());
				}
			}
		}
		
		for (TimeSheetVO aUserTimeSheets: allTimeShets) {
			if (defaulters.contains(aUserTimeSheets.get_id())) {
				
				List<AWeekTimeSheet> aUserAllTimeSheets = aUserTimeSheets.getAllTimeSheets();
				Collections.sort(aUserAllTimeSheets, new AWeekTimeSheetComparator());
				//for (AWeekTimeSheet aWeekTime : aUserAllTimeSheets) {
					//if (aWeekTime.getWeekStartDate() >= recentMonday) {
				int endDate = recentMonday;
				if (endDate< aUserAllTimeSheets.get(aUserAllTimeSheets.size()-1).getWeekStartDate()) {
					endDate = aUserAllTimeSheets.get(aUserAllTimeSheets.size()-1).getWeekStartDate();
				}
				int expectedTimeSheetEntries = getWeeksBetweenDates(aUserAllTimeSheets.get(0).getWeekStartDate(), endDate);
						if (expectedTimeSheetEntries == aUserAllTimeSheets.size())
						defaulters.remove(aUserTimeSheets.get_id());
						//break;
					//}
				//}
				
			}
		}
		return defaulters;
		
	}
	private int getWeeksBetweenDates(int startDate, int endDate) {
		Date start;
		try {
			start = sdf.parse(""+startDate);
			Date end = sdf.parse(""+endDate);
		       long difference = end.getTime() - start.getTime();
		       return ((int)(difference / (1000*60*60*24*7)) +1);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
	       
		return 0;
	}
	public TimeSheetVO getUsertimeSheets(String  clientEmail) {
		clientEmail =clientEmail.toLowerCase();
		TimeSheetVO userAllTimeSheet = new TimeSheetVO();
		Gson  json = new Gson();
		
		String userAllTimeSheetJson = MangoDB.getDocumentWithQuery("ppm","timesheets", clientEmail, null, true, MangoDB.mlabKeySonu, null);
		if (userAllTimeSheetJson != null && userAllTimeSheetJson.indexOf(clientEmail) > 0) {
			userAllTimeSheet = json.fromJson(userAllTimeSheetJson, new TypeToken<TimeSheetVO>() {}.getType());
			Collections.sort(userAllTimeSheet.getAllTimeSheets(), new AWeekTimeSheetComparator());
		}
		
		
		return userAllTimeSheet;
	}
	public Defaulters remindDefaulters(Defaulters defaulters) {
		for (String  toAddress: defaulters.getDefaulters()) {
			sendEmailMessage(toAddress,  "You have not submitted you ppm details. Please submit them in next 1 hour.", "Immediate Action required", defaulters.getManagerClientID());
			
		}
		return defaulters;
	}
	public EmailOTP sendMessage( String from,String message) {
		boolean includeInactive = false;
		List<LoginVO> reporteesList = new TimeSheetFacade().getReportees(from, includeInactive);
		String messageSentTo = message+"  This message has been sent to ";
		
		for (LoginVO aReportee: reporteesList) {
			sendEmailMessage(aReportee.get_id(),  message, "Please fill your timesheet details in advace", from);
			messageSentTo += " "+aReportee.get_id();
		}
		EmailOTP status = new EmailOTP();
		status.set_id(messageSentTo);
		return status;
	}
	
	private void sendEmailMessage(String toAddress, String message, String subject, String fromAddress ) {
		
		
		EmailVO emalVO = new EmailVO();
		emalVO.setUserName("personal.reminder.notification@gmail.com");
		emalVO.setPassword(MailService.key);
		emalVO.setSubject(subject);
		
		emalVO.setHtmlContent(message+" <br/><br/>"
				+ "https://ppmdetails.appspot.com/");
		EmailAddess from = new EmailAddess();
		if (fromAddress.indexOf("@") > 0) {
			fromAddress = fromAddress.substring(0, fromAddress.indexOf("@"));
			fromAddress = fromAddress.replace(".", " ").replaceAll("_", " ");
		}
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
		currentEntry.setUpdateTime(System.currentTimeMillis());
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
	 public Holidays  getHolidays(String clientEmail , String location) {
		 location = location.toLowerCase();
		 String domain = clientEmail.substring(clientEmail.indexOf("@")+1).toLowerCase();
	    	String holidaysJson = MangoDB.getDocumentWithQuery("ppm","holidays",location+"_"+ domain, null, true, MangoDB.mlabKeySonu, null) ;
			Gson  json = new Gson();
			Holidays holidays= json.fromJson(holidaysJson, new TypeToken<Holidays>() {}.getType());
			
			
			return holidays;
	    }

}
