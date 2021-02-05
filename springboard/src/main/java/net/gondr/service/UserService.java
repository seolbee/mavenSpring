package net.gondr.service;

import net.gondr.domain.UserVO;

public interface UserService {
	
	public UserVO login(String userid, String password);
	
	public void register(UserVO user);
	
	public UserVO getUserInfo(String userid);
	
	public void updateLevel(String userid, String password);
	
	public int selectLevel(int level);
	
	public void updateEXP(String userid, String password);
}
