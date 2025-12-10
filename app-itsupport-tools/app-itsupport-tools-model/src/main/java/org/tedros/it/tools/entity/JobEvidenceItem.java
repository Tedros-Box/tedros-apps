/**
 * 
 */
package org.tedros.it.tools.entity;

import java.util.Date;
import java.util.Objects;

import org.tedros.common.model.TFileEntity;
import org.tedros.it.tools.domain.DomainSchema;
import org.tedros.it.tools.domain.DomainTables;
import org.tedros.server.entity.TEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * 
 */
@Entity
@Table(name = DomainTables.JOB_EVIDENCE_ITEM, schema = DomainSchema.schema)
public class JobEvidenceItem extends TEntity {

	private static final long serialVersionUID = 7607736437723984845L;

	@Column(length = 500)
	private String name;
	
	@Column(length = 1000)
	private String filePath;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date evidenceDate;
	
	@Column
	private String description;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "file_id")
	private TFileEntity file;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TFileEntity getFile() {
		return file;
	}

	public void setFile(TFileEntity file) {
		this.file = file;
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

	public Date getEvidenceDate() {
		return evidenceDate;
	}

	public void setEvidenceDate(Date evidenceDate) {
		this.evidenceDate = evidenceDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(description, evidenceDate, file, filePath, name);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof JobEvidenceItem))
			return false;
		JobEvidenceItem other = (JobEvidenceItem) obj;
		
		if(other.getId() != null && this.getId() != null)
			return Objects.equals(other.getId(), this.getId());
		
		return Objects.equals(description, other.description) && Objects.equals(evidenceDate, other.evidenceDate)
				&& Objects.equals(file, other.file) && Objects.equals(filePath, other.filePath)
				&& Objects.equals(name, other.name);
	}

	

}
