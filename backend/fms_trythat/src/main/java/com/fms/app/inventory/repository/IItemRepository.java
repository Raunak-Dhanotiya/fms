package com.fms.app.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fms.app.inventory.model.Item;

public interface IItemRepository extends JpaRepository<Item, Integer>,JpaSpecificationExecutor<Item>{

}
