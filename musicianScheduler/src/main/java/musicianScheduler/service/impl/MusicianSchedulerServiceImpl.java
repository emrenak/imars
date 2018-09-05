package musicianScheduler.service.impl;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.currentDate;
import static com.mongodb.client.model.Updates.set;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import musicianScheduler.MusicianSchedule;
import musicianScheduler.exception.ActivityAlreadyRunException;
import musicianScheduler.exception.ActivityCouldNotBeExecutedException;
import musicianScheduler.exception.ActivityExpiredException;
import musicianScheduler.exception.ScheduleIsNotUpdatedException;
import musicianScheduler.service.MusicianSchedulerService;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.imars.core.service.CollectionFactoryService;
import com.imars.core.utils.ImarsEnums.ScheduleActivityStatus;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;

@Service
@EnableAsync
public class MusicianSchedulerServiceImpl implements MusicianSchedulerService {

	private static final  SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", new Locale("us"));
	
	@Autowired
	CollectionFactoryService collectionFactoryService;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public void addSchedule(String email, String scheduleType,
			String scheduleActivityType, Date scheduleStartTime, Date scheduleEndTime) {
		logger.trace("inside addSchedule :" + email);
		MongoCollection<Document> musicianScheduleCollection = collectionFactoryService.getCollection("musicianschedule");
		 Document doc = new Document("email", email)
         .append("scheduleType", scheduleType)
         .append("scheduleActivityType", scheduleActivityType)
         .append("scheduleStartTime", scheduleStartTime.toString())
         .append("scheduleEndTime", scheduleEndTime.toString())
         .append("scheduleActivityStatus", ScheduleActivityStatus.SCHEDULED.getValue());
		 musicianScheduleCollection.insertOne(doc);
		 logger.info(email + " schedule is added");
	}

	@Override
	public List<MusicianSchedule> getSchedules(String email) {
		logger.trace("inside get schedule :" + email);
		MongoCollection<Document> musicianScheduleCollection = collectionFactoryService.getCollection("musicianschedule");
		FindIterable<Document> mdocs = musicianScheduleCollection.find(eq("email",email));
		MusicianSchedule musicianSchedule = null;
		List<MusicianSchedule> musicianSchedules = new ArrayList<>();
		for (Document mdoc : mdocs) {
			Gson gson = new Gson();
			musicianSchedule = gson.fromJson(mdoc.toJson(), MusicianSchedule.class);
			musicianSchedules.add(musicianSchedule);
		}
		return musicianSchedules;
	}

	@Override
	public void updateSchedule(String email, String scheduleType,
			String scheduleActivityType, Date scheduleStartTime,
			Date scheduleEndTime, String scheduleActivityStatus,
			String scheduleId) throws ScheduleIsNotUpdatedException {
		logger.trace("inside update schedule :" + email);
		MongoCollection<Document> musicianScheduleCollection = collectionFactoryService.getCollection("musicianschedule");
		UpdateResult result =  musicianScheduleCollection.updateOne(eq("_id", new ObjectId(scheduleId)), 
					 combine(set("scheduleType", scheduleType), 
							 set("scheduleActivityType", scheduleActivityType),
							 set("scheduleStartTime", scheduleStartTime.toString()),
							 set("scheduleEndTime", scheduleEndTime.toString()),
							 set("scheduleActivityStatus", scheduleActivityStatus),
							 currentDate("lastModified")));
		if(result.wasAcknowledged()){
			logger.info(email + " schedule is updated");
		}else{
			throw new ScheduleIsNotUpdatedException(email + " schedule is not updated");
		}
	}

	@Override
	public void removeSchedule(String email, String scheduleId) throws ScheduleIsNotUpdatedException {
		logger.trace("inside remove schedule :" + email);
		MongoCollection<Document> musicianScheduleCollection = collectionFactoryService.getCollection("musicianschedule");
		UpdateResult result =  musicianScheduleCollection.updateOne(eq("_id", new ObjectId(scheduleId)), 
					 combine(set("scheduleActivityStatus", ScheduleActivityStatus.CANCELLED.getValue()),
							 currentDate("lastModified")));
		if(result.wasAcknowledged()){
			logger.info(email + " schedule is cancelled");
		}else{
			throw new ScheduleIsNotUpdatedException(email + " schedule is not updated");
		}
		
	}

