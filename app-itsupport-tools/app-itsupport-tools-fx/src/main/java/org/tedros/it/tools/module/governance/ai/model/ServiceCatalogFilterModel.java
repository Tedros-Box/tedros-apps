package org.tedros.it.tools.module.governance.ai.model;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCatalogFilterModel {

    @JsonPropertyDescription("The name or partial name of the service group/category (e.g. 'Codificação', 'Testes', 'Design e UX'). Partial match supported.")
    private String categoryName;

    @JsonPropertyDescription("The exact number/code of the service (e.g. 1, 2, 10).")
    private Integer serviceNumber;

    @JsonPropertyDescription("The name or partial name of the service (e.g. 'Concepção da Solução de TI'). Partial match supported.")
    private String serviceName;

    @JsonPropertyDescription("The variant identifier letter or symbol (e.g. 'a', 'b', 'c', '-').")
    private String variantId;

    @JsonPropertyDescription("The complexity level of the service variant (e.g. 'Baixa', 'Média', 'Alta', 'Única').")
    private String complexity;

    @JsonPropertyDescription("Partial text to search in the scope description of the service variant.")
    private String scope;

    @JsonPropertyDescription("Exact estimated hours (HPA) to filter for.")
    private Double estimatedHours;

    @JsonPropertyDescription("Minimum estimated hours (HPA) to filter for (inclusive).")
    private Double minEstimatedHours;

    @JsonPropertyDescription("Maximum estimated hours (HPA) to filter for (inclusive).")
    private Double maxEstimatedHours;

    @JsonPropertyDescription("Partial text to search in the deliverables/evidence produced by the variant.")
    private String deliverables;

    @JsonPropertyDescription("Partial text to search in the required professional profiles.")
    private String requiredProfiles;

    @JsonPropertyDescription("Partial text to search in the activities performed for the variant.")
    private String activitiesPerformed;

    public boolean hasCriteria() {
        return StringUtils.isNotBlank(categoryName)
                || serviceNumber != null
                || StringUtils.isNotBlank(serviceName)
                || StringUtils.isNotBlank(variantId)
                || StringUtils.isNotBlank(complexity)
                || StringUtils.isNotBlank(scope)
                || estimatedHours != null
                || minEstimatedHours != null
                || maxEstimatedHours != null
                || StringUtils.isNotBlank(deliverables)
                || StringUtils.isNotBlank(requiredProfiles)
                || StringUtils.isNotBlank(activitiesPerformed);
    }
}
