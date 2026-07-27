package sample.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sample.common.dao.LoginDao;
import sample.common.dao.entity.Login;
import sample.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	private final LoginDao loginDao;
	private final PasswordEncoder passwordEncoder;
	private final String dummyHash; 

	public LoginServiceImpl(LoginDao loginDao, PasswordEncoder passwordEncoder) {
		this.loginDao = loginDao;
		this.passwordEncoder = passwordEncoder;
		this.dummyHash = passwordEncoder.encode("dummy-password-for-timing-safety"); 
	}

	@Override
	@Transactional
	public boolean register(Login login) {
		if (loginDao.findByUsername(login.getUsername()) != null) {
			return false;
		}
		login.setPassword(passwordEncoder.encode(login.getPassword()));
		loginDao.insert(login);
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public Login authenticate(String username, String rawPassword) {
		Login login = loginDao.findByUsername(username);
		if (login == null) {
			passwordEncoder.matches(rawPassword, dummyHash);
			return null;
		}
		if (!passwordEncoder.matches(rawPassword, login.getPassword())) {
			return null;
		}
		return login;
	}
}