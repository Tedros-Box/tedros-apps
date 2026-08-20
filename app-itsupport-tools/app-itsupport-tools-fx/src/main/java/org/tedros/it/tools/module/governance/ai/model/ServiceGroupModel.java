package org.tedros.it.tools.module.governance.ai.model;

import java.util.List;

import org.tedros.it.tools.entity.ServiceGroup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceGroupModel {

    private String name;
    private List<CatalogServiceModel> services;

    public ServiceGroupModel(ServiceGroup entity) {
        if (entity != null) {
            this.name = entity.getName();
            if (entity.getServices() != null) {
                this.services = entity.getServices().stream()
                        .map(CatalogServiceModel::new)
                        .toList();
            }
        }
    }

    public ServiceGroupModel(ServiceGroup entity, List<CatalogServiceModel> services) {
        if (entity != null) {
            this.name = entity.getName();
        }
        this.services = services;
    }
}
