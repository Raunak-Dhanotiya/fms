package com.fms.app.helpdesk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fms.app.helpdesk.models.CraftsPerson;

public interface CraftsPersonRepository extends JpaRepository<CraftsPerson, Integer>,JpaSpecificationExecutor<CraftsPerson>{

	boolean existsByName(String name);

	boolean existsByEmail(String email);

	List<CraftsPerson> findAllByPrimaryTrade(Integer tradeId);

}
