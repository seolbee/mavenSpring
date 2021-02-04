package net.gondr.dao;

import net.gondr.domain.UserVO;

public interface UserDAO {
	public UserVO getUser(String userid);
	
	public UserVO loginUser(String userid, String password);
	
	public void insertUser(UserVO user);
	
	public void updateLevel(String userid, String password);
	
	public int selectLevel(int level);
	
	public void updateEXP(String userid, String password);
}
