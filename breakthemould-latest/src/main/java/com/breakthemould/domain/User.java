package com.breakthemould.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import com.breakthemould.security.Authority;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User implements Serializable {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String username;
	private String password;
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private Set<Authority> authorities = new HashSet<>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String userName) {
		this.username = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@JsonIgnore
	public Set<Authority> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}
	
	

}
