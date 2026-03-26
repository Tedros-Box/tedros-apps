# Skill: Tedros EJB Service Layer Implementation Guide

## 1. Objective

Provide a technical reference for creating the **EJB service layer** within the Tedros ecosystem using **`TEjbService`**. This layer sits **below the Controller layer** and **above the BO layer**. It receives delegated calls from the Controller, enforces transaction boundaries via EJB annotations, and forwards all operations to the appropriate business object (BO).

## 2. Architectural Placement

- **Project Location:** All service classes must be created in the `[APP_NAME]-ejb` project.
- **Package:** `org.tedros.[app_name].ejb.service`
- **Dependency:** Services must extend `TEjbService<E>` from `org.tedros.server.ejb.service`.
- **Layer position:** `Client` → `Controller` → `EJB Service` → `BO` → `EAO` → `EntityManager`

## 3. Base Class: `TEjbService<E>`

`TEjbService<E extends ITEntity>` is the abstract base class that implements `ITEjbService<E>`. It delegates **all operations** to the BO obtained via `getBussinesObject()` and manages **transaction boundaries** via EJB annotations.

The single abstract method that every service **must** implement is:

```java
public abstract ITGenericBO<E> getBussinesObject();
```

This method connects the service to its corresponding BO via CDI injection.

### 3.1 Transaction Strategy

The class-level annotation sets the default behavior for all read operations:

```java
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
```

Write operations override this at the method level to require an active transaction:

| Method | Transaction |
| :--- | :--- |
| All `find*`, `list*`, `page*`, `count*`, `search*` | `NOT_SUPPORTED` (read-only, no transaction overhead) |
| `save(E entity)` | `REQUIRED` (creates or joins a transaction) |
| `remove(E entity)` | `REQUIRED` (creates or joins a transaction) |

> **Important:** The `@TransactionAttribute(NOT_SUPPORTED)` must be **repeated on the concrete service class** as well (see examples below). This ensures the annotation is visible at the EJB container level regardless of inheritance.

### 3.2 Built-in Methods

All methods delegate directly to the BO. The service layer adds **no extra logic** beyond transaction boundary control.

| Method | Delegates to |
| :--- | :--- |
| `save(E entity)` | `bo.save(entity)` — routes to persist or merge |
| `remove(E entity)` | `bo.remove(entity)` |
| `findById(E entity)` | `bo.findById(entity)` |
| `findById(Long userId, E entity)` | `bo.findById(userId, entity)` |
| `find(E entity)` | `bo.find(entity)` |
| `find(Long userId, E entity)` | `bo.find(userId, entity)` |
| `findAll(E entity)` | `bo.findAll(entity)` |
| `findAll(Long userId, E entity)` | `bo.findAll(userId, entity)` |
| `findAll(E, firstResult, maxResult, orderByAsc, containsAnyKeyWords)` | `bo.findAll(...)` |
| `findAll(Long userId, E, firstResult, maxResult, orderByAsc, containsAnyKeyWords)` | `bo.findAll(userId, ...)` |
| `listAll(Class entity)` | `bo.listAll(entity)` |
| `listAll(Long userId, Class entity)` | `bo.listAll(userId, entity)` |
| `pageAll(E, firstResult, maxResult, orderByAsc)` | `bo.pageAll(...)` |
| `pageAll(Long userId, E, firstResult, maxResult, orderByAsc)` | `bo.pageAll(userId, ...)` |
| `countAll(Class entity)` | `bo.countAll(entity)` |
| `countAll(Long userId, Class entity)` | `bo.countAll(userId, entity)` |
| `countFindAll(E entity, containsAnyKeyWords)` | `bo.countFindAll(...)` |
| `countFindAll(Long userId, E entity, containsAnyKeyWords)` | `bo.countFindAll(userId, ...)` |
| `search(TSelect<E> sel)` | `bo.search(sel)` |
| `search(Long userId, TSelect<E> sel)` | `bo.search(userId, sel)` |
| `search(TSelect<E>, firstResult, maxResult)` | `bo.search(sel, firstResult, maxResult)` |
| `search(Long userId, TSelect<E>, firstResult, maxResult)` | `bo.search(userId, sel, firstResult, maxResult)` |
| `countSearch(TSelect<E> sel)` | `bo.countSearch(sel)` |
| `countSearch(Long userId, TSelect<E> sel)` | `bo.countSearch(userId, sel)` |

## 4. EJB Configuration Rules

| Annotation | Value | Purpose |
| :--- | :--- | :--- |
| `@Stateless` | `name = "[ServiceClassName]"` | Declares the bean as a stateless session bean with a JNDI-reachable name |
| `@LocalBean` | — | Exposes the bean without a separate business interface, allowing direct local access |
| `@TransactionAttribute` | `NOT_SUPPORTED` | Default for read operations — no transaction overhead |

## 5. Implementation Patterns

### 5.1 Pattern A — Generic Application Service (Recommended First Step)

When creating a new application, always start by creating one generic service that can handle any entity via the generic BO.

**When to use:** Standard CRUD operations on any entity that does not require custom service methods.

