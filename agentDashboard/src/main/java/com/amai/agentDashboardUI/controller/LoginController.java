package com.amai.agentDashboardUI.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

	@RequestMapping("/")
	public String login() {
		System.out.println("Inside Login controller");

		return "home";
	}
}
