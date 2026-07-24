package sample.service.impl;

import org.springframework.stereotype.Service;

import sample.common.dao.LoginDao;
import sample.common.dao.entity.Login;
import sample.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {
	private final LoginDao loginDao;
	public LoginServiceImpl(LoginDao loginDao) {
		this.loginDao = loginDao;
	}
	
	private String hash(String rawPassword) {
		try {
			java.security.MessageDigest digest =
java.security.MessageDigest.getInstance("SHA-256");
			byte[] bytes = digest.digest(rawPassword.getBytes());
			StringBuilder sb = new StringBuilder();
			for (byte b : bytes) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public boolean register(Login login) {
		if (loginDao.findByUsername(login.getUsername()) != null) {
			return false;
		}
		String hashed = hash(login.getPassword());
		login.setPassword(hashed);
		loginDao.insert(login);
		return true;
	}
	
	@Override
	public Login authenticate(String username, String password) {
		Login login = loginDao.findByUsername(username);
		if (login == null) {
			return null;
		}
		String hashed = hash(password);
		if (login.getPassword().equals(hashed)) {
			return login;
		}
		return null;
	}
}