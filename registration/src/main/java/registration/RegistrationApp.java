package registration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
(scanBasePackages={"com.imars.core.service","registration"}, exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@EnableDiscoveryClient
@EnableConfigurationProperties(RegistrationAppProperties.class)
public class RegistrationApp {

	public static void main(String[] args) {
		// Tell server to look for registration yml
		System.setProperty("spring.config.name", "registration");
		
		SpringApplication.run(RegistrationApp.class, args);
	}

}
