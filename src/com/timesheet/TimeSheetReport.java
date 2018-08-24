package com.timesheet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.login.vo.LoginVO;
import com.timesheet.facade.TimeSheetFacade;
import com.timesheet.vo.AWeekTimeSheet;
import com.timesheet.vo.AWeekTimeSheetComparator;
import com.timesheet.vo.TimeSheetEntry;
import com.timesheet.vo.TimeSheetVO;

import mangodb.MangoDB;

/**
 * Servlet implementation class TimeSheetReport
 */
@WebServlet("/TimeSheetReport")
public class TimeSheetReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final SimpleDateFormat sdfIn = new SimpleDateFormat("yyyyMMdd");   
	public static final SimpleDateFormat sdfOut = new SimpleDateFormat("dd MMM yyyy"); 
	public static final SimpleDateFormat sdfReport = new SimpleDateFormat("dd-MMM-yyyy"); 
	private static Map<String, String> clientManagerName = new HashMap<String, String>();
	static {
		clientManagerName.put("ankush.goyal@morganstanley.com", "John");
		clientManagerName.put("kulbir.singh.walia@morganstanley.com", "John");
		clientManagerName.put("sandeep.hooda@morganstanley.com", "John");
		clientManagerName.put("neha.goswamy@morganstanley.com", "John");
		clientManagerName.put("manpreet.singh2@morganstanley.com", "John");
		clientManagerName.put("akriti.mahajan@morganstanley.com", "Krithika");
		
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TimeSheetReport() {
        super();
        // TODO Auto-generated constructor stub
    }

    private int getDate(String date) {
    	String[]  parts = date.split("/");
    	String join = parts[0];
    	if (parts[1].length() ==1) {
    		join += "0"+parts[1];
    	}else {
    		join += parts[1];
    	}
    	
    	if (parts[2].length() ==1) {
    		join += "0"+parts[2];
    	}else {
    		join += parts[2];
    	}
    	return Integer.parseInt(join);
    }
   
    private List<TimeSheetVO> getTimeSheetOfReportees(Set<String> reportees) {
    	String reeporteeQuery = "{\"_id\":{$in:[";//"]}}";
    	for (String aReportee: reportees) {
    		reeporteeQuery += "\""+aReportee+"\",";
    	}
    	reeporteeQuery = reeporteeQuery.substring(0, reeporteeQuery.length()-1);
    	reeporteeQuery += "]}}";
		try {
			reeporteeQuery =URLEncoder.encode(reeporteeQuery, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		reeporteeQuery = "&q="+reeporteeQuery;
		String timeSheetJson = "["+MangoDB.getDocumentWithQuery("ppm","timesheets", null, null, false, MangoDB.mlabKeySonu, reeporteeQuery)+"]" ;
		List<TimeSheetVO> reporteesTimeSheet = new ArrayList<TimeSheetVO>();
		if (null != timeSheetJson && timeSheetJson.trim().length() > 10) {
			Gson  json = new Gson();
			 reporteesTimeSheet = json.fromJson(timeSheetJson, new TypeToken<List<TimeSheetVO>>() {}.getType());
		}
		
		return reporteesTimeSheet;
		
		
    }
    private String timeSheetToCsv(int from, int to, List<AWeekTimeSheet> allReporteeAllweeks , boolean includeAuditData) {
    	StringBuilder sb = new StringBuilder("Date,Client Manager, Emp Name, Emp email ID,billable hours,Non Billable Hours,remarks");
    	if (includeAuditData) {
    		sb.append(", Date,version no, Historical edits");
    	}
    	sb.append("\r\n");
    	int previousdate = 0;
    	String previousManager = "";
    	for (AWeekTimeSheet aTimeSheet : allReporteeAllweeks) {
    		//String id = aReporteeTimeSheets.get_id();
    		//for (AWeekTimeSheet aTimeSheet: aReporteeTimeSheets.getAllTimeSheets()) {
    			int weekStartDate = aTimeSheet.getWeekStartDate();
    			if (weekStartDate>=from && weekStartDate<=to) {	
    				List<TimeSheetEntry> timeSheetEntry = aTimeSheet.getTimeSheetEntry();
    				Collections.sort(timeSheetEntry,new TimeSheetEntryComparator());
    				TimeSheetEntry entry = timeSheetEntry.get(0);
    				if (null == entry.getRemarks()) {
    					entry.setRemarks("");
    				}else {
    					entry.setRemarks(entry.getRemarks().replaceAll(",", "#"));
    				}
    				String empName = aTimeSheet.getEmailID();
    				empName = empName.substring(0, empName.indexOf("@")).replaceAll("[^A-Za-z]", " ");
    				
    				String dateToPrint = "";
    				if (previousdate != weekStartDate) {
    					previousdate = weekStartDate;
    					try {
							dateToPrint = sdfReport.format(sdfIn.parse(""+weekStartDate));
						} catch (ParseException e) {
							dateToPrint = ""+weekStartDate;
						}
    					previousManager = "";
    				}else {
    					dateToPrint = "";
    				}
    				
    				String managerToPrint = "";
    				if (!previousManager.equalsIgnoreCase(aTimeSheet.getClientManagerName())) {
    					previousManager = aTimeSheet.getClientManagerName();
    					managerToPrint = aTimeSheet.getClientManagerName();
    				}else {
    					managerToPrint = "";
    				}
    				sb.append(""+dateToPrint+","+managerToPrint+","+empName+","+aTimeSheet.getEmailID()+","+entry.getBillableHours()+","+entry.getNonBillableHours()+","+entry.getRemarks());
    				if (includeAuditData) {
    					sb.append(","+entry.getUpdateTime_Str()+","+entry.getEditVersion());
    					for (int i=1;i<timeSheetEntry.size();i++) {
        					entry = timeSheetEntry.get(i);
        					if (null == entry.getRemarks()) {
            					entry.setRemarks("");
            				}else {
            					entry.setRemarks(entry.getRemarks().replaceAll(",", "#"));
            				}
        					sb.append(","/*+weekStartDate+","+id+","*/+entry.getBillableHours()+","+entry.getNonBillableHours()+","+entry.getRemarks()+","+entry.getUpdateTime_Str()+","+entry.getEditVersion());
        					
        				}
    				}
    				
    				sb.append("\r\n");
    			}
    		//}
    	}
    	return sb.toString();
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String managerClientEmail = request.getParameter("id");
		int from = getDate(request.getParameter("from"));
		int to = getDate(request.getParameter("to"));
		String fileName = "timeSheet.csv";
		try {
			Date fromDate = sdfIn.parse(""+from);
			Date toDate = sdfIn.parse(""+to);
			fileName = sdfOut.format(fromDate) +" - "+sdfOut.format(toDate)+" .csv";
		} catch (ParseException e1) {
			
			e1.printStackTrace();
		}
		boolean includeAuditData = false;
		if ( "true".equalsIgnoreCase(request.getParameter("includeAuditData"))) {
			includeAuditData = true;
		}
		boolean includeInactive = true;
		List<LoginVO> reporteesList = new TimeSheetFacade().getReportees(managerClientEmail, includeInactive);
		
		Set<String> reporteesSet = new HashSet<String>();
		for (LoginVO aReportee: reporteesList) {
			reporteesSet.add(aReportee.get_id());
		}
		List<TimeSheetVO> reporteesTimeSheet =getTimeSheetOfReportees(reporteesSet);
		List<AWeekTimeSheet> timeSheets = new ArrayList<AWeekTimeSheet>();
		if (null !=reporteesTimeSheet) {
			for (TimeSheetVO auserAllTimeSheets:reporteesTimeSheet ) {
				for (AWeekTimeSheet aweekSheet: auserAllTimeSheets.getAllTimeSheets()) {
					aweekSheet.setEmailID(auserAllTimeSheets.get_id());
					aweekSheet.setClientManagerName(clientManagerName.get(aweekSheet.getEmailID()));
					if (null == aweekSheet.getClientManagerName()) {
						aweekSheet.setClientManagerName("NA");
					}
					timeSheets.add(aweekSheet);
				}
				
			}
		}
		Collections.sort(timeSheets, new AWeekTimeSheetComparator());
		
		 response.setContentType("text/csv");
		 response.setHeader("Content-Disposition", "attachment; filename=\""+fileName+"\"");
		 
		 try
		    {
		        OutputStream outputStream = response.getOutputStream();
		        String outputResult = timeSheetToCsv( from,  to,  timeSheets ,includeAuditData);
		        outputStream.write(outputResult.getBytes());
		        outputStream.flush();
		        outputStream.close();
		    }
		    catch(Exception e)
		    {
		       e.printStackTrace();
		    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
