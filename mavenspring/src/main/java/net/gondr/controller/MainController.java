package net.gondr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MainController{
	
	@GetMapping("/hello")
	public String hello(Model model) {
		return "hello";
	}
	
	@GetMapping("/")
	public String main(Model model) {
		model.addAttribute("msg", "양영디지털고등학교");
		return "main";
	}
}
