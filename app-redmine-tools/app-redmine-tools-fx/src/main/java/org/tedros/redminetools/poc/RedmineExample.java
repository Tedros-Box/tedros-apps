package org.tedros.redminetools.poc;

import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.RedmineManagerFactory;
import com.taskadapter.redmineapi.bean.Issue;

import java.util.List;

public class RedmineExample {
    public static void main(String[] args) {
        String redmineURI = "https://your-redmine-instance.com";
        String apiAccessKey = "your_api_key";
        String projectKey = "your_project_id";

        // Enable REST API key access in your Redmine user profile.
        // Go to My account -> API access key.
        RedmineManager mgr = RedmineManagerFactory.createWithApiKey(redmineURI, apiAccessKey);

        try {
            // Fetch all issues for a specific project
            List<Issue> issues = mgr.getIssueManager().getIssues(projectKey, null);
            for (Issue issue : issues) {
                System.out.println(issue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

