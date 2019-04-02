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
	
	@GetMapping("/getData")
	private String getDataAndInsert() throws IOException {
		
		dataCollectorService.getData();
		
		return "ok";
	}
	
	
	

}
