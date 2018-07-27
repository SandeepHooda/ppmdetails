package com.login.facade;



import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import com.communication.email.EmailAddess;
import com.communication.email.EmailVO;
import com.communication.email.MailService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.login.vo.EmailOTP;
import com.login.vo.LoginVO;

import mangodb.MangoDB;

public class LoginFacade {
	private static final Logger log = Logger.getLogger(LoginFacade.class.getName());
	
	public LoginVO login(LoginVO loginVO) {
		
		Gson  json = new Gson();
		String loginVOJson = json.toJson(loginVO, new TypeToken<LoginVO>() {}.getType());
		String isUserPresent = MangoDB.getDocumentWithQuery("ppm","registered-users", loginVO.getClientEmail(), null, true, MangoDB.mlabKeySonu, null);
		if (isUserPresent != null && isUserPresent.indexOf(loginVO.getClientEmail()) > 0) {
			
		}else {
			MangoDB.createNewDocumentInCollection("ppm","registered-users",  loginVOJson, MangoDB.mlabKeySonu);
		}
		;
		loginVO.setEmailOTP(sendVerificationEmail( loginVO));
		System.out.println(loginVO.getEmailOTP());
		return loginVO;
	}
	private String sendVerificationEmail(LoginVO loginVO) {
		EmailOTP emailOTP = new EmailOTP() ;
		 UUID uuid = UUID.randomUUID();
		emailOTP.set_id(uuid.toString());
		Gson  json = new Gson();
		String uuidJson = json.toJson(emailOTP, new TypeToken<EmailOTP>() {}.getType());
		MangoDB.createNewDocumentInCollection("ppm","email-verification",  uuidJson, MangoDB.mlabKeySonu);
		
		EmailVO emalVO = new EmailVO();
		emalVO.setUserName("personal.reminder.notification@gmail.com");
		emalVO.setPassword(MailService.key);
		emalVO.setSubject("Please verify your email");
		String managerName = loginVO.getManagerInfyEmail().substring(0, loginVO.getManagerInfyEmail().indexOf("@"));
		managerName = managerName.replaceAll("[^A-Za-z]", " ");
		System.out.println(managerName);
		emalVO.setHtmlContent("Please click on the below link to verify your email. <br/><br/>"
				+ "https://ppmdetails.appspot.com/ws/login/validate/"+emailOTP.get_id()+"<br/>"+managerName);
		EmailAddess from = new EmailAddess();
		from.setLabel(loginVO.getManagerInfyEmail());
		from.setAddress(emalVO.getUserName());
		
		List<EmailAddess> receipients = new ArrayList<>();
		EmailAddess to = new EmailAddess();
		to.setAddress(loginVO.getClientEmail());
		emalVO.setFromAddress(from);
		receipients.add(to);
		emalVO.setToAddress(receipients);
		MailService.sendSimpleMail(emalVO);
		return emailOTP.get_id();
	}
	
	public String validateOtp(String otp) {
		
		
		
		String emailOtpStr = MangoDB.getDocumentWithQuery("ppm","email-verification", otp, null, true, MangoDB.mlabKeySonu, null);
		Gson  json = new Gson();
		EmailOTP emailOtp = json.fromJson(emailOtpStr, new TypeToken<EmailOTP>() {}.getType());
		emailOtp.setVerified(true);
		
		emailOtpStr = json.toJson(emailOtp, new TypeToken<EmailOTP>() {}.getType());
		MangoDB.updateData("ppm","email-verification",emailOtpStr, emailOtp.get_id(),  MangoDB.mlabKeySonu);
		return "Your email has been verified. Now you may able to loging to the application.";
		
	}
	public EmailOTP isUserVerified(String otp) {
		
		
		
		String emailOtpStr = MangoDB.getDocumentWithQuery("ppm","email-verification", otp, null, true, MangoDB.mlabKeySonu, null);
		Gson  json = new Gson();
		EmailOTP emailOtp = json.fromJson(emailOtpStr, new TypeToken<EmailOTP>() {}.getType());
		
		return emailOtp;
		
	}
	

}
