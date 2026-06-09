package com.fms.app.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
@Service
public class TimezoneConverter {

	public static ZonedDateTime getDateTimewithTimeZone(Date date, Time time, String zoneId) {
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE);

		calendar.setTime(time);
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		int seconds = calendar.get(Calendar.SECOND);
		int milliseconds = calendar.get(Calendar.MILLISECOND);

		ZonedDateTime datewithZone = ZonedDateTime.of(year, month, day, hours, minutes, seconds, milliseconds,
				ZoneId.of(zoneId));

		return datewithZone;
	}

	public static ZonedDateTime convertDateTimeToZone(ZonedDateTime date, String zoneId) {

		ZonedDateTime dateWithZone = date.withZoneSameInstant(ZoneId.of(zoneId));
		return dateWithZone;
	}

	public static ZonedDateTime getDateTimeWithDefaultUTCZone(Date date, Time time) {

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE);

		calendar.setTime(time);
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		int seconds = calendar.get(Calendar.SECOND);
		int milliseconds = calendar.get(Calendar.MILLISECOND);

		ZonedDateTime datewithZone = ZonedDateTime.of(year, month, day, hours, minutes, seconds, milliseconds,
				ZoneId.of("UTC"));

		return datewithZone;
	}
	
	public static List<ZoneIdsDTO> getAllTimezones() {
	  
	        // Get all available time zones
	        Set<String> zoneIds = ZoneId.getAvailableZoneIds();
	        
	        List<ZoneIdsDTO> zoneIdDTOList = new ArrayList<ZoneIdsDTO>();

	        // Print all time zones with description
	        for (String zoneId : zoneIds) {
	        	ZoneIdsDTO zoneIdsDTO = new ZoneIdsDTO();
	            ZoneId zone = ZoneId.of(zoneId);
	            ZonedDateTime now = ZonedDateTime.now(zone);
	            String description = now.format(DateTimeFormatter.ofPattern("z '('zzzz')'"));
	            zoneIdsDTO.setTimeZoneId(zoneId);
	            zoneIdsDTO.setDescription(description);
	            zoneIdDTOList.add(zoneIdsDTO);
	        }
	        return zoneIdDTOList;
	    
	}
}
