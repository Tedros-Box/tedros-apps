package org.tedros.redminetools.gateway;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tedros.redminetools.mapper.RedmineMapper;
import org.tedros.redminetools.model.TIssue;
import org.tedros.redminetools.model.TProject;

import com.taskadapter.redmineapi.Include;
import com.taskadapter.redmineapi.Params;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.RedmineManagerFactory;
import com.taskadapter.redmineapi.bean.CustomFieldDefinition;
import com.taskadapter.redmineapi.bean.Issue;
import com.taskadapter.redmineapi.bean.Project;
import com.taskadapter.redmineapi.internal.ResultsWrapper;

public class RedmineApiGateway {
	
	private RedmineManager manager;
	private Map<String, CustomFieldMetadata> customFieldCache = new HashMap<>();

	public RedmineApiGateway(String uri, String apiAccessKey) {
		this.manager = RedmineManagerFactory.createWithApiKey(uri, apiAccessKey);
	}
	
	public void loadCustomFieldMetadata() {
	    try {
	        List<CustomFieldDefinition> fields = manager.getCustomFieldManager().getCustomFieldDefinitions();
	        for (CustomFieldDefinition field : fields) {
	            FilterType type;
	            switch (field.getFieldFormat()) {
	                case "int", "float":
	                    type = FilterType.NUMBER; break;
	                case "date":
	                    type = FilterType.DATE; break;
	                case "bool":
	                    type = FilterType.BOOLEAN; break;
	                default:
	                    type = FilterType.TEXT; break;
	            }
	            customFieldCache.put("cf_" + field.getId(),
	                new CustomFieldMetadata(field.getId(), field.getName(), type));
	        }
	    } catch (Exception e) {
	        throw new RuntimeException("Erro ao carregar metadados de campos personalizados: " + e.getMessage());
	    }
	}
	
	public ResultsWrapper<Issue> getIssuesByFilters(Map<String, FilterCondition> filters) {
	    try {
	        Params params = new Params()
	            .add("set_filter", "1")
	            .add("sort", "id:desc");

	        for (Map.Entry<String, FilterCondition> entry : filters.entrySet()) {
	            String field = entry.getKey();
	            FilterCondition condition = entry.getValue();

	            // Detecta tipo automaticamente se for custom field
	            if (field.startsWith("cf_") && customFieldCache.containsKey(field)) {
	                FilterType detectedType = customFieldCache.get(field).getType();
	                condition = FilterCondition.auto(detectedType, condition.getOperator(), condition.getValues());
	            }

	            params.add("f[]", field);
	            params.add("op[" + field + "]", condition.getOperator());

	            if (condition.getValues() != null) {
	                for (String value : condition.getValues()) {
	                    params.add("v[" + field + "][]", value);
	                }
	            }
	        }

	        // colunas padrão (customizável)
	        params.add("c[]", "project")
	              .add("c[]", "tracker")
	              .add("c[]", "status")
	              .add("c[]", "subject")
	              .add("c[]", "done_ratio")
	              .add("c[]", "cf_41")
	              .add("c[]", "start_date")
	              .add("c[]", "due_date")
	              .add("c[]", "assigned_to")
	              .add("c[]", "cf_30")
	              .add("t[]", "spent_hours");

	        return manager.getIssueManager().getIssues(params);

	    } catch (Exception e) {
	        throw new RuntimeException("Erro ao buscar issues: " + e.getMessage());
	    }
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
		
		//return 1L;
		
		try {
			List<Project> projects = this.manager.getProjectManager().getProjects();
			if(projects!=null) 
				return Long.valueOf(projects.size());
			return 0L;
		}catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		
	}
	
	public List<TProject> listAllProjects(){
		
		/*TProject p = new TProject();
		p.setId(1L);
		p.setName("Project 1");
		p.setIdentifier("project1");
		List<TProject> projects = new ArrayList<>();
		projects.add(p);
		return projects;*/
		
		try {
			List<Project> projects = this.manager.getProjectManager().getProjects();
			
			if(projects!=null) 
				return RedmineMapper.convertProjectList(projects);
			
			return List.of();
			
		}catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		String redmineURI = "https://redmine.detran.go.gov.br/";
        String apiAccessKey = "559147fe2183d824e7784c2862e6e0b070cd6804";
        
        RedmineApiGateway gateway = new RedmineApiGateway(redmineURI, apiAccessKey);
        
        //gateway.loadCustomFieldMetadata();

	    // Agora, criar os filtros:
	    Map<String, FilterCondition> filters = new HashMap<>();
	    filters.put("status_id", FilterCondition.notEquals("2"));
	    filters.put("assigned_to_id", FilterCondition.equalsTo("509"));
	
	    // Campo de texto personalizado
	    //filters.put("cf_41", FilterCondition.equalsTo("HPA"));
	
	    // Campo de data personalizado (auto detectado via cache)
	    /*filters.put("cf_30", FilterCondition.betweenDates(
	        LocalDate.of(2025, 10, 1),
	        LocalDate.of(2025, 10, 31)
	    ));*/

	    ResultsWrapper<Issue> issues = gateway.getIssuesByFilters(filters);
        
        //ResultsWrapper<Issue> issues = gateway.getIssues();
        
        List<Issue> projects = issues.getResults();
        projects.stream().forEach(p->{
        	
        	String pr = p.getProjectName();
        	String t = p.getTracker().getName();
        	String tit = p.getSubject();
        	String sit = p.getStatusName();
        	String att = p.getAssigneeName();
    		
        	String str = String.format("[Projeto]: %s, [Tipo]: %s, [Situação]: %s, [Titulo]: %s, [Atribuida para]: %s", pr, t, sit, tit, att);
    		
    		System.out.println(str);
    		
    	});
	}

	

}
