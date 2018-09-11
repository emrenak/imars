package musicianProfiler.service.impl;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import musicianProfiler.MusicianProfilerProperties;
import musicianProfiler.service.MusicianProfilerService;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.imars.core.domain.MusicianHealth;
import com.imars.core.domain.MusicianSchedule;
import com.imars.core.domain.MusicianWealth;
import com.imars.core.domain.Virtuoso;
import com.imars.core.service.CollectionFactoryService;
import com.imars.core.utils.ImarsEnums.ScheduleActivityStatus;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

@Service
@EnableAsync
public class MusicianProfilerServiceImpl implements MusicianProfilerService {

	@Autowired
	CollectionFactoryService collectionFactoryService;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	MusicianProfilerProperties musicianProfilerProperties;
	
	public MusicianProfilerServiceImpl(
			MusicianProfilerProperties musicianProfilerProperties) {
		this.musicianProfilerProperties = musicianProfilerProperties;
	}

	@Override
	@Async
	public void profile(int id, List<Document> musicianList) {
		logger.trace("Inside profile for id:" + id);
		for (Document document : musicianList) {
			String email = document.getString("email");
			logger.info("Profiling is starting for:" + email);
			int healthLevel = getHealthLevel(email);
			logger.info("Health level:" + healthLevel);
			int wealthLevel = getWealthLevel(email);
			logger.info("Wealth level:" + wealthLevel);
			int virtuosityLevel = getVirtuosityLevel(email);
			logger.info("VirtuosityLevel level:" + virtuosityLevel);
			long numberOfLogins = getNumberOfLogins(email);
			logger.info("Number of logins :" + numberOfLogins);
			int regularAttendance = getRegularAttendances(email);
			logger.info("Regular attendances:" + regularAttendance);
			//amount of time spent in application ?? re-think
		}
		/*
		 * criteria:
		 * health
		 * wealth
		 * virtuoso
		 * regular logins 
		 * regular attendance to activities
		 * amount of time spent in application
		 * */
	}
	

  private int getHealthLevel(String email){
		try {
			MongoCollection<Document> musicianHealthCollection = collectionFactoryService.getCollection("musicianhealth");
			FindIterable<Document> mdocs = musicianHealthCollection.find(eq("email",email));
			Document doc = mdocs.first();
			MusicianHealth musicianHealth = null;
			if(doc != null){
				Gson gson = new Gson();
				musicianHealth = gson.fromJson(doc.toJson(), MusicianHealth.class);
			}
			return musicianHealth.getLevel();
		} catch (Exception e) {
			logger.error("Error in getting health level for email: " + email );
			return 0;
		}
	}
	
	private int getWealthLevel(String email){
		try {
			MongoCollection<Document> musicianWealthCollection = collectionFactoryService.getCollection("musicianwealth");
			FindIterable<Document> mdocs = musicianWealthCollection.find(eq("email",email));
			Document doc = mdocs.first();
			MusicianWealth musicianWealth = null;
			if(doc != null){
				Gson gson = new Gson();
				musicianWealth = gson.fromJson(doc.toJson(), MusicianWealth.class);
			}
			return musicianWealth.getLevel();
		} catch (Exception e) {
			logger.error("Error in getting wealth level for email: " + email );
			return 0;
		}
	}
	
	private int getVirtuosityLevel(String email){
		try {
			MongoCollection<Document> virtuosityCollection = collectionFactoryService.getCollection("virtuosity");
			FindIterable<Document> mdocs = virtuosityCollection.find(eq("email",email));
			Virtuoso virtuoso = null;
			Document mdoc = mdocs.first();
			int sum = 0;
			if(mdoc!=null){
				Gson gson = new Gson();
				virtuoso = gson.fromJson(mdoc.toJson(), Virtuoso.class);
				Map<String, Integer> instMap = virtuoso.getInstrumentList();
				Collection<Integer> values = instMap.values();
				
				for (Integer level : values) {
					sum+=level;
				}
			}
			return sum;
		} catch (Exception e) {
			logger.error("Error in getting health level for email: " + email );
			return 0;
		}
	}
	
	private long getNumberOfLogins(String email){
		MongoCollection<Document> signInCollection = collectionFactoryService.getCollection("signIns");
		FindIterable<Document> mdocs = signInCollection.find(eq("email",email));
		long count = 0;
		for (Document document : mdocs) {
			count++;
		}
		return count;
	}
	
	private int getRegularAttendances(String email){
		MongoCollection<Document> musicianScheduleCollection = collectionFactoryService.getCollection("musicianschedule");
		FindIterable<Document> mdocs = musicianScheduleCollection.find(eq("email",email));
		MusicianSchedule musicianSchedule = null;
		List<MusicianSchedule> musicianSchedules = new ArrayList<>(); // for future use
		for (Document mdoc : mdocs) {
			Gson gson = new Gson();
			musicianSchedule = gson.fromJson(mdoc.toJson(), MusicianSchedule.class);
			if(ScheduleActivityStatus.COMPLETED.getValue().equals(musicianSchedule.getScheduleActivityStatus())){
				musicianSchedules.add(musicianSchedule);
			}
		}
		return musicianSchedules.size();
	}
	
	/* A totally loosely coupled implementation may degregade performance since for each member several rest calls are need to be made. 
	 * Instead, get the needed information directly from mongodb
	 * Below code is kept for a reference. 
	 *  
 * private int getHealthLevel(String email){
		try {
			RestTemplate restTemplate = new RestTemplate();
	        MusicianHealth musicianHealth = restTemplate.getForObject(musicianProfilerProperties.getHealthServiceUrl()+"/?email={email}", MusicianHealth.class, email);
	        return musicianHealth.getLevel();	
		} catch (Exception e) {
			logger.error("Error in getting health level for email: " + email );
			return 0;
		}
	}
	
	private int getWealthLevel(String email){
		try {
			RestTemplate restTemplate = new RestTemplate();
	        MusicianWealth musicianWealth = restTemplate.getForObject(musicianProfilerProperties.getWealthServiceUrl()+"/?email={email}", MusicianWealth.class, email);
	        return musicianWealth.getLevel();	
		} catch (Exception e) {
			logger.error("Error in getting health level for email: " + email );
			return 0;
		}
	}
	
	private int getVirtuosityLevel(String email){
		try {
			RestTemplate restTemplate = new RestTemplate();
			Virtuoso virtuoso = restTemplate.getForObject(musicianProfilerProperties.getVirtuosoServiceUrl()+"/?email={email}", Virtuoso.class, email);
			Map<String, Integer> instMap = virtuoso.getInstrumentList();
			Collection<Integer> values = instMap.values();
			int sum = 0;
			for (Integer level : values) {
				sum+=level;
			}
			return sum;
		} catch (Exception e) {
			logger.error("Error in getting health level for email: " + email );
			return 0;
		}
	}*/
}
