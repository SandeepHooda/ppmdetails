package com.login.EndPoint;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import com.login.facade.LoginFacade;
import com.login.vo.LoginVO;

public class LoginEndpointImpl implements LoginEndpoint {
	private LoginFacade loginFacade;
	public LoginFacade getLoginFacade() {
		return loginFacade;
	}

	public void setLoginFacade(LoginFacade loginFacade) {
		this.loginFacade = loginFacade;
	}

	@Override
	public Response login( HttpServletRequest request) {
		try{
			
			return Response.ok().entity("").build();
		}catch(Exception e){
			e.printStackTrace();
			LoginVO vo = new LoginVO();
			vo.setErrorMessage("Internal Server Error ");
			
			return Response.serverError().entity(vo).build();
		}
	}
	

}
