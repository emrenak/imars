package musicianScheduler.service;

import java.util.Date;
import java.util.List;

import musicianScheduler.MusicianSchedule;
import musicianScheduler.exception.ActivityAlreadyRunException;
import musicianScheduler.exception.ActivityCouldNotBeExecutedException;
import musicianScheduler.exception.ActivityExpiredException;
import musicianScheduler.exception.ScheduleIsNotUpdatedException;

public interface MusicianSchedulerService {
	
	public void addSchedule(String email,
			String scheduleType,
			String scheduleActivityType,
			Date scheduleStartTime,
			Date scheduleEndTime);
	
	public List<MusicianSchedule> getSchedules(String email);
	
	public void updateSchedule(String email,
			String scheduleType,
			String scheduleActivityType,
			Date scheduleStartTime,
			Date scheduleEndTime,
			String scheduleActivityStatus,
			String scheduleId) throws ScheduleIsNotUpdatedException;

	public void removeSchedule(String email, String scheduleId) throws ScheduleIsNotUpdatedException;
	
	public void runActivity(String email, String scheduleId) throws ActivityExpiredException, ActivityCouldNotBeExecutedException, ActivityAlreadyRunException;
}
