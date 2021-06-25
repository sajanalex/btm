package com.breakthemould.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import io.swagger.v3.oas.annotations.tags.Tag;


@Entity
public class School {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String schoolName;
	@Embedded
	private Address address;
	@OneToMany(mappedBy = "school",cascade = CascadeType.ALL)
	private Set<Child> children;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	@JsonIgnore
	public Set<Child> getChildren() {
		return children;
	}
	public void setChildren(Set<Child> children) {
		this.children = children;
	}
	
	
	

}
