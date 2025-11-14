package org.tedros.redminetools.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.tedros.core.context.TedrosContext;
import org.tedros.redminetools.model.TAttachment;
import org.tedros.redminetools.model.TChangeset;
import org.tedros.redminetools.model.TCustomField;
import org.tedros.redminetools.model.TGroup;
import org.tedros.redminetools.model.TIssue;
import org.tedros.redminetools.model.TIssueCategory;
import org.tedros.redminetools.model.TIssueEvidenceInfo;
import org.tedros.redminetools.model.TIssueRelation;
import org.tedros.redminetools.model.TJournal;
import org.tedros.redminetools.model.TJournalDetail;
import org.tedros.redminetools.model.TMembership;
import org.tedros.redminetools.model.TProject;
import org.tedros.redminetools.model.TRedmineUser;
import org.tedros.redminetools.model.TRedmineVersion;
import org.tedros.redminetools.model.TRemineRole;
import org.tedros.redminetools.model.TTracker;
import org.tedros.redminetools.model.TWatcher;
import org.tedros.util.TDateUtil;

import com.taskadapter.redmineapi.bean.Attachment;
import com.taskadapter.redmineapi.bean.Changeset;
import com.taskadapter.redmineapi.bean.CustomField;
import com.taskadapter.redmineapi.bean.Group;
import com.taskadapter.redmineapi.bean.Issue;
import com.taskadapter.redmineapi.bean.IssueCategory;
import com.taskadapter.redmineapi.bean.IssueRelation;
import com.taskadapter.redmineapi.bean.Journal;
import com.taskadapter.redmineapi.bean.JournalDetail;
import com.taskadapter.redmineapi.bean.Membership;
import com.taskadapter.redmineapi.bean.Project;
import com.taskadapter.redmineapi.bean.Role;
import com.taskadapter.redmineapi.bean.Tracker;
import com.taskadapter.redmineapi.bean.User;
import com.taskadapter.redmineapi.bean.Version;
import com.taskadapter.redmineapi.bean.Watcher;

public class RedmineMapper {
	
	private static final TDateUtil util = TDateUtil.create(TedrosContext.getLocale());
	
	private RedmineMapper() {
		
	}
	
	public static List<TIssue> convertIssueList(Collection<Issue> issues) {
		
		if(issues!=null && !issues.isEmpty()) {						
			Set<Issue> lst = Set.copyOf(issues);
			return lst.stream()
					.filter(p->p!=null)
					.map(t->convert(t))
					.toList();
		}
		
		return List.of();
	}
	
	private static String convertDateToString(Date date) {
		if(date==null)
			return null;
		
		return util.format(date);
	}
	
	
	public static TIssueEvidenceInfo convertForEvidenceInfo(Issue i) {
		
		TIssueEvidenceInfo issue = new TIssueEvidenceInfo();
		
		issue.setId(Long.valueOf(i.getId()));
		issue.setSubject(i.getSubject());
		issue.setStartDate(convertDateToString(i.getStartDate()));
		issue.setDueDate(convertDateToString(i.getDueDate()));
		issue.setCreatedOn(convertDateToString(i.getCreatedOn()));
		issue.setUpdatedOn(convertDateToString(i.getUpdatedOn()));
		issue.setDoneRatio(i.getDoneRatio());
		issue.setClosedOn(convertDateToString(i.getClosedOn()));
		issue.setEstimatedHours(i.getEstimatedHours());
		issue.setSpentHours(i.getSpentHours());
		issue.setAssigneeId(i.getAssigneeId());
		issue.setAssigneeName(i.getAssigneeName());
		issue.setPriorityText(i.getPriorityText());
		issue.setProjectName(i.getProjectName());
		issue.setAuthorId(i.getAuthorId());
		issue.setAuthorName(i.getAuthorName());
		issue.setDescription(i.getDescription());
		issue.setStatusName(i.getStatusName());
		
		if(i.getCustomFields()!=null) {
			
			Optional<String> opt = i.getCustomFields().stream()
			.filter(cf->cf.getId().equals(79))
			.map(cf->cf.getValue())
			.findFirst();
			
			if(opt.isPresent()) {
				String deliverable = opt.get();
				issue.setDeliverable(deliverable);
			}
			
			opt = i.getCustomFields().stream()
					.filter(cf->cf.getId().equals(75))
					.map(cf->cf.getValue())
					.findFirst();
			
			if(opt.isPresent()) {
				String hpa = opt.get();
				issue.setHpa(hpa);
			}
			
			opt = i.getCustomFields().stream()
					.filter(cf->cf.getId().equals(60))
					.map(cf->cf.getValue())
					.findFirst();
			
			if(opt.isPresent()) {
				String serviceType = opt.get();
				issue.setServiceType(serviceType);
			}
			
			opt = i.getCustomFields().stream()
					.filter(cf->cf.getId().equals(59))
					.map(cf->cf.getValue())
					.findFirst();
			
			if(opt.isPresent()) {
				String requiredProfile = opt.get();
				issue.setRequiredProfile(requiredProfile);
			}
		}
		
		if(i.getJournals()!=null) {
			 List<String> notes = i.getJournals().stream()
					 .filter(j->StringUtils.isNotBlank(j.getNotes()))
					 .map(j->j.getNotes())
					 .toList();
			 
			 issue.setNotes(notes);
		}
		
		return issue;
	}
	
