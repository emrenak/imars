package registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import registration.exception.RegistrationExpiredException;
import registration.exception.TokenNotFoundException;
import registration.exception.UserAlreadyExistsException;
import registration.service.Register;

@RestController
public class RegistrationController {
	
	@Autowired
	Register register;
	
	@RequestMapping("/user/register")
	public void register(@RequestParam(value="email", defaultValue="") String email, 
			@RequestParam(value="password", defaultValue="") String password) throws UserAlreadyExistsException{
		register.addRegistration(email, password);
	}

	
	@RequestMapping("/user/validate")
	public void validate(@RequestParam(value="token", defaultValue="") String token) throws RegistrationExpiredException, TokenNotFoundException{
		register.validate(token);
	}
}
