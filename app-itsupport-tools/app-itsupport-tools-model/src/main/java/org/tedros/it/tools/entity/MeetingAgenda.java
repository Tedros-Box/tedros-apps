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
@Table(name = DomainTables.MEETING_AGENDA, schema = DomainSchema.schema)
public class MeetingAgenda extends TVersionEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_meeting_minutes", nullable = false)
	private MeetingMinutes meetingMinutes;

	@Column(length = 10)
	private String item;

	@Lob
	@Column(columnDefinition = "TEXT")
	private String description;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(description, item);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof MeetingAgenda))
			return false;
		MeetingAgenda other = (MeetingAgenda) obj;
		if (other.getId() != null && this.getId() != null)
			return Objects.equals(other.getId(), this.getId());
		return Objects.equals(description, other.description) && Objects.equals(item, other.item);
	}
}
