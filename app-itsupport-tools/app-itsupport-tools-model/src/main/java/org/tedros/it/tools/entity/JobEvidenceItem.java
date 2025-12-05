/**
 * 
 */
package org.tedros.it.tools.entity;

import java.util.List;
import java.util.Objects;

import org.tedros.common.model.TFileEntity;
import org.tedros.it.tools.domain.DomainSchema;
import org.tedros.it.tools.domain.DomainTables;
import org.tedros.server.entity.TEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * 
 */
@Entity
@Table(name = DomainTables.JOB_EVIDENCE_ITEM, schema = DomainSchema.schema)
public class JobEvidenceItem extends TEntity {

	private static final long serialVersionUID = 7607736437723984845L;
	
	@Column
	private String description;
	
	@JsonIgnore
	@ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(name=DomainTables.JOB_EVIDENCE_ITEM_FILE, schema=DomainSchema.schema, 
	uniqueConstraints=@UniqueConstraint(columnNames = { "job_id", "file_id" }),
	joinColumns=@JoinColumn(name="job_id"), inverseJoinColumns=@JoinColumn(name="file_id"))
	private List<TFileEntity> files;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<TFileEntity> getFiles() {
		return files;
	}

	public void setFiles(List<TFileEntity> files) {
		this.files = files;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(description, files);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof JobEvidenceItem)) {
			return false;
		}
		JobEvidenceItem other = (JobEvidenceItem) obj;
		return Objects.equals(description, other.description) && Objects.equals(files, other.files);
	}

}
