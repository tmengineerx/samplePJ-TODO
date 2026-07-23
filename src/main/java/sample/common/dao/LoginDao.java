package sample.common.dao;

import org.springframework.stereotype.Repository;

import sample.common.dao.entity.Login;
import sample.common.dao.mapper.LoginMapper;

@Repository
public class LoginDao {
	
	private final LoginMapper loginMapper;
	
	public LoginDao(LoginMapper loginMapper) {
		this.loginMapper = loginMapper;
	}
	
	public Login findByUsername(String username) {
		return loginMapper.findByUsername(username);
	}
	
	public int insert(Login login) {
		return loginMapper.insert(login);
	}
}