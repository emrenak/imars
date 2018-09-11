package musicianProfiler;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("musicianprofiler")
public class MusicianProfilerProperties {
	private String validationMessage;
	private int schedulerCoreThreadPoolSize;
	private int schedulerMaxThreadPoolSize;
	private int schedulerQueueCapacity;
	private String healthServiceUrl;
	private String wealthServiceUrl;
	private String virtuosoServiceUrl;
	public String getValidationMessage() {
		return validationMessage;
	}

	public void setValidationMessage(String validationMessage) {
		this.validationMessage = validationMessage;
	}

	public int getSchedulerCoreThreadPoolSize() {
		return schedulerCoreThreadPoolSize;
	}

	public void setSchedulerCoreThreadPoolSize(int schedulerCoreThreadPoolSize) {
		this.schedulerCoreThreadPoolSize = schedulerCoreThreadPoolSize;
	}

	public int getSchedulerMaxThreadPoolSize() {
		return schedulerMaxThreadPoolSize;
	}

	public void setSchedulerMaxThreadPoolSize(int schedulerMaxThreadPoolSize) {
		this.schedulerMaxThreadPoolSize = schedulerMaxThreadPoolSize;
	}

	public int getSchedulerQueueCapacity() {
		return schedulerQueueCapacity;
	}

	public void setSchedulerQueueCapacity(int schedulerQueueCapacity) {
		this.schedulerQueueCapacity = schedulerQueueCapacity;
	}

	public String getHealthServiceUrl() {
		return healthServiceUrl;
	}

	public void setHealthServiceUrl(String healthServiceUrl) {
		this.healthServiceUrl = healthServiceUrl;
	}

	public String getWealthServiceUrl() {
		return wealthServiceUrl;
	}

	public void setWealthServiceUrl(String wealthServiceUrl) {
		this.wealthServiceUrl = wealthServiceUrl;
	}

	public String getVirtuosoServiceUrl() {
		return virtuosoServiceUrl;
	}

	public void setVirtuosoServiceUrl(String virtuosoServiceUrl) {
		this.virtuosoServiceUrl = virtuosoServiceUrl;
	}
}
