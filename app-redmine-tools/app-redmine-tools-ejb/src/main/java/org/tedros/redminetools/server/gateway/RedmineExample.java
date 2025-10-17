package org.tedros.redminetools.server.gateway;

import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.RedmineManagerFactory;
import com.taskadapter.redmineapi.bean.Issue;

import java.util.List;

public class RedmineExample {
    public static void main(String[] args) {
        String redmineURI = "https://redmine.detran.go.gov.br/";
        String apiAccessKey = "559147fe2183d824e7784c2862e6e0b070cd6804";
        String projectKey = "43";

        // Enable REST API key access in your Redmine user profile.
        // Go to My account -> API access key.
        RedmineManager mgr = RedmineManagerFactory.createWithApiKey(redmineURI, apiAccessKey);

        try {
        	
        	//mgr.getProjectManager().
        	
        	mgr.getProjectManager().getProjects()
        	.stream().forEach(p->{
        		
        		System.out.println("Name: " + p.getName());
        		System.out.println("ID: " + p.getId());
        		System.out.println("Identifier: " + p.getIdentifier());
        		System.out.println("Trackers: ");
        		p.getTrackers().stream().forEach(t->{
        			System.out.println("Tracker Name: " + t.getName());
            		System.out.println("Tracker ID: " + t.getId());
        		});        		
        		System.out.println("---");
        		
        	});
        	
        	System.out.println("#####");
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

