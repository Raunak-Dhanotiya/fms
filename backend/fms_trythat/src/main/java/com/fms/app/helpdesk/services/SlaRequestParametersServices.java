package com.fms.app.helpdesk.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fms.app.EquipmentService.EquipmentService;
import com.fms.app.helpdesk.models.ProblemType;
import com.fms.app.helpdesk.models.SlaRequestParameters;
import com.fms.app.helpdesk.models.dto.FilterApplicableSlaDto;
import com.fms.app.helpdesk.models.dto.SlaRequestParameterDto;
import com.fms.app.helpdesk.repository.ISlaRequestParametersRepository;
import com.fms.app.security.AuthorizeUserInfo;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericSpecification;
import com.fms.paginator.models.PagedResponse;

@Service
public class SlaRequestParametersServices {

	@Autowired
	ISlaRequestParametersRepository repository;

	@Autowired
	AuthorizeUserInfo userInfo;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	ModelMapper mapper;

	@Autowired
	ProblemTypeServices problemTypeServices;

	@Autowired
	EquipmentService equipmentService;

	public SlaRequestParameterDto getSlaRequestParametersId(int slaRequestParametersId) {

		SlaRequestParameters modelData = repository.findBySlaRequestParametersId(slaRequestParametersId);
		SlaRequestParameterDto data = new SlaRequestParameterDto();

		String problemTypeString = "";
		Integer problemType = modelData.getProbTypeId();
		if (problemType != null && problemType > 0) {
			problemTypeString = this.problemTypeServices.getProblemTypeString(problemType);
			data = this.mapper.map(modelData, SlaRequestParameterDto.class);
			data.setProblemTypeString(problemTypeString);
		} else {
			data = this.mapper.map(modelData, SlaRequestParameterDto.class);
			data.setProblemTypeString(" ");
		}

		return data;

	}

	public List<SlaRequestParameterDto> getAllSlaRequestParameters() {
		List<SlaRequestParameters> modelData = repository.findAll();

		// Get Problem Type String
		List<SlaRequestParameterDto> slaDto = new ArrayList<SlaRequestParameterDto>();
		String problemTypeString = "";
		for (SlaRequestParameters eachRecord : modelData) {
			Integer problemType = eachRecord.getProbTypeId();
			SlaRequestParameterDto data = new SlaRequestParameterDto();
			if ( problemType != null && problemType > 0) {
				problemTypeString = this.problemTypeServices.getProblemTypeString(problemType);
				data = this.mapper.map(eachRecord, SlaRequestParameterDto.class);
				data.setProblemTypeString(problemTypeString);
			} else {
				data = this.mapper.map(eachRecord, SlaRequestParameterDto.class);
				data.setProblemTypeString(" ");
			}

			slaDto.add(data);
		}
		return slaDto;
	}
	
	public PagedResponse<SlaRequestParameters> getAllSlaRequestParametersPaginated(FilterDTOCopy filterDto) {
		GenericSpecification<SlaRequestParameters> clientSpecification = new GenericSpecification<>();
        Specification<SlaRequestParameters> spec = clientSpecification.buildSpecificationMultiple(filterDto.getFilterCriteria());
        final String sortOrder = filterDto.getPaginationDTO().getSortOrder();
        final String sortBy[] = filterDto.getPaginationDTO().getSortBy();
        final int pageSize = filterDto.getPaginationDTO().getPageSize();
        final int pageNo = filterDto.getPaginationDTO().getPageNo();
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<SlaRequestParameters> slaRequestParametersPage = repository.findAll(spec,pageable);
        return PagedResponse.fromPage(slaRequestParametersPage);
	}

	public SlaRequestParameterDto saveOrUpdate(SlaRequestParameterDto slaRequestParameters) {
		SlaRequestParameters dataRecord = this.mapper.map(slaRequestParameters, SlaRequestParameters.class);
		dataRecord.setProbType(null);
		SlaRequestParameters modelData = repository.save(dataRecord);
		SlaRequestParameterDto dtoData = this.mapper.map(modelData, SlaRequestParameterDto.class);
		dtoData.setProblemTypeString(slaRequestParameters.getProblemTypeString());
		return dtoData;
	}

