package musicianhealth;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("musicianhealth")
public class MusicianHealthProperties {
	private String validationMessage;

	public String getValidationMessage() {
		return validationMessage;
	}

	public void setValidationMessage(String validationMessage) {
		this.validationMessage = validationMessage;
	}

}
