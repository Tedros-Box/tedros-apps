package org.tedros.it.tools.employeeactivity.ai.function;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.ToolCallResult;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.service.remote.TEjbServiceLocator;
import org.tedros.it.tools.ejb.controller.IProductivityActivityController;
import org.tedros.it.tools.model.ProductivityActivityDTO;
import org.tedros.server.result.TResult;

public class GetEmployeeActivitiesAIFunction extends TFunction<EmployeeActivityCriteria> {
	
	public static final String NAME = "get_employee_activities";
	
	public static final String DESCRIPTION = "Retrieves system-captured employee screen time, productivity metrics, idle/active time, and software/application usage logs. "
			+ "Use this tool SPECIFICALLY when the user asks about an employee's productivity, idleness, what software they are using, or what they are doing during work hours. "
			+ "DO NOT use this tool for project tasks or Redmine issues. "
			+ "CRITICAL: You CANNOT use an employee's name here. You MUST provide the numerical 'tedrosUserId'. "
			+ "If you only have a name (e.g., 'What is John's productivity?'), you MUST call 'search_employees' FIRST to find their ID. "
			+ "CRITICAL LIMIT: Limit your queries to a maximum of 30 days at a time to prevent system overload.";

	public GetEmployeeActivitiesAIFunction() {
		super(NAME, DESCRIPTION, EmployeeActivityCriteria.class, filter -> {
			
			if(filter != null) {
				
				Long userId = filter.getTedrosUserId();
				
				if(userId == null || userId <= 0) {
					return ToolCallResult.builder()
							.message("Invalid user ID provided.")
							.result(Map.of(
			                    STATUS, ERROR,
			                    ACTION, "employee_activities_invalid_user_id",
			                    ERROR_MESSAGE, "Invalid user ID provided."
			                ))
							.build();
				}
				
				String start = filter.getActivityBeginDate();
				
				if(StringUtils.isBlank(start)) {
					return ToolCallResult.builder()
							.message("Activity start date is required.")
							.result(Map.of(
			                    STATUS, ERROR,
			                    ACTION, "employee_activities_missing_start_date",
			                    ERROR_MESSAGE, "Activity start date is required."
			                ))
							.build();
				}
				
				try {
					LocalDate startLocalDate = LocalDate.parse(start.trim());
					LocalDateTime startDate = startLocalDate.atStartOfDay();
					
					String end = filter.getActivityEndDate();
					LocalDateTime endDate;
					if (StringUtils.isNotBlank(end)) {
						endDate = LocalDate.parse(end.trim()).atTime(23, 59, 59);
					} else {
						endDate = startLocalDate.atTime(23, 59, 59);
					}
					
					try(TEjbServiceLocator locator = TEjbServiceLocator.getInstance()) {
						
						IProductivityActivityController controller = locator.lookup(IProductivityActivityController.JNDI_NAME);
						TResult<List<ProductivityActivityDTO>> result = controller
								.findUserIdAndDateRange(TedrosContext.getLoggedUser().getAccessToken(), userId, startDate, endDate);
						
						if(result.isSuccess()) {
							
							List<ProductivityActivityDTO> rawLogs = result.getValue();
							
							// 1. LÓGICA DE AGREGAÇÃO E RESUMO
							Map<LocalDate, Map<String, List<ProductivityActivityDTO>>> groupedByDateAndApp = rawLogs.stream()
									.filter(dto -> dto.getTimestamp() != null && StringUtils.isNotBlank(dto.getActiveWindowTitle()))
									.collect(Collectors.groupingBy(
											dto -> dto.getTimestamp().toLocalDate(),
											Collectors.groupingBy(ProductivityActivityDTO::getActiveWindowTitle)
									));

							List<DailyActivitySummary> dailySummaries = new ArrayList<>();
							
							for (Map.Entry<LocalDate, Map<String, List<ProductivityActivityDTO>>> dateEntry : groupedByDateAndApp.entrySet()) {
								LocalDate date = dateEntry.getKey();
								long dailyMouse = 0;
								long dailyKeyboard = 0;
								List<AppUsageSummary> appSummaries = new ArrayList<>();

								for (Map.Entry<String, List<ProductivityActivityDTO>> appEntry : dateEntry.getValue().entrySet()) {
									String appName = appEntry.getKey();
									List<ProductivityActivityDTO> appLogs = appEntry.getValue();
									
									long mouse = appLogs.stream().mapToLong(ProductivityActivityDTO::getMouseEventCount).sum();
									long keyboard = appLogs.stream().mapToLong(ProductivityActivityDTO::getKeyboardEventCount).sum();
									int logsCount = appLogs.size();

									dailyMouse += mouse;
									dailyKeyboard += keyboard;

									appSummaries.add(new AppUsageSummary(appName, mouse, keyboard, logsCount));
								}

								// Ordena os apps mais usados no dia no topo (baseado na quantidade de logs/tempo)
								appSummaries.sort((a, b) -> Integer.compare(b.getLogEntries(), a.getLogEntries()));

								dailySummaries.add(new DailyActivitySummary(date.toString(), dailyMouse, dailyKeyboard, appSummaries));
							}

							// Ordena por data
							dailySummaries.sort(Comparator.comparing(DailyActivitySummary::getDate));

							return ToolCallResult.builder()
									.message("Employee activities retrieved and summarized successfully.")
									.result(Map.of(
					                    STATUS, SUCCESS,
					                    ACTION, "employee_activities_retrieved",
					                    SYSTEM_INSTRUCTION, "Employee activities summarized successfully. "
					                    		+ "Present this summary to the user clearly. Do not retry.",
					                    "days_analyzed", dailySummaries.size(),
					                    "employee_activities_summary", dailySummaries
					                ))
									.build();
							
						} else {
							String status = result.getState().name();
							String message = StringUtils.isNoneBlank(result.getMessage()) 
									? result.getMessage() 
											: "Unknown error";
							
							return ToolCallResult.builder()
									.message(status+" while retrieving employee activities")
									.result(Map.of(
					                    STATUS, ERROR,
					                    ACTION, "employee_activities_" + status.toLowerCase(),
					                    ERROR_MESSAGE, message
					                ))
									.build();
						}
						
					}
				} catch (java.time.format.DateTimeParseException dtpe) {
					return ToolCallResult.builder()
							.message("Invalid date format. Expected YYYY-MM-DD.")
							.result(Map.of(
			                    STATUS, ERROR,
			                    ACTION, "employee_activities_invalid_date",
			                    ERROR_MESSAGE, "You must provide dates strictly in YYYY-MM-DD format without time."
			                ))
							.build();
				} catch (Exception e) {
					return ToolCallResult.builder()
							.message("Error retrieving employee activities: " + e.getMessage())
							.result(Map.of(
			                    STATUS, ERROR,
			                    ACTION, "employee_activities_error",
			                    ERROR_MESSAGE, e.getMessage()
			                ))
							.build();
				}
				
			}
			
			return ToolCallResult.builder()
					.message("No filter criteria provided.")
					.result(Map.of(
	                    STATUS, ERROR,
	                    ACTION, "employee_activities_no_criteria",
	                    ERROR_MESSAGE, "No filter criteria provided."
	                ))
					.build();
		});
	}
}