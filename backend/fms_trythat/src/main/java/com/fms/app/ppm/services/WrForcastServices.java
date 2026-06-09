package com.fms.app.ppm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.app.ppm.models.WrForecast;
import com.fms.app.ppm.repository.WrForecastRepository;

@Service
public class WrForcastServices {
	
	@Autowired
	WrForecastRepository wrForecastRepository;
	
	public WrForecast saveWrForecast(WrForecast data) {
		return this.wrForecastRepository.save(data);
	}
	
	

}
