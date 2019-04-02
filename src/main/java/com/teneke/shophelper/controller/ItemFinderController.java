package com.teneke.shophelper.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.teneke.shophelper.entities.Item;
import com.teneke.shophelper.service.FinderService;

@RestController
@RequestMapping(value="/itemFinder", method = RequestMethod.GET)
public class ItemFinderController {
	
	@Autowired
	FinderService finderService;
	
	@GetMapping("/find")
	private List<Item> find(String name, String brand, String model) {
		
		List<Item> itemList = finderService.findData(name, brand, model);
		
		return itemList;
	}
	

}
