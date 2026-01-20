package org.tedros.it.tools.redmine.ai.model;

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
@JsonClassDescription("Represents a filter for Redmine issues by assigned to ID")
public class RedmineAssignedToFilter {
		
		@JsonPropertyDescription("the assigned to ID.")
        private String assigned_to_id;		
}

