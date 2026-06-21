package org.tedros.it.tools.employeeactivity.ai.function;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonClassDescription("Criteria for analyzing an employee's daily productivity, idleness, active software usage, and screen time.")
public class EmployeeActivityCriteria {
	
	@JsonPropertyDescription("REQUIRED. The numerical internal Tedros user ID. DO NOT guess this ID. If you only have a name, call 'search_employees' first.")
	private Long tedrosUserId;
	
	@JsonPropertyDescription("REQUIRED. The start date to filter activities. MUST be strictly in YYYY-MM-DD format (e.g., '2023-10-25'). Do NOT include time.")
	private String activityBeginDate;
	
	@JsonPropertyDescription("OPTIONAL. The end date to filter activities. MUST be strictly in YYYY-MM-DD format (e.g., '2023-10-25'). Do NOT include time.")
	private String activityEndDate;

}