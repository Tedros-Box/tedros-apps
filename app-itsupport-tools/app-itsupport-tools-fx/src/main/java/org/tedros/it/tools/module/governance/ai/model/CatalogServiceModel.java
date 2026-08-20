package org.tedros.it.tools.module.governance.ai.model;

import java.util.List;

import org.tedros.it.tools.entity.CatalogService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogServiceModel {

    private Integer number;
    private String name;
    private List<ServiceVariantModel> variants;

    public CatalogServiceModel(CatalogService entity) {
        if (entity != null) {
            this.number = entity.getNumber();
            this.name = entity.getName();
            if (entity.getVariants() != null) {
                this.variants = entity.getVariants().stream()
                        .map(ServiceVariantModel::new)
                        .toList();
            }
        }
    }

    public CatalogServiceModel(CatalogService entity, List<ServiceVariantModel> variants) {
        if (entity != null) {
            this.number = entity.getNumber();
            this.name = entity.getName();
        }
        this.variants = variants;
    }
}
