package registration.service.impl;

import static com.mongodb.client.model.Filters.eq;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import registration.exception.RegistrationExpiredException;
import registration.exception.TokenNotFoundException;
import registration.exception.UserAlreadyExistsException;
import registration.service.Register;

import com.imars.core.service.CollectionFactoryService;
import com.imars.core.service.EmailService;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

@Service
public class RegisterImpl implements Register {
		
	@Autowired
	CollectionFactoryService collectionFactoryService;
	
	@Autowired
	EmailService emailService;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public void addRegistration(String email, String password) throws UserAlreadyExistsException {
		logger.trace("inside addRegistration:" + email);
		 MongoCollection<Document> registrationCollection = collectionFactoryService.getCollection("registration");
		 FindIterable<Document> rdocs = registrationCollection.find(eq("email",email));
		 for (Document registration : rdocs) {
				Date expiryDate = registration.getDate("expiryDate");
				MongoCollection<Document> members = collectionFactoryService.getCollection("members");
				boolean isUserAlreadyMember = false;
				FindIterable<Document> mdocs = members.find(eq("email",email));
				for (Document mdoc : mdocs) {
					// User is already a member, why to register ?
					isUserAlreadyMember = true;
				}
				Calendar cal = Calendar.getInstance();
				if(!isUserAlreadyMember && expiryDate.getTime() - cal.getTime().getTime() <= 0){
					// Registration expire date is passed, let user re-register if is not a member.
					registrationCollection.deleteMany(eq("email", email));
					break;
				}
				throw new UserAlreadyExistsException("User already exists");
			
		 }
		 String token = UUID.randomUUID().toString();
		 Date expiryDate = calculateExpiryDate(720);
		 Document doc = new Document("email", email)
         .append("password", password)
         .append("token", token)
         .append("expiryDate", expiryDate);
		 registrationCollection.insertOne(doc);
		 logger.info(email + " registration is added");
		 emailService.sendEmail(email, "Imars Registration Confirmation", generateRegistrationMessage(token));
		 
	}
	
	public void validate(String token) throws RegistrationExpiredException, TokenNotFoundException {
		logger.trace("inside validate:" + token);
		MongoCollection<Document> registrations = collectionFactoryService.getCollection("registration");
		FindIterable<Document> docs = registrations.find(eq("token", token));
		 boolean foundToken = false;
		 for (Document document : docs) {
				Date expiryDate = document.getDate("expiryDate");
				Calendar cal = Calendar.getInstance();
				if(expiryDate.getTime() - cal.getTime().getTime() <= 0){
					throw new RegistrationExpiredException("Registration is expired");
				}
				foundToken = true;
				MongoCollection<Document> members = collectionFactoryService.getCollection("members");
				 Document member = new Document("email", document.getString("email"))
		         .append("password", document.getString("password"))
		         .append("status", "A")
		         .append("activationDate", Calendar.getInstance().getTime());
				 members.insertOne(member);
				 logger.info(document.getString("email") + " is added as member");
				break;
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
    
    /*@TODO
    	Message must be taken from config files
    */
    private String generateRegistrationMessage(String token){
    	String confirmationUrl = "http://localhost:8080/validate?token=" + token;
    	String message = "Please validate your registration" + " rn" + confirmationUrl;
    	return message;
    }



}
