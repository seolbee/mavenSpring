package net.gondr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.gondr.dao.UserDAO;
import net.gondr.domain.UserVO;

@Repository
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDAO userDAO;
	
	@Override
	public UserVO getUserInfo(String userid) {
		return userDAO.getUser(userid);
	}
	
	@Override
	public UserVO login(String userid, String password) {
		return userDAO.loginUser(userid, password);
	}
	
	@Override
	public void register(UserVO user) {
		userDAO.insertUser(user);
	}
}
