package org.tedros.it.tools.gmud.ai.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GmudRedmineRefModel {

	private Integer redmineIssueId;
    private String redmineIssueTitle;
}
