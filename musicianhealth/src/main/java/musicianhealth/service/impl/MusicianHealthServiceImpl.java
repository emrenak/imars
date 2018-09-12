package musicianhealth.service.impl;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.currentDate;
import static com.mongodb.client.model.Updates.set;

import java.util.ArrayList;
import java.util.List;

import musicianhealth.exception.MusicianHealthNotFoundException;
import musicianhealth.service.MusicianHealthService;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.imars.core.domain.MusicianHealth;
import com.imars.core.service.CollectionFactoryService;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Sorts;

@Service
public class MusicianHealthServiceImpl implements MusicianHealthService {

	@Autowired
	CollectionFactoryService collectionFactoryService;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public MusicianHealth get(String email) throws MusicianHealthNotFoundException {
		logger.trace("inside musicianhealth get :" + email);
		MongoCollection<Document> musicianHealthCollection = collectionFactoryService.getCollection("musicianhealth");
		FindIterable<Document> mdocs = musicianHealthCollection.find(eq("email",email));
		Document doc = mdocs.first();
		MusicianHealth musicianHealth = null;
		if(doc != null){
			Gson gson = new Gson();
			musicianHealth = gson.fromJson(doc.toJson(), MusicianHealth.class);
		}else{
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
			}else if (level >= 100){
				level = 100;
			}
			musicianHealthCollection.updateOne(eq("email", email),
			        combine(set("level", level),currentDate("lastModified")));
			logger.info(email + " musician health is updated");
			return;
		}
		Document member = new Document("email", email)
	        .append("level",100);
		 musicianHealthCollection.insertOne(member);
		 logger.info(email + " musician health is added");
	}
	
	public List<MusicianHealth> rankByHealthLevel(){
		logger.trace("inside rankByHealthLevel");
		List<MusicianHealth> musicianHealths = new ArrayList<MusicianHealth>();
		MongoCollection<Document> musicianHealthCollection = collectionFactoryService.getCollection("musicianhealth");
		FindIterable<Document> mdocs = musicianHealthCollection.find();
		mdocs.sort(Sorts.descending("level"));
		for (Document doc : mdocs) {
			Gson gson = new Gson();
			MusicianHealth musicianHealth = gson.fromJson(doc.toJson(), MusicianHealth.class);
			musicianHealths.add(musicianHealth);
		}
		return musicianHealths;
	}

}
