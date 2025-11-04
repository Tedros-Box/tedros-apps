package org.tedros.redminetools.module.ai.function;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Response;
import org.tedros.redminetools.gateway.FilterCondition;
import org.tedros.redminetools.gateway.RedmineApiGateway;
import org.tedros.redminetools.gateway.RedmineFilterField;
import org.tedros.redminetools.gateway.RedmineIssueFilter;
import org.tedros.redminetools.model.TIssue;
import org.tedros.util.TLoggerUtil;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RedmineSearchUserAiFunction extends TFunction<RedmineIssueFilter> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(RedmineIssueSearchAiFunction.class);

	public RedmineSearchUserAiFunction() {
		super("filter_redmine_user", "", RedmineIssueFilter.class, 
				v -> {
					try {
						
						ObjectMapper mapper = new ObjectMapper();
						
						LOGGER.info("Filtros recebidos: {}", mapper.writeValueAsString(v));
						
						Map<String, FilterCondition> filters;
						filters = RedmineFilterField.fromObject(v);
						String redmineURI = "https://redmine.detran.go.gov.br/";
				        String apiAccessKey = "559147fe2183d824e7784c2862e6e0b070cd6804";
				        
				        RedmineApiGateway gateway = new RedmineApiGateway(redmineURI, apiAccessKey);
				        
				        List<TIssue> issues = gateway.getIssuesByFilters(filters);
						
				        LOGGER.info("Resultado da pesquisa: {}", mapper.writeValueAsString(issues));
				        
						return new Response("Result list", issues);
						
					} catch (Exception e) {
						return new Response("An error occurred: "+e.getMessage());
					}
					  
				});
	}

}
