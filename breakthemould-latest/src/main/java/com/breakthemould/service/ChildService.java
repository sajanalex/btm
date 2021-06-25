package com.breakthemould.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.breakthemould.domain.Child;
import com.breakthemould.domain.School;
import com.breakthemould.domain.User;
import com.breakthemould.exception.UserAlreadyExistsException;
import com.breakthemould.repositories.ChildRepository;
import com.breakthemould.repositories.SchoolRepository;
import com.breakthemould.repositories.UserRepository;
import com.breakthemould.repositories.UserVerificationTokenRepository;

@Service
public class ChildService {
	
	@Autowired
	private ChildRepository childRepo;
	
	@Autowired
	private SchoolRepository schoolRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserVerificationTokenRepository userTokenRepo;
	
	@Autowired
	private UserService userService;
	

	

	public Child registerChild(Child pupil,Integer school_id) {
		
		
		if(childExists(pupil.getFirstName(), pupil.getLastName(), pupil.getEmail())) {
			Child inActiveChild = findByEmailWithFirstAndLastName(pupil.getFirstName(), pupil.getLastName(), pupil.getEmail());
			if(userService.tokenExists(inActiveChild.getUser())) {
				userTokenRepo.delete(userTokenRepo.findByUser(inActiveChild.getUser()));
				return inActiveChild;
			}else
				throw new UserAlreadyExistsException("This user already exists. Reset your password if you forget login details");
		}
		Child child = new Child();
		User user = new User();
		child.setFirstName(pupil.getFirstName());
		child.setLastName(pupil.getLastName());
		child.setDob(pupil.getDob());
		child.setGender(pupil.getGender());
		child.setEthnicity(pupil.getEthnicity());
		child.setPhone(pupil.getPhone());
		child.setEmail(pupil.getEmail());
		child.setYearGroup(pupil.getYearGroup());
		child.setAddress(pupil.getAddress());
		child.setSchool(pupil.getSchool());
		School schoolOfChild = schoolRepo.findById(school_id).orElseThrow(()->
									new IllegalArgumentException("School Id does not exist"));
		
		child.setSchool(schoolOfChild);
		
		Child topchild = childRepo.findTopByOrderByIdDesc();
		Long userId= topchild.getId()+10001;
		String username= "BPN"+userId;
		user.setUsername(username);
		userRepo.save(user);
		child.setUser(user);
		
		return childRepo.save(child);
	}
	
	public Child findByEmailWithFirstAndLastName(String firstName,String lastName,String email) {
		return childRepo.findByEmailWithFirstLastName(firstName,lastName,email);
	}
	
	public boolean childExists(String firstName,String lastName,String email) {
		Child child = childRepo.findByEmailWithFirstLastName(firstName,lastName,email);
		if(child!=null)
			return true;
		else
			return false;
		
	}


	public String findEmailByUsername(String username) {
		return childRepo.findEmailByUsername(username);
	}
	

}
