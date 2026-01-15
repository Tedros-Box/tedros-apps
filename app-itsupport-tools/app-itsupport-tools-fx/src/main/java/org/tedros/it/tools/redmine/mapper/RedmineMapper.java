package org.tedros.it.tools.redmine.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.tedros.core.context.TedrosContext;
import org.tedros.it.tools.redmine.api.model.TAttachment;
import org.tedros.it.tools.redmine.api.model.TChangeset;
import org.tedros.it.tools.redmine.api.model.TCustomField;
import org.tedros.it.tools.redmine.api.model.TGroup;
import org.tedros.it.tools.redmine.api.model.TIssue;
import org.tedros.it.tools.redmine.api.model.TIssueCategory;
import org.tedros.it.tools.redmine.api.model.TIssueEvidenceInfo;
import org.tedros.it.tools.redmine.api.model.TIssueRelation;
import org.tedros.it.tools.redmine.api.model.TIssueStatus;
import org.tedros.it.tools.redmine.api.model.TJournal;
import org.tedros.it.tools.redmine.api.model.TJournalDetail;
import org.tedros.it.tools.redmine.api.model.TMembership;
import org.tedros.it.tools.redmine.api.model.TProject;
import org.tedros.it.tools.redmine.api.model.TRedmineRole;
import org.tedros.it.tools.redmine.api.model.TRedmineUser;
import org.tedros.it.tools.redmine.api.model.TRedmineVersion;
import org.tedros.it.tools.redmine.api.model.TTimeEntry;
import org.tedros.it.tools.redmine.api.model.TTracker;
import org.tedros.it.tools.redmine.api.model.TWatcher;
import org.tedros.util.TDateUtil;

import com.taskadapter.redmineapi.bean.Attachment;
import com.taskadapter.redmineapi.bean.AttachmentFactory;
import com.taskadapter.redmineapi.bean.Changeset;
import com.taskadapter.redmineapi.bean.CustomField;
import com.taskadapter.redmineapi.bean.CustomFieldFactory;
import com.taskadapter.redmineapi.bean.Group;
import com.taskadapter.redmineapi.bean.GroupFactory;
import com.taskadapter.redmineapi.bean.Issue;
import com.taskadapter.redmineapi.bean.IssueCategory;
import com.taskadapter.redmineapi.bean.IssueCategoryFactory;
import com.taskadapter.redmineapi.bean.IssueFactory;
import com.taskadapter.redmineapi.bean.IssueRelation;
import com.taskadapter.redmineapi.bean.IssueRelationFactory;
import com.taskadapter.redmineapi.bean.IssueStatus;
import com.taskadapter.redmineapi.bean.Journal;
import com.taskadapter.redmineapi.bean.JournalDetail;
import com.taskadapter.redmineapi.bean.JournalFactory;
import com.taskadapter.redmineapi.bean.Membership;
import com.taskadapter.redmineapi.bean.MembershipFactory;
import com.taskadapter.redmineapi.bean.Project;
import com.taskadapter.redmineapi.bean.ProjectFactory;
import com.taskadapter.redmineapi.bean.Role;
import com.taskadapter.redmineapi.bean.RoleFactory;
import com.taskadapter.redmineapi.bean.TimeEntry;
import com.taskadapter.redmineapi.bean.Tracker;
import com.taskadapter.redmineapi.bean.TrackerFactory;
import com.taskadapter.redmineapi.bean.User;
import com.taskadapter.redmineapi.bean.UserFactory;
import com.taskadapter.redmineapi.bean.Version;
import com.taskadapter.redmineapi.bean.VersionFactory;
import com.taskadapter.redmineapi.bean.Watcher;
import com.taskadapter.redmineapi.bean.WatcherFactory;

public class RedmineMapper {
	
	private static final TDateUtil util = TDateUtil.create(TedrosContext.getLocale());
	
	private RedmineMapper() {
		
	}
	
	private static String convertDateToString(Date date) {
		if(date==null)
			return null;
		
		return util.format(date);
	}
	
	public static List<TTimeEntry> convertTimeEntryList(Collection<TimeEntry> entries) {
		
		if(entries!=null && !entries.isEmpty()) {						
			Set<TimeEntry> lst = Set.copyOf(entries);
			return lst.stream()
					.filter(p->p!=null)
					.map(t->convert(t))
					.toList();
		}
		
		return List.of();
	}
	
