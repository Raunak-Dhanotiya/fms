package com.fms.app.ppm.services;


import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fms.app.helpdesk.services.PartsService;
import com.fms.app.helpdesk.services.ToolsServices;
import com.fms.app.ppm.models.dto.ForecastDataObjectsDto;
import com.fms.app.ppm.models.dto.GenerateRequestsFilterDto;
import com.fms.app.ppm.models.PlanScheduleDate;


@Service
public class ForecastDetailsService {

	@Autowired
	PartsService partsService;

	@Autowired
	ToolsServices toolsServices;

	@PersistenceContext
	private EntityManager entityManager;

	public ForecastDataObjectsDto.FinalObject getForecastDetailsByDates(List<PlanScheduleDate> planScheduleDatesList,
			GenerateRequestsFilterDto generateRequestsFilterDto) {
		String wrStatusRestriction = " and wr.status not in (select enum_key from enum_list where table_name ='wr' "
				+ "and field_name = 'status' and enum_value in ('Rejected','Completed','Close','Cancelled')) ";
		
		String datesStr = "";
		
		int daysCount = this.getInBetweenDateCount(generateRequestsFilterDto.getFromDate(), generateRequestsFilterDto.getToDate());
		ForecastDataObjectsDto.FinalObject finalObject = new ForecastDataObjectsDto.FinalObject();
		if (planScheduleDatesList != null) {
			for (int i = 0; i < planScheduleDatesList.size(); i++) {
				if (i == planScheduleDatesList.size() - 1) {
					datesStr += "' " + planScheduleDatesList.get(i).getScheduleDate().toString() + "'";
				} else {
					datesStr += "' " + planScheduleDatesList.get(i).getScheduleDate().toString() + "' ,";
				}
			}
			
			List<ForecastDataObjectsDto.Trade> tradeDetailsList = this.getTradeForecastDetails(datesStr,
					daysCount,wrStatusRestriction);
			List<ForecastDataObjectsDto.Tool> toolDetailsList = this.getToolForecastDetails(datesStr,
					daysCount,wrStatusRestriction);
			List<ForecastDataObjectsDto.Part> partsDetailsList = this.getPartForecastDetails(datesStr,wrStatusRestriction);
			finalObject.Trades = tradeDetailsList;
			finalObject.Tools = toolDetailsList;
			finalObject.Parts = partsDetailsList;
		}
		
		return finalObject;

	}

	public List<ForecastDataObjectsDto.Trade> getTradeForecastDetails(String datesStr, int dateCount,String wrStatusRestriction ) {
		
		List<ForecastDataObjectsDto.Trade> planTradeList = new ArrayList<ForecastDataObjectsDto.Trade>();
		String query = "select plan_trade.trade_id, trades.trade_code as name, sum(plan_trade.hours_required) as hours_required, "
				// to calculate allocated hours for each trade and technicians belongs to each
				// trade

				+ "((select isnull(sum(hours_required),0) from request_trade inner join wr on wr.wr_id = request_trade.request_id "
				+ "where date_to_perform in (" + datesStr + ") and trade_id = plan_trade.trade_id "
				+ wrStatusRestriction + ") + "
				+ "(select isnull(sum(hours_required),0) from request_technician inner join wr on wr.wr_id = request_technician.request_id "
				+ "inner join craftsperson on craftsperson.cf_id = request_technician.technician_id where date_to_perform in ("
				+ datesStr + ") " + "and primary_trade = plan_trade.trade_id " + wrStatusRestriction
				+ ")) as allocated_hours, "
				// to calculate sum of standard available hours for technicians belongs to the
				// same trade for the given dates
				+ "(select isnull(sum(std_hours_avail),0) * (" + dateCount + ") "
				+ "from craftsperson where primary_trade = plan_trade.trade_id) as standard_hours "
				+ "from plan_sched_date "
				+ "inner join plan_sched on plan_sched.plan_sched_id = plan_sched_date.plan_sched_id "
				+ "inner join plan_loc_eq on plan_loc_eq.plan_loc_eq_id = plan_sched.plan_loc_eq_id "
				+ "inner join plans on plans.plan_id = plan_loc_eq.plan_id "
				+ "inner join plan_step on plan_step.plan_id = plans.plan_id "
				+ "inner join plan_trade on plan_trade.plan_step_id = plan_step.plan_step_id "
				+ "inner join trades ON trades.trade_id = plan_trade.trade_id " + " where sched_date in ("
				+ datesStr + ") and wr_id is null group by trades.trade_code, plan_trade.trade_id";
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		@SuppressWarnings("unchecked")
		List<Object[]> dataRecords = nativeQuery.getResultList();

		if (!dataRecords.isEmpty()) {
			dataRecords.forEach(x -> {
				planTradeList.add(createPlanTradeDetailsDto(Arrays.asList(x)));
			});
		}
		return planTradeList;
	}

