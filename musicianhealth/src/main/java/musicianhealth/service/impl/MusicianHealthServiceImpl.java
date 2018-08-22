package musicianhealth.service.impl;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.currentDate;
import static com.mongodb.client.model.Updates.set;
import musicianhealth.MusicianHealth;
import musicianhealth.exception.MusicianHealthNotFoundException;
import musicianhealth.service.MusicianHealthService;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.imars.core.service.CollectionFactoryService;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

@Service
public class MusicianHealthServiceImpl implements MusicianHealthService {

	@Autowired
	CollectionFactoryService collectionFactoryService;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public MusicianHealth get(String email) throws MusicianHealthNotFoundException {
		logger.trace("inside musicianhealth get :" + email);
		MongoCollection<Document> musicianHealthCollection = collectionFactoryService.getCollection("musicianhealth");
		FindIterable<Document> mdocs = musicianHealthCollection.find(eq("email",email));
		MusicianHealth musicianHealth = null;
		for (Document mdoc : mdocs) {
			Gson gson = new Gson();
			musicianHealth = gson.fromJson(mdoc.toJson(), MusicianHealth.class);
			break;
		}
		if(musicianHealth == null){
			throw new MusicianHealthNotFoundException(email + " musicianhealth not found");
		}
		return musicianHealth;
	}

	public void adjustMusicianHealth(String email, int adjustment) {
		logger.trace("inside adjustMusicianHealth :" + email);
		MongoCollection<Document> musicianHealthCollection = collectionFactoryService.getCollection("musicianhealth");
		FindIterable<Document> mdocs = musicianHealthCollection.find(eq("email",email));
		for (Document doc : mdocs) {
			int level = doc.getInteger("level");
			level += adjustment;
			if(level<0){
				level = 0;
			}
			musicianHealthCollection.updateOne(eq("email", email),
			        combine(set("level", level),currentDate("lastModified")));
			logger.info(email + " musician health is updated");
			return;
		}
		Document member = new Document("email", email)
	        .append("level",adjustment);
		 musicianHealthCollection.insertOne(member);
		 logger.info(email + " musician health is added");
	}

}
