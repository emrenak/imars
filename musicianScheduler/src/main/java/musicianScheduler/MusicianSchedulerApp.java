package musicianScheduler;

import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages={"com.imars.core.service","musicianScheduler"}, exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@EnableConfigurationProperties(MusicianSchedulerProperties.class)
public class MusicianSchedulerApp {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	MusicianSchedulerProperties musicianSchedulerProperties;
	
	public MusicianSchedulerApp(MusicianSchedulerProperties musicianSchedulerProperties) {
		this.musicianSchedulerProperties = musicianSchedulerProperties;
	}
	
	public static void main(String[] args) {
		// Tell server to look for musicianScheduler yml
		System.setProperty("spring.config.name", "musicianscheduler");
		SpringApplication.run(MusicianSchedulerApp.class, args);
	}
	
    @Bean
    public Executor asyncExecutor() {
    	logger.info(musicianSchedulerProperties.toString());
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(musicianSchedulerProperties.getSchedulerCoreThreadPoolSize());
        executor.setMaxPoolSize(musicianSchedulerProperties.getSchedulerMaxThreadPoolSize());
        executor.setQueueCapacity(musicianSchedulerProperties.getSchedulerQueueCapacity());
        executor.setThreadNamePrefix("Scheduler-");
        executor.initialize();
        return executor;
    }
}
