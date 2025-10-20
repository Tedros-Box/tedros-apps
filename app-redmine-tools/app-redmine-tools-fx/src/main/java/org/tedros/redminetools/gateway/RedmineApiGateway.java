package org.tedros.redminetools.gateway;

import java.util.ArrayList;
import java.util.List;

import org.tedros.redminetools.mapper.RedmineMapper;
import org.tedros.redminetools.model.TIssue;
import org.tedros.redminetools.model.TProject;

import com.taskadapter.redmineapi.Include;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.RedmineManagerFactory;
import com.taskadapter.redmineapi.bean.Issue;
import com.taskadapter.redmineapi.bean.Project;

public class RedmineApiGateway {
	
	private RedmineManager manager;

	public RedmineApiGateway(String uri, String apiAccessKey) {
		this.manager = RedmineManagerFactory.createWithApiKey(uri, apiAccessKey);
	}
	
	public TIssue getIssuesById(Integer issueId){
		
		try {
			Issue issue = this.manager.getIssueManager().getIssueById(issueId, Include.values());
			
			if(issue!=null) 
				return RedmineMapper.convert(issue);
			
			return null;
			
		}catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public List<TIssue> getIssuesBySummary(String projectId, String summary){
		
		try {
			List<Issue> issues = this.manager.getIssueManager().getIssuesBySummary(projectId, summary);
			
			if(issues!=null) 
				return RedmineMapper.convertIssueList(issues);
			
			return null;
			
		}catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public TProject getProjectByIdentifier(String identifier){
		
		try {
			Project project = this.manager.getProjectManager().getProjectByKey(identifier);
			
			if(project!=null) 
				return RedmineMapper.convert(project);
			
			return null;
			
		}catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public TProject getProjectById(int idProject){
		
		try {
			Project project = this.manager.getProjectManager().getProjectById(idProject);
			
			if(project!=null) 
				return RedmineMapper.convert(project);
			
			return null;
			
		}catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public Long countAllProjects(){
		
		return 1L;
		
		/*try {
			List<Project> projects = this.manager.getProjectManager().getProjects();
			if(projects!=null) 
				return Long.valueOf(projects.size());
			return 0L;
		}catch (Exception e) {
			throw new TBusinessException(e.getMessage());
		}
		*/
	}
	
	public List<TProject> listAllProjects(){
		
		TProject p = new TProject();
		p.setId(1L);
		p.setName("Project 1");
		p.setIdentifier("project1");
		List<TProject> projects = new ArrayList<>();
		projects.add(p);
		return projects;
		/*
		try {
			List<Project> projects = this.manager.getProjectManager().getProjects();
			
			if(projects!=null) 
				return RedmineMapper.convertProjectList(projects);
			
			return List.of();
			
		}catch (Exception e) {
			throw new TBusinessException(e.getMessage());
		}*/
	}
	
	public static void main(String[] args) {
		String redmineURI = "https://redmine.detran.go.gov.br/";
        String apiAccessKey = "559147fe2183d824e7784c2862e6e0b070cd6804";
        
        RedmineApiGateway gateway = new RedmineApiGateway(redmineURI, apiAccessKey);
        List<TProject> projects = gateway.listAllProjects();
        projects.stream().forEach(p->{
    		
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
	}

	

}
