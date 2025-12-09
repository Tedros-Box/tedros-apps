package org.tedros.it.tools.redmine.gateway;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tedros.common.model.TFileContentInfo;
import org.tedros.it.tools.redmine.ai.model.CustomFieldMetadata;
import org.tedros.it.tools.redmine.ai.model.FilterCondition;
import org.tedros.it.tools.redmine.ai.model.FilterType;
import org.tedros.it.tools.redmine.api.model.TAttachment;
import org.tedros.it.tools.redmine.api.model.TIssue;
import org.tedros.it.tools.redmine.api.model.TIssueEvidenceInfo;
import org.tedros.it.tools.redmine.api.model.TMembership;
import org.tedros.it.tools.redmine.api.model.TProject;
import org.tedros.it.tools.redmine.api.model.TRedmineUser;
import org.tedros.it.tools.redmine.mapper.RedmineMapper;
import org.tedros.util.TedrosFolder;

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
	
	private String uri;
	private RedmineManager manager;
	private Map<String, CustomFieldMetadata> customFieldCache = new HashMap<>();

	public RedmineApiGateway(String uri, String apiAccessKey) {
		this.uri = uri;
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
	
	public void addIssueJournal(Integer userId, Integer issueId) {
		
		try {
			User user = manager.getUserManager().getUserById(userId);
			String userApiKey = user.getApiKey();
			Issue issue = manager.getIssueManager().getIssueById(issueId, Include.journals);
			//Journal journal = JournalFactory.create(null, "Automated journal entry", user, new Date());
			issue.setNotes("Test");
			
			RedmineManagerFactory.createWithApiKey(uri, userApiKey).getIssueManager().update(issue);
			
		} catch (RedmineException e) {			
			throw new RuntimeException("Erro ao atualuzar issue: " + e.getMessage());
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

}
