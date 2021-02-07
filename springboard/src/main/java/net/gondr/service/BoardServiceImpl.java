package net.gondr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.gondr.dao.BoardDAO;
import net.gondr.dao.LevelDAO;
import net.gondr.dao.UserDAO;
import net.gondr.domain.BoardVO;
import net.gondr.domain.UserVO;

@Service
public class BoardServiceImpl implements BoardService{
	@Autowired
	private BoardDAO boardDAO;
	
	@Override
	public void writeArticle(BoardVO board) {
		boardDAO.write(board);
	}
	
	@Override
	public BoardVO viewArticle(Integer id) {
		return boardDAO.view(id);
	}
	
	@Override
	public List<BoardVO> getArticleList(Integer start, Integer cnt) {
		return boardDAO.list(start, cnt);
	}
	
	@Override
	public void updateArticle(BoardVO board) {
		boardDAO.update(board);
	}
	
	@Override
	public void deleteArticle(Integer id) {
		boardDAO.delete(id);
	}
	
	@Override
	public Integer countArticle() {
		return boardDAO.getCnt();
	}
}