	public boolean checkIsSlaRequestExist(SlaRequestParameterDto slaRequestParameters) {

		String eqId = createIntRestriction("eq_id", slaRequestParameters.getEqId());
		String eqStd = createIntRestriction("eq_std_id", slaRequestParameters.getEqStdId());
		String siteId = createIntRestriction("site_id", slaRequestParameters.getSiteId());
		String blId = createIntRestriction("bl_id", slaRequestParameters.getBlId());
		String flId = createIntRestriction("fl_id", slaRequestParameters.getFlId());
		String rmId = createIntRestriction("rm_id", slaRequestParameters.getRmId());
		String probTypeId = createIntRestriction("problem_type_id", slaRequestParameters.getProbTypeId());
		int slaRequestParametersId = slaRequestParameters.getSlaRequestParametersId();

		String query = "SELECT COUNT(*) FROM sla_request_parameters WHERE " + "sla_request_parameters_id != "
				+ slaRequestParametersId + " AND " + probTypeId + " AND " + eqId + " AND " + eqStd + " AND " + siteId
				+ " AND " + blId + " AND " + flId + " AND " + rmId;
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		Object result = nativeQuery.getSingleResult();
		Long count = ((Number) result).longValue();
		boolean isExist = count > 0 ? true : false;
		return isExist;
	}

	public String createRestriction(String field, String value) {
		String restriction = value != null ? field + " = '" + value + "'" : field + " is null";
		return restriction;
	}
	
	public String createIntRestriction(String field, Integer value) {
		String restriction = value != null && value > 0  ? field + " = " + value + "" : field + " is null";
		return restriction;
	}

	public List<SlaRequestParameters> getApplicableSLA(FilterApplicableSlaDto data) {
		if (data.getProbTypeId() == null || data.getProbTypeId() <=0) {
			return getDefaultSLA();
		}

		List<String> slaParamSeq = new ArrayList<String>();
		Integer site_id = data.getSiteId();
		Integer bl_id = data.getBlId();

		Integer assetId = data.getEqId();
		Integer assetStd = assetId == null || assetId <= 0 ? assetId : equipmentService.getEquipmentByEqId(assetId).getEqStdId();

		int probType = data.getProbTypeId();

		Map<String, Integer> fieldRestriction = new HashMap<String, Integer>();
		fieldRestriction.put("site_id", site_id);
		fieldRestriction.put("bl_id", bl_id);
		fieldRestriction.put("asset_std", assetStd);

		int problemTypeCount = 0;
		String key = "problem_type_id";
		boolean flag = true;
		while (flag) {

			ProblemType problemType = problemTypeServices.getByProblemType(probType);
			String field = key + problemTypeCount++;
			int probTypeId = problemType.getProblemTypeId();

			fieldRestriction.put(field, probTypeId);
			slaParamSeq.add(field);

			int parentProblemTypeId = problemType.getParentId() != null ? problemType.getParentId() : 0;
			if (parentProblemTypeId == 0) {
				flag = false;
			}
			probType = parentProblemTypeId;
		}

		for (String problemType : slaParamSeq) {

			String restriction = "problem_type_id = " + fieldRestriction.get(problemType);
			final List<SlaRequestParameters> SLAS = getSLAList(restriction);

			if (!SLAS.isEmpty()) {
				List<SlaRequestParameters> slaList = getSlaByRequestParameters(fieldRestriction, SLAS);
				if (slaList.size() == 1) {
					return slaList;
				}
			}
		}

		return getDefaultSLA();
	}

