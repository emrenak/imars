package musicianScheduler;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("musicianscheduler")
public class MusicianSchedulerProperties {
	private String validationMessage;

	public String getValidationMessage() {
		return validationMessage;
	}

	public void setValidationMessage(String validationMessage) {
		this.validationMessage = validationMessage;
	}

}
