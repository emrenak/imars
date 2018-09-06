package musicianProfiler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages={"com.imars.core.service","musicianProfiler"})
@EnableConfigurationProperties(MusicianProfilerProperties.class)
@EnableScheduling
public class MusicianProfilerApp {

	public static void main(String[] args) {
		// Tell server to look for musicianScheduler yml
		System.setProperty("spring.config.name", "musicianProfiler");
		SpringApplication.run(MusicianProfilerApp.class, args);
	}

}
