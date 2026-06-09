package com.fms.app.spaceManagement.services;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fms.app.spaceManagement.models.City;
import com.fms.app.spaceManagement.models.dto.CityFilterInputDTO;
import com.fms.app.spaceManagement.repository.CityRepository;
import com.fms.paginator.models.GenericSpecification;
import com.fms.paginator.models.PagedResponse;

@Service
public class CityService {

	@Autowired  
	CityRepository cityRepository;
	
	public void saveOrUpdate(City city)   
	{  
		City c=new City();
		c=cityRepository.save(city); 
		System.out.println("object: "+c);
	}
	
	public List<City> getAllCity()   
	{  
		return this.cityRepository.findAll();  
	}
	
	//delete city
	public void delete(City city)   
	{  
		cityRepository.delete(city);; 
	}
	
	//update
	public void update(City city)   
	{  
	   cityRepository.save(city);
	}
	
	//getcity by id
	public City getCityById(Integer cityId)   
	{  
		return cityRepository.findById(cityId).orElse(null);
	}

	public boolean checkCityCodeExists(String cityCode, Integer stateId, Integer regnId, Integer cntryId) {
		
		return this.cityRepository.existsByCityCodeAndStateIdAndRegnIdAndCtryId(cityCode,stateId,regnId,cntryId);
	}
	
	public PagedResponse<City> getFilteredCities(CityFilterInputDTO cityFilterDto) {
		GenericSpecification<City> clientSpecification = new GenericSpecification<>();
        Specification<City> spec = clientSpecification.buildSpecificationMultiple(cityFilterDto.getFilterDto().getFilterCriteria());
        
        if (cityFilterDto.getCityId() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("cityId"), cityFilterDto.getCityId()));
        }

        if (cityFilterDto.getRegnId() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("regnId"), cityFilterDto.getRegnId()));
        }

        if (cityFilterDto.getCntryId() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("ctryId"), cityFilterDto.getCntryId()));
        }

        if (cityFilterDto.getStateId() > 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("stateId"), cityFilterDto.getStateId()));
        }
        
		final String sortOrder = cityFilterDto.getFilterDto().getPaginationDTO().getSortOrder();
		final String sortBy[] = cityFilterDto.getFilterDto().getPaginationDTO().getSortBy();
		final int pageSize = cityFilterDto.getFilterDto().getPaginationDTO().getPageSize();
		final int pageNo = cityFilterDto.getFilterDto().getPaginationDTO().getPageNo();

		Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

		Page<City> cityPage = cityRepository.findAll(spec, pageable);
		return PagedResponse.fromPage(cityPage);

    }

}
