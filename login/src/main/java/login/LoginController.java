package login;

import login.exception.MemberNotFoundException;
import login.exception.PasswordExpiredException;
import login.service.LoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	@Autowired
	LoginService login;
	
	@RequestMapping("/member/login")
	public void login(@RequestParam(value="email", defaultValue="") String email, 
			@RequestParam(value="password", defaultValue="") String password) throws PasswordExpiredException, MemberNotFoundException{
		login.checkUser(email, password);
		
	}
	

	@RequestMapping("/member/update")
	public void updateMember(@RequestParam(value="email", defaultValue="") String email, 
			@RequestParam(value="password", defaultValue="") String password,
			@RequestParam(value="gender", defaultValue="") String gender,
			@RequestParam(value="name", defaultValue="") String name,
			@RequestParam(value="nickname", defaultValue="") String nickname,
			@RequestParam(value="instruments", defaultValue="") String instruments,
			@RequestParam(value="musicStyle", defaultValue="") String musicStyle,
			@RequestParam(value="influences", defaultValue="") String influences,
			@RequestParam(value="avatar", defaultValue="") String avatar){
		
	}
	
	@RequestMapping("/member/delete")
	public void delete(@RequestParam(value="email", defaultValue="") String email ){
		
	}
	

	@RequestMapping("/member/getMember")
	public Member getMember(@RequestParam(value="email", defaultValue="") String email){
		return null;
	}
}
