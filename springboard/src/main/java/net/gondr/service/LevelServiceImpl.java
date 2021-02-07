package net.gondr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.gondr.dao.LevelDAO;

@Service
public class LevelServiceImpl implements LevelService{
	@Autowired
	private LevelDAO dao;
	
	@Override
	public int selectLevel(Integer level) {
		return dao.selectLevel(level);
	}
	
	@Override
	public void updateEXP(String userid, int exp) {
		dao.updateEXP(userid, exp);
	}
	
	@Override
	public void updateLevel(String userid) {
		dao.updateLevel(userid);
	}
}
