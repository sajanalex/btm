package com.breakthemould.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.breakthemould.domain.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
	
	
}
