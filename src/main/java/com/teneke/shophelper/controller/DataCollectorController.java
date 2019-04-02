package com.teneke.shophelper.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.teneke.shophelper.service.DataCollectorService;

@RestController
@RequestMapping(value="/dataCollector", method = RequestMethod.GET)
public class DataCollectorController {
	
	@Autowired
	DataCollectorService dataCollectorService;
	@GetMapping("/")
	private String homepage(){
	return "ana sayfa-taha";
	}
	@GetMapping("/getData")
	private String getDataAndInsert() throws IOException {
		System.out.println("213213");
		dataCollectorService.getData();
		System.out.println("123456");
		return "ok";
	}
	
	
	

}
