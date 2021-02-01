package net.gondr.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.gondr.domain.SampleVO;
import net.gondr.domain.UserVO;

@Controller
@RequestMapping("/user/")
public class UserController {
	
	@RequestMapping(value="regist", method=RequestMethod.GET)
	public String viewRegist() {
		return "user/regist";
	}
	
	@RequestMapping(value="regist", method=RequestMethod.POST)
	public String RegistProcess(Model model, SampleVO vo) {
		if(vo.getId().equals("gondr") && vo.getPassword().equals("1234") && vo.getName().equals("최선한") && vo.getEmail().equals("gondr@naver.com")) {
			System.out.println("성공");
			model.addAttribute("vo", vo);
			return "hello";
		} else {
			return "redirect:/user/regist";
		}
	}
	
	@RequestMapping(value="login", method=RequestMethod.GET)
	public String viewLogin() {
		return "user/login";
	}
	
	@RequestMapping(value="login", method=RequestMethod.POST)
	public String loginProcess(UserVO vo, HttpSession session) {
		if(vo.getUserid().equals("gondr") && vo.getPassword().equals("1234")) {
			session.setAttribute("user", vo);
			return "redirect:/";
		} else {
			session.setAttribute("msg", "아이디 혹은 비밀번호가 틀렸습니다.");
			return "redirect:/user/login";
		}
		
	}
	
	@RequestMapping(value="logout", method=RequestMethod.GET)
	public String logoutProcess(HttpSession session) {
		if(session.getAttribute("user") != null) {
			session.invalidate();
		}
		return "redirect:/";
	}

}
