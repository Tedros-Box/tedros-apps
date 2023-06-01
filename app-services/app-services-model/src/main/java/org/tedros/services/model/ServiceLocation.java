/**
 * 
 */
package org.tedros.services.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.tedros.extension.model.Place;
import org.tedros.server.entity.TVersionEntity;
import org.tedros.services.domain.DomainSchema;
import org.tedros.services.domain.DomainTables;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name=DomainTables.service_location, schema=DomainSchema.schema)
public class ServiceLocation extends TVersionEntity {

	private static final long serialVersionUID = -3229543266820321527L;
	
	@Column(length=120, nullable = false)
	private String name;
	
	@Column(length=1024)
	private String description;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="place_id")
	private Place place;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}
	
	@Override
	public String toString() {
		return  (name != null ?  name : "");
	}
	
	

}
