package org.tedros.it.tools.redmine.gateway;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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
import org.tedros.it.tools.redmine.api.model.TIssueStatus;
import org.tedros.it.tools.redmine.api.model.TMembership;
import org.tedros.it.tools.redmine.api.model.TProject;
import org.tedros.it.tools.redmine.api.model.TRedmineUser;
import org.tedros.it.tools.redmine.api.model.TTimeEntry;
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
import com.taskadapter.redmineapi.bean.IssueStatus;
import com.taskadapter.redmineapi.bean.Membership;
import com.taskadapter.redmineapi.bean.Project;
import com.taskadapter.redmineapi.bean.TimeEntry;
import com.taskadapter.redmineapi.bean.User;
import com.taskadapter.redmineapi.internal.ResultsWrapper;

public class RedmineApiGateway {

	private String uri;
	private RedmineManager manager;
	private Map<String, CustomFieldMetadata> customFieldCache = new HashMap<>();

	public RedmineApiGateway(String uri, String apiAccessKey) {
		this.uri = uri;
		this.manager = RedmineManagerFactory.createWithApiKey(uri, apiAccessKey);
		this.manager.setObjectsPerPage(100);
	}

	public void loadCustomFieldMetadata() {
		try {
			List<CustomFieldDefinition> fields = manager.getCustomFieldManager().getCustomFieldDefinitions();
			for (CustomFieldDefinition field : fields) {
				FilterType type;
				switch (field.getFieldFormat()) {
					case "int", "float":
						type = FilterType.NUMBER;
						break;
					case "date":
						type = FilterType.DATE;
						break;
					case "bool":
						type = FilterType.BOOLEAN;
						break;
					default:
						type = FilterType.TEXT;
						break;
				}
				customFieldCache.put("cf_" + field.getId(),
						new CustomFieldMetadata(field.getId(), field.getName(), type));
			}
		} catch (Exception e) {
			throw new RuntimeException("Erro ao carregar metadados de campos personalizados: " + e.getMessage());
		}
	}

