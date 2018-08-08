package registration.service.impl;

import org.bson.Document;
import org.springframework.stereotype.Service;

import com.imars.core.mongodb.ConnectionFactory;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import registration.service.Register;

@Service
public class RegisterImpl implements Register {

	public void addRegistration(String email, String password) {
		MongoDatabase database = ConnectionFactory.getInstance().getConnection();
		MongoCollection<Document> collection = database.getCollection("registration");
		 Document doc = new Document("email", email)
         .append("password", password);
		 collection.insertOne(doc);
	}

}
