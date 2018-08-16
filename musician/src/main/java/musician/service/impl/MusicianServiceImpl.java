package musician.service.impl;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.currentDate;
import static com.mongodb.client.model.Updates.set;
import musician.Musician;
import musician.exception.MusicianNotFoundException;
import musician.service.MusicianService;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.imars.core.service.CollectionFactoryService;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

@Service
public class MusicianServiceImpl implements MusicianService {

	@Autowired
	CollectionFactoryService collectionFactoryService;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public void updateMusician(String email, String instruments,
			String musicStyle, String influences) throws MusicianNotFoundException {
		logger.trace("inside updateMusician:" + email);
		MongoCollection<Document> membersCollection = collectionFactoryService.getCollection("musicians");
		FindIterable<Document> mdocs = membersCollection.find(eq("email",email));
		boolean isMusicianFound = false;
		for (Document mdoc : mdocs) {
			isMusicianFound = true;
			if("".equals(instruments)){
				instruments = mdoc.getString("instruments");
			}
			if("".equals(musicStyle)){
				musicStyle = mdoc.getString("musicStyle");
			}
			if("".equals(influences)){
				influences = mdoc.getString("influences");
			}
			break;
		}
		if(!isMusicianFound){
			throw new MusicianNotFoundException(email + " musician not found");
		}
		membersCollection.updateOne(eq("email", email),
		        combine(set("instruments", instruments), set("musicStyle", musicStyle), set("influences", influences), 
		        		currentDate("lastModified")));
		logger.info(email + " musician is updated");
		
	}

	@Override
	public Musician getMusician(String email) throws MusicianNotFoundException {
		logger.trace("inside getMusician:" + email);
		MongoCollection<Document> membersCollection = collectionFactoryService.getCollection("musicians");
		FindIterable<Document> mdocs = membersCollection.find(eq("email",email));
		Musician musician = null;
		for (Document mdoc : mdocs) {
			Gson gson = new Gson();
			musician = gson.fromJson(mdoc.toJson(), Musician.class);
			break;
		}
		if(musician == null){
			throw new MusicianNotFoundException(email + " musician not found");
		}
		return musician;
	}

	@Override
	public void addMusician(String email, String instruments,
			String musicStyle, String influences) {
		logger.trace("inside addMusician:" + email);
		MongoCollection<Document> musicianCollection = collectionFactoryService.getCollection("musicians");
		MongoCollection<Document> membersCollection = collectionFactoryService.getCollection("members");
		FindIterable<Document> mdocs = membersCollection.find(eq("email",email));
		ObjectId id = null;
		for (Document mdoc : mdocs) {
			id = mdoc.getObjectId("_id");
		}
		if(id != null){
			 Document doc = new Document("email", email)
	         .append("instruments", instruments)
	         .append("musicStyle", musicStyle)
	         .append("influences", influences)
	         .append("memberId", id);
			 musicianCollection.insertOne(doc);
			 logger.info(email + " is added as musician");
		}

		
	}


}
