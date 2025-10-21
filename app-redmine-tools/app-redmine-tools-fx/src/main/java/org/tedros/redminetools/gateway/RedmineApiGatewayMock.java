package org.tedros.redminetools.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.tedros.redminetools.model.TIssue;
import org.tedros.redminetools.model.TProject;

public class RedmineApiGatewayMock {

    public RedmineApiGatewayMock(String uri, String apiAccessKey) {
        // Mock constructor, does nothing
    }

    public void loadCustomFieldMetadata() {
        // No-op for mock
    }

    public List<TIssue> getIssuesByFilters(Map<String, FilterCondition> filters) {
        List<TIssue> issues = new ArrayList<>();
        TIssue issue = new TIssue();
        issue.setId(1L);
        issue.setSubject("Mocked Issue");
        issue.setProjectId(100);
        issue.setProjectName("Mocked Project");
        issue.setStatusName("Open");
        issue.setAssigneeName("Mocked User");
        issues.add(issue);
        return issues;
    }

    public TIssue getIssuesById(Integer issueId) {
        TIssue tIssue = new TIssue();
        tIssue.setId(issueId != null ? issueId.longValue() : 1L);
        tIssue.setSubject("Mocked TIssue");
        return tIssue;
    }

    public List<TIssue> getIssuesBySummary(String projectId, String summary) {
        List<TIssue> list = new ArrayList<>();
        TIssue tIssue = new TIssue();
        tIssue.setId(2L);
        tIssue.setSubject("Summary Mocked");
        list.add(tIssue);
        return list;
    }

    public TProject getProjectByIdentifier(String identifier) {
        TProject project = new TProject();
        project.setId(10L);
        project.setName("Mocked Project");
        project.setIdentifier(identifier);
        return project;
    }

    public TProject getProjectById(int idProject) {
        TProject project = new TProject();
        project.setId((long) idProject);
        project.setName("Mocked Project by ID");
        project.setIdentifier("mocked_id");
        return project;
    }

    public Long countAllProjects() {
        return 2L;
    }

    public List<TProject> listAllProjects() {
        List<TProject> list = new ArrayList<>();
        TProject p1 = new TProject();
        p1.setId(1L);
        p1.setName("Project 1");
        p1.setIdentifier("project1");
        TProject p2 = new TProject();
        p2.setId(2L);
        p2.setName("Project 2");
        p2.setIdentifier("project2");
        list.add(p1);
        list.add(p2);
        return list;
    }
}
