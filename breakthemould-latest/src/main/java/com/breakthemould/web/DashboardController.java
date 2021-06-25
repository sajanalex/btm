package com.breakthemould.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Pas-sport Home",description = "Pas-sport Home Page for Child and School zone")
public class DashboardController {
	@RequestMapping(value = "/pas-sport/home",method = RequestMethod.GET)
	public ResponseEntity<?> displayHomePage(){
		return ResponseEntity.ok("Pas-sport Home Page");
		
	}

}
