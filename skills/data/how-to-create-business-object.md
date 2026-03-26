# Skill: Tedros Business Object (BO) Implementation Guide

## 1. Objective

Provide a technical reference for creating the business logic layer within the Tedros ecosystem using **`TGenericBO`**. This class sits between the service (EJB) layer and the persistence layer (EAO), centralizing business rules such as user-based record filtering, save routing (persist vs. merge), and delegation to the appropriate EAO.

## 2. Architectural Placement

- **Project Location:** All BO classes must be created in the `[APP_NAME]-ejb` project.
- **Package:** `org.tedros.[app_name].cdi.bo`
- **Dependency:** BOs must extend `TGenericBO<E>` from `org.tedros.server.cdi.bo`.
- **Layer position:** `Service (EJB)` → `BO` → `EAO` → `EntityManager`

## 3. Base Class: `TGenericBO<E>`

`TGenericBO<E extends ITEntity>` is the abstract base class that implements `ITGenericBO<E>`. It delegates all persistence operations to the underlying EAO and adds **user-based filtering** as an extra layer on top.

The single abstract method that every BO **must** implement is:

```java
public abstract ITGenericEAO<E> getEao();
```

This method connects the BO to its corresponding EAO via CDI injection.

### 3.1 Built-in Methods

Every method that accepts a `Long userId` parameter automatically injects the user filter before delegating to the EAO.

| Method | Description |
| :--- | :--- |
| `save(E entity)` | Smart save: calls `persist` for new entities, `merge` for existing ones. Determined by `entity.isNew()`. |
| `remove(E entity)` | Delegates removal to the EAO. |
| `findById(E entity)` | Finds a single entity by `id`. |
| `findById(Long userId, E entity)` | Finds by `id` and validates `createdByUserId` — returns `null` if the record does not belong to the user. |
| `find(E entity)` | Query By Example — finds a single matching record. |
| `find(Long userId, E entity)` | Same as above, but injects `createdByUserId` into the example before querying. |
| `findAll(E entity)` | Returns all records matching the example (supports `LIKE` for Strings). |
| `findAll(Long userId, E entity)` | Same, filtered by `createdByUserId`. |
| `findAll(E entity, firstResult, maxResult, orderByAsc, containsAnyKeyWords)` | Paginated find with ordering and keyword search. |
| `findAll(Long userId, E entity, firstResult, maxResult, orderByAsc, containsAnyKeyWords)` | Same, filtered by user. |
| `listAll(Class entity)` | Returns all persisted instances of an entity class. |
| `listAll(Long userId, Class entity)` | Filtered by `createdByUserId`. |
| `pageAll(E entity, firstResult, maxResult, orderByAsc)` | Paginated list respecting `entity.getOrderBy()`. |
| `pageAll(Long userId, E entity, firstResult, maxResult, orderByAsc)` | Same, filtered by user. |
| `countAll(Class entity)` | Counts all persisted records. |
| `countAll(Long userId, Class entity)` | Counts records filtered by `createdByUserId`. |
| `countFindAll(E entity, containsAnyKeyWords)` | Counts results of a Query By Example. |
| `countFindAll(Long userId, E entity, containsAnyKeyWords)` | Same, filtered by user. |
| `search(TSelect<E> sel)` | Executes a structured JPQL query built with `TSelect`. |
| `search(Long userId, TSelect<E> sel)` | Automatically appends a `createdByUserId = userId` condition before querying. |
| `search(TSelect<E> sel, firstResult, maxResult)` | Paginated version of `search`. |
| `search(Long userId, TSelect<E> sel, firstResult, maxResult)` | Paginated, filtered by user. |
| `countSearch(TSelect<E> sel)` | Counts results of a `TSelect` query. |
| `countSearch(Long userId, TSelect<E> sel)` | Same, filtered by user. |

### 3.2 The `save` Method — Persist vs. Merge

`TGenericBO` provides a unified `save(E entity)` method that removes the need for the caller to decide between `persist` and `merge`:

```java
@Override
public E save(E entity) throws Exception {
    if (entity.isNew()) {
        getEao().persist(entity);
        return entity;
    } else {
        return getEao().merge(entity);
    }
}
```

> **Rule:** Always use `save()` in the service layer. Never call `persist` or `merge` directly from outside the BO.

## 4. CDI Scope Rules

| Scenario | Recommended Scope |
| :--- | :--- |
| Generic app-level BO (used for all entities) | `@Dependent` |
| Specific entity BO with custom business methods | `@RequestScoped` |

> **Note:** The BO scope must be compatible with its injected EAO scope. A `@Dependent` BO injecting a `@Dependent` EAO is always safe. A `@RequestScoped` BO injecting a `@RequestScoped` EAO is also valid.

## 5. Implementation Patterns

### 5.1 Pattern A — Generic Application BO (Recommended First Step)

When creating a new application, always start by creating one generic BO that can be injected and used for any entity in the project without custom business logic.

**When to use:** Standard CRUD operations on any entity that does not require custom business rules.

