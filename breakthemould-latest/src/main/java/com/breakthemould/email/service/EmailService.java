package com.breakthemould.email.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.breakthemould.domain.User;
import com.breakthemould.service.UserService;

@Service
public class EmailService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JavaMailSender mailSender;

	public void sendChildActivationMail(String email, User user) {

		String token = UUID.randomUUID().toString();
		Integer timeValidity = 24;
		userService.createPasswordResetToken(user, token, timeValidity);
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("pas-sport@btm.com");
		mailMessage.setTo(email);
		mailMessage.setSubject("Child Activation Email");
		mailMessage.setText("Your Username is your Pas-sport Number "+user.getUsername()
		+" \n Please click on the link to activate your account and set your password within 24 hours \n "+
				"http://localhost:8080/pas-sport/child/activate?token="+token);
		mailSender.send(mailMessage);
				
		
	}

}
