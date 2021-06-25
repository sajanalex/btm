package com.breakthemould.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ScoreBoard {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate startDate;
	private Long totalActivity;
	private String badge;
	@OneToOne @JoinColumn(name = "CHILD_ID")
	private Child child;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public Long getTotalActivity() {
		return totalActivity;
	}
	public void setTotalActivity(Long totalActivity) {
		this.totalActivity = totalActivity;
	}
	public String getBadge() {
		return badge;
	}
	public void setBadge(String badge) {
		this.badge = badge;
	}
	@JsonIgnore
	public Child getChild() {
		return child;
	}
	public void setChild(Child child) {
		this.child = child;
	}
	
	

}
