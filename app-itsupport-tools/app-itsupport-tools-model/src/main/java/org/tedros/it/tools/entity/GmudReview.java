package org.tedros.it.tools.entity;

import java.util.Date;
import java.util.Objects;

import org.tedros.it.tools.domain.DomainSchema;
import org.tedros.it.tools.domain.DomainTables;
import org.tedros.person.model.Employee;
import org.tedros.server.entity.TEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = DomainTables.GMUD_REVIEW, schema = DomainSchema.schema)
public class GmudReview extends TEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6940391737963052709L;

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reviewer_id", nullable = false)
    private Employee reviewer;

    @Column(length = 1000, nullable = false)
    private String comments;

    @Column(nullable = false)
    private Boolean approved;

    @Temporal(TemporalType.TIMESTAMP)
    private Date reviewDate;

	public Employee getReviewer() {
		return reviewer;
	}

	public void setReviewer(Employee reviewer) {
		this.reviewer = reviewer;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(approved, comments, reviewDate, reviewer);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof GmudReview))
			return false;
		GmudReview other = (GmudReview) obj;
		return Objects.equals(approved, other.approved) && Objects.equals(comments, other.comments)
				&& Objects.equals(reviewDate, other.reviewDate) && Objects.equals(reviewer, other.reviewer);
	}
    
}
