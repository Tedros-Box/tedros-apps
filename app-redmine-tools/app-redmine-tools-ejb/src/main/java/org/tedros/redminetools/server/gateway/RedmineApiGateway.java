package org.tedros.redminetools.server.gateway;

import java.util.List;

import org.tedros.redminetools.model.TProject;
import org.tedros.redminetools.server.mapper.RedmineMapper;
import org.tedros.server.exception.TBusinessException;

import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.RedmineManagerFactory;
import com.taskadapter.redmineapi.bean.Project;

public class RedmineApiGateway {
	
	
	private RedmineManager manager;

	public RedmineApiGateway(String uri, String apiAccessKey) {
		this.manager = RedmineManagerFactory.createWithApiKey(uri, apiAccessKey);
	}
	
	public List<TProject> listAllProjects(){
		
		try {
			List<Project> projects = this.manager.getProjectManager().getProjects();
			
			if(projects!=null) 
				return RedmineMapper.convertProjectList(projects);
			
			return List.of();
			
		}catch (Exception e) {
			throw new TBusinessException(e.getMessage());
		}
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
