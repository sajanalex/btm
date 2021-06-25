package com.breakthemould.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.breakthemould.domain.School;
import com.breakthemould.repositories.SchoolRepository;
import com.breakthemould.repositories.ScoreBoardRepository;

@Service
public class SchoolService {
	
	@Autowired
	private SchoolRepository schoolRepo;
	
	@Autowired
	private ScoreBoardRepository scoreBoardRepo;

	public List<School> listAllSchools() {
		return schoolRepo.findAll();
	}
	public Integer schoolAverageScore(Integer id) {
		return scoreBoardRepo.findAverageTotalActivityBySchool(id);
		
	}
	public Integer classAverageScore(String yearGroup, Integer id) {
		return scoreBoardRepo.findAverageTotalActivityByYearGroup(yearGroup,id);
	}

}
