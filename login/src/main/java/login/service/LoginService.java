package login.service;

import login.exception.MemberNotFoundException;
import login.exception.PasswordExpiredException;

public interface LoginService {

	public void checkUser(String email, String password) throws PasswordExpiredException, MemberNotFoundException;
}
