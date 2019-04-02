package com.teneke.shophelper.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.teneke.shophelper.entities.Item;

@Repository
public interface ItemRepository extends CrudRepository<Item, Integer>{

	public List<Item> findByNameOrBrandOrModel(String name, String brand, String model);
	
	public List<Item> findByNameOrBrandOrModelContaining(String name, String brand, String model);
	
	public List<Item> findByNameAndBrandAndModelContaining(String name, String brand, String model);
	
}
	