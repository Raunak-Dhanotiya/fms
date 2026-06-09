package com.fms.app.utils;

import java.sql.SQLException;

public class HandleExceptions {
	public static String handleSQLException(SQLException e) {
		String errorMessage = e.getMessage();

		if (errorMessage.contains("Cannot insert the value NULL")) {
			return handleNullViolation(e);
		} else if (errorMessage.contains("statement conflicted with the FOREIGN KEY constraint")) {
			return handleForeignKeyViolation(e);
		} else if (errorMessage.contains("Violation of UNIQUE KEY constraint")) {
			return handleUniqueKeyViolation(e);
		} else if (errorMessage.contains("String or binary data would be truncated")) {
			return handleDataTruncated(e);
		} else if(errorMessage.contains("convert")) {
			return errorMessage;
		}else {
			return "Application error occured";
		}
	}

	private static String handleNullViolation(SQLException e) {
		int startIndex = e.getMessage().indexOf("'");
		int endIndex = e.getMessage().indexOf("'", startIndex + 1);
		if (startIndex >= 0) {
			if (endIndex >= 0) {
				String columnName = e.getMessage().substring(startIndex+1, endIndex);
				return String.format(
						"Cannot insert the value NULL into the column '%s'. The column does not allow nulls.",
						columnName);
			}
		}
		return "Application error occured";
	}

	private static String handleForeignKeyViolation(SQLException e) {
		int startIndex = e.getMessage().indexOf("statement conflicted with the FOREIGN KEY constraint");
		if (startIndex >= 0) {
			startIndex = e.getMessage().indexOf("column '", startIndex);
			if (startIndex >= 0) {
				startIndex += 8; // Move to the beginning of the column name
				int endIndex = e.getMessage().indexOf("'", startIndex);
				if (endIndex >= 0) {
					String columnName = e.getMessage().substring(startIndex, endIndex);
					return String.format(
							"Please check the referenced values in column '%s'.",
							columnName);
				}
			}
		}
		return "Application error occured";
	}

	private static String handleUniqueKeyViolation(SQLException e) {
		int startIndex = e.getMessage().indexOf("Violation of UNIQUE KEY constraint");
		if (startIndex >= 0) {
			startIndex = e.getMessage().indexOf("(", startIndex);
			int endIndex = e.getMessage().indexOf(")", startIndex);

			if (startIndex >= 0 && endIndex >= 0) {
				String violationValue = e.getMessage().substring(startIndex + 1, endIndex).trim();
				 // Split the input string by commas
		        String[] parts = violationValue.split(",");

		        // Iterate through the parts and find the first word
		        for (String part : parts) {
		            if (part.matches("[a-zA-Z]+")) {
		            	violationValue =  part;
		                break; // Stop searching once the first word is found
		            }
		        }

				return String.format("Cannot insert duplicate key.The duplicate key value is (%s).", violationValue);
			}
		}

		return "Application error occured";
	}

	private static String handleDataTruncated(SQLException e) {
		int startIndex = e.getMessage().indexOf("Truncated value: ");
		if (startIndex >= 0) {
			String truncatedValue = e.getMessage().substring(startIndex).trim();
			return String.format("The information you're trying to save is reached maximum length and '%s'.", truncatedValue);
		}
		return "Application error occured";
	}

}
