package net.gondr.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.gondr.dao.UserDAO;
import net.gondr.domain.FormVO;
import net.gondr.domain.UserVO;

@Controller
@RequestMapping("/user/")
public class UserController {
	
	@Autowired
	private UserDAO dao;
	
	@RequestMapping(value="regist", method=RequestMethod.GET)
	public String viewRegist(Model model, HttpSession session) {
		model.addAttribute("msg", session.getAttribute("msg"));
		session.removeAttribute("msg");
		return "user/regist";
	}
	
	@RequestMapping(value="regist", method=RequestMethod.POST)
	public String RegistProcess(Model model, FormVO user, HttpSession session) {
		UserVO vo = dao.selectUser(user.getUserid());
		if(vo != null) {
			session.setAttribute("msg", "아이디가 중복됩니다.");
			return "redirect:/user/regist";
		}
		
		if(!user.getPassword().equals(user.getPassword_ok())) {
			session.setAttribute("msg", "비밀번호와 비밀번호 확인이 틀립니다.");
			return "redirect:/user/regist";
		}
		UserVO temp = new UserVO();
		temp.setUserid(user.getUserid());
		temp.setUsername(user.getName());
		temp.setPassword(user.getPassword());
		dao.insertUser(temp);
		return "redirect:/user/login";
	}
	
	@RequestMapping(value="login", method=RequestMethod.GET)
	public String viewLogin() {
		return "user/login";
	}
	
	@RequestMapping(value="login", method=RequestMethod.POST)
	public String loginProcess(UserVO vo, HttpSession session) {
		UserVO temp = dao.selectUser(vo.getUserid());
		if(temp != null) {
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
			session.removeAttribute("user");
		}
		return "redirect:/";
	}
	
	@RequestMapping(value="info", method=RequestMethod.GET)
	public String viewInfoPage(HttpSession session) {
		return "user/info";
	}
	
	@RequestMapping(value="data/{userid}", method=RequestMethod.GET)
	public @ResponseBody Object getUserData(@PathVariable String userid) {
		UserVO temp = dao.selectUser(userid);
		return temp;
	}

}
