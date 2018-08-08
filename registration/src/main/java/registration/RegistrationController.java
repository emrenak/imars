package registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
		} catch (Exception e) {
			validation.setDescription("Error occured");
		}
		return validation;
	}

}
