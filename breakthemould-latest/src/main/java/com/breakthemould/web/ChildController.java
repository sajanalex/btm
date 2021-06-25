package com.breakthemould.web;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.breakthemould.domain.Child;
import com.breakthemould.domain.ChildActivity;
import com.breakthemould.domain.School;
import com.breakthemould.domain.ScoreBoard;
import com.breakthemould.domain.User;
import com.breakthemould.email.service.EmailService;
import com.breakthemould.exception.InvalidTokenException;
import com.breakthemould.repositories.ChildActivityRepository;
import com.breakthemould.repositories.ChildRepository;
import com.breakthemould.repositories.ScoreBoardRepository;
import com.breakthemould.repositories.UserRepository;
import com.breakthemould.service.ChildActivityService;
import com.breakthemould.service.ChildService;
import com.breakthemould.service.SchoolService;
import com.breakthemould.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping(path = "/pas-sport/child")
@Tag(name = "Child",description = "Child Registration and activities API")
public class ChildController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ChildController.class);
	
	@Autowired
	private ChildRepository childRepo;
	
	@Autowired
	private ChildActivityRepository activityRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ScoreBoardRepository scoreBoardRepo;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ChildService childService;
	
	@Autowired
	private ChildActivityService childActivityService;
	
	@Autowired
	private SchoolService schoolService;
	
	@Autowired
	private EmailService emailService;
	
	@GetMapping("/profile")
	@PreAuthorize("hasRole('CHILD')")
	@Operation(summary = "View the Logged in Child Profile, Challengers are friends who accepted challenging, "
			+ "Inviters are who are ready to challenge and accept their invitation if I like. View my total score,"
			+ "badge and avatar")
	@ApiResponses(value = {@ApiResponse(responseCode = "200",description = "OK")})
	public ResponseEntity<?> viewChild() {
		Map<String, Object> model = new HashMap<String, Object>();
		String uname =SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepo.findByUsername(uname);
		Child pupil = childRepo.findByIdWithUser(user.getUsername());
//		List<ChildActivity> activities = activityRepo.findByChildId(pupil.getId());
		ScoreBoard score = scoreBoardRepo.findByChildId(pupil.getId());
		List<Child> challengers = childRepo.findByChallengers(pupil.getId());
		List<Child> inviters = childRepo.findByCanChallenge(pupil.getId());
		
		model.put("pupil", pupil);
//		model.put("activities", activities);
		model.put("score", score);
		model.put("challengers", challengers);
		model.put("inviters", inviters);
		return new ResponseEntity<>(model,HttpStatus.OK);
	}
	
	@PutMapping("/update_profile")
	@PreAuthorize("hasRole('CHILD')")
	@Operation(summary = "Update the Logged in Child Profile, update bio,avatar,can_challenge status")
	public ResponseEntity<?> updateProfile(){
		return null;
		
	}
	
	@GetMapping("/performance")
	@PreAuthorize("hasRole('CHILD')")
	@Operation(summary = "View the performance of the logged in Child compare with school average,"
			+ "class average and with each challengers.")
	@ApiResponses(value = {@ApiResponse(responseCode = "200",description = "OK")})
	public ResponseEntity<?> viewPerformance(){
		Map<String, Object> model = new HashMap<String, Object>();
		String uname =SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepo.findByUsername(uname);
		
		Child pupil = childRepo.findByIdWithUser(user.getUsername());
		List<ChildActivity> activities = activityRepo.findByChildId(pupil.getId());
		List<Child> challengers = childRepo.findByChallengers(pupil.getId());
		Integer schoolAverage = schoolService.schoolAverageScore(pupil.getSchool().getId());
		Integer classAverage = schoolService.classAverageScore(pupil.getYearGroup(),pupil.getSchool().getId());
		
		model.put("pupil", pupil);
		model.put("activities", activities);
		model.put("challengers", challengers);
		model.put("schoolAverage", schoolAverage);
		model.put("classAverage", classAverage);
		return new ResponseEntity<>(model,HttpStatus.OK);
	}
	
	@PostMapping("/activity_update/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','SCHOOL')")
	@Operation(summary = "Update the avitivities of child")
	@ApiResponses(value = {@ApiResponse(responseCode = "201",description = "CREATED")})
//	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	public void updateActivities(@PathVariable Long id,@RequestBody List<ChildActivity> childActivities){

		childActivityService.saveChildActivities(id,childActivities);

		
	}
	
	@GetMapping("/register")
	@Operation(summary = "Registration form of child with the listed schools")
	@ApiResponses(value = {@ApiResponse(responseCode = "200",description = "OK")})
	public ResponseEntity<?> registerChildView(){
		List<School> schools = schoolService.listAllSchools();
		return ResponseEntity.ok(schools);
		
	}
	
	@PostMapping("/register/{school_id}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Registration of Child and sending activation mail after uniqueness check with first, last names and email id")
	@ApiResponses(value = {@ApiResponse(responseCode = "200",description = "OK")})
	public ResponseEntity<?> registerChild(@RequestBody Child pupil,@PathVariable Integer school_id){
		LOGGER.info("POST /register", pupil);
		
		Child child =childService.registerChild(pupil,school_id);
		emailService.sendChildActivationMail(child.getEmail(),child.getUser());
		return ResponseEntity.ok("Activation mail sent to "+child.getEmail()+". Please check your inbox");
		
	}
	
	
	@PostMapping("/activate")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Activating child with valid token from mail link")
	@ApiResponses(value = {@ApiResponse(responseCode = "201",description = "CREATED")})
	public ResponseEntity<?> activateChild(@RequestParam String token,@RequestBody User newUser){
		LOGGER.info("POST /activate/{}", newUser.getUsername());
		User user = userService.findUserByVerificationToken(token); 
		LocalDateTime expiryTime = userService.findExpiryTime(token);
		if(user==null) {
			throw new InvalidTokenException("Invalid Token");
		}
		if(expiryTime.isBefore(LocalDateTime.now())) {
			throw new InvalidTokenException("Token Expired");
		}
		userService.saveNewChildPassword(user, newUser.getPassword(), token);
		URI location = UriComponentsBuilder.fromPath("/child/login").build().toUri();
		return ResponseEntity.created(location).body("username "+user.getUsername()+" activated successfully. Please Login");
		
	}
	


}
