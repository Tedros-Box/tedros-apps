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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

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
	@jakarta.persistence.ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(description, file);
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
		return Objects.equals(description, other.description) && Objects.equals(file, other.file);
	}

}
