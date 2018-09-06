package musicianProfiler.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProfilerTasks {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/*
	 * A task is executed with a fixed delay between the completion of the last invocation and the start of the next, using fixedDelay parameter.
	 * */
	@Scheduled(fixedDelay = 2000)
	public void scheduleTaskWithFixedDelay() { 
		logger.info("Profiling is starting");
		try {
			Thread.sleep(10000);
			/*
			 * criteria:
			 * health
			 * wealth
			 * virtuoso
			 * regular logins 
			 * regular attendance to activities
			 * amount of time spent in application
			 * */
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Profiling is ended");
		
	}
}
