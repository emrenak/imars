package collaborator.service;

import java.util.List;

import collaborator.Message;


public interface CollaboratorService {

	public void sendMessage(String from,String to, String message);
	public List<Message> getSentMessages(String from);
	public List<Message> getMyMessages(String to);
	public void readMessage(String id);
	
}
