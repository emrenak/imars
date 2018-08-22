package musicianwealth;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("musicianwealth")
public class MusicianWealthProperties {
	private String validationMessage;

	public String getValidationMessage() {
		return validationMessage;
	}

	public void setValidationMessage(String validationMessage) {
		this.validationMessage = validationMessage;
	}

}
