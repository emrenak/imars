package musicianScheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages={"com.imars.core.service","musicianScheduler"})
@EnableConfigurationProperties(MusicianSchedulerProperties.class)
public class MusicianSchedulerApp {

	public static void main(String[] args) {
		// Tell server to look for musicianScheduler yml
		System.setProperty("spring.config.name", "musicianscheduler");
		SpringApplication.run(MusicianSchedulerApp.class, args);

	}

}
