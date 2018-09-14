package shopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages={"com.imars.core.service","shopping"})
@EnableDiscoveryClient
@EnableConfigurationProperties(AssetProperties.class)
public class ShoppingApp {

	public static void main(String[] args) {
		// Tell server to look for shopping yml
		System.setProperty("spring.config.name", "shopping");
		SpringApplication.run(ShoppingApp.class, args);
	}
}
