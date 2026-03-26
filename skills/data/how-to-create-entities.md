# Skill: Tedros Entity Implementation Guide

## 1. Objective

Provide a technical reference for creating persistence entities within the Tedros ecosystem. This ensures consistency in table naming, schema allocation, and adherence to the framework's class hierarchy.

## 2. Architectural Placement

- **Project Location:** All entities and models must be created in the `[APP_NAME]-model` project.
- **Dependency:** Entities must extend the appropriate base class from `org.tedros.server` or `org.tedros.common`.

## 3. Naming & Schema Conventions

- **Table Names:** Lowercase, snake_case, and prefixed with the application identifier (e.g., `mc_` for MyCustomApp).
- **Centralized Definitions:** - Table names must be constants in `org.tedros.[APP_NAME].domain.DomainTables`.
  - Schema names must be constants in `org.tedros.[APP_NAME].domain.DomainSchema`.
  - Default Schema: `tedros_apps`.

## 4. Entity Hierarchy

Models must follow this specific hierarchy to inherit standard fields:

| Interface/Class | Description | Key Fields |
| :--- | :--- | :--- |
| `ITModel` | Root interface | No fields. |
| `TEntity` | Standard base entity | `id` (Long), `lastUpdate`, `insertDate`, `createdByUserId`, `orderBy`. |
| `TVersionEntity` | Versioned entity | Inherits `TEntity` + `versionNum` (Integer). |
| `TByteEntity` | Binary data storage | Inherits `TVersionEntity` + `bytes` (byte[]). |
| `TFileEntity` | File metadata | Inherits `TVersionEntity` + `fileName`, `fileExtension`, `fileSize`, `byteEntity`. |
| `TUser` | Identity entity | `login`, `password`, `name`, `active`, `profiles`. |

## 5. Implementation Standard

Entities must use the `@Table` annotation referencing the `DomainTables` and `DomainSchema` interfaces.

### Java Implementation Example

```java
package org.tedros.myCustomApp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.tedros.server.entity.TEntity;
import org.tedros.myCustomApp.domain.DomainTables;
import org.tedros.myCustomApp.domain.DomainSchema;

@Entity
@Table(name = DomainTables.some_table, schema = DomainSchema.schema)
public class SomeEntity extends TEntity {
    private static final long serialVersionUID = 1L;
    // Business attributes go here
}
```

### Domain Configuration Example

```java
package org.tedros.myCustomApp.domain;

public interface DomainTables {
    String some_table = "mc_some_table";
}

public interface DomainSchema {
    String schema = "tedros_apps";
}
```

## 6. Development Constraints

- **Inheritance:** Always choose the most specific base class (e.g., use `TVersionEntity` if optimistic locking is required).
- **Annotation:** Use `jakarta.persistence` (Java 17+) instead of `javax.persistence` where applicable.
- **Reference:** Never hardcode strings in the `@Table(name=...)` attribute; always use `DomainTables` constants.
- **Versioning:** Always define `private static final long serialVersionUID`.
