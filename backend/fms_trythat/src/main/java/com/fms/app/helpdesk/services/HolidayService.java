package com.fms.app.helpdesk.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fms.app.helpdesk.models.Holidays;
import com.fms.app.helpdesk.repository.IHolidaysRepository;
import com.fms.paginator.models.FilterDTO;
import com.fms.paginator.models.FilterDTOCopy;
import com.fms.paginator.models.GenericSpecification;
import com.fms.paginator.models.PagedResponse;

@Service
public class HolidayService {
	
	@Autowired  
	IHolidaysRepository repository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public HolidayService() {
		super();
		// TODO Auto-generated constructor stub
	}


	public HolidayService(IHolidaysRepository repository) {
		super();
		this.repository = repository;
	}


	public void saveOrUpdate(Holidays holidays)   
	{  
		Holidays p=new Holidays();
		p=repository.save(holidays); 
		System.out.println("object: "+p);
	}
		
	
	public List<Holidays> getAllHolidays()   
	{  
		return repository.findAll();  
	}
	
	public PagedResponse<Holidays> getAllHolidaysPaginated(FilterDTOCopy filterDto)   
	{  
		GenericSpecification<Holidays> clientSpecification = new GenericSpecification<>();
        Specification<Holidays> spec = clientSpecification.buildSpecificationMultiple(filterDto.getFilterCriteria());
        final String sortOrder = filterDto.getPaginationDTO().getSortOrder();
        final String sortBy[] = filterDto.getPaginationDTO().getSortBy();
        final int pageSize = filterDto.getPaginationDTO().getPageSize();
        final int pageNo = filterDto.getPaginationDTO().getPageNo();
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Holidays> holidaysPage = repository.findAll(spec,pageable);
        return PagedResponse.fromPage(holidaysPage);
	}
	
	public Holidays getAllHolidayById(int id)   
	{  
		return repository.findById(id).get();  
	}
	
	public Holidays getHolidayByDate(String date)   
	{  
		try {
			Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			return repository.findByHolidayDate(date1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Holidays();
		}
	}
	
	public void deleteHoliday(Integer objId)   
	{  
		repository.deleteById(objId);
		
	}
	
	public Boolean checkHoiday(Date date)   
	{  
		return repository.existsByHolidayDate(date);
		
	}
	
	public Boolean checkHolidayDate(Date date)   
	{  
		return repository.existsByHolidayDate(date);
		
	}
	
	public List<LocalDate> getHolidayDateList (LocalDate requestDate){
		String date = requestDate.toString();
		//Get Holiday Dates From Requested Date To Future;
		String query = "SELECT CONVERT(DATE, holiday_date, 120) FROM holidays WHERE holiday_date  > '" +  date + "'";
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		@SuppressWarnings("unchecked")
		List<Date> datesRecords = nativeQuery.getResultList();
		List<LocalDate> holidaysList = new ArrayList<LocalDate>();
		if(!datesRecords.isEmpty()) {
			for(Date str: datesRecords) {
				LocalDate localDate = LocalDate.parse(str.toString());
				holidaysList.add(localDate);
			}
			return holidaysList;
		}		
		return new ArrayList<LocalDate>();
	}

}
