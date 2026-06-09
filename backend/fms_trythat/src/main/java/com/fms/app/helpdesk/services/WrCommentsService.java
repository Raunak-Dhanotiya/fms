package com.fms.app.helpdesk.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.app.helpdesk.models.WrComments;
import com.fms.app.helpdesk.repository.IWrCommentsRepository;
import com.fms.app.security.AuthorizeUserInfo;

@Service
public class WrCommentsService {

	@Autowired
	IWrCommentsRepository wrCommentsRepository;

	@Autowired
	AuthorizeUserInfo userInfo;

	public WrComments saveOrUpdate(WrComments dataRecord) {
		return this.wrCommentsRepository.save(dataRecord);
	}

	public List<WrComments> getWrCommentsByWrId(int wrId) {
		return this.wrCommentsRepository.findByWrId(wrId);
	}

	public WrComments getById(int commentId) {
	   return this.wrCommentsRepository.findById(commentId).orElse(null);
	}

}
