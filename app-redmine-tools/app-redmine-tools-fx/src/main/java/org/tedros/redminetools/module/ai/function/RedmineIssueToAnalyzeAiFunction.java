package org.tedros.redminetools.module.ai.function;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Response;
import org.tedros.ai.openai.model.ToolCallResult;
import org.tedros.redminetools.gateway.IssueRelevantInfo;
import org.tedros.redminetools.gateway.RedmineApiGateway;
import org.tedros.redminetools.gateway.RedmineIssueIdToFind;
import org.tedros.util.TLoggerUtil;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RedmineIssueToAnalyzeAiFunction extends TFunction<RedmineIssueIdToFind> {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger LOGGER = TLoggerUtil.getLogger(RedmineIssueToAnalyzeAiFunction.class);

    private static final String FUNCTION_NAME = "get_redmine_issue_for_analysis";

    private static final String PROMPT = """
    Use this function whenever the user asks to analyze, review, audit, debug, or understand a Redmine issue/task/demand.

    This function retrieves ALL relevant data from a Redmine issue to enable a complete consistency analysis, including:
    - Issue ID, title, description, status, priority, assignee, author, project
    - Custom fields (if any)
    - Journals (full history of comments and changes)
    - Relations with other issues
    - ALL attached files (with names, sizes, descriptions, and download URLs if available)

    Only call this function when a specific Redmine issue ID is mentioned or can be inferred from context.

    Input:
    - issueId: (integer, required) The numeric ID of the Redmine issue.

    Output:
    - A complete JSON with all issue metadata + list of attachments (including file metadata).

    Do NOT attempt to analyze the issue without first calling this function.
    """;

    public RedmineIssueToAnalyzeAiFunction() {
        super(FUNCTION_NAME, PROMPT, RedmineIssueIdToFind.class, v -> {
            try {
                LOGGER.info("Fetching Redmine issue for analysis. Input: {}", MAPPER.writeValueAsString(v));

                var propertyUtil = RedmineApiPropertyUtil.getInstance();
                var gateway = new RedmineApiGateway(propertyUtil.getRedmineUrl(), propertyUtil.getRedmineKey());

                IssueRelevantInfo issue = gateway.getIssueRelevantInfo(v.getIssueId());

                if (issue == null) {
                    return new Response("Error: Issue not found or inaccessible with ID " + v.getIssueId());
                }

                LOGGER.info("Successfully retrieved issue #{}. Attachments: {}", v.getIssueId(), 
                            issue.attachments() != null ? issue.attachments().size() : 0);

                return new ToolCallResult(FUNCTION_NAME, issue.info(), issue.attachments());

            } catch (Exception e) {
                LOGGER.error("Failed to retrieve Redmine issue ID {}: {}", v.getIssueId(), e.getMessage(), e);
                return new Response("An error occurred while fetching the issue: " + e.getMessage());
            }
        });
    }
}