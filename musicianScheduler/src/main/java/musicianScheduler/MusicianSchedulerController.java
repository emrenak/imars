package musicianScheduler;

import java.util.Date;
import java.util.List;

import musicianScheduler.service.MusicianSchedulerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
			@RequestParam(value="scheduleTime") @DateTimeFormat(iso = ISO.DATE_TIME) Date scheduleTime){ //yyyy-MM-dd'T'HH:mm:ss.SSSZ 2000-10-31T01:30:00.000-05:00
		musicianSchedulerService.addSchedule(email, scheduleType, scheduleActivityType, scheduleTime);
	}
	
	@RequestMapping("/musicianScheduler/remove")
	public void removeSchedule(@RequestParam(value="email", defaultValue="") String email,
			@RequestParam(value="scheduleId", defaultValue="") String scheduleId){
		
	}
	
	@RequestMapping("/musicianScheduler/update")
	public void updateSchedule(@RequestParam(value="email", defaultValue="") String email,
			@RequestParam(value="scheduleType", defaultValue="") String scheduleType,
			@RequestParam(value="scheduleActivityType", defaultValue="") String scheduleActivityType,
			@RequestParam(value="scheduleTime", defaultValue="")  Date scheduleTime,
			@RequestParam(value="scheduleActivityStatus", defaultValue="") String scheduleActivityStatus,
			@RequestParam(value="scheduleId", defaultValue="") String scheduleId){
		
	}
	
	

}