	@SuppressWarnings("deprecation")
	public static TIssue convert(Issue i) {
		
		TIssue issue = new TIssue();
		issue.setId(Long.valueOf(i.getId()));
		issue.setSubject(i.getSubject());
		issue.setStartDate(i.getStartDate());
		issue.setDueDate(i.getDueDate());
		issue.setCreatedOn(i.getCreatedOn());
		issue.setUpdatedOn(i.getUpdatedOn());
		issue.setDoneRatio(i.getDoneRatio());
		issue.setParentId(i.getParentId());
		issue.setPriorityId(i.getPriorityId());
		issue.setEstimatedHours(i.getEstimatedHours());
		issue.setSpentHours(i.getSpentHours());
		issue.setAssigneeId(i.getAssigneeId());
		issue.setAssigneeName(i.getAssigneeName());
		issue.setNotes(i.getNotes());
		//issue.setPrivateNotes(i.isPrivateNotes());
		issue.setPriorityText(i.getPriorityText());
		issue.setProjectId(i.getProjectId());
		issue.setProjectName(i.getProjectName());
		issue.setAuthorId(i.getAuthorId());
		issue.setAuthorName(i.getAuthorName());
		issue.setTracker(convert(i.getTracker()));
		issue.setDescription(i.getDescription());
		issue.setClosedOn(i.getClosedOn());
		issue.setStatusId(i.getStatusId());
		issue.setStatusName(i.getStatusName());
		issue.setTargetVersion(convert(i.getTargetVersion()));
		issue.setCategory(convert(i.getCategory()));
		issue.setPrivateIssue(i.isPrivateIssue());
		issue.setCustomFields(convertCustomFieldList(i.getCustomFields()));
		issue.setJournals(convertJournalList(i.getJournals()));
		issue.setRelations(convertIssueRelationList(i.getRelations()));
		issue.setAttachments(convertAttachmentList(i.getAttachments()));
		issue.setChangesets(convertChangesetList(i.getChangesets()));
		issue.setWatchers(convertWatchersList(i.getWatchers()));
		issue.setChildren(convertIssueList(i.getChildren()));
		
		return issue;
	}
	
	public static List<TWatcher> convertWatchersList(Collection<Watcher> watchers) {
				
		if(watchers!=null && !watchers.isEmpty()) {						
			Set<Watcher> lst = Set.copyOf(watchers);
			return lst.stream()
					.filter(p->p!=null)
					.map(t->convert(t))
					.toList();
		}
		
		return List.of();
	}
	
	public static TWatcher convert(Watcher w) {
		
		if(w==null)
			return null;
		
		TWatcher watcher = new TWatcher();
		watcher.setId(Long.valueOf(w.getId()));
		watcher.setName(w.getName());
		
		return watcher;
	}

	public static List<TChangeset> convertChangesetList(Collection<Changeset> changesets) {
				
		if(changesets!=null && !changesets.isEmpty()) {						
			Set<Changeset> lst = Set.copyOf(changesets);
			return lst.stream()
					.filter(p->p!=null)
					.map(t->convert(t))
					.toList();
		}
		
		return List.of();
	}
	
	public static TChangeset convert(Changeset c) {
		
		if(c==null)
			return null;
		
		TChangeset changeset = new TChangeset();
		changeset.setRevision(c.getRevision());
		changeset.setUser(convert(c.getUser()));
		changeset.setComments(c.getComments());
		changeset.setCommittedOn(c.getCommittedOn());
		
		return changeset;
	}

	public static List<TAttachment> convertAttachmentList(Collection<Attachment> attachments) {
		
		if(attachments!=null && !attachments.isEmpty()) {						
			Set<Attachment> lst = Set.copyOf(attachments);
			return lst.stream()
					.filter(p->p!=null)
					.map(t->convert(t))
					.toList();
		}
		return List.of();
	}
	
	public static TAttachment convert(Attachment a) {
		
		if(a==null)
			return null;
		
		TAttachment att = new TAttachment();
		att.setId(Long.valueOf(a.getId()));
		att.setFileName(a.getFileName());
		att.setFileSize(a.getFileSize());
		att.setContentType(a.getContentType());
		att.setContentURL(a.getContentURL());
		att.setDescription(a.getDescription());
		att.setCreatedOn(a.getCreatedOn());
		att.setAuthor(convert(a.getAuthor()));
		att.setToken(a.getToken());
		
		return att;
	}

