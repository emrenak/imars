package musicianScheduler.service.impl;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.currentDate;
import static com.mongodb.client.model.Updates.set;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import musicianScheduler.MusicianSchedule;
import musicianScheduler.exception.ScheduleIsNotUpdatedException;
import musicianScheduler.service.MusicianSchedulerService;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.imars.core.service.CollectionFactoryService;
import com.imars.core.utils.ImarsEnums.ScheduleActivityStatus;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;

@Service
public class MusicianSchedulerServiceImpl implements MusicianSchedulerService {

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

}
