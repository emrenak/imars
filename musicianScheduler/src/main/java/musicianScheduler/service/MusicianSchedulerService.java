package musicianScheduler.service;

import java.util.Date;
import java.util.List;

import musicianScheduler.MusicianSchedule;

public interface MusicianSchedulerService {
	
	public void addSchedule(String email,
			String scheduleType,
			String scheduleActivityType,
			Date scheduleTime);
	
	public List<MusicianSchedule> getSchedules(String email);

}
