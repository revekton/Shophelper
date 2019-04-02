package com.teneke.shophelper.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teneke.shophelper.entities.Item;
import com.teneke.shophelper.repository.ItemRepository;

@Service
public class FinderService {
	
	@Autowired
	ItemRepository repository;
	
	public List<Item> findData(String name, String brand, String model) {
		
		List<Item> itemList = repository.findByNameOrBrandOrModelContaining(name, brand, model);
		
		return itemList;
	}

}
