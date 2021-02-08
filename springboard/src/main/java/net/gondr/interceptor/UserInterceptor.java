package net.gondr.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import net.gondr.domain.BoardVO;
import net.gondr.domain.UserVO;
import net.gondr.service.BoardService;

public class UserInterceptor implements HandlerInterceptor{
	@Autowired
	private BoardService boardservice;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer id = Integer.parseInt(map.get("id"));
		BoardVO board = boardservice.viewArticle(id);
		if(board.getWriter().equals(user.getUserid())) {
			return true;
		}
		response.sendRedirect("/board/list");
		return false;
	}
}
