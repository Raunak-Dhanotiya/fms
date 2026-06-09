package com.fms.app.ppm.models.dto;

import java.util.List;

import com.fms.app.helpdesk.models.dto.WrDto;

public class ForecastDataObjectsDto {
	public static class Trade {
        public String id;
        public String name;
        public double requiredHours;
        public double hoursInUse;
        public double standardHours;
    }

    public static class Part {
        public String id;
        public String name;
        public double requiredQuantity;
        public double inUseQuantity;
        public double standardQuantity;
    }

    public static class Tool {
        public String id;
        public String name;
        public double requiredHours;
        public double hoursInUse;
        public double standardHours;
    }

    public static class Forecast {
        public List<Trade> planTradeList;
        public List<Tool> planToolList;
        public List<Part> planPartsList;
    }

    public static class Actual {
        public List<Trade> trade;
        public List<Part> part;
        public List<Tool> tool;
    }

    public static class FinalObject {
        public List<Trade> Trades;
        public List<Part> Parts;
        public List<Tool> Tools;
        public List<WrDto> requestsList;
    }

}
