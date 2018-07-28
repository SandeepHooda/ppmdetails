package com.timesheet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.login.vo.LoginVO;

import mangodb.MangoDB;

/**
 * Servlet implementation class TimeSheetReport
 */
@WebServlet("/TimeSheetReport")
public class TimeSheetReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TimeSheetReport() {
        super();
        // TODO Auto-generated constructor stub
    }

    private int getDate(String date) {
    	String[]  parts = date.split("\\/");
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
    private Set<String>  getReportees(String managerClientEmail) {
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
		
		List<LoginVO> reporteeList= json.fromJson(reporteeJson, new TypeToken<List<LoginVO>>() {}.getType());
		Set<String> reportees = new HashSet<String>();
		if (null != reporteeList && reporteeList.indexOf("null") <0) {
			for (LoginVO aReportee: reporteeList) {
				reportees.add(aReportee.get_id());
			}
		}
		
		
		return reportees;
    }
    private void getTimeSheetOfReportees(Set<String> reportees) {
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
		System.out.println(timeSheetJson);
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String managerClientEmail = request.getParameter("id");
		int from = getDate(request.getParameter("from"));
		int to = getDate(request.getParameter("to"));
		
		Set<String> reportees = getReportees(managerClientEmail);
		getTimeSheetOfReportees(reportees);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
