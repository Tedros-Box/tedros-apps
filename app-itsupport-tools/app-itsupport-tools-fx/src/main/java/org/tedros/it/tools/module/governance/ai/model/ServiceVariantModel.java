package org.tedros.it.tools.module.governance.ai.model;

import org.tedros.it.tools.entity.ServiceVariant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceVariantModel {

    private String variantId;
    private String complexity;
    private String scope;
    private Double estimatedHours;
    private String deliverables;
    private String requiredProfiles;
    private String activitiesPerformed;

    public ServiceVariantModel(ServiceVariant entity) {
        if (entity != null) {
            this.variantId = entity.getVariantId();
            this.complexity = entity.getComplexity();
            this.scope = entity.getScope();
            this.estimatedHours = entity.getEstimatedHours();
            this.deliverables = entity.getDeliverables();
            this.requiredProfiles = entity.getRequiredProfiles();
            this.activitiesPerformed = entity.getActivitiesPerformed();
        }
    }
}
