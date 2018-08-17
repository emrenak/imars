package virtuosity.service.impl;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.currentDate;
import static com.mongodb.client.model.Updates.set;

import java.util.HashMap;
import java.util.Map;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import virtuosity.Virtuoso;
import virtuosity.exception.VirtuosityNotFoundException;
import virtuosity.service.VirtuosityService;

import com.google.gson.Gson;
import com.imars.core.service.CollectionFactoryService;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

@Service
public class VirtuosityServiceImpl implements VirtuosityService {

	@Autowired
	CollectionFactoryService collectionFactoryService;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public Virtuoso get(String email) throws VirtuosityNotFoundException {
		MongoCollection<Document> virtuosityCollection = collectionFactoryService.getCollection("virtuosity");
		FindIterable<Document> mdocs = virtuosityCollection.find(eq("email",email));
		Virtuoso virtuoso = null;
		for (Document mdoc : mdocs) {
			Gson gson = new Gson();
			virtuoso = gson.fromJson(mdoc.toJson(), Virtuoso.class);
			break;
		}
		if(virtuoso == null){
			throw new VirtuosityNotFoundException(email + " virtuosity not found");
		}
		return virtuoso;
	}

	public void addInstrument(String email, String instrument) {
		logger.trace("inside add instrument :" + email);
		MongoCollection<Document> virtuosityCollection = collectionFactoryService.getCollection("virtuosity");
		FindIterable<Document> mdocs = virtuosityCollection.find(eq("email",email));
		boolean isVirtuosityExist = false;
		 for (Document doc : mdocs) {
			logger.info("virtuosity exists for email:" + email + ", adding instrument:" + instrument);
			isVirtuosityExist = true;
			Map<String, Integer> documentMapDetail = doc.get("instrumentList", Map.class);
			documentMapDetail.put(instrument, 0);
			virtuosityCollection.updateOne(eq("email", email),
			        combine(set("instrumentList", documentMapDetail),currentDate("lastModified")));
		 }
		if(!isVirtuosityExist){
			 Document doc = new Document("email", email);
			 Map<String, Integer> documentMapDetail = new HashMap<String, Integer>();
			 documentMapDetail.put(instrument, 0);
			 doc.put("instrumentList", documentMapDetail);
			 virtuosityCollection.insertOne(doc);
			 
		}
		logger.info(email + " instrument is added");
	}

	@Override
	public void removeInstrument(String email, String instrument) {
		logger.trace("inside remove instrument" + email);
		MongoCollection<Document> virtuosityCollection = collectionFactoryService.getCollection("virtuosity");
		FindIterable<Document> mdocs = virtuosityCollection.find(eq("email",email));
		 for (Document doc : mdocs) {
			 Map<String, Integer> documentMapDetail = doc.get("instrumentList", Map.class);
			 documentMapDetail.remove(instrument);
			 virtuosityCollection.updateOne(eq("email", email),
				        combine(set("instrumentList", documentMapDetail),currentDate("lastModified")));
			 logger.info(email + " instrument is removed");
		 }
		
	}

	@Override
	public void adjustVirtuosityLevel(String email, String instrument,
			int adjustment) {
		logger.trace("inside adjustVirtuosityLevel :" + email);
		MongoCollection<Document> virtuosityCollection = collectionFactoryService.getCollection("virtuosity");
		FindIterable<Document> mdocs = virtuosityCollection.find(eq("email",email));
		 for (Document doc : mdocs) {
			Map<String, Integer> documentMapDetail = doc.get("instrumentList", Map.class);
			int level = documentMapDetail.get(instrument);
			level += adjustment;
			documentMapDetail.put(instrument, level);
			virtuosityCollection.updateOne(eq("email", email),
			        combine(set("instrumentList", documentMapDetail),currentDate("lastModified")));
		 }
		
	}
	
}
