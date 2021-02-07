package net.gondr.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LevelDAOImpl implements LevelDAO{
	@Autowired
	private SqlSession session;
	
	private static final String ns = "net.gondr.mappers.LevelMapper";
	
	@Override
	public void updateLevel(String userid) {
		session.update(ns+".updateLevel", userid);
	}
	
	@Override
	public int selectLevel(int level) {
		return session.selectOne(ns+".selectLevel", level);
	}
	
	@Override
	public void updateEXP(String userid, int exp) {
		Map<String, Object> temp = new HashMap<>();
		temp.put("exp", exp);
		temp.put("userid", userid);
		session.update(ns+".updateEXP", temp);
	}
}
