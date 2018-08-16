package musician;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("musician")
public class MusicianAppProperties {
	
	private String validationMessage;

	public String getValidationMessage() {
		return validationMessage;
	}

	public void setValidationMessage(String validationMessage) {
		this.validationMessage = validationMessage;
	}

}
