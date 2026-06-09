package com.fms.app.appParams.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fms.app.appParams.models.AppParams;
import com.fms.app.appParams.repository.AppParamsRepository;
import com.fms.app.security.AuthorizeUserInfo;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericSpecification;
import com.fms.paginator.models.PagedResponse;

@Service
@CacheConfig(cacheNames = "App_Param_Ehcache_Cache_Config")
public class AppParamsService {
	
	@Autowired
	AppParamsRepository appParamsRepository;
	
	@Autowired
	AuthorizeUserInfo userInfo;
	
	public List<AppParams> getAllAppParams(){
		return (List<AppParams>)appParamsRepository.findAll();
	}
	
	public PagedResponse<AppParams> getAllAppParamsPaginated(FilterDTOCopy filterDto){
		GenericSpecification<AppParams> clientSpecification = new GenericSpecification<>();
        Specification<AppParams> spec = clientSpecification.buildSpecificationMultiple(filterDto.getFilterCriteria());
        final String sortOrder = filterDto.getPaginationDTO().getSortOrder();
        final String sortBy[] = filterDto.getPaginationDTO().getSortBy();
        final int pageSize = filterDto.getPaginationDTO().getPageSize();
        final int pageNo = filterDto.getPaginationDTO().getPageNo();
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<AppParams> appParamPage = appParamsRepository.findAll(spec,pageable);
        return PagedResponse.fromPage(appParamPage);
	}

	@Cacheable(value = "Ehcache_Cache_Config",key = "'AppParamsCache_AppParamsById'+#appParamsId")
	public AppParams  getAppParamsById(int appParamsId) {
		AppParams AppParam = appParamsRepository.findById(appParamsId).get();
		return AppParam;
	}
	
	public AppParams getAppParamsByParamId(String paramId) {
		AppParams AppParam = appParamsRepository.findByParamId(paramId);
		return AppParam;
	}
	
	public void updateAppParams(AppParams AppParam) {
		 appParamsRepository.save(AppParam);
	}
	
	public void deleteAppParamById(int appParamsId) {
		appParamsRepository.deleteById(appParamsId);
	}
	
	public boolean checkAppParamExistForCode(int appParamsId) {
		// TODO Auto-generated method stub
		return this.appParamsRepository.existsByAppParamsId(appParamsId);
	}
}
