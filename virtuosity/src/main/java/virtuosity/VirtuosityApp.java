package virtuosity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages={"com.imars.core.service","virtuosity"}, exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@EnableConfigurationProperties(VirtuosityAppProperties.class)
public class VirtuosityApp {

	public static void main(String[] args) {
		// Tell server to look for virtuosity yml
		System.setProperty("spring.config.name", "virtuosity");
		SpringApplication.run(VirtuosityApp.class, args);

	}

}
