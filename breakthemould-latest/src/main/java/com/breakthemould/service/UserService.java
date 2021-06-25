package com.breakthemould.service;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.breakthemould.domain.User;
import com.breakthemould.domain.UserVerificationToken;
import com.breakthemould.exception.PasswordValidationException;
import com.breakthemould.repositories.ChildRepository;
import com.breakthemould.repositories.UserRepository;
import com.breakthemould.repositories.UserVerificationTokenRepository;
import com.breakthemould.security.Authority;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ChildRepository childRepo;
	
	@Autowired
	private UserVerificationTokenRepository userVerificationTokenRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	public User findUserByUsername(String username) {
		return userRepo.findByUsername(username);
	}
	


	public void createPasswordResetToken(User user, String token,Integer timeValidity) {
		if(!tokenExists(user)) {
		UserVerificationToken userToken = new UserVerificationToken();
		userToken.setToken(token);
		userToken.setUser(user);
		userToken.setExpiryDate(LocalDateTime.now().plusHours(timeValidity));
		userVerificationTokenRepo.save(userToken);
		}
		else {
			UserVerificationToken verificationToken = userVerificationTokenRepo.findByUser(user);
			verificationToken.setToken(token);
			verificationToken.setExpiryDate(LocalDateTime.now().plusHours(timeValidity));
			userVerificationTokenRepo.save(verificationToken);
		}
		
	}
	
	public boolean tokenExists(User user) {
		UserVerificationToken token= userVerificationTokenRepo.findByUser(user);
		if(token!=null) return true;
		else
		return false;
		
	}


	public User findUserByVerificationToken(String token) {
		return userVerificationTokenRepo.findUserByToken(token);
	}


	public LocalDateTime findExpiryTime(String token) {
		return userVerificationTokenRepo.findExpiryByToken(token);
	}

	//Password Reset
	public void saveNewPassword(User user,String password,String token) {
		if(password.length()<8 || password.length()>20) {
			throw new PasswordValidationException("Password requirements not met");
		}
		String encodedPassword = passwordEncoder.encode(password);
		user.setPassword(encodedPassword);
		userRepo.save(user);
		userVerificationTokenRepo.delete(userVerificationTokenRepo.findByToken(token));
		
	}


	//New Registration
	public void saveNewChildPassword(User user, String password, String token) {
		if(password.length()<8 || password.length()>20) {
			throw new PasswordValidationException("Password requirements not met");
		}
		String encodedPassword = passwordEncoder.encode(password);
		user.setPassword(encodedPassword);
		Authority authority = new Authority();
		authority.setAuthority("ROLE_CHILD");
		authority.setUser(user);
		user.getAuthorities().add(authority);
		userRepo.save(user);
		userVerificationTokenRepo.delete(userVerificationTokenRepo.findByToken(token));
	}



}
