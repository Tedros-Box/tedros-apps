package org.tedros.it.tools.redmine.ai.function;

import java.util.Map;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.ToolCallResult;
import org.tedros.ai.function.model.Response;
import org.tedros.it.tools.redmine.ai.model.RedmineIssueIdToFind;
import org.tedros.it.tools.redmine.api.model.TIssueEvidenceInfo;
import org.tedros.it.tools.redmine.gateway.RedmineApiGateway;
import org.tedros.util.TLoggerUtil;

public class GetRedmineIssueAiFunction extends TFunction<RedmineIssueIdToFind> {
    
    private static final Logger LOGGER = TLoggerUtil.getLogger(GetRedmineIssueAiFunction.class);

    public static final String NAME = "get_redmine_issue";
    public static final String DESCRIPTION = """
	    Call this function when user wants to analyze, review, audit, or understand a Redmine issue.
	    Always use this FIRST when a specific issue ID is mentioned.
	    Returns full issue data including:
	    • Title, description, status, priority, assignee
	    • Custom fields (deliverable, HPA, required profile, service type)
	    • All notes/comments in chronological order
	    • List of attachments with file names, sizes, and metadata
	    Never analyze content or attachments without calling this first.
	    Input: issueId (integer, required)
	    Output: Complete issue object with attachments list
	    """;

    public GetRedmineIssueAiFunction() {
        super(NAME, DESCRIPTION, RedmineIssueIdToFind.class, v -> {
            try {
                LOGGER.info("Fetching Redmine issue #{}", v.getIssueId());
                var gateway = new RedmineApiGateway(
                    RedmineApiPropertyUtil.getInstance().getRedmineUrl(),
                    RedmineApiPropertyUtil.getInstance().getRedmineKey()
                );

                TIssueEvidenceInfo issue = gateway.getTIssueEvidenceInfo(v.getIssueId());
                if (issue == null) {
                    return new Response(NO_DATA_FOUND_MESSAGE);
                }

                int attachCount = issue.getAttachments() != null ? issue.getAttachments().size() : 0;
                LOGGER.info("Issue #{} retrieved. {} attachment(s) found.", v.getIssueId(), attachCount);

                return ToolCallResult.builder()
						.message("Redmine issue retrieved successfully.")
						.result(Map.of(
		                    STATUS, SUCCESS,
		                    ACTION, "redmine_issue_retrieved",
		                    SYSTEM_INSTRUCTION, "Issue retrieved successfully. "
		                    		+ "Do not retry again. Proceed with the user's request.",
		                    "issue", issue
		                ))
						.build();

            } catch (Exception e) {
                LOGGER.error("Failed to fetch issue #{}: {}", v.getIssueId(), e.getMessage(), e);
                return ToolCallResult.builder()
						.message("Error retrieving Redmine issue: " + e.getMessage())
						.result(Map.of(
	                        STATUS, ERROR,
	                        ACTION, "gitlab_repository_commit_error",
	                        ERROR_MESSAGE, e.getMessage()
	                    ))
						.build();
            }
        });
    }
}