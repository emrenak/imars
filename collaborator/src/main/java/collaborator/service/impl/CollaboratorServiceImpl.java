package collaborator.service.impl;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.currentDate;
import static com.mongodb.client.model.Updates.set;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;





import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import collaborator.Message;
import collaborator.service.CollaboratorService;

import com.google.gson.Gson;
import com.imars.core.service.CollectionFactoryService;
import com.imars.core.utils.ImarsEnums.MessageStatus;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Sorts;

@Service
public class CollaboratorServiceImpl implements CollaboratorService {
	
	@Autowired
	CollectionFactoryService collectionFactoryService;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	public void sendMessage(String from, String to, String message) {
		logger.trace("inside sendMessage :" + from);
		MongoCollection<Document> messageCollection = collectionFactoryService.getCollection("messages");
		Document messageDoc = new Document("from", from)
	        .append("to",to).
	        append("message", message).
	        append("status", MessageStatus.UNREAD.getValue()).append("creationDate", Calendar.getInstance().getTime().toString());
		 messageCollection.insertOne(messageDoc);
		 logger.info(from + " message is sent to: " + to);
	}

	public List<Message> getSentMessages(String from) {
		logger.trace("inside getSentMessages :" + from);
		MongoCollection<Document> musicianCollection = collectionFactoryService.getCollection("messages");
		FindIterable<Document> mdocs = musicianCollection.find(eq("from",from));
		mdocs.sort(Sorts.descending("timestamp"));
		List<Message> messages = new ArrayList<Message>();
		for (Document mdoc : mdocs) {
			Gson gson = new Gson();
			Message message = gson.fromJson(mdoc.toJson(), Message.class);
			ObjectId id = mdoc.getObjectId("_id");
			message.setId(id.toHexString());
			messages.add(message);
		}
		return messages;
	}

	public List<Message> getMyMessages(String to) {
		logger.trace("inside getMyMessages :" + to);
		MongoCollection<Document> musicianCollection = collectionFactoryService.getCollection("messages");
		FindIterable<Document> mdocs = musicianCollection.find(eq("to",to));
		mdocs.sort(Sorts.descending("timestamp"));
		List<Message> messages = new ArrayList<Message>();
		for (Document mdoc : mdocs) {
			Gson gson = new Gson();
			Message message = gson.fromJson(mdoc.toJson(), Message.class);
			ObjectId id = mdoc.getObjectId("_id");
			message.setId(id.toHexString());
			messages.add(message);
		}
		return messages;
	}

	public void readMessage(String id) {
		logger.trace("inside readMessage");
		MongoCollection<Document> messageCollection = collectionFactoryService.getCollection("messages");
		messageCollection.updateOne(eq("_id", new ObjectId(id)),
		        combine(set("status", MessageStatus.READ.getValue()),currentDate("lastModified")));
		logger.trace("message is updated," + id);
	}

}
