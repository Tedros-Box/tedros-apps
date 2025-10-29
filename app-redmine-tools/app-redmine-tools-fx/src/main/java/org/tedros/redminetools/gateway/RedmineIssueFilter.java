package org.tedros.redminetools.gateway;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

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

		public FilterCondition getStatus_id() {
			return status_id;
		}

		public void setStatus_id(FilterCondition status_id) {
			this.status_id = status_id;
		}

		public FilterCondition getProject_id() {
			return project_id;
		}

		public void setProject_id(FilterCondition project_id) {
			this.project_id = project_id;
		}

		public FilterCondition getTracker_id() {
			return tracker_id;
		}

		public void setTracker_id(FilterCondition tracker_id) {
			this.tracker_id = tracker_id;
		}

		public FilterCondition getPriority_id() {
			return priority_id;
		}

		public void setPriority_id(FilterCondition priority_id) {
			this.priority_id = priority_id;
		}

		public FilterCondition getAuthor_id() {
			return author_id;
		}

		public void setAuthor_id(FilterCondition author_id) {
			this.author_id = author_id;
		}

		public FilterCondition getAssigned_to_id() {
			return assigned_to_id;
		}

		public void setAssigned_to_id(FilterCondition assigned_to_id) {
			this.assigned_to_id = assigned_to_id;
		}

		public FilterCondition getFixed_version_id() {
			return fixed_version_id;
		}

		public void setFixed_version_id(FilterCondition fixed_version_id) {
			this.fixed_version_id = fixed_version_id;
		}

		public FilterCondition getSubject() {
			return subject;
		}

		public void setSubject(FilterCondition subject) {
			this.subject = subject;
		}

		public FilterCondition getDescription() {
			return description;
		}

		public void setDescription(FilterCondition description) {
			this.description = description;
		}

		public FilterCondition getNotes() {
			return notes;
		}

		public void setNotes(FilterCondition notes) {
			this.notes = notes;
		}

		public FilterCondition getDone_ratio() {
			return done_ratio;
		}

		public void setDone_ratio(FilterCondition done_ratio) {
			this.done_ratio = done_ratio;
		}

		public FilterCondition getIs_private() {
			return is_private;
		}

		public void setIs_private(FilterCondition is_private) {
			this.is_private = is_private;
		}

		public FilterCondition getWatcher_id() {
			return watcher_id;
		}

		public void setWatcher_id(FilterCondition watcher_id) {
			this.watcher_id = watcher_id;
		}

		public FilterCondition getUpdated_by() {
			return updated_by;
		}

		public void setUpdated_by(FilterCondition updated_by) {
			this.updated_by = updated_by;
		}

		public FilterCondition getLast_updated_by() {
			return last_updated_by;
		}

		public void setLast_updated_by(FilterCondition last_updated_by) {
			this.last_updated_by = last_updated_by;
		}

		public FilterCondition getIssue_id() {
			return issue_id;
		}

		public void setIssue_id(FilterCondition issue_id) {
			this.issue_id = issue_id;
		}

		public FilterCondition getTags() {
			return tags;
		}

		public void setTags(FilterCondition tags) {
			this.tags = tags;
		}

		public FilterCondition getMember_of_group() {
			return member_of_group;
		}

		public void setMember_of_group(FilterCondition member_of_group) {
			this.member_of_group = member_of_group;
		}

		public FilterCondition getAssigned_to_role() {
			return assigned_to_role;
		}

		public void setAssigned_to_role(FilterCondition assigned_to_role) {
			this.assigned_to_role = assigned_to_role;
		}

		public FilterCondition getCreated_on() {
			return created_on;
		}

		public void setCreated_on(FilterCondition created_on) {
			this.created_on = created_on;
		}

		public FilterCondition getUpdated_on() {
			return updated_on;
		}

		public void setUpdated_on(FilterCondition updated_on) {
			this.updated_on = updated_on;
		}

		public FilterCondition getClosed_on() {
			return closed_on;
		}

		public void setClosed_on(FilterCondition closed_on) {
			this.closed_on = closed_on;
		}

		public FilterCondition getStart_date() {
			return start_date;
		}

		public void setStart_date(FilterCondition start_date) {
			this.start_date = start_date;
		}

		public FilterCondition getDue_date() {
			return due_date;
		}

		public void setDue_date(FilterCondition due_date) {
			this.due_date = due_date;
		}

		public FilterCondition getEstimated_hours() {
			return estimated_hours;
		}

		public void setEstimated_hours(FilterCondition estimated_hours) {
			this.estimated_hours = estimated_hours;
		}

		public FilterCondition getSpent_time() {
			return spent_time;
		}

		public void setSpent_time(FilterCondition spent_time) {
			this.spent_time = spent_time;
		}

		public FilterCondition getAttachment() {
			return attachment;
		}

		public void setAttachment(FilterCondition attachment) {
			this.attachment = attachment;
		}

		public FilterCondition getAttachment_description() {
			return attachment_description;
		}

		public void setAttachment_description(FilterCondition attachment_description) {
			this.attachment_description = attachment_description;
		}

		public FilterCondition getProject_status() {
			return project_status;
		}

		public void setProject_status(FilterCondition project_status) {
			this.project_status = project_status;
		}

		public FilterCondition getRelates() {
			return relates;
		}

		public void setRelates(FilterCondition relates) {
			this.relates = relates;
		}

		public FilterCondition getDuplicates() {
			return duplicates;
		}

		public void setDuplicates(FilterCondition duplicates) {
			this.duplicates = duplicates;
		}

		public FilterCondition getDuplicated() {
			return duplicated;
		}

		public void setDuplicated(FilterCondition duplicated) {
			this.duplicated = duplicated;
		}

		public FilterCondition getBlocks() {
			return blocks;
		}

		public void setBlocks(FilterCondition blocks) {
			this.blocks = blocks;
		}

		public FilterCondition getBlocked() {
			return blocked;
		}

		public void setBlocked(FilterCondition blocked) {
			this.blocked = blocked;
		}

		public FilterCondition getPrecedes() {
			return precedes;
		}

		public void setPrecedes(FilterCondition precedes) {
			this.precedes = precedes;
		}

		public FilterCondition getFollows() {
			return follows;
		}

		public void setFollows(FilterCondition follows) {
			this.follows = follows;
		}

		public FilterCondition getCopied_to() {
			return copied_to;
		}

		public void setCopied_to(FilterCondition copied_to) {
			this.copied_to = copied_to;
		}

		public FilterCondition getCopied_from() {
			return copied_from;
		}

		public void setCopied_from(FilterCondition copied_from) {
			this.copied_from = copied_from;
		}

		public FilterCondition getParent_id() {
			return parent_id;
		}

		public void setParent_id(FilterCondition parent_id) {
			this.parent_id = parent_id;
		}

		public FilterCondition getChild_id() {
			return child_id;
		}

		public void setChild_id(FilterCondition child_id) {
			this.child_id = child_id;
		}

		public FilterCondition getCf_80() {
			return cf_80;
		}

		public void setCf_80(FilterCondition cf_80) {
			this.cf_80 = cf_80;
		}

		public FilterCondition getCf_62() {
			return cf_62;
		}

		public void setCf_62(FilterCondition cf_62) {
			this.cf_62 = cf_62;
		}

		public FilterCondition getCf_2() {
			return cf_2;
		}

		public void setCf_2(FilterCondition cf_2) {
			this.cf_2 = cf_2;
		}

		public FilterCondition getCf_3() {
			return cf_3;
		}

		public void setCf_3(FilterCondition cf_3) {
			this.cf_3 = cf_3;
		}

		public FilterCondition getCf_5() {
			return cf_5;
		}

		public void setCf_5(FilterCondition cf_5) {
			this.cf_5 = cf_5;
		}

		public FilterCondition getCf_6() {
			return cf_6;
		}

		public void setCf_6(FilterCondition cf_6) {
			this.cf_6 = cf_6;
		}

		public FilterCondition getCf_41() {
			return cf_41;
		}

		public void setCf_41(FilterCondition cf_41) {
			this.cf_41 = cf_41;
		}

		public FilterCondition getCf_59() {
			return cf_59;
		}

		public void setCf_59(FilterCondition cf_59) {
			this.cf_59 = cf_59;
		}

		public FilterCondition getCf_60() {
			return cf_60;
		}

		public void setCf_60(FilterCondition cf_60) {
			this.cf_60 = cf_60;
		}

		public FilterCondition getCf_61() {
			return cf_61;
		}

		public void setCf_61(FilterCondition cf_61) {
			this.cf_61 = cf_61;
		}

		public FilterCondition getCf_75() {
			return cf_75;
		}

		public void setCf_75(FilterCondition cf_75) {
			this.cf_75 = cf_75;
		}

		public FilterCondition getCf_71() {
			return cf_71;
		}

		public void setCf_71(FilterCondition cf_71) {
			this.cf_71 = cf_71;
		}

		public FilterCondition getCf_30() {
			return cf_30;
		}

		public void setCf_30(FilterCondition cf_30) {
			this.cf_30 = cf_30;
		}
        
        
}

