package net.gondr.controller;

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
import net.gondr.domain.UploadResponse;
import net.gondr.domain.UserVO;
import net.gondr.service.BoardService;
import net.gondr.util.FileUtil;
import net.gondr.util.MediaUtil;
import net.gondr.validator.BoardValidator;

@Controller
@RequestMapping("/board/")
public class BoardController {
	
	@Autowired
	private ServletContext context;
	
	@Autowired
	private BoardService service;
	
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
		service.writeArticle(board);
		return "redirect:/board";
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
		return "board/view";
	}
}
