package com.myprofile.facade;



import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import com.communication.email.EmailAddess;
import com.communication.email.EmailVO;
import com.communication.email.MailService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.login.facade.LoginFacade;
import com.login.vo.EmailOTP;
import com.login.vo.LoginVO;

import mangodb.MangoDB;

public class MyProfileFacade {
	private static final Logger log = Logger.getLogger(MyProfileFacade.class.getName());
	
	public LoginVO getMyProfile(String  clientEmail) {
		clientEmail =clientEmail.toLowerCase();
		LoginVO userFromDB = new LoginVO();
		Gson  json = new Gson();
		
		String isUserPresent = MangoDB.getDocumentWithQuery("ppm","registered-users", clientEmail, null, true, MangoDB.mlabKeySonu, null);
		if (isUserPresent != null && isUserPresent.indexOf(clientEmail) > 0) {
			userFromDB = json.fromJson(isUserPresent, new TypeToken<LoginVO>() {}.getType());
			
		}
		
		
		return userFromDB;
	}
	public LoginVO deleteMyProfile(String  clientEmail) {
		clientEmail =clientEmail.toLowerCase();
		LoginVO userFromDB = new LoginVO();
		Gson  json = new Gson();
		
		String isUserPresent = MangoDB.getDocumentWithQuery("ppm","registered-users", clientEmail, null, true, MangoDB.mlabKeySonu, null);
		if (isUserPresent != null && isUserPresent.indexOf(clientEmail) > 0) {
			userFromDB = json.fromJson(isUserPresent, new TypeToken<LoginVO>() {}.getType());
			userFromDB.setInactive(true);
			updateMyProfile(userFromDB);
			
		}
		
		
		return userFromDB;
	}
	public LoginVO updateMyProfile(LoginVO loginVO) {
		
		String isUserPresent = MangoDB.getDocumentWithQuery("ppm","registered-users", loginVO.getClientEmail(), null, true, MangoDB.mlabKeySonu, null);
		if (isUserPresent != null && isUserPresent.indexOf(loginVO.getClientEmail()) > 0) {
			Gson  json = new Gson();
			String loginVOJson = json.toJson(loginVO, new TypeToken<LoginVO>() {}.getType());
			MangoDB.updateData("ppm","registered-users",  loginVOJson, loginVO.getClientEmail(),MangoDB.mlabKeySonu);
		}
		
		
		return loginVO;
	}
	
	
	

}
