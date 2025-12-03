package org.tedros.it.tools.redmine.ai.function;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Response;
import org.tedros.ai.openai.model.ToolCallResult;
import org.tedros.it.tools.redmine.ai.model.RedmineIssueIdToFind;
import org.tedros.it.tools.redmine.api.model.TIssueEvidenceInfo;
import org.tedros.it.tools.redmine.gateway.RedmineApiGateway;
import org.tedros.util.TLoggerUtil;

public class GetRedmineIssueAiFunction extends TFunction<RedmineIssueIdToFind> {
    
    private static final Logger LOGGER = TLoggerUtil.getLogger(GetRedmineIssueAiFunction.class);

    private static final String FUNCTION_NAME = "get_redmine_issue";

    private static final String PROMPT = """
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
        super(FUNCTION_NAME, PROMPT, RedmineIssueIdToFind.class, v -> {
            try {
                LOGGER.info("Fetching Redmine issue #{}", v.getIssueId());
                var gateway = new RedmineApiGateway(
                    RedmineApiPropertyUtil.getInstance().getRedmineUrl(),
                    RedmineApiPropertyUtil.getInstance().getRedmineKey()
                );

                TIssueEvidenceInfo issue = gateway.getTIssueEvidenceInfo(v.getIssueId());
                if (issue == null) {
                    return new Response("Error: Issue #" + v.getIssueId() + " not found or inaccessible.");
                }

                int attachCount = issue.getAttachments() != null ? issue.getAttachments().size() : 0;
                LOGGER.info("Issue #{} retrieved. {} attachment(s) found.", v.getIssueId(), attachCount);

                return new ToolCallResult(FUNCTION_NAME, issue, true);

            } catch (Exception e) {
                LOGGER.error("Failed to fetch issue #{}: {}", v.getIssueId(), e.getMessage(), e);
                return new Response("Failed to retrieve issue: " + e.getMessage());
            }
        });
    }
}