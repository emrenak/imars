package login.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.UNAUTHORIZED)
public class PasswordExpiredException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PasswordExpiredException(String msg){
		super(msg);
	}

	
}
