package com.breakthemould.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.breakthemould.domain.ScoreBoard;

public interface ScoreBoardRepository extends JpaRepository<ScoreBoard, Long> {

	ScoreBoard findByChildId(Long id);

	@Query("SELECT avg(totalActivity) FROM ScoreBoard s where child.id in(select c.id from Child c where school.id=:id)")
	Integer findAverageTotalActivityBySchool(Integer id);

	@Query("SELECT avg(totalActivity) FROM ScoreBoard s where child.id in(select c.id from Child c where school.id=:id AND yearGroup=:yearGroup)")
	Integer findAverageTotalActivityByYearGroup(String yearGroup, Integer id);

}
