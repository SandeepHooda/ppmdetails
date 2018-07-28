package com.timesheet.EndPoint;

import javax.ws.rs.core.Response;

import com.login.vo.LoginVO;
import com.myprofile.facade.MyProfileFacade;
import com.timesheet.facade.TimeSheetFacade;

public class TimeSheetEndpointImpl implements TimeSheetEndpoint {
	private TimeSheetFacade timeSheetFacade;


	public TimeSheetFacade getTimeSheetFacade() {
		return timeSheetFacade;
	}
	public void setTimeSheetFacade(TimeSheetFacade timeSheetFacade) {
		this.timeSheetFacade = timeSheetFacade;
	}
	@Override
	public Response getUsertimeSheets(String clientEmail) {
		try{
			
			return Response.ok().entity(timeSheetFacade.getUsertimeSheets(clientEmail)).build();
		}catch(Exception e){
			e.printStackTrace();
			LoginVO vo = new LoginVO();
			vo.setErrorMessage("Internal Server Error ");
			
			return Response.serverError().entity(vo).build();
		}
	}
	
	
	

}
