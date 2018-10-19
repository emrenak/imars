package login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
(scanBasePackages={"com.imars.core.service","login"}, exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@EnableDiscoveryClient
@EnableConfigurationProperties(LoginAppProperties.class)
public class LoginApp {

	public static void main(String[] args) {
		// Tell server to look for login yml
		System.setProperty("spring.config.name", "login");
		SpringApplication.run(LoginApp.class, args);
	}
}
