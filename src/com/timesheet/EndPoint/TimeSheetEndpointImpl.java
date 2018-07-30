package com.timesheet.EndPoint;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.login.vo.LoginVO;
import com.timesheet.facade.TimeSheetFacade;
import com.timesheet.vo.Defaulters;
import com.timesheet.vo.TimeSheetUpdateVo;

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
	@Override
	public Response remindDefaulters( Defaulters defaulters) {
		try{
			
			return Response.ok().entity(timeSheetFacade.remindDefaulters(defaulters)).build();
		}catch(Exception e){
			e.printStackTrace();
			LoginVO vo = new LoginVO();
			vo.setErrorMessage("Internal Server Error ");
			
			return Response.serverError().entity(vo).build();
		}	
	}
	@Override
	public Response getDefaulterListForManager(String managerClientEmail) {
		try{
			
			return Response.ok().entity(timeSheetFacade.getDefaulterListForManager(managerClientEmail)).build();
		}catch(Exception e){
			e.printStackTrace();
			LoginVO vo = new LoginVO();
			vo.setErrorMessage("Internal Server Error ");
			
			return Response.serverError().entity(vo).build();
		}
	}
	@Override
	public Response  sendMessage( String from,String message) {
		try{
			
			return Response.ok().entity(timeSheetFacade.sendMessage(from, message)).build();
		}catch(Exception e){
			e.printStackTrace();
			LoginVO vo = new LoginVO();
			vo.setErrorMessage("Internal Server Error ");
			
			return Response.serverError().entity(vo).build();
		}
	}
	@Override
	public Response submitTimeSheet(TimeSheetUpdateVo timeSheetUpdateVo) {
		
		try{
			return Response.ok().entity(timeSheetFacade.submitTimeSheet(timeSheetUpdateVo.getClientEmail(),timeSheetUpdateVo.getCurrentEntry(),timeSheetUpdateVo.getCurrentEntryDate())).build();
		}catch(Exception e){
			e.printStackTrace();
			LoginVO vo = new LoginVO();
			vo.setErrorMessage("Internal Server Error ");
			
			return Response.serverError().entity(vo).build();
		}
	}
	@Override
	public Response reworkTimeSheet(TimeSheetUpdateVo timeSheetUpdateVo) {
		
		try{
			return Response.ok().entity(timeSheetFacade.submitTimeSheet(timeSheetUpdateVo.getClientEmail(),timeSheetUpdateVo.getCurrentEntry(),timeSheetUpdateVo.getCurrentEntryDate())).build();
		}catch(Exception e){
			e.printStackTrace();
			LoginVO vo = new LoginVO();
			vo.setErrorMessage("Internal Server Error ");
			
			return Response.serverError().entity(vo).build();
		}
	}
	
	
	

}
