package org.tedros.redminetools.gateway;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("Represents a filter for Redmine issues by assigned to ID")
public class RedmineAssignedToFilter {
		
		@JsonPropertyDescription("the assigned to ID.")
        private String assigned_to_id;

		public String getAssigned_to_id() {
			return assigned_to_id;
		}

		public void setAssigned_to_id(String assigned_to_id) {
			this.assigned_to_id = assigned_to_id;
		}
		
		
		        
}

