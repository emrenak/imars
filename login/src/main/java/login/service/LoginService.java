package login.service;

import login.Member;
import login.exception.MemberNotFoundException;
import login.exception.NameException;
import login.exception.NicknameException;
import login.exception.PasswordExpiredException;

public interface LoginService {

	public void checkMember(String email, String password)
			throws PasswordExpiredException, MemberNotFoundException;

	public void updateMember(String email, String password, String gender,
			String name, String surname, String nickname, String instruments,
			String musicStyle, String influences, String avatar, String status) throws MemberNotFoundException, NameException, NicknameException;
	
	public void deleteMember(String email);
	
	public Member getMember(String email) throws MemberNotFoundException;
	
	public void addMember(String email, String password);

}