```java
package org.tedros.myapp.ejb.service;

import org.tedros.myapp.cdi.bo.MyAppBO;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;
import org.tedros.server.entity.ITEntity;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

/**
 * The generic transactional service bean for the MyApp application.
 *
 * @author Author Name
 */
@LocalBean
@Stateless(name = "MyAppService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class MyAppService<E extends ITEntity> extends TEjbService<E> {

    @Inject
    private MyAppBO<E> bo;

    @Override
    public ITGenericBO<E> getBussinesObject() {
        return bo;
    }
}
```

---

### 5.2 Pattern B — Specific Entity Service

When an entity has a specialized BO (and EAO) with custom query methods, create a dedicated service that injects the specific BO and exposes its custom methods.

**When to use:** The entity has a dedicated BO with custom methods that must be accessible to the client layer.

```java
package org.tedros.myapp.ejb.service;

import java.util.List;

import org.tedros.myapp.cdi.bo.SomeEntityBo;
import org.tedros.myapp.entity.SomeEntity;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

@LocalBean
@Stateless(name = "SomeEntityService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class SomeEntityService extends TEjbService<SomeEntity> {

    @Inject
    private SomeEntityBo bo;

    @Override
    public ITGenericBO<SomeEntity> getBussinesObject() {
        return bo;
    }

    // Delegates custom business method to the specific BO
    public List<SomeEntity> search(String name, String code) {
        return bo.search(name, code);
    }
}
```

## 6. Real-World Examples

### Example 1 — Generic Service (`ItSupportToolsService`)

First service created in the project. Wires the generic BO and handles all standard CRUD operations for any entity.

```java
package org.tedros.it.tools.ejb.service;

import org.tedros.it.tools.cdi.bo.ItSupportToolsBO;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;
import org.tedros.server.entity.ITEntity;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

/**
 * The transactional service bean for the IT Support Tools application.
 *
 * @author Davis Dun
 */
@LocalBean
@Stateless(name = "ItSupportToolsService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ItSupportToolsService<E extends ITEntity> extends TEjbService<E> {

    @Inject
    private ItSupportToolsBO<E> bo;

    @Override
    public ITGenericBO<E> getBussinesObject() {
        return bo;
    }
}
```

---

### Example 2 — Specific Entity Service (`JobEvidenceService`)

Created because `JobEvidence` has a specialized BO (`JobEvidenceBo`) with a custom multi-filter `search` method that the client layer needs to call directly.

```java
package org.tedros.it.tools.ejb.service;

import java.util.Date;
import java.util.List;

import org.tedros.it.tools.cdi.bo.JobEvidenceBo;
import org.tedros.it.tools.entity.JobEvidence;
import org.tedros.person.model.Employee;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

@LocalBean
@Stateless(name = "JobEvidenceService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class JobEvidenceService extends TEjbService<JobEvidence> {

    @Inject
    private JobEvidenceBo bo;

    @Override
    public ITGenericBO<JobEvidence> getBussinesObject() {
        return bo;
    }

    // Delegates custom query to the specific BO
    public List<JobEvidence> search(String name, String issueNumber, String issueTitle,
                                    Employee employee, Date executionDate,
                                    String orderBy, String asc) {
        return bo.search(name, issueNumber, issueTitle, employee, executionDate, orderBy, asc);
    }
}
```

## 7. Full Layer Relationship Summary

```text
Client (JavaFX / REST)
    └── calls Controller  (@Stateless, @TSecurityInterceptor — security & routing)
            └── calls EJB Service  (@Stateless, @LocalBean — transaction boundary)
                    └── injects BO  (@Dependent / @RequestScoped — business rules)
                            └── injects EAO  (@Dependent / @RequestScoped — persistence)
                                    └── EntityManager (JPA / EclipseLink)
```

| Layer | Responsibility |
| :--- | :--- |
| **Controller** | Security enforcement, access policy validation, client entry point |
| **EJB Service** | Transaction boundary control, JNDI exposure, thin delegation to BO |
| **BO** | Business rules, user-scoped filtering, save routing (persist vs. merge) |
| **EAO** | Query execution, EntityManager access, Query By Example |
| **Entity** | JPA mapping and domain data |

## 8. Development Constraints

- **Package:** Always place services under `org.tedros.[app_name].ejb.service`.
- **Module:** Always create services in the `[app_name]-ejb` Maven module.
- **Annotations:** Always declare `@Stateless`, `@LocalBean`, and `@TransactionAttribute(NOT_SUPPORTED)` on the concrete service class.
- **No logic:** The service method body must contain **only** a delegation call to the BO — zero business logic, zero query building.
- **Transaction writes:** Never override `save` or `remove` unless you need additional pre/post logic; `TEjbService` already marks them as `REQUIRED`.
- **Custom methods:** A specific service method must be a thin delegate to the corresponding BO method.
- **Decision rule:** Start with the generic service. Only create an entity-specific service when the entity has a specialized BO with custom methods that the client must call.
- **Pairing rule:** A specific entity service **must** always be paired with a specific entity BO (and its EAO) of the same entity type.
