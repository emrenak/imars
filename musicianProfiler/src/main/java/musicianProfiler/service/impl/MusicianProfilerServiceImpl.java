package musicianProfiler.service.impl;

import java.util.List;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import musicianProfiler.service.MusicianProfilerService;

@Service
@EnableAsync
public class MusicianProfilerServiceImpl implements MusicianProfilerService {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	@Async
	public void profile(int id, List<Document> musicianList) {
		logger.trace("Inside profile for id:" + id);
		for (Document document : musicianList) {
			logger.info("Profiling is done for:" + document.getString("email"));
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

}
