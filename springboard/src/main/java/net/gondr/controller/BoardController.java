package net.gondr.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.nhncorp.lucy.security.xss.LucyXssFilter;
import com.nhncorp.lucy.security.xss.XssSaxFilter;

import net.gondr.domain.BoardVO;
import net.gondr.domain.Criteria;
import net.gondr.domain.UploadResponse;
import net.gondr.domain.UserVO;
import net.gondr.service.BoardService;
import net.gondr.service.LevelService;
import net.gondr.service.UserService;
import net.gondr.util.FileUtil;
import net.gondr.util.MediaUtil;
import net.gondr.validator.BoardValidator;

@Controller
@RequestMapping("/board/")
public class BoardController {
	
	@Autowired
	private ServletContext context;
	
	@Autowired
	private BoardService boardservice;
	
	@Autowired
	private LevelService levelservice;
	
	@Autowired
	private UserService userservice;
	
	
	private BoardValidator validator = new BoardValidator();
	
	@RequestMapping(value="write", method=RequestMethod.GET)
	public String viewWritePage(Model model) {
		model.addAttribute("boardVO", new BoardVO());
		return "board/write";
	}
	
	@RequestMapping(value="write", method=RequestMethod.POST)
	public String writeProcess(BoardVO board, HttpSession session, Errors errors) {
		validator.validate(board, errors);
		if(errors.hasErrors()) {
			return "board/write";//에러가 발생시에 다시 글쓰기 페이지로
		}
		
		UserVO user = (UserVO) session.getAttribute("user");
		board.setWriter(user.getUserid());
		LucyXssFilter filter = XssSaxFilter.getInstance("lucy-xss-sax.xml");
		String clean = filter.doFilter(board.getContent());
		board.setContent(clean);
		boardservice.writeArticle(board);
		
		int exp = levelservice.selectLevel(user.getLevel());
		if(user.getExp() + 5 >= exp) levelservice.updateLevel(user.getUserid());
		else levelservice.updateEXP(user.getUserid(), 5);
		user = userservice.getUserInfo(user.getUserid());
		session.setAttribute("user", user);
		return "redirect:/board/list";
	}
	
	@RequestMapping(value="upload", method=RequestMethod.POST)
	@ResponseBody
	public UploadResponse handleImageUpload(@RequestParam("file") MultipartFile file, HttpServletResponse response) {
		String uploadPath = context.getRealPath("/images");
		UploadResponse upResponse = new UploadResponse();
		try {
			String name = file.getOriginalFilename();//원본이름
			String ext = name.substring(name.lastIndexOf(".")+1);//확장자
			if(MediaUtil.getMediaType(ext) == null) {
				throw new Exception("올바르지 않은 파일 형식");
			}
			String upFile = FileUtil.uploadFile(uploadPath, name, file.getBytes());
			upResponse.setThumbImage("/images"+upFile);
			upFile = "/images/" + upFile.substring(3, upFile.length());
			upResponse.setUploadImage(upFile);
			upResponse.setMsg("성공적으로 업로드 됨");
			upResponse.setResult(true);
		} catch (Exception e) {
			e.printStackTrace();
			upResponse.setMsg(e.getMessage());
			upResponse.setResult(false);
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		return upResponse;
	}
	
	@RequestMapping(value="view/{id}", method=RequestMethod.GET)
	public String viewArticle(@PathVariable Integer id, Model model) {
		BoardVO board = boardservice.viewArticle(id);
		model.addAttribute("board", board);
		return "board/view";
	}
	
	@RequestMapping(value="list", method=RequestMethod.GET)
	public String viewList(Criteria c, Model model) {
		//List<BoardVO> list = boardservice.getArticleList((c.getPage() - 1) * c.getPerPageNum(), c.getPerPageNum());
		List<BoardVO> list = boardservice.getCriteriaList(c);
		model.addAttribute("list", list);
		Integer cnt = boardservice.countCriteria(c);
		c.calculate(cnt);
		model.addAttribute("c", c);
		return "board/list";
	}
	
	@RequestMapping(value="edit/{id}", method=RequestMethod.GET)
	public String viewEdit(@PathVariable Integer id, Model model, HttpSession session) {
		BoardVO temp = boardservice.viewArticle(id);
		UserVO user = (UserVO) session.getAttribute("user");
		if(!temp.getWriter().equals(user.getUserid())){
			
		}
		model.addAttribute("boardVO", temp);
		return "board/edit";
	}
	
	@RequestMapping(value="delete/{id}", method=RequestMethod.GET)
	public String delete(@PathVariable Integer id) {
		boardservice.deleteArticle(id);
		return "redirect:/board/list/";
	}
	
	@RequestMapping(value="edit", method=RequestMethod.POST)
	public String editProcess(BoardVO board, Errors errors) {
		validator.validate(board, errors);
		if(errors.hasErrors()) {
			return "board/edit";
		}
		LucyXssFilter filter = XssSaxFilter.getInstance("lucy-xss-sax.xml");
		String clean = filter.doFilter(board.getContent());
		board.setContent(clean);
		boardservice.updateArticle(board);
		return "redirect:/board/view/"+board.getId();
	}
}
