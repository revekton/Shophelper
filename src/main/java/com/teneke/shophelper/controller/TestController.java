package com.teneke.shophelper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teneke.shophelper.service.TestService;

@RestController
public class TestController {

	@Autowired
	TestService testService;
	
	@GetMapping("/hede")
	public String controller() {
		
		return testService.testService2();
	}
	
}