	public static List<TIssueRelation> convertIssueRelationList(Collection<IssueRelation> relations) {
		
		if(relations!=null && !relations.isEmpty()) {						
			Set<IssueRelation> lst = Set.copyOf(relations);
			return lst.stream()
					.filter(p->p!=null)
					.map(t->convert(t))
					.toList();
		}
		
		return List.of();
	}
	
	private static TIssueRelation convert(IssueRelation r) {
		
		if(r==null)
			return null;
		
		TIssueRelation rel = new TIssueRelation();
		rel.setId(Long.valueOf(r.getId()));
		rel.setIssueId(r.getIssueId());
		rel.setIssueToId(r.getIssueToId());
		rel.setType(r.getType());
		rel.setDelay(r.getDelay());
		
		return rel;
	}

	public static List<TJournal> convertJournalList(Collection<Journal> journals) {
		
		if(journals!=null && !journals.isEmpty()) {						
			Set<Journal> lst = Set.copyOf(journals);
			return lst.stream()
					.map(t->convert(t))
					.toList();
		}
		return List.of();
	}

	private static TJournal convert(Journal j) {
		
		if(j==null)
			return null;
		
		TJournal journal = new TJournal();
		journal.setId(Long.valueOf(j.getId()));
		journal.setNotes(j.getNotes());
		journal.setUser(convert(j.getUser()));
		journal.setCreatedOn(j.getCreatedOn());
		journal.setDetails(convertJournalDetailList(j.getDetails()));
		
		return journal;
	}

	public static List<TJournalDetail> convertJournalDetailList(List<JournalDetail> details) {
				
		if(details!=null && !details.isEmpty()) {						
			Set<JournalDetail> lst = Set.copyOf(details);
			return lst.stream()
					.filter(p->p!=null)
					.map(t->convert(t))
					.toList();
		}
		return List.of();
	}
	
	public static TJournalDetail convert(JournalDetail d) {
		
		if(d==null)
			return null;
		
		TJournalDetail detail = new TJournalDetail();
		detail.setNewValue(d.getNewValue());
		detail.setName(d.getName());
		detail.setProperty(d.getProperty());
		detail.setOldValue(d.getOldValue());
		
		return detail;
	}
	
	public static List<TRedmineUser> convertUserList(Collection<User> users) {
		
		if(users!=null && !users.isEmpty()) {						
			Set<User> lst = Set.copyOf(users);
			return lst.stream()
					.filter(p->p!=null)
					.map(t->convert(t))
					.toList();
		}
		
		return List.of();
	}

	@SuppressWarnings("deprecation")
	public static TRedmineUser convert(User u) {
		
		if(u==null)
			return null;
		
		TRedmineUser user = new TRedmineUser();
		user.setId(Long.valueOf(u.getId()));
		user.setLogin(u.getLogin());
		user.setPassword(u.getPassword());
		user.setFirstName(u.getFirstName());
		user.setLastName(u.getLastName());
		user.setMail(u.getMail());
		user.setApiKey(u.getApiKey());		
		user.setCreatedOn(u.getCreatedOn());
		user.setLastLoginOn(u.getLastLoginOn());
		user.setAuthSourceId(u.getAuthSourceId());
		user.setStatus(u.getStatus());
		user.setCustomFields(convertCustomFieldList(u.getCustomFields()));
		user.setMemberships(convertMembershipList(u.getMemberships()));
		user.setGroups(convertGroupList(u.getGroups()));
		
		return user;
	}

	public static List<TGroup> convertGroupList(Collection<Group> groups) {
				
		if(groups!=null && !groups.isEmpty()) {						
			Set<Group> lst = Set.copyOf(groups);
			return lst.stream()
					.filter(p->p!=null)
					.map(t->convert(t))
					.toList();
		}
		
		return List.of();
	}
	
	public static TGroup convert(Group g) {
		
		if(g==null) 
			return null;
		
		TGroup group = new TGroup();
		group.setId(Long.valueOf(g.getId()));
		group.setName(g.getName());
		
		return group;
	}

	public static List<TMembership> convertMembershipList(Collection<Membership> memberships) {
		
		if(memberships!=null && !memberships.isEmpty()) {						
			Set<Membership> lst = Set.copyOf(memberships);
			return lst.stream()
					.filter(p->p!=null)
					.map(t->convert(t))
					.toList();
		}
		
		return List.of();
	}

