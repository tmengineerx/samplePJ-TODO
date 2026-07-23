package sample.service;

import sample.common.dao.entity.Login;

public interface LoginService {
	void register(Login login);
	Login authenticate(String username, String password);
}