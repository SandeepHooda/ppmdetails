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
import com.timesheet.facade.TimeSheetFacade;
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

   
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Set<String> defaulters =new TimeSheetFacade().getDefaulterList(null);//All defaulters across all managers
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
