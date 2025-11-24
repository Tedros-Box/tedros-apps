package org.tedros.redminetools.gateway;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.tedros.common.model.TFileContentInfo;
import org.tedros.redminetools.mapper.RedmineMapper;
import org.tedros.redminetools.model.TAttachment;
import org.tedros.redminetools.model.TCustomField;
import org.tedros.redminetools.model.TIssue;
import org.tedros.redminetools.model.TIssueEvidenceInfo;
import org.tedros.redminetools.model.TMembership;
import org.tedros.redminetools.model.TProject;
import org.tedros.redminetools.model.TRedmineUser;
import org.tedros.util.TedrosFolder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskadapter.redmineapi.Include;
import com.taskadapter.redmineapi.Params;
import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.RedmineManagerFactory;
import com.taskadapter.redmineapi.bean.Attachment;
import com.taskadapter.redmineapi.bean.CustomFieldDefinition;
import com.taskadapter.redmineapi.bean.Issue;
import com.taskadapter.redmineapi.bean.Membership;
import com.taskadapter.redmineapi.bean.Project;
import com.taskadapter.redmineapi.bean.User;
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
	
	public List<TMembership> getProjectMembers(String projectKey){		
		List<Membership> members;
		try {
			members = manager.getProjectManager().getProjectMembers(projectKey);
			return RedmineMapper.convertMembershipList(members);
		} catch (RedmineException e) {
			throw new RuntimeException("Erro ao carregar os membros do projeto: " + e.getMessage());
		}
	}
	
	public List<TIssue> getIssuesByFilters(Map<String, FilterCondition> filters) {
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
	       /*params.add("c[]", "project")
	              .add("c[]", "tracker")
	              .add("c[]", "status")
	              .add("c[]", "subject")
	              .add("c[]", "done_ratio")
	              .add("c[]", "cf_41")
	              .add("c[]", "start_date")
	              .add("c[]", "due_date")
	              .add("c[]", "assigned_to")
	              .add("c[]", "cf_30")
	              .add("t[]", "spent_hours")
	              .add("c[]", "notes")
	              .add("c[]", "cf_41")
	              .add("c[]", "cf_59")
	              .add("c[]", "cf_60")
	              .add("c[]", "cf_75")
	              .add("c[]", "cf_79")
	              .add("c[]", "issue_id")	       
	              .add("c[]", "attachments");*/
	        
	        ResultsWrapper<Issue> wrapper = manager.getIssueManager().getIssues(params); 

	        return wrapper != null && wrapper.hasSomeResults()
	            ? RedmineMapper.convertIssueList(wrapper.getResults())
	            : List.of();

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
	
	public TIssueEvidenceInfo getTIssueEvidenceInfo(Integer issueId){
		
		try {
			Issue issue = this.manager.getIssueManager().getIssueById(issueId, Include.values());
			return RedmineMapper.convertForEvidenceInfo(issue);
			
		}catch (Exception e) {
			throw new RuntimeException(e);
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
		
		try {
			List<Project> projects = this.manager.getProjectManager().getProjects();
			
			if(projects!=null) 
				return RedmineMapper.convertProjectList(projects);
			
			return List.of();
			
		}catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public List<TRedmineUser> findUser(String name){
		
		try {
			ResultsWrapper<User> wrapper = this.manager.getUserManager().getUsers(Map.of("name", name));
			
			if(wrapper!=null) 
				return RedmineMapper.convertUserList(wrapper.getResults());
			
			return List.of();
			
		}catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	
	public List<String> getAttachments(Collection<Attachment> attachments) {
		return attachments.stream()
				.map(this::getAttachment)
				.toList();
	}
	
	public String getAttachment(Attachment attachment) {
		
		String dir = TedrosFolder.EXPORT_FOLDER.getFullPath();
		String path = dir+attachment.getFileName();
		File f = new File(path);
		try(OutputStream out = new FileOutputStream(f)) {
			this.manager.getAttachmentManager().downloadAttachmentContent(attachment, out);
			return path;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<TFileContentInfo> dowloadAttachments(Collection<Attachment> attachments) {
		return attachments.stream()
				.map(this::getAttachmentInfo)
				.toList();
	}
	
	public List<TFileContentInfo> dowloadTAttachments(Collection<TAttachment> attachments) {
		return attachments.stream()
				.map(this::getAttachmentInfo)
				.toList();
	}
	
	public TFileContentInfo getAttachmentInfo(TAttachment attachment) {
		Attachment att = RedmineMapper.convert(attachment);
		return getAttachmentInfo(att);
	}
	
	public TFileContentInfo getAttachmentInfo(Attachment attachment) {
		String fileName = attachment.getFileName();
		String contentType = attachment.getContentType();
		try {
			byte[] bytes = this.manager.getAttachmentManager().downloadAttachmentContent(attachment);
			return new TFileContentInfo(fileName, contentType, bytes);
		} catch (RedmineException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		//String redmineURI = "http://localhost:8080/";
		String redmineURI = "https://redmine.detran.go.gov.br/";
        String apiAccessKey = "key";
        
        RedmineApiGateway gateway = new RedmineApiGateway(redmineURI, apiAccessKey);
        
        TIssueEvidenceInfo issue = gateway.getTIssueEvidenceInfo(214212);
        
        
        try {
        	 System.out.println(new ObjectMapper().writeValueAsString(issue));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        System.out.println("***********************");
        
        TAttachment att = new TAttachment();
        //att.setId(issue.getAttachments().get(0).getId());
        att.setContentURL(issue.getAttachments().get(0).getContentURL());
        //att.setId(issue.getAttachments().get(0).getId());
        
        System.out.println(gateway.dowloadTAttachments(List.of(att)));
        
        //System.out.println(gateway.findUser("davis"));
        
        //gateway.loadCustomFieldMetadata();

	    
	
	    // Campo de texto personalizado
	    //filters.put("cf_41", FilterCondition.equalsTo("HPA"));
	
	    // Campo de data personalizado (auto detectado via cache)
	    /*filters.put("cf_30", FilterCondition.betweenDates(
	        LocalDate.of(2025, 10, 1),
	        LocalDate.of(2025, 10, 31)
	    ));*/

        //ResultsWrapper<Issue> issues = gateway.getIssues();
       // testFilterIssues(gateway);
	}

	private static void testFilterIssues(RedmineApiGateway gateway) {
		
		// Agora, criar os filtros:
	    Map<String, FilterCondition> filters = new HashMap<>();
	    //filters.put("status_id", FilterCondition.equalsTo("2"));
	    filters.put("assigned_to_id", FilterCondition.equalsTo("509"));
	    
		int x = 1;
        List<TIssue> projects = gateway.getIssuesByFilters(filters);
        for(TIssue p : projects){
        	
        	String pr = p.getProjectName();
        	String t = p.getTracker().getName();
        	String tit = p.getSubject();
        	String sit = p.getStatusName();
        	String att = p.getAssigneeName();
        	//Date dtIni = p.getStartDate().
        	
        	Optional<TCustomField> opt = p.getCustomFields().stream().filter(c->c.getName().equals("HPA")).findFirst();
        	
        	String hpa = opt.isPresent() 
        			? opt.get().getValue()
        					: "";
    		
        	String str = String.format("%s) [Projeto]: %s, [Tipo]: %s, [Situação]: %s, [Titulo]: %s, [Atribuida para]: %s, [HPA] %s", x, pr, t, sit, tit, att, hpa);
    		System.out.println(str);
    		x++;
    		
    	}
	}

	

}
