package org.tedros.it.tools.gmud.ai.model;

import java.util.Date;
import org.tedros.it.tools.domain.GmudStatus;
import org.tedros.it.tools.domain.GmudType;
import com.fasterxml.jackson.annotation.JsonPropertyDescription; // Importante
import com.fasterxml.jackson.annotation.JsonFormat; // Importante para datas

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GmudFilterModel {

    @JsonPropertyDescription("The title or part of the title of the GMUD to search for (partial match supported).")
    private String title;
    
    @JsonPropertyDescription("The type of the GMUD. Valid values: NORMAL, STANDARD, EMERGENCY.")
    private GmudType type; 
    
    @JsonPropertyDescription("The current status of the GMUD. Valid values: OPENED, DRAFT, ANALYSIS, APPROVED, EXECUTING, FINISHED, REJECTED, FAILED.")
    private GmudStatus status;

    @JsonPropertyDescription("The unique database ID of the person who requested the GMUD.")
    private Long requesterId;
    
    @JsonPropertyDescription("The name or partial name of the requester.")
    private String requesterName;

    @JsonPropertyDescription("Filter GMUDs scheduled on or after this date. Format: YYYY-MM-DD or ISO 8601.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") 
    private Date startScheduledDate;
    
    @JsonPropertyDescription("Filter GMUDs scheduled on or before this date. Format: YYYY-MM-DD or ISO 8601.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endScheduledDate;

    @JsonPropertyDescription("The ID of the associated GitLab project.")
    private Long gitLabProjectId;
    
    @JsonPropertyDescription("The name or partial name of the associated GitLab project.")
    private String gitLabProjectName;
    
    @JsonPropertyDescription("The ID of the associated Redmine issue.")
    private Integer redmineIssueId;
    
    @JsonPropertyDescription("The title or partial title of the associated Redmine issue.")
    private String redmineIssueTitle;
    
    @JsonPropertyDescription("The unique ID of the technical reviewer.")
    private String reviewerId;
    
    @JsonPropertyDescription("The name or partial name of the technical reviewer.")
    private String reviewerName;
    
}