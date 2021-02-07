package net.gondr.service;

import java.util.List;

import net.gondr.domain.BoardVO;
import net.gondr.domain.UserVO;

public interface BoardService {
	public void writeArticle(BoardVO board);
	
	public BoardVO viewArticle(Integer id);
	
	public List<BoardVO> getArticleList(Integer start, Integer cnt);
	
	public void updateArticle(BoardVO board);
	
	public void deleteArticle(Integer id);
	
	public Integer countArticle();
}
