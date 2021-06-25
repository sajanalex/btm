package com.breakthemould.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping(path = "/pas-sport/school")
@PreAuthorize("hasRole('SCHOOL')")
@Tag(name = "School",description = "School Registration and children's activities, performance API")
public class SchoolController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SchoolController.class);
	
	@GetMapping("/performance")
	@PreAuthorize("hasAnyRole('ADMIN','SCHOOL','CHILD')")
	@Operation(summary = "View the performance of the school")
	public ResponseEntity<?> viewPerformance(){
		return null;
		
	}

}
