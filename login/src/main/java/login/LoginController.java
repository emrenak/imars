package login;

import login.exception.MemberNotFoundException;
import login.exception.NameException;
import login.exception.NicknameException;
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
		login.checkMember(email, password);
		
	}
	
	@RequestMapping("/member/update")
	public void updateMember(@RequestParam(value="email", defaultValue="") String email, // It is expected that the validation of these parameters are handled in client side. 
			@RequestParam(value="password", defaultValue="") String password,
			@RequestParam(value="gender", defaultValue="") String gender,
			@RequestParam(value="name", defaultValue="") String name,
			@RequestParam(value="surname", defaultValue="") String surname,
			@RequestParam(value="nickname", defaultValue="") String nickname,
			@RequestParam(value="instruments", defaultValue="") String instruments,
			@RequestParam(value="musicStyle", defaultValue="") String musicStyle,
			@RequestParam(value="influences", defaultValue="") String influences,
			@RequestParam(value="avatar", defaultValue="") String avatar,
			@RequestParam(value="status", defaultValue="A") String status) throws MemberNotFoundException, NameException, NicknameException{
		login.updateMember(email, password, gender, name, surname, nickname, instruments, musicStyle, influences, avatar,status);
		
	}
	
	@RequestMapping("/member/delete")
	public void delete(@RequestParam(value="email", defaultValue="") String email ){
		login.deleteMember(email);
	}
	

	@RequestMapping("/member/get")
	public Member getMember(@RequestParam(value="email", defaultValue="") String email) throws MemberNotFoundException{
		return login.getMember(email);
	}
	
	@RequestMapping("/member/add")
	public void addMember(@RequestParam(value="email", defaultValue="") String email,
			@RequestParam(value="password", defaultValue="") String password){
		login.addMember(email, password);
	}
}
