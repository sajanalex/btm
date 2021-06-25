package com.breakthemould.web;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.breakthemould.domain.Child;
import com.breakthemould.domain.User;
import com.breakthemould.exception.InvalidTokenException;
import com.breakthemould.exception.UserNotFoundException;
import com.breakthemould.service.ChildService;
import com.breakthemould.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/pas-sport/child" )
@Tag(name = "Password Reset",description = "Password Reset and User name reminding")
public class PasswordResetController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ChildService childService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	
	@PostMapping("/reset_password")
	@Operation(summary = "Password reset link sending to the email registered with the given Pas-sport number if the user forgot password")
	@ApiResponses(value = {@ApiResponse(responseCode = "200",description = "OK")})
	public ResponseEntity<?> resetPassword(@RequestBody Map<String, Object> userMap){
		String username = (String)userMap.get("username");
		User user = userService.findUserByUsername(username);
		if(user==null) {
			throw new UserNotFoundException("Invalid Pas-sport Number");
		}else 
		if(user.getPassword()==null) {
			throw new UserNotFoundException("User not activated.Please activate");
		}
		String token = UUID.randomUUID().toString();
		Integer timeValidity = 2;
		userService.createPasswordResetToken(user,token,timeValidity);
		String email = childService.findEmailByUsername(username);
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		
		mailMessage.setFrom("sajan@btm.com");
		mailMessage.setTo(email);
		mailMessage.setSubject("Password Reset Link");
		mailMessage.setText("Click on this link to reset the password within 2 hours "+
					"http://localhost:8080/pas-sport/child/confirm_reset?token="+token);
		mailSender.send(mailMessage);
		
		return ResponseEntity.ok("Email Sent");
		
	}
	

	@PostMapping("/confirm_reset")
	@Operation(summary = "Password resets with the verified token from mail link")
	@ApiResponses(value = {@ApiResponse(responseCode = "200",description = "OK")})
	public ResponseEntity<?> saveNewPassword(@RequestParam String token,@RequestBody User resetUser){
		User user = userService.findUserByVerificationToken(token); 
		LocalDateTime expiryTime = userService.findExpiryTime(token);
		if(user==null) {
			throw new InvalidTokenException("Invalid Token");
		}
		if(expiryTime.isBefore(LocalDateTime.now())) {
			throw new InvalidTokenException("Token Expired");
		}
		userService.saveNewPassword(user,resetUser.getPassword(),token);
		return ResponseEntity.ok("username "+user.getUsername()+" password reset complete. Go to Login");
		
	}
	
	@PostMapping("/remind_username")
	@Operation(summary = "Reminds forgotten username with matching firstname,lastname and email")
	@ApiResponses(value = {@ApiResponse(responseCode = "200",description = "OK")})
	public ResponseEntity<?> remindUsername(@RequestBody Child child){
		if(childService.childExists(child.getFirstName(), child.getLastName(), child.getEmail())) {
			Child remindChild = childService.findByEmailWithFirstAndLastName(child.getFirstName(), child.getLastName(), child.getEmail());
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			
			mailMessage.setFrom("sajan@btm.com");
			mailMessage.setTo(remindChild.getEmail());
			mailMessage.setSubject("Username Reminder");
			mailMessage.setText("Your Username/Pas-sport Number is: "+remindChild.getUser().getUsername());
			mailSender.send(mailMessage);
		}
		return ResponseEntity.ok("Your Username sent to your email");
		
	}

}
