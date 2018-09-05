package musicianScheduler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class ActivityCouldNotBeExecutedException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ActivityCouldNotBeExecutedException (String msg){
		super(msg);
	}

}
