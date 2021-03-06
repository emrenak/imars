package musicianhealth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages={"com.imars.core.service","musicianhealth"}, exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@EnableConfigurationProperties(MusicianHealthProperties.class)
public class MusicianHealthApp {

	public static void main(String[] args) {
		// Tell server to look for musicianhealth yml
		System.setProperty("spring.config.name", "musicianhealth");
		SpringApplication.run(MusicianHealthApp.class, args);

	}

}
