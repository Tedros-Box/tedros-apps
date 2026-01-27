package org.tedros.it.tools.entity;

import java.util.Objects;

import org.tedros.it.tools.domain.DomainSchema;
import org.tedros.it.tools.domain.DomainTables;
import org.tedros.server.entity.TEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = DomainTables.GMUD_ITEM, schema = DomainSchema.schema)
public class GmudItem extends TEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4246280208687191618L;

	@Column(nullable = false)
    private Integer stepOrder;

    @Column(length = 500, nullable = false)
    private String action;

    @Column(length = 120)
    private String responsible;

    @Column
    private Boolean completed = false;

	public Integer getStepOrder() {
		return stepOrder;
	}

	public void setStepOrder(Integer stepOrder) {
		this.stepOrder = stepOrder;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getResponsible() {
		return responsible;
	}

	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}

	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(action, completed, responsible, stepOrder);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof GmudItem))
			return false;
		GmudItem other = (GmudItem) obj;
		return Objects.equals(action, other.action) && Objects.equals(completed, other.completed)
				&& Objects.equals(responsible, other.responsible) && Objects.equals(stepOrder, other.stepOrder);
	}
    
}
