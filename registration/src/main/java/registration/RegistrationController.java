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
	
	@RequestMapping("/register")
	public Validation register(@RequestParam(value="email", defaultValue="") String email, 
			@RequestParam(value="password", defaultValue="") String password){
		Validation validation = new Validation();
		try {
			register.addRegistration(email, password);
			validation.setDescription("Check your email");
			return validation;
		}catch (UserAlreadyExistsException uee){
			validation.setDescription(email + ", " + uee.getMessage());
		}catch (Exception e) {
			validation.setDescription("Error occured");
		}
		return validation;
	}

	
	@RequestMapping("/validate")
	public Validation validate(@RequestParam(value="token", defaultValue="") String token){
		Validation validation = new Validation();
		try {
			register.validate(token);
			validation.setDescription("Validation Succeeded");
			return validation;
		}catch (TokenNotFoundException tnfe){
			validation.setDescription(tnfe.getMessage());
		}catch(RegistrationExpiredException ree){
			validation.setDescription(ree.getMessage());
		}catch (Exception e) {
			validation.setDescription("Error occured");
		}
		return validation;
	}
}
