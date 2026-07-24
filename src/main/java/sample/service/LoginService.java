package sample.service;

import sample.common.dao.entity.Login;

public interface LoginService {
	boolean register(Login login);
	Login authenticate(String username, String password);
}