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
	
	// NOME: Usar snake_case é padrão para functions calls
	private static final String NAME = "get_redmine_issue_time_entries";
	
	// PROMPT: Rico em palavras-chave (hours, work logs, time entries)
	private static final String PROMPT = "Retrieves the history of time entries, work logs, and hours spent for a specific Redmine issue ID.";
			
	public RedmineListIssueTimeEntryAiFunction() {
		super(NAME, PROMPT, RedmineIssueIdToFind.class, v -> {
			try {
				RedmineApiPropertyUtil propertyUtil = RedmineApiPropertyUtil.getInstance();
				RedmineApiGateway gateway = new RedmineApiGateway(propertyUtil.getRedmineUrl(), propertyUtil.getRedmineKey());
				
				List<TTimeEntry> entries = gateway.getTimeEntriesForIssue(v.getIssueId());
				
				// Dica: Logs são úteis, mas cuidado com dados sensíveis em produção
				LOGGER.info("Entries found for issue {}: {}", v.getIssueId(), entries.size());
				
				return new Response(SUSCESS_MESSAGE + DO_NOT_CALL_AGAIN  + PROCEED_WITH_HTML_RESPONSE, entries);
				
			} catch (Exception e) {
				LOGGER.error("Error fetching time entries", e);
				// Retorna o erro de forma que a IA saiba explicar o que houve
				return new Response("Error retrieving time entries: " + e.getMessage());
			}
		});
	}
}