# Skill: Tedros Repository (EAO) Implementation Guide

## 1. Objective

Provide a technical reference for creating the persistence layer within the Tedros ecosystem using the **EAO (Entity Access Object)** pattern — the framework's equivalent of the DAO pattern. This ensures consistency in CDI scope usage, query building, and adherence to the framework's class hierarchy.

## 2. Architectural Placement

- **Project Location:** All EAO classes must be created in the `[APP_NAME]-ejb` project.
- **Package:** `org.tedros.[app_name].cdi.eao`
- **Dependency:** EAOs must extend `TGenericEAO<E>` from `org.tedros.server.cdi.eao`.

## 3. Base Class: `TGenericEAO<E>`

`TGenericEAO<E extends ITEntity>` is the abstract generic class that provides the full persistence layer out of the box. All EAOs must extend it directly or indirectly.

### 3.1 Built-in Methods

| Method | Description |
| :--- | :--- |
| `persist(E entity)` | Persists a new entity. Automatically sets `insertDate` on new records and `lastUpdate` on updates. |
| `merge(E entity)` | Merges a detached entity. Same date logic as `persist`. |
| `remove(E entity)` | Removes an entity. Re-attaches it to the context if detached. |
| `findById(E entity)` | Finds a single entity by its `id`. |
| `find(E entity)` | Finds a single entity using Query By Example. |
| `findAll(E entity)` | Returns all entities matching the example (supports `LIKE` for String fields). |
| `findAll(E entity, firstResult, maxResult, orderByAsc, containsAnyKeyWords)` | Paginated find with ordering and keyword search support. |
| `listAll(Class entity)` | Returns all persisted instances of an entity class. |
| `listAll(Long userId, Class entity)` | Returns all records filtered by `createdByUserId`. |
| `listAll(Class entity, boolean asc)` | Returns all records ordered by `id` asc or desc. |
| `listAll(Long userId, Class entity, boolean asc)` | Filtered by user and ordered by `id`. |
| `pageAll(E entity, firstResult, maxResult, orderByAsc)` | Paginated list using `CriteriaQuery`, respecting `entity.getOrderBy()`. |
| `countAll(Class entity)` | Counts all persisted records of an entity class. |
| `countAll(Long userId, Class entity)` | Counts records filtered by `createdByUserId`. |
| `countFindAll(E entity, containsAnyKeyWords)` | Counts results of a Query By Example. |
| `search(TSelect<E> sel)` | Executes a structured JPQL query built with `TSelect`. |
| `search(TSelect<E> sel, firstResult, maxResult)` | Paginated version of `search`. |
| `countSearch(TSelect<E> sel)` | Counts results of a `TSelect` query. |

### 3.2 Lifecycle Hooks

Override these empty methods in a specific EAO to inject custom logic at each lifecycle stage:

| Method | When it is called |
| :--- | :--- |
| `beforePersist(E entity)` | Before `em.persist()` |
| `afterPersist(E entity)` | After `em.persist()` |
| `beforeMerge(E entity)` | Before `em.merge()` |
| `afterMerge(E entity)` | After `em.merge()` |
| `beforeRemove(E entity)` | Before `em.remove()` |
| `afterRemove(E entity)` | After `em.remove()` |
| `afterFind(E entity)` | After `findById` or `find` |
| `afterListAll(List<E> lst)` | After `listAll` |
| `afterFindAll(List<E> lst)` | After `findAll` |
| `afterPageAll(List<E> lst)` | After `pageAll` |

### 3.3 Helper Methods (protected)

Use these in specific EAOs when writing custom queries:

| Method | Description |
| :--- | :--- |
| `executeAndGetList(ReadAllQuery query)` | Executes an EclipseLink `ReadAllQuery` and returns a list. |
| `executeAndGetList(CriteriaQuery<E> cq)` | Executes a JPA `CriteriaQuery` and returns a list. |
| `executeAndGet(CriteriaQuery<T> cq)` | Executes a `CriteriaQuery` and returns a single result. |
| `getEntityManager()` | Exposes the injected `EntityManager` for custom JPQL queries. |

## 4. CDI Scope Rules

| Scenario | Recommended Scope |
| :--- | :--- |
| Generic app-level EAO (used by all entities) | `@Dependent` |
| Specific entity EAO with custom queries | `@RequestScoped` |

> **Note:** Use `@Dependent` for the generic EAO so that its scope is inherited from the injecting bean. Use `@RequestScoped` for specific EAOs that manage state within a single request.

## 5. Implementation Patterns

### 5.1 Pattern A — Generic Application EAO (Recommended First Step)

When creating a new application, always start by creating one generic EAO that can be injected and used for any entity in the project without custom query needs.

**When to use:** CRUD operations on any entity that does not require custom queries.

```java
package org.tedros.myapp.cdi.eao;

import org.tedros.server.cdi.eao.TGenericEAO;
import org.tedros.server.entity.ITEntity;
import jakarta.enterprise.context.Dependent;

/**
 * The generic Entity Access Object for the MyApp application.
 * This uses a Dependent context so its scope is dictated by the injecting bean.
 *
 * @author Author Name
 */
@Dependent
public class MyAppEAO<E extends ITEntity> extends TGenericEAO<E> {

}
```

**Injection example:**

```java
@Inject
private MyAppEAO<SomeEntity> someEntityEao;
```

---

### 5.2 Pattern B — Specific Entity EAO

