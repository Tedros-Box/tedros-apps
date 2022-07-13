/**
 * 
 */
package com.tedros.services.model;

import java.util.Set;

import javax.persistence.Column;

import com.tedros.ejb.base.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
public class Service extends TEntity {
	
	@Column(length=15)
	private String code;

	@Column(length=250, nullable = false)
	private String name;
	
	@Column(length=1024)
	private String description;
	
	@Column(length=1024)
	private String observation;
	
	private ServiceType type;
	
	private Set<Plan> plans;
	
	
}
