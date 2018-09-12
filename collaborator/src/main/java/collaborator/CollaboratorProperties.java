package collaborator;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("collaborator")
public class CollaboratorProperties {
	private String validationMessage;

	public String getValidationMessage() {
		return validationMessage;
	}

	public void setValidationMessage(String validationMessage) {
		this.validationMessage = validationMessage;
	}

}
