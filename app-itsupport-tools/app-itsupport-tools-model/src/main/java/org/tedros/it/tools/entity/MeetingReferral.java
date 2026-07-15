package org.tedros.it.tools.entity;

import java.util.Objects;

import org.tedros.it.tools.domain.DomainSchema;
import org.tedros.it.tools.domain.DomainTables;
import org.tedros.server.entity.TVersionEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = DomainTables.MEETING_REFERRAL, schema = DomainSchema.schema)
public class MeetingReferral extends TVersionEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_meeting_minutes", nullable = false)
	private MeetingMinutes meetingMinutes;

	@Column(length = 10)
	private String item;

	@Lob
	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(length = 200)
	private String responsable;

	@Column(length = 100)
	private String deadline;

	public MeetingMinutes getMeetingMinutes() {
		return meetingMinutes;
	}

	public void setMeetingMinutes(MeetingMinutes meetingMinutes) {
		this.meetingMinutes = meetingMinutes;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(deadline, description, item, responsable);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof MeetingReferral))
			return false;
		MeetingReferral other = (MeetingReferral) obj;
		if (other.getId() != null && this.getId() != null)
			return Objects.equals(other.getId(), this.getId());
		return Objects.equals(deadline, other.deadline) && Objects.equals(description, other.description)
				&& Objects.equals(item, other.item) && Objects.equals(responsable, other.responsable);
	}
}
