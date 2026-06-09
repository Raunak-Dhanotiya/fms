package com.fms.app.helpdesk.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.app.helpdesk.models.ProblemType;
import com.fms.app.helpdesk.models.dto.ProblemTypeDto;
import com.fms.app.helpdesk.repository.ProblemTypeRepository;
import com.fms.app.security.AuthorizeUserInfo;
import com.fms.app.utils.CommonConstant;

@Service
public class ProblemTypeServices {
	@Autowired
	ProblemTypeRepository problemTypeRepository;

	@Autowired
	AuthorizeUserInfo userInfo;

	public List<ProblemType> getAll() {
		return this.problemTypeRepository.findAll();
	}

	public void saveOrUpdate(ProblemType problemType) {
		this.problemTypeRepository.save(problemType);
	}

	public List<ProblemTypeDto> getProblemTypeHierarchy() {
		List<ProblemType> roots = problemTypeRepository.findByParentIdIsNull();
		List<ProblemTypeDto> dtos = new ArrayList<>();
		for (ProblemType root : roots) {
			ProblemTypeDto dto = convertToDto(root);
			dtos.add(dto);
		}
		return dtos;
	}

	private ProblemTypeDto convertToDto(ProblemType problemType) {
		ProblemTypeDto dto = new ProblemTypeDto(0, null, null, problemType);
		dto.setKey(problemType.getProblemTypeId());
		dto.setLabel(problemType.getProbType());
		dto.setData(problemType);
		List<ProblemType> children = problemTypeRepository.findByParentId(problemType.getProblemTypeId());
		if (!children.isEmpty()) {
			List<ProblemTypeDto> childDtos = new ArrayList<>();
			for (ProblemType child : children) {
				childDtos.add(convertToDto(child));
			}
			dto.setChildren(childDtos);
		}
		return dto;
	}

	public ProblemType getByProblemType(int problemType) {
		if (problemType >0) {
			ProblemType problemTypeData = problemTypeRepository.findByProblemTypeId(problemType);
			return problemTypeData;
		} else {
			return new ProblemType();
		}
	}

	public String getProblemTypeString(Integer problemType) {
		ProblemType problemTypeData = getByProblemType(problemType);
		List<ProblemType> data = new ArrayList<ProblemType>();
		if (problemTypeData != null) {
			data.add(problemTypeData);
			if (problemTypeData.getParentId() != null) {
				data.addAll(getParentRecordsById(problemTypeData.getParentId()));
			}
		}
		Collections.reverse(data);
		return getStringByParentData(data);
	}

	public List<ProblemType> getParentRecordsById(Integer parentId) {
		List<ProblemType> parentList = new ArrayList<ProblemType>();
		ProblemType problemTypeData = problemTypeRepository.findById(parentId).orElse(null);
		if (problemTypeData != null) {
			parentList.add(problemTypeData);
			if (problemTypeData.getParentId() != null) {
				parentList.addAll(getParentRecordsById(problemTypeData.getParentId()));
			}
		}
		return parentList;
	}

	public String getStringByParentData(List<ProblemType> data) {
		String problemTypeString = "";
		if(data != null) {
			for (ProblemType each : data) {
				problemTypeString = problemTypeString + each.getProbType() + " | ";
			}
			problemTypeString = problemTypeString.substring(0, problemTypeString.length() - 3);
			return problemTypeString;
		}else {
			return problemTypeString;
		}
	}
	
	public ProblemType getPreventiveManitenanceProbType() {
		String probType = CommonConstant.PREVENTIVE_MAINTENANCE;
		return this.problemTypeRepository.findByProbType(probType);
	}

}
