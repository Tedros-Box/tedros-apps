package org.tedros.it.tools.entity;

import java.util.List;
import java.util.Objects;

import org.tedros.it.tools.domain.DomainSchema;
import org.tedros.it.tools.domain.DomainTables;
import org.tedros.server.entity.TVersionEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = DomainTables.MEETING_MINUTES, schema = DomainSchema.schema)
public class MeetingMinutes extends TVersionEntity {

	private static final long serialVersionUID = 1L;

	@Column(length = 200)
	private String meetingPlace;

	@Column(length = 50)
	private String meetingDate;

	@Column(length = 20)
	private String meetingStartTime;

	@Column(length = 20)
	private String meetingFinishTime;

	@Lob
	@Column(columnDefinition = "TEXT")
	private String meetingTopic;

	@Lob
	@Column(columnDefinition = "TEXT")
	private String participants;

	@Column(length = 200)
	private String nextMeetingPlace;

	@Column(length = 50)
	private String nextMeetingDate;

	@Column(length = 20)
	private String nextMeetingStartTime;

	@Column(length = 20)
	private String nextMeetingFinishTime;

	@Column(length = 50)
	private String issueNumber;

	@Column(length = 500)
	private String issueTitle;

	@Column
	private Integer activityId = 19;

	@Column(length = 50)
	private String redmineAttachmentId;

	@Column(length = 50)
	private String redmineTimeEntryId;

	@Column(length = 500)
	private String transcriptionFileName;

	@Lob
	@Column(columnDefinition = "TEXT")
	private String transcription;

	@Column(length = 500)
	private String generatedPdfPath;

