package com.fms.app.appParams.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fms.app.appParams.models.Messages;
import com.fms.app.appParams.repository.MessagesRepository;
import com.fms.app.security.AuthorizeUserInfo;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericSpecification;
import com.fms.paginator.models.PagedResponse;

@Service
public class MessagesService {
	@Autowired
	MessagesRepository messagesRepository;
	
	@Autowired
	AuthorizeUserInfo userInfo;
	
	public List<Messages> getAllMessages(){
		return messagesRepository.findAll();
	}
	
	public PagedResponse<Messages> getAllMessagesPaginated(FilterDTOCopy filterDto){
		GenericSpecification<Messages> clientSpecification = new GenericSpecification<>();
        Specification<Messages> spec = clientSpecification.buildSpecificationMultiple(filterDto.getFilterCriteria());
        final String sortOrder = filterDto.getPaginationDTO().getSortOrder();
        final String sortBy[] = filterDto.getPaginationDTO().getSortBy();
        final int pageSize = filterDto.getPaginationDTO().getPageSize();
        final int pageNo = filterDto.getPaginationDTO().getPageNo();
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Messages> messagesPage = messagesRepository.findAll(spec,pageable);
        return PagedResponse.fromPage(messagesPage);
	}
	public Messages getMessagesById(int msgId) {
		
		Messages msgs = messagesRepository.findById(msgId).get();
		return msgs;
	}
	public Messages getMessagesByProcessIdAndCode(Integer processId, String msgCode) {
		
		Messages msgs = messagesRepository.findByProcessIdAndMsgCode(processId,msgCode);
		return msgs;
	}
	public void updateMessages(Messages message) {
		messagesRepository.save(message);
	}
	
	public void deleteMessageById(int msgId) {
		messagesRepository.deleteById(msgId);
	}
	
	public boolean checkMsgIdExistForCode(int msgId) {
		// TODO Auto-generated method stub
		return this.messagesRepository.existsByMsgId(msgId);
	}
	
	public void insertDataFromMasterToComp() {
		this.messagesRepository.insertData();
	}
}
