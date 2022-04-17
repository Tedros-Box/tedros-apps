/**
 * 
 */
package com.tedros.location.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tedros.ejb.base.annotation.TCaseSensitive;
import com.tedros.ejb.base.annotation.TEntityImportRule;
import com.tedros.ejb.base.annotation.TFieldImportRule;
import com.tedros.ejb.base.annotation.TFileType;
import com.tedros.ejb.base.entity.TEntity;
import com.tedros.location.domain.DomainSchema;
import com.tedros.location.domain.DomainTables;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.adminArea, schema = DomainSchema.schema)
@TEntityImportRule(description = "#{city.import.rule.desc}", 
fileType = { TFileType.CSV, TFileType.XLS })
public class AdminArea extends TEntity {

	private static final long serialVersionUID = 1L;

	@Column(length=2, nullable=false)
	@TFieldImportRule(required = true, 
	description = "#{label.country.code} (ISO2)", column = "country_iso2", 
	example="BR", caseSensitive=TCaseSensitive.UPPER, maxLength=2)
	private String countryIso2Code;
	
	@Column(length=120, nullable=false)
	@TFieldImportRule(required = true, 
	description = "#{label.name}", column = "name", 
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
	

	final static String city = "city";
}
