package com.reminder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.communication.email.EmailAddess;
import com.communication.email.EmailVO;
import com.communication.email.MailService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.login.vo.EmailOTP;
import com.login.vo.LoginVO;
import com.timesheet.vo.AWeekTimeSheet;
import com.timesheet.vo.TimeSheetVO;

import mangodb.MangoDB;

/**
 * Servlet implementation class TimeSheetReminder
 */
@WebServlet("/TimeSheetReminder")
public class TimeSheetReminder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TimeSheetReminder() {
        super();
        // TODO Auto-generated constructor stub
    }

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
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		defaulters.addAll(activeUsersSet);
		for (TimeSheetVO aUserTimeSheets: allTimeShets) {
			if (activeUsersSet.contains(aUserTimeSheets.get_id())) {
				boolean defaulter = true;
				for (AWeekTimeSheet aWeekTime : aUserTimeSheets.getAllTimeSheets()) {
					if (aWeekTime.getWeekStartDate() == recentMonday) {
						defaulter = false;
						defaulters.remove(aUserTimeSheets.get_id());
						break;
					}
				}
				
			}
		}
		
		for (String defaulter: defaulters) {
			System.out.println(" defaulter "+defaulter);
			sendReminderEmail(defaulter);
		}
	}
	
	private void sendReminderEmail(String toAddress) {
		
		
		EmailVO emalVO = new EmailVO();
		emalVO.setUserName("personal.reminder.notification@gmail.com");
		emalVO.setPassword(MailService.key);
		emalVO.setSubject("Please fill your timesheet details");
		
		emalVO.setHtmlContent("Please click on the below link to fill your timesheet details. <br/><br/>"
				+ "https://ppmdetails.appspot.com/");
		EmailAddess from = new EmailAddess();
		
		from.setAddress(emalVO.getUserName());
		
		List<EmailAddess> receipients = new ArrayList<>();
		EmailAddess to = new EmailAddess();
		to.setAddress(toAddress);
		emalVO.setFromAddress(from);
		receipients.add(to);
		emalVO.setToAddress(receipients);
		MailService.sendSimpleMail(emalVO);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
