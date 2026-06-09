package com.fms.app.spaceManagement.services;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fms.app.security.AuthorizeUserInfo;
import com.fms.app.spaceManagement.models.Bl;
import com.fms.app.spaceManagement.models.Site;
import com.fms.app.spaceManagement.models.dto.SiteFilterInputDTO;
import com.fms.app.spaceManagement.repository.SiteRepository;
import com.fms.paginator.models.FilterCriteria;
import com.fms.paginator.models.GenericSpecification;
import com.fms.paginator.models.PagedResponse;

@Service
public class SiteService {

	@Autowired  
	SiteRepository siteRepository;
	
	@Autowired
	AuthorizeUserInfo userInfo;

	//save site
	public void saveOrUpdate(Site site)   
	{  
//		Site s=new Site();
//		s=
		this.siteRepository.save(site); 
	}
	//get site
	public List<Site> getAllSite()   
	{
		return (List<Site>) this.siteRepository.findAll();
	}
	
	//get site
	public List<Site> getAllSiteByCodeOrName(String siteCode, String siteName)   
	{
		return this.siteRepository.findBySiteCodeContainingOrSiteNameContaining(siteCode,siteName);
	}
	
	public List<Site> getAllSiteByBlId(int blId)   
	{  

		return siteRepository.getAllSitesByBlIds(blId);  
	}
	
	//delete site
	public void deleteSite(Site site)   
	{  
		siteRepository.delete(site);
		
	}

	/*
	 * @param Site Id <p>Delete site</p>
	 */
	@Transactional
	public void deleteSiteById(int siteId) {
		
		this.siteRepository.deleteBySiteId(siteId);

	}
	
	//get site  by id
	public Site getSiteById(int siteId)   
	{  
		Optional<Site> s = siteRepository.findById(siteId);
		Site siteDetails = s.isPresent() ? s.get() : null;
		return siteDetails;
	}
	
	//update site
	public void updateSite(Site site)   
	{  
	   this.siteRepository.save(site);
	}
	
	public boolean checkSiteIdExistForCode(int siteId) {
		// TODO Auto-generated method stub
		return this.siteRepository.existsBySiteId(siteId);
	}
	
	public PagedResponse<Site> getAllSitesByFilter(SiteFilterInputDTO siteFilterDto) {
		GenericSpecification<Site> clientSpecification = new GenericSpecification<>();
        Specification<Site> spec = clientSpecification.buildSpecificationMultiple(siteFilterDto.getFilterDto().getFilterCriteria());
      
        if (siteFilterDto.getSiteName() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("siteName"), siteFilterDto.getSiteName()));
        }

        if (siteFilterDto.getSiteId() != null && siteFilterDto.getSiteId() != 0) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("siteId"), siteFilterDto.getSiteId()));
        }
       
        final String sortOrder = siteFilterDto.getFilterDto().getPaginationDTO().getSortOrder();
        final String sortBy[] = siteFilterDto.getFilterDto().getPaginationDTO().getSortBy();
        final int pageSize = siteFilterDto.getFilterDto().getPaginationDTO().getPageSize();
        final int pageNo = siteFilterDto.getFilterDto().getPaginationDTO().getPageNo();
        
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Site> sitesPage = siteRepository.findAll(spec,pageable);
         return PagedResponse.fromPage(sitesPage);
	}
	
	public List<Site> getAllSiteByScroll(FilterCriteria filter) {
		GenericSpecification<Site> clientSpecification = new GenericSpecification<>();
        Specification<Site> spec = clientSpecification.buildSpecification(filter);
		 Pageable pageable = PageRequest.of(filter.getOffset() / filter.getLimit(), filter.getLimit());
		 
	     return siteRepository.findAll(spec,pageable).getContent();
	}
		
}
