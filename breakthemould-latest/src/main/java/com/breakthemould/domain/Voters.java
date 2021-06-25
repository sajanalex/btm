package com.breakthemould.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "voters" , uniqueConstraints = {@UniqueConstraint(name="voter_email_unique",columnNames = "email")})
public class Voters {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name= "email",nullable = false)
	private String email;
	private boolean hasVoted;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isHasVoted() {
		return hasVoted;
	}
	public void setHasVoted(boolean hasVoted) {
		this.hasVoted = hasVoted;
	}
	
	

}
