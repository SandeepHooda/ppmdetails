package com.timesheet.EndPoint;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.login.vo.LoginVO;


@Path("myprofile")
public interface TimeSheetEndpoint {
	
	@GET
	@Path("/{clientEmail}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUsertimeSheets(@PathParam("clientEmail") String clientEmail);

	
	
	

}
