package virtuosity;

import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;

public class Virtuoso {

	private String email;
	private ObjectId musicianId;
	private Map<String, Object> instrumentList = new HashMap<String, Object>();
	public ObjectId getMusicianId() {
		return musicianId;
	}
	public void setMusicianId(ObjectId musicianId) {
		this.musicianId = musicianId;
	}
	public Map<String, Object> getInstrumentList() {
		return instrumentList;
	}
	public void setInstrumentList(Map<String, Object> virtuosityLevel) {
		this.instrumentList = virtuosityLevel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
