package org.tedros.it.tools.entity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import org.tedros.common.model.TFileEntity;
import org.tedros.it.tools.domain.DomainSchema;
import org.tedros.it.tools.domain.DomainTables;
import org.tedros.server.entity.TVersionEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = DomainTables.MEETING_EVIDENCE, schema = DomainSchema.schema)
public class MeetingEvidence extends TVersionEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_meeting_minutes", nullable = false)
	private MeetingMinutes meetingMinutes;

	@Column(length = 500)
	private String name;

	@Column(length = 1000)
	private String filePath;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "file_id")
	private TFileEntity file;

	@Transient
	private InputStream evidence;

	public void initInputStream() {
		if (file != null && file.getByteEntity() != null && file.getByteEntity().getBytes() != null) {
			evidence = new ByteArrayInputStream(file.getByteEntity().getBytes());
		}
	}

	public void closeInputStream() throws IOException {
		if (evidence != null) {
			evidence.close();
			evidence = null;
		}
	}

	public MeetingMinutes getMeetingMinutes() {
		return meetingMinutes;
	}

	public void setMeetingMinutes(MeetingMinutes meetingMinutes) {
		this.meetingMinutes = meetingMinutes;
	}

	public InputStream getEvidence() {
		return evidence;
	}

	public void setEvidence(InputStream evidence) {
		this.evidence = evidence;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public TFileEntity getFile() {
		return file;
	}

	public void setFile(TFileEntity file) {
		this.file = file;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(file, filePath, name);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof MeetingEvidence))
			return false;
		MeetingEvidence other = (MeetingEvidence) obj;
		if (other.getId() != null && this.getId() != null)
			return Objects.equals(other.getId(), this.getId());
		return Objects.equals(file, other.file) && Objects.equals(filePath, other.filePath)
				&& Objects.equals(name, other.name);
	}
}
