package com.login.EndPoint;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import com.login.facade.LoginFacade;
import com.login.vo.LoginVO;
import com.login.vo.UpdateObo;

public class LoginEndpointImpl implements LoginEndpoint {
	private LoginFacade loginFacade;
	public LoginFacade getLoginFacade() {
		return loginFacade;
	}

	public void setLoginFacade(LoginFacade loginFacade) {
		this.loginFacade = loginFacade;
	}

	@Override
	public Response login(LoginVO loginVO, HttpServletRequest request) {
		try{
			
			return Response.ok().entity(loginFacade.login(loginVO)).build();
		}catch(Exception e){
			e.printStackTrace();
			LoginVO vo = new LoginVO();
			vo.setErrorMessage("Internal Server Error ");
			
			return Response.serverError().entity(vo).build();
		}
	}
	
	@Override
	public Response validateOtp(String otp) {
		try{
			
			return Response.ok().entity(loginFacade.validateOtp(otp)).build();
		}catch(Exception e){
			e.printStackTrace();
			LoginVO vo = new LoginVO();
			vo.setErrorMessage("Internal Server Error ");
			
			return Response.serverError().entity(vo).build();
		}
	}
	@Override
	public Response isUserVerified(String otp) {
		try{
			
			return Response.ok().entity(loginFacade.isUserVerified(otp)).build();
		}catch(Exception e){
			e.printStackTrace();
			LoginVO vo = new LoginVO();
			vo.setErrorMessage("Internal Server Error ");
			
			return Response.serverError().entity(vo).build();
		}
	}
	
	@Override
	public Response updateOboRole(UpdateObo updateObo) {
		try{
			
			return Response.ok().entity(loginFacade.updateOboRole(updateObo)).build();
		}catch(Exception e){
			e.printStackTrace();
			LoginVO vo = new LoginVO();
			vo.setErrorMessage("Internal Server Error ");
			
			return Response.serverError().entity(vo).build();
		}
	}

}
