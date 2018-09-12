package collaborator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages={"com.imars.core.service","collaborator"})
@EnableConfigurationProperties(CollaboratorProperties.class)
public class CollaboratorApp {

	public static void main(String[] args) {
		// Tell server to look for collaborator yml
		System.setProperty("spring.config.name", "collaborator");
		SpringApplication.run(CollaboratorApp.class, args);

	}

}
