package org.tedros.it.tools.entity;

import java.util.List;
import java.util.Objects;

import org.tedros.it.tools.domain.DomainSchema;
import org.tedros.it.tools.domain.DomainTables;
import org.tedros.server.entity.TVersionEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Represents the root service catalog (Catálogo de Serviços).
 */
@Entity
@Table(name = DomainTables.SERVICE_CATALOG, schema = DomainSchema.schema)
public class ServiceCatalog extends TVersionEntity {

    private static final long serialVersionUID = 1L;

    @Column(length = 200, nullable = false)
    private String name;

    @OneToMany(mappedBy = "catalog", orphanRemoval = true,
            cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ServiceGroup> groups;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ServiceGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<ServiceGroup> groups) {
        this.groups = groups;
        if (this.groups != null) {
            for (ServiceGroup g : this.groups) {
                g.setCatalog(this);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof ServiceCatalog))
            return false;
        ServiceCatalog other = (ServiceCatalog) obj;
        return Objects.equals(name, other.name);
    }

    @Override
    public String toString() {
        return String.format("ServiceCatalog [getId()=%s, name=%s]", getId(), name);
    }
}
