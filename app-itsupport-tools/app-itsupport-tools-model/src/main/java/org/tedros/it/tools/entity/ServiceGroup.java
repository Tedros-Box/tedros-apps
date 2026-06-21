package org.tedros.it.tools.entity;

import java.util.List;
import java.util.Objects;

import org.tedros.it.tools.domain.DomainSchema;
import org.tedros.it.tools.domain.DomainTables;
import org.tedros.server.entity.TEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Represents a group of services within a catalog (e.g., Codificação, Testes, Design e UX).
 */
@Entity
@Table(name = DomainTables.SERVICE_GROUP, schema = DomainSchema.schema)
public class ServiceGroup extends TEntity {

    private static final long serialVersionUID = 1L;

    @Column(length = 200, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "catalog_id", nullable = false)
    private ServiceCatalog catalog;

    @OneToMany(mappedBy = "group", orphanRemoval = true,
            cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CatalogService> services;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ServiceCatalog getCatalog() {
        return catalog;
    }

    public void setCatalog(ServiceCatalog catalog) {
        this.catalog = catalog;
    }

    public List<CatalogService> getServices() {
        return services;
    }

    public void setServices(List<CatalogService> services) {
        this.services = services;
        if (this.services != null) {
            for (CatalogService s : this.services) {
                s.setGroup(this);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof ServiceGroup))
            return false;
        ServiceGroup other = (ServiceGroup) obj;
        return Objects.equals(name, other.name);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", catalog != null ? catalog.getName() : "", name);
    }
}
