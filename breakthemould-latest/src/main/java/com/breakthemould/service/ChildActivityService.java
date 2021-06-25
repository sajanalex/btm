package com.breakthemould.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.breakthemould.domain.Child;
import com.breakthemould.domain.ChildActivity;
import com.breakthemould.domain.ScoreBoard;
import com.breakthemould.exception.UserNotFoundException;
import com.breakthemould.repositories.ChildActivityRepository;
import com.breakthemould.repositories.ChildRepository;
import com.breakthemould.repositories.ScoreBoardRepository;

@Service
public class ChildActivityService {
	

	@Autowired
	private ChildActivityRepository childActivityRepo;
	
	@Autowired
	private ChildRepository childRepo;
	
	@Autowired
	private ScoreBoardRepository scoreBoardRepo;
	

	public void saveChildActivities(Long id,List<ChildActivity> childActivities) {
		Child child = childRepo.findById(id).orElseThrow(()-> new UserNotFoundException("Invalid User"));

		for(ChildActivity ca:childActivities) {
			ChildActivity newActivity = new ChildActivity();
			newActivity.setActivity(ca.getActivity());
			newActivity.setActivityDate(ca.getActivityDate());
			newActivity.setActivityStart(ca.getActivityStart());
			newActivity.setActivityFinish(ca.getActivityFinish());
			newActivity.setChild(child);
			childActivityRepo.save(newActivity);
		}

		Long totalScore = childActivityRepo.findTotalDurationById(child.getId());
		ScoreBoard updatedBoard;
		if(scoreBoardRepo.findByChildId(id)!=null) {
			updatedBoard = scoreBoardRepo.findByChildId(id);
		}else {
			updatedBoard = new ScoreBoard();
			updatedBoard.setChild(child);
		}
		
		updatedBoard.setTotalActivity(totalScore);
		Long totalHours = totalScore/60;
		String badge = totalHours>100?"GOLD":totalHours>70?"SILVER":totalHours>30?
				"BRONZE":"BLUE";
		updatedBoard.setBadge(badge);
	
		scoreBoardRepo.saveAndFlush(updatedBoard);
	}

}