	public static TTimeEntry convert(TimeEntry entry) {
		if(entry==null)
			return null;
		
		TTimeEntry te = new TTimeEntry();
		te.setId(entry.getId());
		te.setIssueId(entry.getIssueId());
		te.setProjectId(entry.getProjectId());
		te.setProjectName(entry.getProjectName());
		te.setUserName(entry.getUserName());
		te.setUserId(entry.getUserId());
		te.setActivityName(entry.getActivityName());
		te.setActivityId(entry.getActivityId());
		te.setHours(entry.getHours());
		te.setComment(entry.getComment());
		te.setSpentOn(entry.getSpentOn());
		te.setCreatedOn(entry.getCreatedOn());
		te.setUpdatedOn(entry.getUpdatedOn());
		te.setCustomFields(convertCustomFieldList(entry.getCustomFields()));
		
		return te;
		
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
		
		if(i.getAttachments()!=null) {
			 List<TAttachment> atts = convertAttachmentList(i.getAttachments());
			 issue.setAttachments(atts);
		}
		
		return issue;
	}
	
	public static List<TIssueStatus> convertIssueStatusList(Collection<IssueStatus> statuses) {
		if(statuses!=null && !statuses.isEmpty()) {						
			Set<IssueStatus> lst = Set.copyOf(statuses);
			return lst.stream()
					.filter(p->p!=null)
					.map(t->convert(t))
					.toList();
		}
		
		return List.of();
	}
	
