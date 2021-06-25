package com.breakthemould.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.breakthemould.domain.ChildActivity;

public interface ChildActivityRepository extends JpaRepository<ChildActivity, Long> {

	List<ChildActivity> findByChildId(Long id);

	@Query("SELECT SUM(duration) from ChildActivity a where a.child.id=:id")
	Long findTotalDurationById(Long id);

}
