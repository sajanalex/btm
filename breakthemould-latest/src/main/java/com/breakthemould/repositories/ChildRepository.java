package com.breakthemould.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.breakthemould.domain.Child;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {
	Optional<Child> findById(Long Id);

	@Query("select  c from Child c where username = :username")
	Child findByIdWithUser(String username);
	
	@Query("select c from Child c JOIN c.opponents t where t.id=:id")
	List<Child> findByChallengers(Long id);

	@Query("select c from Child c where id NOT IN(select d.id from Child d JOIN d.opponents o where o.id=:id) AND canChallenge=true AND id!=:id")
	List<Child> findByCanChallenge(Long id);

	@Query("select  c.email from Child c where username = :username")
	String findEmailByUsername(String username);

	@Query("SELECT c FROM Child c WHERE firstName=:firstName AND lastName=:lastName AND email=:email")
	Child findByEmailWithFirstLastName(String firstName, String lastName, String email);

	Child findTopByOrderByIdDesc();


	
	

	



}
