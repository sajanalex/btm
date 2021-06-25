package com.breakthemould.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Child {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty(message = "First name should not be empty")
	@Size(min = 1,max = 32)
	private String firstName;
	@NotEmpty(message = "Last name should not be empty")
	@Size(min = 1,max = 45)
	private String lastName;
	private LocalDate dob;
	private String gender;
	private String ethnicity;
	private String phone;
	@NotEmpty(message = "Email should not be empty")
	@Email(message = "Enter valid email")
	private String email;
	private String yearGroup;
	private String bio;
	private String avatar;
	private Boolean canChallenge;
	private Address address;
	@OneToOne(mappedBy = "child",cascade = CascadeType.ALL)
	private ScoreBoard scoreBoard;
	
	
	
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public ScoreBoard getScoreBoard() {
		return scoreBoard;
	}
	public void setScoreBoard(ScoreBoard scoreBoard) {
		this.scoreBoard = scoreBoard;
	}
	@ManyToMany
	private Set<Child> challengers;
	@ManyToMany(mappedBy = "challengers",cascade = CascadeType.ALL)
	private Set<Child> opponents = new HashSet<>();
	@OneToMany(mappedBy = "child", cascade = CascadeType.ALL)
	private Set<ChildActivity> activities = new HashSet<ChildActivity>();
	@OneToOne @JoinColumn(name="Parent_id")
	private Parent parent;
	@ManyToOne @JoinColumn(name = "SCHOOL_ID",referencedColumnName = "id")
	private School school;
	@OneToOne(cascade = CascadeType.ALL) @JoinColumn(name = "username",referencedColumnName = "username")
	private User user;
	
	public Boolean getCanChallenge() {
		return canChallenge;
	}
	public void setCanChallenge(Boolean canChallenge) {
		this.canChallenge = canChallenge;
	}

	
	@JsonIgnore
	public Set<Child> getChallengers() {
		return challengers;
	}
	public void setChallengers(Set<Child> challengers) {
		this.challengers = challengers;
	}
	
	@JsonIgnore
	public Set<Child> getOpponents() {
		return opponents;
	}
	public void setOpponents(Set<Child> opponents) {
		this.opponents = opponents;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public LocalDate getDob() {
		return dob;
	}
	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEthnicity() {
		return ethnicity;
	}
	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getYearGroup() {
		return yearGroup;
	}
	public void setYearGroup(String yearGroup) {
		this.yearGroup = yearGroup;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	@JsonIgnore
	public Set<ChildActivity> getActivities() {
		return activities;
	}
	public void setActivities(Set<ChildActivity> activities) {
		this.activities = activities;
	}
	@JsonIgnore
	public Parent getParent() {
		return parent;
	}
	public void setParent(Parent parent) {
		this.parent = parent;
	}
	
	@JsonIgnore
	public School getSchool() {
		return school;
	}
	public void setSchool(School school) {
		this.school = school;
	}
	
	@JsonIgnore
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	

}
