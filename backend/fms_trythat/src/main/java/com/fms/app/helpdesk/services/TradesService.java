package com.fms.app.helpdesk.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fms.app.helpdesk.models.Trades;
import com.fms.app.helpdesk.repository.TradesRepository;
import com.fms.app.security.AuthorizeUserInfo;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericSpecification;
import com.fms.paginator.models.PagedResponse;

@Service
public class TradesService {

	@Autowired
	TradesRepository tradesRepository;

	@Autowired
	AuthorizeUserInfo userInfo;

	public Trades saveOrUpdate(Trades data) {
		return this.tradesRepository.save(data);
	}

	public PagedResponse<Trades> getAllTradesPaginated(FilterDTOCopy filterDto) {
		GenericSpecification<Trades> clientSpecification = new GenericSpecification<>();
        Specification<Trades> spec = clientSpecification.buildSpecificationMultiple(filterDto.getFilterCriteria());
        final String sortOrder = filterDto.getPaginationDTO().getSortOrder();
        final String sortBy[] = filterDto.getPaginationDTO().getSortBy();
        final int pageSize = filterDto.getPaginationDTO().getPageSize();
        final int pageNo = filterDto.getPaginationDTO().getPageNo();
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Trades> TradesPage = this.tradesRepository.findAll(spec,pageable);
        return PagedResponse.fromPage(TradesPage);
	}
	
	public List<Trades> getAllTrades() {
		return tradesRepository.findAll();
	}

	public Trades getTradeById(int tradeId) {
		return this.tradesRepository.findById(tradeId).orElse(null);
	}

	public void deleteTradeById(Trades obj) {
		this.tradesRepository.delete(obj);
	}

	public boolean checkTradeExists(int tradeId) {
		return tradesRepository.existsById(tradeId);
	}

}
