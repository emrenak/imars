package registration.service;

import registration.exception.RegistrationExpiredException;
import registration.exception.TokenNotFoundException;
import registration.exception.UserAlreadyExistsException;

public interface Register {

	public void addRegistration(String email, String password) throws UserAlreadyExistsException;
	
	public void validate(String token) throws RegistrationExpiredException, TokenNotFoundException;
}
