package com.myApp.photoAppApiAccountManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountManagementController {

	@Autowired
	private Environment env;
	
	@GetMapping("/status/check")
	public String getStatus() {

		return "AccountManagement is Up and Running..."+"on PORT: "+env.getProperty("local.server.port");

	}

}
