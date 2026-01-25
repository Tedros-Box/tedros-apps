package org.tedros.tools.test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.tedros.it.tools.redmine.ai.model.FilterCondition;
import org.tedros.it.tools.redmine.ai.model.RedmineFilterField;
import org.tedros.it.tools.redmine.ai.model.RedmineIssueFilter;
import org.tedros.it.tools.redmine.api.model.TIssue;
import org.tedros.it.tools.redmine.api.model.TIssueEvidenceInfo;
import org.tedros.it.tools.redmine.api.model.TIssueStatus;
import org.tedros.it.tools.redmine.gateway.RedmineApiGateway;

// ... other imports
public class RedminePoc {
	
	private static String token = System.getenv("REDMINE_TOKEN"); 
	private static String url = "https://redmine.detran.go.gov.br/";
	private static RedmineApiGateway gateway = new RedmineApiGateway(url, token);
	
    public static void main(String[] args) {
    	filterIssuesByDateInterval();
    	filterIssuesByStatus();
    }

	private static void filterIssuesByStatus() {
		List<TIssueStatus> statuses = gateway.listIssueStatuses();
    	
    	TIssueStatus statusNova = statuses.stream()
				.filter(s -> s.getName().equals("Nova"))
				.findFirst()
				.orElse(null);
    	
    	
    	RedmineIssueFilter filter = new RedmineIssueFilter();
    	filter.setStatus_id(FilterCondition.equalsTo(statusNova.getId().toString()));
    	Map<String, FilterCondition> filters = RedmineFilterField.fromObject(filter);
    	
    	List<TIssueEvidenceInfo> issues = gateway.getIssuesByFilters(filters);
    	
    	System.out.println(issues);
	}

	private static void filterIssuesByDateInterval() {
		RedmineIssueFilter filter = new RedmineIssueFilter();
    	
    	LocalDate startDate = LocalDate.of(2026, 1, 1);
    	LocalDate endDate = LocalDate.of(2026, 1, 6);
    	
    	filter.setCreated_on(FilterCondition.betweenDates(startDate, endDate));
    	
    	Map<String, FilterCondition> filters = RedmineFilterField.fromObject(filter);
    	
    	List<TIssueEvidenceInfo> issues = gateway.getIssuesByFilters(filters);
    	
    	System.out.println(issues.size());
	}
}

