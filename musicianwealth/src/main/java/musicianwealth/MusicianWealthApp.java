package musicianwealth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages={"com.imars.core.service","musicianwealth"}, exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@EnableConfigurationProperties(MusicianWealthProperties.class)
public class MusicianWealthApp {

	public static void main(String[] args) {
		// Tell server to look for musicianwealth yml
		System.setProperty("spring.config.name", "musicianwealth");
		SpringApplication.run(MusicianWealthApp.class, args);

	}

}
