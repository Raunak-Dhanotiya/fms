package com.fms.app.ppm.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.app.ppm.models.Plan;
import com.fms.app.ppm.models.PlanSchedule;
import com.fms.app.ppm.repository.PlanScheduleRepository;
import com.fms.app.reservation.models.dto.RecurrencePatternDTO;

@Service
public class PlanScheduleServices {
	
	@Autowired
	PlanScheduleRepository planScheduleRepo;
	
	public void saveOrUpdate(PlanSchedule planSchedule) {
		this.planScheduleRepo.save(planSchedule);
	}
	
	public List<PlanSchedule> getByPlanLocEqId(int planLocEqId) {
		return this.planScheduleRepo.findByPlanLocEqId(planLocEqId);
	}
	
	public void delete(int planScheduleId) {
		this.planScheduleRepo.deleteById(planScheduleId);
	}
	
	public PlanSchedule getById(int scheduleId) {
		return this.planScheduleRepo.findById(scheduleId).orElse(null);
	}
	
	public List<LocalDate> getScheduleDates(RecurrencePatternDTO recurrencePatternDTO) {
		final String recType = recurrencePatternDTO.getType();
		final LocalDate dateStart = recurrencePatternDTO.getDateStart();
		final LocalDate dateEnd = recurrencePatternDTO.getDateEnd();
		final int frequency = recurrencePatternDTO.getFrequency();
		final int[] weekDays = parseIntArray(
				recurrencePatternDTO.getWeekDays().length() > 0 ? recurrencePatternDTO.getWeekDays().split(",") : null);
		final int[] monthDays = parseIntArray(
				recurrencePatternDTO.getMonthDays().length() > 0 ? recurrencePatternDTO.getMonthDays().split(",")
						: null);
		final int[] weeks = parseIntArray(
				recurrencePatternDTO.getWeeks().length() > 0 ? recurrencePatternDTO.getWeeks().split(",") : null);

		List<LocalDate> scheduleDates = new ArrayList<LocalDate>();
		switch (recType) {
		case "daily":
			scheduleDates = getDailyScheduleDates(dateStart, dateEnd, frequency);
			break;

		case "weekly":
			scheduleDates = getWeeklyScheduleDates(dateStart, dateEnd, weekDays, frequency);
			break;

		case "annually":
			scheduleDates = getAnnualyScheduleDates(dateStart, dateEnd, frequency);
			break;

		case "monthly":
			scheduleDates = getMonthlyScheduleDates(dateStart, dateEnd, monthDays, weekDays, weeks, frequency);
			break;
		}

		List<LocalDate> sortedDates = scheduleDates.stream().sorted()
                .collect(Collectors.toList());
        return sortedDates;
	}

	private static List<LocalDate> getMonthlyScheduleDates(final LocalDate dateStart, final LocalDate dateEnd,
			final int monthDays[], final int weekDays[], final int weeks[], final int frequency) {
		final List<LocalDate> scheduleDates = new ArrayList<LocalDate>();
        final LocalDate tempDate = dateStart;
        LocalDate monthStartDate = tempDate.withDayOfMonth(1);
        LocalDate monthEndDate =
                tempDate.withDayOfMonth(tempDate.getMonth().length(tempDate.isLeapYear()));

        if (monthDays != null) {
            while (monthStartDate.compareTo(dateEnd) <= 0) {
                for (final int monthDay : monthDays) {
                    monthStartDate = monthStartDate.plusDays(monthDay - 1);
                    if (monthStartDate.compareTo(dateStart) >= 0
                            && monthStartDate.compareTo(dateEnd) <= 0
                            && monthEndDate.compareTo(monthStartDate) >= 0) {
                    	scheduleDates.add(monthStartDate);
                    } else {
                        monthStartDate = monthStartDate.plusDays(-(monthDay - 1));
                    }
                    monthStartDate = monthStartDate.withDayOfMonth(1);
                }
                monthStartDate = monthStartDate.plusMonths(frequency);
                monthEndDate = monthStartDate
                    .withDayOfMonth(monthStartDate.getMonth().length(monthStartDate.isLeapYear()));
            }
        } else {
            while (monthStartDate.compareTo(dateEnd) <= 0) {
                for (final int week : weeks) {
                    for (int i = 0; i < 7; i++) {
                        if (weekDays[i] == 1) {
                            final DayOfWeek weekday = DayOfWeek.of(i+1);
                            final LocalDate adjustedDate = monthStartDate
                                .with(TemporalAdjusters.dayOfWeekInMonth(week, weekday));
                            if (adjustedDate.compareTo(dateStart) >= 0 &&adjustedDate.compareTo(dateEnd) <= 0) {
                            	scheduleDates.add(adjustedDate);
                            }
                        }
                    }
                }
                monthStartDate = monthStartDate.plusMonths(frequency);
                monthEndDate = monthStartDate
                    .withDayOfMonth(monthStartDate.getMonth().length(monthStartDate.isLeapYear()));
            }
        }

        return scheduleDates;
	}

	private static List<LocalDate> getAnnualyScheduleDates(final LocalDate dateStart, final LocalDate dateEnd,
			final int frequency) {
        final List<LocalDate> scheduleDates = new ArrayList<LocalDate>();
        LocalDate tempDate = dateStart;
        while (tempDate.compareTo(dateEnd) <= 0) {
        	scheduleDates.add(tempDate);
            tempDate = tempDate.plusYears(frequency);
        }
        
        return scheduleDates;
    }

	private static List<LocalDate> getWeeklyScheduleDates(final LocalDate dateStart, final LocalDate dateEnd,
			final int[] weekDays, final int frequency) {
        final List<LocalDate> scheduleDates = new ArrayList<LocalDate>();
        WeekFields weekFields
        = WeekFields.of(DayOfWeek.MONDAY, 1);
        
        final TemporalField fields = weekFields.dayOfWeek();

        LocalDate weekDateStart = dateStart.with(fields, 1);

        while (weekDateStart.compareTo(dateEnd) <= 0) {
            for (int i = 0; i < 7; i++) {
                final int day = weekDays.length > i ? weekDays[i] : 0;
                if (day == 1 && weekDateStart.compareTo(dateStart) >= 0) {
                	scheduleDates.add(weekDateStart);
                }
                weekDateStart = weekDateStart.plusDays(1);
                if (weekDateStart.compareTo(dateEnd) > 0) {
                    break;
                }
               
            }
            weekDateStart = weekDateStart.plusWeeks(frequency - 1);
        }

        return scheduleDates;
    }

	private static List<LocalDate> getDailyScheduleDates(final LocalDate dateStart, final LocalDate dateEnd,
			final int frequency) {
        final List<LocalDate> scheduleDates = new ArrayList<LocalDate>();
        LocalDate tempDate = dateStart;
        while (tempDate.compareTo(dateEnd) <= 0) {
        	scheduleDates.add(tempDate);
            tempDate = tempDate.plusDays(frequency);
        }

        return scheduleDates;
    }

	//To get integer array from string values
	private static int[] parseIntArray(String[] arr) {
		if (arr != null) {
			int[] ints = new int[arr.length];
			for (int i = 0; i < ints.length; i++) {
				ints[i] = Integer.parseInt(arr[i]);
			}
			return ints;
		}
		return null;
	}
	
}