	private List<SlaRequestParameters> getSlaByRequestParameters(Map<String, Integer> fieldRestriction,
			final List<SlaRequestParameters> slaList) {

		List<SlaRequestParameters> filterSLA = slaList;

		try {

			// asset standard + site + building
			filterSLA = getSlaByAssetStandard(fieldRestriction, filterSLA);
			filterSLA = getSlaBySite(fieldRestriction, filterSLA);
			filterSLA = getSlaByBuilding(fieldRestriction, filterSLA);

			// site + building
			if (filterSLA.isEmpty()) {
				filterSLA = slaList;
				filterSLA = getSlaBySite(fieldRestriction, filterSLA);
				filterSLA = getSlaByBuilding(fieldRestriction, filterSLA);
				filterSLA = filterSLA.stream().filter(sla -> sla.getEqStdId() == null).collect(Collectors.toList());
			}

			// asset standard + site
			if (filterSLA.isEmpty()) {
				filterSLA = slaList;
				filterSLA = getSlaByAssetStandard(fieldRestriction, filterSLA);
				filterSLA = getSlaBySite(fieldRestriction, filterSLA);
				filterSLA = filterSLA.stream().filter(sla -> sla.getBlId() == null).collect(Collectors.toList());
			}

			// site
			if (filterSLA.isEmpty()) {
				filterSLA = slaList;
				filterSLA = getSlaBySite(fieldRestriction, filterSLA);
				filterSLA = filterSLA.stream().filter(sla -> sla.getEqStdId() == null)
						.filter(sla -> sla.getBlId() == null).collect(Collectors.toList());
			}

			// asset standard
			if (filterSLA.isEmpty()) {
				filterSLA = slaList;
				filterSLA = getSlaByAssetStandard(fieldRestriction, filterSLA);
				filterSLA = filterSLA.stream().filter(sla -> sla.getSiteId() == null)
						.filter(sla -> sla.getBlId() ==null).collect(Collectors.toList());
			}

			// not parameters (only problem type)
			if (filterSLA.isEmpty()) {
				filterSLA = slaList;
				filterSLA = filterSLA.stream().filter(sla -> sla.getEqStdId() == null)
						.filter(sla -> sla.getSiteId() == null).filter(sla -> sla.getBlId() == null)
						.collect(Collectors.toList());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return filterSLA;
	}

	private List<SlaRequestParameters> getSlaByAssetStandard(Map<String, Integer> fieldRestriction,
			final List<SlaRequestParameters> slaList) {

		List<SlaRequestParameters> filterSLA = slaList;
		Integer value = fieldRestriction.get("asset_std");

		if ( value != null && value > 0) {
			filterSLA = filterSLA.stream()
					.filter(sla -> sla.getEqStdId()!=null && fieldRestriction.get("asset_std").intValue() == sla.getEqStdId())
					.collect(Collectors.toList());

		} else {
			filterSLA = filterSLA.stream().filter(sla -> fieldRestriction.get("asset_std") == (sla.getEqStdId()))
					.collect(Collectors.toList());
		}
		return filterSLA;
	}

	private List<SlaRequestParameters> getSlaBySite(Map<String, Integer> fieldRestriction,
			final List<SlaRequestParameters> slaList) {

		List<SlaRequestParameters> filterSLA = slaList;
		Integer value = fieldRestriction.get("site_id");

		if (value != null && value > 0) {
			filterSLA = filterSLA.stream()
					.filter(sla -> fieldRestriction.get("site_id") == sla.getSiteId())
					.collect(Collectors.toList());

		} else {
			filterSLA = filterSLA.stream().filter(sla -> fieldRestriction.get("site_id") == (sla.getSiteId()))
					.collect(Collectors.toList());
		}
		return filterSLA;
	}

	private List<SlaRequestParameters> getSlaByBuilding(Map<String, Integer> fieldRestriction,
			final List<SlaRequestParameters> slaList) {

		List<SlaRequestParameters> filterSLA = slaList;
		Integer value = fieldRestriction.get("bl_id");

		if (value != null && value > 0) {
			filterSLA = filterSLA.stream().filter(sla -> fieldRestriction.get("bl_id") == sla.getBlId())
					.collect(Collectors.toList());

		} else {
			filterSLA = filterSLA.stream().filter(sla -> fieldRestriction.get("bl_id") == (sla.getBlId()))
					.collect(Collectors.toList());
		}
		return filterSLA;
	}

	private List<SlaRequestParameters> getSLAList(String restriction) {

		final String query = "SELECT * FROM sla_request_parameters WHERE " + restriction;
		Query nativeQuery = this.entityManager.createNativeQuery(query, SlaRequestParameters.class);

		@SuppressWarnings("unchecked")
		List<SlaRequestParameters> result = nativeQuery.getResultList();

		return result;

	}

	private List<SlaRequestParameters> getDefaultSLA() {
		return getSLAList("problem_type_id is null");
	}

}