	public List<ForecastDataObjectsDto.Tool> getToolForecastDetails(String datesStr, int dateCount,String wrStatusRestriction) {
		
		List<ForecastDataObjectsDto.Tool> planToolList = new ArrayList<ForecastDataObjectsDto.Tool>();
		String query = "select plan_tool.tools_id, tools.tool as name, sum(plan_tool.hours_required) as hours_required, "
				// to calculate allocated hours for each tool

				+ "(select isnull(sum(hours_required),0) from request_tools inner join wr on wr.wr_id = request_tools.request_id "
				+ "inner join tools on tools.tools_id = request_tools.tool_id where date_to_perform in (" + datesStr + ") "
				+ "and tools.tools_id = plan_tool.tools_id " + wrStatusRestriction + " ) as allocated_hours, "
				// to calculate sum of standard available hours for tool 

				+ "(select isnull(sum(std_hours_avail),0) * (" + dateCount + ") "
				+ "from tools where tools.tools_id = plan_tool.tools_id) as standard_hours " + "from plan_sched_date "
				+ "inner join plan_sched on plan_sched.plan_sched_id = plan_sched_date.plan_sched_id "
				+ "inner join plan_loc_eq on plan_loc_eq.plan_loc_eq_id = plan_sched.plan_loc_eq_id "
				+ "inner join plans on plans.plan_id = plan_loc_eq.plan_id "
				+ "inner join plan_step on plan_step.plan_id = plans.plan_id "
				+ "inner join plan_tool on plan_tool.plan_step_id = plan_step.plan_step_id "
				+ "inner join tools on plan_tool.tools_id = tools.tools_id  " + "where sched_date in ("
				+ datesStr + ") and wr_id is null group by plan_tool.tools_id,tools.tool";
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		@SuppressWarnings("unchecked")
		List<Object[]> dataRecords = nativeQuery.getResultList();

		if (!dataRecords.isEmpty()) {
			dataRecords.forEach(x -> {
				planToolList.add(createPlanToolDetailsDto(Arrays.asList(x)));
			});
		}
		return planToolList;
	}
	
	public List<ForecastDataObjectsDto.Part> getPartForecastDetails(String datesStr,String wrStatusRestriction) {
		
		List<ForecastDataObjectsDto.Part> planPartList = new ArrayList<ForecastDataObjectsDto.Part>();
		String query = "select plan_part.part_id, parts.part_code as name,  sum(plan_part.qunatity_required) as qunatity_required, "
				
				// to calculate allocated part quantity for each part
				+ "(select isnull(sum(request_parts.req_quantity),0) from request_parts  inner join wr on wr.wr_id = request_parts.request_id  "
				+ "inner join parts on parts.part_id = request_parts.part_id where date_to_perform in (" + datesStr + ") "
				+ " and parts.part_id = plan_part.part_id " + wrStatusRestriction + " ) as allocated_quantity, "
				
				// to calculate sum of standard quantity  for part belongs 
				+ "(select qty_on_hand from parts "
				+ "where parts.part_id = plan_part.part_id) as standard_qunatity " + "from plan_sched_date "
				+ "inner join plan_sched on plan_sched.plan_sched_id = plan_sched_date.plan_sched_id "
				+ "inner join plan_loc_eq on plan_loc_eq.plan_loc_eq_id = plan_sched.plan_loc_eq_id "
				+ "inner join plans on plans.plan_id = plan_loc_eq.plan_id "
				+ "inner join plan_step on plan_step.plan_id = plans.plan_id "
				+ "inner join plan_part on plan_part.plan_step_id = plan_step.plan_step_id "
				+ "inner join parts on parts.part_id = plan_part.part_id " + "where sched_date in ("
				+ datesStr + ") and wr_id is null group by plan_part.part_id,parts.part_code";
		Query nativeQuery = this.entityManager.createNativeQuery(query);
		@SuppressWarnings("unchecked")
		List<Object[]> dataRecords = nativeQuery.getResultList();

		if (!dataRecords.isEmpty()) {
			dataRecords.forEach(x -> {
				planPartList.add(createPlanPartDetailsDto(Arrays.asList(x)));
			});
		}
		return planPartList;
	}


	public ForecastDataObjectsDto.Trade createPlanTradeDetailsDto(List<Object> data) {
		ForecastDataObjectsDto.Trade planTrade = new ForecastDataObjectsDto.Trade();
		planTrade.id = data.get(0).toString();
		planTrade.name = data.get(1).toString();
		planTrade.requiredHours = Double.parseDouble(data.get(2).toString());
		planTrade.hoursInUse = Double.parseDouble(data.get(3).toString());
		planTrade.standardHours = Double.parseDouble(data.get(4).toString());
		return planTrade;
	}

	public ForecastDataObjectsDto.Tool createPlanToolDetailsDto(List<Object> data) {
		ForecastDataObjectsDto.Tool planTool = new ForecastDataObjectsDto.Tool();
		planTool.id = data.get(0).toString();
		planTool.name = data.get(1).toString();
		planTool.requiredHours = Double.parseDouble(data.get(2).toString());
		planTool.hoursInUse = Double.parseDouble(data.get(3).toString());
		planTool.standardHours = Double.parseDouble(data.get(4).toString());
		return planTool;
	}
	
	public ForecastDataObjectsDto.Part createPlanPartDetailsDto(List<Object> data) {
		ForecastDataObjectsDto.Part planPart = new ForecastDataObjectsDto.Part();
		planPart.id = data.get(0).toString();
		planPart.name = data.get(1).toString();
		planPart.requiredQuantity = Double.parseDouble(data.get(2).toString());
		planPart.inUseQuantity = Double.parseDouble(data.get(3).toString());
		planPart.standardQuantity = Double.parseDouble(data.get(4).toString());
		return planPart;
	}
	
	public int getInBetweenDateCount(String fromDateStr,String toDateStr) {
	    // Define a formatter for your date string pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Parse the date string into a LocalDate object
        LocalDate fromDate = LocalDate.parse(fromDateStr, formatter);
        LocalDate toDate = LocalDate.parse(toDateStr, formatter);

	    Period period = Period.between(fromDate, toDate);
	    int days = period.getDays();
	    return days + 1;
	}

}
