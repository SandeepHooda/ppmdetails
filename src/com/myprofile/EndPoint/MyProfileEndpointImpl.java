package com.myprofile.EndPoint;

import javax.ws.rs.core.Response;

import com.login.vo.LoginVO;
import com.myprofile.facade.MyProfileFacade;

public class MyProfileEndpointImpl implements MyProfileEndpoint {
	private MyProfileFacade myProfileFacade;

	public MyProfileFacade getMyProfileFacade() {
		return myProfileFacade;
	}

	public void setMyProfileFacade(MyProfileFacade myProfileFacade) {
		this.myProfileFacade = myProfileFacade;
	}

	@Override
	public Response getMyProfile(String clientEmail) {
			try{
				
			return Response.ok().entity(myProfileFacade.getMyProfile(clientEmail)).build();
		}catch(Exception e){
			e.printStackTrace();
			LoginVO vo = new LoginVO();
			vo.setErrorMessage("Internal Server Error ");
			
			return Response.serverError().entity(vo).build();
		}
	}
	@Override
	public Response deleteMyProfile(String clientEmail) {
			try{
				
			return Response.ok().entity(myProfileFacade.deleteMyProfile(clientEmail)).build();
		}catch(Exception e){
			e.printStackTrace();
			LoginVO vo = new LoginVO();
			vo.setErrorMessage("Internal Server Error ");
			
			return Response.serverError().entity(vo).build();
		}
	}

	@Override
	public Response updateMyProfile(LoginVO loginVO) {
		try{
			
			return Response.ok().entity(myProfileFacade.updateMyProfile(loginVO)).build();
		}catch(Exception e){
			e.printStackTrace();
			LoginVO vo = new LoginVO();
			vo.setErrorMessage("Internal Server Error ");
			
			return Response.serverError().entity(vo).build();
		}
	}
	
	
	

}
