package sample.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DuplicateKeyException;

import sample.common.dao.mapper.LoginMapper;
import sample.common.dao.entity.Login;

@Service
public class LoginService {

	private final LoginMapper loginMapper;
	private final PasswordEncoder passwordEncoder;
	private final String dummyHash;

	public LoginService(LoginMapper loginMapper, PasswordEncoder passwordEncoder) {
		this.loginMapper = loginMapper;
		this.passwordEncoder = passwordEncoder;
		this.dummyHash = passwordEncoder.encode("dummy-password-for-timing-safety");
	}

	@Transactional
	public boolean register(Login login) {
		if (loginMapper.findByUsername(login.getUsername()) != null) {
			return false;
		}
		login.setPassword(passwordEncoder.encode(login.getPassword()));
		try {
			loginMapper.insert(login);
		} catch (DuplicateKeyException e) {
			return false;
		}
		return true;
	}

	@Transactional(readOnly = true)
	public Login authenticate(String username, String rawPassword) {
		Login login = loginMapper.findByUsername(username);
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