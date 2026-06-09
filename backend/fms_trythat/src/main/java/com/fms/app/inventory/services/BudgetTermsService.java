package com.fms.app.inventory.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.app.inventory.model.BudgetTerms;
import com.fms.app.inventory.model.dto.BudgetTermOutputDto;
import com.fms.app.inventory.repository.IBudgetTermsRepository;

@Service
public class BudgetTermsService {

	private static final Logger logger = LogManager.getLogger(BudgetTermsService.class);

	@Autowired
	IBudgetTermsRepository budgetTermsRepository;

	public void saveOrUpdate(BudgetTerms budgetTerms) {
		budgetTermsRepository.save(budgetTerms);
	}

	public List<BudgetTermOutputDto> getAllBudgetTerms() {
		List<BudgetTerms> budgetTerms = budgetTermsRepository.findAll();
		List<BudgetTermOutputDto> budgetTermOps = new ArrayList<BudgetTermOutputDto>();
		for (BudgetTerms bt : budgetTerms) {
			BudgetTermOutputDto btOp = this.getBudgetTermOutput(bt);
			budgetTermOps.add(btOp);
		}
		return budgetTermOps;
	}

	public BudgetTermOutputDto getBudgetTermsById(int budgetTermId) {
		BudgetTerms budgetTerms = budgetTermsRepository.findById(budgetTermId).orElse(new BudgetTerms());
		return this.getBudgetTermOutput(budgetTerms);

	}

	public void deleteByBudgetTermId(int budgetTermId) {
		budgetTermsRepository.deleteById(budgetTermId);
	}

	private BudgetTermOutputDto getBudgetTermOutput(BudgetTerms budgetTerms) {
		BudgetTermOutputDto btdto = new BudgetTermOutputDto(budgetTerms.getBudgetTermId(), budgetTerms.getName(), "",
				"");
		String dateFrom = this.getStrigFromDate(budgetTerms.getDateFrom());
		String dateTo = this.getStrigFromDate(budgetTerms.getDateTo());
		btdto.setDateFrom(dateFrom);
		btdto.setDateTo(dateTo);
		return btdto;
	}

	public String getStrigFromDate(Date date) {
		Instant instant = date.toInstant();
		LocalDate ld = instant.atZone(ZoneId.systemDefault()).toLocalDate();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return ld.format(formatter);
	}

}
