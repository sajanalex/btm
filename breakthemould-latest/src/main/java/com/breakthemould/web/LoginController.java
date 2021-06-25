package com.breakthemould.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.breakthemould.jwt.model.AuthenticationRequest;
import com.breakthemould.jwt.model.AuthenticationResponse;
import com.breakthemould.jwt.util.JwtUtil;
import com.breakthemould.service.UserDetailServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/pas-sport/child")
@Tag(name = "Pas-Sport Login",description = "Pas-sport login to get the JWT Token")
public class LoginController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailServiceImpl userDetailsService;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@PostMapping("/login")
	@Operation(summary = "Returns JWT Token in Response body")
	@ApiResponses(value = {@ApiResponse(responseCode = "200",description = "OK"),
			@ApiResponse(responseCode = "403",description = "FORBIDDEN")})
	public ResponseEntity<?> createAuthenticationToken(
			@RequestBody AuthenticationRequest authenticationRequest) {
		LOGGER.info("POST /login");
		try {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
				(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch(BadCredentialsException e) {
			throw new RuntimeException("Invalid Username and Password",e);
		}
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername
				(authenticationRequest.getUsername());
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
		
	}

}
