package net.gondr.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice("net.gondr.controller")
public class CommonExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public String handlerException(Model model, Exception e) {
		e.printStackTrace();
		model.addAttribute("title", "에러 클래스 : " + e.getClass().getName());
		model.addAttribute("errorMsg", e.getMessage());
		return "errorPage";
	}
}