```java
package org.tedros.myapp.cdi.bo;

import org.tedros.myapp.cdi.eao.MyAppEAO;
import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;
import org.tedros.server.entity.ITEntity;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

/**
 * The generic CDI business object for the MyApp application.
 * Uses a Dependent context so its scope is inherited from the injecting bean.
 *
 * @author Author Name
 */
@Dependent
public class MyAppBO<E extends ITEntity> extends TGenericBO<E> {

    @Inject
    private MyAppEAO<E> eao;

    @Override
    public ITGenericEAO<E> getEao() {
        return eao;
    }
}
```

**Injection example (in an EJB service):**

```java
@Inject
private MyAppBO<SomeEntity> someEntityBo;

// save (auto-routes persist vs merge)
someEntityBo.save(entity);

// list all
List<SomeEntity> list = someEntityBo.listAll(SomeEntity.class);
```

---

### 5.2 Pattern B — Specific Entity BO

When an entity requires custom business methods (e.g., delegating to a specialized EAO method), create a dedicated BO typed to that specific entity. It injects the specialized EAO and exposes the custom query methods as business-layer calls.

**When to use:** The entity has a dedicated EAO with custom queries that must be exposed through the business layer.

```java
package org.tedros.myapp.cdi.bo;

import java.util.List;

import org.tedros.myapp.cdi.eao.SomeEntityEao;
import org.tedros.myapp.entity.SomeEntity;
import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class SomeEntityBo extends TGenericBO<SomeEntity> {

    @Inject
    private SomeEntityEao eao;

    @Override
    public ITGenericEAO<SomeEntity> getEao() {
        return eao;
    }

    // Delegates to the specialized EAO method
    public List<SomeEntity> search(String name, String code) {
        return eao.search(name, code);
    }
}
```

**Injection example (in an EJB service):**

```java
@Inject
private SomeEntityBo someEntityBo;

List<SomeEntity> results = someEntityBo.search("foo", "BAR-01");
```

## 6. Real-World Examples

### Example 1 — Generic BO (`ItSupportToolsBO`)

First BO created in the project. Wires the generic EAO and is suitable for all standard CRUD operations across any entity.

```java
package org.tedros.it.tools.cdi.bo;

import org.tedros.it.tools.cdi.eao.ItSupportToolsEAO;
import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;
import org.tedros.server.entity.ITEntity;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

/**
 * The CDI business object for the IT Support Tools application.
 *
 * @author Davis Dun
 */
@Dependent
public class ItSupportToolsBO<E extends ITEntity> extends TGenericBO<E> {

    @Inject
    private ItSupportToolsEAO<E> eao;

    @Override
    public ITGenericEAO<E> getEao() {
        return eao;
    }
}
```

---

### Example 2 — Specific Entity BO (`JobEvidenceBo`)

Created because `JobEvidence` has a specialized EAO (`JobEvidenceEao`) with a custom multi-filter `search` method. The BO wraps and exposes this method at the business layer, keeping the service tier clean.

```java
package org.tedros.it.tools.cdi.bo;

import java.util.Date;
import java.util.List;

import org.tedros.it.tools.cdi.eao.JobEvidenceEao;
import org.tedros.it.tools.entity.JobEvidence;
import org.tedros.person.model.Employee;
import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class JobEvidenceBo extends TGenericBO<JobEvidence> {

    @Inject
    private JobEvidenceEao eao;

    @Override
    public ITGenericEAO<JobEvidence> getEao() {
        return eao;
    }

    // Delegates custom query to the specialized EAO
    public List<JobEvidence> search(String name, String issueNumber, String issueTitle,
                                    Employee employee, Date executionDate,
                                    String orderBy, String asc) {
        return eao.search(name, issueNumber, issueTitle, employee, executionDate, orderBy, asc);
    }
}
```

## 7. Layer Relationship Summary

```text
EJB Service (stateless)
    └── injects BO
            └── injects EAO
                    └── EntityManager (JPA / EclipseLink)
```

- **BO** is responsible for business rules, user-scoped filtering, and save routing.
- **BO** never accesses `EntityManager` directly — that is exclusively the EAO's responsibility.
- **BO** custom methods are thin wrappers that delegate to the specialized EAO method.

## 8. Development Constraints

- **Package:** Always place BOs under `org.tedros.[app_name].cdi.bo`.
- **Module:** Always create BOs in the `[app_name]-ejb` Maven module.
- **Scope:** Use `@Dependent` for generic BOs; use `@RequestScoped` for entity-specific BOs.
- **EAO access:** Never inject `EntityManager` or call JPA directly from the BO. Access persistence exclusively via `getEao()`.
- **Save routing:** Always use `save(entity)` — never call `persist` or `merge` directly from outside the BO.
- **Custom methods:** A specific BO method must be a thin delegate to the EAO — business logic beyond user filtering should not live in the BO query itself.
- **Decision rule:** Start with the generic BO. Only create an entity-specific BO when its corresponding EAO has specialized methods that need to be exposed to the service layer.
- **Pairing rule:** A specific entity BO **must** always be paired with a specific entity EAO of the same entity type.
