package com.fms.app.helpdesk.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.app.helpdesk.models.Parts;
import com.fms.app.helpdesk.models.RequestParts;
import com.fms.app.helpdesk.repository.RequestPartsRepository;
import com.fms.app.security.AuthorizeUserInfo;

@Service
public class RequestPartsService {
	@Autowired
	RequestPartsRepository requestPartsRepository;

	@Autowired
	AuthorizeUserInfo userInfo;

	@Autowired
	PartsService partsService;

	public void saveOrUpdate(RequestParts data) {
		if (data.getAddedBy() == null || data.getAddedBy() == 0) {
			int addedBy = userInfo.getUserIfo().getUserId();
			data.setAddedBy(addedBy);
		}

		this.requestPartsRepository.save(data);
	}

	public List<RequestParts> getByRequestId(int requestId) {
		return this.requestPartsRepository.findByRequestId(requestId);
	}

	public void deleteById(int id) {
		this.requestPartsRepository.deleteById(id);
	}

	public RequestParts getById(int id) {
		return this.requestPartsRepository.findById(id).orElse(null);
	}

	public boolean checkIsPartExist(int requestId, int partId) {

		return this.requestPartsRepository.existsByRequestIdAndPartId(requestId, partId);
	}

	public void updatePartUsage(int requestId) {
		List<RequestParts> requestParts = this.getByRequestId(requestId);
		if (requestParts.size() > 0) {
			requestParts.forEach(part -> {
				Parts partData = partsService.getByPartId(part.getPartId());
				int avalQnty = partData.getQutOnHand();
				int reqQnty = part.getReqQuantity();
				int usedQnty = part.getActualQuantityUsed() == null ? 0: part.getActualQuantityUsed();
				avalQnty = avalQnty + (reqQnty - usedQnty);
				partData.setQutOnHand(avalQnty);
				partsService.saveOrUpdate(partData);
			});
		}
	}
	
	public RequestParts getByRequestIdAndPartId(int requestId, int partId) {
		return this.requestPartsRepository.findByRequestIdAndPartId(requestId,partId);
	}

}
