package com.breakthemould.domain;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.Formula;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
public class ChildActivity {
	public  ChildActivity() {}
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate activityDate;
	private LocalTime activityStart;
	private LocalTime activityFinish;
	private String notes;

	@ManyToOne @JoinColumn(name="CHILD_ID",referencedColumnName = "id")
	private Child child;
	@OneToOne(cascade = CascadeType.DETACH,fetch = FetchType.EAGER) @JoinColumn(name="Activity_id",referencedColumnName = "id")
	private Activity activity;
	
	//@Formula("Duration.between(activityStart, activityFinish).toMinutes()")
	private Long duration;
	
	@PreUpdate
	@PrePersist
	public void calc() {
		duration=Duration.between(activityStart, activityFinish).toMinutes();
	}
	
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getActivityDate() {
		return activityDate;
	}
	public void setActivityDate(LocalDate activityDate) {
		this.activityDate = activityDate;
	}
	public LocalTime getActivityStart() {
		return activityStart;
	}
	public void setActivityStart(LocalTime activityStart) {
		this.activityStart = activityStart;
	}
	public LocalTime getActivityFinish() {
		return activityFinish;
	}
	public void setActivityFinish(LocalTime activityFinish) {
		this.activityFinish = activityFinish;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	@JsonIgnore
	public Child getChild() {
		return child;
	}
	public void setChild(Child child) {
		this.child = child;
	}
	
	public Activity getActivity() {
		return activity;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	
	
	
		

}