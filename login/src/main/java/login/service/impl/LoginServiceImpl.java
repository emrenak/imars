package login.service.impl;

import static com.mongodb.client.model.Filters.eq;

import java.util.Calendar;
import java.util.Date;

import login.exception.MemberNotFoundException;
import login.exception.PasswordExpiredException;
import login.service.LoginService;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imars.core.service.CollectionFactoryService;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

@Service
public class LoginServiceImpl implements LoginService{

	private static final long passwordExpiryPeriod = 15552000000L; //180 * 24 * 60 * 60 * 1000;
	
	@Autowired
	CollectionFactoryService collectionFactoryService;
	
	public void checkUser(String email, String password) throws PasswordExpiredException, MemberNotFoundException {
		MongoCollection<Document> members = collectionFactoryService.getCollection("members");
		FindIterable<Document> mdocs = members.find(eq("email",email));
		Document member = null;
		for (Document mdoc : mdocs) {
			if(mdoc.getString("email").equals(email)){
				member = mdoc;
				Calendar cal = Calendar.getInstance();
				Date activationDate = member.getDate("activationDate");
				if(cal.getTime().getTime() >= (activationDate.getTime() + passwordExpiryPeriod) ){
					throw new PasswordExpiredException("Your password is expired, please change it");
				}
				break;
			}
		}
		if(member==null){
			throw new MemberNotFoundException(email + " member not found");
		}
	}

}
