package musician;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
(scanBasePackages={"com.imars.core.service","musician"})
@EnableDiscoveryClient
@EnableConfigurationProperties(MusicianAppProperties.class)
public class MusicianApp {

	public static void main(String[] args) {
		// Tell server to look for musician yml
		System.setProperty("spring.config.name", "musician");
				
		SpringApplication.run(MusicianApp.class, args);

	}

}
