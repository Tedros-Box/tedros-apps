package org.tedros.it.tools.redmine.ai.function;

import java.util.List;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Response;
import org.tedros.it.tools.redmine.ai.model.RedmineIssueIdToFind;
import org.tedros.it.tools.redmine.api.model.TTimeEntry;
import org.tedros.it.tools.redmine.gateway.RedmineApiGateway;
import org.tedros.util.TLoggerUtil;

public class RedmineListIssueTimeEntryAiFunction extends TFunction<RedmineIssueIdToFind> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(RedmineListIssueTimeEntryAiFunction.class);
	
	public static final String NAME = "get_redmine_issue_time_entries";	
	public static final String DESCRIPTION = "Retrieves the history of time entries, work logs, and hours spent for a specific Redmine issue ID.";
	
	public RedmineListIssueTimeEntryAiFunction() {
		super(NAME, DESCRIPTION, RedmineIssueIdToFind.class, v -> {
			try {
				LOGGER.info("Fetching time entries for Redmine issue ID: {}", v.getIssueId());
				RedmineApiPropertyUtil propertyUtil = RedmineApiPropertyUtil.getInstance();
				RedmineApiGateway gateway = new RedmineApiGateway(propertyUtil.getRedmineUrl(), propertyUtil.getRedmineKey());
				
				List<TTimeEntry> entries = gateway.getTimeEntriesForIssue(v.getIssueId());
				
				LOGGER.info("Entries found for issue {}: {}", v.getIssueId(), entries.size());
				
				return new Response(SUSCESS_MESSAGE + DO_NOT_CALL_AGAIN  + PROCEED_WITH_HTML_RESPONSE, entries);
				
			} catch (Exception e) {
				LOGGER.error("Error fetching time entries", e);
				return new Response(EXCEPTION_MESSAGE + e.getMessage());
			}
		});
	}
}