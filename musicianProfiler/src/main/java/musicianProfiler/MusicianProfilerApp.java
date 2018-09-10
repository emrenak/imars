package musicianProfiler;

import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages={"com.imars.core.service","musicianProfiler"})
@EnableConfigurationProperties(MusicianProfilerProperties.class)
@EnableScheduling
public class MusicianProfilerApp {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	MusicianProfilerProperties musicianProfilerProperties;
	
	public MusicianProfilerApp(
			MusicianProfilerProperties musicianProfilerProperties) {
		this.musicianProfilerProperties = musicianProfilerProperties;
	}

	public static void main(String[] args) {
		// Tell server to look for musicianScheduler yml
		System.setProperty("spring.config.name", "musicianProfiler");
		SpringApplication.run(MusicianProfilerApp.class, args);
	}

	
    @Bean
    public Executor asyncExecutor() {
    	logger.info(musicianProfilerProperties.toString());
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(musicianProfilerProperties.getSchedulerCoreThreadPoolSize());
        executor.setMaxPoolSize(musicianProfilerProperties.getSchedulerMaxThreadPoolSize());
        executor.setQueueCapacity(musicianProfilerProperties.getSchedulerQueueCapacity());
        executor.setThreadNamePrefix("Profiler-");
        executor.initialize();
        return executor;
    }
}
