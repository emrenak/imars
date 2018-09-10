package musicianProfiler.service.impl;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import musicianProfiler.MusicianProfilerProperties;
import musicianProfiler.service.MusicianProfilerService;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.imars.core.service.CollectionFactoryService;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

@Component
public class ProfilerTasks {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	CollectionFactoryService collectionFactoryService;
	
	@Autowired
	MusicianProfilerService musicianProfilerService;
	
	MusicianProfilerProperties musicianProfilerProperties;
	
	Map<Integer, List<Document>> profilingMap;
	
	public ProfilerTasks(
			MusicianProfilerProperties musicianProfilerProperties) {
		this.musicianProfilerProperties = musicianProfilerProperties;
	}
	
	/*
	 * A task is executed with a fixed delay between the completion of the last invocation
	 *  and the start of the next, using fixedDelay parameter.
	 * */
	@Scheduled(fixedDelay = 10000)
	public void scheduleTaskWithFixedDelay() { 
		logger.info("Profiling is starting");
		profilingMap = new HashMap<Integer, List<Document>>();
		MongoCollection<Document> musicianCollection = collectionFactoryService.getCollection("musicians");
		FindIterable<Document> mdocs = musicianCollection.find();
		for (Document document : mdocs) {
			String id = document.getObjectId("_id").toString();
			int key = id.hashCode() % musicianProfilerProperties.getSchedulerMaxThreadPoolSize();
			List<Document> musicianList = profilingMap.get(key);
			if(musicianList == null){
				musicianList = new ArrayList<Document>();
				profilingMap.put(key, musicianList);
			}
			musicianList.add(document);
		}
		Set<Integer> keySet = profilingMap.keySet();
		for (Integer id : keySet) {
			musicianProfilerService.profile(id, profilingMap.get(id));
		}
		profilingMap = null;
		logger.info("Profiling is forked to threads");
		
	}
}
