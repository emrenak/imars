package musicianScheduler;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages={"com.imars.core.service","musicianScheduler"})
@EnableConfigurationProperties(MusicianSchedulerProperties.class)
public class MusicianSchedulerApp {

	public static void main(String[] args) {
		// Tell server to look for musicianScheduler yml
		System.setProperty("spring.config.name", "musicianscheduler");
		SpringApplication.run(MusicianSchedulerApp.class, args);
	}

    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("Scheduler-");
        executor.initialize();
        return executor;
    }
}
