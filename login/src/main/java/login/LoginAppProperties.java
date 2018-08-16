package login;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("login")
public class LoginAppProperties {

	private String loginMessage;

	public String getLoginMessage() {
		return loginMessage;
	}

	public void setLoginMessage(String loginMessage) {
		this.loginMessage = loginMessage;
	}
	
	
}
