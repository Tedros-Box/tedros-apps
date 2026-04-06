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
 * Represents an individual service entry in the catalog (e.g., "Concepção da Solução de TI").
 * Each service has a sequential number, a name, a group and a list of variants.
 */
@Entity
@Table(name = DomainTables.CATALOG_SERVICE, schema = DomainSchema.schema)
public class CatalogService extends TEntity {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private Integer number;

    @Column(length = 300, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private ServiceGroup group;

    @OneToMany(mappedBy = "service", orphanRemoval = true,
            cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ServiceVariant> variants;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ServiceGroup getGroup() {
        return group;
    }

    public void setGroup(ServiceGroup group) {
        this.group = group;
    }

    public List<ServiceVariant> getVariants() {
        return variants;
    }

    public void setVariants(List<ServiceVariant> variants) {
        this.variants = variants;
        if (this.variants != null) {
            for (ServiceVariant v : this.variants) {
                v.setService(this);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof CatalogService))
            return false;
        CatalogService other = (CatalogService) obj;
        return Objects.equals(number, other.number) && Objects.equals(name, other.name);
    }

    @Override
    public String toString() {
        return String.format("CatalogService [getId()=%s, number=%s, name=%s]", getId(), number, name);
    }
}
