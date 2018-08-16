package registration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
(scanBasePackages={"com.imars.core.service","registration"})
@EnableDiscoveryClient
@EnableConfigurationProperties(RegistrationAppProperties.class)
public class App {

	public static void main(String[] args) {
		// Tell server to look for registration yml
		System.setProperty("spring.config.name", "registration");
		
		SpringApplication.run(App.class, args);
	}

}
