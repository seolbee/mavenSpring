package net.gondr.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import net.gondr.domain.LoginDTO;
import net.gondr.domain.RegisterDTO;
import net.gondr.domain.UserVO;
import net.gondr.service.UserService;
import net.gondr.util.FileUtil;
import net.gondr.validator.RegisterValidator;

@Controller
@RequestMapping("/user/")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private ServletContext context;
	
	@RequestMapping(value="register", method=RequestMethod.GET)
	public String viewRegisterPage(Model model) {
		model.addAttribute("registerDTO", new RegisterDTO());
		return "user/register";
	}
	
	@RequestMapping(value="register", method=RequestMethod.POST)
	public String registerProcess(RegisterDTO registerDTO, Errors errors) throws Exception{
		new RegisterValidator().validate(registerDTO, errors);
		if(errors.hasErrors()) {
			return "user/register";
		}
		
		
		String uploadPath = context.getRealPath("/WEB-INF/upload");
		
		MultipartFile file = registerDTO.getProfileImg();
		String upFile = "";
		if(!file.getOriginalFilename().equals("")) {
			upFile = FileUtil.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
		}
		
		UserVO user = new UserVO();
		user.setImg(upFile);
		user.setName(registerDTO.getUsername());
		user.setPassword(registerDTO.getPassword());
		user.setUserid(registerDTO.getUserid());
		
		service.register(user);
		
		return "redirect:/";
	}
	
	@RequestMapping(value="login", method=RequestMethod.POST)
	public String userLogin(LoginDTO loginDTO, HttpSession session, Model model) {
		
		if(loginDTO.getUserid() == null || loginDTO.getPassword().isEmpty()) {
			model.addAttribute("msg", "로그인 실패, 아이디와 비밀번호를 입력하세요");
			return "user/login";
		}
		
		UserVO user = service.login(loginDTO.getUserid(), loginDTO.getPassword());
		
		if(user == null) {
			model.addAttribute("msg", "로그인 실패, 아이디나 비밀번호가 틀렸습니다.");
			return "user/login";
		}
		
		int exp = service.selectLevel(user.getLevel());
		if((user.getExp() + 5) >= exp) service.updateLevel(loginDTO.getUserid(), loginDTO.getPassword());
		else service.updateEXP(loginDTO.getUserid(), loginDTO.getPassword());
		
		user = service.login(loginDTO.getUserid(), loginDTO.getPassword());
		session.setAttribute("user", user);
		
		return "redirect:/";
	}
	
	@RequestMapping(value="login", method=RequestMethod.GET)
	public String viewLoginPage(Model model) {
		model.addAttribute("loginDTO", new LoginDTO());
		return "user/login";
	}
	
	@RequestMapping(value="logout", method=RequestMethod.GET)
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/";
	}
}
