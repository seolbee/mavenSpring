package net.gondr.service;

public interface LevelService {
	public void updateLevel(String userid);
	
	public int selectLevel(Integer level);
	
	public void updateEXP(String userid, int exp);
}
