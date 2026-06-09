package com.fms.app.inventory.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.app.inventory.model.Item;
import com.fms.app.inventory.repository.IItemRepository;

@Service
public class ItemService {

	private static final Logger logger = LogManager.getLogger(ItemService.class);

	@Autowired
	IItemRepository iItemRepository;

	public void saveOrUpdate(Item item) {

		iItemRepository.save(item);

	}

	public List<Item> getAllItem() {
		return iItemRepository.findAll();
	}

	public Item getItemById(int itemId) {
		return iItemRepository.findById(itemId).orElse(new Item());
	}

	public void deleteByItemId(int itemId) {
		iItemRepository.deleteById(itemId);
	}

}
