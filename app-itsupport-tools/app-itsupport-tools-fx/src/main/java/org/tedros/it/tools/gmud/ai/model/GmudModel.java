package org.tedros.it.tools.gmud.ai.model;

import java.util.Date;
import java.util.List;

import org.tedros.extension.model.ContactType;
import org.tedros.it.tools.entity.Gmud;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GmudModel {

	private String title;

    private String description;
    
    private String type;
    
    private String status;

    private PersonModel requester;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date scheduledDate;

    private String rollbackPlan;
    
    private Long gitLabProjectId;
    
    private String gitLabProjectName;
    
    private List<GmudRedmineRefModel> redmineIssueReferences;
    
    private List<GmudItemModel> executionPlan;

    private List<GmudReviewModel> reviews;
    
    public GmudModel(Gmud gmud) {	
		this.title = gmud.getTitle();
		this.description = gmud.getDescription();
		this.type = gmud.getType();
		this.status = gmud.getStatus();
		if(gmud.getRequester()!=null) {
			String email = gmud.getRequester().getContacts().stream()
					.filter(c -> c.getType().equals(ContactType.EMAIL))
					.findFirst()
					.map(c -> c.getValue())
					.orElse(null);
			this.requester = new PersonModel(
					gmud.getRequester().getId(), 
					gmud.getRequester().getName(), 
					email);
		}
		this.scheduledDate = gmud.getScheduledDate();
		this.rollbackPlan = gmud.getRollbackPlan();
		this.gitLabProjectId = gmud.getProjectId();
		this.gitLabProjectName = gmud.getProjectName();
		
		if(gmud.getIssueReferences()!=null && !gmud.getIssueReferences().isEmpty()) {
			this.redmineIssueReferences = gmud.getIssueReferences().stream()
					.map(ref -> new GmudRedmineRefModel(ref.getIssueId(), ref.getIssueTitle()))
					.toList();
		}
		
		if(gmud.getExecutionPlan()!=null && !gmud.getExecutionPlan().isEmpty()) {
			this.executionPlan = gmud.getExecutionPlan().stream()
					
					.map(item ->{
						
						PersonModel person = null;
						
						if(item.getResponsible()!=null) {
							String email = item.getResponsible().getContacts().stream()
									.filter(c -> c.getType().equals(ContactType.EMAIL))
									.findFirst()
									.map(c -> c.getValue())
									.orElse(null);
							person = new PersonModel(
									item.getResponsible().getId(), 
									item.getResponsible().getName(), 
									email);
						}
			
						return new GmudItemModel(
							item.getStepOrder(), 
							item.getAction(), 
							person, 
							item.getStatus());
						})
					.toList();
		}
		
		if(gmud.getReviews()!=null && !gmud.getReviews().isEmpty()) {
			this.reviews = gmud.getReviews().stream()
					.map(review -> {
						
						PersonModel reviewer = null;
						
						if(review.getReviewer()!=null) {
							String email = review.getReviewer().getContacts().stream()
									.filter(c -> c.getType().equals(ContactType.EMAIL))
									.findFirst()
									.map(c -> c.getValue())
									.orElse(null);
							reviewer = new PersonModel(
									review.getReviewer().getId(), 
									review.getReviewer().getName(), 
									email);
						}
						
						return new GmudReviewModel(
								reviewer, 
								review.getComments(), 
								review.getStatus(), 
								review.getReviewDate());
					})
					.toList();
		}
	}
	
}