When an entity requires custom queries (e.g., multi-join JPQL, date range filters, dynamic `WHERE` clauses), create a dedicated EAO that extends `TGenericEAO` typed to that specific entity.

**When to use:** The entity has complex search/filter requirements beyond Query By Example.

```java
package org.tedros.myapp.cdi.eao;

import java.util.List;
import org.tedros.myapp.entity.SomeEntity;
import org.tedros.server.cdi.eao.TGenericEAO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.Query;

@RequestScoped
public class SomeEntityEao extends TGenericEAO<SomeEntity> {

    @SuppressWarnings("unchecked")
    public List<SomeEntity> search(String name, String code) {

        StringBuilder sb = new StringBuilder("select distinct e ");
        sb.append("from SomeEntity e ")
          .append("where 1=1 ");

        if (name != null && !name.isBlank())
            sb.append("and lower(e.name) like :name ");
        if (code != null && !code.isBlank())
            sb.append("and e.code = :code ");

        sb.append("order by e.name asc");

        Query qry = super.getEntityManager().createQuery(sb.toString());

        if (name != null && !name.isBlank())
            qry.setParameter("name", "%" + name.toLowerCase() + "%");
        if (code != null && !code.isBlank())
            qry.setParameter("code", code);

        return qry.getResultList();
    }
}
```

**Injection example:**

```java
@Inject
private SomeEntityEao someEntityEao;
```

## 6. Real-World Examples

### Example 1 — Generic EAO (`ItSupportToolsEAO`)

This is the first EAO to be created in a new project. It is used for standard CRUD operations across all entities.

```java
package org.tedros.it.tools.cdi.eao;

import org.tedros.server.cdi.eao.TGenericEAO;
import org.tedros.server.entity.ITEntity;
import jakarta.enterprise.context.Dependent;

/**
 * The generic entity access object.
 * This uses a Dependent context.
 *
 * @author Davis Dun
 */
@Dependent
public class ItSupportToolsEAO<E extends ITEntity> extends TGenericEAO<E> {

}
```

---

### Example 2 — Specific Entity EAO (`JobEvidenceEao`)

This EAO is created because `JobEvidence` requires a complex multi-join query with optional filters including joins, date range, and dynamic ordering.

```java
package org.tedros.it.tools.cdi.eao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.tedros.it.tools.entity.JobEvidence;
import org.tedros.person.model.Employee;
import org.tedros.server.cdi.eao.TGenericEAO;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.Query;

@RequestScoped
public class JobEvidenceEao extends TGenericEAO<JobEvidence> {

    @SuppressWarnings("unchecked")
    public List<JobEvidence> search(String name, String issueNumber, String issueTitle,
                                    Employee employee, Date executionDate,
                                    String orderBy, String asc) {

        StringBuilder sb = new StringBuilder("select distinct p ");
        sb.append("from JobEvidence p ")
          .append("left join p.employee e ")
          .append("left join p.items i ")
          .append("where 1=1 ");

        if (StringUtils.isNotBlank(name))
            sb.append("and lower(p.name) like :name ");
        if (StringUtils.isNotBlank(issueNumber))
            sb.append("and p.issueNumber = :issueNumber ");
        if (StringUtils.isNotBlank(issueTitle))
            sb.append("and lower(p.issueTitle) like :issueTitle ");
        if (employee != null)
            sb.append("and e.id = :employeeId ");
        if (executionDate != null) {
            sb.append("and (p.executionDate >= :executionDateBegin ");
            sb.append("and p.executionDate < :executionDateEnd) ");
        }

        if (orderBy == null) orderBy = "p.name";
        if (asc == null) asc = "asc";

        sb.append("order by ").append(orderBy);
        sb.append(" ").append(asc);

        Query qry = super.getEntityManager().createQuery(sb.toString());

        if (StringUtils.isNotBlank(name))
            qry.setParameter("name", "%" + name.toLowerCase() + "%");
        if (StringUtils.isNotBlank(issueNumber))
            qry.setParameter("issueNumber", issueNumber);
        if (StringUtils.isNotBlank(issueTitle))
            qry.setParameter("issueTitle", "%" + issueTitle.toLowerCase() + "%");
        if (employee != null)
            qry.setParameter("employeeId", employee.getId());
        if (executionDate != null) {
            Date begin = DateUtils.truncate(executionDate, Calendar.DAY_OF_MONTH);
            Date end = DateUtils.addDays(begin, 1);
            qry.setParameter("executionDateBegin", begin);
            qry.setParameter("executionDateEnd", end);
        }

        return qry.getResultList();
    }
}
```

## 7. Development Constraints

- **Package:** Always place EAOs under `org.tedros.[app_name].cdi.eao`.
- **Module:** Always create EAOs in the `[app_name]-ejb` Maven module.
- **Scope:** Use `@Dependent` for generic EAOs; use `@RequestScoped` for entity-specific EAOs.
- **EntityManager:** Never inject `EntityManager` directly in an EAO — it is already managed by `TGenericEAO`. Access it via `super.getEntityManager()`.
- **Custom Queries:** Always use dynamic `StringBuilder` + named parameters (`:param`) — never concatenate user input directly into JPQL strings.
- **String Filters:** Apply `lower()` in JPQL and `.toLowerCase()` on the parameter value for case-insensitive searches.
- **Null-Safety:** Always guard optional filter parameters with null/blank checks before appending to the query and before binding parameters.
- **Decision Rule:** Start with the generic EAO. Only create an entity-specific EAO when custom query logic is required.
