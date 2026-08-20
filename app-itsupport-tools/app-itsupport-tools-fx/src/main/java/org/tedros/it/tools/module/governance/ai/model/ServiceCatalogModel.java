package org.tedros.it.tools.module.governance.ai.model;

import java.util.List;

import org.tedros.it.tools.entity.ServiceCatalog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCatalogModel {

    private String name;
    private List<ServiceGroupModel> groups;

    public ServiceCatalogModel(ServiceCatalog entity) {
        if (entity != null) {
            this.name = entity.getName();
            if (entity.getGroups() != null) {
                this.groups = entity.getGroups().stream()
                        .map(ServiceGroupModel::new)
                        .toList();
            }
        }
    }

    public ServiceCatalogModel(ServiceCatalog entity, List<ServiceGroupModel> groups) {
        if (entity != null) {
            this.name = entity.getName();
        }
        this.groups = groups;
    }
}
