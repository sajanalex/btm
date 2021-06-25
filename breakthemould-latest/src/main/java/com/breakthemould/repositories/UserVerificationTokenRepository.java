package com.breakthemould.repositories;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.breakthemould.domain.User;
import com.breakthemould.domain.UserVerificationToken;

public interface UserVerificationTokenRepository extends JpaRepository<UserVerificationToken, Long> {

	@Query("select t.user from UserVerificationToken t where t.token=:token")
	User findUserByToken(String token);

	@Query("select t.expiryDate from UserVerificationToken t where t.token=:token")
	LocalDateTime findExpiryByToken(String token);

	UserVerificationToken findByToken(String token);

	@Query("select t from UserVerificationToken t where t.user=:user")
	UserVerificationToken findByUser(User user);

}
