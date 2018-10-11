package login.service.impl;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.not;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.currentDate;
import static com.mongodb.client.model.Updates.set;

import java.util.Calendar;
import java.util.Date;

import login.Member;
import login.exception.MemberNotFoundException;
import login.exception.NameException;
import login.exception.NicknameException;
import login.exception.PasswordExpiredException;
import login.service.LoginService;

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
public class LoginServiceImpl implements LoginService{

	private static final long passwordExpiryPeriod = 15552000000L; //180 * 24 * 60 * 60 * 1000;
	
	@Autowired
	CollectionFactoryService collectionFactoryService;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public void checkMember(String email, String password) throws PasswordExpiredException, MemberNotFoundException {
		logger.trace("inside checkMember:" + email);
		MongoCollection<Document> membersCollection = collectionFactoryService.getCollection("members");
		FindIterable<Document> mdocs = membersCollection.find(eq("email",email));
		boolean isMemberFound = false;
		for (Document mdoc : mdocs) {
			isMemberFound = true;
			logger.info("member is found:" + email);
			Calendar cal = Calendar.getInstance();
			Date activationDate = mdoc.getDate("activationDate");
			if(cal.getTime().getTime() >= (activationDate.getTime() + passwordExpiryPeriod) ){
				throw new PasswordExpiredException("Your password is expired, please change it");
			}
			break;
		}
		if(!isMemberFound){
			throw new MemberNotFoundException(email + " member not found");
		}
	}

	public void updateMember(String email, String password, String gender,
			String name, String surname, String nickname, String instruments,
			String musicStyle, String influences, String avatar, String status) throws MemberNotFoundException, NameException, NicknameException {
		logger.trace("inside updateMember:" + email);
		MongoCollection<Document> membersCollection = collectionFactoryService.getCollection("members");
		FindIterable<Document> mdocs = membersCollection.find(eq("email",email));
		boolean isMemberFound = false;
		for (Document mdoc : mdocs) {
			isMemberFound = true;
			if("".equals(password)){
				password = mdoc.getString("password");
			}
			if("".equals(gender)){
				gender = mdoc.getString("gender");
			}
			if("".equals(name)){
				name = mdoc.getString("name");
			}
			if("".equals(surname)){
				surname = mdoc.getString("surname");
			}
			if("".equals(nickname)){
				nickname = mdoc.getString("nickname");
			}
			if("".equals(avatar)){
				avatar = mdoc.getString("avatar");
			}
			break;
		}
		if(!isMemberFound){
			throw new MemberNotFoundException(email + " member not found");
		}
		//check name and nickname to be unique
		FindIterable<Document> ndocs = membersCollection.find(and(eq("name",name), not(eq("email",email))));
		for (Document ndoc : ndocs) {
			throw new NameException(name + " exists.  Name must be unique");
		}
		FindIterable<Document> nndocs = membersCollection.find(and(eq("nickname",nickname), not(eq("email",email))));
		for (Document nndoc : nndocs) {
			throw new NicknameException(nickname + " exists. Nickname must be unique");
		}
		membersCollection.updateOne(eq("email", email),
		        combine(set("password", password), set("gender", gender), set("name", name), set("surname", surname), set("nickname", nickname), set("avatar", avatar), set("status", status), 
		        		currentDate("lastModified")));
		logger.info(email + " is updated");
	}

	public void deleteMember(String email) {
		logger.trace("inside deleteMember:" + email);
		MongoCollection<Document> membersCollection = collectionFactoryService.getCollection("members");
		membersCollection.updateOne(eq("email", email),
		        combine(set("status", "D"), currentDate("lastModified")));
		logger.info(email + " is deleted/deactivated");
	}

	public Member getMember(String email) throws MemberNotFoundException {
		logger.trace("inside getMember:" + email);
		Member member = null;
		MongoCollection<Document> membersCollection = collectionFactoryService.getCollection("members");
		FindIterable<Document> mdocs = membersCollection.find(eq("email",email));
		Document mdoc = mdocs.first();
		if(mdoc!=null){
			Gson gson = new Gson();
			member = gson.fromJson(mdoc.toJson(), Member.class);	
			MongoCollection<Document> signInCollection = collectionFactoryService.getCollection("signIns");
			Document signIned = new Document("email", email).append("loginDate", Calendar.getInstance().getTime());
			signInCollection.insertOne(signIned);
		}else{
			throw new MemberNotFoundException(email + " member not found");
		}
		return member;
	}

	@Override
	public void addMember(String email, String password) {
		MongoCollection<Document> members = collectionFactoryService.getCollection("members");
		 Document member = new Document("email", email)
        .append("password",password)
        .append("status", "A")
        .append("activationDate", Calendar.getInstance().getTime());
		 members.insertOne(member);
		 logger.info(email + " is added as member");
		
	}

}
