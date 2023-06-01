/**
 * 
 */
package org.tedros.extension.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.tedros.extension.domain.DomainSchema;
import org.tedros.extension.domain.DomainTables;
import org.tedros.server.annotation.TCaseSensitive;
import org.tedros.server.annotation.TField;
import org.tedros.server.annotation.TFileType;
import org.tedros.server.annotation.TImportInfo;
import org.tedros.server.entity.TVersionEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.adminArea, schema = DomainSchema.schema)
@TImportInfo(description = "#{city.import.rule.desc}", 
fileType = { TFileType.CSV, TFileType.XLS })
public class AdminArea extends TVersionEntity {

	private static final long serialVersionUID = 1L;

	@Column(length=2, nullable=false)
	@TField(required = true, 
	label = "#{label.country.code} (ISO2)", column = "country_iso2", 
	example="BR", caseSensitive=TCaseSensitive.UPPER, maxLength=2)
	private String countryIso2Code;
	
	@Column(length=120, nullable=false)
	@TField(required = true, 
	label = "#{label.name}", column = "name", 
	example="São Paulo", maxLength=120)
	private String name;
	
	@Column(length=10, nullable=true)
	private String iso2Code;

	public String getCountryIso2Code() {
		return countryIso2Code;
	}

	public void setCountryIso2Code(String countryIso2Code) {
		this.countryIso2Code = countryIso2Code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIso2Code() {
		return iso2Code;
	}

	public void setIso2Code(String iso2Code) {
		this.iso2Code = iso2Code;
	}

	@Override
	public String toString() {
		return name!=null ? name : "";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((countryIso2Code == null) ? 0 : countryIso2Code.hashCode());
		result = prime * result + ((iso2Code == null) ? 0 : iso2Code.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		AdminArea other = (AdminArea) obj;
		if (countryIso2Code == null) {
			if (other.countryIso2Code != null)
				return false;
		} else if (!countryIso2Code.equals(other.countryIso2Code))
			return false;
		if (iso2Code == null) {
			if (other.iso2Code != null)
				return false;
		} else if (!iso2Code.equals(other.iso2Code))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
