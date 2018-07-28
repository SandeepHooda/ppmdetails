package com.myprofile.EndPoint;

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
public interface MyProfileEndpoint {
	
	

	
	@GET
	@Path("/{clientEmail}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getMyProfile(@PathParam("clientEmail") String clientEmail);
	
	@DELETE
	@Path("/{clientEmail}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteMyProfile(@PathParam("clientEmail") String clientEmail);
	
	
	@PUT
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateMyProfile(LoginVO loginVO);
	
	
	

}
