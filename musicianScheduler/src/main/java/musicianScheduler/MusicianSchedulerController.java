package musicianScheduler;

import java.util.Date;
import java.util.List;

import musicianScheduler.exception.ActivityAlreadyRunException;
import musicianScheduler.exception.ActivityCouldNotBeExecutedException;
import musicianScheduler.exception.ActivityExpiredException;
import musicianScheduler.exception.ScheduleIsNotUpdatedException;
import musicianScheduler.service.MusicianSchedulerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imars.core.domain.MusicianSchedule;

@RestController
public class MusicianSchedulerController {

	@Autowired
	MusicianSchedulerService musicianSchedulerService;
	
	@RequestMapping("/musicianScheduler/get")
	public List<MusicianSchedule> get(@RequestParam(value="email", defaultValue="") String email) {
		return musicianSchedulerService.getSchedules(email);
	}
	
	@RequestMapping("/musicianScheduler/add")
	public void addSchedule(@RequestParam(value="email", defaultValue="") String email,
			@RequestParam(value="scheduleType" , defaultValue="") String scheduleType,
			@RequestParam(value="scheduleActivityType", defaultValue="")  String scheduleActivityType,
			@RequestParam(value="scheduleStartTime") @DateTimeFormat(iso = ISO.DATE_TIME) Date scheduleStartTime,
			@RequestParam(value="scheduleEndTime") @DateTimeFormat(iso = ISO.DATE_TIME) Date scheduleEndTime){ //yyyy-MM-dd'T'HH:mm:ss.SSSZ 2000-10-31T01:30:00.000-05:00
		musicianSchedulerService.addSchedule(email, scheduleType, scheduleActivityType, scheduleStartTime, scheduleEndTime);
	}
	
	@RequestMapping("/musicianScheduler/remove")
	public void removeSchedule(@RequestParam(value="email", defaultValue="") String email,
			@RequestParam(value="scheduleId", defaultValue="") String scheduleId) throws ScheduleIsNotUpdatedException{
		musicianSchedulerService.removeSchedule(email, scheduleId);
		
	}
	
	@RequestMapping("/musicianScheduler/update")
	public void updateSchedule(@RequestParam(value="email", defaultValue="") String email,
			@RequestParam(value="scheduleType", defaultValue="") String scheduleType,
			@RequestParam(value="scheduleActivityType", defaultValue="") String scheduleActivityType,
			@RequestParam(value="scheduleStartTime", defaultValue="") @DateTimeFormat(iso = ISO.DATE_TIME)  Date scheduleStartTime,
			@RequestParam(value="scheduleEndTime", defaultValue="")  @DateTimeFormat(iso = ISO.DATE_TIME) Date scheduleEndTime,
			@RequestParam(value="scheduleActivityStatus", defaultValue="") String scheduleActivityStatus,
			@RequestParam(value="scheduleId", defaultValue="") String scheduleId) throws ScheduleIsNotUpdatedException{
		musicianSchedulerService.updateSchedule(email, scheduleType, scheduleActivityType, scheduleStartTime, scheduleEndTime, scheduleActivityStatus, scheduleId);
		
	}
	
	@RequestMapping("/musicianScheduler/kickOffActivity")
	public void kickOffActivity(@RequestParam(value="email", defaultValue="") String email,
			@RequestParam(value="scheduleId", defaultValue="") String scheduleId) throws ActivityExpiredException, ActivityCouldNotBeExecutedException, ActivityAlreadyRunException{
		musicianSchedulerService.runActivity(email, scheduleId);
		//TODO
		//Since runActivity is async, exceptions are not thrown to client, think about it. For now, updating the status in mongo. 
	}
	
	

}
