package org.tedros.it.tools.redmine.ai.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("Represents a filter for Redmine users by user name")
public class RedmineUserFilter {
	
		@JsonPropertyDescription("the user name")
        private String userName;

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}
		
		        
}

