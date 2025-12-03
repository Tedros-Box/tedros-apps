package org.tedros.tools.test;

import java.util.Map;

import org.tedros.it.tools.redmine.ai.model.FilterCondition;
import org.tedros.it.tools.redmine.ai.model.RedmineFilterField;

// ... other imports
public class RedmineMainExample {
    public static void main(String[] args) {
    	Map<String, Object> raw = Map.of(
    		    "status_id", Map.of("op", "!=", "value", "2"),
    		    "assigned_to_id", Map.of("op", "=", "value", "509"),
    		    "cf_30", Map.of("op", "=", "value", "Entregue"),
    		    "due_date", Map.of("value", Map.of("from", "2025-01-01", "to", "2025-12-31"))
    		);

    		Map<String, FilterCondition> filters = RedmineFilterField.fromRawMap(raw);

    		filters.forEach((field, condition) -> {
    		    String joinedValues = String.join(", ", condition.getValues());
    		    System.out.println(field + " " + condition.getOperator() + " " + joinedValues);
    		});

    }
}

