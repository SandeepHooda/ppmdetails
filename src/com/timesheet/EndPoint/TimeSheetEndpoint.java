package com.timesheet.EndPoint;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.login.vo.LoginVO;
import com.timesheet.vo.Defaulters;
import com.timesheet.vo.TimeSheetEntry;
import com.timesheet.vo.TimeSheetUpdateVo;
import com.timesheet.vo.TimeSheetVO;


@Path("timesheet")
public interface TimeSheetEndpoint {
	
	@GET
	@Path("/{clientEmail}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUsertimeSheets(@PathParam("clientEmail") String clientEmail);
	@GET
	@Path("/defaulter/{clientEmail}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getDefaulterListForManager(@PathParam("clientEmail") String managerClientEmail);
	@POST
	@Path("/remindDefaulters")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response remindDefaulters( Defaulters defaulters);
	
	@GET
	@Path("/sendmessage/{from}/{message}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response sendMessage(@PathParam("from") String from,@PathParam("message") String message);
	
	@POST
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response submitTimeSheet( TimeSheetUpdateVo timeSheetUpdateVo);
	
	@PUT
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response reworkTimeSheet( TimeSheetUpdateVo timeSheetUpdateVo);


	
	
	

}
