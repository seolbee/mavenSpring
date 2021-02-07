package net.gondr.dao;


public interface LevelDAO {
	public void updateLevel(String userid);
	
	public int selectLevel(int level);
	
	public void updateEXP(String userid, int exp);
}
