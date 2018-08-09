package registration.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.bson.Document;
import org.springframework.stereotype.Service;
import static com.mongodb.client.model.Filters.eq;

import registration.exception.RegistrationExpiredException;
import registration.exception.TokenNotFoundException;
import registration.exception.UserAlreadyExistsException;
import registration.service.Register;

import com.imars.core.mongodb.CollectionFactory;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

@Service
public class RegisterImpl implements Register {

	public void addRegistration(String email, String password) throws UserAlreadyExistsException {
		MongoCollection<Document> collection = CollectionFactory.getInstance().getCollection("registration");
		 FindIterable<Document> docs = collection.find(eq("email",email));
		 for (Document document : docs) {
			if(document.getString("email").equals(email)){
				throw new UserAlreadyExistsException("User already exists");
			}
		 }
		 String token = UUID.randomUUID().toString();
		 Date expiryDate = calculateExpiryDate(720);
		 Document doc = new Document("email", email)
         .append("password", password)
         .append("token", token)
         .append("expiryDate", expiryDate);
		 collection.insertOne(doc);
	}
	
	public void validate(String token) throws RegistrationExpiredException, TokenNotFoundException {
		MongoCollection<Document> registrations = CollectionFactory.getInstance().getCollection("registration");
		FindIterable<Document> docs = registrations.find(eq("token", token));
		 boolean foundToken = false;
		 for (Document document : docs) {
			if(document.getString("token").equals(token)){
				Date expiryDate = document.getDate("expiryDate");
				Calendar cal = Calendar.getInstance();
				if(expiryDate.getTime() - cal.getTime().getTime() <= 0){
					throw new RegistrationExpiredException("Registration is expired");
				}
				foundToken = true;
				MongoCollection<Document> members = CollectionFactory.getInstance().getCollection("members");
				 Document member = new Document("email", document.getString("email"))
		         .append("password", document.getString("password"))
		         .append("activationDate", Calendar.getInstance().getTime());
				 members.insertOne(member);
				break;
			}
		 }
		 if(!foundToken){
			 throw new TokenNotFoundException("Token is not found");
		 }
		
	}
	
    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }



}
