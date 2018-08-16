package login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
(scanBasePackages={"com.imars.core.service","login"})
@EnableDiscoveryClient
@EnableConfigurationProperties(LoginAppProperties.class)
public class App {

	public static void main(String[] args) {
		// Tell server to look for login yml
		System.setProperty("spring.config.name", "login");
		SpringApplication.run(App.class, args);
	}
}
