package com.login.EndPoint;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.login.vo.LoginVO;
import com.login.vo.UpdateObo;


@Path("")
public interface LoginEndpoint {
	
	

	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response login(LoginVO loginVO, @Context HttpServletRequest request);
	
	@GET
	@Path("/login/validate/{otp}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response validateOtp(@PathParam("otp") String otp);
	@GET
	@Path("/login/isUserVerified/{otp}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response isUserVerified(@PathParam("otp") String otp);
	
	@POST
	@Path("/login/updateOboRole")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateOboRole(UpdateObo updateObo);
	

}
