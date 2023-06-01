/**
 * 
 */
package org.tedros.extension.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.tedros.extension.domain.DomainSchema;
import org.tedros.extension.domain.DomainTables;
import org.tedros.server.annotation.TModelInfo;

/**
 * @author Davis Gordon
 *
 */
@Entity
@TModelInfo("#{model.info.document.type}")
@Table(name=DomainTables.document_type, schema=DomainSchema.schema)
@DiscriminatorValue("doc_type")
public class DocumentType extends ExtensionDomain {
	
	private static final long serialVersionUID = 8489348034424401175L;

	@Column(length=30)
	@Enumerated(EnumType.STRING)
	private DocType docType;

	/**
	 * @return the docType
	 */
	public DocType getDocType() {
		return docType;
	}

	/**
	 * @param docType the docType to set
	 */
	public void setDocType(DocType docType) {
		this.docType = docType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((docType == null) ? 0 : docType.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocumentType other = (DocumentType) obj;
		if (docType != other.docType)
			return false;
		return true;
	}
}
