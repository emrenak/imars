package musician;

import org.bson.types.ObjectId;

public class Musician {

	private String email;
	private String instruments;
	private String musicStyle;
	private String influences;
	private ObjectId memberId;
	private ObjectId virtuosityId;
	private ObjectId bandId;
	private ObjectId wealthId;
	private ObjectId healthId;
	private ObjectId scheduleId;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getInstruments() {
		return instruments;
	}
	public void setInstruments(String instruments) {
		this.instruments = instruments;
	}
	public String getMusicStyle() {
		return musicStyle;
	}
	public void setMusicStyle(String musicStyle) {
		this.musicStyle = musicStyle;
	}
	public String getInfluences() {
		return influences;
	}
	public void setInfluences(String influences) {
		this.influences = influences;
	}
	public ObjectId getMemberId() {
		return memberId;
	}
	public void setMemberId(ObjectId memberId) {
		this.memberId = memberId;
	}
	public ObjectId getVirtuosityId() {
		return virtuosityId;
	}
	public void setVirtuosityId(ObjectId virtuosityId) {
		this.virtuosityId = virtuosityId;
	}
	public ObjectId getBandId() {
		return bandId;
	}
	public void setBandId(ObjectId bandId) {
		this.bandId = bandId;
	}
	public ObjectId getWealthId() {
		return wealthId;
	}
	public void setWealthId(ObjectId wealthId) {
		this.wealthId = wealthId;
	}
	public ObjectId getHealthId() {
		return healthId;
	}
	public void setHealthId(ObjectId healthId) {
		this.healthId = healthId;
	}
	public ObjectId getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(ObjectId scheduleId) {
		this.scheduleId = scheduleId;
	}
	
	
}
