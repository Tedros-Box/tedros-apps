package org.tedros.it.tools.entity;

import java.util.Objects;

import org.tedros.it.tools.domain.DomainSchema;
import org.tedros.it.tools.domain.DomainTables;
import org.tedros.person.model.Employee;
import org.tedros.server.entity.TVersionEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = DomainTables.GMUD_ITEM, schema = DomainSchema.schema)
public class GmudItem extends TVersionEntity {

    private static final long serialVersionUID = 4246280208687191618L;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_gmud", nullable = false)
    private Gmud gmud;

	@Column(nullable = false)
    private Integer stepOrder;

    @Column(length = 1000, nullable = false)
    private String action;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "responsible_id", nullable = false)
    private Employee responsible;

    @Column(length = 20, nullable = false)
    private String status;

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

	public Employee getResponsible() {
		return responsible;
	}

	public void setResponsible(Employee responsible) {
		this.responsible = responsible;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Gmud getGmud() {
		return gmud;
	}

	public void setGmud(Gmud gmud) {
		this.gmud = gmud;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(action, status, responsible, stepOrder);
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
		return Objects.equals(action, other.action) && Objects.equals(status, other.status)
				&& Objects.equals(responsible, other.responsible) && Objects.equals(stepOrder, other.stepOrder);
	}
    
}
