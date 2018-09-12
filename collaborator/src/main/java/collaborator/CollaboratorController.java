package collaborator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import collaborator.service.CollaboratorService;

@RestController
public class CollaboratorController {

	@Autowired
	CollaboratorService collaboratorService;
	
	@RequestMapping("/collaborator/sendMessage")
	public void sendMessage(@RequestParam(value="from", defaultValue="") String from,
			@RequestParam(value="to", defaultValue="") String to,
			@RequestParam(value="message", defaultValue="") String message){
		collaboratorService.sendMessage(from, to, message);
	}
	
	@RequestMapping("/collaborator/getSentMessages")
	public List<Message> getSentMessages(@RequestParam(value="from", defaultValue="") String from){
		return collaboratorService.getSentMessages(from);
	}
	
	@RequestMapping("/collaborator/getMyMessages")
	public List<Message> getMyMessages(@RequestParam(value="to", defaultValue="") String to){
		return collaboratorService.getMyMessages(to);
	}
	
	@RequestMapping("/collaborator/readMessage")
	public void readMessage(@RequestParam(value="id", defaultValue="") String id){
		 collaboratorService.readMessage(id);
	}

}
