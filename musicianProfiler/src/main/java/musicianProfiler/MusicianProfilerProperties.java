package musicianProfiler;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("musicianprofiler")
public class MusicianProfilerProperties {
	private String validationMessage;
	
	public String getValidationMessage() {
		return validationMessage;
	}

	public void setValidationMessage(String validationMessage) {
		this.validationMessage = validationMessage;
	}
	


}