	public static TIssueStatus convert(IssueStatus status) {
		if(status==null)
			return null;		
		TIssueStatus s = new TIssueStatus();
		s.setId(status.getId());
		s.setName(status.getName());
		s.setClosed(status.isClosed());
		s.setDefaultStatus(status.isDefaultStatus());		
		return s;
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
	
	@SuppressWarnings("deprecation")
	public static TIssue convert(Issue i) {
		
		TIssue issue = new TIssue();
		issue.setId(i.getId());
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
		try { issue.setPrivateNotes(i.isPrivateNotes()); } catch(Exception e) {}
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
		try { issue.setPrivateIssue(i.isPrivateIssue()); } catch(Exception e) {}
		issue.setCustomFields(convertCustomFieldList(i.getCustomFields()));
		issue.setJournals(convertJournalList(i.getJournals()));
		issue.setRelations(convertIssueRelationList(i.getRelations()));
		issue.setAttachments(convertAttachmentList(i.getAttachments()));
		issue.setChangesets(convertChangesetList(i.getChangesets()));
		issue.setWatchers(convertWatchersList(i.getWatchers()));
		issue.setChildren(convertIssueList(i.getChildren()));
		
		return issue;
	}
	
	public static List<Issue> convertTIssueList(Collection<TIssue> issues) {
		
		if(issues!=null && !issues.isEmpty()) {						
			Set<TIssue> lst = Set.copyOf(issues);
			return lst.stream()
					.filter(p->p!=null)
					.map(t->convert(t))
					.toList();
		}
		
		return List.of();
	}
	
	@SuppressWarnings("deprecation")
	public static Issue convert(TIssue i) {
		
		Issue issue = IssueFactory.create(i.getId());
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
		issue.setPrivateNotes(i.getPrivateNotes());
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
		issue.setPrivateIssue(i.getPrivateIssue());
		issue.addCustomFields(convertTCustomFieldList(i.getCustomFields()));
		issue.addJournals(convertTJournalList(i.getJournals()));
		issue.addRelations(convertTIssueRelationList(i.getRelations()));
		issue.addAttachments(convertTAttachmentList(i.getAttachments()));
		issue.addChangesets(convertTChangesetList(i.getChangesets()));
		issue.addWatchers(convertTWatchersList(i.getWatchers()));
		issue.addChildren(convertTIssueList(i.getChildren()));
		
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
		watcher.setId(w.getId());
		watcher.setName(w.getName());
		
		return watcher;
	}
	
	public static List<Watcher> convertTWatchersList(Collection<TWatcher> watchers) {
		
		if(watchers!=null && !watchers.isEmpty()) {						
			Set<TWatcher> lst = Set.copyOf(watchers);
			return lst.stream()
					.filter(p->p!=null)
					.map(t->convert(t))
					.toList();
		}
		
		return List.of();
	}
	
	public static Watcher convert(TWatcher w) {
		
		if(w==null)
			return null;
		
		Watcher watcher = WatcherFactory.create(w.getId());
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
	
	public static List<Changeset> convertTChangesetList(Collection<TChangeset> changesets) {
		
		if(changesets!=null && !changesets.isEmpty()) {						
			Set<TChangeset> lst = Set.copyOf(changesets);
			return lst.stream()
					.filter(p->p!=null)
					.map(t->convert(t))
					.toList();
		}
		
		return List.of();
	}
	
	public static Changeset convert(TChangeset c) {
		
		if(c==null)
			return null;
		
		Changeset changeset = new Changeset();
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
		att.setId(a.getId());
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
	
	public static List<Attachment> convertTAttachmentList(Collection<TAttachment> attachments) {
		
		if(attachments!=null && !attachments.isEmpty()) {						
			Set<TAttachment> lst = Set.copyOf(attachments);
			return lst.stream()
					.filter(p->p!=null)
					.map(t->convert(t))
					.toList();
		}
		return List.of();
	}
	
	public static Attachment convert(TAttachment a) {	
		
		if(a==null)
			return null;
		
		Attachment att = AttachmentFactory.create(a.getId());
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
	
	public static TIssueRelation convert(IssueRelation r) {
		
		if(r==null)
			return null;
		
		TIssueRelation rel = new TIssueRelation();
		rel.setId(r.getId());
		rel.setIssueId(r.getIssueId());
		rel.setIssueToId(r.getIssueToId());
		rel.setType(r.getType());
		rel.setDelay(r.getDelay());
		
		return rel;
	}
	
	public static List<IssueRelation> convertTIssueRelationList(Collection<TIssueRelation> relations) {
		
		if(relations!=null && !relations.isEmpty()) {						
			Set<TIssueRelation> lst = Set.copyOf(relations);
			return lst.stream()
					.filter(p->p!=null)
					.map(t->convert(t))
					.toList();
		}
		
		return List.of();
	}
	
	public static IssueRelation convert(TIssueRelation r) {
		
		if(r==null)
			return null;
		
		IssueRelation rel = IssueRelationFactory.create(r.getId());
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
		journal.setId(j.getId());
		journal.setNotes(j.getNotes());
		journal.setUser(convert(j.getUser()));
		journal.setCreatedOn(j.getCreatedOn());
		journal.setDetails(convertJournalDetailList(j.getDetails()));
		
		return journal;
	}
	
	public static List<Journal> convertTJournalList(Collection<TJournal> journals) {
		
		if(journals!=null && !journals.isEmpty()) {						
			Set<TJournal> lst = Set.copyOf(journals);
			return lst.stream()
					.map(t->convert(t))
					.toList();
		}
		return List.of();
	}
	
	private static Journal convert(TJournal j) {
		
		if(j==null)
			return null;
		
		Journal journal = JournalFactory.create(j.getId());
		journal.setNotes(j.getNotes());
		journal.setUser(convert(j.getUser()));
		journal.setCreatedOn(j.getCreatedOn());
		journal.addDetails(convertTJournalDetailList(j.getDetails()));
		
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
	
	public static List<JournalDetail> convertTJournalDetailList(List<TJournalDetail> details) {
		
		if(details!=null && !details.isEmpty()) {						
			Set<TJournalDetail> lst = Set.copyOf(details);
			return lst.stream()
					.filter(p->p!=null)
					.map(t->convert(t))
					.toList();
		}
		return List.of();
	}
	
	public static JournalDetail convert(TJournalDetail d) {
		
		if(d==null)
			return null;
		
		JournalDetail detail = new JournalDetail();
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
		user.setId(u.getId());
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
	
	public static User convert(TRedmineUser u) {
		
		if(u==null)
			return null;
		
		User user = UserFactory.create(u.getId());
		user.setLogin(u.getLogin());
		user.setPassword(u.getPassword());
		user.setFirstName(u.getFirstName());
		user.setLastName(u.getLastName());
		user.setMail(u.getMail());
				
		user.setCreatedOn(u.getCreatedOn());
		user.setLastLoginOn(u.getLastLoginOn());
		user.setAuthSourceId(u.getAuthSourceId());
		user.setStatus(u.getStatus());
		user.addCustomFields(convertTCustomFieldList(u.getCustomFields()));
		user.addMemberships(convertTMembershipList(u.getMemberships()));
		user.addGroups(convertTGroupList(u.getGroups()));
		
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
		group.setId(g.getId());
		group.setName(g.getName());
		
		return group;
	}
	
	public static List<Group> convertTGroupList(Collection<TGroup> groups) {
		
		if(groups!=null && !groups.isEmpty()) {						
			Set<TGroup> lst = Set.copyOf(groups);
			return lst.stream()
					.filter(p->p!=null)
					.map(t->convert(t))
					.toList();
		}
		
		return List.of();
	}
	
	public static Group convert(TGroup g) {
		
		if(g==null) 
			return null;
		
		Group group = GroupFactory.create(g.getId());
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
		mem.setId(m.getId());
		mem.setProject(convert(m.getProject()));
		mem.setUserId(m.getUserId());
		mem.setUserName(m.getUserName());
		mem.setGroupId(m.getGroupId());
		mem.setGroupName(m.getGroupName());
		mem.setRoles(convertRoleList(m.getRoles()));
		return mem;
	}
	
	public static List<Membership> convertTMembershipList(Collection<TMembership> memberships) {
		
		if(memberships!=null && !memberships.isEmpty()) {						
			Set<TMembership> lst = Set.copyOf(memberships);
			return lst.stream()
					.filter(p->p!=null)
					.map(t->convert(t))
					.toList();
		}
		
		return List.of();
	}
	
	public static Membership convert(TMembership m) {
		
		if(m==null) 
			return null;		
		
		Membership mem = MembershipFactory.create(m.getId());
		mem.setProject(convert(m.getProject()));
		mem.setUserId(m.getUserId());
		mem.setUserName(m.getUserName());
		mem.setGroupId(m.getGroupId());
		mem.setGroupName(m.getGroupName());
		mem.addRoles(convertTRedmineRoleList(m.getRoles()));
		return mem;
	}

	public static List<TRedmineRole> convertRoleList(Collection<Role> roles) {
		
		if(roles!=null && !roles.isEmpty()) {						
			Set<Role> lst = Set.copyOf(roles);
			return lst.stream()
					.filter(p->p!=null)
					.map(t->convert(t))
					.toList();
		}
		
		return List.of();
	}

	public static TRedmineRole convert(Role r) {
		
		if(r==null)
			return null;
		
		TRedmineRole role = new TRedmineRole();
		role.setId(r.getId());
		role.setName(r.getName());
		role.setInherited(r.getInherited());
		role.setPermissions(convertPermissionList(r.getPermissions()));
		return role;
	}
	
	public static List<Role> convertTRedmineRoleList(Collection<TRedmineRole> roles) {
		
		if(roles!=null && !roles.isEmpty()) {						
			Set<TRedmineRole> lst = Set.copyOf(roles);
			return lst.stream()
					.filter(p->p!=null)
					.map(t->convert(t))
					.toList();
		}
		
		return List.of();
	}
	
	public static Role convert(TRedmineRole r) {
		
		if(r==null)
			return null;
		
		Role role = RoleFactory.create(r.getId());
		role.setName(r.getName());
		role.setInherited(r.getInherited());
		role.addPermissions(convertPermissionList(r.getPermissions()));
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
		cat.setId(category.getId());
		cat.setName(category.getName());
		cat.setProjectId(category.getProjectId());
		cat.setAssigneeId(category.getAssigneeId());
		cat.setAssigneeName(category.getAssigneeName());
		
		return cat;
	}
	
	public static IssueCategory convert(TIssueCategory category) {
		
		if(category==null) 
			return null;
		
		IssueCategory cat = IssueCategoryFactory.create(category.getId());
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
		version.setId(v.getId());
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
	
	public static Version convert(TRedmineVersion v) {
		
		if(v==null)
			return null;		
		
		Version version = VersionFactory.create(v.getId());
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
		proj.setId(p.getId());
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
	
	public static Project convert(TProject p) {
		
		if(p==null)
			return null;
		
		Project proj = ProjectFactory.create(p.getId());
		
		proj.setDescription(p.getDescription());
		proj.setHomepage(p.getHomepage());
		proj.setIdentifier(p.getIdentifier());
		proj.setName(p.getName());
		proj.setCreatedOn(p.getCreatedOn());
		proj.setUpdatedOn(p.getUpdatedOn());
		proj.setProjectPublic(p.getPublicProject());
		proj.setInheritMembers(p.getInheritMembers());
		proj.setParentId(p.getParentId());
		proj.addTrackers(convertTTrackerList(p.getTrackers()));
		proj.addCustomFields(convertTCustomFieldList(p.getCustomFields()));
		
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
		field.setId(c.getId());
		field.setName(c.getName());
		field.setValue(c.getValue());
		field.setMultiple(c.isMultiple());
		
		if(c.getValues()!=null)
			field.setValues(new ArrayList<>(c.getValues()));
		
		return field;
	}
	
	public static List<CustomField> convertTCustomFieldList(Collection<TCustomField> customFields) {
		if(customFields!=null && !customFields.isEmpty()) {						
			Set<TCustomField> lst = Set.copyOf(customFields);
			return lst.stream()
					.filter(p->p!=null)
					.map(t->convert(t))
					.toList();
		}
		
		return List.of();
	}
	
	public static CustomField convert(TCustomField c) {
		
		if(c==null)
			return null;
		
		CustomField field = CustomFieldFactory.create(c.getId());
		field.setName(c.getName());
		field.setValue(c.getValue());
		
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
		tk.setId(t.getId());
		tk.setName(t.getName());
		
		return tk;
	}
	
	public static List<Tracker> convertTTrackerList(Collection<TTracker> trackers) {
		
		if(trackers!=null && !trackers.isEmpty()) {						
			Set<TTracker> lst = Set.copyOf(trackers);
			return lst.stream()
					.filter(p->p!=null)
					.map(t->convert(t))
					.toList();
		}
		
		return List.of();
	}
	
	public static Tracker convert(TTracker t) {
		
		if(t==null)
			return null;
		
		Tracker tk = TrackerFactory.create(t.getId());
		tk.setName(t.getName());
		
		return tk;
	}

}
