package registration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("registration")
public class RegistrationAppProperties {

	private String validationMessage;

	public String getValidationMessage() {
		return validationMessage;
	}

	public void setValidationMessage(String validationMessage) {
		this.validationMessage = validationMessage;
	}
	
	

}
