package org.tedros.redminetools.module.ai.function;

import java.util.function.Function;

import org.tedros.ai.function.TFunction;

public class RedmineIssueSearchAiFunction extends TFunction<String> {

	public RedmineIssueSearchAiFunction(String name, String description, Class<String> model,
			Function<String, Object> callback) {
		super(name, description, model, callback);
		// TODO Auto-generated constructor stub
	}

}
