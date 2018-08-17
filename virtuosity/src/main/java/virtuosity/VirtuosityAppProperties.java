package virtuosity;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("virtuosity")
public class VirtuosityAppProperties {
	
	private String validationMessage;

	public String getValidationMessage() {
		return validationMessage;
	}

	public void setValidationMessage(String validationMessage) {
		this.validationMessage = validationMessage;
	}

}
