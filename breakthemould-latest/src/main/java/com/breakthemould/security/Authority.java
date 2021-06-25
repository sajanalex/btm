package com.breakthemould.security;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.security.core.GrantedAuthority;

import com.breakthemould.domain.User;


@Entity
public class Authority implements GrantedAuthority {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String authority;
	@ManyToOne
	private User user;
	@Override
	public String getAuthority() {
		return this.authority;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	
	
	

}