	public static TMembership convert(Membership m) {
		
		if(m==null) 
			return null;		
		
		TMembership mem = new TMembership();
		mem.setId(Long.valueOf(m.getId()));
		mem.setProject(convert(m.getProject()));
		mem.setUserId(m.getUserId());
		mem.setUserName(m.getUserName());
		mem.setGroupId(m.getGroupId());
		mem.setGroupName(m.getGroupName());
		mem.setRoles(convertRoleList(m.getRoles()));
		return mem;
	}

	public static List<TRemineRole> convertRoleList(Collection<Role> roles) {
		
		if(roles!=null && !roles.isEmpty()) {						
			Set<Role> lst = Set.copyOf(roles);
			return lst.stream()
					.filter(p->p!=null)
					.map(t->convert(t))
					.toList();
		}
		
		return List.of();
	}

	public static TRemineRole convert(Role r) {
		
		if(r==null)
			return null;
		
		TRemineRole role = new TRemineRole();
		role.setId(Long.valueOf(r.getId()));
		role.setName(r.getName());
		role.setInherited(r.getInherited());
		role.setPermissions(convertPermissionList(r.getPermissions()));
		return role;
	}

	public static List<String> convertPermissionList(Collection<String> permissions) {
		
		if(permissions!=null && !permissions.isEmpty()) {						
			Set<String> lst = Set.copyOf(permissions);
			return new ArrayList<>(lst);
		}
		return List.of();
	}

	public static TIssueCategory convert(IssueCategory category) {
				
		if(category==null) 
			return null;
		
		TIssueCategory cat = new TIssueCategory();
		cat.setId(Long.valueOf(category.getId()));
		cat.setName(category.getName());
		cat.setProjectId(category.getProjectId());
		cat.setAssigneeId(category.getAssigneeId());
		cat.setAssigneeName(category.getAssigneeName());
		
		return cat;
	}

	public static TRedmineVersion convert(Version v) {
		
		if(v==null)
			return null;		
		
		TRedmineVersion version = new TRedmineVersion();
		version.setId(Long.valueOf(v.getId()));
		version.setProjectName(v.getProjectName());		
		version.setName(v.getName());
		version.setDescription(v.getDescription());
		version.setStatus(v.getStatus());
		version.setSharing(v.getSharing());
		version.setDueDate(v.getDueDate());
		version.setCreatedOn(v.getCreatedOn());
		version.setUpdatedOn(v.getUpdatedOn());
		
		return version;
	}
	
	public static List<TProject> convertProjectList(Collection<Project> projects) {
		
		if(projects!=null && !projects.isEmpty()) {						
			Set<Project> lst = Set.copyOf(projects);
			return lst.stream()
					.filter(p->p!=null)
					.map(t->convert(t))
					.toList();
		}
		
		return List.of();
	}

	@SuppressWarnings("deprecation")
	public static TProject convert(Project p) {
		
		if(p==null)
			return null;
		
		TProject proj = new TProject();
		proj.setId(Long.valueOf(p.getId()));
		proj.setDescription(p.getDescription());
		proj.setHomepage(p.getHomepage());
		proj.setIdentifier(p.getIdentifier());
		proj.setName(p.getName());
		proj.setCreatedOn(p.getCreatedOn());
		proj.setUpdatedOn(p.getUpdatedOn());
		proj.setPublicProject(p.getProjectPublic());
		proj.setInheritMembers(p.getInheritMembers());
		proj.setParentId(p.getParentId());
		proj.setTrackers(convertTrackerList(p.getTrackers()));
		proj.setCustomFields(convertCustomFieldList(p.getCustomFields()));
		
		return proj;
	}
	
	public static List<TCustomField> convertCustomFieldList(Collection<CustomField> customFields) {
		if(customFields!=null && !customFields.isEmpty()) {						
			Set<CustomField> lst = Set.copyOf(customFields);
			return lst.stream()
					.filter(p->p!=null)
					.map(t->convert(t))
					.toList();
		}
		
		return List.of();
	}

	public static TCustomField convert(CustomField c) {
				
		if(c==null)
			return null;
		
		TCustomField field = new TCustomField();
		field.setId(Long.valueOf(c.getId()));
		field.setName(c.getName());
		field.setValue(c.getValue());
		field.setMultiple(c.isMultiple());
		
		if(c.getValues()!=null)
			field.setValues(new ArrayList<>(c.getValues()));
		
		return field;
	}
	
	public static List<TTracker> convertTrackerList(Collection<Tracker> trackers) {
		
		if(trackers!=null && !trackers.isEmpty()) {						
			Set<Tracker> lst = Set.copyOf(trackers);
			return lst.stream()
					.filter(p->p!=null)
					.map(t->convert(t))
					.toList();
		}
		
		return List.of();
	}

	public static TTracker convert(Tracker t) {
		
		if(t==null)
			return null;
		
		TTracker tk = new TTracker();
		tk.setId(Long.valueOf(t.getId()));
		tk.setName(t.getName());
		
		return tk;
	}

}