	@Override
	@Async
	public void runActivity(String email, String scheduleId) throws ActivityExpiredException, ActivityCouldNotBeExecutedException, ActivityAlreadyRunException {
		MongoCollection<Document> musicianScheduleCollection = collectionFactoryService.getCollection("musicianschedule");
		FindIterable<Document> mdocs = musicianScheduleCollection.find(eq("_id", new ObjectId(scheduleId)));
		Document mdoc = mdocs.first();
		if(mdoc != null){
			isActivityEligible(email, scheduleId, musicianScheduleCollection, mdoc);
			// update schedule as inprogress
			musicianScheduleCollection.updateOne(eq("_id", new ObjectId(scheduleId)), 
					 combine(set("scheduleActivityStatus", ScheduleActivityStatus.INPROGRESS.getValue()),
					 currentDate("lastModified")));
			//5 minutes of activity is 1 h. For each hour, count down from 5 minutes. Calculate number of activity periods, and count down (ap * 5min)
			Timer timer = new Timer();
			double calculatedActivityDuration = new Double (getActivityPeriod(mdoc.getString("scheduleStartTime"), mdoc.getString("scheduleEndTime"))); 
			timer.scheduleAtFixedRate(new TimerTask() {
            	double countDown = calculatedActivityDuration;
				public void run() {
	                countDown--;
	            	if (countDown < 0){
	                    timer.cancel();
	                    // update activity as completed.
	        	        musicianScheduleCollection.updateOne(eq("_id", new ObjectId(scheduleId)), 
	          					combine(set("activityCompletionPercentage", 1), 
	          							set("scheduleActivityStatus", ScheduleActivityStatus.COMPLETED.getValue()),
	          							currentDate("lastModified")));
	            	}else if (countDown % 20 == 0){
	            		Double completedPerc =  (calculatedActivityDuration - countDown) / calculatedActivityDuration;
	        			musicianScheduleCollection.updateOne(eq("_id", new ObjectId(scheduleId)), 
	       					 combine(set("activityCompletionPercentage", completedPerc.toString()),
	       					 currentDate("lastModified")));
	            	}
	            }
	        }, 0, 1000);
		}	
		 
	}
	
	private long getActivityPeriod(String start, String end){
		try {
			long startDate = formatter.parse(start).getTime();
			long endDate = formatter.parse(end).getTime();
			long diff = endDate - startDate;
			return (diff / (12))/1000;
		} catch (ParseException e) {
			return -1;
		}	
	}
	
	private void isActivityEligible(String email,  String scheduleId, MongoCollection<Document> musicianScheduleCollection,Document mdoc) throws ActivityExpiredException, ActivityCouldNotBeExecutedException, ActivityAlreadyRunException{
		Date endDate;
		try {
			endDate = formatter.parse( mdoc.getString("scheduleEndTime"));
			if(Calendar.getInstance().getTime().after(endDate)){
				musicianScheduleCollection.updateOne(eq("_id", new ObjectId(scheduleId)), 
						 combine(set("scheduleActivityStatus", ScheduleActivityStatus.EXPIRED.getValue()),
								 currentDate("lastModified")));
				throw new ActivityExpiredException(email + " activity is expired");
			}
			String activityStatus = mdoc.getString("scheduleActivityStatus");
			ScheduleActivityStatus status = ScheduleActivityStatus.fromValue(activityStatus);
			if(!ScheduleActivityStatus.SCHEDULED.equals(status)){
				throw new ActivityAlreadyRunException(email + " activity already run");
			}
		} catch (ParseException e) {
			throw new ActivityCouldNotBeExecutedException(email + " activity could not be executed");
		}
	}
}