	@OneToMany(mappedBy = "meetingMinutes", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<MeetingAgenda> meetingAgenda;

	@OneToMany(mappedBy = "meetingMinutes", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<MeetingReferral> meetingReferrals;

	@OneToMany(mappedBy = "meetingMinutes", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<MeetingEvidence> evidences;

	public String getMeetingPlace() {
		return meetingPlace;
	}

	public void setMeetingPlace(String meetingPlace) {
		this.meetingPlace = meetingPlace;
	}

	public String getMeetingDate() {
		return meetingDate;
	}

	public void setMeetingDate(String meetingDate) {
		this.meetingDate = meetingDate;
	}

	public String getMeetingStartTime() {
		return meetingStartTime;
	}

	public void setMeetingStartTime(String meetingStartTime) {
		this.meetingStartTime = meetingStartTime;
	}

	public String getMeetingFinishTime() {
		return meetingFinishTime;
	}

	public void setMeetingFinishTime(String meetingFinishTime) {
		this.meetingFinishTime = meetingFinishTime;
	}

	public String getMeetingTopic() {
		return meetingTopic;
	}

	public void setMeetingTopic(String meetingTopic) {
		this.meetingTopic = meetingTopic;
	}

	public String getParticipants() {
		return participants;
	}

	public void setParticipants(String participants) {
		this.participants = participants;
	}

	public String getNextMeetingPlace() {
		return nextMeetingPlace;
	}

	public void setNextMeetingPlace(String nextMeetingPlace) {
		this.nextMeetingPlace = nextMeetingPlace;
	}

	public String getNextMeetingDate() {
		return nextMeetingDate;
	}

	public void setNextMeetingDate(String nextMeetingDate) {
		this.nextMeetingDate = nextMeetingDate;
	}

	public String getNextMeetingStartTime() {
		return nextMeetingStartTime;
	}

	public void setNextMeetingStartTime(String nextMeetingStartTime) {
		this.nextMeetingStartTime = nextMeetingStartTime;
	}

	public String getNextMeetingFinishTime() {
		return nextMeetingFinishTime;
	}

	public void setNextMeetingFinishTime(String nextMeetingFinishTime) {
		this.nextMeetingFinishTime = nextMeetingFinishTime;
	}

	public String getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}

	public String getIssueTitle() {
		return issueTitle;
	}

	public void setIssueTitle(String issueTitle) {
		this.issueTitle = issueTitle;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getRedmineAttachmentId() {
		return redmineAttachmentId;
	}

	public void setRedmineAttachmentId(String redmineAttachmentId) {
		this.redmineAttachmentId = redmineAttachmentId;
	}

	public String getRedmineTimeEntryId() {
		return redmineTimeEntryId;
	}

	public void setRedmineTimeEntryId(String redmineTimeEntryId) {
		this.redmineTimeEntryId = redmineTimeEntryId;
	}

	public String getTranscriptionFileName() {
		return transcriptionFileName;
	}

	public void setTranscriptionFileName(String transcriptionFileName) {
		this.transcriptionFileName = transcriptionFileName;
	}

	public String getTranscription() {
		return transcription;
	}

	public void setTranscription(String transcription) {
		this.transcription = transcription;
	}

	public String getGeneratedPdfPath() {
		return generatedPdfPath;
	}

	public void setGeneratedPdfPath(String generatedPdfPath) {
		this.generatedPdfPath = generatedPdfPath;
	}

	public List<MeetingAgenda> getMeetingAgenda() {
		return meetingAgenda;
	}

	public void setMeetingAgenda(List<MeetingAgenda> meetingAgenda) {
		this.meetingAgenda = meetingAgenda;
		if (this.meetingAgenda != null) {
			for (MeetingAgenda item : this.meetingAgenda) {
				item.setMeetingMinutes(this);
			}
		}
	}

	public List<MeetingReferral> getMeetingReferrals() {
		return meetingReferrals;
	}

	public void setMeetingReferrals(List<MeetingReferral> meetingReferrals) {
		this.meetingReferrals = meetingReferrals;
		if (this.meetingReferrals != null) {
			for (MeetingReferral item : this.meetingReferrals) {
				item.setMeetingMinutes(this);
			}
		}
	}

	public List<MeetingEvidence> getEvidences() {
		return evidences;
	}

	public void setEvidences(List<MeetingEvidence> evidences) {
		this.evidences = evidences;
		if (this.evidences != null) {
			for (MeetingEvidence item : this.evidences) {
				item.setMeetingMinutes(this);
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(activityId, evidences, generatedPdfPath, issueNumber, issueTitle,
				meetingAgenda, meetingDate, meetingFinishTime, meetingPlace, meetingReferrals, meetingStartTime,
				meetingTopic, nextMeetingDate, nextMeetingFinishTime, nextMeetingPlace, nextMeetingStartTime,
				participants, redmineAttachmentId, redmineTimeEntryId, transcription, transcriptionFileName);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof MeetingMinutes))
			return false;
		MeetingMinutes other = (MeetingMinutes) obj;
		return Objects.equals(activityId, other.activityId) && Objects.equals(evidences, other.evidences)
				&& Objects.equals(generatedPdfPath, other.generatedPdfPath)
				&& Objects.equals(issueNumber, other.issueNumber) && Objects.equals(issueTitle, other.issueTitle)
				&& Objects.equals(meetingAgenda, other.meetingAgenda)
				&& Objects.equals(meetingDate, other.meetingDate)
				&& Objects.equals(meetingFinishTime, other.meetingFinishTime)
				&& Objects.equals(meetingPlace, other.meetingPlace)
				&& Objects.equals(meetingReferrals, other.meetingReferrals)
				&& Objects.equals(meetingStartTime, other.meetingStartTime)
				&& Objects.equals(meetingTopic, other.meetingTopic)
				&& Objects.equals(nextMeetingDate, other.nextMeetingDate)
				&& Objects.equals(nextMeetingFinishTime, other.nextMeetingFinishTime)
				&& Objects.equals(nextMeetingPlace, other.nextMeetingPlace)
				&& Objects.equals(nextMeetingStartTime, other.nextMeetingStartTime)
				&& Objects.equals(participants, other.participants)
				&& Objects.equals(redmineAttachmentId, other.redmineAttachmentId)
				&& Objects.equals(redmineTimeEntryId, other.redmineTimeEntryId)
				&& Objects.equals(transcription, other.transcription)
				&& Objects.equals(transcriptionFileName, other.transcriptionFileName);
	}
}
