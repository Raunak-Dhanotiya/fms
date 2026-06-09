package com.fms.app.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.domain.Specification;

import com.fms.paginator.models.FilterCriteria;

public class CommonUtil {

	// compress the image bytes before storing it in the database
	
	private static final Logger logger = LogManager.getLogger(CommonUtil.class);
	public static byte[] compressBytes(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		}catch (Exception e) {
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in CommonUtil.compressBytes: "+stacktrace,e);
		}
		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

		return outputStream.toByteArray();
	}

	// uncompress the image bytes before returning it to the angular application
	public static byte[] decompressBytes(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		}catch (Exception e) {
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in CommonUtil.decompressBytes: "+stacktrace,e);
		}
		return outputStream.toByteArray();
	}

	public static boolean valueExits(String data) {
		return (data != null && data.length() > 0) ? true : false;
	}
	
	public static Date getSqlDate(String strDate) {
		// convert string date to sql date
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(strDate, formatter);
		java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
		return sqlDate;
	}
	
	public static String getExceptionCause(Exception ec) {

		Throwable rootCause = getRootCause(ec);
	    if (rootCause instanceof SQLException) {
	        // Handle SQLException
	        return HandleExceptions.handleSQLException((SQLException) rootCause);
	        
	    } else {
	        return "Application error occured";
	    }
	}
	
	public static String getStakeTrace(Exception e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String stacktrace = sw.toString();
		return stacktrace; 
	}
	
	private static Throwable getRootCause(Throwable throwable) {
	    Throwable rootCause = throwable;

	    while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
	        rootCause = rootCause.getCause();
	    }

	    return rootCause;
	}
	
	public static String getPaginationFilterQuery(FilterCriteria filterCriteria) {
		String result = "";
		if (filterCriteria.getValue() != null) {
            switch (filterCriteria.getMatchMode().toLowerCase()) {
                case "startswith":
                	result = " WHERE "+filterCriteria.getFieldName()+" LIKE '"+filterCriteria.getValue()+"%' ";
                    break;
                case "contains":
                	result = " WHERE "+filterCriteria.getFieldName()+" LIKE '%"+filterCriteria.getValue()+"%' ";
                    break;
                case "notcontains":
                	result = " WHERE "+filterCriteria.getFieldName()+" NOT LIKE '%"+filterCriteria.getValue()+"%' ";
                    break;
                case "endswith":
                	result = " WHERE "+filterCriteria.getFieldName()+" LIKE '%"+filterCriteria.getValue()+"' ";
                    break;
                case "equals":
                	result = " WHERE "+filterCriteria.getFieldName()+" ="+filterCriteria.getValue()+" ";
                    break;
                case "notequals":
                	result = " WHERE "+filterCriteria.getFieldName()+" !="+filterCriteria.getValue()+" ";
                    break;
                default:
                    break;
            }
        }
		return result;
	}
	
	public static String getPaginationFilterQueryMultiple(List<FilterCriteria> filterCriteria) {
		String result ="";
		if(filterCriteria.size()>0) {
			result += " WHERE ";
		}
		for (int i = 0; i < filterCriteria.size(); i++) {
		    result += getPaginationFilterQueryWithoutWhere(filterCriteria.get(i));
		    if (i < filterCriteria.size() - 1) {
		        result += " AND ";
		    }
		}
		return result;
	}
	
	public static String getPaginationFilterQueryMultipleWithoutWhere(List<FilterCriteria> filterCriteria) {
		String result ="";
		for (int i = 0; i < filterCriteria.size(); i++) {
		    result += " AND "+ getPaginationFilterQueryWithoutWhere(filterCriteria.get(i));
//		    if (i < filterCriteria.size() - 1) {
//		        result += " AND ";
//		    }
		}
		return result;
	}
	
	public static String getPaginationFilterQueryWithoutWhere(FilterCriteria filterCriteria) {
		String result = "";
		if (filterCriteria.getValue() != null) {
            switch (filterCriteria.getMatchMode().toLowerCase()) {
                case "startswith":
                	result = " "+filterCriteria.getFieldName()+" LIKE '"+filterCriteria.getValue()+"%' ";
                    break;
                case "contains":
                	result = " "+filterCriteria.getFieldName()+" LIKE '%"+filterCriteria.getValue()+"%' ";
                    break;
                case "notcontains":
                	result = " "+filterCriteria.getFieldName()+" NOT LIKE '%"+filterCriteria.getValue()+"%' ";
                    break;
                case "endswith":
                	result = " "+filterCriteria.getFieldName()+" LIKE '%"+filterCriteria.getValue()+"' ";
                    break;
                case "equals":
                	result = " "+filterCriteria.getFieldName()+" = '"+filterCriteria.getValue()+"' ";
                    break;
                case "notequals":
                	result = " "+filterCriteria.getFieldName()+" != '"+filterCriteria.getValue()+"' ";
                    break;
                default:
                    break;
            }
        }
		return result;
	}
	
	public static <T> Specification<T> orSpecification(List<Specification<T>> specifications) {
        if (specifications == null || specifications.isEmpty()) {
            return null;
        }
        Specification<T> result = specifications.get(0);
        for (int i = 1; i < specifications.size(); i++) {
            result = Specification.where(result).or(specifications.get(i));
        }
        return result;
    }
}
