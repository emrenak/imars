package musicianwealth.service.impl;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.currentDate;
import static com.mongodb.client.model.Updates.set;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import musicianwealth.exception.MusicianWealthNotFoundException;
import musicianwealth.service.MusicianWealthService;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.imars.core.domain.Asset;
import com.imars.core.domain.MusicianWealth;
import com.imars.core.service.CollectionFactoryService;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Sorts;

@Service
public class MusicianWealthServiceImpl implements MusicianWealthService {

	@Autowired
	CollectionFactoryService collectionFactoryService;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public MusicianWealth get(String email) throws MusicianWealthNotFoundException {
		logger.trace("inside musicianwealth get :" + email);
		MongoCollection<Document> musicianWealthCollection = collectionFactoryService.getCollection("musicianwealth");
		FindIterable<Document> mdocs = musicianWealthCollection.find(eq("email",email));
		MusicianWealth musicianWealth = null;
		for (Document mdoc : mdocs) {
			Gson gson = new Gson();
			musicianWealth = gson.fromJson(mdoc.toJson(), MusicianWealth.class);
			break;
		}
		if(musicianWealth == null){
			throw new MusicianWealthNotFoundException(email + " musicianwealth not found");
		}
		return musicianWealth;
	}

	public void adjustMusicianWealth(String email, int adjustment) {
		logger.trace("inside adjustMusicianWealth :" + email);
		MongoCollection<Document> musicianWealthCollection = collectionFactoryService.getCollection("musicianwealth");
		FindIterable<Document> mdocs = musicianWealthCollection.find(eq("email",email));
		for (Document doc : mdocs) {
			int level = doc.getInteger("level");
			level += adjustment;
			if(level<0){
				level = 0;
			}
			musicianWealthCollection.updateOne(eq("email", email),
			        combine(set("level", level),currentDate("lastModified")));
			logger.info(email + " musician wealth is updated");
			return;
		}
		Document member = new Document("email", email)
	        .append("level",adjustment);
		 musicianWealthCollection.insertOne(member);
		 logger.info(email + " musician wealth is added");
	}

	@Override
	public void addAssets(String email, String asset, int numOfasset) {
		logger.trace("inside addAssets :" + email);
		MongoCollection<Document> musicianWealthCollection = collectionFactoryService.getCollection("musicianwealth");
		FindIterable<Document> mdocs = musicianWealthCollection.find(eq("email",email));
		Document doc = mdocs.first();
		Map<String, String> assetList = null;
		if(doc != null){
			assetList = doc.get("assetList", Map.class);
			if(assetList == null){
				assetList = new HashMap<String, String>();
				doc.put("assetList", assetList);
			}
		}else{
			assetList = new HashMap<String, String>();
			doc = new Document("email", email)
	        .append("level",0).append("assetList", assetList);
			musicianWealthCollection.insertOne(doc);
			mdocs = musicianWealthCollection.find(eq("email",email));
			doc = mdocs.first();
		}
		assetList = doc.get("assetList", Map.class); // Due to codec exception, re-get the asset list
		String assetCount = assetList.get(asset);
		Long count = 0L;
		if(assetCount != null){
			count = Long.parseLong(assetCount);
		}
		count += numOfasset;
		assetList.put(asset, count.toString());
		musicianWealthCollection.updateOne(eq("email", email),
		        combine(set("assetList", assetList),currentDate("lastModified")));
		logger.info(email + " asset is added");
			
	}
		
	
	
	public List<MusicianWealth> rankByWealthLevel(){
		logger.trace("inside rankByWealthLevel");
		List<MusicianWealth> musicianWealths = new ArrayList<MusicianWealth>();
		MongoCollection<Document> musicianWealthCollection = collectionFactoryService.getCollection("musicianwealth");
		FindIterable<Document> mdocs = musicianWealthCollection.find();
		mdocs.sort(Sorts.descending("level"));
		for (Document doc : mdocs) {
			Gson gson = new Gson();
			MusicianWealth musicianWealth = gson.fromJson(doc.toJson(), MusicianWealth.class);
			musicianWealths.add(musicianWealth);
		}
		return musicianWealths;
	}

	
	//TODO a level calculator is needed. it may be called from addassets

}
