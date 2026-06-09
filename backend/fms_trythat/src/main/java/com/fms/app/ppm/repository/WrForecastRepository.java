package com.fms.app.ppm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fms.app.ppm.models.WrForecast;

public interface WrForecastRepository extends JpaRepository<WrForecast,Integer> {

}
