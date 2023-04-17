/**
 * 
 */
package org.tedros.location.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.tedros.location.domain.DomainSchema;
import org.tedros.location.domain.DomainTables;
import org.tedros.server.annotation.TCaseSensitive;
import org.tedros.server.annotation.TImportInfo;
import org.tedros.server.annotation.TField;
import org.tedros.server.annotation.TFileType;
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
	
}
