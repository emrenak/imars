package registration.exception;

public class RegistrationExpiredException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RegistrationExpiredException(String msg){
		super(msg);
	}

}
