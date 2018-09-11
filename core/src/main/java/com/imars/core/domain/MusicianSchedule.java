package com.imars.core.domain;

public class MusicianSchedule {

	private String email;
	private String scheduleType;
	private String scheduleActivityType;
	private String scheduleStartTime;
	private String scheduleEndTime;
	private String scheduleActivityStatus;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getScheduleType() {
		return scheduleType;
	}
	public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
	}
	public String getScheduleActivityType() {
		return scheduleActivityType;
	}
	public void setScheduleActivityType(String scheduleActivityType) {
		this.scheduleActivityType = scheduleActivityType;
	}
	public String getScheduleActivityStatus() {
		return scheduleActivityStatus;
	}
	public void setScheduleActivityStatus(String scheduleActivityStatus) {
		this.scheduleActivityStatus = scheduleActivityStatus;
	}
	public String getScheduleStartTime() {
		return scheduleStartTime;
	}
	public void setScheduleStartTime(String scheduleStartTime) {
		this.scheduleStartTime = scheduleStartTime;
	}
	public String getScheduleEndTime() {
		return scheduleEndTime;
	}
	public void setScheduleEndTime(String scheduleEndTime) {
		this.scheduleEndTime = scheduleEndTime;
	}
	
}
