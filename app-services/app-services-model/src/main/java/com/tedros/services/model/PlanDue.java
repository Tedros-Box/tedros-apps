/**
 * 
 */
package com.tedros.services.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tedros.ejb.base.entity.TEntity;
import com.tedros.services.domain.DomainSchema;
import com.tedros.services.domain.DomainTables;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name=DomainTables.plan_due, schema=DomainSchema.schema)
public class PlanDue extends TEntity {

	 
	
}
