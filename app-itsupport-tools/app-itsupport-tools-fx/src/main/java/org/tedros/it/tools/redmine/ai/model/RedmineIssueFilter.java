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
@JsonClassDescription("Represents a filter for Redmine issues with various filter conditions.")
public class RedmineIssueFilter {
	
		@JsonPropertyDescription("the status ID.")
        private FilterCondition status_id;
		
		@JsonPropertyDescription("the project ID.")
        private FilterCondition project_id;
		
		@JsonPropertyDescription("the tracker ID.")
        private FilterCondition tracker_id;
		
		@JsonPropertyDescription("the priority ID.")
        private FilterCondition priority_id;
		
		@JsonPropertyDescription("the author ID.")
        private FilterCondition author_id;
		
		@JsonPropertyDescription("the assigned to ID.")
        private FilterCondition assigned_to_id;
		
		@JsonPropertyDescription("the fixed version ID.")
        private FilterCondition fixed_version_id;
		
		@JsonPropertyDescription("the subject.")
        private FilterCondition subject;
		
		@JsonPropertyDescription("the description.")
        private FilterCondition description;
		
		@JsonPropertyDescription("the notes.")
        private FilterCondition notes;
		
		@JsonPropertyDescription("the done ratio.")
        private FilterCondition done_ratio;
				
        private FilterCondition is_private;
		
		@JsonPropertyDescription("the watcher ID.")
        private FilterCondition watcher_id;
		
		@JsonPropertyDescription("the user who last updated the issue.")
        private FilterCondition updated_by;
		
		@JsonPropertyDescription("the user who made the last update.")
        private FilterCondition last_updated_by;
		
		@JsonPropertyDescription("the issue ID.")
        private FilterCondition issue_id;
		
		@JsonPropertyDescription("the tags associated with the issue.")
        private FilterCondition tags;
        private FilterCondition member_of_group;
        private FilterCondition assigned_to_role;
        private FilterCondition created_on;
        private FilterCondition updated_on;
        private FilterCondition closed_on;
        private FilterCondition start_date;
        private FilterCondition due_date;
        private FilterCondition estimated_hours;
        private FilterCondition spent_time;
        private FilterCondition attachment;
        private FilterCondition attachment_description;
        private FilterCondition project_status;
        private FilterCondition relates;
        private FilterCondition duplicates;
        private FilterCondition duplicated;
        private FilterCondition blocks;
        private FilterCondition blocked;
        private FilterCondition precedes;
        private FilterCondition follows;
        private FilterCondition copied_to;
        private FilterCondition copied_from;
        
        @JsonPropertyDescription("the parent issue ID.")
        private FilterCondition parent_id;
        
        @JsonPropertyDescription("the child issue ID.")
        private FilterCondition child_id;
        // Campos personalizados
        @JsonPropertyDescription("OS Globalweb.")
        private FilterCondition cf_80;
        
        @JsonPropertyDescription("Origem")
        private FilterCondition cf_62;
        
        @JsonPropertyDescription("SEI")
        private FilterCondition cf_2;
        
        @JsonPropertyDescription("Ambiente")
        private FilterCondition cf_3;
        
        @JsonPropertyDescription("Janela")
        private FilterCondition cf_5;
        
        @JsonPropertyDescription("Número do protocolo")
        private FilterCondition cf_6;
        
        @JsonPropertyDescription("Observações")
        private FilterCondition cf_41;
        
        @JsonPropertyDescription("Perfil Exigido")
        private FilterCondition cf_59;
        
        @JsonPropertyDescription("Serviços")
        private FilterCondition cf_60;
        
        @JsonPropertyDescription("Qtd. Itens Serviço")
        private FilterCondition cf_61;
        
        @JsonPropertyDescription("HPA")
        private FilterCondition cf_75;
        
        @JsonPropertyDescription("Classificação")
        private FilterCondition cf_71;
        
        @JsonPropertyDescription("Relatório G4F (OS)")
        private FilterCondition cf_30;

		
        
}

