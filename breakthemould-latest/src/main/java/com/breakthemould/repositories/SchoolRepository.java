package com.breakthemould.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breakthemould.domain.School;

public interface SchoolRepository extends JpaRepository<School, Integer> {

}
