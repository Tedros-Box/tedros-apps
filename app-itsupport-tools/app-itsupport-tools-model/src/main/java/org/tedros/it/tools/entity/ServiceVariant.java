package org.tedros.it.tools.entity;

import java.util.Objects;

import org.tedros.it.tools.domain.DomainSchema;
import org.tedros.it.tools.domain.DomainTables;
import org.tedros.server.entity.TEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Represents a variant of a catalog service, identified by a letter (e.g., "a", "b", "c").
 * Each variant has its own complexity level, scope, estimated hours, deliverables,
 * required professional profiles and activities performed.
 */
@Entity
@Table(name = DomainTables.SERVICE_VARIANT, schema = DomainSchema.schema)
public class ServiceVariant extends TEntity {

    private static final long serialVersionUID = 1L;

    /**
     * Variant identifier letter (e.g., "a", "b", "c", "-").
     */
    @Column(length = 10, nullable = false)
    private String variantId;

    /**
     * Complexity level (e.g., "Baixa", "Média", "Alta", "Única").
     */
    @Column(length = 50, nullable = false)
    private String complexity;

    /**
     * Scope description defining what this variant covers.
     */
    @Lob
    @Column
    private String scope;

    /**
     * Estimated hours to complete the service variant.
     */
    @Column(nullable = false)
    private Double estimatedHours;

    /**
     * Deliverables produced upon completion of this variant.
     */
    @Lob
    @Column
    private String deliverables;

    /**
     * Required professional profiles to perform this variant.
     */
    @Lob
    @Column
    private String requiredProfiles;

    /**
     * Activities performed to deliver this variant.
     */
    @Lob
    @Column
    private String activitiesPerformed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private CatalogService service;

    public String getVariantId() {
        return variantId;
    }

    public void setVariantId(String variantId) {
        this.variantId = variantId;
    }

    public String getComplexity() {
        return complexity;
    }

    public void setComplexity(String complexity) {
        this.complexity = complexity;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Double getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(Double estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public String getDeliverables() {
        return deliverables;
    }

    public void setDeliverables(String deliverables) {
        this.deliverables = deliverables;
    }

    public String getRequiredProfiles() {
        return requiredProfiles;
    }

    public void setRequiredProfiles(String requiredProfiles) {
        this.requiredProfiles = requiredProfiles;
    }

    public String getActivitiesPerformed() {
        return activitiesPerformed;
    }

    public void setActivitiesPerformed(String activitiesPerformed) {
        this.activitiesPerformed = activitiesPerformed;
    }

    public CatalogService getService() {
        return service;
    }

    public void setService(CatalogService service) {
        this.service = service;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof ServiceVariant))
            return false;
        ServiceVariant other = (ServiceVariant) obj;
        return Objects.equals(variantId, other.variantId)
                && Objects.equals(complexity, other.complexity)
                && Objects.equals(service, other.service);
    }

    @Override
    public String toString() {
        return String.format("ServiceVariant [getId()=%s, variantId=%s, complexity=%s]",
                getId(), variantId, complexity);
    }
}