	public List<TTimeEntry> getTimeEntriesForIssue(Integer issueId) {
		try {
			List<TimeEntry> entries = this.manager.getTimeEntryManager().getTimeEntriesForIssue(issueId);
			if (entries != null)
				return RedmineMapper.convertTimeEntryList(entries);
			return List.of();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public List<TIssueStatus> listIssueStatuses() {

		try {
			List<IssueStatus> statuses = this.manager.getIssueManager().getStatuses();

			if (statuses != null)
				return RedmineMapper.convertIssueStatusList(statuses);

			return List.of();

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public void addIssueJournal(Integer userId, Integer issueId) {

		try {
			User user = manager.getUserManager().getUserById(userId);
			String userApiKey = user.getApiKey();
			Issue issue = manager.getIssueManager().getIssueById(issueId, Include.journals);
			// Journal journal = JournalFactory.create(null, "Automated journal entry",
			// user, new Date());
			issue.setNotes("Test");

			RedmineManagerFactory.createWithApiKey(uri, userApiKey).getIssueManager().update(issue);

		} catch (RedmineException e) {
			throw new RuntimeException("Erro ao atualuzar issue: " + e.getMessage());
		}
	}

	public List<TMembership> getProjectMembers(String projectKey) {
		List<Membership> members;
		try {
			members = manager.getProjectManager().getProjectMembers(projectKey);
			return RedmineMapper.convertMembershipList(members);
		} catch (RedmineException e) {
			throw new RuntimeException("Erro ao carregar os membros do projeto: " + e.getMessage());
		}
	}

	public List<TIssueEvidenceInfo> getIssuesByFilters(Map<String, FilterCondition> filters) {
		List<TIssueEvidenceInfo> todasIssues = new ArrayList<>();
		int limit = 100; // Máximo permitido pelo Redmine geralmente é 100
		int offset = 0;
		int totalEncontrado = 0;

		try {
			// 1. Configuração inicial dos Parâmetros (Filtros fixos e dinâmicos)
			Params params = new Params()
					.add("set_filter", "1")
					.add("sort", "id:desc")
					.add("limit", String.valueOf(limit)); // Define o tamanho da página

			// Aplica os filtros do mapa
			for (Map.Entry<String, FilterCondition> entry : filters.entrySet()) {
				String field = entry.getKey();
				FilterCondition condition = entry.getValue();

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

			// 2. Loop de Paginação
			do {
				// Atualiza o offset para a próxima página
				// Nota: Certifique-se que sua classe Params atualiza o valor se a chave já
				// existir,
				// ou use um método .replace() se disponível.
				params.add("offset", String.valueOf(offset));

				// Chamada à API
				ResultsWrapper<Issue> wrapper = manager.getIssueManager().getIssues(params);

				// Se não houver resultados ou wrapper for nulo, encerra
				if (wrapper == null || !wrapper.hasSomeResults()) {
					break;
				}

				// Pega o total disponível no servidor (metadado da resposta)
				totalEncontrado = wrapper.getTotalFoundOnServer();

				// Converte e adiciona à lista acumulada
				List<Issue> paginaAtual = wrapper.getResults();
				if (!paginaAtual.isEmpty()) {
					paginaAtual.forEach(issue -> todasIssues.add(RedmineMapper.convertForEvidenceInfo(issue)));
				}

				// Incrementa o offset com a quantidade real retornada nesta página
				offset += paginaAtual.size();

			} while (offset < totalEncontrado);

			return todasIssues;

		} catch (Exception e) {
			throw new RuntimeException("Erro ao buscar issues paginadas: " + e.getMessage(), e);
		}
	}

	public TIssue getIssuesById(Integer issueId) {

		try {
			Issue issue = this.manager.getIssueManager().getIssueById(issueId, Include.values());

			if (issue != null)
				return RedmineMapper.convert(issue);

			return null;

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public TIssueEvidenceInfo getTIssueEvidenceInfo(Integer issueId) {

		try {
			Issue issue = this.manager.getIssueManager().getIssueById(issueId, Include.values());
			return RedmineMapper.convertForEvidenceInfo(issue);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<TIssue> getIssuesBySummary(String projectId, String summary) {

		try {
			List<Issue> issues = this.manager.getIssueManager().getIssuesBySummary(projectId, summary);

			if (issues != null)
				return RedmineMapper.convertIssueList(issues);

			return null;

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public TProject getProjectByIdentifier(String identifier) {

		try {
			Project project = this.manager.getProjectManager().getProjectByKey(identifier);

			if (project != null)
				return RedmineMapper.convert(project);

			return null;

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public TProject getProjectById(int idProject) {

		try {
			Project project = this.manager.getProjectManager().getProjectById(idProject);

			if (project != null)
				return RedmineMapper.convert(project);

			return null;

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public Long countAllProjects() {

		try {
			List<Project> projects = this.manager.getProjectManager().getProjects();
			if (projects != null)
				return Long.valueOf(projects.size());
			return 0L;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	public List<TProject> listAllProjects() {

		try {
			List<Project> projects = this.manager.getProjectManager().getProjects();

			if (projects != null)
				return RedmineMapper.convertProjectList(projects);

			return List.of();

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public List<TRedmineUser> findUser(String name) {

		try {
			ResultsWrapper<User> wrapper = this.manager.getUserManager().getUsers(Map.of("name", name));

			if (wrapper != null)
				return RedmineMapper.convertUserList(wrapper.getResults());

			return List.of();

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public List<TRedmineUser> listUsers() {
		try {
			// Fetching default page of users.
			// WARNING: fetching all users in a large instance might be slow.
			// Currently fetching 100 as per manager configuration.
			List<User> users = this.manager.getUserManager().getUsers();
			if (users != null)
				return RedmineMapper.convertUserList(users);
			return List.of();
		} catch (Exception e) {
			throw new RuntimeException("Error fetching users: " + e.getMessage());
		}
	}

	public List<String> getAttachments(Collection<Attachment> attachments) {
		return attachments.stream()
				.map(this::getAttachment)
				.toList();
	}

	public String getAttachment(Attachment attachment) {

		String dir = TedrosFolder.EXPORT_FOLDER.getFullPath();
		String path = dir + attachment.getFileName();
		File f = new File(path);
		try (OutputStream out = new FileOutputStream(f)) {
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
