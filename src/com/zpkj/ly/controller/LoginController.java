package com.zpkj.ly.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class LoginController {
	
	@RequestMapping("/toLogin")
	public String toLogin(){
		return "login";
	}
	
	@RequestMapping("/login")
	public String login(@RequestParam String username,
						@RequestParam String password,
						HttpSession httpSession){
						System.out.println(username);
						System.out.println(password);
						httpSession.setAttribute("username", username);
							return "redirect:/test/itemList.action";
	}

}
